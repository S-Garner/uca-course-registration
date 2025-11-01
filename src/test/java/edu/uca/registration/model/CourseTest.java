package edu.uca.registration.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    @Test
    void addStudentToRoster() {
        Course course = new Course("CSCI 4490", "Software Engineering", 12);
        course.addToRoster("B0001");
        assertEquals(1, course.getRoster().size());
        assertTrue(course.getRoster().contains("B0001"));
    }

    @Test
    void addStudentToWaitlist() {
        Course course = new Course("CSCI 4490", "Software Engineering", 12);
        course.addToWaitlist("B0002");
        assertEquals(1, course.getWaitlist().size());
        assertTrue(course.getWaitlist().contains("B0002")); 
    }

    @Test
    void addStudentToWaitlistWhenRosterFull() {
        Course course = new Course("CSCI 4490", "Software Engineering", 2);
        course.addToRoster("B0001");
        course.addToRoster("B0002");
        // Roster is now full
        course.addToRoster("B0003"); // This should add to waitlist instead
        assertEquals(2, course.getRoster().size());
        assertEquals(1, course.getWaitlist().size());
        assertTrue(course.getWaitlist().contains("B0003"));
    }

    @Test
    void courseCapacity() {
        Course course = new Course("CSCI 4490", "Software Engineering", 3);
        assertEquals(3, course.getCapacity());
    }

    @Test
    void getCourseNameAndId() {
        Course course = new Course("CSCI 4490", "Software Engineering", 12);
        assertEquals("CSCI 4490", course.getId());
        assertEquals("Software Engineering", course.getName());
    }
}