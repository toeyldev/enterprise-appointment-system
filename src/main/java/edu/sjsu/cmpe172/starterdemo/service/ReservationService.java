package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.ClassSession;
import edu.sjsu.cmpe172.starterdemo.model.NotificationRequest;
import edu.sjsu.cmpe172.starterdemo.model.Reservation;
import edu.sjsu.cmpe172.starterdemo.repository.ReservationRepository;
import edu.sjsu.cmpe172.starterdemo.repository.ScheduleRepository;
import edu.sjsu.cmpe172.starterdemo.repository.WaitlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final WaitlistRepository waitlistRepository;
    private final NotificationClient notificationClient;

    public ReservationService(ReservationRepository reservationRepository,
                              ScheduleRepository scheduleRepository,
                              WaitlistRepository waitlistRepository,
                              NotificationClient notificationClient) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
        this.waitlistRepository = waitlistRepository;
        this.notificationClient = notificationClient;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public String makeReservation(Long customerUserId, Long classId) {

        ClassSession selected = scheduleRepository.findByIdForUpdate(classId);

        if (selected == null) {
            return "Class not found";
        }

        if (reservationRepository.existsByCustomerAndClass(customerUserId, classId)) {
            return "You already reserved this class";
        }

        if (waitlistRepository.existsByCustomerAndClass(customerUserId, classId)) {
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

            return "Reservation successful. " + notificationResult;
        }

        waitlistRepository.insertWaitlistEntry(customerUserId, classId);
        return "Class is full. You have been added to the waitlist";
    }
}