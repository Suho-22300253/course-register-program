package manager;

import course.Course;
import users.Professor;
import users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ProfessorManager {
    private ArrayList<Course> courses;
    private SystemManager systemManager;

    private static final String COURSE_FILE = "courses.txt";

    public ProfessorManager(SystemManager systemManager) {
        this.systemManager = systemManager;
        this.courses = new ArrayList<>();
    }

    public void loadCourses() {
        File file = new File(COURSE_FILE);

        if (!file.exists()) {
            System.out.println("No course file. Start with empty course list.");
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

                String[] arr = line.split("\\|");

                if (arr.length != 5) {
                    continue;
                }

                String courseId = arr[0];
                String courseName = arr[1];
                int credit = Integer.parseInt(arr[2]);
                String professorName = arr[3];
                String professorId = arr[4];

                Course course = new Course(courseId, courseName, credit, professorName, professorId);
                courses.add(course);
            }

            System.out.println("Course data loaded.");

        } catch (FileNotFoundException e) {
            System.out.println("Error opening courses file.");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public void saveCourses() {
        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(COURSE_FILE);

            for (int i = 0; i < courses.size(); i++) {
                Course course = courses.get(i);

                outputStream.println(
                        course.getCourseId() + "|" +
                                course.getCourseName() + "|" +
                                course.getCredit() + "|" +
                                course.getProfessorName() + "|" +
                                course.getProfessorId()
                );
            }

            System.out.println("Course data saved.");

        } catch (FileNotFoundException e) {
            System.out.println("Error saving courses file.");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public void addCourse(String courseId, String courseName, int credit) {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Professor)) {
            System.out.println("Only professor can add course.");
            return;
        }

        if (isDuplicatedCourseId(courseId)) {
            System.out.println("This course id already exists.");
            return;
        }

        Course course = new Course(
                courseId,
                courseName,
                credit,
                currentUser.getName(),
                currentUser.getId()
        );

        courses.add(course);
        saveCourses();
        createCourseFile(course);

        System.out.println("Course added successfully.");
    }

    public void showMyCourses() {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Professor)) {
            System.out.println("Only professor can check course list.");
            return;
        }

        boolean found = false;

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (course.getProfessorId().equals(currentUser.getId())) {
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
            System.out.println("No course found.");
        }
    }

    public void enterGrade(String courseId, String studentId, String grade) {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Professor)) {
            System.out.println("Only professor can enter grade.");
            return;
        }

        Course course = findCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (!course.getProfessorId().equals(currentUser.getId())) {
            System.out.println("You can enter grade only for your own course.");
            return;
        }

        updateStudentGradeInCourseFile(course, studentId, grade);
    }

    private boolean isDuplicatedCourseId(String courseId) {
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (course.getCourseId().equals(courseId)) {
                return true;
            }
        }

        return false;
    }

    private Course findCourseById(String courseId) {
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }

        return null;
    }

    private void createCourseFile(Course course) {
        String fileName = course.getCourseId() + ".txt";
        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(fileName);

            outputStream.println("COURSE_ID|" + course.getCourseId());
            outputStream.println("COURSE_NAME|" + course.getCourseName());
            outputStream.println("CREDIT|" + course.getCredit());
            outputStream.println("PROFESSOR_NAME|" + course.getProfessorName());
            outputStream.println("PROFESSOR_ID|" + course.getProfessorId());
            outputStream.println("STUDENTS");

        } catch (FileNotFoundException e) {
            System.out.println("Error creating course file.");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private void updateCourseFile(Course course) {
        String fileName = course.getCourseId() + ".txt";
        File file = new File(fileName);

        if (!file.exists()) {
            createCourseFile(course);
            return;
        }

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

        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(fileName);

            outputStream.println("COURSE_ID|" + course.getCourseId());
            outputStream.println("COURSE_NAME|" + course.getCourseName());
            outputStream.println("CREDIT|" + course.getCredit());
            outputStream.println("PROFESSOR_NAME|" + course.getProfessorName());
            outputStream.println("PROFESSOR_ID|" + course.getProfessorId());
            outputStream.println("STUDENTS");

            for (int i = 0; i < studentLines.size(); i++) {
                outputStream.println(studentLines.get(i));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error updating course file.");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private void updateStudentGradeInCourseFile(Course course, String studentId, String grade) {
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

        boolean found = false;

        for (int i = 0; i < studentLines.size(); i++) {
            String[] arr = studentLines.get(i).split("\\|");

            if (arr.length == 2 && arr[0].equals(studentId)) {
                studentLines.set(i, studentId + "|" + grade);
                found = true;
                break;
            }
        }

        if (!found) {
            studentLines.add(studentId + "|" + grade);
        }

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

            System.out.println("Grade entered successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("Error writing course file.");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}