package users;

public class Professor extends User {
    private int professorNumber;

    public Professor(int num, String nameIn, String idIn, String pwIn){
        super(nameIn,idIn,pwIn);
        professorNumber = num;
    }
    public int getProfessorNumber() {
        return professorNumber;
    }

}
