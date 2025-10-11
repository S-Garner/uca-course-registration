package edu.uca.registration.repo;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uca.registration.model.Registrable;

public interface Repository<T extends Registrable> {
    Map<String, T> load();
    void save(Map<String, T> data);
    void setFilePath(String stringPath);
    String getFilePath();
}
