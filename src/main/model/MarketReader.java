package model;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class MarketReader {
    private static HttpURLConnection connection;


    public Cryptocurrency retrieveInfo(String cryptoCode, String cryptoName, double amount) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            URL url = new URL("https://api.cryptonator.com/api/ticker/" + cryptoCode.toLowerCase(Locale.ROOT) + "-cad");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return parse(responseContent.toString(), cryptoCode, amount, cryptoName);
    }

    public Cryptocurrency parse(String responseBody, String cryptoCode, double amount, String cryptoName) {
        JSONObject data = new JSONObject(responseBody);
        JSONObject bodyJSON = data.getJSONObject("ticker");
        String priceString = bodyJSON.getString("price");
        double price = Double.parseDouble(priceString);
        return new Cryptocurrency(cryptoName, cryptoCode, price, amount);
    }
}
