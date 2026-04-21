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
                SELECT class_id, class_date, class_time, instructor_name, class_capacity
                FROM class_sessions
                ORDER BY class_id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ClassSession(
                        rs.getLong("class_id"),
                        rs.getString("class_date"),
                        rs.getString("class_time"),
                        rs.getString("instructor_name"),
                        rs.getInt("class_capacity")
                )
        );
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
}
