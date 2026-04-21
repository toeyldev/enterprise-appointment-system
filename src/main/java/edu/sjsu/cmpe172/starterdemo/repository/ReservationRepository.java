package edu.sjsu.cmpe172.starterdemo.repository;

import edu.sjsu.cmpe172.starterdemo.model.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertReservation(Long customerUserId, Long classId) {
        String sql = """
                INSERT INTO reservations
                (customer_user_id, class_id, status, reserved_at, canceled_at)
                VALUES (?, ?, 'Booked', CURRENT_TIMESTAMP, NULL)
                """;

        jdbcTemplate.update(sql, customerUserId, classId);
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT reservation_id, customer_user_id, class_id, status, reserved_at, canceled_at
                FROM reservations
                ORDER BY reservation_id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getLong("customer_user_id"),
                        rs.getLong("class_id"),
                        rs.getString("status"),
                        rs.getString("reserved_at"),
                        rs.getString("canceled_at")
                )
        );
    }

    public int countBookedReservations(Long classId) {
        String sql = """
                SELECT COUNT(*)
                FROM reservations
                WHERE class_id = ? AND status = 'Booked'
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, classId);
        return count == null ? 0 : count;
    }

    public boolean existsByCustomerAndClass(Long customerUserId, Long classId) {
        String sql = """
                SELECT COUNT(*)
                FROM reservations
                WHERE customer_user_id = ? 
                  AND class_id = ?
                  AND status = 'Booked'
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                customerUserId,
                classId
        );

        return count != null && count > 0;
    }
}
