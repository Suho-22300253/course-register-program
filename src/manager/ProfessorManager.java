package manager;

import course.Course;
import users.Professor;
import users.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handle detail function of Professor
 * about load and save course add course and show my course method
 */
public class ProfessorManager {
    private ArrayList<Course> courses = new ArrayList<>();;
    private SystemManager systemManager;

    private static final String COURSE_FILE = "courses.txt";

    public ProfessorManager(SystemManager systemManager) {
        this.systemManager = systemManager;
    }

    /**
     * load courses from courses.txt
     */
    public void loadCourses() {
        File file = new File(COURSE_FILE);

        /*
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("courses.txt file created.");
            } catch (Exception e) {
                System.out.println("Error creating courses file.");
            }
            return;
        }*/
        //load data from courses.txt
        Scanner inputStream = null;

        try {
            inputStream = new Scanner(file);

            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }
                //split data as coursename, credit, professor name
                String[] arr = line.split("\\|");

                if (arr.length != 3) {
                    continue;
                }

                String courseName = arr[0];
                int credit = Integer.parseInt(arr[1]);
                String professorName = arr[2];

                //Create a course object file for each subject.
                Course course = new Course(courseName, credit, professorName);
                courses.add(course);
                //Create a text file for each course - for storing student information
                createCourseFile(course);
            }

            System.out.println("Course data loaded.");

        } catch (FileNotFoundException e) {
            // error when fail with open file
            System.out.println("Error opening courses file.");
        }

        if (inputStream != null) {
            inputStream.close();
        }
    }

    /**
     * Saves the changed courses arraylist when added, modified, or deleted in courses.txt
     */
    public void saveCourses() {
        PrintWriter outputStream = null;

        try {
            outputStream = new PrintWriter(COURSE_FILE);

            for (int i = 0; i < courses.size(); i++) {
                Course course = courses.get(i);
                //save as (course name))|(credits)|(professor name)
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


    /**
     * method for Professor object add new course
     * @param courseName
     * @param credit
     */
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
        //create new text file for new course
        createCourseFile(course);

        System.out.println("Course added successfully.");
    }

    /**
     * if professor name of course and current user name is same print the course
     */
    public void showMyCourses() {
        User currentUser = systemManager.getCurrentUser();

        int count = 0;

        for (int i = 0; i < courses.size(); i++) {

            if (courses.get(i).getProfessorName().equals(currentUser.getName())) {
                System.out.println(courses.get(i).toString());
                count++;
            }
        }

        System.out.println("Total: " + count +" courses");
    }

    /**
     * print all offered courses
     */
    public void showAllCourses() {
        // when there is no opened courses
        if (courses.isEmpty()) {
            System.out.println("There are currently no courses registered.");
            return;
        }

        for (int i = 0; i < courses.size(); i++) {
            System.out.println(courses.get(i).toString());
        }
    }

    /**
     * compare course name
     * @param courseName
     * @return
     */
    public Course findCourseByName(String courseName) {
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    /**
     * chekc course name is duplicated
     * @param courseName
     * @return
     */
    private boolean isDuplicatedCourseName(String courseName) {
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);

            if (course.getCourseName().equals(courseName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * create course text file
     * text file name is (course name).txt
     * @param course
     */
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