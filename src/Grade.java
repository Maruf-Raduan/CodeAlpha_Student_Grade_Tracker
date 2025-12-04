public class Grade {
    private double score;
    private String courseCode;
    private String subjectName;

    public Grade(double score, String courseCode, String subjectName) {
        this.score = score;
        this.courseCode = courseCode;
        this.subjectName = subjectName;
    }

    public double getScore() {
        return score;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    @Override
    public String toString() {
        return courseCode + ": " + subjectName + " - " + score;
    }
}
