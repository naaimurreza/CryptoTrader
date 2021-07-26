package model;

import java.text.DecimalFormat;

public class Cryptocurrency {

    private final String cryptoName;
    private final String cryptoCode;
    private double amount;
    private double price;

    DecimalFormat decimalFormat = new DecimalFormat("####.0000");

/*
 Represents a cryptocurrency with cryptoName, cryptoCode, amount of it present and price in CAD
 */
    public Cryptocurrency(String cryptoName, String cryptoCode, double price) {
        this.cryptoName = cryptoName;
        this.cryptoCode = cryptoCode;
        this.amount = 0;
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

    public void addAmount(double amount) {
        this.amount += amount;
    }

    public void subAmount(double amount) {
        this.amount -= amount;
    }

    // MODIFIES: this
    // EFFECTS: Increases the price of the cryptocurrency by 5% or decrease the price of the cryptocurrency by 5% with
    //          the probability of each case being 1/2.
    //          Tries to mimic the high volatility of the cryptocurrency market.
    public double getCurrentPrice() {
        int randomInt = (int)Math.floor(Math.random() * 100);
        double newPrice;
        if (randomInt > 50) {
            newPrice = this.getPrice() * 1.05;
        } else {
            newPrice = this.getPrice() * 0.95;
        }
        this.setPrice(newPrice);
        return Double.parseDouble(decimalFormat.format(newPrice));
    }
}
