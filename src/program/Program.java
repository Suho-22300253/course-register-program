package program;

import manager.ProfessorManager;
import manager.StudentManager;
import manager.SystemManager;
import users.Professor;
import users.Student;
import users.User;

import java.util.Scanner;

/**
 * Classes regarding account creation, login, and program termination
 */
public class Program {
    private Scanner keyboard;
    private SystemManager systemManager;
    private ProfessorManager professorManager;
    private StudentManager studentManager;

    //share basic classes(Scanner,SystemManager, ProfessorManager, StudentManager)
    public Program(Scanner sc, SystemManager sysmanage, ProfessorManager pm, StudentManager stumanage) {
        keyboard = sc;
        systemManager = sysmanage;
        professorManager = pm;
        studentManager = stumanage;
    }

    /**
     * Creates an object based on the type.
     */
    public void createAccountMenu() {
        System.out.println("\n===== Create Account =====");
        System.out.println("1. Student");
        System.out.println("2. Professor");
        System.out.print("Select user type: ");
        int type = keyboard.nextInt();
        keyboard.nextLine();

        System.out.print("Name: ");
        String name = keyboard.nextLine();

        System.out.print("Id: ");
        String id = keyboard.nextLine();

        System.out.print("Password: ");
        String password = keyboard.nextLine();

        if (type == 1) {
            System.out.print("Student Number: ");
            int studentNumber = keyboard.nextInt();
            keyboard.nextLine();
            // create student object and save in ArrayList
            systemManager.createStudentAccount(name, id, password, studentNumber);

        } else if (type == 2) {
            System.out.print("Professor Number: ");
            int professorNumber = keyboard.nextInt();
            keyboard.nextLine();
            // create professor object and save in ArrayList
            systemManager.createProfessorAccount(name, id, password, professorNumber);

        } else {
            System.out.println("Wrong user type.");
        }
    }

    /**
     * define current user and show different menu depends on type of object
     */
    public void loginMenu() {
        if (systemManager.getCurrentUser() != null) {
            System.out.println("A user is already logged in.");
            return;
        }
        System.out.println();
        System.out.println("===== Login =====");

        System.out.print("Id: ");
        String id = keyboard.nextLine();

        System.out.print("Password: ");
        String password = keyboard.nextLine();

        // check type of current user
        User user = systemManager.login(id, password);

        if (user == null) {
            return;
        }

        UserMenu userMenu = new UserMenu(keyboard, systemManager, professorManager, studentManager);

        if (user instanceof Professor) {
            userMenu.professorMenu();
        } else if (user instanceof Student) {
            userMenu.studentMenu();
        }
    }

    /**
     * Save progress made so far
     */
    public void exitProgram() {
        systemManager.saveAccounts();
        professorManager.saveCourses();
        System.out.println("Program terminated.");
    }
}