package edu.sjsu.cmpe172.starterdemo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addClass(String classDate,
                         String classTime,
                         Long instructorUserId,
                         String instructorName,
                         int classCapacity) {

        String sql = """
                INSERT INTO class_sessions
                (class_date, class_time, instructor_user_id, instructor_name, class_capacity, status)
                VALUES (?, ?, ?, ?, ?, 'Active')
                """;

        jdbcTemplate.update(
                sql,
                classDate,
                classTime,
                instructorUserId,
                instructorName,
                classCapacity
        );
    }

    public int cancelClass(Long classId) {
        String sql = """
            UPDATE class_sessions
            SET status = 'Canceled'
            WHERE class_id = ?
              AND status = 'Active'
            """;

        return jdbcTemplate.update(sql, classId);
    }

    public int updateInstructor(Long classId,
                                Long instructorUserId,
                                String instructorName) {

        String sql = """
                UPDATE class_sessions
                SET instructor_user_id = ?,
                    instructor_name = ?
                WHERE class_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                instructorUserId,
                instructorName,
                classId
        );
    }

    public int cancelBookedReservationsForClass(Long classId) {
        String sql = """
                UPDATE reservations
                SET status = 'Canceled by Admin',
                    canceled_at = CURRENT_TIMESTAMP
                WHERE class_id = ?
                  AND status = 'Booked'
                """;

        return jdbcTemplate.update(sql, classId);
    }

    public void refundCreditsForCanceledClass(Long classId) {
        String sql = """
                UPDATE class_credit cc
                JOIN reservations r ON cc.customer_user_id = r.customer_user_id
                SET cc.remaining_credit = cc.remaining_credit + 1
                WHERE r.class_id = ?
                  AND r.status = 'Canceled by Admin'
                """;

        jdbcTemplate.update(sql, classId);
    }
}