package view;

import model.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AdminView extends JFrame{
	
	// fields
	private JTabbedPane tabbedPane;
	
	private JTable studentTable, teacherTable, courseTable, assignedCourseTable;
	private DefaultTableModel studentTableModel, teacherTableModel, courseTableModel, assignedCourseTableModel;
	
	private JTextField txtSFirstName, txtSLastName, txtSEmail, txtSPassword;
	private JTextField txtTFirstName, txtTLastName, txtTEmail, txtTPassword;
	private JTextField txtCode, txtName, txtDescription, txtCapacity, txtStatus;
	
	private JButton sBtnAdd, sBtnClear, sBtnUpdate, sBtnRemove;
	private JButton tBtnAdd, tBtnClear, tBtnUpdate, tBtnRemove;
	private JButton cBtnAdd, cBtnClear, cBtnUpdate, cBtnRemove;
	
	private JList<Student> lstStudentList;
	private JList<Teacher> lstTeacherList;
	private JList<Course> lstCourseList;
	private JList<Course> lstAssignedCourseList;
	
	private DefaultListModel<Student> studentListModel;
	private DefaultListModel<Teacher> teacherListModel;
	private DefaultListModel<Course> courseListModel;
	private DefaultListModel<Course> assignedCourseListModel;
	private User loggedInUser;
	private JPanel studentPanel, teacherPanel, coursePanel;
	//private JPanel formPanel, listPanel;

	private JTextField txtAssignCode;
	private JTextField txtTeacherFirstName;

	private JTextField txtTeacherLastName;

	private JButton btnAssignTeacher;

	private JButton btnUnassignTeacher;

	private JPanel profilePanel;

	private JTextField idField;

	private JTextField firstNameField;

	private JTextField lastNameField;

	private JTextField emailField;

	private JPasswordField passwordField;

	private JLabel photoLabel;

	private JButton uploadButton;

	
	// Initialize GUI and adds panels
	public AdminView(User user){
		
		this.loggedInUser = user; 
		
		// == TABBED VIEW ===
		setTitle("Admin");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create tabs
        tabbedPane = new JTabbedPane();
        studentTab();
        courseTab();
        teacherTab();
        profileTab();

        add(tabbedPane);
        setVisible(true);
	}
	
	private void studentTab() {
		studentPanel = new JPanel(new BorderLayout());

	    // Table on left to display students
		String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Password"};
	    studentTableModel = new DefaultTableModel(columnNames, 0);
	    studentTable = new JTable(studentTableModel);
	    studentPanel.add(new JScrollPane(studentTable), BorderLayout.WEST);

	    // Form panel for input fields
	    JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
	    formPanel.setBorder(BorderFactory.createTitledBorder("Student Info"));

	    formPanel.add(new JLabel("First Name:"));
	    txtSFirstName = new JTextField();
	    formPanel.add(txtSFirstName);

	    formPanel.add(new JLabel("Last Name:"));
	    txtSLastName = new JTextField();
	    formPanel.add(txtSLastName);

	    formPanel.add(new JLabel("Email:"));
	    txtSEmail = new JTextField();
	    formPanel.add(txtSEmail);


	    // Button panel
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	    sBtnAdd = new JButton("Add");
	    sBtnRemove = new JButton("Remove");
	    sBtnClear = new JButton("Update");

	    buttonPanel.add(sBtnAdd);
	    buttonPanel.add(sBtnRemove);
	    buttonPanel.add(sBtnClear);

	    // Combine form and buttons
	    JPanel rightPanel = new JPanel(new BorderLayout());
	    rightPanel.add(formPanel, BorderLayout.CENTER);
	    rightPanel.add(buttonPanel, BorderLayout.SOUTH);

	    // Add both table and form+buttons to the student panel
	    studentPanel.add(rightPanel, BorderLayout.CENTER);

	    // Add the final tab
	    tabbedPane.addTab("Students", studentPanel);
    }
	
	private void courseTab() {
		// --- Main Course Panel ---
		coursePanel = new JPanel(new BorderLayout());

		// --- Top: Courses table ---
		String[] courseColumns = {"Code", "Name", "Description", "Max Capacity", "Status"};
		courseTableModel = new DefaultTableModel(courseColumns, 0);
		courseTable = new JTable(courseTableModel);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(BorderFactory.createTitledBorder("Courses"));
		topPanel.add(new JScrollPane(courseTable), BorderLayout.CENTER);

		// --- Input Form for Course Info ---
		JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));  // Grid for form fields
		formPanel.setBorder(BorderFactory.createTitledBorder("Course Info"));

		formPanel.add(new JLabel("Code:"));
		txtCode = new JTextField();
		formPanel.add(txtCode);

		formPanel.add(new JLabel("Name:"));
		txtName = new JTextField();
		formPanel.add(txtName);

		formPanel.add(new JLabel("Description:"));
		txtDescription = new JTextField();
		formPanel.add(txtDescription);

		formPanel.add(new JLabel("Max Capacity:"));
		txtCapacity = new JTextField();
		formPanel.add(txtCapacity);

		formPanel.add(new JLabel("Status:"));
		txtStatus = new JTextField();
		formPanel.add(txtStatus);

		// --- Buttons for Adding/Removing/Updating Courses ---
		JPanel courseButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cBtnAdd = new JButton("Add");
		cBtnRemove = new JButton("Remove");
		cBtnClear = new JButton("Update");

		courseButtonPanel.add(cBtnAdd);
		courseButtonPanel.add(cBtnRemove);
		courseButtonPanel.add(cBtnClear);

		// --- Right Side of Top Panel (Form + Buttons) ---
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(formPanel, BorderLayout.CENTER);
		rightPanel.add(courseButtonPanel, BorderLayout.SOUTH);

		// Add form to the right side of topPanel
		topPanel.add(rightPanel, BorderLayout.EAST);

		// --- Bottom: Assigned Teachers Section ---
		// Table for showing assigned teachers
		String[] assignedTeacherColumns = {"Course Code", "Teacher First Name", "Teacher Last Name"};
		assignedCourseTableModel = new DefaultTableModel(assignedTeacherColumns, 0);
		assignedCourseTable = new JTable(assignedCourseTableModel);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBorder(BorderFactory.createTitledBorder("Assigned Teachers"));
		bottomPanel.add(new JScrollPane(assignedCourseTable), BorderLayout.CENTER);

		// Form for assigning a teacher
		JPanel assignFormPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		assignFormPanel.setBorder(BorderFactory.createTitledBorder("Assign/Unassign Teacher"));
		
		assignFormPanel.add(new JLabel("Code: "));
		txtAssignCode = new JTextField();
		assignFormPanel.add(txtAssignCode);

		assignFormPanel.add(new JLabel("Teacher's First Name:"));
		txtTeacherFirstName = new JTextField();
		assignFormPanel.add(txtTeacherFirstName);

		assignFormPanel.add(new JLabel("Teacher's Last Name:"));
		txtTeacherLastName = new JTextField();
		assignFormPanel.add(txtTeacherLastName);

		// Buttons for Assign and Unassign
		JPanel assignButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnAssignTeacher = new JButton("Assign");
		btnUnassignTeacher = new JButton("Unassign");

		assignButtonPanel.add(btnAssignTeacher);
		assignButtonPanel.add(btnUnassignTeacher);

		// Combine assign form + buttons into a single panel
		JPanel bottomControlsPanel = new JPanel(new BorderLayout());
		bottomControlsPanel.add(assignFormPanel, BorderLayout.CENTER);
		bottomControlsPanel.add(assignButtonPanel, BorderLayout.SOUTH);

		// Add controls panel below assigned teachers table
		bottomPanel.add(bottomControlsPanel, BorderLayout.SOUTH);

		// --- Split Pane to separate Top and Bottom ---
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
		splitPane.setDividerLocation(350); // Adjust height as needed
		splitPane.setResizeWeight(0.5);  // 50% top, 50% bottom

		// --- Add Split Pane to Course Panel ---
		coursePanel.add(splitPane, BorderLayout.CENTER);

		// --- Add tab to TabbedPane ---
		tabbedPane.addTab("Courses", coursePanel);

		
    }

    private void teacherTab() {
    	
    	teacherPanel = new JPanel(new BorderLayout());

	    // Table on left to display students
		String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Password"};
	    teacherTableModel = new DefaultTableModel(columnNames, 0);
	    teacherTable = new JTable(teacherTableModel);
	    teacherPanel.add(new JScrollPane(teacherTable), BorderLayout.WEST);

	    // Form panel for input fields
	    JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
	    formPanel.setBorder(BorderFactory.createTitledBorder("Teacher Info"));

	    formPanel.add(new JLabel("First Name:"));
	    txtTFirstName = new JTextField();
	    formPanel.add(txtTFirstName);

	    formPanel.add(new JLabel("Last Name:"));
	    txtTLastName = new JTextField();
	    formPanel.add(txtTLastName);

	    formPanel.add(new JLabel("Email:"));
	    txtTEmail = new JTextField();
	    formPanel.add(txtTEmail);


	    // Button panel
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	    tBtnAdd = new JButton("Add");
	    tBtnRemove = new JButton("Remove");
	    tBtnClear = new JButton("Update");

	    buttonPanel.add(tBtnAdd);
	    buttonPanel.add(tBtnRemove);
	    buttonPanel.add(tBtnClear);

	    // Combine form and buttons
	    JPanel rightPanel = new JPanel(new BorderLayout());
	    rightPanel.add(formPanel, BorderLayout.CENTER);
	    rightPanel.add(buttonPanel, BorderLayout.SOUTH);

	    // Add both table and form+buttons to the student panel
	    teacherPanel.add(rightPanel, BorderLayout.CENTER);

	    // Add the final tab
	    tabbedPane.addTab("Teachers", teacherPanel);
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
	 * @return the txtFirstName
	 */
	public JTextField getTxtSFirstName() {
		return txtSFirstName;
	}

	/**
	 * @param txtFirstName the txtFirstName to set
	 */
	public void setTxtSFirstName(JTextField txtSFirstName) {
		this.txtSFirstName = txtSFirstName;
	}

	/**
	 * @return the txtLastName
	 */
	public JTextField getTxtSLastName() {
		return txtSLastName;
	}

	/**
	 * @param txtLastName the txtLastName to set
	 */
	public void setTxtSLastName(JTextField txtSLastName) {
		this.txtSLastName = txtSLastName;
	}

	/**
	 * @return the txtPhoneNumber
	 */
	public JTextField getTxtSEmail() {
		return txtSEmail;
	}

	/**
	 * @param txtPhoneNumber the txtPhoneNumber to set
	 */
	public void setTxtSEmail(JTextField txtSEmail) {
		this.txtSEmail = txtSEmail;
	}
	
	/**
	 * @return the txtPhoneNumber
	 */
	public JTextField getTxtSPassword() {
		return txtSPassword;
	}

	/**
	 * @param txtPhoneNumber the txtPhoneNumber to set
	 */
	public void setTxtSPassword(JTextField txtSPassword) {
		this.txtSPassword = txtSPassword;
	}
	
	/**
	 * @return the txtFirstName
	 */
	public JTextField getTxtTFirstName() {
		return txtTFirstName;
	}

	/**
	 * @param txtFirstName the txtFirstName to set
	 */
	public void setTxtTFirstName(JTextField txtTFirstName) {
		this.txtTFirstName = txtTFirstName;
	}

	/**
	 * @return the txtLastName
	 */
	public JTextField getTxtTLastName() {
		return txtTLastName;
	}

	/**
	 * @param txtLastName the txtLastName to set
	 */
	public void setTxtTLastName(JTextField txtTLastName) {
		this.txtTLastName = txtTLastName;
	}

	/**
	 * @return the txtPhoneNumber
	 */
	public JTextField getTxtTEmail() {
		return txtTEmail;
	}

	/**
	 * @param txtPhoneNumber the txtPhoneNumber to set
	 */
	public void setTxtTEmail(JTextField txtTEmail) {
		this.txtTEmail = txtTEmail;
	}
	
	/**
	 * @return the txtPhoneNumber
	 */
	public JTextField getTxtTPassword() {
		return txtTPassword;
	}

	/**
	 * @param txtPhoneNumber the txtPhoneNumber to set
	 */
	public void setTxtTPassword(JTextField txtTPassword) {
		this.txtTPassword = txtTPassword;
	}
	

	/**
	 * @return the txtCode
	 */
	public JTextField getTxtCode() {
		return txtCode;
	}

	/**
	 * @param txtCode the txtCode to set
	 */
	public void setTxtCode(JTextField txtCode) {
		this.txtCode = txtCode;
	}

	/**
	 * @return the txtName
	 */
	public JTextField getTxtName() {
		return txtName;
	}

	/**
	 * @param txtName the txtName to set
	 */
	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}

	/**
	 * @return the txtDescription
	 */
	public JTextField getTxtDescription() {
		return txtDescription;
	}

	/**
	 * @return the txtAssignCode
	 */
	public JTextField getTxtAssignCode() {
		return txtAssignCode;
	}

	/**
	 * @param txtAssignCode the txtAssignCode to set
	 */
	public void setTxtAssignCode(JTextField txtAssignCode) {
		this.txtAssignCode = txtAssignCode;
	}

	/**
	 * @return the txtTeacherFirstName
	 */
	public JTextField getTxtTeacherFirstName() {
		return txtTeacherFirstName;
	}

	/**
	 * @return the btnAssignTeacher
	 */
	public JButton getBtnAssignTeacher() {
		return btnAssignTeacher;
	}

	/**
	 * @param btnAssignTeacher the btnAssignTeacher to set
	 */
	public void setBtnAssignTeacher(JButton btnAssignTeacher) {
		this.btnAssignTeacher = btnAssignTeacher;
	}

	/**
	 * @return the btnUnassignTeacher
	 */
	public JButton getBtnUnassignTeacher() {
		return btnUnassignTeacher;
	}

	/**
	 * @param btnUnassignTeacher the btnUnassignTeacher to set
	 */
	public void setBtnUnassignTeacher(JButton btnUnassignTeacher) {
		this.btnUnassignTeacher = btnUnassignTeacher;
	}

	/**
	 * @param txtTeacherFirstName the txtTeacherFirstName to set
	 */
	public void setTxtTeacherFirstName(JTextField txtTeacherFirstName) {
		this.txtTeacherFirstName = txtTeacherFirstName;
	}

	/**
	 * @return the txtTeacherLastName
	 */
	public JTextField getTxtTeacherLastName() {
		return txtTeacherLastName;
	}

	/**
	 * @param txtTeacherLastName the txtTeacherLastName to set
	 */
	public void setTxtTeacherLastName(JTextField txtTeacherLastName) {
		this.txtTeacherLastName = txtTeacherLastName;
	}

	/**
	 * @param txtDescription the txtDescription to set
	 */
	public void setTxtDescription(JTextField txtDescription) {
		this.txtDescription = txtDescription;
	}

	/**
	 * @return the txtCapacity
	 */
	public JTextField getTxtCapacity() {
		return txtCapacity;
	}

	/**
	 * @param txtCapacity the txtCapacity to set
	 */
	public void setTxtCapacity(JTextField txtCapacity) {
		this.txtCapacity = txtCapacity;
	}

	/**
	 * @return the txtStatus
	 */
	public JTextField getTxtStatus() {
		return txtStatus;
	}

	/**
	 * @param txtStatus the txtStatus to set
	 */
	public void setTxtStatus(JTextField txtStatus) {
		this.txtStatus = txtStatus;
	}

	
	// ==== STUDENT METHODS ====
	
	/**
	 * @return the btnAdd
	 */
	public JButton getSBtnAdd() {
		return sBtnAdd;
	}

	/**
	 * @param btnAdd the btnAdd to set
	 */
	public void setSBtnAdd(JButton sBtnAdd) {
		this.sBtnAdd = sBtnAdd;
	}

	/**
	 * @return the btnClear
	 */
	public JButton getSBtnClear() {
		return sBtnClear;
	}

	/**
	 * @param btnClear the btnClear to set
	 */
	public void setSBtnClear(JButton sBtnClear) {
		this.sBtnClear = sBtnClear;
	}

	/**
	 * @return the btnUpdate
	 */
	public JButton getSBtnUpdate() {
		return sBtnUpdate;
	}

	/**
	 * @param btnUpdate the btnUpdate to set
	 */
	public void setSBtnUpdate(JButton sBtnUpdate) {
		this.sBtnUpdate = sBtnUpdate;
	}

	/**
	 * @return the btnRemove
	 */
	public JButton getSBtnRemove() {
		return sBtnRemove;
	}

	/**
	 * @param btnRemove the btnRemove to set
	 */
	public void setSBtnRemove(JButton sBtnRemove) {
		this.sBtnRemove = sBtnRemove;
	}

	/**
	 * @return the lstContactList
	 */
	public JList<Student> getLstStudentList() {
		return lstStudentList;
	}

	/**
	 * @param lstContactList the lstContactList to set
	 */
	public void setLstStudentList(JList<Student> lstStudentList) {
		this.lstStudentList = lstStudentList;
	}

	/**
	 * @return the contactListModel
	 */
	public DefaultListModel<Student> getStudentListModel() {
		return studentListModel;
	}

	/**
	 * @param contactListModel the contactListModel to set
	 */
	public void setStudentListModel(ArrayList<Student> student) {
		//this.contactListModel = contactListModel;
		
		studentListModel.clear();
		
		for(Student s: student) {
			studentListModel.addElement(s);
		}
	}

	public JTable getStudentTable() {
		return studentTable;
	}
	
	
	
	// ==== TEACHER METHODS ====
	
	/**
	 * @return the btnAdd
	 */
	public JButton getTBtnAdd() {
		return tBtnAdd;
	}

	/**
	 * @param btnAdd the btnAdd to set
	 */
	public void setTBtnAdd(JButton tBtnAdd) {
		this.tBtnAdd = tBtnAdd;
	}

	/**
	 * @return the btnClear
	 */
	public JButton getTBtnClear() {
		return tBtnClear;
	}

	/**
	 * @param btnClear the btnClear to set
	 */
	public void setTBtnClear(JButton tBtnClear) {
		this.tBtnClear = tBtnClear;
	}

	/**
	 * @return the btnUpdate
	 */
	public JButton getTBtnUpdate() {
		return tBtnUpdate;
	}

	/**
	 * @param btnUpdate the btnUpdate to set
	 */
	public void setTBtnUpdate(JButton tBtnUpdate) {
		this.tBtnUpdate = tBtnUpdate;
	}

	/**
	 * @return the btnRemove
	 */
	public JButton getTBtnRemove() {
		return tBtnRemove;
	}

	/**
	 * @param btnRemove the btnRemove to set
	 */
	public void setTBtnRemove(JButton tBtnRemove) {
		this.tBtnRemove = tBtnRemove;
	}
	
	/**
	 * @return the lstContactList
	 */
	public JList<Teacher> getLstTeacherList() {
		return lstTeacherList;
	}

	/**
	 * @param lstContactList the lstContactList to set
	 */
	public void setLstTeacherList(JList<Teacher> lstTeacherList) {
		this.lstTeacherList = lstTeacherList;
	}

	/**
	 * @return the contactListModel
	 */
	public DefaultListModel<Teacher> getTeacherListModel() {
		return teacherListModel;
	}

	/**
	 * @param contactListModel the contactListModel to set
	 */
	public void setTeacherListModel(ArrayList<Teacher> teacher) {
		//this.contactListModel = contactListModel;
		
		teacherListModel.clear();
		
		for(Teacher t: teacher) {
			teacherListModel.addElement(t);
		}
	}

	public JTable getTeacherTable() {
		return teacherTable;
	}
	
	// ==== COURSE METHODS ====
	
	/**
	 * @return the btnAdd
	 */
	public JButton getCBtnAdd() {
		return cBtnAdd;
	}

	/**
	 * @param btnAdd the btnAdd to set
	 */
	public void setCBtnAdd(JButton cBtnAdd) {
		this.cBtnAdd = cBtnAdd;
	}

	/**
	 * @return the btnClear
	 */
	public JButton getCBtnClear() {
		return cBtnClear;
	}

	/**
	 * @param btnClear the btnClear to set
	 */
	public void setCBtnClear(JButton cBtnClear) {
		this.cBtnClear = cBtnClear;
	}

	/**
	 * @return the btnUpdate
	 */
	public JButton getCBtnUpdate() {
		return cBtnUpdate;
	}

	/**
	 * @param btnUpdate the btnUpdate to set
	 */
	public void setCBtnUpdate(JButton cBtnUpdate) {
		this.cBtnUpdate = cBtnUpdate;
	}

	/**
	 * @return the btnRemove
	 */
	public JButton getCBtnRemove() {
		return cBtnRemove;
	}

	/**
	 * @param btnRemove the btnRemove to set
	 */
	public void setCBtnRemove(JButton cBtnRemove) {
		this.cBtnRemove = cBtnRemove;
	}
	
	/**
	 * @return the lstContactList
	 */
	public JList<Course> getLstCourseList() {
		return lstCourseList;
	}

	/**
	 * @param lstContactList the lstContactList to set
	 */
	public void setLstCourseList(JList<Course> lstCourseList) {
		this.lstCourseList = lstCourseList;
	}

	/**
	 * @return the contactListModel
	 */
	public DefaultListModel<Course> getCourseListModel() {
		return courseListModel;
	}
	
	/**
	 * @return the lstContactList
	 */
	public JList<Course> getLstAssignedCourseList() {
		return lstAssignedCourseList;
	}

	/**
	 * @param lstContactList the lstContactList to set
	 */
	public void setLstAssignedCourseList(JList<Course> lstAssignedCourseList) {
		this.lstAssignedCourseList = lstAssignedCourseList;
	}

	/**
	 * @return the contactListModel
	 */
	public DefaultListModel<Course> getAssignedCourseListModel() {
		return assignedCourseListModel;
	}

	/**
	 * @param contactListModel the contactListModel to set
	 */
	public void setCourseListModel(ArrayList<Course> course) {
		//this.contactListModel = contactListModel;
		
		courseListModel.clear();
		
		for(Course c: course) {
			courseListModel.addElement(c);
		}
	}
	
	public void setAssignedCourseListModel(ArrayList<Course> course) {
		//this.contactListModel = contactListModel;
		
		assignedCourseListModel.clear();
		
		for(Course c: course) {
			assignedCourseListModel.addElement(c);
		}
	}

	public JTable getCourseTable() {
		return courseTable;
	}
	
	public JTable getAssignedCourseTable() {
		return assignedCourseTable;
	}
	
	public DefaultTableModel getStudentTableModel() {
	    return studentTableModel;
	}
	
	public DefaultTableModel getTeacherTableModel() {
	    return teacherTableModel;
	}
	
	public DefaultTableModel getCourseTableModel() {
	    return courseTableModel;
	}
	
	public DefaultTableModel getAssignedCourseTableModel() {
	    return assignedCourseTableModel;
	}

	public JPanel getProfilePanel() {
        return profilePanel;
    }

    // Set user's information (for example, after login)
    public void setUserInfo(String userId, String firstName, String lastName, String email, String password) {
        idField.setText(userId);
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        emailField.setText(email);
        passwordField.setText(password);  // Set password (this could be hashed or not displayed in real apps)
    }
	
	
}
