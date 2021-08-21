package ui;

import exceptions.InvalidAmountException;
import exceptions.InvalidSelectionException;
import model.Cryptocurrency;
import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
  Represents a sell frame for CryptoTrader
 */
public class SellFrame extends Frame implements ActionListener {
    private final CryptoTraderGUI cryptoTraderGUI;
    private final Profile profile;

    // EFFECTS: Constructs a sell frame.
    public SellFrame(Profile profile, CryptoTraderGUI cryptoTraderGUI) {
        super("SellCrypto");
        this.profile = profile;
        this.cryptoTraderGUI = cryptoTraderGUI;

        GradientPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());
        add(panel);
        pack();

        displaySellMenu(panel);
    }

    // EFFECTS: Getter for profile.
    public Profile getProfile() {
        return profile;
    }


    // MODIFIES: panel
    // EFFECTS: Displays the sell menu.
    public void displaySellMenu(JPanel panel) {
        if (this.profile.getCryptoWallet().size() == 0) {
            displayEmptyWallet(panel);
        } else {
            int position = 20;
            int index = 1;
            for (Cryptocurrency cryptocurrency : this.profile.getCryptoWallet()) {
                String name = cryptocurrency.getCryptoName();
                double price = cryptocurrency.getAmount() * cryptocurrency.getCurrentPrice();

                JButton button = new JButton(index + ") " + name + "          $" + price);
                button.setBounds(25, position, 350, 50);

                button.setActionCommand(Integer.toString(index));
                button.addActionListener(this);

                panel.add(button);
                position += 55;
                index++;
            }
            panel.add(new JLabel(""));
        }
    }


    // MODIFIES: this
    // EFFECTS: Handles the event when user selects a cryptocurrency and performs the sell operation.
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = Integer.parseInt(e.getActionCommand());
        Cryptocurrency sellCrypto = this.profile.getCryptoWallet().get(index - 1);
        try {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount of "
                    + sellCrypto.getCryptoCode()
                    + " you want to sell: "));
            profile.sell(index, amount);
            JOptionPane.showMessageDialog(null, "Successfully sold "
                    + amount + " "
                    + sellCrypto.getCryptoCode()
                    + " for $" + (amount * sellCrypto.getPrice()));
            cryptoTraderGUI.setBalanceLabel("Balance: $" + this.profile.getBalance());
            new WalletFrame(profile);
            dispose();
        } catch (InvalidAmountException exception) {
            JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
        } catch (InvalidSelectionException | NumberFormatException | NullPointerException exception) {
            // Retry if caught
        }
    }
}
