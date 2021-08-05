package ui;

import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;


public class WalletFrame extends JFrame implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;

    DecimalFormat decimalFormat = new DecimalFormat("###0.0000");

    public WalletFrame(Profile profile) {
        super("CryptoWallet");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WalletFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocation(19, 176);
        setVisible(true);
        setResizable(false);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());
        panel.setSize(WIDTH, HEIGHT);
        panel.setLayout(new BorderLayout());
        this.add(panel);

        displayWallet(profile, panel);
    }

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

                JLabel amountLabel = new JLabel("       " + amount);
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
        if (e.getActionCommand().equals("back")) {
            this.dispose();
            new CryptoTraderGUI();
        }
    }
}
