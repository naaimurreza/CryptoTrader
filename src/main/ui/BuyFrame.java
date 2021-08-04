package ui;

import model.Cryptocurrency;
import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BuyFrame extends JFrame {
    private static int WIDTH = 400;
    private static int HEIGHT = 500;

    public BuyFrame(Profile profile, List<Cryptocurrency> market) {
        super("BuyCrypto");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WalletFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setBackground(Color.lightGray);
        setResizable(false);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());
        panel.setSize(WIDTH, HEIGHT);
        panel.setLayout(new BorderLayout());
        //this.add(panel);

        displayMarket(profile, market, panel);
    }

    public void displayMarket(Profile profile, List<Cryptocurrency> market, JPanel panel) {
        int postition = 20;
        for (int i  = 0; i  < market.size(); i++) {
            String name = profile.getCryptoWallet().get(i).getCryptoName();
            double amount = profile.getCryptoWallet().get(i).getAmount();
            double price = profile.getCryptoWallet().get(i).getCurrentPrice();

            JButton button = new JButton((i + 1) + ") " + name + "          $" + price);
            button.setBounds(25, postition, 350, 50);

            add(button);
            postition += 55;
        }
        JLabel label = new JLabel("");
        panel.add(label);
        JLabel label2 = new JLabel("");
        panel.add(label2);
    }
}
