package com.example.demo.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewClientController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public CheckBox PayeeAddress_box;
    public Label pAddress_lbl;
    public CheckBox chAcc_box;
    public CheckBox sv_Acc_box;
    public TextField ch_amount_fld;
    public TextField svAmount_flx;
    public Button createClient_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
