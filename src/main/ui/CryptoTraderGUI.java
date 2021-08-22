package ui;

import model.Cryptocurrency;
import model.MarketReader;
import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
  Represents a CryptoTrader game with a GUI
 */
public class CryptoTraderGUI extends JFrame implements ActionListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;

    private static final String JSON_STORE = "./data/profile.json";
    private JsonWriter jsonWriter;
    private Profile profile;
    private final List<Cryptocurrency> market = new ArrayList<>();

    DecimalFormat decimalFormat = new DecimalFormat("###0.00000000000");

    private BuyFrame buyFrame;
    private WalletFrame walletFrame;
    private JLabel balanceLabel;

    ImageIcon buy = new ImageIcon("./data/icons/buy2.png");
    ImageIcon sell = new ImageIcon("./data/icons/sell3.png");
    ImageIcon trade = new ImageIcon("./data/icons/trade.png");
    ImageIcon wallet = new ImageIcon("./data/icons/wallet.png");
    ImageIcon quit = new ImageIcon("./data/icons/quit.png");

    private final String[][] marketList = {{"BTC","Bitcoin"}, {"ETH", "Ethereum"},
            {"LTC", "Litecoin"}, {"DOGE", "Dogecoin"}, {"ADA", "Cardano"},
            {"USDT", "Tether"}, {"XRP", "Ripple"}, {"DOT", "Pokadot"}};


    // EFFECTS: Constructs the JFrame, creates a GradientPanel and initializes fields and graphics
    public CryptoTraderGUI() {
        super("CryptoTrader");
        setResizable(false);
        setLayout(new BorderLayout());
        GradientPanel panel = new GradientPanel();
        panel.setSize(WIDTH, HEIGHT);
        panel.setLayout(new BorderLayout());
        add(panel);
        pack();
        initializeFields();
        initializeGraphics(panel);

    }

    public void setBalanceLabel(String text) {
        this.balanceLabel.setText(text);
    }

    public WalletFrame getWalletFrame() {
        return walletFrame;
    }

    public void setWalletFrame(WalletFrame walletFrame) {
        this.walletFrame = walletFrame;
    }

    // MODIFIES: this
    // EFFECTS: Initializes the fields
    private void initializeFields() {
        JsonReader jsonReaderProfile = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        MarketReader marketReader = new MarketReader();
        try {
            this.profile = jsonReaderProfile.read();
            for (String[] code : this.marketList) {
                this.market.add(marketReader.retrieveInfo(code[0], code[1], 0));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load CryptoTrader.",
                    "CryptoTrader", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this, panel
    // initializes the labels and buttons for the main menu
    private void initializeGraphics(JPanel panel) {
        initializeButtons(panel);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        initializeLabel(panel);
        add(new JLabel(""));
        panel.add(new JLabel(""));
    }


    // MODIFIES: panel
    // EFFECTS: Creates and adds buttons to panel
    public void initializeButtons(JPanel panel) {
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

        initializeButtonsHelper(quitButton, walletButton, buyButton, sellButton, tradeButton, panel);

    }

    // MODIFIES: quitButton, walletButton, buyButton, sellButton, tradeButton, panel
    // EFFECTS: Adds action listener to the buttons, sets their bounds and adds them to panel.
    public void initializeButtonsHelper(JButton quitButton,
                                        JButton walletButton,
                                        JButton buyButton,
                                        JButton sellButton,
                                        JButton tradeButton,
                                        JPanel panel) {
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

        panel.add(new JLabel(""));
    }


    // MODIFIES: panel
    // EFFECTS: Creates and adds the labels to panel
    public void initializeLabel(JPanel panel) {
        JLabel brandLabel = new JLabel("CryptoTrader©");
        JLabel welcomeText = new JLabel("CryptoMaster : " + this.profile.getName());
        this.balanceLabel = new JLabel("Balance: $" + decimalFormat.format(this.profile.getBalance()));

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


    // MODIFIES: this
    // EFFECTS: Executes appropriate event when a button on the main menu is pressed.
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
            buyFrame = new BuyFrame(this.profile, market, this);
        } else if (e.getActionCommand().equals("sell")) {
            new SellFrame(this.profile, this);
            if (walletFrame != null) {
                walletFrame.dispose();
            }
            walletFrame = new WalletFrame(this.profile);
        } else if (e.getActionCommand().equals("trade")) {
            new TradeFrame(this.profile, market, this);
        } else if (e.getActionCommand().equals("quit")) {
            quitButtonHelper();
        }
    }



    // EFFECTS: Saves the data to file and closes the application when quit button is pressed
    public void quitButtonHelper() {
        try {
            jsonWriter.open();
            jsonWriter.write(profile);
            jsonWriter.close();
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(null, "Auto-save failed.", "CryptoTrader", JOptionPane.ERROR_MESSAGE);
        }
        System.exit(26);
    }
}
