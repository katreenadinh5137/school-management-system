package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.*;

public class AuthenticatorView extends JFrame {
	
	private JTextField txtEmail;
	private JPasswordField txtPassword;
    private JButton btnLogin, btnForgot;
    private JPanel mainPanel;
    private AuthenticatorModel model;
    
    public AuthenticatorView() {
        setTitle("Login");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // <-- Important: use BorderLayout!

        // Initialize the model
        model = new AuthenticatorModel();
        
        buildMainPanel();

        add(mainPanel, BorderLayout.NORTH);

        // Build button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnLogin = new JButton("Login");
        btnForgot = new JButton("Forgot Password?");
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnForgot);

        add(buttonPanel, BorderLayout.SOUTH);
        
        btnForgot.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		openForgotPasswordWindow();
        	}
        });

        setVisible(true);
    }

    private void buildMainPanel() {
        mainPanel = new JPanel(new GridLayout(2, 2, 5, 5)); // 2 rows, 2 columns with padding

        mainPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField(20);
        mainPanel.add(txtEmail);

        mainPanel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField(20);
        mainPanel.add(txtPassword);
    }

    private void openForgotPasswordWindow() {
        JFrame forgotFrame = new JFrame("Reset Password");
        forgotFrame.setSize(400, 250);
        forgotFrame.setLocationRelativeTo(null);
        forgotFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        forgotFrame.setLayout(new BorderLayout(10, 10));

        // Panel for user input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JLabel emailLabel = new JLabel("Enter your email:");
        JTextField emailField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Enter new password:");
        JPasswordField passwordField = new JPasswordField(20);
        
        JLabel confirmPasswordLabel = new JLabel("Confirm new password:");
        JPasswordField confirmPasswordField = new JPasswordField(20);

        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(confirmPasswordLabel);
        inputPanel.add(confirmPasswordField);

        JButton submitButton = new JButton("Reset Password");

        // Wrap everything nicely in a container
        JPanel container = new JPanel(new BorderLayout(10, 10));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.add(inputPanel, BorderLayout.CENTER);
        
        forgotFrame.add(container, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        forgotFrame.add(buttonPanel, BorderLayout.SOUTH);

        forgotFrame.setVisible(true);

        // Handle submit button click
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredEmail = emailField.getText();
                String enteredPassword = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Validate all fields
                if (enteredEmail.isEmpty() || enteredPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(forgotFrame, "Please fill out all fields.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate email format
                if (!isValidEmailFormat(enteredEmail)) {
                    JOptionPane.showMessageDialog(forgotFrame, "Please enter a valid email address.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Check if email exists in database
                if (!model.emailExists(enteredEmail)) {
                    JOptionPane.showMessageDialog(forgotFrame, "Email not found in our records.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate password match
                if (!enteredPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(forgotFrame, "Passwords do not match.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate password strength
                if (enteredPassword.length() < 8) {
                    JOptionPane.showMessageDialog(forgotFrame, "Password must be at least 8 characters long.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Update password in database
                boolean success = model.updatePassword(enteredEmail, enteredPassword);
                
                if (success) {
                    JOptionPane.showMessageDialog(forgotFrame, "Password reset successful. You can now log in with your new password.", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    forgotFrame.dispose(); // Close window after submission
                } else {
                    JOptionPane.showMessageDialog(forgotFrame, "An error occurred while resetting your password. Please try again later.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    /**
     * Basic email validation
     * @param email The email address to validate
     * @return true if the email format is valid
     */
    private boolean isValidEmailFormat(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

	/**
	 * @return the txtEmail
	 */
	public JTextField getTxtEmail() {
		return txtEmail;
	}

	/**
	 * @param txtEmail the txtEmail to set
	 */
	public void setTxtEmail(JTextField txtEmail) {
		this.txtEmail = txtEmail;
	}

	/**
	 * @return the txtPassword
	 */
	public JPasswordField getTxtPassword() {
		return txtPassword;
	}

	/**
	 * @param txtPassword the txtPassword to set
	 */
	public void setTxtPassword(JPasswordField txtPassword) {
		this.txtPassword = txtPassword;
	}

	/**
	 * @return the btnLogin
	 */
	public JButton getBtnLogin() {
		return btnLogin;
	}

	/**
	 * @param btnLogin the btnLogin to set
	 */
	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	/**
	 * @return the btnForgot
	 */
	public JButton getBtnForgot() {
		return btnForgot;
	}

	/**
	 * @param btnForgot the btnForgot to set
	 */
	public void setBtnForgot(JButton btnForgot) {
		this.btnForgot = btnForgot;
	}

	/**
	 * @return the mainPanel
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * @param mainPanel the mainPanel to set
	 */
	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
}