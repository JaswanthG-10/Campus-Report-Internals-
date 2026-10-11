
package com.campusissue.dao;

import com.campusissue.database.DatabaseConnection;
import com.campusissue.model.Admin;

import java.sql.*;

public class AdminDAO {

    public Admin login(String username, String password)
            throws SQLException {

        String sql = """
            SELECT * FROM admins
            WHERE username = ? AND password = ?
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Admin admin = new Admin();
                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setUsername(rs.getString("username"));
                    admin.setPassword(rs.getString("password"));
                    return admin;
                }
            }
        }

        return null;
    }
}
