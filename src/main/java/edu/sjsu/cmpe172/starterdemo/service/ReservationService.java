package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.*;
import edu.sjsu.cmpe172.starterdemo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final WaitlistRepository waitlistRepository;
    private final NotificationClient notificationClient;
    private final SystemMetricsService metricsService;
    private final UserRepository userRepository;
    private final ClassCreditRepository classCreditRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ScheduleRepository scheduleRepository,
                              WaitlistRepository waitlistRepository,
                              NotificationClient notificationClient,
                              SystemMetricsService metricsService,
                              UserRepository userRepository,
                              ClassCreditRepository classCreditRepository) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
        this.waitlistRepository = waitlistRepository;
        this.notificationClient = notificationClient;
        this.metricsService = metricsService;
        this.userRepository = userRepository;
        this.classCreditRepository = classCreditRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByCustomer(Long customerUserId) {
        return reservationRepository.findByCustomerUserId(customerUserId);
    }

    @Transactional
    public String cancelReservation(Long reservationId, Long customerUserId) {
        Reservation reservation = reservationRepository.findById(reservationId);

        if (reservation == null || !"Booked".equals(reservation.getStatus())) {
            return "Cancellation failed. Reservation not found or already canceled.";
        }

        Long classId = reservation.getClassId();

        int rowsUpdated = reservationRepository.cancelReservation(reservationId, customerUserId);

        if (rowsUpdated == 0) {
            return "Cancellation failed. Reservation not found or already canceled.";
        }

        classCreditRepository.addCredits(customerUserId, 1);

        WaitlistEntry nextWaitlisted = waitlistRepository.findFirstWaitingByClassId(classId);

        if (nextWaitlisted != null) {
            boolean creditDeducted =
                    classCreditRepository.deductOneCredit(nextWaitlisted.getCustomerUserId());

            if (creditDeducted) {
                reservationRepository.insertReservation(
                        nextWaitlisted.getCustomerUserId(),
                        classId
                );

                waitlistRepository.markPromoted(nextWaitlisted.getWaitlistId());

                logger.info("Waitlisted customer promoted: customerUserId={}, classId={}",
                        nextWaitlisted.getCustomerUserId(), classId);

                return "Reservation canceled successfully. One credit refunded. Next waitlisted customer was promoted.";
            }

            logger.warn("Waitlisted customer could not be promoted due to insufficient credits: customerUserId={}, classId={}",
                    nextWaitlisted.getCustomerUserId(), classId);
        }

        logger.info("Reservation canceled: reservationId={}, customerUserId={}",
                reservationId, customerUserId);

        return "Reservation canceled successfully. One class credit has been refunded.";
    }

    @Transactional
    public String makeReservation(Long customerUserId, Long classId) {
        long startTime = System.currentTimeMillis();

        logger.info("Booking request started: customerUserId={}, classId={}", customerUserId, classId);

        try {
            ClassSession selected = scheduleRepository.findByIdForUpdate(classId);

            if (selected == null) {
                long latency = System.currentTimeMillis() - startTime;
                metricsService.recordFailedBooking(latency);
                logger.warn("Booking failed: class not found. customerUserId={}, classId={}", customerUserId, classId);
                return "Class not found";
            }

            if (reservationRepository.existsByCustomerAndClass(customerUserId, classId)) {
                long latency = System.currentTimeMillis() - startTime;
                metricsService.recordFailedBooking(latency);
                logger.warn("Booking rejected: duplicate reservation. customerUserId={}, classId={}", customerUserId, classId);
                return "You already reserved this class";
            }

            if (waitlistRepository.existsByCustomerAndClass(customerUserId, classId)) {
                long latency = System.currentTimeMillis() - startTime;
                metricsService.recordFailedBooking(latency);
                logger.warn("Booking rejected: customer already waitlisted. customerUserId={}, classId={}", customerUserId, classId);
                return "You are already on the waitlist for this class";
            }

            int bookedCount = reservationRepository.countBookedReservations(classId);

            if (bookedCount < selected.getClassCapacity()) {

                boolean creditDeducted = classCreditRepository.deductOneCredit(customerUserId);

                if (!creditDeducted) {
                    long latency = System.currentTimeMillis() - startTime;
                    metricsService.recordFailedBooking(latency);

                    logger.warn("Booking rejected: insufficient credits. customerUserId={}, classId={}",
                            customerUserId, classId);

                    return "Reservation failed: not enough class credits.";
                }

                reservationRepository.insertReservation(customerUserId, classId);

                User customer = userRepository.findById(customerUserId);
                String customerEmail = customer != null ? customer.getEmail() : "unknown@example.com";

                NotificationRequest notificationRequest = new NotificationRequest(
                        customerUserId,
                        classId,
                        customerEmail,
                        "Your Pilates reservation is confirmed."
                );

                String notificationResult = notificationClient.sendReservationConfirmation(notificationRequest);

                long latency = System.currentTimeMillis() - startTime;
                metricsService.recordSuccessfulBooking(latency);

                logger.info("Booking successful: customerUserId={}, classId={}, latencyMs={}",
                        customerUserId, classId, latency);

                return "Reservation successful. " + notificationResult;
            }

            waitlistRepository.insertWaitlistEntry(customerUserId, classId);

            long latency = System.currentTimeMillis() - startTime;
            metricsService.recordFailedBooking(latency);

            logger.warn("Class full: customer added to waitlist. customerUserId={}, classId={}, latencyMs={}",
                    customerUserId, classId, latency);

            return "Class is full. You have been added to the waitlist";

        } catch (Exception e) {
            long latency = System.currentTimeMillis() - startTime;
            metricsService.recordFailedBooking(latency);

            logger.error("Booking failed due to system error: customerUserId={}, classId={}, latencyMs={}",
                    customerUserId, classId, latency, e);

            return "Reservation failed due to a system error. Please try again later.";
        }
    }
}