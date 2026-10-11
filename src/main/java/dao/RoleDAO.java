package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.DBConnection;
import models.Role;

public class RoleDAO {

	public List<Role> getAll() {
		List<Role> roles = new ArrayList<Role>();

		String sql = "SELECT * FROM Role";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {
			while(rs.next()) {
				roles.add(new Role(rs.getInt("id"),rs.getString("name_role"),rs.getString("description")));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return roles;
	}

	public void insert(String name, String description) {
	    String sql = "INSERT INTO Role (name_role, description) VALUES (?, ?)";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, name);
	        ps.setString(2, description);
	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void deleteById(int id) {
	    String sql = "DELETE FROM Role WHERE id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public Role findById(int id) {
	    String sql = "SELECT * FROM Role WHERE id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return new Role(rs.getInt("id"), rs.getString("name_role"), rs.getString("description"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public void update(int id, String name, String description) {
	    String sql = "UPDATE Role SET name_role = ?, description = ? WHERE id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, name);
	        ps.setString(2, description);
	        ps.setInt(3, id);
	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
