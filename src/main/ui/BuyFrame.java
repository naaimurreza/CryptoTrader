package ui;

import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import model.Cryptocurrency;
import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;


/*
  Represents a buy frame for CryptoTrader
 */
public class BuyFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;

    private final Profile profile;
    private final List<Cryptocurrency> market;
    private final CryptoTraderGUI cryptoTraderGUI;

    DecimalFormat decimalFormat = new DecimalFormat("###0.00000000000");


    // EFFECTS: constructs a new buy frame.
    public BuyFrame(Profile profile, List<Cryptocurrency> market, CryptoTraderGUI cryptoTraderGUI) {
        super("CryptoMarket");
        this.profile = profile;
        this.market = market;
        this.cryptoTraderGUI = cryptoTraderGUI;
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(BuyFrame.DISPOSE_ON_CLOSE);
        setVisible(false);
        setResizable(false);
        setLocation(1020, 176);
        GradientPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());
        add(panel);
        pack();
        setLocationRelativeTo(null);
        pack();

        displayMarket(panel);
        new LoadingScreen();
        setVisible(true);
    }


    // EFFECTS: Getter for profile.
    public Profile getProfile() {
        return profile;
    }


    // MODIFIES: panel, this
    // EFFECTS: Displays the market
    public void displayMarket(JPanel panel) {
        int position = 20;
        int index = 1;
        for (Cryptocurrency cryptocurrency : this.market) {
            String name = cryptocurrency.getCryptoName();
            double price = cryptocurrency.getCurrentPrice();

            JButton button = new JButton("       " + name + "          $" + price);
            button.setBounds(25, position, 350, 50);

            ImageIcon icon = new ImageIcon("./data/icons/" + cryptocurrency.getCryptoCode() + ".PNG");
            button.setIcon(icon);

            button.setActionCommand(Integer.toString(index));
            button.addActionListener(this);

            panel.add(button);
            position += 55;
            index++;
        }
        panel.add(new JLabel(""));
        setLocation(1020, 176);
    }

    // MODIFIES: this
    // EFFECTS: Handles the event when user selects a cryptocurrency and performs the buy operation
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = Integer.parseInt(e.getActionCommand());
        Cryptocurrency buyCrypto = this.market.get(index - 1);
        try {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount of "
                    + buyCrypto.getCryptoCode()
                    + " you want to buy: "));
            profile.buy(buyCrypto, amount);
            JOptionPane.showMessageDialog(null, "Successfully purchased "
                    + amount + " "
                    +  buyCrypto.getCryptoCode()
                    + " for $" + (amount * buyCrypto.getPrice()));
            cryptoTraderGUI.setBalanceLabel("Balance: $" + decimalFormat.format(this.profile.getBalance()));
            if (cryptoTraderGUI.getWalletFrame() != null) {
                cryptoTraderGUI.getWalletFrame().dispose();
            }
            cryptoTraderGUI.setWalletFrame(new WalletFrame(this.profile));
        } catch (InsufficientBalanceException exception) {
            JOptionPane.showMessageDialog(null, "Purchase failed due to insufficient balance.");
        } catch (InvalidAmountException | NullPointerException | NumberFormatException exception) {
            // retry if caught
        }


    }
}
