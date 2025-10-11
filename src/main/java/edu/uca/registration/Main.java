package edu.uca.registration;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import edu.uca.registration.model.Course;
import edu.uca.registration.model.Student;
import edu.uca.registration.repo.CourseRepo;
import edu.uca.registration.repo.EnrollmentRepo;
import edu.uca.registration.util.Log;
import edu.uca.registration.repo.StudentRepo;
import edu.uca.registration.model.Session;
import edu.uca.registration.app.Menu;
import edu.uca.registration.util.*;

public class Main {
    // ---- Global state (intentionally messy for refactor) ----
    static Session session;
    static Student student;
    static Course course;
    static Map<String, Student> students = new LinkedHashMap<>();
    static Map<String, Course> courses = new LinkedHashMap<>();
    static List<String> auditLog = new ArrayList<>();

    // ---- CSV "persistence" files ----
    static final String STUDENTS_CSV = "students.csv";
    static final String COURSES_CSV = "courses.csv";
    static final String ENROLLMENTS_CSV = "enrollments.csv";

    public static void main(String[] args) throws IOException {
        session = new Session();

        CsvToJsonConverter.convertAll();

        CourseRepo courseRepo = new CourseRepo();
        StudentRepo studentRepo = new StudentRepo();
        EnrollmentRepo enrollmentRepo = new EnrollmentRepo();

        Log log = new Log();

        boolean demo = args.length > 0 && "--demo".equalsIgnoreCase(args[0]);

        /*
        if (demo) {
            seedDemoData();
            audit("SEED demo data");
        } else {
            loadAll();
        }
        */

        Utils.println("=== UCA Course Registration (Baseline) ===");
        Utils.println("NOTE: This code is intentionally messy. You'll refactor it.");
        
        /*
        session.setStudents(studentRepo.load());
        session.setCourses(courseRepo.load());

        enrollmentRepo.load(session);
        */

        Transaction.load(session);

        Menu.menuLoop(session);

        /* 
        studentRepo.save(session.getStudents());
        courseRepo.save(session.getCourses());
        enrollmentRepo.save(session);
        */

        Transaction.save(session);

        log.write(session);

        //saveAll();
        Utils.println("Goodbye!");
    }
    // -------------------- Persistence --------------------
    //private static void loadAll() { loadStudents(); loadCourses(); loadEnrollments(); }

    //private static void saveAll() { saveStudents(); saveCourses(); saveEnrollments(); }

    /*
    // -------------------- Demo data --------------------
    private static void seedDemoData() {
        students.put("B001", new Student("B001", "Alice", "alice@uca.edu"));
        students.put("B002", new Student("B002", "Brian", "brian@uca.edu"));
        courses.put("CSCI4490", new Course("CSCI4490", "Software Engineering", 2));
        courses.put("MATH1496", new Course("MATH1496", "Calculus I", 50));
    }
    */
}
