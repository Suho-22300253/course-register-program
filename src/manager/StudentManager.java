package manager;

import course.Course;
import users.Student;
import users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentManager {
    private SystemManager systemManager;
    private ProfessorManager professorManager;

    public StudentManager(SystemManager systemManager, ProfessorManager professorManager) {
        this.systemManager = systemManager;
        this.professorManager = professorManager;
    }

    public void registerCourse(String courseName) {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Student)) {
            System.out.println("Only student can register course.");
            return;
        }

        Course course = professorManager.findCourseByName(courseName);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        int studentNumber = ((Student) currentUser).getStudentNumber();
        ArrayList<Integer> studentNumbers = readStudentNumbers(course.getCourseName());

        if (studentNumbers == null) {
            System.out.println("The course has not been opened.");
            return;
        }

        if (studentNumbers.contains(studentNumber)) {
            System.out.println("You already registered this course.");
            return;
        }

        studentNumbers.add(studentNumber);
        writeCourseFile(course, studentNumbers);

        System.out.println("Course registered successfully.");
    }

    public void dropCourse(String courseName) {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Student)) {
            System.out.println("Only student can drop course.");
            return;
        }

        Course course = professorManager.findCourseByName(courseName);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        int studentNumber = ((Student) currentUser).getStudentNumber();
        ArrayList<Integer> studentNumbers = readStudentNumbers(course.getCourseName());

        if (studentNumbers == null) {
            System.out.println("Course detail file not found.");
            return;
        }

        if (!studentNumbers.contains(studentNumber)) {
            System.out.println("You are not registered in this course.");
            return;
        }

        studentNumbers.remove(Integer.valueOf(studentNumber));
        writeCourseFile(course, studentNumbers);

        System.out.println("Course dropped successfully.");
    }

    public void showMyCourses() {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Student)) {
            System.out.println("Only student can check registered courses.");
            return;
        }

        int studentNumber = ((Student) currentUser).getStudentNumber();
        ArrayList<Course> courses = professorManager.getCourses();
        int count = 0;

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (isStudentRegistered(course.getCourseName(), studentNumber)) {
                System.out.println(course);
                count++;
            }
        }

        System.out.println("Total: " + count +" courses");
    }

    public void checkCredits() {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Student)) {
            System.out.println("Only student can check credits.");
            return;
        }

        int studentNumber = ((Student) currentUser).getStudentNumber();
        ArrayList<Course> courses = professorManager.getCourses();
        int totalCredits = 0;

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (isStudentRegistered(course.getCourseName(), studentNumber)) {
                totalCredits += course.getCredit();
            }
        }

        System.out.println("Total registered credits: " + totalCredits);
    }

    private ArrayList<Integer> readStudentNumbers(String courseName) {
        String fileName = courseName + ".txt";
        File file = new File(fileName);

        if (!file.exists()) {
            return null;
        }

        ArrayList<Integer> studentNumbers = new ArrayList<>();

        try (Scanner inputStream = new Scanner(file)) {
            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine().trim();

                if (!line.isEmpty()) {
                    studentNumbers.add(Integer.parseInt(line));
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        }

        return studentNumbers;
    }

    private void writeCourseFile(Course course, ArrayList<Integer> studentNumbers) {
        String fileName = course.getCourseName() + ".txt";

        try (PrintWriter outputStream = new PrintWriter(fileName)) {
            for (int i = 0; i < studentNumbers.size(); i++) {
                outputStream.println(studentNumbers.get(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error writing course file.");
        }
    }

    private boolean isStudentRegistered(String courseName, int studentNumber) {
        ArrayList<Integer> studentNumbers = readStudentNumbers(courseName);

        if (studentNumbers == null) {
            return false;
        }

        return studentNumbers.contains(studentNumber);
    }
}