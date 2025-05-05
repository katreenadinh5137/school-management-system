package model;

public class Authenticator {
	
	private static Student currentStudent;
    private static Teacher currentTeacher;
    private static Admin currentAdmin;

    // Setters for current user

    public static void setCurrentTeacher(Teacher teacher) {
        currentTeacher = teacher;
    }

    public static void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }

    // Getters for current user
    public static Student getCurrentStudent() {
        return currentStudent;
    }

    public static Teacher getCurrentTeacher() {
        return currentTeacher;
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

	public static void setCurrentStudent(Student student) {
		currentStudent = student;
		
	}
}
