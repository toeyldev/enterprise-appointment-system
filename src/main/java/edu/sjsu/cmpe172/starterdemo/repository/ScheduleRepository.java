package edu.sjsu.cmpe172.starterdemo.repository;

import edu.sjsu.cmpe172.starterdemo.model.ClassSession;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepository {

    private final Map<Long, ClassSession> store = new HashMap<>();
    private long nextId = 1L;

    public ScheduleRepository() {
        // Mock data
        save(new ClassSession(null, "03-10-2026","6:00 AM", "Instructor 1", 20));
        save(new ClassSession(null, "03-10-2026","7:15 AM", "Instructor 1", 20));
        save(new ClassSession(null, "03-10-2026","8:30 AM", "Instructor 2", 20));
        save(new ClassSession(null, "03-10-2026","9:45 AM", "Instructor 2", 20));
    }

    public List<ClassSession> findAll() {
        return new ArrayList<>(store.values());
    }

    public ClassSession save(ClassSession session) {
        if (session.getClassId() == null) {
            session.setClassId(nextId++);
        }
        store.put(session.getClassId(), session);
        return session;
    }
}
