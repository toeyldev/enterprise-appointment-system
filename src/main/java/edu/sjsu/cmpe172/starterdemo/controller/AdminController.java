package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.User;
import edu.sjsu.cmpe172.starterdemo.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/instructors")
    public List<User> getInstructors() {
        return adminService.getInstructors();
    }

    @PostMapping("/classes")
    public String addClass(@RequestBody Map<String, Object> body) {
        String classDate = body.get("classDate").toString();
        String classTime = body.get("classTime").toString();
        Long instructorUserId = Long.valueOf(body.get("instructorUserId").toString());
        int classCapacity = Integer.parseInt(body.get("classCapacity").toString());

        return adminService.addClass(classDate, classTime, instructorUserId, classCapacity);
    }

    @PostMapping("/classes/cancel")
    public String cancelClass(@RequestBody Map<String, Long> body) {
        Long classId = body.get("classId");
        return adminService.cancelClass(classId);
    }

    @PostMapping("/classes/update-instructor")
    public String updateInstructor(@RequestBody Map<String, Long> body) {
        Long classId = body.get("classId");
        Long instructorUserId = body.get("instructorUserId");

        return adminService.updateInstructor(classId, instructorUserId);
    }
}