package controller;

import view.*;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.*;

public class TeacherController {
    
    private TeacherView view;
    private TeacherModel model;
    
    public TeacherController(TeacherView view, TeacherModel model) {
        this.view = view;
        this.model = model;
        
        loadCourseStudentList();
        loadEmailList();
        
        emailListeners();
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
        // Get courses assigned to this teacher from the model
        Teacher currentTeacher = AuthenticatorController.getCurrentTeacher();
        ArrayList<Course> teacherCourses = model.getCourseList(currentTeacher);
        
        // Open the email form first
        view.openCreateEmailForm();
        
        // Convert courses to a string array for the combobox
        String[] courseArray = new String[teacherCourses.size() + 1];
        courseArray[0] = "Select Course";
        for (int i = 0; i < teacherCourses.size(); i++) {
            courseArray[i + 1] = teacherCourses.get(i).getCode();
        }
        
        // Update the course combo box with actual courses
        view.getCourseComboBox().setModel(new DefaultComboBoxModel<>(courseArray));
        
        // Set up the listener for the send email button
        setupSendEmailListener();
    }
    
    private void setupSendEmailListener() {
        view.getBtnSendEmail().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the current teacher
                Teacher currentTeacher = AuthenticatorController.getCurrentTeacher();
                if (currentTeacher == null) {
                    JOptionPane.showMessageDialog(null, "Not currently logged in.");
                    return;
                }

                // Get fields from the view
                String recipientEmail = view.getRecipientField().getText().trim();
                String subject = view.getSubjectField().getText().trim();
                String message = view.getComposeMessageArea().getText();
                Object selectedItem = view.getCourseComboBox().getSelectedItem();
                
                if (selectedItem == null) {
                    JOptionPane.showMessageDialog(null, "Please select a course!");
                    return;
                }
                
                String selectedCourse = selectedItem.toString();

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
                    // Get sender ID (current teacher)
                    int senderId = currentTeacher.getId();
                    
                    // Find recipient ID by email
                    int recipientId = model.getStudentIdByEmail(recipientEmail);
                    
                    // If recipient not found, try finding as a teacher (for teacher-to-teacher communication)
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
    
    private void loadCourseStudentList() {
        ArrayList<Course> allStudentCourses = model.getCourseStudentList();
        DefaultTableModel tableModel = view.getCourseStudentTableModel();
        tableModel.setRowCount(0);
        
        for (Course c : allStudentCourses) {
            tableModel.addRow(new Object[] {
                c.getCode(), c.getfName(), c.getlName()
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