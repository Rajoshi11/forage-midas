package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private final String topic;
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public KafkaProducer(@Value("${general.kafka-topic}") String topic, KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String transactionLine) {
        try {
            // Sanitize input by removing extra spaces and newlines
            transactionLine = transactionLine.replace("\n", "").trim();

            // Split and parse the input
            String[] transactionData = transactionLine.split(", ");
            if (transactionData.length != 3) {
                throw new IllegalArgumentException("Invalid transaction format: " + transactionLine);
            }

            // Create and send a transaction
            long senderId = Long.parseLong(transactionData[0].trim());
            long recipientId = Long.parseLong(transactionData[1].trim());
            float amount = Float.parseFloat(transactionData[2].trim());
            kafkaTemplate.send(topic, new Transaction(senderId, recipientId, amount));
        } catch (NumberFormatException e) {
            System.err.println("Error parsing transaction line: " + transactionLine + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error while processing transaction line: " + e.getMessage());
        }
    }
}
