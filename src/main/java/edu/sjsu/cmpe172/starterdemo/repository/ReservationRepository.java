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
                ON DUPLICATE KEY UPDATE
                    status = 'Booked',
                    reserved_at = CURRENT_TIMESTAMP,
                    canceled_at = NULL
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

    public List<Reservation> findByCustomerUserId(Long customerUserId) {
        String sql = """
                SELECT reservation_id, customer_user_id, class_id, status, reserved_at, canceled_at
                FROM reservations
                WHERE customer_user_id = ?
                ORDER BY reserved_at DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getLong("customer_user_id"),
                        rs.getLong("class_id"),
                        rs.getString("status"),
                        rs.getString("reserved_at"),
                        rs.getString("canceled_at")
                ), customerUserId);
    }

    public int cancelReservation(Long reservationId, Long customerUserId) {
        String sql = """
                UPDATE reservations
                SET status = 'Canceled',
                    canceled_at = CURRENT_TIMESTAMP
                WHERE reservation_id = ?
                  AND customer_user_id = ?
                  AND status = 'Booked'
                """;

        return jdbcTemplate.update(sql, reservationId, customerUserId);
    }

    public Reservation findById(Long reservationId) {
        String sql = """
                SELECT reservation_id, customer_user_id, class_id, status, reserved_at, canceled_at
                FROM reservations
                WHERE reservation_id = ?
                """;

        List<Reservation> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getLong("customer_user_id"),
                        rs.getLong("class_id"),
                        rs.getString("status"),
                        rs.getString("reserved_at"),
                        rs.getString("canceled_at")
                ), reservationId);

        return results.isEmpty() ? null : results.get(0);
    }

    public List<Reservation> findBookedReservationsByClassId(Long classId) {
        String sql = """
                SELECT reservation_id, customer_user_id, class_id, status, reserved_at, canceled_at
                FROM reservations
                WHERE class_id = ?
                  AND status = 'Booked'
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getLong("customer_user_id"),
                        rs.getLong("class_id"),
                        rs.getString("status"),
                        rs.getString("reserved_at"),
                        rs.getString("canceled_at")
                ), classId);
    }

    public void deleteByClassId(Long classId) {
        String sql = "DELETE FROM reservations WHERE class_id = ?";
        jdbcTemplate.update(sql, classId);
    }
}
