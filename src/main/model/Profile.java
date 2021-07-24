package model;

import ui.CryptoTraderUI;
import ui.ProfileUI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Profile {
    private final String name;
    private double balance;
    private List<Cryptocurrency> wallet;

    public Profile(String name, double balance) {
        this.name = name;
        this.balance = balance;
        wallet = new ArrayList<>();
    }

    public Profile(String name, double balance, List<Cryptocurrency> wallet) {
        this.name = name;
        this.balance = balance;
        this.wallet = wallet;
    }

    public List<Cryptocurrency> getWallet() {
        return wallet;
    }

    public String getName() {
        return name;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void runCryptoTrader() {
        new CryptoTraderUI(this);
    }
}
