package com.example.seniorfinal.Core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Location
{
    private final String APIKEY = "AIzaSyDcL0ii3auquxDciREAu8QGQM9K1-0bQng";

    public ArrayList<String> setStates()
    {
        return new ArrayList<>(java.util.List.of(
                "AK","AL","AR","AZ","CA","CO","CT","DE","FL","GA",
                "HI","IA","ID","IL","IN","KS","KY","LA","MA","MD",
                "ME","MI","MN","MO","MS","MT","NC","ND","NE","NH",
                "NJ","NM","NV","NY","OH","OK","OR","PA","RI","SC",
                "SD","TN","TX","UT","VA","VT","WA","WI","WV","WY"
        ));
    }


    public double[] convertAddrToLongAndLat(String address) throws IOException, InterruptedException
    {
        double latitude = 0;
        double longitude = 0;
        String urlAddress = java.net.URLEncoder.encode(address, "UTF-8");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + urlAddress + "&key=" + APIKEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject json = new JSONObject(response.body());
        if ("OK".equals(json.getString("status")))
        {
            JSONObject location = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            latitude = location.getDouble("lat");
            longitude = location.getDouble("lng");
        }
        else
        {
            System.out.println("Geocoding failed: " + json.getString("status"));
            latitude = 0;
            longitude = 0;
        }
        double[] nums = {latitude, longitude};

        return nums;
    }


    public String[] convertLongAndLatToAddr(double latitude, double longitude)
    {
        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=" + APIKEY;
        String town = new String();
        String state = new String();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("HTTP error: " + responseCode);
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray results = json.getJSONArray("results");

            if (results.length() > 0) {
                JSONArray components = results.getJSONObject(0).getJSONArray("address_components");

                for (int i = 0; i < components.length(); i++) {
                    JSONObject comp = components.getJSONObject(i);
                    JSONArray types = comp.getJSONArray("types");

                    for (int j = 0; j < types.length(); j++) {
                        String type = types.getString(j);
                        if (type.equals("locality")) {
                            town = comp.getString("long_name");
                            break; // stop checking other types for this component
                        }
                        else if (type.equals("administrative_area_level_1")) {
                            String fullState = comp.getString("long_name");
                            String code = stateNameToCode(fullState);
                            if (code != null) {
                                state = code;
                            }
                            else state = "N/A";
                            break; // stop checking other types for this component
                        }
                    }
                }

            } else {
                System.out.println("No results found for these coordinates.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] address = {town,state};
        return address;
    }



    private String stateNameToCode(String fullState) {
        switch (fullState) {
            case "Alabama": return "AL";
            case "Alaska": return "AK";
            case "Arizona": return "AZ";
            case "Arkansas": return "AR";
            case "California": return "CA";
            case "Colorado": return "CO";
            case "Connecticut": return "CT";
            case "Delaware": return "DE";
            case "Florida": return "FL";
            case "Georgia": return "GA";
            case "Hawaii": return "HI";
            case "Idaho": return "ID";
            case "Illinois": return "IL";
            case "Indiana": return "IN";
            case "Iowa": return "IA";
            case "Kansas": return "KS";
            case "Kentucky": return "KY";
            case "Louisiana": return "LA";
            case "Maine": return "ME";
            case "Maryland": return "MD";
            case "Massachusetts": return "MA";
            case "Michigan": return "MI";
            case "Minnesota": return "MN";
            case "Mississippi": return "MS";
            case "Missouri": return "MO";
            case "Montana": return "MT";
            case "Nebraska": return "NE";
            case "Nevada": return "NV";
            case "New Hampshire": return "NH";
            case "New Jersey": return "NJ";
            case "New Mexico": return "NM";
            case "New York": return "NY";
            case "North Carolina": return "NC";
            case "North Dakota": return "ND";
            case "Ohio": return "OH";
            case "Oklahoma": return "OK";
            case "Oregon": return "OR";
            case "Pennsylvania": return "PA";
            case "Rhode Island": return "RI";
            case "South Carolina": return "SC";
            case "South Dakota": return "SD";
            case "Tennessee": return "TN";
            case "Texas": return "TX";
            case "Utah": return "UT";
            case "Vermont": return "VT";
            case "Virginia": return "VA";
            case "Washington": return "WA";
            case "West Virginia": return "WV";
            case "Wisconsin": return "WI";
            case "Wyoming": return "WY";
            default: return "N/A";
        }
    }
}
