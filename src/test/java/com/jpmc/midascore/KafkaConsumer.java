package com.jpmc.midascore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;

@Component
public class KafkaConsumer {
    @Autowired 
    private IncentiveService incentiveService; 

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private TransactionRepository transactionRepository; 

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "${general.kafka-group-id}")
    public void consume(Transaction transaction) {
        System.out.println("Consumed: " + transaction);

        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        // Verify transaction
        if (sender != null && recipient != null && sender.getBalance() >= transaction.getAmount()) {
            Incentive incentive = incentiveService.fetchIncentive(transaction);

            // Adjust Balances
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentive.getAmount());
            userRepository.save(sender);
            userRepository.save(recipient); 
            
            
            TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, transaction.getAmount(), incentive.getAmount());
            transactionRepository.save(transactionRecord);

            System.out.println("Transaction Successful: " 
            + sender.getName() + ": " + sender.getBalance() + " "
            + recipient.getName() + ": " + recipient.getBalance() + " "
            + transaction.getAmount() + " Incentive: " + incentive.getAmount()); 
        }
        else {
            System.out.println("Invalid Transaction: " + transaction);
        }
    }
}
