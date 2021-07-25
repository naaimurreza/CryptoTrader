package model;


import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import exceptions.InvalidSelectionException;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private final String name;
    private double balance;
    private final List<Cryptocurrency> wallet;

    public Profile(String name, double balance) {
        this.name = name;
        this.balance = balance;
        wallet = new ArrayList<>();
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


    public void buy(Cryptocurrency cryptocurrency, double amount) throws InsufficientBalanceException {
        double cryptoPrice = cryptocurrency.getCurrentPrice() * amount;
        if (cryptoPrice <= this.balance) {
            cryptocurrency.addAmount(amount);
            if (!this.wallet.contains(cryptocurrency)) {
                this.wallet.add(cryptocurrency);
            }
            this.balance -= cryptoPrice;
        } else {
            throw new InsufficientBalanceException();
        }
    }

    public void sell(int index, double amount) throws InvalidSelectionException, InvalidAmountException {
        if (index <= this.wallet.size() && index > 0) {
            Cryptocurrency cryptocurrency = this.wallet.get(index - 1);
            if (amount <= cryptocurrency.getAmount()) {
                double soldPrice = cryptocurrency.getCurrentPrice() * amount;
                cryptocurrency.subAmount(amount);
                this.balance += soldPrice;
                if (cryptocurrency.getAmount() == 0) {
                    this.wallet.remove(cryptocurrency);
                }
            } else {
                throw new InvalidAmountException();
            }
        } else {
            throw new InvalidSelectionException();
        }

    }

    public void trade(int giveIndex, Cryptocurrency takeCrypto) throws InvalidSelectionException {
        if (giveIndex <= this.wallet.size() && giveIndex > 0) {
            Cryptocurrency giveCrypto = this.wallet.get(giveIndex - 1);
            double givePrice = giveCrypto.getCurrentPrice() * giveCrypto.getAmount();
            double getAmountCrypto = givePrice / takeCrypto.getCurrentPrice();
            takeCrypto.addAmount(getAmountCrypto);
            this.wallet.remove(giveCrypto);
            if (!this.wallet.contains(takeCrypto)) {
                this.wallet.add(takeCrypto);
            }
        } else {
            throw new InvalidSelectionException();
        }
    }

}
