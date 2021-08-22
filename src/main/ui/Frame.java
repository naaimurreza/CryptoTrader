package ui;

import javax.swing.*;
import java.awt.*;

/*
  The frame superclass
   @author Naaimur Reza
 */
public class Frame extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;

    // EFFECTS: Constructs a new frame object
    public Frame(String title) {
        super(title);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WalletFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: Displays empty wallet image and tells user they have an empty wallet if they do.
    public void displayEmptyWallet(JPanel panel) {
        JLabel messageLabel = new JLabel("Looks like you have an empty wallet!");
        messageLabel.setBounds(39, 210,350, 200);
        messageLabel.setFont(new Font("Zapf Dingbats", Font.PLAIN, 20));

        JLabel picLabel = new JLabel(new ImageIcon("./data/icons/emptyWallet.png"));

        panel.add(picLabel);
        panel.add(messageLabel);
        picLabel.setBounds(90,90,200,190);

        panel.add(new JLabel(""));
    }


}
