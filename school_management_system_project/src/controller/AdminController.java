package controller;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import model.*;
import view.*;

public class AdminController {

	private AdminView view;
	private AdminModel model;
	
	public AdminController(AdminView view, AdminModel model) {
		this.view = view;
		this.model = model;
		
		loadStudentList();
		loadTeacherList();
		loadCourseList();
		loadAssignedCourseList();
		
		studentListeners();
		teacherListeners();
		courseListeners();
	}
		
	// Student Listener
	public void studentListeners() {
		//add button listener	
		view.getSBtnAdd().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Get the input values
		        String firstName = view.getTxtSFirstName().getText();
		        String lastName = view.getTxtSLastName().getText();
		        String email = view.getTxtSEmail().getText();
		        
		        boolean hasError = false;
		        
		        // Check if first name is filled
		        if (firstName.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter a first name!");
		            hasError = true;
		          
		        }
		        
		        // Check if last name is filled
		        if (lastName.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter a last name!");
		            hasError = true;
		        }
		        
		        // Check if email is filled
		        if (email.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter an email!");
		            hasError = true;
		        }
		        else if (!email.endsWith("@example.com")) {
		            JOptionPane.showMessageDialog(null, "Email must end with @example.com");
		            hasError = true;
		        }
		        
		        if (hasError) {
		        	return;
		        }

		        // Generate a new student with a random password
		        Student newStudent = new Student(firstName, lastName, email);  // The constructor will handle password generation and hashing
		        
		        // Attempt to add the new student
		        if (model.addStudent(newStudent)) {
		            JOptionPane.showMessageDialog(null, "Student added successfully.");
		            loadStudentList();  // Refresh the student list in the UI
		        } else {
		            JOptionPane.showMessageDialog(null, "An error occurred while adding the student. \nTry a different email.");
		        }
		    }
		});
		
		view.getSBtnRemove().addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = view.getStudentTable().getSelectedRow();

		        if (selectedRow != -1) {
		            // Get values from the selected row
		            int id = Integer.parseInt(view.getStudentTable().getValueAt(selectedRow, 0).toString());
		            String firstName = view.getStudentTable().getValueAt(selectedRow, 1).toString();
		            String lastName = view.getStudentTable().getValueAt(selectedRow, 2).toString();
		            String email = view.getStudentTable().getValueAt(selectedRow, 3).toString();
		            String password = view.getStudentTable().getValueAt(selectedRow, 4).toString();

		            // Construct Student object with ID
		            Student studentToRemove = new Student(id, firstName, lastName, email, password);

		            if (model.removeStudent(studentToRemove)) {
		                JOptionPane.showMessageDialog(null, "Student " + firstName + " removed successfully");
		                loadStudentList(); // Refresh the table
		            } else {
		                JOptionPane.showMessageDialog(null, "An error occurred while removing the student.");
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Please select a student to remove.");
		        }
		    }
		});
		
		
		// Update button
		view.getSBtnClear().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	// Retrieve text from first name, last name, email, and password
                String firstName = view.getTxtSFirstName().getText();
                String lastName = view.getTxtSLastName().getText();
                String email = view.getTxtSEmail().getText();
                
                // Get the selected student from the table
                Student selectedStudent = getSelectedStudent();
                
                if (selectedStudent != null) {
                    // Update the selected student with new values
                    selectedStudent.setfName(firstName);
                    selectedStudent.setlName(lastName);
                    selectedStudent.setEmail(email);
                    
                    // Attempt to update the student in the model
                    if (model.updateStudent(selectedStudent)) {
                        JOptionPane.showMessageDialog(null, "Student updated successfully");
                        loadStudentList();  // Refresh the student list in the table
                    } else {
                        JOptionPane.showMessageDialog(null, "Error updating student.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No student selected for update.");
                }
            }
        });
		
		view.getStudentTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        // Check if a row is selected
		        int row = view.getStudentTable().getSelectedRow();
		        if (row != -1) {
		            // If selected, populate the fields with data from the table
		            view.getTxtSFirstName().setText(view.getStudentTableModel().getValueAt(row, 1).toString());
		            view.getTxtSLastName().setText(view.getStudentTableModel().getValueAt(row, 2).toString());
		            view.getTxtSEmail().setText(view.getStudentTableModel().getValueAt(row, 3).toString());
		        }
		    }
		});
		
	}
	
	
	// Teacher Listeners
	public void teacherListeners() {
		//add button listener	
		view.getTBtnAdd().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Get the input values
		        String firstName = view.getTxtTFirstName().getText();
		        String lastName = view.getTxtTLastName().getText();
		        String email = view.getTxtTEmail().getText();
		        
		        boolean hasError = false;
		        
		        // Check if first name is filled
		        if (firstName.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter a first name!");
		            hasError = true;
		        }
		        
		        // Check if last name is filled
		        if (lastName.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter a last name!");
		            hasError = true;
		        }
		        
		        // Check if email is filled
		        if (email.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter an email!");
		            hasError = true;
		        }
		        else if (!email.endsWith("@example.com")) {
		            JOptionPane.showMessageDialog(null, "Email must end with @example.com");
		            hasError = true;
		        }
		        
		        if (hasError) {
		        	return;
		        }

		        // Generate a new student with a random password
		        Teacher newTeacher = new Teacher(firstName, lastName, email);  // The constructor will handle password generation and hashing
		        
		        // Attempt to add the new student
		        if (model.addTeacher(newTeacher)) {
		            JOptionPane.showMessageDialog(null, "Teacher added successfully.");
		            loadTeacherList();  // Refresh the student list in the UI
		            loadAssignedCourseList();
		        } else {
		            JOptionPane.showMessageDialog(null, "An error occurred while adding the teacher. \n Try a different email.");
		        }
		    }
		});
		
		view.getTBtnRemove().addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = view.getTeacherTable().getSelectedRow();

		        if (selectedRow != -1) {
		            // Get values from the selected row
		            int id = Integer.parseInt(view.getTeacherTable().getValueAt(selectedRow, 0).toString());
		            String firstName = view.getTeacherTable().getValueAt(selectedRow, 1).toString();
		            String lastName = view.getTeacherTable().getValueAt(selectedRow, 2).toString();
		            String email = view.getTeacherTable().getValueAt(selectedRow, 3).toString();
		            String password = view.getTeacherTable().getValueAt(selectedRow, 4).toString();

		            // Construct Student object with ID
		            Teacher teacherToRemove = new Teacher(id, firstName, lastName, email, password);

		            if (model.removeTeacher(teacherToRemove)) {
		                JOptionPane.showMessageDialog(null, "Teacher " + firstName + " removed successfully");
		                loadTeacherList(); // Refresh the table
		                loadAssignedCourseList();
		            } else {
		                JOptionPane.showMessageDialog(null, "An error occurred while removing the teacher.");
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Please select a teacher to remove.");
		        }
		    }
		});
		
		
		// Update button
		view.getTBtnClear().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	// Retrieve text from first name, last name, email, and password
                String firstName = view.getTxtTFirstName().getText();
                String lastName = view.getTxtTLastName().getText();
                String email = view.getTxtTEmail().getText();
                
                // Get the selected student from the table
                Teacher selectedTeacher = getSelectedTeacher();
                
                if (selectedTeacher != null) {
                    // Update the selected student with new values
                    selectedTeacher.setfName(firstName);
                    selectedTeacher.setlName(lastName);
                    selectedTeacher.setEmail(email);
                    
                    // Attempt to update the student in the model
                    if (model.updateTeacher(selectedTeacher)) {
                        JOptionPane.showMessageDialog(null, "Teacher updated successfully");
                        loadTeacherList();  // Refresh the student list in the table
                        loadAssignedCourseList();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error updating teacher.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No teacher selected for update.");
                }
            }
        });
		
		view.getTeacherTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        // Check if a row is selected
		        int row = view.getTeacherTable().getSelectedRow();
		        if (row != -1) {
		            // If selected, populate the fields with data from the table
		            view.getTxtTFirstName().setText(view.getTeacherTableModel().getValueAt(row, 1).toString());
		            view.getTxtTLastName().setText(view.getTeacherTableModel().getValueAt(row, 2).toString());
		            view.getTxtTEmail().setText(view.getTeacherTableModel().getValueAt(row, 3).toString());
		        }
		    }
		});
		
	}
	
	// Course Listeners
	public void courseListeners() {
		//add button listener	
		view.getCBtnAdd().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Get the input values
		        String code = view.getTxtCode().getText();
		        String name = view.getTxtName().getText();
		        String description = view.getTxtDescription().getText();
		        String capacityStr = view.getTxtCapacity().getText();
		        String status = view.getTxtStatus().getText();
		        
		        // Check if code is filled
		        if (code.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter a code!");
		            return;
		        }
		        
		        // Check if name is filled
		        if (name.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter a code name!");
		            return;
		        }
		        
		        // Check if email is filled
		        if (description.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter a description!");
		            return;
		        }
		        
		        if (capacityStr.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter a capacity!");
		            return;
		        }
		        
		        int capacity;
	            try {
	                capacity = Integer.parseInt(capacityStr); 
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Capacity must be a valid number.");
	                return;
	            }
		        
		        if (status.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter a status!");
		            return;
		        }

		        // Generate a new student with a random password
		        Course newCourse = new Course(code, name, description, capacity, status);  // The constructor will handle password generation and hashing
		        
		        // Attempt to add the new student
		        if (model.addCourse(newCourse)) {
		            JOptionPane.showMessageDialog(null, "Course added successfully.");
		            loadCourseList();  // Refresh the student list in the UI
		            loadAssignedCourseList();
		        } else {
		            JOptionPane.showMessageDialog(null, "An error occurred while adding the course. Try a different code.");
		        }
		    }
		});
		
		view.getCBtnRemove().addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = view.getCourseTable().getSelectedRow();

		        if (selectedRow != -1) {
		            // Get values from the selected row
		            
		            String code = view.getCourseTable().getValueAt(selectedRow, 0).toString();
		            String name = view.getCourseTable().getValueAt(selectedRow, 1).toString();
		            String description = view.getCourseTable().getValueAt(selectedRow, 2).toString();
		            int capacity = Integer.parseInt(view.getCourseTable().getValueAt(selectedRow, 3).toString());
		            String status = view.getCourseTable().getValueAt(selectedRow, 4).toString();

		            // Construct Course object with ID
		            Course courseToRemove = new Course(code, name, description, capacity, status);

		            if (model.removeCourse(courseToRemove)) {
		                JOptionPane.showMessageDialog(null, "Course " + code + " removed successfully");
		                loadCourseList(); // Refresh the table
		            } else {
		                JOptionPane.showMessageDialog(null, "An error occurred while removing the course.");
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Please select a course to remove.");
		        }
		    }
		});
		
		
		// Update button
		view.getCBtnClear().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	// Retrieve text from first name, last name, email, and password
            	String code = view.getTxtCode().getText();
		        String name = view.getTxtName().getText();
		        String description = view.getTxtDescription().getText();
		        String capacityStr = view.getTxtCapacity().getText();
		        String status = view.getTxtStatus().getText();
                
                // Get the selected course from the table
                Course selectedCourse = getSelectedCourse();
                
                int capacity;
	            try {
	                capacity = Integer.parseInt(capacityStr); 
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Capacity must be a valid number.");
	                return;
	            }
                
                if (selectedCourse != null) {
                    // Update the selected student with new values
                    selectedCourse.setCode(code);
                    selectedCourse.setName(name);
                    selectedCourse.setDescription(description);
                    selectedCourse.setMax_capacity(capacity);
                    selectedCourse.setStatus(status);
                    
                    // Attempt to update the student in the model
                    if (model.updateCourse(selectedCourse)) {
                        JOptionPane.showMessageDialog(null, "Course updated successfully");
                        loadCourseList();  // Refresh the student list in the table
                    } else {
                        JOptionPane.showMessageDialog(null, "Error updating course.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No course selected for update.");
                }
            }
        });
		
		view.getBtnAssignTeacher().addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		    	// Get course code and teacher from the selected row
		        String courseCode = view.getTxtAssignCode().getText();
		        String firstName = view.getTxtTeacherFirstName().getText();
		        String lastName = view.getTxtTeacherLastName().getText();
		        
		        if (courseCode.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a course!");
				}
				//checks if last name is filled
				if (firstName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a first name!");
				}
				//checks if phone number is filled
				if (lastName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a last name!");
				}
				else {
					//add a new contact
					Course newCourse = new Course(courseCode, firstName, lastName);
					if(model.assignTeacherToCourse(newCourse)) {
						JOptionPane.showMessageDialog(null, "Teacher assigned");
						loadAssignedCourseList();
					}
					else {
						JOptionPane.showMessageDialog(null, "Error. Try a valid teacher.");
					}
					
				}
		        
		    }
		});

		// Unassign Teacher Button
		view.getBtnUnassignTeacher().addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		    	int selectedRow = view.getAssignedCourseTable().getSelectedRow();

		        if (selectedRow != -1) {
		            // Get values from the selected row
		            String code = view.getAssignedCourseTable().getValueAt(selectedRow, 0).toString();
		            String firstName = view.getAssignedCourseTable().getValueAt(selectedRow, 1).toString();
		            String lastName = view.getAssignedCourseTable().getValueAt(selectedRow, 2).toString();

		            // Construct Student object with ID
		            Course courseToRemove = new Course(code, firstName, lastName);

		            if (model.unassignTeacherFromCourse(courseToRemove)) {
		                JOptionPane.showMessageDialog(null, "Teacher " + firstName + " removed successfully");
		                loadStudentList(); // Refresh the table
		                loadAssignedCourseList();
		            } else {
		                JOptionPane.showMessageDialog(null, "An error occurred while removing the teacher.");
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Please select a teacher to remove.");
		        }
		    }
		});
		
		view.getCourseTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        // Check if a row is selected
		        int row = view.getCourseTable().getSelectedRow();
		        if (row != -1) {
		            // If selected, populate the fields with data from the table
		            view.getTxtCode().setText(view.getCourseTableModel().getValueAt(row, 0).toString());
		            view.getTxtName().setText(view.getCourseTableModel().getValueAt(row, 1).toString());
		            view.getTxtDescription().setText(view.getCourseTableModel().getValueAt(row, 2).toString());
		            view.getTxtCapacity().setText(view.getCourseTableModel().getValueAt(row, 3).toString());
		            view.getTxtStatus().setText(view.getCourseTableModel().getValueAt(row, 4).toString());
		        }
		    }
		});
		
		view.getAssignedCourseTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        // Check if a row is selected
		        int row = view.getAssignedCourseTable().getSelectedRow();
		        if (row != -1) {
		            // If selected, populate the fields with data from the table
		            view.getTxtAssignCode().setText(view.getAssignedCourseTableModel().getValueAt(row, 0).toString());
		            view.getTxtTeacherFirstName().setText(view.getAssignedCourseTableModel().getValueAt(row, 1).toString());
		            view.getTxtTeacherLastName().setText(view.getAssignedCourseTableModel().getValueAt(row, 2).toString());
		        }
		    }
		});
		
	}
	
	private Student getSelectedStudent() {
		Student selectedStudent = null;

	    // Get the selected row index from the JTable
	    int row = view.getStudentTable().getSelectedRow();

	    if (row != -1) {
	        // Get student details from the selected row in the table
	        int id = Integer.parseInt(view.getStudentTable().getValueAt(row, 0).toString()); // ID is in column 0
	        String firstName = view.getStudentTable().getValueAt(row, 1).toString(); // First name is in column 1
	        String lastName = view.getStudentTable().getValueAt(row, 2).toString(); // Last name is in column 2
	        String email = view.getStudentTable().getValueAt(row, 3).toString(); // Email is in column 3
	        String password = view.getStudentTable().getValueAt(row, 4).toString(); // Password is in column 4

	        selectedStudent = new Student(id, firstName, lastName, email, password);
	    }

	    return selectedStudent;
	}
	
	private Teacher getSelectedTeacher() {
		Teacher selectedTeacher = null;

	    // Get the selected row index from the JTable
	    int row = view.getTeacherTable().getSelectedRow();

	    if (row != -1) {
	        // Get student details from the selected row in the table
	        int id = Integer.parseInt(view.getTeacherTable().getValueAt(row, 0).toString()); // ID is in column 0
	        String firstName = view.getTeacherTable().getValueAt(row, 1).toString(); // First name is in column 1
	        String lastName = view.getTeacherTable().getValueAt(row, 2).toString(); // Last name is in column 2
	        String email = view.getTeacherTable().getValueAt(row, 3).toString(); // Email is in column 3
	        String password = view.getTeacherTable().getValueAt(row, 4).toString(); // Password is in column 4

	        selectedTeacher = new Teacher(id, firstName, lastName, email, password);
	    }

	    return selectedTeacher;
	}
	
	private Course getSelectedCourse() {
		Course selectedCourse = null;

	    // Get the selected row index from the JTable
	    int row = view.getCourseTable().getSelectedRow();

	    if (row != -1) {
	        // Get student details from the selected row in the table
	        String code = view.getCourseTable().getValueAt(row, 0).toString();
	        String name = view.getCourseTable().getValueAt(row, 1).toString(); // First name is in column 1
	        String description = view.getCourseTable().getValueAt(row, 2).toString(); // Last name is in column 2
	        int capacity = Integer.parseInt(view.getCourseTable().getValueAt(row, 3).toString());
	        String status = view.getCourseTable().getValueAt(row, 4).toString(); // Password is in column 4

	        selectedCourse = new Course(code, name, description, capacity, status);
	    }

	    return selectedCourse;
	}
	
	private Course getSelectedAssignedCourse() {
		Course selectedAssignedCourse = null;
		
		int row = view.getAssignedCourseTable().getSelectedRow();
		
		if (row != -1) {
			
			String code = view.getAssignedCourseTable().getValueAt(row, 0).toString();
	        String fName = view.getAssignedCourseTable().getValueAt(row, 1).toString();
	        String lName = view.getAssignedCourseTable().getValueAt(row, 1).toString();
	        
	        selectedAssignedCourse = new Course(code, fName, lName);
		}
		return selectedAssignedCourse;
	}
	
	// Loads the current list of items from the model into the table
	private void loadStudentList() {
	    ArrayList<Student> allStudents = model.getStudentList();
	    DefaultTableModel tableModel = view.getStudentTableModel();
	    tableModel.setRowCount(0); // Clear existing rows

	    for (Student s : allStudents) {
	        tableModel.addRow(new Object[] { 
	        	s.getId(), s.getfName(), s.getlName(), s.getEmail(), s.getPassword()
	        });
	    }
	}
	
	// Loads the current list of items from the model into the table
	private void loadTeacherList() {
	    ArrayList<Teacher> allTeachers = model.getTeacherList();
	    DefaultTableModel tableModel = view.getTeacherTableModel();
	    tableModel.setRowCount(0); // Clear existing rows

	    for (Teacher t : allTeachers) {
	        tableModel.addRow(new Object[] { 
	        	t.getId(), t.getfName(), t.getlName(), t.getEmail(), t.getPassword()
	        });
	    }
	}
	
	// Loads the current list of items from the model into the table
	private void loadCourseList() {
	    ArrayList<Course> allCourses = model.getCourseList();
	    DefaultTableModel tableModel = view.getCourseTableModel();
	    tableModel.setRowCount(0); // Clear existing rows

	    for (Course c : allCourses) {
	        tableModel.addRow(new Object[] { 
	        	c.getCode(), c.getName(), c.getDescription(), c.getMax_capacity(), c.getStatus()
	        });
	    }
	}
	
	private void loadAssignedCourseList() {
		ArrayList<Course> allAssignedCourses = model.getAssignedCourseList();
	    DefaultTableModel tableModel = view.getAssignedCourseTableModel();
	    tableModel.setRowCount(0); // Clear existing rows

	    for (Course c : allAssignedCourses) {
	        tableModel.addRow(new Object[] { 
	        	c.getCode(), c.getfName(), c.getlName()
	        });
	    }
	}
	
}
