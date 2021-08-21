package model;

import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import exceptions.InvalidSelectionException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/*
  Represents a profile in CryptoTrader with name, balance in CAD and wallet with a list of acquired cryptocurrencies
 */
public class Profile implements Writable {
    private final String name;
    private double balance;
    private final List<Cryptocurrency> cryptoWallet;

    // EFFECTS: Constructs a Profile object with name and balance
    //          If balance is <= 0, sets the balance to $1,000,000.00
    public Profile(String name, double balance) {
        this.name = name;
        if (balance > 0) {
            this.balance = balance;
        } else {
            this.balance = 1000000;
        }
        cryptoWallet = new ArrayList<>();
    }

    public List<Cryptocurrency> getCryptoWallet() {
        return cryptoWallet;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this, cryptocurrency
    // EFFECTS: If there is sufficient balance on the profile, subtracts the cost of cryptocurrency from balance and
    //          adds the cryptocurrency to the wallet.
    //          Adds amount to the amount field of the cryptocurrency
    public boolean buy(Cryptocurrency crypto, double amount)
            throws InsufficientBalanceException, InvalidAmountException {
        if (amount > 0) {
            double cryptoPrice = crypto.getCurrentPrice() * amount;
            if (cryptoPrice <= this.balance) {
                if (!this.cryptoWallet.contains(crypto)) {
                    crypto.addAmount(amount);
                    this.cryptoWallet.add(crypto);
                } else {
                    Cryptocurrency cryptocurrency = getCrypto(this.cryptoWallet, crypto);
                    cryptocurrency.addAmount(amount);
                }
                this.balance = this.getBalance() - cryptoPrice;
                return true;
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
    public void trade(Cryptocurrency giveCrypto, Cryptocurrency takeCrypto) {
        double givePrice = giveCrypto.getCurrentPrice() * giveCrypto.getAmount();
        double getAmountCrypto = givePrice / takeCrypto.getCurrentPrice();
        takeCrypto.addAmount(getAmountCrypto);
        this.cryptoWallet.remove(giveCrypto);
        if (!this.cryptoWallet.contains(takeCrypto)) {
            this.cryptoWallet.add(takeCrypto);
        } else {
            Cryptocurrency takeCryptoNewInstance = getCrypto(this.cryptoWallet, takeCrypto);
            takeCryptoNewInstance.addAmount(getAmountCrypto);
        }
    }


    // EFFECTS: Returns the Cryptocurrency instance in the crypto wallet that is equal to cryptocurrency if found,
    //          returns null otherwise
    public Cryptocurrency getCrypto(List<Cryptocurrency> wallet, Cryptocurrency cryptocurrency) {
        for (Cryptocurrency crypto : wallet) {
            if (crypto.equals(cryptocurrency)) {
                return crypto;
            }
        }
        return null;
    }


    // EFFECTS: Returns all the data in this as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", this.name);
        jsonObject.put("balance", this.balance);
        jsonObject.put("cryptowallet", cryptoWalletToJson());
        return jsonObject;
    }


    // EFFECTS: Returns cryptocurrencies in this.cryptoWallet as a JSON array
    public JSONArray cryptoWalletToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Cryptocurrency cryptocurrency : this.cryptoWallet) {
            jsonArray.put(cryptocurrency.toJson());
        }
        return jsonArray;
    }
}
