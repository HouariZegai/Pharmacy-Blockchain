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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {
    private final static String HOST = "http://a4fa76c6.ngrok.io";

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/tiaretdevgroup/openhackathon/resources/views/System.fxml"));
        
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        File file = new File("C:\\App");
        if(!file.exists()) {
            file.mkdir();
            // Get The main block chain
            String urls = HOST + "/api/block/client";

            String productsJSON = null;
            try {
                HttpResponse<JsonNode> node = Unirest.get(urls).asJson();
                productsJSON = node.getBody().toString();
                MaladyBlockChain chain = BlockchainFactory.INSTANCE.readMaladyBlockChainFromJSONString(productsJSON);
                BlockchainFactory.INSTANCE.saveBlockChainToJSONFile(chain, Constants.FILE_MALADIES);
            } catch (UnirestException e) {
                e.printStackTrace();
            }

            urls = HOST + "/api/block/sale";

            productsJSON = null;
            try {
                HttpResponse<JsonNode> node = Unirest.get(urls).asJson();
                productsJSON = node.getBody().toString();
                SalesBlockChain chain = BlockchainFactory.INSTANCE.readSalesBlockChainFromJSONString(productsJSON);
                BlockchainFactory.INSTANCE.saveBlockChainToJSONFile(chain,"C:\\App\\sales.json");
            } catch (UnirestException e) {
                e.printStackTrace();
            }



        }

        launch(args);
    }

}