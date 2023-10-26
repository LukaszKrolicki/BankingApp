package com.example.demo.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_balance;
    public Label checking_acc_num;
    public Label savings_val;
    public Label savings_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView transaction_listview;
    public VBox pay;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
