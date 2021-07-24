package ui;

import model.Cryptocurrency;
import model.Profile;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;


public class CryptoTraderUI {
    private Profile profile;

    private static Scanner scanner;
    private static HttpURLConnection connection;

    public CryptoTraderUI(Profile profile) {
        this.profile = profile;
        runCryptoTraderUI();
    }


    public void runCryptoTraderUI() {
        boolean quit = false;
        int command;
        scanner = new Scanner(System.in);
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
        System.out.println("\t5: To go back to the main menu");
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
        if (this.profile.getWallet().isEmpty()) {
            System.out.println("Your wallet is currently empty");
        } else {
            int position = 1;
            for (Cryptocurrency cryptocurrency : this.profile.getWallet()) {
                double currPrice = retrieveInfo(cryptocurrency.getCryptoCode(), 0).getPrice();
                System.out.println(position + ") "
                        + cryptocurrency.getAmount()
                        + " "
                        + cryptocurrency.getCryptoCode()
                        + "\t - \t" + "$"
                        + cryptocurrency.getPrice());
                position++;
            }
        }
    }

    public void buyCrypto() {
        boolean quit = false;
        int buyOption;
        scanner = new Scanner(System.in);
        showBuyMenu();
        while (!quit) {
            buyOption = scanner.nextInt();
            if (buyOption == 6) {
                quit = true;
            } else {
                processBuyOption(buyOption);
                runCryptoTraderUI();
            }
        }

    }

    public void showBuyMenu() {
        System.out.println("Select the Cryptocurrency you want to buy:\n");
        System.out.println("1) Bitcoin \t  - \t $" + retrieveInfo("btc", 0).getPrice());
        System.out.println("2) Ethereum \t - \t $" + retrieveInfo("eth", 0).getPrice());
        System.out.println("3) Litecoin \t - \t $" + retrieveInfo("ltc", 0).getPrice());
        System.out.println("4) Pokadot \t  - \t $" + retrieveInfo("dot", 0).getPrice());
        System.out.println("5) other\n");
        System.out.println("6) To go back");
    }

    public void processBuyOption(int buyOption) {
        switch (buyOption) {
            case 1:
                processPurchase("btc");
                break;
            case 2:
                processPurchase("eth");
                break;
            case 3:
                processPurchase("ltc");
                break;
            case 4:
                processPurchase("dot");
                break;
            case 5:
                processPurchase("other");
                break;
        }
    }

    public void processPurchase(String cyptoCode) {
        scanner = new Scanner(System.in);
        if (cyptoCode.equals("other")) {
            System.out.println("Enter the code of the Cryptocurrency you want to buy: ");
            String code = scanner.nextLine();
            processPurchase(code);
        } else {
            System.out.println("Enter the amount of " + cyptoCode.toUpperCase() + " you want to buy: ");
            double amount = scanner.nextDouble();
            if (amount > 0) {
                try {
                    Cryptocurrency cryptocurrency = retrieveInfo(cyptoCode, amount);
                    double cryptoPrice = cryptocurrency.getPrice() * amount;
                    if (this.profile.getBalance() >= cryptoPrice) {
                        if (isInWallet(cryptocurrency) != null) {
                            double price = isInWallet(cryptocurrency).getPrice();
                            double cryptoAmount = isInWallet(cryptocurrency).getAmount();
                            isInWallet(cryptocurrency).setPrice(price + cryptoPrice);
                            isInWallet(cryptocurrency).setAmount(cryptoAmount + amount);
                        } else {
                            this.profile.getWallet().add(cryptocurrency);
                        }
                        this.profile.setBalance(this.profile.getBalance() - cryptoPrice);
                        System.out.println("Purchase successful!");
                    } else {
                        System.out.println("Insufficient balance to make purchase.");
                    }
                } catch (Exception e) {
                    System.out.println("Cryptocurrency selected is currently unavailable.");
                }
            } else {
                System.out.println("Please enter a valid amount.");
                buyCrypto();
            }
        }
    }

    public Cryptocurrency isInWallet(Cryptocurrency cryptocurrency) {
        for (Cryptocurrency crypto : this.profile.getWallet()) {
            if (crypto.getCryptoName().equals(cryptocurrency.getCryptoName())) {
                return crypto;
            }
        }
        return null;
    }

    public void sellCrypto() {
        boolean quit = false;
        scanner = new Scanner(System.in);
        while (!quit) {
            System.out.println("Select the Cryptocurrency you want to sell: ");
            printWallet();
            int cryptoNum = scanner.nextInt();
            if (cryptoNum > this.profile.getWallet().size()) {
                System.out.println("please select a valid option");
            } else {
                if (this.profile.getWallet().size() != 0) {
                    processSale(cryptoNum);
                    quit = true;
                } else {
                    System.out.println("Your wallet is currently empty");
                }
            }
        }
    }

