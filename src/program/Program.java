package program;

import manager.*;
import users.*;

import java.util.Scanner;

public class Program {
    private Scanner keyboard;
    private SystemManager systemManager;
    private ProfessorManager professorManager;
    private StudentManager studentManager;

    public Program() {
        keyboard = new Scanner(System.in);

        systemManager = new SystemManager();
        professorManager = new ProfessorManager(systemManager);
        studentManager = new StudentManager(systemManager, professorManager);

        systemManager.loadAccounts();
        professorManager.loadCourses();
    }

    public void run() {
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
                    systemManager.logout();
                    break;
                case 4:
                    exitProgram();
                    run = false;
                    break;
                default:
                    System.out.println("Wrong menu number.");
            }
        }

        keyboard.close();
    }

    private void printMainMenu() {
        System.out.println("\n===== Course Register Program =====");
        System.out.println("1. Create Account");
        System.out.println("2. Login");
        System.out.println("3. Logout");
        System.out.println("4. Exit");
        System.out.print("Select menu: ");
    }

    private void createAccountMenu() {
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

    private void loginMenu() {
        System.out.println("\n===== Login =====");

        System.out.print("Id: ");
        String id = keyboard.nextLine();

        System.out.print("Password: ");
        String password = keyboard.nextLine();

        User user = systemManager.login(id, password);

        if (user == null) {
            return;
        }

        if (user instanceof Professor) {
            professorMenu();
        } else if (user instanceof Student) {
            studentMenu();
        }
    }

    private void professorMenu() {
        boolean run = true;

        while (run) {
            System.out.println("\n===== Professor Menu =====");
            System.out.println("1. Add Course");
            System.out.println("2. Show My Courses");
            System.out.println("3. Enter Grade");
            System.out.println("4. Logout");
            System.out.print("Select menu: ");

            int option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Course ID: ");
                    String courseId = keyboard.nextLine();

                    System.out.print("Course Name: ");
                    String courseName = keyboard.nextLine();

                    System.out.print("Credit: ");
                    int credit = keyboard.nextInt();
                    keyboard.nextLine();

                    professorManager.addCourse(courseId, courseName, credit);
                    break;

                case 2:
                    professorManager.showMyCourses();
                    break;

                case 3:
                    System.out.print("Course ID: ");
                    String gradeCourseId = keyboard.nextLine();

                    System.out.print("Student ID: ");
                    String studentId = keyboard.nextLine();

                    System.out.print("Grade: ");
                    String grade = keyboard.nextLine();

                    professorManager.enterGrade(gradeCourseId, studentId, grade);
                    break;

                case 4:
                    systemManager.logout();
                    run = false;
                    break;

                default:
                    System.out.println("Wrong menu number.");
            }
        }
    }

    private void studentMenu() {
        boolean run = true;

        while (run) {
            System.out.println("\n===== Student Menu =====");
            System.out.println("1. Register Course");
            System.out.println("2. Drop Course");
            System.out.println("3. Show My Courses");
            System.out.println("4. Check Credits");
            System.out.println("5. Check Grades");
            System.out.println("6. Logout");
            System.out.print("Select menu: ");

            int option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Course ID: ");
                    String registerId = keyboard.nextLine();
                    studentManager.registerCourse(registerId);
                    break;

                case 2:
                    System.out.print("Course ID: ");
                    String dropId = keyboard.nextLine();
                    studentManager.dropCourse(dropId);
                    break;

                case 3:
                    studentManager.showMyCourses();
                    break;

                case 4:
                    studentManager.checkCredits();
                    break;

                case 5:
                    studentManager.checkGrades();
                    break;

                case 6:
                    systemManager.logout();
                    run = false;
                    break;

                default:
                    System.out.println("Wrong menu number.");
            }
        }
    }

    private void exitProgram() {
        systemManager.saveAccounts();
        professorManager.saveCourses();
        System.out.println("Program terminated.");
    }
}
