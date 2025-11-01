package edu.uca.registration.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentTest {
    @Test
    void testEnrollmentConstructorAndGetters() {
        Enrollment enrollment = new Enrollment("CSCI 4490", "B0001", "enrolled");
        assertEquals("CSCI 4490", enrollment.getCourseCode());
        assertEquals("B0001", enrollment.getStudentId());
        assertEquals("enrolled", enrollment.getStatus());
    }

    @Test
    void testSetters() {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseCode("CSCI 4490");
        enrollment.setStudentId("B0001");
        enrollment.setStatus("waitlisted");

        assertEquals("CSCI 4490", enrollment.getCourseCode());
        assertEquals("B0001", enrollment.getStudentId());
        assertEquals("waitlisted", enrollment.getStatus());
    }

    @Test
    void testDefaultConstructor() {
        Enrollment enrollment = new Enrollment();
        assertNull(enrollment.getCourseCode());
        assertNull(enrollment.getStudentId());
        assertNull(enrollment.getStatus());
    }

    @Test
    void testChangeStatus() {
        Enrollment enrollment = new Enrollment("CSCI 4490", "B0001", "enrolled");
        enrollment.setStatus("completed");
        assertEquals("completed", enrollment.getStatus()); 
    }
}