/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

/**
 *
 * @author samri
 */
public class FCM_RealtimeDBUtil {

//    final private String firebaseDBURL = "https://semms-a2748.firebaseio.com/locations";
    private String firebaseCoreIndexURL = "";
    private String firebaseServiceTokenFilePath = "";
    private final int expiredMinutes = 10;

    public static void main(String[] args) {
        try {
            new FCM_RealtimeDBUtil("https://semms-a2748.firebaseio.com/locations", "D:/tempfile/conf/firebase/ServiceAccountKey_SEEMS.json").processCleaningTask();
        } catch (Exception e) {

        }
    }

    public FCM_RealtimeDBUtil(String firebaseCoreIndexURL, String firebaseServiceTokenFilePath) {
        this.firebaseCoreIndexURL = firebaseCoreIndexURL;
        this.firebaseServiceTokenFilePath = firebaseServiceTokenFilePath;
    }

    public void processCleaningTask() throws Exception {

        String authToken = getFirebaseAuthToken();

        //Test Write To Index
//            writeIntoIndex(authToken, "Test");
        String readWholeJsonStringOfIndexFromServer = readWholeIndexInJSONFormatFromFirebaseServer(authToken);

        List<String> timeOutKeys = getKeyNamesOfTimeoutRecordsByParsingWholeJsonStringOfIndex(readWholeJsonStringOfIndexFromServer);

        for (String timeOutKey : timeOutKeys) {
            System.out.println("Processing For Time-Out Key : " + timeOutKey);

            deleteTimeoutRecordByKeyFromServer(authToken, timeOutKey);
            System.out.println("Processing For Time-Out Key : " + timeOutKey + " Complete.");
        }

    }

