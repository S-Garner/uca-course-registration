package edu.uca.registration.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.LinkedHashMap;

public class SessionTest {
    @Test
    void testSessionCreation() {
        Session session = new Session("Fall 2024");
        assertEquals("Fall 2024", session.getSessionName());
    }

    @Test
    void setStudents() {
        Session session = new Session("Fall 2024");
        Map<String, Student> students = new LinkedHashMap<>();
        students.put("B0001", new Student("B0001", "Alice", "Alice@alice.com"));
        session.setStudents(students);
        assertEquals(1, session.getStudents().size());
        assertTrue(session.getStudents().containsKey("B0001"));
    }

    @Test
    void setCourses() {
        Session session = new Session("Fall 2024");
        Map<String, Course> courses = new LinkedHashMap<>();
        courses.put("CSCI 4490", new Course("CSCI 4490", "Software Engineering", 12));
        session.setCourses(courses);
        assertEquals(1, session.getCourses().size());
        assertTrue(session.getCourses().containsKey("CSCI 4490"));
    }

    @Test
    void auditLogInitialization() {
        Session session = new Session("Fall 2024");
        assertNotNull(session.getAuditLog());
        assertEquals(0, session.getAuditLog().size());
    }
}