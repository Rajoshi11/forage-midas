package com.jpmc.midascore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;

@Component
public class UserPopulator {
    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private DatabaseConduit databaseConduit;

    public void populate() {
        String[] userLines = fileLoader.loadStrings("/test_data/lkjhgfdsa.hjkl");
        for (String userLine : userLines) {
            try {
                // Normalize and split line using a simple comma delimiter
                String[] userData = userLine.trim().split(",");
                
                // Validate correct format
                if (userData.length == 2) {
                    String username = userData[0].trim();
                    float balance = Float.parseFloat(userData[1].trim());
                    UserRecord user = new UserRecord(username, balance);
                    databaseConduit.save(user);
                } else {
                    System.err.println("Malformed line (skipping): " + userLine);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid balance value in line: " + userLine);
                e.printStackTrace();
            }
        }
    }
}
