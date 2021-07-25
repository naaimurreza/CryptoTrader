package model;

public class Cryptocurrency {

    private final String cryptoName;
    private final String cryptoCode;
    private double amount;
    private double price;


    public Cryptocurrency(String cryptoName, String cryptoCode, double price) {
        this.cryptoName = cryptoName;
        this.cryptoCode = cryptoCode;
        this.amount = 0;
        this.price = price;
    }


    public double getAmount() {
        return amount;
    }

    public String getCryptoName() {
        return cryptoName;
    }

    public String getCryptoCode() {
        return cryptoCode;
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

    public double getCurrentPrice() {
        int randomInt = (int)Math.floor(Math.random() * 100);
        double newPrice;
        if (randomInt > 50) {
            newPrice = this.getPrice() * 1.05;
        } else {
            newPrice = this.getPrice() * 0.95;
        }
        this.setPrice(newPrice);
        return newPrice;
    }
}
