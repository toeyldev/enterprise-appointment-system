package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.ClassSession;
import edu.sjsu.cmpe172.starterdemo.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    //@GetMapping("/schedule")
    //public String schedule() { return "schedule"; }

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
}