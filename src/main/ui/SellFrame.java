package ui;

import exceptions.InvalidAmountException;
import exceptions.InvalidSelectionException;
import model.Cryptocurrency;
import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SellFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;

    private final Profile profile;

    public SellFrame(Profile profile) {
        super("SellCrypto");
        this.profile = profile;
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WalletFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());
        add(panel);
        pack();

        displaySellMenu(panel);
    }

    public void displaySellMenu(JPanel panel) {
        if (this.profile.getCryptoWallet().size() == 0) {
            displayEmptyWallet(panel);
        } else {
            int position = 20;
            int index = 1;
            for (Cryptocurrency cryptocurrency : this.profile.getCryptoWallet()) {
                String name = cryptocurrency.getCryptoName();
                double price = cryptocurrency.getCurrentPrice();

                JButton button = new JButton(index + ") " + name + "          $" + price);
                button.setBounds(25, position, 350, 50);

                button.setActionCommand(Integer.toString(index));
                button.addActionListener(this);

                panel.add(button);
                position += 55;
                index++;
            }
            JLabel placeHolder = new JLabel("");
            panel.add(placeHolder);
        }
    }


    public void displayEmptyWallet(JPanel panel) {
        JLabel messageLabel = new JLabel("Looks like you have an empty wallet!");
        messageLabel.setBounds(39, 210,350, 200);
        messageLabel.setFont(new Font("Zapf Dingbats", Font.PLAIN, 20));

        JLabel picLabel = new JLabel(new ImageIcon("./data/icons/emptyWallet.png"));

        panel.add(picLabel);
        panel.add(messageLabel);
        picLabel.setBounds(90,90,200,190);

        JLabel placeHolder = new JLabel("");
        panel.add(placeHolder);
    }


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
            new WalletFrame(profile);
            dispose();
        } catch (InvalidAmountException exception) {
            JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
        } catch (InvalidSelectionException | NumberFormatException | NullPointerException exception) {
            // Retry if caught
        }
    }
}
