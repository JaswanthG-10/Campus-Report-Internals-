
package com.campusissue.dao;

import com.campusissue.database.DatabaseConnection;
import com.campusissue.model.Workperson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkpersonDAO {

    public List<Workperson> findAll() throws SQLException {
        List<Workperson> workpersons = new ArrayList<>();

        String sql = """
            SELECT workperson_id, name, specialization, phone
            FROM workpersons
            ORDER BY workperson_id
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Workperson workperson = new Workperson();
                workperson.setWorkpersonId(
                    rs.getInt("workperson_id")
                );
                workperson.setName(rs.getString("name"));
                workperson.setSpecialization(
                    rs.getString("specialization")
                );
                workperson.setPhone(rs.getString("phone"));

                workpersons.add(workperson);
            }
        }

        return workpersons;
    }

    public Workperson findById(int workpersonId)
            throws SQLException {

        String sql = """
            SELECT workperson_id, name, specialization, phone
            FROM workpersons
            WHERE workperson_id = ?
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, workpersonId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Workperson workperson = new Workperson();
                    workperson.setWorkpersonId(
                        rs.getInt("workperson_id")
                    );
                    workperson.setName(rs.getString("name"));
                    workperson.setSpecialization(
                        rs.getString("specialization")
                    );
                    workperson.setPhone(rs.getString("phone"));
                    return workperson;
                }
            }
        }

        return null;
    }
}
