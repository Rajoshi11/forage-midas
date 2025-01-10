package com.jpmc.midascore;

public class Incentive {
    private float amount; 

    // Empty Constructor
    public Incentive() {

    }

    // Paramaterized Constructor
    public Incentive(float amount) {
        this.amount = amount; 
    }

    // Accessors and Mutators
    public float getAmount() {
        return amount; 
    }

    public void setAmount(float amount) {
        this.amount = amount; 
    }

    @Override
    public String toString() {
        return "Incentive: [Amount: ]" + amount + "]";
    }
}
