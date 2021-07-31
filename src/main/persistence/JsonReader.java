package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.Cryptocurrency;
import model.Profile;
import org.json.*;

/*
  Represents a reader that reads workroom from JSON data stored in file
 */
public class JsonReader {
    private final String file;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.file = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Profile read() throws IOException {
        String jsonData = readFile(file);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseProfile(jsonObject);
    }

    // EFFECTS: Returns list of Cryptocurrencies formed by reading and parsing the data in cryptoMarket.json.
    //          Throws IOException if an error occurs reading data from file.
    public List<Cryptocurrency> readMarket() throws IOException {
        String jsonData = readFile(file);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("market");
        List<Cryptocurrency> market = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject crypto = (JSONObject) json;
            String cryptoName = crypto.getString("name");
            double price = crypto.getDouble("price");
            String cryptoCode = crypto.getString("base");
            Cryptocurrency newCrypto = new Cryptocurrency(cryptoName, cryptoCode, price);
            market.add(newCrypto);
        }
        return market;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Profile parseProfile(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double balance = jsonObject.getDouble("balance");
        Profile profile = new Profile(name, balance);
        addCryptoWallet(profile, jsonObject);
        return profile;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addCryptoWallet(Profile profile, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cryptowallet");
        for (Object json : jsonArray) {
            JSONObject nextCrypto = (JSONObject) json;
            addCryptocurrency(profile, nextCrypto);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addCryptocurrency(Profile profile, JSONObject jsonObject) {
        String cryptoName = jsonObject.getString("cryptoName");
        double price = jsonObject.getDouble("price");
        double amount = jsonObject.getDouble("amount");
        String cryptoCode = jsonObject.getString("cryptoCode");
        Cryptocurrency cryptocurrency = new Cryptocurrency(cryptoName, cryptoCode, price, amount);
        profile.getCryptoWallet().add(cryptocurrency);
    }
}
