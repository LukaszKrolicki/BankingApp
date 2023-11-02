package com.example.demo.Controllers.Admin;

import com.example.demo.Models.Client;
import com.example.demo.Models.Model;
import com.example.demo.Views.ClientCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public TextField pAddress_fld;
    public Button search_btn;
    public ListView result_listview;
    public TextField amount_fld;
    public Button deposit_btn;

    public Client client;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_btn.setOnAction(e->onClientSearch());
        deposit_btn.setOnAction(e->onDeposit());
    }

    private void onClientSearch(){
        ObservableList<Client> searchResults = Model.getInstance().searchClient(pAddress_fld.getText());
        result_listview.setItems(searchResults);
        result_listview.setCellFactory(e-> new ClientCellFactory());
        client= searchResults.get(0);
    }

    private  void onDeposit(){
        double amount = Double.parseDouble(amount_fld.getText());
        double newBalance = amount + client.savingsAccountProperty().get().balanceProperty().get();
        if(amount_fld.getText()!=null){
            Model.getInstance().getDatabaseDriver().depositSavings(client.payeeAdressProperty(), newBalance);
        }
        emptyFileds();
    }

    private  void emptyFileds(){
        pAddress_fld.setText("");
        amount_fld.setText("");
    }
}
