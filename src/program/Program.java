package program;

import manager.ProfessorManager;
import manager.StudentManager;
import manager.SystemManager;
import users.Professor;
import users.Student;
import users.User;

import java.util.Scanner;

public class Program {
    private Scanner keyboard;
    private SystemManager systemManager;
    private ProfessorManager professorManager;
    private StudentManager studentManager;

    public Program(Scanner sc, SystemManager sysmanage, ProfessorManager pm, StudentManager stumanage) {
        keyboard = sc;
        systemManager = sysmanage;
        professorManager = pm;
        studentManager = stumanage;
    }

    public void start() {
        systemManager.loadAccounts();
        professorManager.loadCourses();

        boolean run = true;

        while (run) {
            printMainMenu();
            int option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1:
                    createAccountMenu();
                    break;
                case 2:
                    loginMenu();
                    break;
                case 3:
                    exitProgram();
                    run = false;
                    break;
                default:
                    System.out.println("Wrong menu number.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n===== Course Register Program =====");
        System.out.println("1. Create Account");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Select menu: ");
    }

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

            systemManager.createStudentAccount(name, id, password, studentNumber);

        } else if (type == 2) {
            System.out.print("Professor Number: ");
            int professorNumber = keyboard.nextInt();
            keyboard.nextLine();

            systemManager.createProfessorAccount(name, id, password, professorNumber);

        } else {
            System.out.println("Wrong user type.");
        }
    }

    public void loginMenu() {
        if (systemManager.getCurrentUser() != null) {
            System.out.println("A user is already logged in.");
            return;
        }

        System.out.println("\n===== Login =====");

        System.out.print("Id: ");
        String id = keyboard.nextLine();

        System.out.print("Password: ");
        String password = keyboard.nextLine();

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

    public void exitProgram() {
        systemManager.saveAccounts();
        professorManager.saveCourses();
        System.out.println("Program terminated.");
    }
}