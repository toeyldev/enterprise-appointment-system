package edu.sjsu.cmpe172.starterdemo.repository;

import edu.sjsu.cmpe172.starterdemo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(Long userId) {
        String sql = """
                SELECT user_id, first_name, last_name, email, password, role
                FROM users
                WHERE user_id = ?
                """;

        List<User> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getLong("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                ), userId);

        return results.isEmpty() ? null : results.get(0);
    }

    public User findByEmail(String email) {
        String sql = """
                SELECT user_id, first_name, last_name, email, password, role
                FROM users
                WHERE email = ?
                """;

        List<User> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getLong("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                ), email);

        return results.isEmpty() ? null : results.get(0);
    }
}