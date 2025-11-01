package edu.uca.registration.repo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class ConfigManagerTest {
    private static final String TEST_CONFIG_PATH = "data/test/config.json";
    private String content;

    @BeforeEach
    void backupOriginalConfig() throws IOException{
        // Backup original config content if needed
        Path path = Path.of(TEST_CONFIG_PATH);
        if (Files.exists(path)) {
            content = Files.readString(path);
        } else {
            content = null;
        }
    }

    @AfterEach
    void restoreOriginalConfig() throws IOException {
        // Restore original config content
        Path path = Path.of(TEST_CONFIG_PATH);
        if (content != null) {
            Files.writeString(path, content);
        } else {
            Files.deleteIfExists(path);
        }
    }

    @Test
    void testGetPathExistingKey() throws IOException {
        String testJson = """
        {
            "students": { "filename": "data/test/students.json" },
            "courses": { "filename": "data/test/courses.json" },
            "enrollments": { "filename": "data/test/enrollments.json" }
        }
        """;

        Files.createDirectories(Path.of("data/test"));
        Files.writeString(Path.of("data/test/config.json"), testJson);

        ConfigManager.setConfigPath("data/test/config.json");

        assertEquals("data/test/students.json", ConfigManager.getPath("students"));
        assertEquals("data/test/courses.json", ConfigManager.getPath("courses"));
        assertEquals("data/test/enrollments.json", ConfigManager.getPath("enrollments"));
    }

    @Test
    void missingFileDoesNotThrow() throws IOException {
        // Delete config file if exists
        Path path = Path.of(TEST_CONFIG_PATH);
        Files.deleteIfExists(path);

        // Should not throw any exception
        assertDoesNotThrow(() -> {
            ConfigManager.getPath("students");
        });
    }
}