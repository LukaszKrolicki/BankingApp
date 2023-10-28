package com.example.demo.Controllers.Admin;

import com.example.demo.Models.Model;
import com.example.demo.Views.AdminMenuOptions;
import com.example.demo.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button create_client_btn;
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        create_client_btn.setOnAction(e->onMenuClick(AdminMenuOptions.CREATE_CLIENT));
        clients_btn.setOnAction(e->onMenuClick(AdminMenuOptions.CLIENTS));
        deposit_btn.setOnAction(e->onMenuClick(AdminMenuOptions.DEPOSIT));
        logout_btn.setOnAction(e->onLogout());
    }

    private void onMenuClick(Enum setName){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set((AdminMenuOptions) setName);
    }

    private void onLogout(){
        Stage stage = (Stage) clients_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();

        //setting flags
        Model.getInstance().setAdminLoginSuccessFlag(false);

    }
}
