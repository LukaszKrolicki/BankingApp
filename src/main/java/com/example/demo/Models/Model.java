package com.example.demo.Models;

import com.example.demo.Views.AccountType;
import com.example.demo.Views.ViewFactory;

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

    private Model() {

        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        //Client data section
        this.clientLoginSuccessFlag=false;
        this.client = new Client("", "", "", null, null, null);


        //Admin data section
        this.adminLoginSuccessFlag=false;
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
                this.client.payeeAdressProperty(resultSet.getString("PayeeAddress"));
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]),
                        Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                this.client.dateCreatedProperty().set(date);
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
}
