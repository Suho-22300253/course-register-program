package manager;

import users.Student;
import users.Professor;
import users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SystemManager implements ISystemManager {
    private ArrayList<Student> students;
    private ArrayList<Professor> professors;
    private User currentUser;

    private static final String FILE_NAME = "accounts.txt";

    public SystemManager() {
        students = new ArrayList<>();
        professors = new ArrayList<>();
        currentUser = null;
    }

    /**
     * when program starts
     * load all account data from account.txt
     */
    public void loadAccounts() {
        File file = new File(FILE_NAME);
        // if account file doesn't exit
        if (!file.exists()) {
            System.out.println("No account file. Start with empty account list.");
            return;
        }

        Scanner inputStream = null;

        try {
            inputStream = new Scanner(file);

            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                //Divide the data based on the |
                String[] arr = line.split("\\|");

                if (arr.length != 5) {
                    continue;
                }

                String type = arr[0];
                int number = Integer.parseInt(arr[1]);
                String name = arr[2];
                String id = arr[3];
                String password = arr[4];

                //based on type create user account and save in ArrayList
                if (type.equals("STUDENT")) {
                    Student student = new Student(number, name, id, password);
                    students.add(student);
                } else if (type.equals("PROFESSOR")) {
                    Professor professor = new Professor(number, name, id, password);
                    professors.add(professor);
                }
            }

            System.out.println("Account data loaded.");

        } catch (FileNotFoundException e) {
            System.out.println("Error opening file.");
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

    /**
     * save all data about account no matter it changes or not
     */
    public void saveAccounts() {
        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(FILE_NAME);

            //save as type|(student/professor)number|id|password
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                outputStream.println("STUDENT|" + student.getStudentNumber() + "|" + student.getName() + "|" +student.getId() + "|" +student.getPassword());
            }

            for (int i = 0; i < professors.size(); i++) {
                Professor professor = professors.get(i);
                outputStream.println("PROFESSOR|" + professor.getProfessorNumber() + "|" + professor.getName() + "|" + professor.getId() + "|" + professor.getPassword());
            }

            System.out.println("Account data saved.");

        } catch (FileNotFoundException e) {
            System.out.println("Error saving file.");
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }

    /**
     * check id and student number if duplicated and then make object of student
     * @param name
     * @param id
     * @param password
     * @param studentNumber
     */
    @Override
    public void createStudentAccount(String name, String id, String password, int studentNumber) {
        if (isDuplicatedId(id)) {
            System.out.println("This id already exists.");
            return;
        }
        if(isDuplicatedNumber(studentNumber)){
            System.out.println("This studnet number is already exists.");
            return;
        }

        Student student = new Student(studentNumber, name, id, password);
        students.add(student);
        saveAccounts();
        System.out.println("Student account created successfully.");
    }

    /**
     * check id and professor number if duplicated and then make object of student
     * @param name
     * @param id
     * @param password
     * @param professorNumber
     */
    @Override
    public void createProfessorAccount(String name, String id, String password, int professorNumber) {
        if (isDuplicatedId(id)) {
            System.out.println("This id already exists.");
            return;
        }
        if(isDuplicatedNumber(professorNumber)){
            System.out.println("This professor number is already exists.");
            return;
        }

        Professor professor = new Professor(professorNumber, name, id, password);
        professors.add(professor);
        saveAccounts();
        System.out.println("Professor account created successfully.");
    }

    /**
     * if id and password is correct then return an object containing the relevant information
     * @param id
     * @param password
     * @return
     */
    @Override
    public User login(String id, String password) {
        //Student
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if (student.getId().equals(id) && student.getPassword().equals(password)) {
                currentUser = student;
                System.out.println(student.getName() + " logged in.");
                return currentUser;
            }
        }
        // Professor
        for (int i = 0; i < professors.size(); i++) {
            Professor professor = professors.get(i);

            if (professor.getId().equals(id) && professor.getPassword().equals(password)) {
                currentUser = professor;
                System.out.println(professor.getName() + " logged in.");
                return currentUser;
            }
        }

        System.out.println("Login failed.");
        return null;
    }

    /**
     * current object is null
     */
    @Override
    public void logout() {
        if (currentUser != null) {
            System.out.println(currentUser.getName() + " logged out.");
            currentUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    /**
     * compare input id and saved id
     * @param id
     * @return
     */
    private boolean isDuplicatedId(String id) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId().equals(id)) {
                return true;
            }
        }

        for (int i = 0; i < professors.size(); i++) {
            Professor professor = professors.get(i);
            if (professor.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    /**
     * check number is duplicated
     * @param number
     * @return
     */
    private boolean isDuplicatedNumber(int number) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getStudentNumber() == number) {
                return true;
            }
        }

        for (int i = 0; i < professors.size(); i++) {
            Professor professor = professors.get(i);
            if (professor.getProfessorNumber() == number) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}