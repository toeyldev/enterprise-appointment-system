package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.dto.ClassStudentListDTO;
import edu.sjsu.cmpe172.starterdemo.model.ClassSession;
import edu.sjsu.cmpe172.starterdemo.model.Reservation;
import edu.sjsu.cmpe172.starterdemo.model.User;
import edu.sjsu.cmpe172.starterdemo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository repo;
    private final ReservationRepository reservationRepository;
    private final WaitlistRepository waitlistRepository;
    private final ClassCreditRepository classCreditRepository;
    private final UserRepository userRepository;
    private final NotificationClient notificationClient;

    public ScheduleService(ScheduleRepository repo,
                           ReservationRepository reservationRepository,
                           WaitlistRepository waitlistRepository,
                           ClassCreditRepository classCreditRepository,
                           UserRepository userRepository,
                           NotificationClient notificationClient) {
        this.repo = repo;
        this.reservationRepository = reservationRepository;
        this.waitlistRepository = waitlistRepository;
        this.classCreditRepository = classCreditRepository;
        this.userRepository = userRepository;
        this.notificationClient = notificationClient;
    }

    public List<ClassSession> getAllSessions() {
        return repo.findAll();
    }

    public ClassSession addClassSession(ClassSession session) {
        return repo.save(session);
    }

    public List<ClassSession> getSessionsByInstructor(Long instructorUserId) {
        return repo.findByInstructorUserId(instructorUserId);
    }

    public ClassStudentListDTO getClassStudentList(Long classId) {
        return repo.getClassStudentList(classId);
    }

    @Transactional
    public String cancelClassByAdmin(Long classId) {
        ClassSession session = repo.findById(classId);

        if (session == null) {
            return "Class not found.";
        }

        List<Reservation> bookedReservations =
                reservationRepository.findBookedReservationsByClassId(classId);

        for (Reservation reservation : bookedReservations) {
            Long customerUserId = reservation.getCustomerUserId();

            classCreditRepository.addCredit(customerUserId, 1);

            User customer = userRepository.findById(customerUserId);

            notificationClient.sendNotification(
                    customerUserId,
                    classId,
                    customer.getEmail(),
                    "Your Pilates class on " + session.getClassDate()
                            + " at " + session.getClassTime()
                            + " was canceled. Credit refunded."
            );
        }

        reservationRepository.deleteByClassId(classId);
        waitlistRepository.deleteByClassId(classId);
        repo.deleteById(classId);

        return "Class canceled successfully.";
    }

    public List<ClassSession> getAllSessionsIncludingCanceled() {
        return repo.findAllIncludingCanceled();
    }
}