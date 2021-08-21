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

    }

}
