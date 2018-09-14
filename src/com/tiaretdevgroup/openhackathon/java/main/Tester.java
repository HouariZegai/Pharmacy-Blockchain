package com.tiaretdevgroup.openhackathon.java.main;

import blockchain.factory.BlockchainFactory;
import blockchain.models.Sale;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tiaretdevgroup.openhackathon.java.blockchain.chains.SalesBlockChain;
import com.tiaretdevgroup.openhackathon.java.models.Disease;
import com.tiaretdevgroup.openhackathon.java.utils.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tester {


    public static void main(String[] args) {
        SalesBlockChain chain = BlockchainFactory.INSTANCE.readSalesBlockChainFromJSONFile();

        List<Sale> sales = chain.getSales();
        JSONArray array = new JSONArray();
        for (Sale sale : sales) {
            JSONObject object = new JSONObject();
            object.put("client_id", sale.getIdPatient())
                    .put("product_id", sale.getProductId());
            array.put(object);
        }


        try {
            String url = Constants.SALES;

            HttpResponse<JsonNode> call = Unirest.post(url).field("q", array.toString())
                    .asJson();

            JSONArray jsonArray = new JSONArray(call.getBody().toString());
            List<Disease> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt("identifier");
                String fName = object.getString("firstname");
                String lName = object.getString("lastname");
                int category = object.getInt("category_id");
                String date = object.getString("humanDate");
                String product = object.getString("name");
                System.out.println(new Disease(id, fName, lName, product, String.valueOf(category), date).toString());
                list.add(new Disease(id, fName, lName, product, String.valueOf(category), date));
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }

}
