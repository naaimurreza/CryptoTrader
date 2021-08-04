package ui;

import model.Cryptocurrency;
import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

/*
  Main class of CryptoTrader
 */

// 50 shades of grey
// Color color1 = new Color(189, 195, 199);
//  Color color2 = new Color(44, 62, 80);


public class CryptoTraderGUI extends JFrame implements ActionListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;

    private static JLabel label;
    private static JTextField field;

    private static final String JSON_STORE = "./data/profile.json";
    private static final String JSON_MARKET = "./data/cryptoMarket.json";
    private JsonWriter jsonWriter;
    private  Profile profile;
    private List<Cryptocurrency> market;

    DecimalFormat decimalFormat = new DecimalFormat("###0.0000");

    public CryptoTraderGUI() {
        super("CryptoTrader");
        setResizable(false);
        initializeFields();
        initializeGraphics();

    }

    private void initializeFields() {
        JsonReader jsonReaderProfile = new JsonReader(JSON_STORE);
        JsonReader jsonReaderMarket = new JsonReader(JSON_MARKET);
        jsonWriter = new JsonWriter(JSON_STORE);
        try {
            this.profile = jsonReaderProfile.read();
            this.market = jsonReaderMarket.readMarket();
            System.out.println("Successfully loaded CryptoTrader");
        } catch (IOException e) {
            System.out.println("\nLooks like you don't have a profile yet.\n");
        }
    }

    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        GradientPanel panel = new GradientPanel();
        panel.setSize(WIDTH, HEIGHT);
        this.add(panel);

        initializeLabel(panel);
        initializeButton(panel);
    }

    public void initializeButton(JPanel panel) {
        JButton walletButton = new JButton("CryptoWallet");
        JButton buyButton = new JButton("BuyCrypto");
        JButton sellButton = new JButton("SellCrypto");
        JButton tradeButton = new JButton("TradeCrypto");
        JButton quitButton = new JButton("Quit");

        quitButton.setActionCommand("quit");
        quitButton.addActionListener(this);

        walletButton.setActionCommand("wallet");
        walletButton.addActionListener(this);

        buyButton.setActionCommand("buy");
        buyButton.addActionListener(this);

        walletButton.setBounds(50, 320, 96, 50);
        buyButton.setBounds(150, 320, 96, 50);
        sellButton.setBounds(250, 320, 96, 50);
        tradeButton.setBounds(350, 320, 96, 50);
        quitButton.setBounds(450, 320, 96, 50);

        panel.add(walletButton);
        panel.add(buyButton);
        panel.add(sellButton);
        panel.add(tradeButton);
        panel.add(quitButton);
    }

    public void initializeLabel(JPanel panel) {
        JLabel brandLabel = new JLabel("CryptoTrader©");
        JLabel welcomeText = new JLabel("CryptoMaster : " + this.profile.getName());
        JLabel balanceLabel = new JLabel("Balance: $" + this.profile.getBalance());

        balanceLabel.setBounds(100, 190, 400, 100);
        balanceLabel.setFont(new Font("Gotham", Font.BOLD, 25));

        brandLabel.setBounds(115, 50, 400, 100);
        brandLabel.setFont(new Font("Gotham", Font.BOLD, 50));

        welcomeText.setBounds(150, 150, 400, 100);
        welcomeText.setFont(new Font("Gotham", Font.BOLD, 25));

        panel.add(balanceLabel);
        panel.add(brandLabel);
        panel.add(welcomeText);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("wallet")) {
            new WalletFrame(this.profile);
        }
        if (e.getActionCommand().equals("quit")) {
            System.exit(26);
        }
        if (e.getActionCommand().equals("buy")) {
            new BuyFrame(this.profile, market);
        }


    }
}
