package model;


public class User {
	
	// fields
	private int id;
	private String fName, lName, email, password, role;
	
	public User(int id, String fName, String lName, String email, String password, String role) {
		this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.role = role;
	}
	
	public User(int id,String email, String role) {
		this.id = id;
		this.email = email;
		this.role = role;
	}
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}



	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}



	/**
	 * @param fName the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}



	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}



	/**
	 * @param lName the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}



	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}



	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}



	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	public boolean isStudent() {
		return "student".equals(this.role);
	}
	
	public boolean isTeacher() {
		return "teacher".equals(this.role);
	}
	
	public boolean isAdmin() {
		return "admin".equals(this.role);
	}
	
}


