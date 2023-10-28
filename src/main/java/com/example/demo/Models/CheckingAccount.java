package com.example.demo.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends Account{

    private final IntegerProperty transactionLimit;

    public CheckingAccount(String owner, String accountNumber, Double balance, int tlimit) {
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleIntegerProperty(this,"balance",tlimit);
    }
    public IntegerProperty transactionLimitProperty() {
        return transactionLimit;
    }

}
