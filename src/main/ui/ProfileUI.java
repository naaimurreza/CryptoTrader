package ui;


import model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProfileUI {
    private List<Profile> profiles = new ArrayList<>();

    private static Scanner scanner;

    public ProfileUI() {
        runProfileUI();
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public void runProfileUI() {
        boolean quit = false;
        int command;
        scanner = new Scanner(System.in);
        if (profiles.size() == 0) {
            System.out.println("Looks like you don't have a profile yet.");
            while (!quit) {
                displayProfileMenu();
                command = scanner.nextInt();
                if (command == 3) {
                    quit = true;
                    System.out.println("GoodBye");
                } else {
                    if (command == 2 && this.profiles.size() != 0) {
                        quit = true;
                    }
                    processCommand(command);
                }
            }
        }
    }

    private void displayProfileMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("\t1: To create a new profile");
        System.out.println("\t2: To load an existing profile");
        System.out.println("\t3: To quit");
    }

    private void processCommand(int command) {
        switch (command) {
            case 1:
                createProfile();
                break;
            case 2:
                loadProfile();
                break;
            default:
                System.out.println("Please enter a valid option");
                break;
        }
    }

    public void createProfile() {
        scanner = new Scanner(System.in);
        System.out.println("Enter a name for your profile:");
        String name = scanner.nextLine();
        System.out.println("Enter balance for your new profile:");
        double balance = scanner.nextInt();
        Profile newProfile = new Profile(name, balance);
        this.profiles.add(newProfile);
        new CryptoTraderUI(newProfile);
    }

    public void loadProfile() {
        if (this.profiles.size() == 0) {
            System.out.println("Currently no profiles to show");
        } else {
            boolean quit = false;
            while (!quit) {
                scanner = new Scanner(System.in);
                printProfiles();
                int input = scanner.nextInt();
                if (input > this.profiles.size() || input <= 0) {
                    System.out.println("Enter a valid number");
                } else {
                    this.profiles.get(input - 1).runCryptoTrader();
                    quit = true;
                }
            }

        }
    }

    public void printProfiles() {
        System.out.println("\nSelect a profile:");
        for (int i = 1; i <= this.profiles.size(); i++) {
            System.out.println("\t" + i + ")" + this.profiles.get(i - 1).getName());
        }
    }






}
