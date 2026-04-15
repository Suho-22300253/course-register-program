package users;

public interface ISystemManager {
    Student createStudentAccount(String name, String id, String password, int studentNumber);
    Professor createProfessorAccount(String name, String id, String password, int professorNumber);

    User login(String id, String password);
    void logout();
}