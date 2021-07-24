package model;

import javafx.collections.FXCollections;
import ui.ProfileUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfileData {
    private ProfileUI profileInstance = new ProfileUI();
    private static String fileName = "Profiles.txt";

    private List<Profile> profiles;

    public ProfileData() {
    }

    public ProfileUI getProfileInstance() {
        return profileInstance;
    }

    public void addProfile(Profile profile) {
        this.profileInstance.getProfiles().add(profile);
    }

    public void loadProfile() throws IOException {




//        profiles = FXCollections.observableArrayList();
//        Path path = Paths.get(fileName);
//        BufferedReader bufferedReader = Files.newBufferedReader(path);
//
//        String input;
//
//        try {
//            while ((input = bufferedReader.readLine()) != null) {
//                String[] itemPieces = input.split(" ");
//                String name = itemPieces[0];
//                double balance = Double.parseDouble(itemPieces[1]);
//                String walletStr = itemPieces[2];
//                List<Cryptocurrency> wallet = parseWallet(walletStr);
//                Profile profile = new Profile(name, balance, wallet);
//                profiles.add(profile);
//            }
//        } finally {
//            if (bufferedReader != null) {
//                bufferedReader.close();
//            }
//        }
    }

    public List<Cryptocurrency> parseWallet(String walletStr) {
        return new ArrayList<>();
    }

    public void storeProfile() throws IOException {
        Path path = Paths.get(fileName);
        BufferedWriter bufferedWriter = Files.newBufferedWriter(path);
        try {
            Iterator<Profile> profileIterator = profiles.iterator();
            while (profileIterator.hasNext()) {
                Profile profile = profileIterator.next();
                bufferedWriter.write(String.format("%s %s %s",
                        profile.getName(),
                        profile.getBalance(),
                        profile.getWallet()));
                bufferedWriter.newLine();
            }
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }



}
