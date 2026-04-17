package course;

public class Course {
    private String courseId;
    private String courseName;
    private int credit;
    private String professorName;
    private String professorId;

    public Course(String courseId, String courseName, int credit, String professorName, String professorId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.professorName = professorName;
        this.professorId = professorId;
    }

    public String getCourseId() {
        return courseId;
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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}