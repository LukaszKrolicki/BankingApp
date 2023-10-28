package com.example.demo.Controllers.Admin;

import com.example.demo.Models.Model;
import com.example.demo.Views.AccountType;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
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

    private String payeeAddress;

    private boolean createCheckingAccountFlag = false;
    private boolean createSavingsAccountFlag = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createClient_btn.setOnAction(e->createClient());
        PayeeAddress_box.selectedProperty().addListener(((observableValue, oldVal, newVal) -> {
            if(newVal){
                payeeAddress = createPayeeAddress();
                onCreatePayeeAddress();
            }
        }));

        chAcc_box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal){
                createCheckingAccountFlag=true;
            }
                }
        );

        sv_Acc_box.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
                    if(newVal){
                        createSavingsAccountFlag=true;
                    }
                }
        );
    }

    private void createClient(){
        if(createCheckingAccountFlag){
            createAccount("Checking");

        }

        if(createSavingsAccountFlag){
            createAccount("Savings");
        }

        //Create Client
        String fname = fName_fld.getText();
        String lname=lName_fld.getText();
        String password= password_fld.getText();
        Model.getInstance().getDatabaseDriver().createClient(fname, lname, payeeAddress, password, LocalDate.now());
        error_lbl.setStyle("fx-text-fill: blue; -fx-font-size:1.3em; -fx-font-weight: bold");
        error_lbl.setText("Client Created Succesfully");
        emptyFields();

    }

    private void createAccount(String accountType){
        double balance = Double.parseDouble(ch_amount_fld.getText());

        //generate Account number
        String firstSection="3201";
        String lastSection = Integer.toString(new Random().nextInt(9999)+1000);
        String accountNumber = firstSection + " " + lastSection;

        //Create Checking or Savings Account
        Model.getInstance().getDatabaseDriver().createCheckingAccount(payeeAddress, accountNumber,
                10,balance);

        if(accountType.equals("Checking")){
            Model.getInstance().getDatabaseDriver().createCheckingAccount(payeeAddress, accountNumber,
                    100,balance);
        }
        else{
            Model.getInstance().getDatabaseDriver().createSavingsAccount(payeeAddress, accountNumber,
                    2000,balance);
        }


    }

    private  void onCreatePayeeAddress(){
        if(fName_fld.getText()!=null & lName_fld.getText()!=null){
            pAddress_lbl.setText(payeeAddress);
        }
    }

    private String createPayeeAddress(){
        int id = Model.getInstance().getDatabaseDriver().getLastClientsId()+1;
        System.out.println(id);
        char fchar = Character.toLowerCase(fName_fld.getText().charAt(0));
        return "@"+fchar+lName_fld.getText()+id;
    }

    private void emptyFields(){
        fName_fld.setText("");
        lName_fld.setText("");
        password_fld.setText("");
        PayeeAddress_box.setSelected(false);
        pAddress_lbl.setText("");
        chAcc_box.setSelected(false);
        ch_amount_fld.setText("");
        sv_Acc_box.setSelected(false);
        svAmount_flx.setText("");

    }

}
