package course;

public class Course {
    private String courseName;
    private int credit;
    private String professorName;

    public Course(String courseName, int credit, String professorName) {
        this.courseName = courseName;
        this.credit = credit;
        this.professorName = professorName;
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



    @Override
    public String toString() {
        return "Course Name: " + courseName + ", Credit: " + credit + ", Professor: " + professorName;
    }
}