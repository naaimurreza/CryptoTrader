package ui;

import model.Cryptocurrency;
import model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TradeFrame extends JFrame implements ActionListener {
    private static int WIDTH = 400;
    private static int HEIGHT = 500;

    private Profile profile;
    private List<Cryptocurrency> market;

    public TradeFrame(Profile profile, List<Cryptocurrency> market) {
        super("TradeCrypto");
        this.profile = profile;
        this.market = market;
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WalletFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);
        setVisible(true);
        setResizable(false);

        displayTradeMenu();
    }

    public void displayTradeMenu() {
        int postition = 20;
        int index = 1;
        for (Cryptocurrency cryptocurrency : this.profile.getCryptoWallet()) {
            String name = cryptocurrency.getCryptoName();
            double amount = cryptocurrency.getAmount();
            double price = cryptocurrency.getCurrentPrice();

            JButton button = new JButton(index + ") " + name + "          $" + price);
            button.setBounds(25, postition, 350, 50);

            button.setActionCommand(Integer.toString(index));
            button.addActionListener(this);

            add(button);
            postition += 55;
            index++;
        }
        JLabel placeHolder = new JLabel("");
        add(placeHolder);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
//        int index = Integer.parseInt(e.getActionCommand());
//        Cryptocurrency tradeCrypto = this.profile.getCryptoWallet().get(index - 1);
//        try {
//            profile.trade(index, );
//            JOptionPane.showMessageDialog(null, "Successfully sold "
//                    + amount + " "
//                    + tradeCrypto.getCryptoCode()
//                    + " for $" + (amount * tradeCrypto.getPrice()));
//            dispose();
//        } catch (InvalidAmountException exception) {
//            JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
//        } catch (InvalidSelectionException exception) {
//            // Won't occur in GUI version
//        }
    }
}
