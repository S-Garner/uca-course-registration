package edu.uca.registration.repo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigManager {
    private static String CONFIG_PATH = "data/config.json";
    private static final Map<String, String> paths = new HashMap<>();

    static {
        loadConfig();
    }

    public static void setConfigPath(String path) {
        CONFIG_PATH = path;
        loadConfig();
    }

    private static void loadConfig() {
        File file = new File(CONFIG_PATH);
        if (!file.exists()) {
            System.err.println("Config file not found: " + file.getAbsolutePath());
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(file);

            if (root.has("students"))
                paths.put("students", root.get("students").get("filename").asText());
            if (root.has("courses"))
                paths.put("courses", root.get("courses").get("filename").asText());
            if (root.has("enrollments"))
                paths.put("enrollments", root.get("enrollments").get("filename").asText());

        } catch (IOException e) {
            System.err.println("Failed to read config.json: " + e.getMessage());
        }
    }

    public static String getPath(String key) {
        return paths.get(key);
    }
}
