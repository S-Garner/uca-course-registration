package edu.uca.registration.service;

import edu.uca.registration.model.*;
import edu.uca.registration.repo.Log;
import edu.uca.registration.util.*;

public class RegistrationServices {

    private static final Log log = new Log();

    public static void addStudentService(Student student, Session sessionObj) {
        if (sessionObj.getStudents().containsKey(student.getId())) {
            Utils.println("Student with ID " + student.getId() + " already exists.");
            return;
        }

        sessionObj.getStudents().put(student.getId(), student);

        String message = "ADD_STUDENT " + student.getId();

        sessionObj.getAuditLog().add(message);
        log.add("ACTION", message, sessionObj.getSessionName());
    }

    public static void setSessionName(String sessionName, Session sessionObj) {
        sessionObj.setSessionName(sessionName);
    }

    public static void addCourseService(Course course, Session sessionObj) {
        if (sessionObj.getCourses().containsKey(course.getId())) {
            Utils.println("Course with code " + course.getId() + " already exists.");
            return;
        }

        sessionObj.getCourses().put(course.getId(), course);
        String message = "ADD_COURSE " + course.getId();

        sessionObj.getAuditLog().add(message);
        log.add("ACTION", message, sessionObj.getSessionName());
    }

    public static void enrollService(String studentID, Course course, Session sessionObj) {
        if (course == null) {
            Utils.println("No such course");
            return;
        }

        if (course.getRoster().contains(studentID)) {
            Utils.println(studentID + " is already enrolled");
            return;
        }

        if (course.getWaitlist().contains(studentID)) {
            Utils.println(studentID + " is already waitlisted");
            return;
        }

        if (course.getRoster().size() >= course.getCapacity()) {
            course.addToWaitlist(studentID);
            String message = "WAITLIST " + studentID + " -> " + course.getId();

            sessionObj.getAuditLog().add(message);
            log.add("ACTION", message, sessionObj.getSessionName());

            Utils.println("Added " + studentID + " to waitlist");
        } else {
            course.addToRoster(studentID);
            String message = "ENROLL " + studentID + " -> " + course.getId();

            sessionObj.getAuditLog().add(message);
            log.add("ACTION", message, sessionObj.getSessionName());

            Utils.println("Enrolled " + studentID);
        }
    }

    public static void dropService(String studentID, Course course, Session sessionObj) {
        if (course == null) {
            Utils.println("Course does not exist");
            return;
        }

        if (course.getRoster().remove(studentID)) {
            String message = "DROP " + studentID + " from " + course.getId();

            sessionObj.getAuditLog().add(message);

            log.add("ACTION", message, sessionObj.getSessionName());

            if (!course.getWaitlist().isEmpty()) {
                String promote = course.getWaitlist().remove(0);
                course.addToRoster(promote);

                String promoteMsg = "PROMOTE " + promote + " -> " + course.getId();
                sessionObj.getAuditLog().add(promoteMsg);
                log.add("ACTION", promoteMsg, sessionObj.getSessionName());

                Utils.println("Promoted " + promote + " from waitlist.");
            } else {
                Utils.println("Dropped.");
            }

        } else if (course.getWaitlist().remove(studentID)) {
            String message = "WAITLIST_REMOVE " + studentID + " from " + course.getId();

            sessionObj.getAuditLog().add(message);
            log.add("ACTION", message, sessionObj.getSessionName());

            Utils.println("Removed from waitlist.");
        } else {
            Utils.println("Not enrolled or waitlisted.");
        }
    }
}
