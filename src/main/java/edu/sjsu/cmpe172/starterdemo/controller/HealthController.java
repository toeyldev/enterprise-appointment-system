package edu.sjsu.cmpe172.starterdemo.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        String databaseStatus = "UP";

        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        } catch (Exception e) {
            databaseStatus = "DOWN";
        }

        String overallStatus = databaseStatus.equals("UP") ? "UP" : "DEGRADED";

        return Map.of(
                "status", overallStatus,
                "service", "Pilates Scheduler",
                "database", databaseStatus,
                "timestamp", LocalDateTime.now().toString()
        );
    }
}