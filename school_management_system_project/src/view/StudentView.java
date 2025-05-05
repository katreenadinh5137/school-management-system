package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;

import model.User;

public class StudentView extends JFrame{
	
	// fields
	private JTabbedPane tabbedPane;
	
	private JButton btnEnroll, btnSendEmail, btnComposeNewEmail, btnCreateEmail;
	
	private JPanel mainPanel, emailPanel, composeEmailPanel; 
	
	private JTable availCourseTable, enrolledCourseTable, emailTable;
	private DefaultTableModel availCourseTableModel, enrolledCourseTableModel, emailTableModel;
	
	private JTextArea emailDetailArea, composeMessageArea;
	
	private JTextField subjectField, recipientField, senderField;

	private JComboBox<String> courseComboBox;

	private JPanel profilePanel;

	private JTextField idField;

	private JTextField firstNameField;

	private JTextField lastNameField;

	private JTextField emailField;

	private JPasswordField passwordField;

	private JLabel photoLabel;

	private JButton uploadButton;
	private User loggedInUser;
	
	
	public StudentView(User user) {
		
		this.loggedInUser = user;
		setTitle("Student");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		tabbedPane = new JTabbedPane();
        mainTab();
        emailTab();
        profileTab();
        
        add(tabbedPane); // add tabbedPane to the frame
        
        setVisible(true);
	}
	
	private void mainTab() {
		mainPanel = new JPanel(new BorderLayout());
		
		// Top: Available Courses
		String[] enrollColumnNames = {"Course Code", "Name"};
		enrolledCourseTableModel = new DefaultTableModel(enrollColumnNames, 0);
		enrolledCourseTable = new JTable(enrolledCourseTableModel);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(BorderFactory.createTitledBorder("Enrolled Courses"));
		topPanel.add(new JScrollPane(enrolledCourseTable), BorderLayout.CENTER);

		// Bottom: Enrolled Courses 
		String[] columnNames = {"Code", "Name", "Description", "Max Capacity", "Status"};
		availCourseTableModel = new DefaultTableModel(columnNames, 0);
		availCourseTable = new JTable(availCourseTableModel);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBorder(BorderFactory.createTitledBorder("Available Courses"));
		bottomPanel.add(new JScrollPane(availCourseTable), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnEnroll = new JButton("Enroll");
		buttonPanel.add(btnEnroll);
		
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		// --- Split pane to show top and bottom ---
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
		splitPane.setDividerLocation(150); // Adjust height split as needed
		splitPane.setResizeWeight(0.5); // 50% for top, 50% for bottom

		mainPanel.add(splitPane, BorderLayout.CENTER);

		// --- Add tab ---
		tabbedPane.addTab("Courses", mainPanel);
	}
	
	public void emailTab() {
		emailPanel = new JPanel(new BorderLayout());

	    // Email Table (List of Emails)
	    String[] emailColumnNames = {"Sender ID", "Recipient ID", "Course Code", "Subject", "Message", "Time Stamp"};
	    emailTableModel = new DefaultTableModel(emailColumnNames, 0);
	    emailTable = new JTable(emailTableModel);

	    JPanel emailTablePanel = new JPanel(new BorderLayout());
	    emailTablePanel.setBorder(BorderFactory.createTitledBorder("Emails"));
	    emailTablePanel.add(new JScrollPane(emailTable), BorderLayout.CENTER);

	    // Email Detail Area (Right Panel)
	    JPanel emailDetailPanel = new JPanel(new BorderLayout());
	    emailDetailPanel.setBorder(BorderFactory.createTitledBorder("Email Details"));

	    emailDetailArea = new JTextArea();
	    emailDetailArea.setEditable(false);
	    emailDetailPanel.add(new JScrollPane(emailDetailArea), BorderLayout.CENTER);

	    // Add "Create Email" button
	    btnCreateEmail = new JButton("Create Email");
	    JPanel createEmailButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    createEmailButtonPanel.add(btnCreateEmail);

	    emailTablePanel.add(createEmailButtonPanel, BorderLayout.SOUTH);

	    // Add panels to the email tab
	    JSplitPane emailSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, emailTablePanel, emailDetailPanel);
	    emailSplitPane.setDividerLocation(300); // Adjust left-right panel division
	    emailSplitPane.setResizeWeight(0.3); // 30% for left panel (list of emails), 70% for right panel (email details)

	    emailPanel.add(emailSplitPane, BorderLayout.CENTER);

	    // Add email tab to the tabbedPane
	    tabbedPane.addTab("Email", emailPanel);
	}
	
