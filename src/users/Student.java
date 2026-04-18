package users;

/**
 * implementation class of User class
 */
public class Student extends User{
    //variables
    private int studentNumber;

    //constructor
    public Student(int num, String nameIn, String idIn, String pwIn){
        super(nameIn,idIn,pwIn);
        studentNumber = num;
    }
    //method
    public int getStudentNumber() {
        return studentNumber;
    }
}
