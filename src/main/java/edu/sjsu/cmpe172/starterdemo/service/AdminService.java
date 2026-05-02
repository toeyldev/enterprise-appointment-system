package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.User;
import edu.sjsu.cmpe172.starterdemo.repository.AdminRepository;
import edu.sjsu.cmpe172.starterdemo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    public AdminService(AdminRepository adminRepository,
                        UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }

    public List<User> getInstructors() {
        return userRepository.findByRole("Instructor");
    }

    @Transactional
    public String addClass(String classDate,
                           String classTime,
                           Long instructorUserId,
                           int classCapacity) {

        User instructor = userRepository.findById(instructorUserId);

        if (instructor == null || !"Instructor".equals(instructor.getRole())) {
            return "Instructor not found.";
        }

        String instructorName = instructor.getFirstName() + " " + instructor.getLastName();

        adminRepository.addClass(
                classDate,
                classTime,
                instructorUserId,
                instructorName,
                classCapacity
        );

        return "Class added successfully.";
    }

    @Transactional
    public String cancelClass(Long classId) {
        int rowsUpdated = adminRepository.cancelClass(classId);

        if (rowsUpdated == 0) {
            return "Class not found or already canceled.";
        }

        return "Class canceled successfully.";
    }

    @Transactional
    public String updateInstructor(Long classId, Long instructorUserId) {
        User instructor = userRepository.findById(instructorUserId);

        if (instructor == null || !"Instructor".equals(instructor.getRole())) {
            return "Instructor not found.";
        }

        String instructorName = instructor.getFirstName() + " " + instructor.getLastName();

        int rowsUpdated = adminRepository.updateInstructor(
                classId,
                instructorUserId,
                instructorName
        );

        if (rowsUpdated == 0) {
            return "Class not found.";
        }

        return "Instructor updated successfully.";
    }
}