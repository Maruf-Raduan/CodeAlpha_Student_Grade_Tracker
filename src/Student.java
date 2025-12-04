import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
    private String name;
    private String registrationNumber;
    private ArrayList<Grade> grades;

    public Student(String name, String registrationNumber) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void addGrade(double score, String courseCode, String subjectName) {
        grades.add(new Grade(score, courseCode, subjectName));
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public double getAverage() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Grade grade : grades) {
            sum += grade.getScore();
        }
        return sum / grades.size();
    }

    public double getHighest() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double max = Double.MIN_VALUE;
        for (Grade grade : grades) {
            if (grade.getScore() > max) {
                max = grade.getScore();
            }
        }
        return max;
    }

    public double getLowest() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double min = Double.MAX_VALUE;
        for (Grade grade : grades) {
            if (grade.getScore() < min) {
                min = grade.getScore();
            }
        }
        return min;
    }

    @Override
    public String toString() {
        return "ID: " + registrationNumber + " | Name: " + name + " | Grades: " + grades +
                " | Avg: " + String.format("%.2f", getAverage()) +
                " | Max: " + getHighest() +
                " | Min: " + getLowest();
    }
}
