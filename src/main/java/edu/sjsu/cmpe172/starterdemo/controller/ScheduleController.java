package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.ClassSession;
import edu.sjsu.cmpe172.starterdemo.service.ScheduleService;
import org.springframework.web.bind.annotation.*;
import edu.sjsu.cmpe172.starterdemo.dto.ClassStudentListDTO;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClassSession> getSessions() {
        return service.getAllSessions();
    }

    @PostMapping
    public ClassSession createSession(@RequestBody ClassSession session) {
        return service.addClassSession(session);
    }

    @GetMapping("/instructor/{instructorUserId}")
    public List<ClassSession> getInstructorSessions(@PathVariable Long instructorUserId) {
        return service.getSessionsByInstructor(instructorUserId);
    }

    @GetMapping("/classes/{classId}/students")
    public ClassStudentListDTO getClassStudents(@PathVariable Long classId) {
        return service.getClassStudentList(classId);
    }
}