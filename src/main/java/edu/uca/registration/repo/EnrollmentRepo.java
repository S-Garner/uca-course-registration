package edu.uca.registration.repo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uca.registration.model.Course;
import edu.uca.registration.model.Enrollment;
import edu.uca.registration.model.Session;

public class EnrollmentRepo {
    private static String FILE_PATH = "data/enrollments.json";

    private static final ObjectMapper mapper = new ObjectMapper();

    public EnrollmentRepo() {};

    public EnrollmentRepo(String filePath) {
        this.FILE_PATH = filePath;
    }

    public void setFilePath(String path) {
        this.FILE_PATH = path;
    }

    public String getFilePath() {
        return FILE_PATH;
    }

    public void load(Session session) {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try {
            List<Enrollment> enrollments =
                mapper.readValue(file, new TypeReference<List<Enrollment>>() {});

            Map<String, Course> courses = session.getCourses();
            // clear existing lists to avoid duplicate accumulation
            for (Course c : courses.values()) {
                c.getRoster().clear();
                c.getWaitlist().clear();
            }

            for (Enrollment e : enrollments) {
                Course c = courses.get(e.getCourseCode());
                if (c == null) continue;

                if ("ENROLLED".equalsIgnoreCase(e.getStatus())) {
                    c.addToRoster(e.getStudentId());
                } else if ("WAITLIST".equalsIgnoreCase(e.getStatus())) {
                    c.addToWaitlist(e.getStudentId());
                }
            }
            session.getAuditLog().add("LOAD enrollments");
        } catch (Exception e) {
            System.err.println("Failed to load enrollments: " + e.getMessage());
        }
    }

    public void save(Session session) {
        List<Enrollment> enrollments = new ArrayList<>();

        for (Course c : session.getCourses().values()) {
            for (String sid : c.getRoster()) {
                enrollments.add(new Enrollment(c.getId(), sid, "ENROLLED"));
            }
            for (String sid : c.getWaitlist()) {
                enrollments.add(new Enrollment(c.getId(), sid, "WAITLIST"));
            }
        }

        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(FILE_PATH), enrollments);
        } catch (Exception e) {
            System.err.println("Failed to save enrollments: " + e.getMessage());
        }
    }
}
