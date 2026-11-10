package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import models.Project;

public class ProjectDAO {

    public int countProjects(String keywords) {
        if (keywords == null) keywords = "";
        String sql = "SELECT COUNT(*) FROM Project WHERE project_name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keywords + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Project> getProjectPaging(String keywords, int size, int page) {
        if (keywords == null) keywords = "";
        List<Project> list = new ArrayList<>();
        String sql = "SELECT * FROM Project WHERE project_name LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keywords + "%");
            ps.setInt(2, size);
            ps.setInt(3, (page - 1) * size);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Project(rs.getInt("id"), rs.getString("project_name"), rs.getDate("start_date"), rs.getDate("end_date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Project> getAll() {
        List<Project> list = new ArrayList<>();
        String sql = "SELECT * FROM Project ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Project(rs.getInt("id"), rs.getString("project_name"), rs.getDate("start_date"), rs.getDate("end_date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Project> getProjectsByManager(int managerId) {
        List<Project> list = new ArrayList<>();
        String sql = "SELECT * FROM Project WHERE create_user = ? ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Project(rs.getInt("id"), rs.getString("project_name"), rs.getDate("start_date"), rs.getDate("end_date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(String name, java.sql.Date startDate, java.sql.Date endDate, int createUser) {
        String sql = "INSERT INTO Project (project_name, start_date, end_date, create_user) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            ps.setInt(4, createUser);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM Project WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Project findById(int id) {
        String sql = "SELECT * FROM Project WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Project(rs.getInt("id"), rs.getString("project_name"), rs.getDate("start_date"), rs.getDate("end_date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(int id, String name, Date startDate, Date endDate) {
        String sql = "UPDATE Project SET project_name = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}