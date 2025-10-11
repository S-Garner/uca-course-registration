package edu.uca.registration.repo;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.uca.registration.model.Student;
import java.util.Map;

public class StudentRepo extends JsonRepository<Student> {
    public StudentRepo() {
        super("data/students.json");
    }

    @Override
    protected TypeReference<Map<String, Student>> getTypeReference() {
        return new TypeReference<Map<String, Student>>() {};
    }
}
