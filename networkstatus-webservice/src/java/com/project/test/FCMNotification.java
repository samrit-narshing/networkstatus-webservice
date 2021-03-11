/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minidev.json.JSONObject;

/**
 *
 * @author Samrit
 */
public class FCMNotification {

    // Method to send Notifications from server to client end.
    public final static String AUTH_KEY_FCM = "AAAAauzaZ8s:APA91bH5xw5hCL5TURi-bYLXE70EGjIbxT9YtV_9WhJsurxeOFvUtuJYies7NEmgHB-VDOUEug_SbCZ16ZPu4qGPubSCDfoKmHKxACp3K0nfqQ5W-EjEjx5XKK33RFT7WzvNoOoTiCrO";
    public final static String AUTH_KEY_FCM_2 = "AAAAgqs0zO4:APA91bFLeHhpJkY73c3tsc-4ETeMmH5H2ewAuYUp_1jp5VC_hTbZ4CCTC4XDbbmbsL88WfGBnSZuqt5dJyspNJkcuR4PAFdZ9OBS1ON8gks3O9m4JJar0yD_Ipxy3ot1v0DWr4xD5Prb";
    public final static String AUTH_KEY_FCM_3 = "AAAA3OD6nVM:APA91bF0PgFnYfyGhkqKmu8JniJm81RkGdLRLgUnP97n6p-RDJAoeOd2FvqnuT-jfv6VbqoY7dmAcd43YXaLIYUtuWuGg7IjLGcqyYnHMkiTxXsyZ7TNxuM2Ru1616xT10e0L4J1Bied";

    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public static void pushFCMNotification(String DeviceIdKey) throws Exception {

        String authKey = AUTH_KEY_FCM_3; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject obj = new JSONObject();
        JSONObject msgObject = new JSONObject();
        msgObject.put("title", "Starting Notificatoin Title");
        msgObject.put("body", "Hello First Test notification");
//        msgObject.put("icon", ANDROID_NOTIFICATION_ICON);
//        msgObject.put("color", ANDROID_NOTIFICATION_COLOR);
        msgObject.put("sound", "default");

        JSONObject dataObject = new JSONObject();
        dataObject.put("message", "Second Again Hello First Test notificationnotificationnotification notificationnotification notification notification notification ");
        dataObject.put("image", "https://ibin.co/2t1lLdpfS06F.png");
        dataObject.put("AnotherActivity", "False");

        obj.put("to", DeviceIdKey.trim());
        obj.put("notification", msgObject);
        obj.put("data", dataObject);

        System.out.println(">>>>>>>>>>>>>" + obj.toString());
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(obj.toString());
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

    }

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        String USER_DEVICE_TOKEN = "cOoNcbUJK9A:APA91bGGfprgEU4o32tzoNoQ9hQHTvt9N0agla2zne6kcollnhzbMqZsHarx0TOoGOSkO6HvvKZ8SbwPgcgPwH5AcONMOUFLyzXRVsqA2A-9KRAHpmdL42FKInbj8_PNy38nzDJ5LZvl";
        String USER_DEVICE_TOKEN_2 = "duTl5kkRGKg:APA91bH-cb3PT2UlhaZqJyrxgL1aZGsuOKrlVigZGbvtFaLkSWPDhP7SJ1dJOGo8WHruHQkmrkApcAcO4vGfE-u-grPDgqyqNOJdiUFotNyAasYoY7Q_P0j1fv37MJc-MP_Oep5T-XsI";
        String USER_DEVICE_TOKEN_3 = "eA5RqoPP-3c:APA91bGwWmzG34BIY90q1qcSydKflEcZnf7K4BQuuW_oTviZm6r9KKxhL7RL1v5fzSWxnRAVbotfIrEGX8gkFMCv5htZgGyzfpUxrRh1ObfOG7pDWhwxqbLrINPhL2_fA88KwlyNqJ8Q";
        FCMNotification obj = new FCMNotification();
        obj.pushFCMNotification(USER_DEVICE_TOKEN_3);
    }
}
