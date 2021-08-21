package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.DecimalFormat;
import java.util.Objects;

/*
 Represents a cryptocurrency with cryptoName, cryptoCode, amount of it present and price in CAD
 */
public class Cryptocurrency implements Writable {

    private final String cryptoName;
    private final String cryptoCode;
    private double amount;
    private double price;

    DecimalFormat decimalFormat = new DecimalFormat("###0.0000");
    private MarketReader marketReader;

    // EFFECTS: Constructs a cryptocurrency object with amount set to zero
    // REQUIRES: price >= 0
    public Cryptocurrency(String cryptoName, String cryptoCode, double price) {
        this.cryptoName = cryptoName;
        this.cryptoCode = cryptoCode;
        this.amount = 0;
        this.price = price;
    }

    // EFFECTS: Constructs a cryptocurrency object
    // REQUIRES: price >= 0
    public Cryptocurrency(String cryptoName, String cryptoCode, double price, double amount) {
        this.cryptoName = cryptoName;
        this.cryptoCode = cryptoCode;
        this.amount = amount;
        this.price = price;
    }

    public String getCryptoName() {
        return cryptoName;
    }

    public String getCryptoCode() {
        return cryptoCode;
    }

    public double getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: Adds amount to this.amount
    public void addAmount(double amount) {
        this.amount += amount;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: Subtracts amount to this.amount
    public void subAmount(double amount) {
        this.amount -= amount;
    }

    // MODIFIES: this
    // EFFECTS: Gets current price of this
    public double getCurrentPrice() {
        this.marketReader = new MarketReader();
        double newPrice = marketReader.retrieveInfo(this.cryptoCode, this.cryptoName, this.amount).getPrice();
        return Double.parseDouble(decimalFormat.format(newPrice));
    }

    //EFFECTS: Returns data in this cryptocurrency as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cryptoName", this.cryptoName);
        jsonObject.put("cryptoCode", this.cryptoCode);
        jsonObject.put("amount", this.amount);
        jsonObject.put("price", this.price);
        return jsonObject;
    }

    // EFFECTS: Returns true if this.cryptoCode is the same as that.cryptoCode, false otherwise
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        Cryptocurrency that = (Cryptocurrency) o;
        return Objects.equals(cryptoCode, that.cryptoCode);
    }

    // EFFECTS: Returns integer hashcode gotten by hashing this.cryptoCode;
    @Override
    public int hashCode() {
        return Objects.hash(cryptoCode);
    }
}
