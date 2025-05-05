package model;
import java.sql.*;
import my_util.*;
import java.util.ArrayList;



public class AdminModel {
	
	private Connection connect;
	
	public AdminModel() {
		
		try {
			connect = Database.getConnection();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	// === STUDENT METHODS ===
	
	public ArrayList<Student> getStudentList() {
		ArrayList<Student> studentList = new ArrayList<>();
		try {
			//go to the database and select all from tb_contact
			String query = "SELECT * FROM tb_user WHERE role_type = 'student'";
			
			// Prepare the SQL statement
			PreparedStatement stm = connect.prepareStatement(query);
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

	
	public boolean addStudent(Student student) {
		
		try {
		        
			// SQL query to insert a student to table
			String query = "INSERT INTO tb_user (first_name, last_name, email, password, role_type) VALUES (?, ?, ?, ?, 'student')";
			PreparedStatement stm = connect.prepareStatement(query);
			
			// Set values for the query
			stm.setString(1, student.getfName());
			stm.setString(2, student.getlName());
			stm.setString(3, student.getEmail());
			stm.setString(4, student.getPassword());
			
			int row = stm.executeUpdate();
			if (row > 0) {
				return true; // returns true if a row is affected
			}
			else {
				return false;
			}
		}
			catch(SQLException e) {
				System.out.println(e.getMessage()); // print error if SQL exception occurs
				return false;
			}
	}
	
	public boolean removeStudent(Student student) {
		 
		try {
			// SQL query to delete a student
			String query = "DELETE FROM tb_user WHERE id = ?";
			PreparedStatement stm = connect.prepareStatement(query);
			
			stm.setInt(1, student.getId());
			
			int row = stm.executeUpdate();
			
			if (row > 0) {
				return true;
			}
			else {
				return false;
			}
					
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	public boolean updateStudent(Student student) {
		try {
			// SQL query to update contact details
			String query = "UPDATE tb_user SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
			PreparedStatement stm = connect.prepareStatement(query);
			
			// Set values for the query
			stm.setString(1, student.getfName());
			stm.setString(2, student.getlName());
			stm.setString(3, student.getEmail());
			stm.setInt(4, student.getId());
			
			// Execute the update query
			int row = stm.executeUpdate();
			
			if (row > 0) {
				return true; // returns true if a row was updated
			}
			else {
				return false;
			}
		}
			// Print error message if SQL exception occurs
			catch(SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
	}
	
	
	// === TEACHER METHODS ===
	
	// Retrieves list of courses from the database
	public ArrayList<Teacher> getTeacherList() {
		ArrayList<Teacher> teacherList = new ArrayList<>();
		try {
			//go to the database and select all from tb_contact
			String query = "SELECT * FROM tb_user WHERE role_type = 'teacher'";
			
			// Prepare the SQL statement
			PreparedStatement stm = connect.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			
			// Loops through to create admin for each object
			while(result.next()) {
				int id = result.getInt("id");
				String fn = result.getString("first_name");
				String ln = result.getString("last_name");
				String em = result.getString("email");
				String pw = result.getString("password");
				
				// Create a new course object and adds it to the list
				Teacher teacher = new Teacher(id, fn, ln, em, pw);
				teacherList.add(teacher);
			}
		}
		// Print error message if SQL exception occurs
		catch(SQLException e) {System.out.println(e.getMessage());}
		
		// return the list of contacts
		return teacherList;
	}
	
	public boolean addTeacher(Teacher teacher) {
		
		try {
			// SQL query to insert a student to table
			String query = "INSERT INTO tb_user (first_name, last_name, email, password, role_type) VALUES (?, ?, ?, ?, 'teacher')";
			PreparedStatement stm = connect.prepareStatement(query);
			
			// Set values for the query
			stm.setString(1, teacher.getfName());
			stm.setString(2, teacher.getlName());
			stm.setString(3, teacher.getEmail());
			stm.setString(4, teacher.getPassword());
			
			int row = stm.executeUpdate();
			if (row > 0) {
				return true; // returns true if a row is affected
			}
			else {
				return false;
			}
		}
			catch(SQLException e) {
				System.out.println(e.getMessage()); // print error if SQL exception occurs
				return false;
			}
	}
	
	public boolean removeTeacher(Teacher teacher) {
		try {
			// SQL query to delete a student
			String query = "DELETE FROM tb_user WHERE id = ?";
			PreparedStatement stm = connect.prepareStatement(query);
			
			stm.setInt(1, teacher.getId());
			
			int row = stm.executeUpdate();
			
			if (row > 0) {
				return true;
			}
			else {
				return false;
			}
					
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean updateTeacher(Teacher teacher) {
		try {
			// SQL query to update contact details
			String query = "UPDATE tb_user SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
			PreparedStatement stm = connect.prepareStatement(query);
			
			// Set values for the query
			stm.setString(1, teacher.getfName());
			stm.setString(2, teacher.getlName());
			stm.setString(3, teacher.getEmail());
			stm.setInt(4, teacher.getId());
			
			// Execute the update query
			int row = stm.executeUpdate();
			
			if (row > 0) {
				return true; // returns true if a row was updated
			}
			else {
				return false;
			}
		}
			// Print error message if SQL exception occurs
			catch(SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
	}
	
	// === COURSE METHODS ===
	
	
	// Retrieves list of courses from the database
	public ArrayList<Course> getCourseList() {
		ArrayList<Course> courseList = new ArrayList<>();
		try {
			//go to the database and select all from tb_contact
			String query = "SELECT * FROM tb_course";
			
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
	
	// Retrieves list of courses from the database
	public ArrayList<Course> getAssignedCourseList() {
	    ArrayList<Course> courseList = new ArrayList<>();
	    try {
	        // SQL query to fetch all courses with their assigned teachers, even if no teacher is assigned (LEFT JOIN)
	        String query = "SELECT c.code, c.name, u.first_name, u.last_name "
	                     + "FROM tb_course c "
	                     + "LEFT JOIN tb_teacher_courses tc ON c.code = tc.course_code "
	                     + "LEFT JOIN tb_user u ON tc.teacher_id = u.id";
	        
	        // Prepare the SQL statement
	        PreparedStatement stm = connect.prepareStatement(query);
	        ResultSet result = stm.executeQuery();
	        
	        // Loops through the result set to create Course objects
	        while(result.next()) {
	            String courseCode = result.getString("code");
	            String courseName = result.getString("name");
	            String teacherFirstName = result.getString("first_name");
	            String teacherLastName = result.getString("last_name");
	            
	            // If no teacher is assigned, teacherFirstName and teacherLastName will be null
	            // Create a new Course object and add it to the list
	            Course course;
	            if (teacherFirstName == null || teacherLastName == null) {
	                // If no teacher is assigned, you can set them to "Unassigned" or handle as needed
	                course = new Course(courseCode, "Unassigned", "Unassigned");
	            } else {
	                course = new Course(courseCode, teacherFirstName, teacherLastName);
	            }
	            courseList.add(course);
	        }
	    } catch (SQLException e) {
	        // Print error message if SQL exception occurs
	        System.out.println(e.getMessage());
	    }

	    // Return the list of courses (with or without assigned teachers)
	    return courseList;
	}
	
	public boolean addCourse(Course course) {
		try {
			// SQL query to insert a student to table
			String query = "INSERT INTO tb_course (code, name, description, max_capacity, status) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stm = connect.prepareStatement(query);
			
			// Set values for the query
			stm.setString(1, course.getCode());
			stm.setString(2, course.getName());
			stm.setString(3, course.getDescription());
			stm.setInt(4, course.getMax_capacity());
			stm.setString(5, course.getStatus());
			
			int row = stm.executeUpdate();
			if (row > 0) {
				return true; // returns true if a row is affected
			}
			else {
				return false;
			}
		}
			catch(SQLException e) {
				System.out.println(e.getMessage()); // print error if SQL exception occurs
				return false;
			}
	}
	
	public boolean removeCourse(Course course) {
		try {
			// SQL query to delete a student
			String query = "DELETE FROM tb_course WHERE code = ?";
			PreparedStatement stm = connect.prepareStatement(query);
			
			stm.setString(1, course.getCode());
			
			int row = stm.executeUpdate();
			
			if (row > 0) {
				return true;
			}
			else {
				return false;
			}
					
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean updateCourse(Course course) {
		try {
			// SQL query to update contact details
			String query = "UPDATE tb_course SET name = ?, description = ?, max_capacity = ?, status = ? WHERE code = ?";
			PreparedStatement stm = connect.prepareStatement(query);
			
			// Set values for the query
			stm.setString(1, course.getName());
			stm.setString(2, course.getDescription());
			stm.setInt(3, course.getMax_capacity());
			stm.setString(4, course.getStatus());
			stm.setString(5, course.getCode());
			
			// Execute the update query
			int row = stm.executeUpdate();
			
			if (row > 0) {
				return true; // returns true if a row was updated
			}
			else {
				return false;
			}
		}
			// Print error message if SQL exception occurs
			catch(SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
	}
	
	public boolean assignTeacherToCourse(Course course) {
	    // Get teacher ID based on first and last name
	    int teacherId = getTeacherId(course.getfName(), course.getlName());
	    
	    if (teacherId == -1) {
	        System.out.println("Teacher not found.");
	        return false;
	    }

	    String sql = "INSERT INTO tb_teacher_courses (teacher_id, course_code) VALUES (?, ?)";
	    
	    try (PreparedStatement stmt = connect.prepareStatement(sql)) {
	        stmt.setInt(1, teacherId);  // Use the retrieved teacher ID
	        stmt.setString(2, course.getCode());
	        
	        int rowsInserted = stmt.executeUpdate();
	        return rowsInserted > 0; // Return true if at least one row was inserted
	    } catch (SQLException e) {
	        System.out.println("Error assigning teacher to course: " + e.getMessage());
	        return false;
	    }
	}
	
	private int getTeacherId(String firstName, String lastName) {
	    String sql = "SELECT id FROM tb_user WHERE first_name = ? AND last_name = ?";
	    
	    try (PreparedStatement stmt = connect.prepareStatement(sql)) {
	        stmt.setString(1, firstName);
	        stmt.setString(2, lastName);
	        
	        ResultSet resultSet = stmt.executeQuery();
	        
	        if (resultSet.next()) {
	            return resultSet.getInt("id");  // Return teacher ID if found
	        } else {
	            return -1; // Return -1 if no teacher is found with the given names
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving teacher ID: " + e.getMessage());
	        return -1;  // Return -1 in case of error
	    }
	}

	// Unassigns a teacher from a course
	public boolean unassignTeacherFromCourse(Course course) {
	    // SQL to find the teacher's ID based on first name and last name
	    String findTeacherSql = "SELECT id FROM tb_user WHERE first_name = ? AND last_name = ?";
	    
	    // SQL to delete the assignment from the tb_teacher_courses table
	    String deleteAssignmentSql = "DELETE FROM tb_teacher_courses WHERE teacher_id = ? AND course_code = ?";
	    
	    try (
	        PreparedStatement findStmt = connect.prepareStatement(findTeacherSql);
	        PreparedStatement deleteStmt = connect.prepareStatement(deleteAssignmentSql)
	    ) {
	        // Set the parameters for the findStmt (to find teacher ID)
	        findStmt.setString(1, course.getfName());
	        findStmt.setString(2, course.getlName());
	        
	        ResultSet rs = findStmt.executeQuery();
	        
	        // Check if teacher was found
	        if (rs.next()) {
	            int teacherId = rs.getInt("id");  // Get the teacher ID from the result set
	            String courseCode = course.getCode(); // Get the course code from the course object
	            
	            // Set the parameters for the deleteStmt (to remove the teacher-course assignment)
	            deleteStmt.setInt(1, teacherId);
	            deleteStmt.setString(2, courseCode);
	            
	            // Execute the delete statement
	            int rowsDeleted = deleteStmt.executeUpdate();
	            
	            // Return true if at least one row was deleted, indicating success
	            return rowsDeleted > 0;
	        } else {
	            System.out.println("Teacher not found.");
	            return false;  // Teacher not found, so cannot unassign
	        }
	    } catch (SQLException e) {
	        System.out.println("Error unassigning teacher from course: " + e.getMessage());
	        return false;  // Error occurred while unassigning
	    }
	}
}
