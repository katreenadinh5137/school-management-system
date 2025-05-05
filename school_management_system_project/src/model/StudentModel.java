package model;

import java.sql.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.Date;

import controller.*;
import my_util.*;

public class StudentModel {
	
	private Connection connect;
	
	public StudentModel() {
		
		try {
			connect = Database.getConnection();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Retrieves list of courses from the database
	public ArrayList<Course> getAvaibleCourseList() {
		ArrayList<Course> courseList = new ArrayList<>();
		try {
			//go to the database and select all from tb_contact
			String query = "SELECT * FROM tb_course WHERE status = 'active'";
			
			// Prepare the SQL statement
			PreparedStatement stm = connect.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			
			// Loops through to create admin for each object
			while(result.next()) {
				String c = result.getString("code");
				String n = result.getString("name");
				String d = result.getString("description");
				int mc = result.getInt("max_capacity");
				String s = result.getString("status"); 
				
				// Create a new course object and adds it to the list
				Course course = new Course(c, n, d, mc, s);
				courseList.add(course);
			}
		}
		// Print error message if SQL exception occurs
		catch(SQLException e) {System.out.println(e.getMessage());}
		
		// return the list of contacts
		return courseList;
	}
	
	public ArrayList<Course> getEnrolledCourseList() {
	    ArrayList<Course> courseList = new ArrayList<>();

	    Student currentStudent = AuthenticatorController.getCurrentStudent();

	    if (currentStudent == null) {
	        System.out.println("Error: currentStudent is null. Please check the authentication process.");
	        return courseList;
	    }

	    try {
	        String query = "SELECT c.code, c.name " 
	                + "FROM tb_enrollment e "
	                + "JOIN tb_course c ON e.course_code = c.code "
	                + "WHERE e.student_id = ?";
	        
	        PreparedStatement stm = connect.prepareStatement(query);
	        stm.setInt(1, currentStudent.getId());
	        ResultSet result = stm.executeQuery();

	        while(result.next()) {
	            String c = result.getString("code");
	            String n = result.getString("name");

	            Course course = new Course(c, n);
	            courseList.add(course);
	        }
	    }
	    catch(SQLException e) {
	        System.out.println(e.getMessage());
	    }

	    return courseList;
	}
	
	public ArrayList<Email> getEmailList() {
		ArrayList<Email> emailList = new ArrayList<>();
		
		Student currentStudent = AuthenticatorController.getCurrentStudent();
		try {
			//go to the database and select all from tb_contact
			String query = "SELECT * FROM tb_message WHERE recipient_id = ?";
			
			// Prepare the SQL statement
			PreparedStatement stm = connect.prepareStatement(query);
			
			stm.setInt(1, currentStudent.getId());
			
			ResultSet result = stm.executeQuery();
			
			// Loops through to create admin for each object
			while(result.next()) {
				int sId = result.getInt("sender_id");
				int rId = result.getInt("recipient_id");
				String c = result.getString("code_code");
				String s = result.getString("subject");
				String m = result.getString("message");
				String t = result.getString("timestamp");
				
				// Create a new course object and adds it to the list
				Email email = new Email(sId, rId, c, s, m, t, "read");
				emailList.add(email);
			}
		}
		// Print error message if SQL exception occurs
		catch(SQLException e) {System.out.println(e.getMessage());}
		
		// return the list of contacts
		return emailList;
	}

	public boolean enroll(Student student, Course course) {
	    try {
	        // Check if the student is already enrolled in the course
	        String checkEnrollmentQuery = "SELECT COUNT(*) FROM tb_enrollment WHERE student_id = ? AND course_code = ?";
	        try (PreparedStatement stmCheck = connect.prepareStatement(checkEnrollmentQuery)) {
	            stmCheck.setInt(1, student.getId());
	            stmCheck.setString(2, course.getCode());
	            ResultSet rsCheck = stmCheck.executeQuery();

	            if (rsCheck.next() && rsCheck.getInt(1) > 0) {
	                // The student is already enrolled in the course
	                return false;
	            }
	        }

	        // Proceed to insert the enrollment if not already enrolled
	        LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String enrollmentDate = currentDate.format(formatter);

	        String query = "INSERT INTO tb_enrollment (student_id, course_code, enrollment_date) VALUES (?, ?, ?)";
	        try (PreparedStatement stm = connect.prepareStatement(query)) {
	            stm.setInt(1, student.getId());
	            stm.setString(2, course.getCode());
	            stm.setString(3, enrollmentDate);

	            int row = stm.executeUpdate();

	            // Return true if the enrollment was successful (i.e., at least one row affected)
	            return row > 0;
	        }

	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
	
	public boolean sendEmail(int senderId, int recipientId, String course, String subject, String message) {
	    // Get the current timestamp in the desired format
	    LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedTimestamp = now.format(formatter);

	    // Use the correct table name and fields
	    String query = "INSERT INTO tb_message (sender_id, recipient_id, code_code, subject, message, timestamp) VALUES (?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement stm = connect.prepareStatement(query)) {
	        stm.setInt(1, senderId);
	        stm.setInt(2, recipientId);
	        stm.setString(3, course);
	        stm.setString(4, subject);
	        stm.setString(5, message);
	        stm.setString(6, formattedTimestamp);

	        int rowsAffected = stm.executeUpdate();
	        return rowsAffected > 0;  // Return true if email was saved to the DB
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public int getTeacherIdByEmail(String email) {
	    try {
	        String query = "SELECT id FROM tb_user WHERE email = ?";
	        PreparedStatement stmt = connect.prepareStatement(query);
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; // Not found
	}

	public int getStudentIdByEmail(String email) {
	    try {
	        String query = "SELECT id FROM tb_user WHERE email = ? AND role = 'student'";
	        PreparedStatement stmt = connect.prepareStatement(query);
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; // Not found
	}
}