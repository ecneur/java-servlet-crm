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

    public int[] getTaskStatistics(int roleId, int userId) {
        int[] stats = new int[3]; 
        String sql = "";
        
        if (roleId == 1) { 
            sql = "SELECT s.id, COUNT(t.id) FROM Status s LEFT JOIN Task t ON s.id = t.status_id GROUP BY s.id";
        } else if (roleId == 2) { 
            sql = "SELECT s.id, COUNT(t.id) FROM Status s LEFT JOIN (SELECT t1.* FROM Task t1 JOIN Project p ON t1.project_id = p.id WHERE p.create_user = ?) t ON s.id = t.status_id GROUP BY s.id";
        } else if (roleId == 3) { 
            sql = "SELECT s.id, COUNT(t.id) FROM Status s LEFT JOIN (SELECT * FROM Task WHERE user_id = ?) t ON s.id = t.status_id GROUP BY s.id";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            if (roleId == 2 || roleId == 3) {
                ps.setInt(1, userId);
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int statusId = rs.getInt(1);
                int count = rs.getInt(2);
                
                if (statusId == 1) stats[0] = count;
                else if (statusId == 2) stats[1] = count;
                else if (statusId == 3) stats[2] = count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

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
                     "WHERE t.task_name LIKE ? ORDER BY t.id DESC " +
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

    public List<TaskDTO> getAllTasksDTO() {
        List<TaskDTO> list = new ArrayList<>();
        String sql = "SELECT t.id, t.task_name, p.project_name, u.fullname, t.start_date, t.end_date, s.name_status " +
                     "FROM Task t " +
                     "INNER JOIN Project p ON t.project_id = p.id " +
                     "INNER JOIN User u ON t.user_id = u.id " +
                     "INNER JOIN Status s ON t.status_id = s.id ORDER BY t.id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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

    public List<TaskDTO> getTasksByEmployee(int employeeId) {
        List<TaskDTO> list = new ArrayList<>();
        String sql = "SELECT t.id, t.task_name, p.project_name, u.fullname, t.start_date, t.end_date, s.name_status " +
                     "FROM Task t " +
                     "INNER JOIN Project p ON t.project_id = p.id " +
                     "INNER JOIN User u ON t.user_id = u.id " +
                     "INNER JOIN Status s ON t.status_id = s.id " +
                     "WHERE t.user_id = ? ORDER BY t.id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
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

    public List<TaskDTO> getTasksByManager(int managerId) {
        List<TaskDTO> list = new ArrayList<>();
        String sql = "SELECT t.id, t.task_name, p.project_name, u.fullname, t.start_date, t.end_date, s.name_status " +
                     "FROM Task t " +
                     "INNER JOIN Project p ON t.project_id = p.id " +
                     "INNER JOIN User u ON t.user_id = u.id " +
                     "INNER JOIN Status s ON t.status_id = s.id " +
                     "WHERE p.create_user = ? ORDER BY t.id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);
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
}