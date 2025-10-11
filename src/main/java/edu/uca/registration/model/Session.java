package edu.uca.registration.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private Map<String, Student> students = new LinkedHashMap<>();
    private Map<String, Course> courses = new LinkedHashMap<>();
    private List<String> auditLog = new ArrayList<>();

    private String sessionName;

    public Map<String, Student> getStudents() {
        return students;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public List<String> getAuditLog() {
        return auditLog;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setStudents(Map<String, Student> students) {
        this.students = students;
    }

    public void setCourses(Map<String, Course> courses) {
        this.courses = courses;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
}
