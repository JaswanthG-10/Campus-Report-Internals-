
package com.campusissue.dao;

import com.campusissue.database.DatabaseConnection;
import com.campusissue.model.Problem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemDAO {

    public List<Problem> findAll() throws SQLException {
        List<Problem> problems = new ArrayList<>();

        String sql = """
            SELECT problem_id, problem_name, category
            FROM problems
            ORDER BY problem_id
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Problem problem = new Problem();
                problem.setProblemId(rs.getInt("problem_id"));
                problem.setProblemName(rs.getString("problem_name"));
                problem.setCategory(rs.getString("category"));

                problems.add(problem);
            }
        }

        return problems;
    }

    public Problem findById(int problemId) throws SQLException {
        String sql = """
            SELECT problem_id, problem_name, category
            FROM problems WHERE problem_id = ?
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, problemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Problem problem = new Problem();
                    problem.setProblemId(rs.getInt("problem_id"));
                    problem.setProblemName(rs.getString("problem_name"));
                    problem.setCategory(rs.getString("category"));
                    return problem;
                }
            }
        }

        return null;
    }
}
