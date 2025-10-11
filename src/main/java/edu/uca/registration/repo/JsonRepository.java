package edu.uca.registration.repo;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uca.registration.model.Registrable;

public abstract class JsonRepository<T extends Registrable> 
implements Repository<T> {
    private String filePath;
    protected final ObjectMapper mapper = new ObjectMapper();

    protected abstract TypeReference<Map<String, T>> getTypeReference();

    protected JsonRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public Map<String, T> load() {
        File file = new File(filePath);

        if (!file.exists()) {
            return new LinkedHashMap<>();
        }

        try {
            return mapper.readValue(file, getTypeReference());
        } catch (Exception e) {
            System.err.println("Could not load " + filePath + ": " + e.getMessage());
            return new LinkedHashMap<>();
        }
    }

    @Override
    public void save(Map<String, T> data) {
        try {
            mapper.writerWithDefaultPrettyPrinter()
            .writeValue(new File(filePath), data);
        } catch (IOException e) {
            System.err.println("Failed to save to " + filePath + ": " + e.getMessage());
        }
    }
} 