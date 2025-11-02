package edu.uca.registration.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uca.registration.model.Course;
import edu.uca.registration.model.Session;
import edu.uca.registration.model.Student;
import edu.uca.registration.repo.ConfigManager;

public class MenuTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddStudentUI() {
        Session session = new Session();
        String fakeUserInput = "B012345\nTest Student\ntest@uca.edu\n";
        Scanner sc = new Scanner(fakeUserInput);

        Menu.addStudentUI(session, sc);

        assertTrue(!session.getStudents().isEmpty(), "Student map should not be empty");
        assertTrue(session.getStudents().containsKey("B012345"), "Student should be in the map");
        assertEquals("Test Student", session.getStudents().get("B012345").getName());

        sc.close();
    }

    @Test
    public void testAddCourseUI() {
        Session session = new Session();

        String fakeUserInput = "CSCI 4490\nSoftware Engineering\n30\n";
        Scanner sc = new Scanner(fakeUserInput);

        Menu.addCourseUI(session, sc);

        assertTrue(!session.getCourses().isEmpty(), "Course map should not be empty");
        assertTrue(session.getCourses().containsKey("CSCI 4490"), "Course should be in the map");
        assertEquals("Software Engineering", session.getCourses().get("CSCI 4490").getName());
        assertEquals(30, session.getCourses().get("CSCI 4490").getCapacity());

        sc.close();
    }

    @Test
    public void testEnrollStudentInCourseUI() {
        Session session = new Session();

        Student student = new Student("B012345", "Test Student", "test@uca.edu");
        session.getStudents().put(student.getId(), student);

        Course course = new Course("CSCI 4490", "Test Course", 30);
        session.getCourses().put(course.getId(), course);

        String fakeUserInput = "B012345\nCSCI 4490\n";
        Scanner sc = new Scanner(fakeUserInput);

        Menu.enrollUI(session, sc);

        assertTrue(session.getCourses().get("CSCI 4490").getRoster().contains("B012345"), "Student should be enrolled in the course");

        sc.close();
    }

    @Test
    public void testDropStudentFromCourseUI() {
        Session session = new Session();

        Student student = new Student("B012345", "Test Student", "test@uca.edu");
        session.getStudents().put(student.getId(), student);

        Course course = new Course("CSCI 4490", "Test Course", 30);
        session.getCourses().put(course.getId(), course);

        String fakeUserInput = "B012345\nCSCI 4490\n";
        Scanner sc = new Scanner(fakeUserInput);

        Menu.enrollUI(session, sc);

        assertTrue(session.getCourses().get("CSCI 4490").getRoster().contains("B012345"), "Student should be enrolled in the course");

        sc.close();
    }

    @Test
    public void testListStudentsUI() {
        Session session = new Session();
        Student s1 = new Student("S1", "Alice", "a@uca.edu");
        Student s2 = new Student("S2", "Bob", "b@uca.edu");
        session.getStudents().put(s1.getId(), s1);
        session.getStudents().put(s2.getId(), s2);

        Menu.listStudents(session.getStudents());

        String output = outContent.toString();
        assertTrue(output.contains("Alice"), "Output should contain student 'Alice'");
        assertTrue(output.contains("S2"), "Output should contain student ID 'S2'");
    }

    @Test
    public void testListCoursesUI() {
        Session session = new Session();
        Course c1 = new Course("CS101", "Intro to CS", 30);
        Course c2 = new Course("CS102", "Data Structures", 25);
        session.getCourses().put(c1.getId(), c1);
        session.getCourses().put(c2.getId(), c2);

        Menu.listCourses(session.getCourses());

        String output = outContent.toString();
        assertTrue(output.contains("CS101"), "Output should contain course 'CS101'");
        assertTrue(output.contains("Data Structures"), "Output should contain course 'Data Structures'");
        assertTrue(output.contains("cap=25"), "Output should show capacity for CS102");
    }

    @Test
    public void testSaveAndLoadUI() {
        ConfigManager.setConfigPath("data/test/config.json");

        File studentsFile = new File("data/students.json");
        File coursesFile = new File("data/courses.json");
        File enrollmentsFile = new File("data/enrollments.json");

        if (studentsFile.exists()) {
            studentsFile.delete();
        }
        if (coursesFile.exists()) {
            coursesFile.delete();
        }
        if (enrollmentsFile.exists()) {
            enrollmentsFile.delete();
        }

        Session saveSession = new Session();
        saveSession.setSessionName("junit-test-session");
        
        Student student = new Student("SAVE001", "Save Test", "save@test.com");
        saveSession.getStudents().put(student.getId(), student);
        
        Course course = new Course("CSCI-SAVE", "Save Course", 10);
        saveSession.getCourses().put(course.getId(), course);
        
        course.getRoster().add(student.getId());

        try {
            Menu.save(saveSession);

            assertTrue(studentsFile.exists(), "data/students.json should have been created");
            assertTrue(coursesFile.exists(), "data/courses.json should have been created");
            assertTrue(enrollmentsFile.exists(), "data/enrollments.json should have been created");


            Session loadSession = new Session();

            Menu.load(loadSession);

            assertTrue(loadSession.getStudents().containsKey("SAVE001"), "Loaded session should have student");
            assertTrue(loadSession.getCourses().containsKey("CSCI-SAVE"), "Loaded session should have course");
            
            Course loadedCourse = loadSession.getCourses().get("CSCI-SAVE");
            assertTrue(loadedCourse.getRoster().contains("SAVE001"), "Enrollment data should be loaded");

        } finally {
            if (studentsFile.exists()) {
                studentsFile.delete();
            }
            if (coursesFile.exists()) {
                coursesFile.delete();
            }
            if (enrollmentsFile.exists()) {
                enrollmentsFile.delete();
            }
        }
    }
}