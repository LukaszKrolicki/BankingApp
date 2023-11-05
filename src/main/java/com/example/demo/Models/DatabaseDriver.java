package com.example.demo.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {
    private Connection conn;


    public DatabaseDriver(){
        try{
            this.conn = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Client Section

    public ResultSet getClientData(String pAddress, String password){
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = this.conn.createStatement();
            resultSet=statement.executeQuery(
                    ("SELECT * FROM Clients WHERE PayeeAddress='"+pAddress+"' AND Password='"+password+"';")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }




    //Admin Section

    public ResultSet getAdminData(String username, String password){
        Statement statement;
        ResultSet resultSet= null;

        try{
            statement=this.conn.createStatement();
            resultSet=statement.executeQuery(
                    "SELECT * FROM Admins WHERE Username='"+username+"' AND Password='"+password+"';");

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void createClient(String fname, String lName, String pAddress, String password, LocalDate date){
        Statement statement;

        try{
            statement=this.conn.createStatement();
            statement.executeUpdate(
                    "INSERT INTO CLIENTS(FirstName,LastName,PayeeAddress, Password, Date)"+
                            "VALUES('"+fname+"','"+lName+"','"+pAddress+"','"+password+"','"+date.toString()+"');"
            );
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCheckingAccount(String owner, String number, double tLimit, double balance){
        Statement statement;

        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    "CheckingAccounts(Owner,AccountNumber,TransactionLimit,Balance)"+
                    " VALUES('"+owner+"','"+number+"','"+tLimit+"','"+balance+"');");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSavingsAccount(String owner, String number, double wLimit, double balance){
        Statement statement;

        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("INSERT INTO "+
                    "SavingsAccounts(Owner,AccountNumber,WithdrawalLimit,Balance)"+
                    " VALUES('"+owner+"','"+number+"','"+wLimit+"','"+balance+"');");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getTransactions(String pAddress, int limit){
        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Transactions WHERE Sender='"+pAddress+"' OR Receiver='"+pAddress+"' LIMIT '"+limit+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public double getSavingsAccountBalance(String pAddress){
        Statement statement;
        ResultSet resultSet;
        double balance=0;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("Select * FROM SavingsAccounts WHERE Owner='"+pAddress+"';");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return balance;
    }
    public void updateBalance(String pAddress, double amount, String operation){
        Statement statement;
        ResultSet resultSet;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+pAddress+"';");
            double newBalance;
            if(operation.equals("ADD")){
                newBalance = resultSet.getDouble("Balance")+amount;
                statement.executeUpdate("UPDATE SavingsAccounts SET Balance='"+newBalance+"' WHERE Owner ='"+pAddress+"';");

            }
            else{
                if(resultSet.getDouble("Balance")>= amount){
                    newBalance = resultSet.getDouble("Balance")-amount;
                    statement.executeUpdate("UPDATE SavingsAccounts SET Balance='"+newBalance+"' WHERE Owner ='"+pAddress+"';");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void  CreateNewTransaction(String sender, String receiver, double amount, String message){
        Statement statement;
        ResultSet resultSet;

        try{
            statement = this.conn.createStatement();
            LocalDate date= LocalDate.now();
            statement.executeUpdate("INSERT INTO Transactions(Sender,Receiver,Amount,Date, Message) VALUES ('"+sender+"', '"+receiver+"', '"+amount+"', '"+date+"', '"+message+"');");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    //Utility Methods
    public int  getLastClientsId(){
        Statement statement;
        ResultSet resultSet;
        int id=0;

        try{
            statement= this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Clients';");
            id = resultSet.getInt("seq");
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return id;
    }

    public ResultSet getAllClientsData(){
        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet= statement.executeQuery("SELECT * FROM Clients;");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getCheckingAccountData(String pAddress){
        Statement statement;
        ResultSet resultSet=null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("Select * FROM CheckingAccounts WHERE Owner='"+pAddress+"';");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet getSavingsAccountData(String pAddress){
        Statement statement;
        ResultSet resultSet=null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("Select * FROM SavingsAccounts WHERE Owner='"+pAddress+"';");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public ResultSet searchClient(String pAddress){
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement=this.conn.createStatement();
            resultSet = statement.executeQuery("Select * FROM Clients WHERE PayeeAddress='"+pAddress+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public void depositSavings(String pAddress, double amount){
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE SavingsAccounts SET Balance = '"+amount+"' WHERE Address='"+pAddress+"';");

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

}
