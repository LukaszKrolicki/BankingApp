package com.example.demo.Controllers.Client;

import com.example.demo.Models.Model;
import com.example.demo.Models.Transaction;
import com.example.demo.Views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        bindData();
        initTransactionsList();
        transaction_listview.setItems(Model.getInstance().getLatestTransactions());
        transaction_listview.setCellFactory(e->new TransactionCellFactory());
        send_money_btn.setOnAction(e->onSendMoney());
        accountSummary();
    }

    private void bindData(){
        user_name.textProperty().bind(Bindings.concat("HI, ").concat(Model.getInstance().getClient().firstNameProperty()));
        login_date.setText("Today, "+ LocalDate.now());
        checking_balance.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString());
        checking_acc_num.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().accountNumberProperty());
        savings_val.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
        savings_acc_num.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty());


    }

    private void initTransactionsList(){
        if(Model.getInstance().getLatestTransactions().isEmpty()){
            Model.getInstance().setLatestTransactions();
        }
    }

    private void onSendMoney(){
        String receiver = payee_fld.getText();
        double amount = Double.parseDouble(amount_fld.getText());
        String message = message_fld.getText();
        String sender = Model.getInstance().getClient().payeeAdressProperty().get();
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().searchClient(receiver);

        try{
            if(resultSet.isBeforeFirst()){
                Model.getInstance().getDatabaseDriver().updateBalance(receiver, amount, "ADD");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Model.getInstance().getDatabaseDriver().updateBalance(sender, amount, "SUB");
        Model.getInstance().getClient().savingsAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(sender));
        Model.getInstance().getDatabaseDriver().CreateNewTransaction(sender,receiver,amount,message);
        payee_fld.setText("");
        message_fld.setText("");
        amount_fld.setText("");
    }

    private void accountSummary(){
        double income =0;
        double expenses = 0;
        if(Model.getInstance().getAllTransactions().isEmpty()){
            Model.getInstance().setAllTransactions();
            for(Transaction transaction: Model.getInstance().getAllTransactions()){
                if(transaction.senderProperty().get().equals(Model.getInstance().getClient().payeeAdressProperty().get())){
                    expenses = expenses+transaction.amountProperty().get();
                }
                else{
                    income = income + transaction.amountProperty().get();
                }
            }

            income_lbl.setText("$" + income);
            expense_lbl.setText("$" + expenses);

        }
    }
}
