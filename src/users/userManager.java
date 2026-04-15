package users;
import java.util.Scanner;
import java.util.ArrayList;
public class userManager implements ISystemManager{
    Scanner keyboard = new Scanner(System.in);
    private ArrayList<Student> students;
    private ArrayList<Professor> professors;
    private User currentUser;

    public userManager() {
        students = new ArrayList<>();
        professors = new ArrayList<>();
        currentUser = null;
    }

    @Override
    public Student createStudentAccount(String name, String id, String password, int studentNumber) {
        if (isDuplicatedId(id)) {
            System.out.println("This id already exists.");
            return null;
        }
        Student student = new Student(studentNumber, name, id, password);
        students.add(student);
        return student; // 왜 student를 반환하는 거지?
    }

    @Override
    public Professor createProfessorAccount(String name, String id, String password, int professorNumber) {
        if (isDuplicatedId(id)) {
            System.out.println("This id already exists.");
            return null;
        }
        Professor professor = new Professor(professorNumber, name, id, password);
        professors.add(professor);
        return professor;
    }



    @Override
    public User login(String id, String password) {
        for (Student student : students) {
            if (student.getId().equals(id) && student.getPassword().equals(password)) {
                currentUser = student;
                System.out.println(student.getName() + " logged in.");
                return currentUser;
            }
        }

        for (Professor professor : professors) {
            if (professor.getId().equals(id) && professor.getPassword().equals(password)) {
                currentUser = professor;
                System.out.println(professor.getName() + " logged in.");
                return currentUser;
            }
        }

        System.out.println("Login failed.");
        return null;
    }

    @Override
    public void logout() {
        if (currentUser != null) {
            System.out.println(currentUser.getName() + " logged out.");
            currentUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
    private boolean isDuplicatedId(String id) {
        for (int i = 0 ; i < students.size() ; i++) {
            Student student = students.get(i);
            if (student.getId().equals(id)) {
                return true;
            }
        }

        for (int i = 0 ; i < professors.size() ; i++) {
            Professor professor = professors.get(i);
            if (professor.getId().equals(id)) {
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
}
