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
    private ArrayList<Course> courses = new ArrayList<>();;
    private SystemManager systemManager;

    private static final String COURSE_FILE = "courses.txt";

    public ProfessorManager(SystemManager systemManager) {
        this.systemManager = systemManager;
    }

    public void loadCourses() {
        File file = new File(COURSE_FILE);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("courses.txt file created.");
            } catch (Exception e) {
                System.out.println("Error creating courses file.");
            }
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

                if (arr.length != 3) {
                    continue;
                }

                String courseName = arr[0];
                int credit = Integer.parseInt(arr[1]);
                String professorName = arr[2];

                Course course = new Course(courseName, credit, professorName);
                courses.add(course);

                createCourseFile(course);
            }

            System.out.println("Course data loaded.");

        } catch (FileNotFoundException e) {
            System.out.println("Error opening courses file.");
        }

        if (inputStream != null) {
            inputStream.close();
        }
    }

    public void saveCourses() {
        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(COURSE_FILE);

            for (int i = 0; i < courses.size(); i++) {
                Course course = courses.get(i);
                outputStream.println(course.getCourseName() + "|" +course.getCredit() + "|" +course.getProfessorName());
            }

            System.out.println("Course data saved.");

        } catch (FileNotFoundException e) {
            System.out.println("Error saving courses file.");
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }

    public void addCourse(String courseName, int credit) {
        User currentUser = systemManager.getCurrentUser();

        if (!(currentUser instanceof Professor)) {
            System.out.println("Only professor can add course.");
            return;
        }

        if (isDuplicatedCourseName(courseName)) {
            System.out.println("This course already exists.");
            return;
        }

        Course course = new Course(courseName, credit, currentUser.getName());
        courses.add(course);
        saveCourses();
        createCourseFile(course);

        System.out.println("Course added successfully.");
    }

    public void showMyCourses() {
        User currentUser = systemManager.getCurrentUser();

        int count = 0;

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (course.getProfessorName().equals(currentUser.getName())) {
                System.out.println(course);
                count++;
            }
        }

        System.out.println("Total: " + count +" courses");
    }

    public void showAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("There are currently no courses registered.");
            return;
        }

        for (int i = 0; i < courses.size(); i++) {
            System.out.println(courses.get(i).toString());
        }
    }

    public Course findCourseByName(String courseName) {
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    private boolean isDuplicatedCourseName(String courseName) {
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (course.getCourseName().equals(courseName)) {
                return true;
            }
        }
        return false;
    }

    private void createCourseFile(Course course) {
        String fileName = course.getCourseName() + ".txt";
        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Error creating course file.");
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}