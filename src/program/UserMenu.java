package program;

import manager.ProfessorManager;
import manager.StudentManager;
import manager.SystemManager;

import java.util.Scanner;

/**
 * class for User menu
 * show next contents of login
 * show just basic options and handle detail in StudentManager and ProfessorManager
 */
public class UserMenu {
    private Scanner keyboard;
    private SystemManager systemManager;
    private ProfessorManager professorManager;
    private StudentManager studentManager;

    public UserMenu(Scanner sc, SystemManager sysmanage, ProfessorManager pm, StudentManager stumanage) {
        keyboard = sc;
        systemManager = sysmanage;
        professorManager = pm;
        studentManager = stumanage;
    }

    /**
     * menu for professor
     */
    public void professorMenu() {
        boolean run = true;

        while (run) {
            System.out.println();
            System.out.println("===== Professor Menu =====");
            System.out.println("1. Add Course");
            System.out.println("2. Show My Courses");
            System.out.println("3. Logout");
            System.out.print("Select menu: ");

            int option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1: // add new course
                    System.out.print("Course Name: ");
                    String courseName = keyboard.nextLine();

                    System.out.print("Credit: ");
                    int credit = keyboard.nextInt();
                    keyboard.nextLine();

                    professorManager.addCourse(courseName, credit);
                    break;

                case 2: // show all courses created by current professor object
                    professorManager.showMyCourses();
                    break;

                case 3: // logout
                    systemManager.logout();
                    run = false;
                    break;

                default:
                    System.out.println("Wrong menu number.");
            }
        }
    }

    /**
     * menu for student
     */
    public void studentMenu() {
        boolean run = true;

        while (run) {
            System.out.println();
            System.out.println("===== Student Menu =====");
            System.out.println("1. Register Course");
            System.out.println("2. Drop Course");
            System.out.println("3. Show My Courses");
            System.out.println("4. Check Credits");
            System.out.println("5. Logout");
            System.out.print("Select menu: ");

            int option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1: // register the course
                    System.out.println("\n[All Courses]");
                    professorManager.showAllCourses();

                    System.out.print("Course Name: ");
                    String registerCourseName = keyboard.nextLine();
                    studentManager.registerCourse(registerCourseName);
                    break;

                case 2: // drop coursee
                    System.out.println("\n[My Courses]");
                    studentManager.showMyCourses();

                    System.out.print("Course Name: ");
                    String dropCourseName = keyboard.nextLine();
                    studentManager.dropCourse(dropCourseName);
                    break;

                case 3: // print all course that current user register
                    studentManager.showMyCourses();
                    break;

                case 4: // get total credits
                    studentManager.checkCredits();
                    break;

                case 5: //Logout
                    systemManager.logout();
                    run = false;
                    break;

                default:
                    System.out.println("Wrong menu number.");
            }
        }
    }
}