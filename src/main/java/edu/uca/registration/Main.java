package edu.uca.registration;

import java.io.*;
import edu.uca.registration.util.Log;
import edu.uca.registration.model.Session;
import edu.uca.registration.app.Demo;
import edu.uca.registration.app.Menu;
import edu.uca.registration.util.*;

public class Main {
    static Session session;

    public static void main(String[] args) throws IOException {
        session = new Session();

        CsvToJsonConverter.convertAll();

        Log log = new Log();

        boolean demo = args.length > 0 && "--demo".equalsIgnoreCase(args[0]);

        Demo.seedDemoData(session, demo);

        Utils.println("\n=== UCA Course Registration ===\n");

        Transaction.load(session);

        Menu.menuLoop(session);

        Transaction.save(session);

        log.write(session);

        Utils.println("Goodbye!");
    }
}
