package edu.uca.registration;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import edu.uca.registration.model.Course;
import edu.uca.registration.model.Student;
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

    public static void main(String[] args) {
        session = new Session();
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
        //menuLoop();
        Menu.menuLoop(session);
        //saveAll();
        Utils.println("Goodbye!");
    }
    // -------------------- Persistence --------------------
    //private static void loadAll() { loadStudents(); loadCourses(); loadEnrollments(); }

    //private static void saveAll() { saveStudents(); saveCourses(); saveEnrollments(); }

    /*
    private static void loadStudents() {
        File f = new File(STUDENTS_CSV);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length >= 3) {
                    students.put(p[0], new Student(p[0], p[1], p[2]));
                }
            }
            audit("LOAD students=" + students.size());
        } catch (Exception e) {
            println("Failed load students: " + e.getMessage());
        }
    }

    private static void saveStudents() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(STUDENTS_CSV))) {
            for (Student s : students.values()) {
                pw.println(s.id + "," + s.name + "," + s.email);
            }
        } catch (Exception e) {
            println("Failed save students: " + e.getMessage());
        }
    }

    private static void loadCourses() {
        File f = new File(COURSES_CSV);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length >= 3) {
                    try {
                        int cap = Integer.parseInt(p[2]);
                        courses.put(p[0], new Course(p[0], p[1], cap));
                    } catch (NumberFormatException ignored) {}
                }
            }
            audit("LOAD courses=" + courses.size());
        } catch (Exception e) {
            println("Failed load courses: " + e.getMessage());
        }
    }

    private static void saveCourses() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(COURSES_CSV))) {
            for (Course c : courses.values()) {
                pw.println(c.code + "," + c.title + "," + c.capacity);
            }
        } catch (Exception e) {
            println("Failed save courses: " + e.getMessage());
        }
    }

    private static void loadEnrollments() {
        File f = new File(ENROLLMENTS_CSV);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: courseCode|studentId|ENROLLED or WAITLIST
                String[] p = line.split("\\|", -1);
                if (p.length >= 3) {
                    String code = p[0], sid = p[1], status = p[2];
                    Course c = courses.get(code);
                    if (c == null) continue;
                    if ("ENROLLED".equalsIgnoreCase(status)) {
                        if (!c.roster.contains(sid)) c.roster.add(sid);
                    } else if ("WAITLIST".equalsIgnoreCase(status)) {
                        if (!c.waitlist.contains(sid)) c.waitlist.add(sid);
                    }
                }
            }
            audit("LOAD enrollments");
        } catch (Exception e) {
            println("Failed load enrollments: " + e.getMessage());
        }
    }

    private static void saveEnrollments() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ENROLLMENTS_CSV))) {
            for (Course c : courses.values()) {
                for (String sid : c.roster) pw.println(c.code + "|" + sid + "|ENROLLED");
                for (String sid : c.waitlist) pw.println(c.code + "|" + sid + "|WAITLIST");
            }
        } catch (Exception e) {
            println("Failed save enrollments: " + e.getMessage());
        }
    }

    // -------------------- Demo data --------------------
    private static void seedDemoData() {
        students.put("B001", new Student("B001", "Alice", "alice@uca.edu"));
        students.put("B002", new Student("B002", "Brian", "brian@uca.edu"));
        courses.put("CSCI4490", new Course("CSCI4490", "Software Engineering", 2));
        courses.put("MATH1496", new Course("MATH1496", "Calculus I", 50));
    }

    /*
    // -------------------- Tiny domain types --------------------
    static class Student {
        String id, name, email;
        Student(String id, String name, String email) { this.id=id; this.name=name; this.email=email; }
        public String toString() { return id + " " + name + " <" + email + ">"; }
    }
    */
    /*
    static class Course {
        String code, title; int capacity;
        List<String> roster = new ArrayList<>(), waitlist = new ArrayList<>();
        Course(String code, String title, int capacity) { this.code=code; this.title=title; this.capacity=capacity; }
    }
    */

    /*
    // -------------------- Utils --------------------
    private static void print(String s){ System.out.print(s); }
    private static void println(String s){ System.out.println(s); }
    private static void audit(String ev){ auditLog.add(LocalDateTime.now() + " | " + ev); }

    */
}
