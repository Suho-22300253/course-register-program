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

        ArrayList<String> studentIds = readStudentIds(course.getCourseName());

        if (studentIds == null) {
            System.out.println("Course detail file not found.");
            return;
        }

        if (studentIds.contains(currentUser.getId())) {
            System.out.println("You already registered this course.");
            return;
        }

        studentIds.add(currentUser.getId());
        writeCourseFile(course, studentIds);

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

        ArrayList<String> studentIds = readStudentIds(course.getCourseName());

        if (studentIds == null) {
            System.out.println("Course detail file not found.");
            return;
        }

        if (!studentIds.contains(currentUser.getId())) {
            System.out.println("You are not registered in this course.");
            return;
        }

        studentIds.remove(currentUser.getId());
        writeCourseFile(course, studentIds);

        System.out.println("Course dropped successfully.");
    }

    public void showMyCourses() {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Student)) {
            System.out.println("Only student can check registered courses.");
            return;
        }

        ArrayList<Course> courses = professorManager.getCourses();
        boolean found = false;

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (isStudentRegistered(course.getCourseName(), currentUser.getId())) {
                System.out.println(course);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No registered course.");
        }
    }

    public void checkCredits() {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Student)) {
            System.out.println("Only student can check credits.");
            return;
        }

        ArrayList<Course> courses = professorManager.getCourses();
        int totalCredits = 0;

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (isStudentRegistered(course.getCourseName(), currentUser.getId())) {
                totalCredits += course.getCredit();
            }
        }

        System.out.println("Total registered credits: " + totalCredits);
    }

    private ArrayList<String> readStudentIds(String courseName) {
        String fileName = courseName + ".txt";
        File file = new File(fileName);

        if (!file.exists()) {
            return null;
        }

        ArrayList<String> studentIds = new ArrayList<>();
        Scanner inputStream = null;

        try {
            inputStream = new Scanner(file);
            boolean studentPart = false;

            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine();

                if (line.equals("STUDENTS")) {
                    studentPart = true;
                    continue;
                }

                if (studentPart && !line.trim().isEmpty()) {
                    studentIds.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return studentIds;
    }

    private void writeCourseFile(Course course, ArrayList<String> studentIds) {
        String fileName = course.getCourseName() + ".txt";
        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(fileName);

            outputStream.println("COURSE_NAME|" + course.getCourseName());
            outputStream.println("CREDIT|" + course.getCredit());
            outputStream.println("PROFESSOR_NAME|" + course.getProfessorName());
            outputStream.println("PROFESSOR_ID|" + course.getProfessorId());
            outputStream.println("STUDENTS");

            for (int i = 0; i < studentIds.size(); i++) {
                outputStream.println(studentIds.get(i));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error writing course file.");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private boolean isStudentRegistered(String courseName, String studentId) {
        ArrayList<String> studentIds = readStudentIds(courseName);

        if (studentIds == null) {
            return false;
        }

        return studentIds.contains(studentId);
    }
}