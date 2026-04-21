package edu.sjsu.cmpe172.starterdemo.repository;

import edu.sjsu.cmpe172.starterdemo.model.ClassSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ClassSession> findAll() {
        String sql = """
                SELECT cs.class_id,
                       cs.class_date,
                       cs.class_time,
                       cs.instructor_name,
                       cs.class_capacity,
                       (
                           cs.class_capacity -
                           (
                               SELECT COUNT(*)
                               FROM reservations r
                               WHERE r.class_id = cs.class_id
                                 AND r.status = 'Booked'
                           )
                       ) AS available_spots,
                       (
                           SELECT COUNT(*)
                           FROM waitlist w
                           WHERE w.class_id = cs.class_id
                             AND w.status = 'Waiting'
                       ) AS waitlist_count
                FROM class_sessions cs
                ORDER BY cs.class_id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ClassSession session = new ClassSession(
                    rs.getLong("class_id"),
                    rs.getString("class_date"),
                    rs.getString("class_time"),
                    rs.getString("instructor_name"),
                    rs.getInt("class_capacity")
            );
            session.setAvailableSpots(rs.getInt("available_spots"));
            session.setWaitlistCount(rs.getInt("waitlist_count"));
            return session;
        });
    }

    public ClassSession save(ClassSession session) {
        String sql = """
                INSERT INTO class_sessions
                (class_date, class_time, instructor_name, class_capacity)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                session.getClassDate(),
                session.getClassTime(),
                session.getInstructorName(),
                session.getClassCapacity()
        );

        return session;
    }

    public ClassSession findById(Long classId) {
        String sql = """
                SELECT class_id, class_date, class_time, instructor_name, class_capacity
                FROM class_sessions
                WHERE class_id = ?
                """;

        List<ClassSession> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                new ClassSession(
                        rs.getLong("class_id"),
                        rs.getString("class_date"),
                        rs.getString("class_time"),
                        rs.getString("instructor_name"),
                        rs.getInt("class_capacity")
                ), classId);

        return results.isEmpty() ? null : results.get(0);
    }

    public ClassSession findByIdForUpdate(Long classId) {
        String sql = """
                SELECT class_id, class_date, class_time, instructor_name, class_capacity
                FROM class_sessions
                WHERE class_id = ?
                FOR UPDATE
                """;

        List<ClassSession> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                new ClassSession(
                        rs.getLong("class_id"),
                        rs.getString("class_date"),
                        rs.getString("class_time"),
                        rs.getString("instructor_name"),
                        rs.getInt("class_capacity")
                ), classId);

        return results.isEmpty() ? null : results.get(0);
    }
}