package com.jpmc.midascore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; 
    
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserRecord sender; 
    
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private UserRecord recipient; 

    private float amount; 

    private float incentive; 
    
    public TransactionRecord() {

    }
    
    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, float incentive) {
        this.sender = sender; 
        this.recipient = recipient; 
        this.amount = amount; 
        this.incentive = incentive; 
    }

    // Accessors
    public Long getId() {
        return id; 
    }

    public UserRecord getSenderId() {
        return sender;
    }

    public UserRecord getRecipientId() {
        return recipient; 
    }

    public float getAmount() {
        return amount; 
    }

    public float getIncentive() {
        return incentive; 
    }

    // Mutators
    public void setId(long id) {
        this.id = id; 
    }

    public void setSenderId(UserRecord sender) {
        this.sender = sender; 
    }

    public void setRecipientId(UserRecord recipient) {
        this.recipient = recipient; 
    }

    public void setAmount(float amount) {
        this.amount = amount; 
    }

    public void setIncentive(float incentive) {
        this.incentive = incentive; 
    }

    @Override
    public String toString() {
        return "TransactionRecord: [id: " + id + ", senderId: " + sender + 
        ", recipientId: " + recipient + ", amount: " +
        amount + "]";  
    }
}
