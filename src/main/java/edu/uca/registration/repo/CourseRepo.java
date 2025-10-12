package edu.uca.registration.repo;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.uca.registration.model.Course;
import java.util.Map;

public class CourseRepo extends JsonRepository<Course> {
    public CourseRepo() {
        super(ConfigManager.getPath("courses"));
    }

    @Override
    protected TypeReference<Map<String, Course>> getTypeReference() {
        return new TypeReference<Map<String, Course>>() {};
    }
}
