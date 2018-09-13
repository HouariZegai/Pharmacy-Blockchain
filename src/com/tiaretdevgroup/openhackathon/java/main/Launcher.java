package com.tiaretdevgroup.openhackathon.java.main;

import blockchain.chains.MaladyBlockChain;
import blockchain.chains.SalesBlockChain;
import blockchain.factory.BlockchainFactory;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tiaretdevgroup.openhackathon.java.utils.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/tiaretdevgroup/openhackathon/resources/views/System.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        File file = new File(Constants.BASE_FOLDER);
        if (!file.exists()) {
            boolean bool = file.mkdir();

            String productsJSON;
            if (!new File(Constants.FILE_MALADIES).exists()) {

                try {
                    HttpResponse<JsonNode> node = Unirest.get(Constants.CLIENT_URL).asJson();
                    productsJSON = node.getBody().toString();
                    MaladyBlockChain chain = BlockchainFactory.INSTANCE.readMaladyBlockChainFromJSONString(productsJSON);
                    BlockchainFactory.INSTANCE.saveBlockChainToJSONFile(chain, Constants.FILE_MALADIES);
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }


            if (!new File(Constants.FILE_SALES).exists()) {
                try {
                    HttpResponse<JsonNode> node = Unirest.get(Constants.SALES_URL).asJson();
                    productsJSON = node.getBody().toString();
                    SalesBlockChain chain = BlockchainFactory.INSTANCE.readSalesBlockChainFromJSONString(productsJSON);
                    BlockchainFactory.INSTANCE.saveBlockChainToJSONFile(chain, Constants.FILE_SALES);
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
        }

        launch(args);
    }

}