package edu.uca.registration.repo;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.uca.registration.model.Course;
import edu.uca.registration.model.Session;
import edu.uca.registration.model.Student;

public class DemoRepo {
    private Map<String, Student> students = new LinkedHashMap<>();
    private Map<String, Course> courses = new LinkedHashMap<>();

    public DemoRepo() {
        students.put("B001", new Student("B001", "Alice", "alice@uca.edu"));
        students.put("B002", new Student("B002", "Brian", "brian@uca.edu"));
        courses.put("CSCI4490", new Course("CSCI4490", "Software Engineering", 2));
        courses.put("MATH1496", new Course("MATH1496", "Calculus I", 50));
    }

    public void setDemo(Session sessionObj) {
        sessionObj.setStudents(students);
        sessionObj.setCourses(courses);
    }
}
