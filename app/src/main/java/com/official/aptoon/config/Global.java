package com.official.aptoon.config;

import com.official.aptoon.entity.Notification;
import com.official.aptoon.entity.Poster;

import java.util.ArrayList;

/**
 * Created by Tamim on 28/09/2017.
 */



public class Global {
//    public static final String API_URL = "http://admin.aptoon.tv/api/";
    public static final String API_URL = "http://192.168.207.182/api/";
    public static final String SECURE_KEY = "4F5A9C3D9A86FA54EACEDDD635185";
    public static final String Youtube_Key = "AIzaSyAephi0fVTEBXgphX7Z_WVSW8iPusDibtg"; // get it from this link  https://console.developers.google.com/apis/credentials

    public static final String ITEM_PURCHASE_CODE = "d506abfd-9fe2-4b71-b979-feff21bcad13";

    public static final String SUBSCRIPTION_ID = "SUBSCRIPTION_ID";
    public static final String MERCHANT_KEY = "MERCHANT_KEY" ; // PUT YOUR MERCHANT KEY HERE;
    public static final long   SUBSCRIPTION_DURATION = 30; // PUT SUBSCRIPTION DURATION DAYS HERE;


    public static ArrayList<Notification> notifications = new ArrayList<Notification>();

    public static Poster next_poster ;
    public static String user_name = "";
    public static String user_image = "";

    public static Integer val_watching = 10;
    public static Integer val_completed = 20;
    public static Integer val_on_hold = 30;
    public static Integer val_dropped = 30;
    public static Integer val_plan_to_watch = 10;
    public static Integer val_days = 25;
    public static Integer val_mean_score = 927;
    public static Integer val_total_entries = 152;
    public static Integer val_rewathced = 3;
    public static Integer val_episodes = 1216;

}



