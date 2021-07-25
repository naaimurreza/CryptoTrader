package ui;

import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import exceptions.InvalidSelectionException;
import model.Cryptocurrency;
import model.Profile;

import java.util.Scanner;

public class CryptoTrader {
    private Profile profile;

    boolean quit = false;

    private final Cryptocurrency bitcoin = new Cryptocurrency("Bitcoin", "BTC", 43297.86);
    private final Cryptocurrency ethereum = new Cryptocurrency("Ethereum", "ETH", 2726.81);
    private final Cryptocurrency litecoin = new Cryptocurrency("Litecoin", "LTC", 158.51);
    private final Cryptocurrency dogecoin = new Cryptocurrency("Dogecoin", "DOGE", 0.25);

    private Scanner scanner = new Scanner(System.in);

    public CryptoTrader() {
        runProfileUI();
    }

    public void runProfileUI() {
        System.out.println("WELCOME TO CryptoTrader!");
        System.out.println("Please enter a name for your profile: ");
        String name = scanner.nextLine();
        System.out.println("Please enter a balance for your profile: ");
        double balance = scanner.nextDouble();
        this.profile = new Profile(name, balance);
        runCryptoTrader();
    }

    public void runCryptoTrader() {
        int command;
        while (!quit) {
            System.out.println("\nWelcome, " + this.profile.getName() + "!");
            System.out.println("Balance: $" + this.profile.getBalance());
            displayCryptoTraderMenu();
            command = scanner.nextInt();
            if (command == 5) {
                quit = true;
            } else {
                processCommand(command);
            }
        }

    }

    public void displayCryptoTraderMenu() {
        System.out.println("Select an option:");
        System.out.println("\t1: To view your wallet");
        System.out.println("\t2: To buy");
        System.out.println("\t3: To sell");
        System.out.println("\t4: To trade");
        System.out.println("\t5: To quit");
    }

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
            default:
                System.out.println("Please enter a valid option");
        }
    }

    public void printWallet() {
        if (this.profile.getWallet().size() == 0) {
            System.out.println("Your wallet is currently empty.");
        } else {
            int position = 1;
            for (Cryptocurrency cryptocurrency : this.profile.getWallet()) {
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

    public void buyCrypto() {
        while (!quit) {
            System.out.println("\nSelect the Cryptocurrency you want to buy: ");
            showCryptoMenu();
            int picked = scanner.nextInt();
            if (picked > 5 || picked < 1) {
                System.out.println("Please select a valid option.");
            } else {
                if (picked == 5) {
                    runCryptoTrader();
                } else {
                    processBuyOption(picked);
                }
            }
        }

    }

    public void processBuyOption(int picked) {
        System.out.println("Enter the amount you want to buy: ");
        double amount = scanner.nextDouble();
        try {
            switch (picked) {
                case 1:
                    this.profile.buy(bitcoin, amount);
                    break;
                case 2:
                    this.profile.buy(ethereum, amount);
                    break;
                case 3:
                    this.profile.buy(litecoin, amount);
                    break;
                case 4:
                    this.profile.buy(dogecoin, amount);
                    break;
            }
        } catch (InsufficientBalanceException e) {
            System.out.println("Insufficient balance");
            runCryptoTrader();
        }
        System.out.println("Purchase successful");
        runCryptoTrader();
    }

    public void showCryptoMenu() {
        System.out.println("1) " + bitcoin.getCryptoName() + "\t  - \t $" + bitcoin.getCurrentPrice());
        System.out.println("2) " + ethereum.getCryptoName() + "\t  - \t $" + ethereum.getCurrentPrice());
        System.out.println("3) " + litecoin.getCryptoName() + "\t  - \t $" + litecoin.getCurrentPrice());
        System.out.println("4) " + dogecoin.getCryptoName() + "\t  - \t $" + dogecoin.getCurrentPrice());
        System.out.println("5) To go back");
    }

    public void sellCrypto() {
        while (!quit) {
            System.out.println("Select the Cryptocurrency you want to sell: ");
            printWallet();
            int index = scanner.nextInt();
            System.out.println("Select the amount you want to sell (enter 0 to sell all): ");
            double amount = scanner.nextDouble();
            try {
                this.profile.sell(index, amount);
                System.out.println("Successfully sold");
                runCryptoTrader();
            } catch (InvalidSelectionException e) {
                System.out.println("Please select a valid option.");
            } catch (InvalidAmountException e) {
                System.out.println("Please enter a valid amount.");
            }


        }
    }

    public void tradeCrypto() {
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

    public void performTrade(int takeIndex, int giveIndex) {
        try {
            switch (takeIndex) {
                case 1:
                    this.profile.trade(giveIndex, bitcoin);
                    runCryptoTrader();
                    break;
                case 2:
                    this.profile.trade(giveIndex, ethereum);
                    runCryptoTrader();
                    break;
                case 3:
                    this.profile.trade(giveIndex, litecoin);
                    runCryptoTrader();
                    break;
                case 4:
                    this.profile.trade(giveIndex, dogecoin);
                    runCryptoTrader();
                    break;
            }

        } catch (InvalidSelectionException e) {
            System.out.println("Please select a valid option.");
        }
    }


}
