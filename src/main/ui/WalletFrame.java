package ui;

import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/*
  Wallet frame for CryptoTrader
   @author Naaimur Reza
 */
public class WalletFrame extends Frame {
    DecimalFormat decimalFormat = new DecimalFormat("###0.0000");
    DecimalFormat amountFormat = new DecimalFormat("###0.0###");


    // EFFECTS: Constructs a wallet frame
    public WalletFrame(Profile profile) {
        super("CryptoWallet");
        setLocation(19, 176);
        setVisible(false);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());
        this.add(panel);

        displayWallet(profile, panel);
        new LoadingScreen();
        setVisible(true);
    }

    // MODIFIES: this, pamel
    // EFFECTS: Displays the wallet
    public void displayWallet(Profile profile, JPanel panel) {
        if (profile.getCryptoWallet().size() == 0) {
            displayEmptyWallet(panel);
        } else {
            int position = 20;
            for (int i  = 0; i  < profile.getCryptoWallet().size(); i++) {
                double amount = profile.getCryptoWallet().get(i).getAmount();
                double price = profile.getCryptoWallet().get(i).getCurrentPrice();

                RoundedPanel roundedPanel = new RoundedPanel();
                roundedPanel.setBounds(25, position, 350, 50);
                roundedPanel.setBackground(new Color(253, 253, 253, 77));

                JLabel amountLabel = new JLabel("       " + amountFormat.format(amount));
                JLabel priceLabel = new JLabel("      $" + decimalFormat.format(price * amount));

                JLabel nameLabel = new JLabel((i + 1) + ") " + profile.getCryptoWallet().get(i).getCryptoName());
                roundedPanel.add(nameLabel);
                roundedPanel.add(amountLabel);
                roundedPanel.add(priceLabel);

                panel.add(roundedPanel);
                position += 55;
            }
            JLabel label = new JLabel("");
            panel.add(label);
        }

    }

}
