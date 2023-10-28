package com.example.demo.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAdress;

    private final ObjectProperty<Account> checkingAccount;

    private final ObjectProperty<Account> savingsAccount;

    private final ObjectProperty<LocalDate> dateCreated;

    public Client(String firstName, String lastName, String payeeAdress, Account checkingAccount, Account savingsAccount, LocalDate dateCreated) {
        this.firstName = new SimpleStringProperty(this, "firstName", firstName);
        this.lastName = new SimpleStringProperty(this, "lastName", lastName);
        this.payeeAdress = new SimpleStringProperty(this, "payeeAdress", payeeAdress);
        this.checkingAccount = new SimpleObjectProperty(this, "checkingAccount", checkingAccount);
        this.savingsAccount = new SimpleObjectProperty(this, "savingsAccount", savingsAccount);
        this.dateCreated = new SimpleObjectProperty(this, "dateCreated", dateCreated);
    }



    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty payeeAdressProperty() {
        return payeeAdress;
    }

    public ObjectProperty<Account> checkingAccountProperty() {
        return checkingAccount;
    }

    public ObjectProperty<Account> savingsAccountProperty() {
        return savingsAccount;
    }

    public ObjectProperty<LocalDate> dateCreatedProperty() {
        return dateCreated;
    }
}