    private void writeIntoIndex(String authToken, String indexName) {
        try {
            System.out.println("Writing.....");
            String url = firebaseCoreIndexURL + "/" + indexName + ".json" + "?access_token=" + authToken;

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            conn.setRequestMethod("PUT");

//            String userpass = "user" + ":" + "pass";
//            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
//            conn.setRequestProperty("Authorization", basicAuth);
//            String data = "{\"accuracy\":\"testname\",\"time\":\"valuedsdfs\"}";
            String data = "{\"accuracy\":541923,\"time\":523232}";
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(data);
            out.close();

            new InputStreamReader(conn.getInputStream());

            System.out.println("Writing Complete.....");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String readWholeIndexInJSONFormatFromFirebaseServer(String authToken) {
        try {
            System.out.println("Reading From Firebase Readtime Databbase.....");
            String url = firebaseCoreIndexURL + "/" + ".json" + "?access_token=" + authToken;

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            conn.setRequestMethod("GET");

//            String userpass = "user" + ":" + "pass";
//            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
//            conn.setRequestProperty("Authorization", basicAuth);
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(in);
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            String jsonResponse = out.toString();
            System.out.println(jsonResponse);   //Prints the string content read from input stream
            reader.close();

            System.out.println("Reading From Firebase Readtime Databbase Complete.....");
            return jsonResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getKeyNamesOfTimeoutRecordsByParsingWholeJsonStringOfIndex(String jsonString) {
        List<String> timeExpiredIndexs = new ArrayList();
        try {

            System.out.println("Reading JSON to Find Time-Out Keys.............");
//            jsonString = "{\"123\":{\"accuracy\":10,\"altitude\":1278,\"bearing\":5,\"bearingAccuracyDegrees\":0,\"complete\":true,\"elapsedRealtimeNanos\":181891432358575,\"extras\":{\"empty\":false,\"emptyParcel\":false,\"mFlags\":1537,\"parcelled\":false,\"size\":0},\"fromMockProvider\":false,\"latitude\":27.6731498,\"longitude\":85.3243905,\"provider\":\"fused\",\"speed\":0,\"speedAccuracyMetersPerSecond\":0,\"time\":1518011675000,\"verticalAccuracyMeters\":20},\"1234\":{\"accuracy\":10,\"altitude\":1248,\"bearing\":0,\"complete\":true,\"elapsedRealtimeNanos\":75682311825286,\"extras\":{\"empty\":false,\"parcelled\":false},\"fromMockProvider\":false,\"latitude\":27.6731559,\"longitude\":85.3243388,\"provider\":\"fused\",\"speed\":0,\"time\":1517275809000},\"123456\":{\"accuracy\":20,\"altitude\":10,\"bearing\":0,\"complete\":true,\"elapsedRealtimeNanos\":8569426755030,\"extras\":{\"empty\":false,\"emptyParcel\":false,\"parcelled\":false},\"fromMockProvider\":false,\"latitude\":27.4959983,\"longitude\":85.084,\"provider\":\"fused\",\"speed\":0,\"time\":1517190213000},\"new1\":{\"accuracy\":123,\"time\":22323232},\"tc-152\":{\"accuracy\":20,\"altitude\":10,\"bearing\":0,\"complete\":true,\"elapsedRealtimeNanos\":9280905100300,\"extras\":{\"empty\":false,\"emptyParcel\":false,\"parcelled\":false},\"fromMockProvider\":false,\"latitude\":27.838,\"longitude\":85.084,\"provider\":\"fused\",\"speed\":0,\"time\":1517190925000}}";
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonString);
            JSONObject jsonObject = (JSONObject) obj;
            for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
//                System.out.println(jsonObject.get(key));
                System.out.println("Key : " + key);
                String readKeyOfJsonObject = jsonObject.get(key).toString();

//                System.out.println(readJsonObjectOfKey);
                if (isRecordTimeOut(readKeyOfJsonObject)) {
                    System.out.println(key + " Key is  Expired.  Adding in expiredList");
                    timeExpiredIndexs.add(key);
                }
            }

            System.out.println("Reading JSON to Find Time-Out Keys Complete.............");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return timeExpiredIndexs;
    }

    private boolean isRecordTimeOut(String readJsonObjectOfKey) {
        try {

            String jsonString = readJsonObjectOfKey;

            JSONParser parser = new JSONParser();
            Object readObj = parser.parse(jsonString);

            JSONObject jsonObject = (JSONObject) readObj;
//            System.out.println(jsonObject);

//            long name = (Long) jsonObject.get("accuracy");
//            System.out.println(name);
            long recordedTimestamp = ((Long) jsonObject.get("time")) / 1000;
            System.out.println("Recorded Time : " + recordedTimestamp);
            long currentTimestamp = DateConverter.getCurrentConvertedDateAndTimeInUnixDate();
            System.out.println("Current Time : " + currentTimestamp);

            int differenceInMinutes = (int) ((currentTimestamp - recordedTimestamp) / 60d);
            System.out.println("Diffrence In Minute : " + differenceInMinutes);
//            javax.swing.JOptionPane.showMessageDialog(null, differenceInMinutes);
            if (differenceInMinutes > expiredMinutes) {
                return true;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private void deleteTimeoutRecordByKeyFromServer(String authToken, String keyName) {
        try {
            System.out.println("Deleting Process Start For Key " + keyName);
//            String url = "https://livetrack-d793f.firebaseio.com/locations/new1.json";
            String url = firebaseCoreIndexURL + "/" + keyName + ".json" + "?access_token=" + authToken;
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            conn.setRequestMethod("DELETE");

            String userpass = "user" + ":" + "pass";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
            conn.setRequestProperty("Authorization", basicAuth);

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(in);
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
//            System.out.println(out.toString());   //Prints the string content read from input stream
            reader.close();
            System.out.println("Deleting Process For Key " + keyName + " Complete.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFirebaseAuthToken() throws Exception {

        FileInputStream serviceAccount = new FileInputStream(firebaseServiceTokenFilePath);

        // Authenticate a Google credential with the service account
        GoogleCredential googleCred = GoogleCredential.fromStream(serviceAccount);

// Add the required scopes to the Google credential
        GoogleCredential scoped = googleCred.createScoped(
                Arrays.asList(
                        "https://www.googleapis.com/auth/firebase.database",
                        "https://www.googleapis.com/auth/userinfo.email"
                )
        );

// Use the Google credential to generate an access token
        scoped.refreshToken();
        String token = scoped.getAccessToken();
        System.out.println("Firbase Realtime Database Auth Token : " + token);

        return token;
    }

    public void deleteRecordByKeyFromServer(String keyName) {
        try {
            System.out.println("Deleting Process Start For Key " + keyName);
//            String url = "https://livetrack-d793f.firebaseio.com/locations/new1.json";
            String url = firebaseCoreIndexURL + "/" + keyName + ".json" + "?access_token=" + getFirebaseAuthToken();
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            conn.setRequestMethod("DELETE");

            String userpass = "user" + ":" + "pass";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
            conn.setRequestProperty("Authorization", basicAuth);

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(in);
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
//            System.out.println(out.toString());   //Prints the string content read from input stream
            reader.close();
            System.out.println("Deleting Process For Key " + keyName + " Complete.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
