package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.ClassSession;
import edu.sjsu.cmpe172.starterdemo.model.Reservation;
import edu.sjsu.cmpe172.starterdemo.repository.ReservationRepository;
import edu.sjsu.cmpe172.starterdemo.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ScheduleRepository scheduleRepository) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public String makeReservation(Long customerUserId, Long classId) {

        List<ClassSession> sessions = scheduleRepository.findAll();

        ClassSession selected = sessions.stream()
                .filter(s -> s.getClassId().equals(classId))
                .findFirst()
                .orElse(null);

        if (selected == null) {
            return "Class not found";
        }

        // NEW: prevent duplicate booking
        if (reservationRepository.existsByCustomerAndClass(customerUserId, classId)) {
            return "You already reserved this class";
        }

        int bookedCount = reservationRepository.countBookedReservations(classId);

        if (bookedCount >= selected.getClassCapacity()) {
            return "Class is full";
        }

        reservationRepository.insertReservation(customerUserId, classId);

        return "Reservation successful";
    }
}
