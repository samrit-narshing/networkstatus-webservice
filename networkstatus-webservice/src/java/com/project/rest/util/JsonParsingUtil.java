/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.util;

import com.project.rest.mvc.web.ChartDataTwo;
import com.project.rest.mvc.web.ChartDataTwoEdge;
import com.project.rest.mvc.web.ChartDataTwoFill;
import com.project.rest.mvc.web.ChartDataTwoMain;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author samri_g64pbd3
 */
public class JsonParsingUtil {

    public static String readJSONText() throws Exception {
        URL url = new URL("http://localhost:8084/networkstatus-webservice/rest/device/test/chartData2");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        String inline = "";
        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

            }

        }

        Scanner sc = new Scanner(url.openStream());
        while (sc.hasNext()) {
            inline += sc.nextLine();
        }

        System.out.println("\nJSON data in string format");
        System.out.println(inline);

        sc.close();
        return inline;
    }

    public static ChartDataTwoMain getChartData() throws Exception {
        String jsonString = readJSONText(); //assign your JSON String here
        JSONObject obj = new JSONObject(jsonString);
//String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        ChartDataTwoMain main = new ChartDataTwoMain();
        List<ChartDataTwo> nodes = new ArrayList<>();
        List<ChartDataTwoEdge> edges = new ArrayList<>();

        JSONArray arr = obj.getJSONArray("nodes");
        for (int i = 0; i < arr.length(); i++) {
            String id = arr.getJSONObject(i).getString("id");
            int numId = arr.getJSONObject(i).getInt("numId");
            int height = arr.getJSONObject(i).getInt("height");
            String link = arr.getJSONObject(i).getString("link");
            String fillSource = arr.getJSONObject(i).getJSONObject("fill").getString("src");
            Boolean alert = arr.getJSONObject(i).getBoolean("alert");

            System.out.println("ID :- " + id);
            System.out.println("height :- " + id);
            System.out.println("fillSource :- " + fillSource);

            ChartDataTwo cdt = new ChartDataTwo();
            ChartDataTwoFill fill = new ChartDataTwoFill();
            fill.setSrc(fillSource);
            cdt.setFill(fill);
            cdt.setHeight(height);
            cdt.setId(id);
            cdt.setLink(link);
            cdt.setNumId(numId);
            cdt.setAlert(alert);
            nodes.add(cdt);
        }

        JSONArray arr2 = obj.getJSONArray("edges");
        for (int i = 0; i < arr2.length(); i++) {
            String from = arr2.getJSONObject(i).getString("from");
            String to = arr2.getJSONObject(i).getString("to");

            System.out.println("From :- " + from);
            System.out.println("To :- " + to);

            ChartDataTwoEdge cdt = new ChartDataTwoEdge();
            cdt.setFrom(from);
            cdt.setTo(to);

            edges.add(cdt);

        }

        main.setEdges(edges);
        main.setNodes(nodes);
        return main;

    }

}
