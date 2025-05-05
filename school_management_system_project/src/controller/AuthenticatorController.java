package controller;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import model.*;
import view.*;

public class AuthenticatorController {
	
	private static Student currentStudent;
	private static Teacher currentTeacher;
	
	private AuthenticatorModel model;
    private AuthenticatorView view;
    
    public AuthenticatorController(AuthenticatorView view, AuthenticatorModel model) {
        this.model = model;
        this.view = view;
        
        // Register event listeners
        loginListeners();
        forgotPasswordListeners();
    }
    
    // Listeners for login-related actions
    public void loginListeners() {
        view.getBtnLogin().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = view.getTxtEmail().getText();
                String password = new String(view.getTxtPassword().getPassword());
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in both email and password!");
                    return;
                }
                User user = model.authenticate(email, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(null, "Login successful. Welcome, " + user.getRole() + "!");
                    switch (user.getRole().toLowerCase()) {
                        case "admin":
                            AdminView aView = new AdminView(user);
                            AdminModel aModel = new AdminModel();
                            new AdminController(aView, aModel);
                            aView.setVisible(true);
                            break;
                        case "student":
                            if (user instanceof Student) {
                                currentStudent = (Student) user;
                            }
                            StudentView sView = new StudentView(user);
                            StudentModel sModel = new StudentModel();
                            new StudentController(sView, sModel);
                            sView.setVisible(true);
                            break;
                        case "teacher":
                            if (user instanceof Teacher) {
                                currentTeacher = (Teacher) user;
                            }
                            TeacherView tView = new TeacherView(user);
                            TeacherModel tModel = new TeacherModel();
                            new TeacherController(tView, tModel);
                            tView.setVisible(true);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Unknown role: " + user.getRole());
                            return; // Do not close the login view if unknown
                    }
                    view.dispose(); // Close login window after opening the new window
                } 
                else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.");
                }
            }
        });
    }
    
    // Listeners for forgot password-related actions
    public void forgotPasswordListeners() {
        view.getBtnForgot().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openPasswordResetDialog();
            }
        });
    }
    
    // Method to handle password reset functionality
    private void openPasswordResetDialog() {
        JDialog resetDialog = new JDialog(view, "Reset Password", true);
        resetDialog.setSize(400, 250);
        resetDialog.setLocationRelativeTo(view);
        resetDialog.setLayout(new BorderLayout(10, 10));
        
        // Create the form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        
        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField();
        
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField();
        
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(newPasswordLabel);
        formPanel.add(newPasswordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton resetButton = new JButton("Reset Password");
        JButton cancelButton = new JButton("Cancel");
        
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);
        
        resetDialog.add(formPanel, BorderLayout.CENTER);
        resetDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                
                // Validate inputs
                if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(resetDialog, 
                            "Please fill out all fields.",
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate email format
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(resetDialog, 
                            "Please enter a valid email address.",
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Check if email exists
                if (!model.emailExists(email)) {
                    JOptionPane.showMessageDialog(resetDialog, 
                            "Email not found in our system.",
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Check if passwords match
                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(resetDialog, 
                            "Passwords do not match.",
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
               
                
                // Update password
                boolean success = model.updatePassword(email, newPassword);
                
                if (success) {
                    JOptionPane.showMessageDialog(resetDialog, 
                            "Password reset successful! You can now log in with your new password.",
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    resetDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(resetDialog, 
                            "An error occurred while resetting your password. Please try again later.",
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetDialog.dispose();
            }
        });
        
        // Set default button and make dialog visible
        resetDialog.getRootPane().setDefaultButton(resetButton);
        resetDialog.setVisible(true);
    }
    
    /**
     * Basic email validation
     * @param email The email to validate
     * @return true if email format is valid
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

	/**
	 * @return the currentStudent
	 */
	public static Student getCurrentStudent() {
		return currentStudent;
	}
	
	/**
	 * @param currentStudent the currentStudent to set
	 */
	public static void setCurrentStudent(Student currentStudent) {
		AuthenticatorController.currentStudent = currentStudent;
	}
	
	/**
	 * @return the currentTeacher
	 */
	public static Teacher getCurrentTeacher() {
		return currentTeacher;
	}
	
	/**
	 * @param currentTeacher the currentTeacher to set
	 */
	public static void setCurrentTeacher(Teacher currentTeacher) {
		AuthenticatorController.currentTeacher = currentTeacher;
	}
}