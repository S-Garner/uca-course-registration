package edu.uca.registration.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private Map<String, Student> students = new LinkedHashMap<>();
    private Map<String, Course> courses = new LinkedHashMap<>();
    private List<String> auditLog = new ArrayList<>();

    public Map<String, Student> getStudents() {
        return students;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public List<String> getAuditLog() {
        return auditLog;
    }
}
