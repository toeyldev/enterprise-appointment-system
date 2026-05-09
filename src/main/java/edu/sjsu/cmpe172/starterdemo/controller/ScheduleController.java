package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.dto.ClassStudentListDTO;
import edu.sjsu.cmpe172.starterdemo.model.ClassSession;
import edu.sjsu.cmpe172.starterdemo.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    // return all active class sessions
    @GetMapping
    public List<ClassSession> getSessions() {
        return service.getAllSessions();
    }

    // create a new class session
    @PostMapping
    public ClassSession createSession(@RequestBody ClassSession session) {
        return service.addClassSession(session);
    }

    // instructor can view classes assigned to them
    @GetMapping("/instructor/{instructorUserId}")
    public List<ClassSession> getInstructorSessions(@PathVariable Long instructorUserId) {
        return service.getSessionsByInstructor(instructorUserId);
    }

    // return enrolled students for a class
    @GetMapping("/classes/{classId}/students")
    public ClassStudentListDTO getClassStudents(@PathVariable Long classId) {
        return service.getClassStudentList(classId);
    }

    // admin can cancel class from dashboard
    @DeleteMapping("/admin/classes/{classId}")
    public String cancelClassByAdmin(@PathVariable Long classId) {
        return service.cancelClassByAdmin(classId);
    }

    // return all sessions, including canceled ones
    @GetMapping("/all")
    public List<ClassSession> getAllSessionsIncludingCanceled() {
        return service.getAllSessionsIncludingCanceled();
    }
}