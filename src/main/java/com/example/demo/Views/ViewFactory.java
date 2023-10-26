package com.example.demo.Views;

import com.example.demo.Controllers.Client.ClientController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    //Client Views
    private AnchorPane dashboardView;
    private AnchorPane transactionsView;

    private final StringProperty clientSelectedMenuItem;

    public ViewFactory(){
        this.clientSelectedMenuItem = new SimpleStringProperty("");
    }


    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public StringProperty clientSelectedMenuItemProperty() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView(){
        if(dashboardView == null){
            try{
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
        return dashboardView;
    }

    public AnchorPane getTransactionsView() {
        if(transactionsView == null){
            try{
                transactionsView= new FXMLLoader(getClass().getResource("/Fxml/Client/Transactions.fxml")).load();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
        return transactionsView;
    }

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/LoginPage.fxml"));
        createStage(loader);
    }

    public void showClientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);

    }

    private static void createStage(FXMLLoader loader) {
        Scene scene = null;

        try{
            scene = new Scene(loader.load());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Kroli Bank");
        stage.show();
    }



    public void closeStage(Stage stage){
        stage.close();
    }
}
