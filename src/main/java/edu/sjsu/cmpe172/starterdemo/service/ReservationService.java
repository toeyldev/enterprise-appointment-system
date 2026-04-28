package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.ClassSession;
import edu.sjsu.cmpe172.starterdemo.model.NotificationRequest;
import edu.sjsu.cmpe172.starterdemo.model.Reservation;
import edu.sjsu.cmpe172.starterdemo.repository.ReservationRepository;
import edu.sjsu.cmpe172.starterdemo.repository.ScheduleRepository;
import edu.sjsu.cmpe172.starterdemo.repository.WaitlistRepository;
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

    public ReservationService(ReservationRepository reservationRepository,
                              ScheduleRepository scheduleRepository,
                              WaitlistRepository waitlistRepository,
                              NotificationClient notificationClient,
                              SystemMetricsService metricsService) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
        this.waitlistRepository = waitlistRepository;
        this.notificationClient = notificationClient;
        this.metricsService = metricsService;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
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
                reservationRepository.insertReservation(customerUserId, classId);

                NotificationRequest notificationRequest = new NotificationRequest(
                        customerUserId,
                        classId,
                        "customer1@example.com",
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