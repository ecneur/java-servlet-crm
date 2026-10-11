package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import models.Status;

public class StatusDAO {
    public List<Status> getAll() {
        List<Status> list = new ArrayList<>();
        String sql = "SELECT * FROM Status";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Status(rs.getInt("id"), rs.getString("name_status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}