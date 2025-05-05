package model;

import java.security.*;

public class Student extends User {
	
	// Constructor for adding a new student with a generated and hashed password
    public Student(String firstName, String lastName, String email) {
        super(-1, firstName, lastName, email, hashPassword(generateRandomPassword(12)), "student");
    }

    // Constructor for an existing student with an ID and hashed password
    public Student(int id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password, "student");
    }
    
    public Student(int id, String email) {
    	super(id, email, "student");
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
		return getfName() + " " + getlName() + "--" + getEmail() + " " + getlName() + getPassword();
	}
}