package edu.uca.registration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.uca.registration.model.Course;
import edu.uca.registration.model.Enrollment;
import edu.uca.registration.model.Student;

import java.io.*;
import java.util.*;

public class CsvToJsonConverter {

    private static final String STUDENTS_CSV = "data/students.csv";
    private static final String COURSES_CSV = "data/courses.csv";
    private static final String ENROLLMENTS_CSV = "data/enrollments.csv";

    private static final String STUDENTS_JSON = "data/students.json";
    private static final String COURSES_JSON = "data/courses.json";
    private static final String ENROLLMENTS_JSON = "data/enrollments.json";

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    /** Converts existing CSV files into the new JSON formats. */
    public static void convertAll() throws IOException {
        convertStudents();
        convertCourses();
        convertEnrollments();
    }

    private static void convertStudents() throws IOException {
        Map<String, Student> students = new LinkedHashMap<>();
        File input = new File(STUDENTS_CSV);
        if (!input.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length >= 3) {
                    Student s = new Student(p[0], p[1], p[2]);
                    students.put(p[0], s);
                }
            }
        }

        mapper.writeValue(new File(STUDENTS_JSON), students);
    }

    private static void convertCourses() throws IOException {
        Map<String, Course> courses = new LinkedHashMap<>();
        File input = new File(COURSES_CSV);
        if (!input.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1);
                if (p.length >= 3) {
                    try {
                        int cap = Integer.parseInt(p[2]);
                        Course c = new Course(p[0], p[1], cap);
                        courses.put(p[0], c);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        mapper.writeValue(new File(COURSES_JSON), courses);
    }

    private static void convertEnrollments() throws IOException {
        List<Enrollment> enrollments = new ArrayList<>();
        File input = new File(ENROLLMENTS_CSV);
        if (!input.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|", -1);
                if (p.length >= 3) {
                    enrollments.add(new Enrollment(p[0], p[1], p[2]));
                }
            }
        }

        mapper.writeValue(new File(ENROLLMENTS_JSON), enrollments);
    }
}
