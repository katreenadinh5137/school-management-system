package model;

import java.text.*;
import java.time.*;
import java.util.*;

public class Email {
	
	// fields
	private int id, senderID, recipientID;
	private String code, subject, message; 
	private String timestamp, status;
	/// private timestamp
	
	public Email (int id, int senderID, int recipientID, String code, String subject, String message, String timestamp, String status) {
		this.id = id;
		this.senderID = senderID;
		this.recipientID = recipientID;
		this.code = code;
		this.subject = subject;
		this.message = message;
		this.timestamp = timestamp;
		this.status = status;
	}
	public Email (int senderID, int recipientID, String code, String subject, String message, String timestamp, String status) {
		this.senderID = senderID;
		this.recipientID = recipientID;
		this.code = code;
		this.subject = subject;
		this.message = message;
		this.timestamp = timestamp;
		this.status = status;
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
	 * @return the senderID
	 */
	public int getSenderID() {
		return senderID;
	}
	/**
	 * @param senderID the senderID to set
	 */
	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}
	/**
	 * @return the recipientID
	 */
	public int getRecipientID() {
		return recipientID;
	}
	/**
	 * @param recipientID the recipientID to set
	 */
	public void setRecipientID(int recipientID) {
		this.recipientID = recipientID;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
