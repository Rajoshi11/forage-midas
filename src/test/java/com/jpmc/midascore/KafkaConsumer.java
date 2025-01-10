package com.jpmc.midascore;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.foundation.Transaction;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "${general.kafka-topic}", groupId = "${general.kafka-group-id}")
    public void consume(Transaction transaction) {
        System.out.println("Consumed: " + transaction);
    }
}