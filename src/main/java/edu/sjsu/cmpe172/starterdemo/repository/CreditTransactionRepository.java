package edu.sjsu.cmpe172.starterdemo.repository;

import edu.sjsu.cmpe172.starterdemo.model.CreditTransaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CreditTransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public CreditTransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertTransaction(Long customerUserId,
                                  Long packageId,
                                  int creditsAdded) {

        String sql = """
                    INSERT INTO credit_transactions
                    (customer_user_id, package_id, credits_added, purchase_date_and_time)
                    VALUES (?, ?, ?, CURRENT_TIMESTAMP)
                """;

        jdbcTemplate.update(sql,
                customerUserId,
                packageId,
                creditsAdded);
    }

    public List<CreditTransaction> findByCustomerUserId(Long customerUserId) {

        String sql = """
                    SELECT transaction_id, customer_user_id, package_id,
                           credits_added, purchase_date_and_time
                    FROM credit_transactions
                    WHERE customer_user_id = ?
                    ORDER BY transaction_id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CreditTransaction(
                        rs.getLong("transaction_id"),
                        rs.getLong("customer_user_id"),
                        rs.getLong("package_id"),
                        rs.getInt("credits_added"),
                        rs.getString("purchase_date_and_time")
                ), customerUserId
        );
    }
}