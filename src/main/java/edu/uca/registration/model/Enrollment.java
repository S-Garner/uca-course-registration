package edu.uca.registration.model;

public class Enrollment {
    private String courseCode;
    private String studentId;
    private String status;

    public Enrollment() {};

    public Enrollment(String courseCode, String studentId, String status) {
        this.courseCode = courseCode;
        this.studentId = studentId;
        this.status = status;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
