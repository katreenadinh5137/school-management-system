package my_util;

import java.security.*;

public class Security {

	public static String hashPassword(String password) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256"); // Create SHA-256 digest
	        byte[] hashedBytes = md.digest(password.getBytes());     // Hash the password
	        StringBuilder stringBuilder = new StringBuilder();
	        for (byte b : hashedBytes) {                             // Convert each byte to hex
	            stringBuilder.append(String.format("%02x", b));
	        }
	        return stringBuilder.toString();                         // Return final hex string
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public static boolean verifyPassword(String inputPassword, String storedHashedPassword) {
	    String hashedInput = hashPassword(inputPassword);
	    return hashedInput != null && hashedInput.equals(storedHashedPassword);
	}
}
