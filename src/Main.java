import manager.ProfessorManager;
import manager.StudentManager;
import manager.SystemManager;
import program.Program;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        SystemManager systemManager = new SystemManager();
        ProfessorManager professorManager = new ProfessorManager(systemManager);
        StudentManager studentManager = new StudentManager(systemManager, professorManager);

        Program program = new Program(keyboard, systemManager, professorManager, studentManager);
        systemManager.loadAccounts();
        professorManager.loadCourses();
        boolean run = true;
        while (run) {
            //options for first stage
            System.out.println("\n===== Course Register Program =====");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select menu: ");
            int option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1: // create account method
                    program.createAccountMenu();
                    break;
                case 2: // login class
                    program.loginMenu();
                    break;
                case 3: // terminate program
                    program.exitProgram();
                    run = false;
                    break;
                default:
                    System.out.println("Wrong menu number.");
            }
        }

        keyboard.close();
    }
}