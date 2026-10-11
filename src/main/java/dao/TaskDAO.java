package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import dto.TaskDTO;
import models.Task;

public class TaskDAO {

    public int countTasks(String keywords) {
        if (keywords == null) keywords = "";
        String sql = "SELECT COUNT(*) FROM Task t " +
                     "INNER JOIN Project p ON t.project_id = p.id " +
                     "INNER JOIN User u ON t.user_id = u.id " +
                     "INNER JOIN Status s ON t.status_id = s.id " +
                     "WHERE t.task_name LIKE ?";
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

    public List<TaskDTO> getTaskPaging(String keywords, int size, int page) {
        if (keywords == null) keywords = "";
        List<TaskDTO> list = new ArrayList<>();
        String sql = "SELECT t.id, t.task_name, p.project_name, u.fullname, t.start_date, t.end_date, s.name_status " +
                     "FROM Task t " +
                     "INNER JOIN Project p ON t.project_id = p.id " +
                     "INNER JOIN User u ON t.user_id = u.id " +
                     "INNER JOIN Status s ON t.status_id = s.id " +
                     "WHERE t.task_name LIKE ? " +
                     "LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keywords + "%");
            ps.setInt(2, size);
            ps.setInt(3, (page - 1) * size);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new TaskDTO(
                    rs.getInt("id"),
                    rs.getString("task_name"),
                    rs.getString("project_name"),
                    rs.getString("fullname"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("name_status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(String taskName, java.sql.Date startDate, java.sql.Date endDate, int userId, int projectId, int statusId) {
        String sql = "INSERT INTO Task (task_name, start_date, end_date, user_id, project_id, status_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, taskName);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            ps.setInt(4, userId);
            ps.setInt(5, projectId);
            ps.setInt(6, statusId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM Task WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Task findById(int id) {
        String sql = "SELECT * FROM Task WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Task(
                    rs.getInt("id"),
                    rs.getString("task_name"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getInt("status_id"),
                    rs.getInt("user_id"),
                    rs.getInt("project_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TaskDTO findTaskDTOById(int id) {
        String sql = "SELECT t.id, t.task_name, p.project_name, u.fullname, t.start_date, t.end_date, s.name_status " +
                     "FROM Task t " +
                     "INNER JOIN Project p ON t.project_id = p.id " +
                     "INNER JOIN User u ON t.user_id = u.id " +
                     "INNER JOIN Status s ON t.status_id = s.id " +
                     "WHERE t.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TaskDTO(
                    rs.getInt("id"),
                    rs.getString("task_name"),
                    rs.getString("project_name"),
                    rs.getString("fullname"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("name_status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(int id, String taskName, java.sql.Date startDate, java.sql.Date endDate, int userId, int projectId, int statusId) {
        String sql = "UPDATE Task SET task_name = ?, start_date = ?, end_date = ?, user_id = ?, project_id = ?, status_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, taskName);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            ps.setInt(4, userId);
            ps.setInt(5, projectId);
            ps.setInt(6, statusId);
            ps.setInt(7, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<TaskDTO> getTasksByUserId(int userId) {
        List<TaskDTO> list = new ArrayList<>();
        String sql = "SELECT t.id, t.task_name, p.project_name, u.fullname, t.start_date, t.end_date, s.name_status " +
                     "FROM Task t " +
                     "INNER JOIN Project p ON t.project_id = p.id " +
                     "INNER JOIN User u ON t.user_id = u.id " +
                     "INNER JOIN Status s ON t.status_id = s.id " +
                     "WHERE t.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new TaskDTO(
                    rs.getInt("id"),
                    rs.getString("task_name"),
                    rs.getString("project_name"),
                    rs.getString("fullname"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("name_status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int[] getTaskStatistics() {
        int[] stats = new int[3]; // [0]: Chưa bắt đầu, [1]: Đang thực hiện, [2]: Hoàn thành
        String sql = "SELECT s.name_status, COUNT(t.id) FROM Status s LEFT JOIN Task t ON s.id = t.status_id GROUP BY s.name_status";
        try (java.sql.Connection conn = connection.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String statusName = rs.getString(1).toLowerCase();
                int count = rs.getInt(2);
                
                if (statusName.contains("chua") || statusName.contains("chưa")) {
                    stats[0] += count;
                } else if (statusName.contains("dang") || statusName.contains("đang")) {
                    stats[1] += count;
                } else if (statusName.contains("hoan") || statusName.contains("hoàn") || statusName.contains("xong")) {
                    stats[2] += count;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }
}