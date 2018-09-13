package com.tiaretdevgroup.openhackathon.java.main;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tester {

    private final static String HOST = "http://a4fa76c6.ngrok.io";

    public static void main(String[] args) {
        String urls = HOST + "/api/client/11/product";

        String productsJSON = null;
        try {
            HttpResponse<JsonNode> node = Unirest.get(urls).asJson();
            productsJSON = node.getBody().toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject(productsJSON);
        JSONArray arrayData = obj.getJSONArray("data");
        List<String[]> products = new ArrayList<>();

        for(int i = 0; i < arrayData.length(); i++) {
            JSONObject object = arrayData.getJSONObject(i);

            String[] product = {object.getString("name"), object.getString("code")};

            products.add(product);
        }

        for(String[] item : products) {
            System.out.println(item[0] + " " + item[1]);
        }
    }

}
