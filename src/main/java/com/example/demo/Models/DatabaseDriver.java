package com.example.demo.Models;

import javafx.scene.chart.PieChart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDriver {
    private Connection conn;

    public DatabaseDriver(Connection conn){
        try{
            this.conn = DriverManager.getConnection("jdbc:sqlite://mazebank.db");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Client Section




    //Admin Section





    //Utility Methods
}
