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

    public void registerCourse(String courseId) {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Student)) {
            System.out.println("Only student can register course.");
            return;
        }

        Course course = findCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        String fileName = course.getCourseId() + ".txt";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Course detail file not found.");
            return;
        }

        ArrayList<String> headerLines = new ArrayList<>();
        ArrayList<String> studentLines = new ArrayList<>();
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

                if (studentPart) {
                    studentLines.add(line);
                } else {
                    headerLines.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading course file.");
            return;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        for (int i = 0; i < studentLines.size(); i++) {
            String[] arr = studentLines.get(i).split("\\|");

            if (arr.length == 2 && arr[0].equals(currentUser.getId())) {
                System.out.println("You already registered this course.");
                return;
            }
        }

        studentLines.add(currentUser.getId() + "|NONE");

        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(fileName);

            for (int i = 0; i < headerLines.size(); i++) {
                outputStream.println(headerLines.get(i));
            }

            outputStream.println("STUDENTS");

            for (int i = 0; i < studentLines.size(); i++) {
                outputStream.println(studentLines.get(i));
            }

            System.out.println("Course registered successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("Error writing course file.");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public void dropCourse(String courseId) {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Student)) {
            System.out.println("Only student can drop course.");
            return;
        }

        Course course = findCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        String fileName = course.getCourseId() + ".txt";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Course detail file not found.");
            return;
        }

        ArrayList<String> headerLines = new ArrayList<>();
        ArrayList<String> studentLines = new ArrayList<>();
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

                if (studentPart) {
                    studentLines.add(line);
                } else {
                    headerLines.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading course file.");
            return;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        boolean removed = false;
        ArrayList<String> updatedStudentLines = new ArrayList<>();

        for (int i = 0; i < studentLines.size(); i++) {
            String[] arr = studentLines.get(i).split("\\|");

            if (arr.length == 2 && arr[0].equals(currentUser.getId())) {
                removed = true;
            } else {
                updatedStudentLines.add(studentLines.get(i));
            }
        }

        if (!removed) {
            System.out.println("You are not registered in this course.");
            return;
        }

        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(fileName);

            for (int i = 0; i < headerLines.size(); i++) {
                outputStream.println(headerLines.get(i));
            }

            outputStream.println("STUDENTS");

            for (int i = 0; i < updatedStudentLines.size(); i++) {
                outputStream.println(updatedStudentLines.get(i));
            }

            System.out.println("Course dropped successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("Error writing course file.");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
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

            if (isStudentRegistered(course.getCourseId(), currentUser.getId())) {
                System.out.println(
                        "Course ID: " + course.getCourseId() +
                                ", Name: " + course.getCourseName() +
                                ", Credit: " + course.getCredit() +
                                ", Professor: " + course.getProfessorName()
                );
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

            if (isStudentRegistered(course.getCourseId(), currentUser.getId())) {
                totalCredits += course.getCredit();
            }
        }

        System.out.println("Total registered credits: " + totalCredits);
    }

    public void checkGrades() {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Student)) {
            System.out.println("Only student can check grades.");
            return;
        }

        ArrayList<Course> courses = professorManager.getCourses();
        boolean found = false;

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            String grade = getStudentGrade(course.getCourseId(), currentUser.getId());

            if (grade != null) {
                System.out.println(
                        "Course ID: " + course.getCourseId() +
                                ", Name: " + course.getCourseName() +
                                ", Grade: " + grade
                );
                found = true;
            }
        }

        if (!found) {
            System.out.println("No grade information.");
        }
    }

    private Course findCourseById(String courseId) {
        ArrayList<Course> courses = professorManager.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }

        return null;
    }

    private boolean isStudentRegistered(String courseId, String studentId) {
        String fileName = courseId + ".txt";
        File file = new File(fileName);

        if (!file.exists()) {
            return false;
        }

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

                if (studentPart) {
                    String[] arr = line.split("\\|");

                    if (arr.length == 2 && arr[0].equals(studentId)) {
                        return true;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return false;
    }

    private String getStudentGrade(String courseId, String studentId) {
        String fileName = courseId + ".txt";
        File file = new File(fileName);

        if (!file.exists()) {
            return null;
        }

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

                if (studentPart) {
                    String[] arr = line.split("\\|");

                    if (arr.length == 2 && arr[0].equals(studentId)) {
                        return arr[1];
                    }
                }
            }

        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return null;
    }
}