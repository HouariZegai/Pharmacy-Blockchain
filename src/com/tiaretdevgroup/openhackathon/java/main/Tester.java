package com.tiaretdevgroup.openhackathon.java.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tester {


    public static void main(String[] args) {
        Date date = new Date();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date date2 = new Date();

        System.out.println(date.getTime());
        System.out.println(date2.getTime());

    }

}
