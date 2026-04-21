package edu.sjsu.cmpe172.starterdemo.repository;

import edu.sjsu.cmpe172.starterdemo.model.WaitlistEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WaitlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public WaitlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertWaitlistEntry(Long customerUserId, Long classId) {
        String sql = """
                INSERT INTO waitlist
                (customer_user_id, class_id, joined_at, status)
                VALUES (?, ?, CURRENT_TIMESTAMP, 'Waiting')
                """;

        jdbcTemplate.update(sql, customerUserId, classId);
    }

    public boolean existsByCustomerAndClass(Long customerUserId, Long classId) {
        String sql = """
                SELECT COUNT(*)
                FROM waitlist
                WHERE customer_user_id = ?
                  AND class_id = ?
                  AND status = 'Waiting'
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, customerUserId, classId);
        return count != null && count > 0;
    }

    public int countWaitingByClassId(Long classId) {
        String sql = """
                SELECT COUNT(*)
                FROM waitlist
                WHERE class_id = ?
                  AND status = 'Waiting'
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, classId);
        return count == null ? 0 : count;
    }

    public List<WaitlistEntry> findAll() {
        String sql = """
                SELECT waitlist_id, customer_user_id, class_id, joined_at, status
                FROM waitlist
                ORDER BY waitlist_id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new WaitlistEntry(
                        rs.getLong("waitlist_id"),
                        rs.getLong("customer_user_id"),
                        rs.getLong("class_id"),
                        rs.getString("joined_at"),
                        rs.getString("status")
                )
        );
    }
}