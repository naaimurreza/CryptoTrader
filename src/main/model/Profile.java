package model;


import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import exceptions.InvalidSelectionException;

import java.util.ArrayList;
import java.util.List;

/*
  Represents a profile in CryptoTrader with name, balance in CAD and wallet with a list of acquired cryptocurrencies
 */
public class Profile {
    public final String name;
    private double balance;
    private final List<Cryptocurrency> cryptoWallet;

    public Profile(String name, double balance) {
        this.name = name;
        this.balance = balance;
        cryptoWallet = new ArrayList<>();
    }

    public List<Cryptocurrency> getCryptoWallet() {
        return cryptoWallet;
    }

    public double getBalance() {
        return balance;
    }


    // MODIFIES: this, cryptocurrency
    // EFFECTS: If there is sufficient balance on the profile, subtracts the cost of cryptocurrency from balance and
    //          adds the cryptocurrency to the wallet.
    //          Adds amount to the amount field of the cryptocurrency
    public void buy(Cryptocurrency crypto, double amount) throws InsufficientBalanceException, InvalidAmountException {
        if (amount > 0) {
            double cryptoPrice = crypto.getCurrentPrice() * amount;
            if (cryptoPrice <= this.balance) {
                crypto.addAmount(amount);
                if (!this.cryptoWallet.contains(crypto)) {
                    this.cryptoWallet.add(crypto);
                }
                this.balance = this.getBalance() - cryptoPrice;
            } else {
                throw new InsufficientBalanceException();
            }
        } else {
            throw new InvalidAmountException();
        }

    }

    // MODIFIES: this
    // EFFECTS: Subtracts amount from the amount field of the cryptocurrency at the (index - 1) index in the wallet.
    //          Removes the cryptocurrency from the wallet if its amount becomes 0.
    //          Adds the price of the cryptocurrency removed to balance.
    public void sell(int index, double amount) throws InvalidSelectionException, InvalidAmountException {
        if (index <= this.cryptoWallet.size() && index > 0) {
            Cryptocurrency cryptocurrency = this.cryptoWallet.get(index - 1);
            if (amount <= cryptocurrency.getAmount()) {
                double soldPrice = cryptocurrency.getCurrentPrice() * amount;
                cryptocurrency.subAmount(amount);
                this.balance += soldPrice;
                if (cryptocurrency.getAmount() == 0) {
                    this.cryptoWallet.remove(cryptocurrency);
                }
            } else {
                throw new InvalidAmountException();
            }
        } else {
            throw new InvalidSelectionException();
        }

    }

    // MODIFIES: this, takeCrypto
    // EFFECTS: Removes the cryptocurrency at index (giveIndex - 1) of the wallet and adds takeCrypto with an amount
    //          equivalent to the amount of cryptocurrency removed.
    public void trade(int giveIndex, Cryptocurrency takeCrypto) throws InvalidSelectionException {
        if (giveIndex <= this.cryptoWallet.size() && giveIndex > 0) {
            Cryptocurrency giveCrypto = this.cryptoWallet.get(giveIndex - 1);
            double givePrice = giveCrypto.getCurrentPrice() * giveCrypto.getAmount();
            double getAmountCrypto = givePrice / takeCrypto.getCurrentPrice();
            takeCrypto.addAmount(getAmountCrypto);
            this.cryptoWallet.remove(giveCrypto);
            if (!this.cryptoWallet.contains(takeCrypto)) {
                this.cryptoWallet.add(takeCrypto);
            }
        } else {
            throw new InvalidSelectionException();
        }
    }

}
