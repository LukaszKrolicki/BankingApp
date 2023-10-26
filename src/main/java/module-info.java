module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.Controllers;
    exports com.example.demo.Controllers.Admin;
    exports com.example.demo.Controllers.Client;
    exports com.example.demo.Models;
    exports com.example.demo.Views;

}