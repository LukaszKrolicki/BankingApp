package com.example.demo.Controllers.Admin;

import com.example.demo.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    BorderPane adminParent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem()
                .addListener(((observableValue, oldVal, newVal) -> {
                            switch (newVal){
                                default -> adminParent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
                            }
                        })
                );
    }
}
