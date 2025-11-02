package edu.uca.registration.model;

import org.junit.jupiter.api.Test;

public class StudentTest {
    @Test
    void testAddStudent() {
        Student student = new Student("B0001", "John Doe", "john@john.com");
        assert student.getId().equals("B0001");
    }
}
