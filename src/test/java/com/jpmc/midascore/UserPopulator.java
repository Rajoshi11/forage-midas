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
                // Split the line into parts
                String[] userData = userLine.split(", ");
                
                // Ensure userData has the expected length
                if (userData.length != 2) {
                    System.err.println("Invalid user line format: " + userLine);
                    continue;
                }

                // Parse and validate data
                String username = userData[0].trim();
                float balance;
                try {
                    balance = Float.parseFloat(userData[1].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid balance for user: " + userLine);
                    continue;
                }

                // Create and save the UserRecord
                UserRecord user = new UserRecord(username, balance);
                databaseConduit.save(user);

            } catch (Exception e) {
                // Log unexpected errors and continue processing
                System.err.println("Error processing user line: " + userLine + " - " + e.getMessage());
            }
        }
    }
}
