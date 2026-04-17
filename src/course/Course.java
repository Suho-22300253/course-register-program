package course;

public class Course {
    private String courseName;
    private int credit;
    private String professorName;
    private String professorId;

    public Course(String courseName, int credit, String professorName, String professorId) {
        this.courseName = courseName;
        this.credit = credit;
        this.professorName = professorName;
        this.professorId = professorId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredit() {
        return credit;
    }

    public String getProfessorName() {
        return professorName;
    }

    public String getProfessorId() {
        return professorId;
    }

    @Override
    public String toString() {
        return "Course Name: " + courseName +
                ", Credit: " + credit +
                ", Professor: " + professorName;
    }
}