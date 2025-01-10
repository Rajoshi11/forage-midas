package com.jpmc.midascore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.foundation.Transaction;

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
            // Normalize and split transaction line using a comma delimiter
            String[] transactionData = transactionLine.trim().split(",");
            
            // Validate and parse transaction data
            if (transactionData.length == 3) {
                long senderId = Long.parseLong(transactionData[0].trim());
                long receiverId = Long.parseLong(transactionData[1].trim());
                float amount = Float.parseFloat(transactionData[2].trim());
                kafkaTemplate.send(topic, new Transaction(senderId, receiverId, amount));
            } else {
                System.err.println("Malformed transaction line (skipping): " + transactionLine);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid transaction data in line: " + transactionLine);
            e.printStackTrace();
        }
    }
}
