package com.example.demo.Models;

import com.example.demo.Views.AccountType;
import com.example.demo.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;

    private final DatabaseDriver databaseDriver;

    //Client data section
    private Client client;
    private boolean clientLoginSuccessFlag;

    //Admin Data section

    private boolean adminLoginSuccessFlag;

    private final ObservableList<Client> clients;

    private Model() {

        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        //Client data section
        this.clientLoginSuccessFlag=false;
        this.client = new Client("", "", "", null, null, null);


        //Admin data section
        this.adminLoginSuccessFlag=false;
        this.clients= FXCollections.observableArrayList();
    }

    public static synchronized Model getInstance(){
        if(model == null){
            model = new Model();
        }

        return model;
    }

    public ViewFactory getViewFactory(){
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    //Client method section
    public boolean getClientLoginSuccessFlag(){
        return this.clientLoginSuccessFlag;
    }

    public void setClientLoginSuccessFlag(boolean flag){
        this.clientLoginSuccessFlag=flag;
    }

    public Client getClient(){
        return client;
    }

    public void evaluateClientCred(String pAddress, String password){
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = databaseDriver.getClientData(pAddress,password);

        try{
            if(resultSet.isBeforeFirst()){ //if there is fetched data
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.setPayeeAdress(resultSet.getString("PayeeAddress"));
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]),
                        Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                this.client.dateCreatedProperty().set(date);
                checkingAccount = getCheckingAccount(pAddress);
                savingsAccount = getSavingsAccount(pAddress);
                this.client.checkingAccountProperty().set(checkingAccount);
                this.client.savingsAccountProperty().set(savingsAccount);
                this.clientLoginSuccessFlag = true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Admin method section
    public boolean getAdminLoginSuccessFlag(){
        return this.adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag){
        this.adminLoginSuccessFlag=adminLoginSuccessFlag;
    }

    public void evaluateAdminCre(String username, String password){
        ResultSet resultSet = databaseDriver.getAdminData(username, password);
        try{
            if(resultSet.isBeforeFirst()){
                this.adminLoginSuccessFlag=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public void setClient() {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = databaseDriver.getAllClientsData();

        try{
            while(resultSet.next()){
                String fname = resultSet.getString("FirstName");
                String lname = resultSet.getString("LastName");
                String pAddress= resultSet.getString("PayeeAddress");
                String[] dataParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dataParts[0]), Integer.parseInt(dataParts[1]), Integer.parseInt(dataParts[2]));
                checkingAccount = getCheckingAccount(pAddress);
                savingsAccount = getSavingsAccount(pAddress);
                clients.add(new Client(fname,lname,pAddress,checkingAccount,savingsAccount,date));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }




    //Utility methods section

    public CheckingAccount getCheckingAccount(String pAddress){
        CheckingAccount account = null;
        ResultSet resultSet = databaseDriver.getCheckingAccountData(pAddress);

        try {
            String num = resultSet.getString("AccountNumber");
            int tLimit = (int) resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            account = new CheckingAccount(pAddress, num, balance, tLimit);
        }catch (Exception e){
            e.printStackTrace();
        }

        return account;
    }

    public SavingsAccount getSavingsAccount(String pAddress){
        SavingsAccount account = null;
        ResultSet resultSet = databaseDriver.getSavingsAccountData(pAddress);

        try {
            String num = resultSet.getString("AccountNumber");
            double wLimit =  resultSet.getDouble("WithdrawalLimit");
            double balance = resultSet.getDouble("Balance");
            account = new SavingsAccount(pAddress, num, balance, wLimit);
        }catch (Exception e){
            e.printStackTrace();
        }

        return account;
    }
}
