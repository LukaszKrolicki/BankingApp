package com.example.demo.Models;

import javafx.scene.chart.PieChart;

import java.sql.*;

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





    //Utility Methods
}
