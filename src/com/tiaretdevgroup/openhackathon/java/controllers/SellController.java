package com.tiaretdevgroup.openhackathon.java.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SellController implements Initializable {
    @FXML
    private HBox checkPane, sellProductPane;
    
    @FXML
    private VBox rightCheckPane, rightSellPane;
    @FXML
    private JFXTextField identifierField;
    
    private JFXSnackbar toastErrorMsgCheckPane, toastErrorMsgProductPane;
    
    /* Start Sell Pane */
    
    @FXML
    private JFXComboBox medicamentField;
    @FXML
    private Label identifierLbl, firstNameLbl, lastNameLbl, typeOfDiseaseLbl;
    
    /* End Sell Pane */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toastErrorMsgCheckPane = new JFXSnackbar(rightCheckPane);
        toastErrorMsgProductPane = new JFXSnackbar(rightSellPane);
    }
    
    @FXML
    public void onCheck() {
        if(identifierField.getText() == null || !identifierField.getText().matches("[0-9]{4,}")) {
            toastErrorMsgCheckPane.show("Identifier not valid !", 2000);
            return;
        }
        
        // Check Valid of Identifier
        
        
        initMedicament();
        checkPane.setVisible(false);
        sellProductPane.setVisible(true);
    }
    
    @FXML
    private void onSell() {
        if(medicamentField.getSelectionModel().getSelectedItem() == null) {
            toastErrorMsgCheckPane.show("Please Select Medicament !", 2000);
            return;
        }
    }
    
    private void initMedicament() {
        
    }
}
