/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author samri
 */
public class GeoLocationTest {
    public static void main(String[] args) {
        System.out.println(getUserLocation("27.67329623", "85.32436502"));
    }
    
    public static String getUserLocation(String lat, String lon) {
   String userlocation = null;
   String readUserFeed = readUserLocationFeed(lat.trim() + "," + lon.trim());
   try {
       JSONObject Strjson = new JSONObject(readUserFeed);
      JSONArray jsonArray = new JSONArray(Strjson.getString("results"));
      userlocation = jsonArray.getJSONObject(1)
            .getString("formatted_address").toString();
   } catch (Exception e) {
      e.printStackTrace();
   }
        System.out.println("User Location " + userlocation);
   return userlocation;
}

public static String readUserLocationFeed(String address) {
   StringBuilder builder = new StringBuilder();
    HttpClient client = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(
         "http://maps.google.com/maps/api/geocode/json?latlng=" + address
               + "&sensor=false");
   try {
       HttpResponse response = client.execute(httpGet);
       StatusLine statusLine = response.getStatusLine();
      int statusCode = statusLine.getStatusCode();
      if (statusCode == 200) {
          HttpEntity entity = response.getEntity();
          InputStream content = entity.getContent();
          BufferedReader reader = new BufferedReader(new InputStreamReader(
               content));
         String line;
         while ((line = reader.readLine()) != null) {
             System.out.println(line);
            builder.append(line);
         }
      } else {
          System.out.println("Failed to download file");
//         Log.e(ReverseGeocode.class.toString(), "Failed to download file");
      }
   } catch (ClientProtocolException e) {
      e.printStackTrace();
   } catch (IOException e) {
      e.printStackTrace();
   }
   return builder.toString();
}
    
}
