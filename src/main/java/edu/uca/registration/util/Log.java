package edu.uca.registration.util;

import edu.uca.registration.model.Session;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Log {
    private static final String DEFAULT_LOG_PATH = "data/audit.log";
    private final String filePath;
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Log() {
        this.filePath = DEFAULT_LOG_PATH;
    }

    public Log(String filePath) {
        this.filePath = filePath;
    }

    public void write(Session session) {
        write(session.getAuditLog(), session.getSessionName());
    }

    public void write(List<String> entries, String sessionName) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            for (String entry : entries) {
                String line = String.format("[%s] %s %s",
                        LocalDateTime.now().format(formatter),
                        sessionName,
                        entry);
                pw.println(line);
            }
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }

    public void add(String action, String message, String sessionName) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        String entry = String.format("[%s] %s %s %s",
                LocalDateTime.now().format(formatter),
                sessionName,
                action,
                message != null ? message : "");

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            pw.println(entry);
        } catch (IOException e) {
            System.err.println("Failed to append to log: " + e.getMessage());
        }
    }
}
