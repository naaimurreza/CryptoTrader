package ui;

import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;


public class WalletFrame extends JFrame implements ActionListener {
    private static int WIDTH = 400;
    private static int HEIGHT = 500;

    DecimalFormat decimalFormat = new DecimalFormat("###0.0000");

    public WalletFrame(Profile profile) {
        super("CryptoWallet");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WalletFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        //setLocation(1020, 176);
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
        int postition = 20;
        for (int i  = 0; i  < profile.getCryptoWallet().size(); i++) {
            double amount = profile.getCryptoWallet().get(i).getAmount();
            double price = profile.getCryptoWallet().get(i).getCurrentPrice();

            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setBounds(25, postition, 350, 50);
            roundedPanel.setBackground(Color.lightGray);

            JLabel amountLabel = new JLabel("       "
                    + Double.toString(amount));
            JLabel priceLabel = new JLabel("      $"
                    + decimalFormat.format(price * amount));

            JLabel nameLabel = new JLabel(profile.getCryptoWallet().get(i).getCryptoName());
            roundedPanel.add(nameLabel);
            roundedPanel.add(amountLabel);
            roundedPanel.add(priceLabel);

            panel.add(roundedPanel);
            postition += 55;
        }
        JLabel label = new JLabel("");
        panel.add(label);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            this.dispose();
            new CryptoTraderGUI();
        }
    }
}
