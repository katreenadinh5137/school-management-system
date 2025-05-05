package model;


public class Admin extends User {
	
	      
	public Admin(int id, String firstName, String lastName, String email, String password) {
	        super(id, firstName, lastName, email, password, "admin");
	 }
	 
	 
	public Admin(int id, String email) {
        super(id, email, "admin"); 
    }
	
	
}

