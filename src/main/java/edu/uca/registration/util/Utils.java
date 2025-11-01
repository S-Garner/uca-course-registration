package edu.uca.registration.util;

import java.time.LocalDateTime;
import java.util.List;

public class Utils {
    public static void print(String s) {
        System.out.print(s);
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void audit(String ev, List<String> auditLog) {
        auditLog.add(LocalDateTime.now() + " | " + ev);
    }
}
