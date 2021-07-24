package model;

public class Cryptocurrency {

    private String cryptoName;
    private String cryptoCode;
    private double amount;
    private double price;

    public Cryptocurrency(String cryptoName, String cryptoCode, double amount, double price) {
        this.cryptoName = cryptoName;
        this.cryptoCode = cryptoCode;
        this.amount = amount;
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCryptoName() {
        return cryptoName;
    }

    public void setCryptoName(String cryptoName) {
        this.cryptoName = cryptoName;
    }

    public String getCryptoCode() {
        return cryptoCode;
    }

    public void setCryptoCode(String cryptoCode) {
        this.cryptoCode = cryptoCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
