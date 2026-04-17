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
        program.start();

        keyboard.close();
    }
}