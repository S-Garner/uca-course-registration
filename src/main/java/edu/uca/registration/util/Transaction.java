package edu.uca.registration.util;

import edu.uca.registration.model.Session;
import edu.uca.registration.repo.CourseRepo;
import edu.uca.registration.repo.EnrollmentRepo;
import edu.uca.registration.repo.StudentRepo;

public class Transaction {
    private static CourseRepo courseRepo = new CourseRepo();
    private static StudentRepo studentRepo = new StudentRepo();
    private static EnrollmentRepo enrollmentRepo = new EnrollmentRepo();

    public static void load(Session sessionObj) {
        sessionObj.setStudents(studentRepo.load());
        sessionObj.setCourses(courseRepo.load());

        enrollmentRepo.load(sessionObj);
    }

    public static void save(Session sessionObj) {
        studentRepo.save(sessionObj.getStudents());
        courseRepo.save(sessionObj.getCourses());
        enrollmentRepo.save(sessionObj);
    }
}