    public void processSale(int cryptoNum) {
        scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            Cryptocurrency cryptocurrency = this.profile.getWallet().get(cryptoNum - 1);
            Cryptocurrency currCryptocurrency = retrieveInfo(cryptocurrency.getCryptoCode(), 0);
            System.out.println(cryptocurrency.getCryptoCode() + "\t  - \t $" + currCryptocurrency.getPrice());
            System.out.println("Enter amount of "
                    + cryptocurrency.getCryptoCode().toUpperCase() + " you want to sell:");
            double amount = scanner.nextDouble();
            if (amount > cryptocurrency.getAmount() || amount < 0) {
                System.out.println("Invalid amount provided.");
            } else {
                this.profile.setBalance(this.profile.getBalance() + (amount * currCryptocurrency.getPrice()));
                cryptocurrency.setAmount(cryptocurrency.getAmount() - amount);
                System.out.println("Successfully sold " + amount + " " + cryptocurrency.getCryptoCode().toUpperCase()
                        + " for $"
                        + (amount * currCryptocurrency.getPrice()));
                if (cryptocurrency.getAmount() == 0) {
                    this.profile.getWallet().remove(cryptocurrency);
                }
                quit = true;
            }
        }
    }


    public void tradeCrypto() {
        boolean quit = false;
        scanner = new Scanner(System.in);
        while (!quit) {
            System.out.println("Select the Cryptocurrency you want to trade: ");
            printWallet();
            int cryptoNum = scanner.nextInt();
            if (cryptoNum > this.profile.getWallet().size()) {
                System.out.println("please select a valid option");
            } else {
                if (this.profile.getWallet().size() != 0) {
                    processTrade(cryptoNum);
                    quit = true;
                } else {
                    System.out.println("Your wallet is currently empty");
                }
            }
        }
    }


    public void processTrade(int cryptoNum) {
        scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("Select the Cryptocurrency you want to trade with: ");
            showBuyMenu();
        }
    }



    public Cryptocurrency retrieveInfo(String cryptoCode, double amount) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            URL url = new URL("https://api.coingecko.com/api/v3/exchange_rates");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return parse(responseContent.toString(), cryptoCode, amount);
    }

    public Cryptocurrency parse(String responseBody, String cryptoCode, double amount) {
        JSONObject data = new JSONObject(responseBody);
        JSONObject rates = data.getJSONObject("rates");
        double helper = rates.getJSONObject("cad").getDouble("value");
        JSONObject crypto = rates.getJSONObject(cryptoCode);
        double price = helper / crypto.getDouble("value");
        String name = crypto.getString("name");
        String unit = crypto.getString("unit");
        return new Cryptocurrency(name, cryptoCode, amount, price);
    }

//    public void storeProfiles() throws IOException {
//        Path profilePath = FileSystems.getDefault().getPath("data/Profiles.txt");
//        Path walletPath = FileSystems.getDefault().getPath("data/Wallets.txt");
//        try (BufferedWriter profileFile = Files.newBufferedWriter(profilePath);
//             BufferedWriter walletFile = Files.newBufferedWriter(walletPath)) {
//            for (Profile profile : this.profileUI.getProfiles()) {
//                profileFile.write(profile.getName() + ","
//                        + profile.getBalance() + "\n");
//                for (Cryptocurrency cryptocurrency : profile.getWallet()) {
//                    walletFile.write(cryptocurrency.getCryptoName() + ","
//                            + cryptocurrency.getCryptoCode() + ","
//                            + cryptocurrency.getAmount() + ","
//                            + cryptocurrency.getPrice() + "\n");
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("IOException: " + e.getMessage());
//        }
//    }
//
//    public void loadProfiles() throws IOException {
//        Path profilePath = FileSystems.getDefault().getPath("data/Profiles.txt");
//        Path walletPath = FileSystems.getDefault().getPath("data/Wallets.txt");
//
//        try {
//            scanner = new Scanner((ReadableByteChannel) Files.newBufferedWriter(profilePath));
//            scanner.useDelimiter(",");
//            while (scanner.hasNext()) {
//                String name = scanner.nextLine();
//                scanner.skip(scanner.delimiter());
//                double balance = scanner.nextDouble();
//                Profile profile = new Profile(name, balance, new ArrayList<>());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try (BufferedReader walletFile = Files.newBufferedReader(walletPath)) {
//            String input;
//            while ((input = walletFile.readLine()) != null) {
//                String[] data = input.split(",");
//                String cryptoName = data[0];
//                String cryptoCode = data[1];
//                double amount = Double.parseDouble(data[2]);
//                double price = Double.parseDouble(data[3]);
//                Cryptocurrency cryptocurrency = new Cryptocurrency(cryptoName, cryptoCode, amount, price);
//                profile.getWallet().add(cryptocurrency);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
