package ui;

import model.Cryptocurrency;
import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

/*
  Represents a trade frame in CryptoTrader
 */
public class TradeFrame extends Frame implements ActionListener {
    private final Profile profile;
    private final List<Cryptocurrency> market;
    private final JPanel panel;
    private Cryptocurrency takeCrypto;
    private Cryptocurrency giveCrypto;
    private CryptoTraderGUI cryptoTraderGUI;

    JLabel givePic;
    JLabel takePic;
    JLabel giveAmount;
    JLabel takeAmount;

    JComboBox giveComboBox;
    JComboBox takeComboBox;

    DecimalFormat decimalFormat = new DecimalFormat("###0.0000");

    // EFFECTS: Constructs a trade frame.
    public TradeFrame(Profile profile, List<Cryptocurrency> market, CryptoTraderGUI cryptoTraderGUI) {
        super("TradeCrypto");
        this.profile = profile;
        this.market = market;
        this.cryptoTraderGUI = cryptoTraderGUI;
        this.giveCrypto = profile.getCryptoWallet().get(0);
        this.takeCrypto = market.get(0);


        panel = new GradientPanel();
        panel.setLayout(new BorderLayout());
        add(panel);
        pack();

        displayTradeMenu();
    }

    // MODIFIES: panel
    // EFFECTS: Displays the trade menu
    public void displayTradeMenu() {
        initializeLabels();
        initializeImages();
        String[] options = new String[this.profile.getCryptoWallet().size()];
        for (int i = 0; i < profile.getCryptoWallet().size(); i++) {
            options[i] = profile.getCryptoWallet().get(i).getCryptoCode();
        }

        giveComboBox = new JComboBox(options);
        giveComboBox.setBounds(34, 200, 135, 20);
        giveComboBox.addActionListener(this);

        String[] marketList = new String[this.market.size()];
        for (int i = 0; i < this.market.size(); i++) {
            marketList[i] = this.market.get(i).getCryptoCode();
        }

        takeComboBox = new JComboBox(marketList);
        takeComboBox.setBounds(230, 200, 135, 20);
        takeComboBox.addActionListener(this);

        JButton tradeButton = new JButton("TRADE");
        tradeButton.setBounds(10, 240, 380, 50);
        tradeButton.addActionListener(this);
        tradeButton.setActionCommand("trade");

        panel.add(tradeButton);
        panel.add(takeComboBox);
        panel.add(giveComboBox);
        panel.add(new JLabel(""));
    }

    public void initializeLabels() {
        JLabel giveLabel = new JLabel("Give");
        giveLabel.setFont(new Font("Zapf Dingbats", Font.PLAIN, 25));
        giveLabel.setBounds(73, 0, 100, 100);

        JLabel takeLabel = new JLabel("Take");
        takeLabel.setFont(new Font("Zapf Dingbats", Font.PLAIN, 25));
        takeLabel.setBounds(265, 0, 100, 100);

        giveAmount = new JLabel(decimalFormat.format(this.profile.getCryptoWallet().get(0).getAmount()));
        giveAmount.setFont(new Font("Zapf Dingbats", Font.PLAIN, 19));
        giveAmount.setBounds(77, 168, 300, 20);

        double givePrice = giveCrypto.getCurrentPrice() * giveCrypto.getAmount();
        double amount = givePrice / takeCrypto.getCurrentPrice();
        takeAmount = new JLabel(decimalFormat.format(amount));
        takeAmount.setFont(new Font("Zapf Dingbats", Font.PLAIN, 19));
        takeAmount.setBounds(260, 168, 300, 20);

        panel.add(takeAmount);
        panel.add(giveAmount);
        panel.add(takeLabel);
        panel.add(giveLabel);
    }

    public void initializeImages() {
        givePic = new JLabel(new ImageIcon("./data/icons/"
                + this.profile.getCryptoWallet().get(0).getCryptoCode() + "-big.png"));
        givePic.setBounds(62,80, 75, 75);

        takePic = new JLabel(new ImageIcon("./data/icons/"
                + this.market.get(0).getCryptoCode() + "-big.png"));
        takePic.setBounds(256,80, 75, 75);

        panel.add(takePic);
        panel.add(givePic);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == giveComboBox) {
            giveCrypto = this.profile.getCryptoWallet().get(giveComboBox.getSelectedIndex());
            givePic.setIcon(new ImageIcon("./data/icons/" + giveComboBox.getSelectedItem() + "-big.png"));
            giveAmount.setText(Double.toString(profile.getCryptoWallet().get(giveComboBox.getSelectedIndex()).getAmount()));
            double givePrice = giveCrypto.getCurrentPrice() * giveCrypto.getAmount();
            double amount = givePrice / takeCrypto.getCurrentPrice();
            takeAmount.setText(decimalFormat.format(amount));
        }
        if (e.getSource() == takeComboBox) {
            takeCrypto = this.market.get(takeComboBox.getSelectedIndex());
            takePic.setIcon(new ImageIcon("./data/icons/" + takeComboBox.getSelectedItem() + "-big.png"));
            double givePrice = giveCrypto.getCurrentPrice() * giveCrypto.getAmount();
            double amount = givePrice / takeCrypto.getCurrentPrice();
            takeAmount.setText(decimalFormat.format(amount));
        }
        if (e.getActionCommand().equals("trade")) {
            this.profile.trade(giveCrypto, takeCrypto);
            JOptionPane.showMessageDialog(null, "Successfully traded "
                    + decimalFormat.format(giveCrypto.getAmount()) + " " + giveCrypto.getCryptoCode() + " for "
                    + decimalFormat.format(takeCrypto.getAmount()) + " " + takeCrypto.getCryptoCode() + "!");
            this.dispose();
            if (cryptoTraderGUI.getWalletFrame() != null) {
                cryptoTraderGUI.getWalletFrame().dispose();
            }
            cryptoTraderGUI.setWalletFrame(new WalletFrame(this.profile));
        }
    }
}
