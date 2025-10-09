package edu.uca.registration.app;

import java.util.Map;
import java.util.Scanner;

import edu.uca.registration.model.Course;
import edu.uca.registration.model.Session;
import edu.uca.registration.model.Student;
import edu.uca.registration.util.*;

public class Menu {

    public static void menuLoop(Session sessionObj) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            Utils.println("\nMenu:");
            Utils.println("1) Add Student");
            Utils.println("2) Add Course");
            Utils.println("3) Enroll student in course");
            Utils.println("4) Drop student from course");
            Utils.println("5) List students");
            Utils.println("6) List courses");
            Utils.println("0) Exit");
            Utils.println("Choose: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": addStudentUI(sessionObj, sc);
                    break;
                case "2": addCourseUI(sessionObj, sc);
                    break;
                case "3": enrollUI(sessionObj, sc);
                    break;
                case "4": dropUI(sessionObj, sc);
                    break;
                case "5": listStudents(sessionObj.getStudents());
                    break;
                case "6": listCourses(sessionObj.getCourses());
                    break;
                case "0":
                    return;
                default:
                    Utils.println("Invalid");
                    break;
            }
        }
    }

    private static void addStudentUI(Session sessionObj, Scanner sc) {
        Utils.print("Banner ID: ");
        String id = sc.nextLine().trim();

        Utils.print("Name: ");
        String name = sc.nextLine().trim();

        Utils.print("Email: ");
        String email = sc.nextLine().trim();

        Student s = new Student(id, name, email);

        sessionObj.getStudents().put(id, s);

        Utils.audit("ADD_STUDENT" + id, sessionObj.getAuditLog());
    }

    private static void addCourseUI(Session sessionObj, Scanner sc) {
        Utils.print("Course code: ");
        String code = sc.nextLine().trim();

        Utils.print("Title: ");
        String title = sc.nextLine().trim();

        Utils.print("Capacity: ");
        int cap = Integer.parseInt(sc.nextLine().trim());

        Course c = new Course(code, title, cap);

        sessionObj.getCourses().put(code, c);

        Utils.audit("ADD_COURSE" + code, sessionObj.getAuditLog());
    }

    private static void enrollUI(Session sessionObj, Scanner sc) {
        Utils.print("Student ID: ");
        String sid = sc.nextLine().trim();

        Utils.print("Course Code: ");
        String cc = sc.nextLine().trim();

        Course c = sessionObj.getCourses().get(cc);

        if (c == null) {
            Utils.println("No such course");
            return;
        } else if (c.getRoster().contains(sid)) {
            Utils.println("Already enrolled");
            return;
        } else if (c.getWaitlist().contains(sid)) {
            Utils.println("Already waitlisted");
            return;
        } else {
            if (c.getRoster().size() >= c.getCapacity()) {
                c.getWaitlist().add(sid);
                Utils.audit("WAITLIST" + sid + "->" + cc, sessionObj.getAuditLog());
            } else {
                c.getRoster().add(sid);
                Utils.audit("ENROLL " + sid + "->" + cc, sessionObj.getAuditLog());
                Utils.println("Enrolled.");
            }
        }
    }

    private static void dropUI(Session sessionObj, Scanner sc) {
        Utils.print("Student ID: ");
        String sid = sc.nextLine().trim();

        Utils.print("Course Code: ");
        String cc = sc.nextLine().trim();

        Course c = sessionObj.getCourses().get(cc);

        if (c == null) {
            Utils.println("No such course");
            return;
        } else {
            if (c.getRoster().remove(sid)) {
                Utils.audit("DROP " + sid + " from " + cc, sessionObj.getAuditLog());

                if (!c.getWaitlist().isEmpty()) {
                    String promote = c.getWaitlist().remove(0);

                    c.getRoster().add(promote);

                    Utils.audit("PROMOTE " + promote + "->" + cc, sessionObj.getAuditLog());
                    Utils.println("Promoted " + promote + " from waitlist.");
                } else {
                    Utils.println("Dropped.");
                }
            } else if (c.getWaitlist().remove(sid)) {
                Utils.audit("WAITLIST_REMOVE " + sid + " " + cc, sessionObj.getAuditLog());
                Utils.println("Removed from waitlist.");
            } else {
                Utils.println("Not enrolled or waitlisted.");
            }
        }
    }

    private static void listStudents(Map<String, Student> students) {
        Utils.println("Students:");

        for (Student s : students.values()) {
            Utils.println(" - " + s);
        }
    }

    private static void listCourses(Map<String, Course> courses) {
        Utils.println("Courses:");

        for (Course c : courses.values()) {
            Utils.println(" - " + c.getCode()
                    + " " + c.getTitle()
                    + " cap=" + c.getCapacity()
                    + " enrolled=" + c.getRoster().size()
                    + " wait=" + c.getWaitlist().size());
        }
    }

}