	public void openCreateEmailForm(String[] courseArray) {
	    composeEmailPanel = new JPanel();
	    composeEmailPanel.setLayout(new BoxLayout(composeEmailPanel, BoxLayout.Y_AXIS));

	    // Recipient field (enter the recipient email)
	    recipientField = new JTextField();
	    recipientField.setMaximumSize(new Dimension(500, 30));
	    recipientField.setBorder(BorderFactory.createTitledBorder("Recipient"));
	    composeEmailPanel.add(recipientField);

	    // Subject field
	    subjectField = new JTextField();
	    subjectField.setMaximumSize(new Dimension(500, 30));
	    subjectField.setBorder(BorderFactory.createTitledBorder("Subject"));
	    composeEmailPanel.add(subjectField);

	    // Course selection combo box with passed course data
	    courseComboBox = new JComboBox<>(courseArray);
	    courseComboBox.setMaximumSize(new Dimension(500, 30));
	    composeEmailPanel.add(courseComboBox);

	    // Message body
	    composeMessageArea = new JTextArea();
	    composeMessageArea.setLineWrap(true);
	    composeMessageArea.setWrapStyleWord(true);
	    composeMessageArea.setBorder(BorderFactory.createTitledBorder("Message"));
	    composeMessageArea.setPreferredSize(new Dimension(500, 200));
	    composeEmailPanel.add(new JScrollPane(composeMessageArea));
	    
	    // Send button
	    btnSendEmail = new JButton("Send Email");
	    composeEmailPanel.add(btnSendEmail);

	    // Open the new window
	    JFrame composeFrame = new JFrame("Compose Email");
	    composeFrame.setSize(600, 400);
	    composeFrame.add(composeEmailPanel);
	    composeFrame.setLocationRelativeTo(this); // Center the compose window
	    composeFrame.setVisible(true);
	}
	
	private void profileTab() {
        // Initialize the panel
        profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());  // Main layout
        
        
        // Create Profile Info Panel
        JPanel profileInfoPanel = new JPanel(new GridLayout(6, 2));

        profileInfoPanel.add(new JLabel("User ID:"));
        idField = new JTextField();
        idField.setEditable(false);
        profileInfoPanel.add(idField);

        profileInfoPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        profileInfoPanel.add(firstNameField);

        profileInfoPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        profileInfoPanel.add(lastNameField);

        profileInfoPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        profileInfoPanel.add(emailField);

        profileInfoPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        profileInfoPanel.add(passwordField);
        
        idField.setText(String.valueOf(loggedInUser.getId()));
        firstNameField.setText(loggedInUser.getfName());
        lastNameField.setText(loggedInUser.getlName());
        emailField.setText(loggedInUser.getEmail());
        passwordField.setText(loggedInUser.getPassword());

        profilePanel.add(profileInfoPanel, BorderLayout.CENTER);

        photoLabel = new JLabel("No photo uploaded", JLabel.CENTER);
        profilePanel.add(photoLabel, BorderLayout.SOUTH);

