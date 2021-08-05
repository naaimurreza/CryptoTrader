package ui;

import model.Cryptocurrency;
import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private static final String JSON_STORE = "./data/profile.json";
    private static final String JSON_MARKET = "./data/cryptoMarket.json";
    private JsonWriter jsonWriter;
    private  Profile profile;
    private List<Cryptocurrency> market;

    private BuyFrame buyFrame;
    private WalletFrame walletFrame;

    ImageIcon buy = new ImageIcon("./data/icons/buy2.png");
    ImageIcon sell = new ImageIcon("./data/icons/sell3.png");
    ImageIcon trade = new ImageIcon("./data/icons/trade.png");
    ImageIcon wallet = new ImageIcon("./data/icons/wallet.png");
    ImageIcon quit = new ImageIcon("./data/icons/quit.png");


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
            JOptionPane.showMessageDialog(null, "Successfully loaded CryptoTrader!",
                    "CryptoTrader", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load CryptoTrader.",
                    "CryptoTrader", JOptionPane.ERROR_MESSAGE);
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

        buyButton.setIcon(buy);
        buyButton.setHorizontalTextPosition(JButton.CENTER);
        buyButton.setVerticalTextPosition(JButton.BOTTOM);

        sellButton.setIcon(sell);
        sellButton.setHorizontalTextPosition(JButton.CENTER);
        sellButton.setVerticalTextPosition(JButton.BOTTOM);

        tradeButton.setIcon(trade);
        tradeButton.setHorizontalTextPosition(JButton.CENTER);
        tradeButton.setVerticalTextPosition(JButton.BOTTOM);

        walletButton.setIcon(wallet);
        walletButton.setHorizontalTextPosition(JButton.CENTER);
        walletButton.setVerticalTextPosition(JButton.BOTTOM);

        quitButton.setIcon(quit);
        quitButton.setHorizontalTextPosition(JButton.CENTER);
        quitButton.setVerticalTextPosition(JButton.BOTTOM);

        quitButton.setActionCommand("quit");
        quitButton.addActionListener(this);

        walletButton.setActionCommand("wallet");
        walletButton.addActionListener(this);

        buyButton.setActionCommand("buy");
        buyButton.addActionListener(this);

        sellButton.setActionCommand("sell");
        sellButton.addActionListener(this);

        tradeButton.setActionCommand("trade");
        tradeButton.addActionListener(this);

        walletButton.setBounds(50, 320, 96, 75);
        buyButton.setBounds(150, 320, 96, 75);
        sellButton.setBounds(250, 320, 96, 75);
        tradeButton.setBounds(350, 320, 96, 75);
        quitButton.setBounds(450, 320, 96, 75);

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

        balanceLabel.setBounds(110, 190, 400, 100);
        balanceLabel.setFont(new Font("Zapf Dingbats", Font.PLAIN, 25));

        brandLabel.setBounds(110, 50, 400, 100);
        brandLabel.setFont(new Font("Zapf Dingbats", Font.BOLD, 60));

        welcomeText.setBounds(170, 150, 400, 100);
        welcomeText.setFont(new Font("Zapf Dingbats", Font.PLAIN, 25));

        panel.add(balanceLabel);
        panel.add(brandLabel);
        panel.add(welcomeText);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("wallet")) {
            if (walletFrame != null) {
                walletFrame.dispose();
            }
            walletFrame = new WalletFrame(this.profile);
        } else if (e.getActionCommand().equals("buy")) {
            if (buyFrame != null) {
                buyFrame.dispose();
            }
            buyFrame = new BuyFrame(this.profile, market);
        } else if (e.getActionCommand().equals("sell")) {
            new SellFrame(this.profile);
        } else if (e.getActionCommand().equals("trade")) {
            new TradeFrame(this.profile, market);
        } else if (e.getActionCommand().equals("quit")) {
            try {
                jsonWriter.open();
                jsonWriter.write(profile);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null,"Auto-save complete!",
                        "CryptoTrader",JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null, "Auto-save failed.", "CryptoTrader", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(26);
        }

    }
}
