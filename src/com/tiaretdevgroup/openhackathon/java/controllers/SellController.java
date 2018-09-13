package com.tiaretdevgroup.openhackathon.java.controllers;

import blockchain.chains.MaladyBlockChain;
import blockchain.chains.SalesBlockChain;
import blockchain.factory.BlockchainFactory;
import blockchain.models.Sale;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tiaretdevgroup.openhackathon.java.utils.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

public class SellController implements Initializable {

    // IP address of server
    private final static String HOST = "http://a4fa76c6.ngrok.io";

    // Medicaments
    List<String[]> products;

    @FXML
    private HBox checkPane, sellProductPane;

    @FXML
    private VBox rightCheckPane, rightSellPane;

    @FXML
    private JFXTextField identifierField;
    private JFXSnackbar toastErrorMsgCheckPane, toastErrorMsgProductPane;

    /* Start Sell Pane */

    @FXML
    private JFXComboBox<String> comboMedicament;
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

        if(identifierField.getText() == null || !identifierField.getText().matches("[0-9]")) {
            toastErrorMsgCheckPane.show("Identifier not valid !", 2000);
            return;
        }

        MaladyBlockChain maladyBlockChain = BlockchainFactory.INSTANCE.
                readMaladyBlockChainFromJSONFile(Constants.FILE_MALADIES);


        if(maladyBlockChain.isPatientAMalady(identifierField.getText().trim())) {
            // receive JSON & inserted in views
            getJSONMedicament();
            getJSONProfile();

            checkPane.setVisible(false);
            sellProductPane.setVisible(true);
        } else {
            toastErrorMsgCheckPane.show("You are not sick !", 2000);
        }

        //test code of malady
        if (new MaladyBlockChain().isPatientAMalady("123456")) {
            toastErrorMsgCheckPane.show("Identifier not valid !", 2000);
            return;
        } else {

        }
        initMedicament();


    }

    @FXML
    private void onSell() {
        if (comboMedicament.getSelectionModel().getSelectedItem() == null) {
            toastErrorMsgProductPane.show("Please Select Medicament !", 2000);
            return;
        }

        String medicamentSelected = comboMedicament.getSelectionModel().getSelectedItem().toString();
        String codeSelected = null;

        for(String[] item : products) {
            if(item[0].equals(medicamentSelected)) {
                codeSelected = item[1];
                break;
            }
        }

        SalesBlockChain salesBlockChain = BlockchainFactory.INSTANCE.readSalesBlockChainFromJSONFile(Constants.FILE_SALES);
        salesBlockChain.mineBlock(new Sale(identifierLbl.getText().trim(), codeSelected, "123"));

        BlockchainFactory.INSTANCE.saveBlockChainToJSONFile(salesBlockChain, Constants.FILE_SALES);
    }

    private void initMedicament() {

    }

    private void getJSONMedicament() {
        String urls = HOST + "/api/client/" + identifierField.getText().trim() + "/product";

        String productsJSON = null;
        try {
            HttpResponse<JsonNode> node = Unirest.get(urls).asJson();
            productsJSON = node.getBody().toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject(productsJSON);
        JSONArray arrayData = obj.getJSONArray("data");
        products = new ArrayList<>();
        comboMedicament.getItems().clear();

        for(int i = 0; i < arrayData.length(); i++) {
            JSONObject object = arrayData.getJSONObject(i);

            String[] product = {object.getString("name"), object.getString("code")};
            comboMedicament.getItems().add(product[0]);

            products.add(product);
        }
    }

    private void getJSONProfile() {
        String urls = HOST + "/api/client/" + identifierField.getText().trim()  ;

        String productsJSON = null;
        try {
            HttpResponse<JsonNode> node = Unirest.get(urls).asJson();
            productsJSON = node.getBody().toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject(productsJSON);
        identifierLbl.setText(obj.getString("identifier"));
        firstNameLbl.setText(obj.getString("firstname"));
        lastNameLbl.setText( obj.getString("lastname"));
        typeOfDiseaseLbl.setText(obj.getString("firstname"));
    }

}