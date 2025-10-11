package edu.uca.registration.app;

import java.util.Map;
import java.util.Scanner;

import edu.uca.registration.model.Course;
import edu.uca.registration.model.Session;
import edu.uca.registration.model.Student;
import edu.uca.registration.service.RegistrationServices;
import edu.uca.registration.util.*;

public class Menu {

    public static void menuLoop(Session sessionObj) {
        Scanner sc = new Scanner(System.in);

        createSessionName(sessionObj, sc);

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

        RegistrationServices.addStudentService(s, sessionObj);
    }

    private static void addCourseUI(Session sessionObj, Scanner sc) {
        Utils.print("Course code: ");
        String code = sc.nextLine().trim();

        Utils.print("Title: ");
        String title = sc.nextLine().trim();

        Utils.print("Capacity: ");
        int cap = Integer.parseInt(sc.nextLine().trim());

        Course c = new Course(code, title, cap);

        RegistrationServices.addCourseService(c, sessionObj);
    }

    private static void enrollUI(Session sessionObj, Scanner sc) {
        Utils.print("Student ID: ");
        String sid = sc.nextLine().trim();

        Utils.print("Course Code: ");
        String cc = sc.nextLine().trim();

        Course c = sessionObj.getCourses().get(cc);

        RegistrationServices.enrollService(sid, c, sessionObj);
    }

    private static void dropUI(Session sessionObj, Scanner sc) {
        Utils.print("Student ID: ");
        String sid = sc.nextLine().trim();

        Utils.print("Course Code: ");
        String cc = sc.nextLine().trim();

        Course c = sessionObj.getCourses().get(cc);

        RegistrationServices.dropService(sid, c, sessionObj);
    }

    private static void createSessionName(Session sessionObj, Scanner sc) {
        Utils.println("Please input your session name: ");

        String sessionName = sc.nextLine().trim();

        RegistrationServices.setSessionName(sessionName, sessionObj);

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
            Utils.println(" - " + c.getId()
                    + " " + c.getName()
                    + " cap=" + c.getCapacity()
                    + " enrolled=" + c.getRoster().size()
                    + " wait=" + c.getWaitlist().size());
        }
    }

}
