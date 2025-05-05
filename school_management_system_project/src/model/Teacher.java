package model;

import java.security.*;


public class Teacher extends User {
    private String department;

    public Teacher(int id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password, "teacher");
    }
    
 // Constructor for adding a new student with a generated and hashed password
    public Teacher(String firstName, String lastName, String email) {
        super(-1, firstName, lastName, email, hashPassword(generateRandomPassword(12)), "teacher");
    }
    
    public Teacher(int id, String email) {
    	super(id, email,"teacher");
    }
    

	// Generate a random password with a given length
    private static String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();  // Return the random password
    }

    // Hash the password using SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();  // Return the hashed password
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
	// Returns a string of the contact
	public String toString() {
		return getfName() + " " + getlName() + "--" + getEmail() + " " + getlName() + getPassword() + getDepartment();
	}
    
    /**
	 * @return the department
	 */
    public String getDepartment() {
        return department;
    }
    
    /**
	 * @param department the department to set 
	 */
    public void setDepartment(String department) {
        this.department = department;
    }
    
}