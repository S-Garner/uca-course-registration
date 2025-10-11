package edu.uca.registration.app;

import edu.uca.registration.model.Session;
import edu.uca.registration.repo.DemoRepo;
import edu.uca.registration.util.Transaction;
import edu.uca.registration.util.Utils;

public class Demo {
    private static DemoRepo demoRepo = new DemoRepo();

    public static void seedDemoData(Session sessionObj, boolean demo) {
        if (demo) {
            demoRepo.setDemo(sessionObj);
            Utils.audit("SEED demo data", sessionObj.getAuditLog());
            Utils.println("USING DEMO PARAMETERS");
        } else {
            Transaction.load(sessionObj);
        }
    }
}