        uploadButton = new JButton("Upload Photo");
        profilePanel.add(uploadButton, BorderLayout.NORTH);
        

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg"));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedImage img = ImageIO.read(selectedFile);
                        ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                        photoLabel.setIcon(icon);
                        photoLabel.setText("");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error loading image: " + ex.getMessage());
                    }
                }
            }
        });

        tabbedPane.addTab("Profile", profilePanel);
    }
        
	/**
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	/**
	 * @param tabbedPane the tabbedPane to set
	 */
	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
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
	
	/**
	 * @return the emailPanel
	 */
	public JPanel getEmailPanel() {
		return emailPanel;
	}
	
	/**
	 * @param emailPanel the emailPanel to set
	 */
	public void setEmailPanel(JPanel emailPanel) {
		this.emailPanel = emailPanel;
	}
	
	/**
	 * @return the availCourseTable
	 */
	public JTable getAvailCourseTable() {
		return availCourseTable;
	}
	
	/**
	 * @param availCourseTable the availCourseTable to set
	 */
	public void setAvailCourseTable(JTable availCourseTable) {
		this.availCourseTable = availCourseTable;
	}
	
	/**
	 * @return the studentCourseTable
	 */
	public JTable getEnrolledCourseTable() {
		return enrolledCourseTable;
	}
	
	/**
	 * @return the emailDetailArea
	 */
	public JTextArea getEmailDetailArea() {
		return emailDetailArea;
	}

	/**
	 * @param emailDetailArea the emailDetailArea to set
	 */
	public void setEmailDetailArea(JTextArea emailDetailArea) {
		this.emailDetailArea = emailDetailArea;
	}

	/**
	 * @param studentCourseTable the studentCourseTable to set
	 */
	public void setEnrolledCourseTable(JTable enrolledCourseTable) {
		this.enrolledCourseTable = enrolledCourseTable;
	}
	
	/**
	 * @return the availCourseTableModel
	 */
	public DefaultTableModel getAvailCourseTableModel() {
		return availCourseTableModel;
	}
	

	/**
	 * @param availCourseTableModel the availCourseTableModel to set
	 */
	public void setAvailCourseTableModel(DefaultTableModel availCourseTableModel) {
		this.availCourseTableModel = availCourseTableModel;
	}
	
	/**
	 * @return the studentCourseTableModel
	 */
	public DefaultTableModel getEnrolledCourseTableModel() {
		return enrolledCourseTableModel;
	}
	
	/**
	 * @param studentCourseTableModel the studentCourseTableModel to set
	 */
	public void setEnrolledCourseTableModel(DefaultTableModel enrolledCourseTableModel) {
		this.enrolledCourseTableModel = enrolledCourseTableModel;
	}

	/**
	 * @return the btnEnroll
	 */
	public JButton getBtnEnroll() {
		return btnEnroll;
	}

	/**
	 * @return the emailTable
	 */
	public JTable getEmailTable() {
		return emailTable;
	}

	/**
	 * @param emailTable the emailTable to set
	 */
	public void setEmailTable(JTable emailTable) {
		this.emailTable = emailTable;
	}

	/**
	 * @return the emailTableModel
	 */
	public DefaultTableModel getEmailTableModel() {
		return emailTableModel;
	}

	/**
	 * @param emailTableModel the emailTableModel to set
	 */
	public void setEmailTableModel(DefaultTableModel emailTableModel) {
		this.emailTableModel = emailTableModel;
	}

	/**
	 * @param btnEnroll the btnEnroll to set
	 */
	public void setBtnEnroll(JButton btnEnroll) {
		this.btnEnroll = btnEnroll;
	}

	/**
	 * @return the composeMessageArea
	 */
	public JTextArea getComposeMessageArea() {
		return composeMessageArea;
	}

	/**
	 * @param composeMessageArea the composeMessageArea to set
	 */
	public void setComposeMessageArea(JTextArea composeMessageArea) {
		this.composeMessageArea = composeMessageArea;
	}

	/**
	 * @return the senderField
	 */
	public JTextField getSenderField() {
		return senderField;
	}

	/**
	 * @param senderField the senderField to set
	 */
	public void setSenderField(JTextField senderField) {
		this.senderField = senderField;
	}

	/**
	 * @return the subjectField
	 */
	public JTextField getSubjectField() {
		return subjectField;
	}

	/**
	 * @return the courseComboBox
	 */
	public JComboBox<String> getCourseComboBox() {
		return courseComboBox;
	}

	/**
	 * @param courseComboBox the courseComboBox to set
	 */
	public void setCourseComboBox(JComboBox<String> courseComboBox) {
		this.courseComboBox = courseComboBox;
	}

	/**
	 * @param subjectField the subjectField to set
	 */
	public void setSubjectField(JTextField subjectField) {
		this.subjectField = subjectField;
	}

	/**
	 * @return the recipientField
	 */
	public JTextField getRecipientField() {
		return recipientField;
	}

	/**
	 * @param recipientField the recipientField to set
	 */
	public void setRecipientField(JTextField recipientField) {
		this.recipientField = recipientField;
	}

	// Set user's information (for example, after login)
    public void setUserInfo(String userId, String firstName, String lastName, String email, String password) {
        idField.setText(userId);
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        emailField.setText(email);
        passwordField.setText(password);  // Set password (this could be hashed or not displayed in real apps)
    }

	public JButton getBtnComposeNewEmail() {
		return btnComposeNewEmail;
	}

	public void setBtnComposeNewEmail(JButton btnComposeNewEmail) {
		this.btnComposeNewEmail = btnComposeNewEmail;
	}

	/**
	 * @param btnSendEmail the btnSendEmail to set
	 */
	public void setBtnSendEmail(JButton btnSendEmail) {
		this.btnSendEmail = btnSendEmail;
	}

	public JButton getBtnSendEmail() {
		return btnSendEmail;
	}
	
	public JButton getBtnCreateEmail() {
		return btnCreateEmail;
	}
}