package com.tiaretdevgroup.openhackathon.java.main;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;

public class Tester {


    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            HttpResponse<JsonNode> v = Unirest.post("http://a4fa76c6.ngrok.io/api/pharmacy")
                    .field("code", "1234")
                    .field("ip", inetAddress.getHostAddress())
                    .asJson();
            JSONObject object = new JSONObject(v.getBody().toString());

            File file = new File("C:\\App\\token.json");
            FileOutputStream out = new FileOutputStream(file);
            out.write(object.toString().getBytes());
        } catch (UnirestException | IOException e) {
            e.printStackTrace();
        }
    }

}
