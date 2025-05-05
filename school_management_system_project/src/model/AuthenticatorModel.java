package model;
import java.sql.*;
import my_util.*;

public class AuthenticatorModel {
	
	private Connection connect;
	
	public AuthenticatorModel() {
		try {
			connect = Database.getConnection();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public User authenticate(String email, String password) {
	    try {
	        String query = "SELECT * FROM tb_user WHERE email = ?";
	        PreparedStatement stm = connect.prepareStatement(query);
	        stm.setString(1, email);
	        ResultSet rs = stm.executeQuery();
	        if (rs.next()) {
	            String storedPassword = rs.getString("password");
	            String role = rs.getString("role_type");
	            int id = rs.getInt("id");
	            String firstName = rs.getString("first_name");
	            String lastName = rs.getString("last_name");
	            // Verify password using Security
	            if (Security.verifyPassword(password, storedPassword)) {
	                // Return the appropriate user based on role
	                if ("admin".equals(role)) {
	                    return new Admin(id, firstName, lastName, email, storedPassword);  // Return Admin with full info
	                } 
	                else if ("student".equals(role)) {
	                    return new Student(id, firstName, lastName, email, storedPassword);  // Return Student with full info
	                } 
	                else if ("teacher".equals(role)) {
	                    return new Teacher(id, firstName, lastName, email, storedPassword);  // Return Teacher with full info
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;  // Authentication failed
	}
	
	/**
	 * Checks if an email exists in the database
	 * @param email the email to check
	 * @return true if the email exists, false otherwise
	 */
	public boolean emailExists(String email) {
	    try {
	        String query = "SELECT COUNT(*) FROM tb_user WHERE email = ?";
	        PreparedStatement stm = connect.prepareStatement(query);
	        stm.setString(1, email);
	        ResultSet rs = stm.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Updates a user's password in the database
	 * @param email the user's email
	 * @param newPassword the new password (will be hashed before storage)
	 * @return true if update was successful, false otherwise
	 */
	public boolean updatePassword(String email, String newPassword) {
	    try {
	        // Hash the password before storing
	        String hashedPassword = Security.hashPassword(newPassword);
	        
	        String query = "UPDATE tb_user SET password = ? WHERE email = ?";
	        PreparedStatement stm = connect.prepareStatement(query);
	        stm.setString(1, hashedPassword);
	        stm.setString(2, email);
	        
	        int rowsAffected = stm.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}