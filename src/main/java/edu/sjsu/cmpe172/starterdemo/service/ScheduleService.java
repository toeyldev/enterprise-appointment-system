package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.ClassSession;
import edu.sjsu.cmpe172.starterdemo.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository repo;

    public ScheduleService(ScheduleRepository repo) { this.repo = repo;}

    public List<ClassSession> getAllSessions() { return repo.findAll(); }

    public ClassSession addClassSession(ClassSession session) {
        return repo.save(session);
    }

    public List<ClassSession> getSessionsByInstructor(Long instructorUserId) {
        return repo.findByInstructorUserId(instructorUserId);
    }
}