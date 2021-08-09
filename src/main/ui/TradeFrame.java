package ui;

import model.Cryptocurrency;
import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
  Represents a trade frame in CryptoTrader
 */
public class TradeFrame extends Frame {
    private final Profile profile;
    private final List<Cryptocurrency> market;

    // EFFECTS: Constructs a trade frame.
    public TradeFrame(Profile profile, List<Cryptocurrency> market) {
        super("TradeCrypto");
        this.profile = profile;
        this.market = market;

        GradientPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());
        add(panel);
        pack();

        displayTradeMenu(panel);
    }

    // MODIFIES: panel
    // EFFECTS: Displays the trade menu
    public void displayTradeMenu(JPanel panel) {
        int postition = 20;
        int index = 1;
        for (Cryptocurrency cryptocurrency : this.profile.getCryptoWallet()) {
            String name = cryptocurrency.getCryptoName();
            double price = cryptocurrency.getCurrentPrice();

            JButton button = new JButton(index + ") " + name + "          $" + price);
            button.setBounds(25, postition, 350, 50);

            panel.add(button);
            postition += 55;
            index++;
        }
        panel.add(new JLabel(""));
    }

    // MODIFIES: panel
    // EFFECTS: Displays the market.
    public void displayMarket(JPanel panel) {
        int position = 30;
        int index = 1;
        JLabel prompt = new JLabel("Select the cryptocurrency you want to trade with: ");
        prompt.setBounds(0, 0, 350, 40);
        panel.add(panel);
        for (Cryptocurrency cryptocurrency : this.market) {
            String name = cryptocurrency.getCryptoName();
            double price = cryptocurrency.getCurrentPrice();

            JButton button = new JButton("       " + name + "          $" + price);
            button.setBounds(25, position, 350, 50);

            ImageIcon icon = new ImageIcon("./data/icons/" + cryptocurrency.getCryptoCode() + ".PNG");
            button.setIcon(icon);

            panel.add(button);
            position += 55;
            index++;
        }
        panel.add(new JLabel(""));

    }
}
