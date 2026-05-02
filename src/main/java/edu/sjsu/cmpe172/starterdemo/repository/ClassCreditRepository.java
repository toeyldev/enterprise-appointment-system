package edu.sjsu.cmpe172.starterdemo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClassCreditRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClassCreditRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getBalance(Long customerUserId) {
        String sql = """
                SELECT remaining_credit
                FROM class_credit
                WHERE customer_user_id = ?
                """;

        Integer result = jdbcTemplate.query(
                sql,
                rs -> rs.next() ? rs.getInt("remaining_credit") : 0,
                customerUserId
        );

        return result == null ? 0 : result;
    }

    public void addCredits(Long customerUserId, int credits) {
        String sql = """
                INSERT INTO class_credit (customer_user_id, remaining_credit)
                VALUES (?, ?)
                ON DUPLICATE KEY UPDATE
                remaining_credit = remaining_credit + VALUES(remaining_credit)
                """;

        jdbcTemplate.update(sql, customerUserId, credits);
    }

    public boolean deductOneCredit(Long customerUserId) {
        String sql = """
                UPDATE class_credit
                SET remaining_credit = remaining_credit - 1
                WHERE customer_user_id = ?
                  AND remaining_credit > 0
                """;

        int rows = jdbcTemplate.update(sql, customerUserId);
        return rows > 0;
    }

    public void addCredit(Long customerUserId, int amount) {
        String sql = """
                UPDATE class_credits
                SET balance = balance + ?
                WHERE customer_user_id = ?
                """;

        jdbcTemplate.update(sql, amount, customerUserId);
    }
}