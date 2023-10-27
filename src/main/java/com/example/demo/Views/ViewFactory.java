package com.example.demo.Views;

import com.example.demo.Controllers.Admin.AdminController;
import com.example.demo.Controllers.Admin.AdminMenuController;
import com.example.demo.Controllers.Client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    private AccountType loginAccountType;

    //Client Views
    private AnchorPane dashboardView;
    private AnchorPane transactionsView;

    private AnchorPane accountsView;

    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;


    //Admin Views
    private AnchorPane createClientView;

    private AnchorPane createClientssView;

    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;

    public ViewFactory(){
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    //ClientViews Section
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

    public AnchorPane getAccountsViewView() {
        if(accountsView == null){
            try{
                accountsView= new FXMLLoader(getClass().getResource("/Fxml/Client/Accounts.fxml")).load();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
        return accountsView;
    }

    public void showClientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);

    }



    //Admin Views
    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/LoginPage.fxml"));
        createStage(loader);
    }

    public void showAdminWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }

    public AnchorPane getCreateClientView() {
        if(createClientView== null){
            try{
                createClientView= new FXMLLoader(getClass().getResource("/Fxml/Admin/CreateNewClient.fxml")).load();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
        return createClientView;
    }

    public AnchorPane getClientssView() {
        if(createClientssView == null){
            try{
                createClientssView =new FXMLLoader(getClass().getResource("/Fxml/Admin/Clients.fxml")).load();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
        return createClientssView;
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
