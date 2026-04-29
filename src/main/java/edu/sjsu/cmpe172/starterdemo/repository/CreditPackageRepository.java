package edu.sjsu.cmpe172.starterdemo.repository;

import edu.sjsu.cmpe172.starterdemo.model.CreditPackage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CreditPackageRepository {

    private final JdbcTemplate jdbcTemplate;

    public CreditPackageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CreditPackage> findAll() {
        String sql = """
                SELECT package_id, package_cost, classes_per_package
                FROM credit_packages
                ORDER BY package_id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CreditPackage(
                        rs.getLong("package_id"),
                        rs.getDouble("package_cost"),
                        rs.getInt("classes_per_package")
                )
        );
    }

    public CreditPackage save(CreditPackage creditPackage) {
        String sql = """
                INSERT INTO credit_packages
                (package_cost, classes_per_package)
                VALUES (?, ?)
                """;

        jdbcTemplate.update(
                sql,
                creditPackage.getPackageCost(),
                creditPackage.getClassesPerPackage()
        );

        return creditPackage;
    }

    public CreditPackage findById(Long packageId) {
        String sql = """
                    SELECT package_id, package_cost, classes_per_package
                    FROM credit_packages
                    WHERE package_id = ?
                """;

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return new CreditPackage(
                        rs.getLong("package_id"),
                        rs.getDouble("package_cost"),
                        rs.getInt("classes_per_package")
                );
            }
            return null;
        }, packageId);
    }
}
