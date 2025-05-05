package controller;

import view.*;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import model.*;

public class StudentController {

	private StudentView view;
	private StudentModel model;
	
	
	public StudentController(StudentView view, StudentModel model) {
		this.view = view;
		this.model = model;
		
		loadAvailCourseList();
		loadEnrolledCourseList();
		loadEmailList();
		
		enrollListeners();
		emailListeners();
		
	}
	
	public void enrollListeners() {
		view.getBtnEnroll().addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            // Get the selected course from the available courses table
	            int row = view.getAvailCourseTable().getSelectedRow();
	            if (row != -1) {
	                String courseCode = view.getAvailCourseTableModel().getValueAt(row, 0).toString();
	                String courseName = view.getAvailCourseTableModel().getValueAt(row, 1).toString();

	                // Get the current student (assumed you have a current student object)
	                Student currentStudent = AuthenticatorController.getCurrentStudent();
	                
	                Course selectedCourse = new Course(courseCode, courseName);
	                
	                // Call method to enroll the student into the course
	                if (model.enroll(currentStudent, selectedCourse)) {
	                	JOptionPane.showMessageDialog(null, "Course enrolled successfully.");
			            loadEnrolledCourseList();
	                }
	                else{
	                	JOptionPane.showMessageDialog(null, "An error occured when enrolling.");
	                };
	            } else {
	                JOptionPane.showMessageDialog(null, "Please select a course to enroll.");
	            }
	        }
	    });

	    // List selection listener for course table (no need to update fields)
	    view.getAvailCourseTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	        public void valueChanged(ListSelectionEvent e) {
	            // Check if a row is selected in the course table
	            int row = view.getAvailCourseTable().getSelectedRow();
	            if (row != -1) {
	                // Optionally, you can display a message with the course info if needed
	                String courseCode = view.getAvailCourseTableModel().getValueAt(row, 0).toString();
	                String courseName = view.getAvailCourseTableModel().getValueAt(row, 1).toString();
	                
	                // Optional: Show message indicating selected course (or just skip)
	                System.out.println("Selected Course: " + courseCode + " - " + courseName);
	            }
	        }
	    });
		
		
	}
	
	public void emailListeners() {
	    // Add listener for the Create Email button
	    view.getBtnCreateEmail().addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            openComposeEmailForm();
	        }
	    });
	    
	    // Add listener for email table selection
	    view.getEmailTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	        public void valueChanged(ListSelectionEvent e) {
	            if (!e.getValueIsAdjusting()) {
	                int selectedRow = view.getEmailTable().getSelectedRow();
	                if (selectedRow >= 0) {
	                    // Display email details when a row is selected
	                    displayEmailDetails(selectedRow);
	                }
	            }
	        }
	    });
	}

	private void displayEmailDetails(int row) {
	    // Get values from the selected row
	    DefaultTableModel model = view.getEmailTableModel();
	    
	    // Make sure we access columns using the correct indices
	    int senderIndex = 0;      // "Sender ID" is at index 0
	    int subjectIndex = 3;     // "Subject" is at index 3
	    int messageIndex = 4;     // "Message" is at index 4
	    int timestampIndex = 5;   // "Time Stamp" is at index 5
	    
	    String sender = model.getValueAt(row, senderIndex).toString();
	    String subject = model.getValueAt(row, subjectIndex).toString();
	    String message = model.getValueAt(row, messageIndex).toString();
	    String timestamp = model.getValueAt(row, timestampIndex).toString();
	    
	    // Format and display the details
	    String details = "From: " + sender + "\n";
	    details += "Subject: " + subject + "\n";
	    details += "Date: " + timestamp + "\n\n";
	    details += message;
	    
	    // Update the text area
	    JTextArea detailArea = view.getEmailDetailArea();
	    if (detailArea != null) {
	        detailArea.setText(details);
	        detailArea.setCaretPosition(0); // Scroll to top
	    }
	}

	private void openComposeEmailForm() {
	    // Get enrolled courses from the model
	    ArrayList<Course> enrolledCourses = model.getEnrolledCourseList();
	    
	    // Convert courses to a string array for the combobox
	    String[] courseArray = new String[enrolledCourses.size() + 1];
	    courseArray[0] = "Select Course";
	    for (int i = 0; i < enrolledCourses.size(); i++) {
	        courseArray[i + 1] = enrolledCourses.get(i).getCode();
	    }
	    
	    // Open the email form
	    view.openCreateEmailForm(courseArray);
	    
	    // Set up the listener for the send email button
	    setupSendEmailListener();
	}

	private void setupSendEmailListener() {
	    view.getBtnSendEmail().addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            // Get the current student
	            Student currentStudent = AuthenticatorController.getCurrentStudent();
	            if (currentStudent == null) {
	                JOptionPane.showMessageDialog(null, "Not currently logged in.");
	                return;
	            }

	            // Get fields from the view
	            String recipientEmail = view.getRecipientField().getText().trim();
	            String subject = view.getSubjectField().getText().trim();
	            String message = view.getComposeMessageArea().getText();
	            String selectedCourse = (String) view.getCourseComboBox().getSelectedItem();

	            // Validations
	            if (recipientEmail.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Please enter a recipient email!");
	                return;
	            }
	            if (subject.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Please enter a subject!");
	                return;
	            }
	            if (message.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Please enter a message!");
	                return;
	            }
	            if (selectedCourse.equals("Select Course")) {
	                JOptionPane.showMessageDialog(null, "Please select a course!");
	                return;
	            }

	            try {
	                // Get sender ID (current student)
	                int senderId = currentStudent.getId();
	                
	                // Find recipient ID by email
	                int recipientId = model.getStudentIdByEmail(recipientEmail);
	                
	                // If recipient not found, try finding a teacher with that email
	                if (recipientId == -1) {
	                    recipientId = model.getTeacherIdByEmail(recipientEmail);
	                }

	                if (recipientId == -1) {
	                    JOptionPane.showMessageDialog(null, "Recipient not found!");
	                    return;
	                }

	                // Send the email
	                boolean emailSent = model.sendEmail(senderId, recipientId, selectedCourse, subject, message);

	                if (emailSent) {
	                    JOptionPane.showMessageDialog(null, "Email sent successfully!");
	                    
	                    // Refresh the email list
	                    loadEmailList();
	                    
	                    // Close the compose window
	                    Window window = SwingUtilities.windowForComponent((Component)e.getSource());
	                    window.dispose();
	                } else {
	                    JOptionPane.showMessageDialog(null, "Failed to send email.");
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(null, "An error occurred while sending email.");
	            }
	        }
	    });
	}

	private void loadAvailCourseList() {
	    ArrayList<Course> allAvailableCourses = model.getAvaibleCourseList();
	    DefaultTableModel tableModel = view.getAvailCourseTableModel();
	    tableModel.setRowCount(0);
	    
	    for (Course c : allAvailableCourses) {
	        tableModel.addRow(new Object[] {
	            c.getCode(), c.getName(), c.getDescription(), c.getMax_capacity(), c.getStatus()
	        });
	    }
	}

	private void loadEnrolledCourseList() {
	    ArrayList<Course> allEnrolledCourses = model.getEnrolledCourseList();
	    DefaultTableModel tableModel = view.getEnrolledCourseTableModel();
	    tableModel.setRowCount(0);
	    
	    for (Course c : allEnrolledCourses) {
	        tableModel.addRow(new Object[] {
	            c.getCode(), c.getName()
	        });
	    }
	}

	private void loadEmailList() {
	    ArrayList<Email> allEmails = model.getEmailList();
	    DefaultTableModel tableModel = view.getEmailTableModel();
	    tableModel.setRowCount(0);
	    
	    for (Email e : allEmails) {
	        tableModel.addRow(new Object[] {
	            e.getSenderID(), e.getRecipientID(), e.getCode(), e.getSubject(), e.getMessage(), e.getTimestamp()
	        });
	    }
	}
	
}
