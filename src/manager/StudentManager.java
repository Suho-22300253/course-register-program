package manager;

import course.Course;
import users.Student;
import users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Handle detail function of Student
 * about register and drop course and show my course nad credits method
 */
public class StudentManager {
    private SystemManager systemManager;
    private ProfessorManager professorManager;

    public StudentManager(SystemManager systemManager, ProfessorManager professorManager) {
        this.systemManager = systemManager;
        this.professorManager = professorManager;
    }

    /**
     * add studnet information in certain course file
     * @param courseName
     */
    public void registerCourse(String courseName) {
        User currentUser = systemManager.getCurrentUser();

        //check current user is Student object
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

        if (studentNumbers.contains(studentNumber)) {
            System.out.println("You already registered this course.");
            return;
        }

        studentNumbers.add(studentNumber);
        writeCourseFile(course, studentNumbers);

        System.out.println("Course registered successfully.");
    }

    /**
     * remove data from ArrayList
     * @param courseName
     */
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
        //get student number who register certain course and save in ArrayList
        ArrayList<Integer> studentNumbers = readStudentNumbers(course.getCourseName());

        if (!studentNumbers.contains(studentNumber)) {
            System.out.println("You are not registered in this course.");
            return;
        }

        studentNumbers.remove(Integer.valueOf(studentNumber));
        writeCourseFile(course, studentNumbers);

        System.out.println("Course dropped successfully.");
    }

    /**
     * Shows the list of courses the object has applied for.
     */
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
    /**
     * Shows the total credits of courses the object has applied for.
     */
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

    /**
     * Get the student ID of the student who selected the course.
     * @param courseName
     * @return
     */
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

    /**
     * update the courses file about register student
     * @param course
     * @param studentNumbers
     */
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

    /**
     * check the student has registered already
     * @param courseName
     * @param studentNumber
     * @return
     */
    private boolean isStudentRegistered(String courseName, int studentNumber) {
        ArrayList<Integer> studentNumbers = readStudentNumbers(courseName);

        if (studentNumbers == null) {
            return false;
        }

        return studentNumbers.contains(studentNumber);
    }
}