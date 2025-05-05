package model;
import java.sql.*;
import java.util.ArrayList;

import controller.AuthenticatorController;
import my_util.Database;

public class TeacherModel {
	
private Connection connect;
	
	public TeacherModel() {
		
		try {
			connect = Database.getConnection();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public ArrayList <Course> getCourseStudentList(){
		ArrayList <Course> studentCourseList = new ArrayList<>();
		
		Teacher currentTeacher = AuthenticatorController.getCurrentTeacher();
		try {
			
			String query = "SELECT c.code AS course_code, c.name AS course_name, "
                    + "s.id AS student_id, s.first_name AS student_first_name, "
                    + "s.last_name AS student_last_name, s.email AS student_email "
                    + "FROM tb_teacher_courses tc "
                    + "JOIN tb_course c ON tc.course_code = c.code "
                    + "LEFT JOIN tb_enrollment e ON c.code = e.course_code "
                    + "LEFT JOIN tb_user s ON e.student_id = s.id "
                    + "WHERE tc.teacher_id = ? "
                    + "ORDER BY c.code, s.last_name";
			
			PreparedStatement stm = connect.prepareStatement(query);
			
			stm.setInt(1, currentTeacher.getId());
			
			ResultSet result = stm.executeQuery();
			
			while(result.next()) {
				String c = result.getString("course_code");
				String fn = result.getString("student_first_name");
				String ln = result.getString("student_last_name");
				
				Course course = new Course (c, fn, ln);
				studentCourseList.add(course);
			}
			
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return studentCourseList;
	}
	
	// get a teacher's assigned courses
	public ArrayList<Course> getCourseList(Teacher teacher) {
		ArrayList<Course> courseList = new ArrayList<>();
		try {
			//go to the database and select all from tb_contact
			String query = "SELECT * FROM tb_teacher_courses WHERE teacher_id = ?";
			
			// Prepare the SQL statement
			PreparedStatement stm = connect.prepareStatement(query);
	        stm.setInt(1, teacher.getId());
	        
	        ResultSet result = stm.executeQuery();
			
			// Loops through to create admin for each object
			while(result.next()) {
				String crs = result.getString("course_code");
				
				// Create a new course object and adds it to the list
				//Admin admin = new Admin(id, fn, ln, em, pw, rl);
				Course course = new Course(crs);
				courseList.add(course);
			}
		}
		// Print error message if SQL exception occurs
		catch(SQLException e) {System.out.println(e.getMessage());}
		
		// return the list of contacts
		return courseList;
	}
	
	// view enrolled students for a course
	public ArrayList<Student> getStudentList(Course course) {
		ArrayList<Student> studentList = new ArrayList<>();
		try {
			//go to the database and select all from tb_contact
			String query = "SELECT * FROM tb_enrollment WHERE code = ?";
			
			// Prepare the SQL statement
			PreparedStatement stm = connect.prepareStatement(query);
			
			stm.setString(1, course.getCode());
			
			ResultSet result = stm.executeQuery();
			
			// Loops through to create admin for each object
			while(result.next()) {
				int id = result.getInt("id");
				String fn = result.getString("first_name");
				String ln = result.getString("last_name");
				String em = result.getString("email");
				String pw = result.getString("password");
				//String rl = result.getString("role_type");
				
				// Create a new contact object and adds it to the list
				//Admin admin = new Admin(id, fn, ln, em, pw, rl);
				Student student = new Student(id, fn, ln, em, pw);
				studentList.add(student);
			}
		}
		// Print error message if SQL exception occurs
		catch(SQLException e) {System.out.println(e.getMessage());}
		
		// return the list of contacts
		return studentList;
	}
	
	public boolean sendEmail(int senderId, int recipientId, String courseCode, String subject, String message) {
	    try {
	        String query = "INSERT INTO tb_message (sender_id, recipient_id, code_code, subject, message, timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
	        PreparedStatement stm = connect.prepareStatement(query);
	        
	        stm.setInt(1, senderId);
	        stm.setInt(2, recipientId);
	        stm.setString(3, courseCode);
	        stm.setString(4, subject);
	        stm.setString(5, message);
	        
	        int row = stm.executeUpdate();
	        return row > 0;
	        
	    } catch(SQLException e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
		
	}
	
	public ArrayList<Email> getEmailList() {
		ArrayList<Email> emailList = new ArrayList<>();
		
		Teacher currentTeacher = AuthenticatorController.getCurrentTeacher();
		try {
			//go to the database and select all from tb_contact
			String query = "SELECT * FROM tb_message WHERE recipient_id = ?";
			
			// Prepare the SQL statement
			PreparedStatement stm = connect.prepareStatement(query);
			
			stm.setInt(1, currentTeacher.getId());
			
			ResultSet result = stm.executeQuery();
			
			// Loops through to create admin for each object
			while(result.next()) {
				int sId = result.getInt("sender_id");
				int rId = result.getInt("recipient_id");
				String c = result.getString("code_code");
				String s = result.getString("subject");
				String m = result.getString("message");
				String t = result.getString("timestamp"); 
				String st = result.getString("status");
				
				// Create a new course object and adds it to the list
				Email email = new Email(sId, rId, c, s, m, t, st);
				emailList.add(email);
			}
		}
		// Print error message if SQL exception occurs
		catch(SQLException e) {System.out.println(e.getMessage());}
		
		// return the list of contacts
		return emailList;
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
}
