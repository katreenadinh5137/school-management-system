package app;

import controller.*;
import model.*;
import view.*;

public class main {

	public static void main(String[] args) {
		
		AuthenticatorView auView = new AuthenticatorView();
		AuthenticatorModel auModel = new AuthenticatorModel();
		
		new AuthenticatorController(auView, auModel);
		
		
//		// Create view and model
//		AdminView aView = new AdminView();
//		AdminModel aModel = new AdminModel();
//		new AdminController(aView, aModel);
//		aView.setVisible(true);
		
		
//		TeacherView tView = new TeacherView();
//		TeacherModel tModel = new TeacherModel();
//		new TeacherController(tView, tModel);
//		tView.setVisible(true);
		
		
//		StudentView sView = new StudentView();
//		StudentModel sModel = new StudentModel();
//		
//		new StudentController(sView, sModel);
//		
//		sView.setVisible(true);
		
		// Create a controller with view and model
		
		//Make the view visible
		
		
		

	}

}
