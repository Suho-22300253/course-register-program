package users;

public class Student extends User{
    //variables
    private int studentNumber;

    //constructor
    public Student(int num, String nameIn, String idIn, String pwIn){
        super(nameIn,idIn,pwIn);
        studentNumber = num;
    }


    //method
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }
    public int getStudentNumber() {
        return studentNumber;
    }
    void courseRegister(){

    }
    void dropACourse(){

    }

}
