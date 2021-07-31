package ui;

import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import exceptions.InvalidSelectionException;
import model.Cryptocurrency;
import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/*
  Represents a CryptoTrader game
 */
public class CryptoTrader {
    private static final String JSON_STORE = "./data/profile.json";
    private static final String JSON_MARKET = "./data/cryptoMarket.json";
    private Profile profile;
    private final JsonWriter jsonWriter;
    private List<Cryptocurrency> market;

    boolean quit = false;

    private final Scanner scanner = new Scanner(System.in);

    // MODIFIES: this
    // EFFECTS: runs the profile UI
    public CryptoTrader() {
        JsonReader jsonReaderProfile = new JsonReader(JSON_STORE);
        JsonReader jsonReaderMarket = new JsonReader(JSON_MARKET);
        jsonWriter = new JsonWriter(JSON_STORE);

        try {
            this.market = jsonReaderMarket.readMarket();
            this.profile = jsonReaderProfile.read();
            System.out.println("Successfully loaded CryptoTrade©!");
        } catch (IOException e) {
            System.out.println("\nLooks like you don't have a profile yet.\n");
        }
        if (this.profile == null) {
            runProfileUI();
        } else {
            System.out.println("Welcome back, " + this.profile.getName() + "!");
            runCryptoTrader();
        }

    }

    // MODIFIES: this
    // EFFECTS: Displays welcome message and asks for user name and initial balance to instantiate a new Profile object.
    //          runs the CryptoTrader UI.
    public void runProfileUI() {
        try {
            System.out.println("Welcome To CryptoTrader©!");
            System.out.println("Please enter a name for your profile: ");
            String name = scanner.next();
            scanner.nextLine();
            System.out.println("Please enter a balance for your profile: ");
            double balance = scanner.nextDouble();
            this.profile = new Profile(name, balance);
            runCryptoTrader();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid input");
        }

    }

    // MODIFIES: this
    // EFFECTS: Displays the profile name, balance and the main CryptoTrader menu. Processes user input.
    public void runCryptoTrader() {
        int command;
        while (!quit) {
            System.out.println("\nCryptoMaster, " + this.profile.getName() + ".");
            System.out.println("Balance: $" + this.profile.getBalance());
            displayCryptoTraderMenu();
            command = scanner.nextInt();
            if (command == 5) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(profile);
                    jsonWriter.close();
                    System.out.println("Auto-save complete.");
                    System.out.println("Goodbye.");
                    quit = true;
                } catch (FileNotFoundException e) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }

            } else {
                processCommand(command);
            }
        }

    }

    // EFFECTS: Displays the main CryptoTrader menu.
    public void displayCryptoTraderMenu() {
        System.out.println("Select an option:");
        System.out.println("\t1: To view your wallet");
        System.out.println("\t2: To buy");
        System.out.println("\t3: To sell");
        System.out.println("\t4: To trade");
        System.out.println("\t5: To quit");
        System.out.println("\t6: To restart");
    }

    // EFFECTS: Processes user command
    public void processCommand(int command) {
        switch (command) {
            case 1:
                printWallet();
                break;
            case 2:
                buyCrypto();
                break;
            case 3:
                sellCrypto();
                break;
            case 4:
                tradeCrypto();
                break;
            case 6:
                this.profile = null;
                runProfileUI();
            default:
                System.out.println("Please enter a valid option");
        }
    }

    // EFFECTS: Displays all the cryptocurrencies in the wallet or tells user if there is nothing in the wallet.
    public void printWallet() {
        if (this.profile.getCryptoWallet().size() == 0) {
            System.out.println("Your wallet is currently empty.");
        } else {
            int position = 1;
            for (Cryptocurrency cryptocurrency : this.profile.getCryptoWallet()) {
                System.out.println(position + ") "
                        + cryptocurrency.getAmount()
                        + " "
                        + cryptocurrency.getCryptoCode().toUpperCase()
                        + "\t - \t" + "$"
                        + cryptocurrency.getCurrentPrice()
                        * cryptocurrency.getAmount());
                position++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Conducts a cryptocurrency purchase.
    public void buyCrypto() {
        while (!quit) {
            System.out.println("\nSelect the Cryptocurrency you want to buy: ");
            showCryptoMenu();
            int picked = scanner.nextInt();
            if (picked > this.market.size() || picked < 1) {
                System.out.println("Please select a valid option.");
            } else {
                if (picked == this.market.size() + 1) {
                    runCryptoTrader();
                } else {
                    System.out.println("Enter the amount you want to buy: ");
                    processBuyOption(picked);
                }
            }
        }

    }

    // EFFECT: Processes buy option.
    public void processBuyOption(int picked) {
        double amount = scanner.nextDouble();
        try {
            Cryptocurrency pickedToBuy = this.market.get(picked - 1);
            if (this.profile.buy(pickedToBuy, amount)) {
                System.out.println("\nPurchase successful!");
            }
        } catch (InsufficientBalanceException e) {
            System.out.println("Insufficient balance");
            runCryptoTrader();
        } catch (InvalidAmountException e) {
            System.out.println("Please enter a valid amount.");
        }
        runCryptoTrader();
    }


    // MODIFIES: this
    // EFFECTS: Displays all the available cryptocurrencies with current prices.
    public void showCryptoMenu() {
        int position = 1;
        for (Cryptocurrency cryptocurrency : this.market) {
            System.out.println(position + ") "
                    + cryptocurrency.getCryptoName()
                    + "\t  - \t $"
                    + cryptocurrency.getCurrentPrice());
            position++;
        }
        System.out.println(position + ") To go back");
    }

    // MODIFIES: this
    // EFFECTS: Asks user to select cryptocurrency and amount conducts a sale.
    public void sellCrypto() {
        if (this.profile.getCryptoWallet().isEmpty()) {
            printWallet();
            runCryptoTrader();
        }
        while (!quit) {
            System.out.println("Select the Cryptocurrency you want to sell: ");
            printWallet();
            int index = scanner.nextInt();
            System.out.println("Select the amount you want to sell: ");
            double amount = scanner.nextDouble();
            try {
                this.profile.sell(index, amount);
                System.out.println("Successfully sold!");
                runCryptoTrader();
            } catch (InvalidSelectionException e) {
                System.out.println("Please select a valid option.");
            } catch (InvalidAmountException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Asks user for a trade option and conducts a trade.
    public void tradeCrypto() {
        if (this.profile.getCryptoWallet().isEmpty()) {
            printWallet();
            runCryptoTrader();
        }
        while (!quit) {
            System.out.println("Select Cryptocurrency you want to trade: ");
            printWallet();
            int giveIndex = scanner.nextInt();
            System.out.println("Select the Cryptocurrency you want to trade with: ");
            showCryptoMenu();
            int takeIndex = scanner.nextInt();
            performTrade(takeIndex, giveIndex);
        }
    }

    // MODIFIES: this
    // EFFECTS: Conducts a trade.
    public void performTrade(int takeIndex, int giveIndex) {
        try {
            Cryptocurrency cryptoToTake = this.market.get(takeIndex);
            this.profile.trade(giveIndex, cryptoToTake);
            runCryptoTrader();
        } catch (InvalidSelectionException e) {
            System.out.println("Please select a valid option.");
        }
    }


}
