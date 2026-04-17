package manager;

import users.User;

public interface ISystemManager {
    void createStudentAccount(String name, String id, String password, int studentNumber);
    void createProfessorAccount(String name, String id, String password, int professorNumber);
    User login(String id, String password);
    void logout();
}