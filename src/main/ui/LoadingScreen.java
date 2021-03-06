package ui;

import javax.swing.*;
import java.awt.*;

/*
  Loading screen in CryptoTrader
  @author Naaimur Reza
 */
public class LoadingScreen extends JFrame {
    JProgressBar bar = new JProgressBar();
    JLabel progressLabel = new JLabel(bar.getString());

    // EFFECTS: Constructs a loading screen
    public LoadingScreen() {
        super("Loading...");
        setSize(400, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setVisible(true);

        GradientPanel panel = new GradientPanel();

        bar.setValue(0);
        bar.setBounds(0, 50, 400, 30);
        bar.setStringPainted(false);
        bar.setVisible(true);
        bar.setFont(new Font("MV Boli", Font.BOLD, 15));

        progressLabel.setBounds(180, 80, 400, 20);
        progressLabel.setFont(new Font("MV Boli", Font.BOLD, 15));

        JLabel loadLabel = new JLabel("Loading...");
        loadLabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
        loadLabel.setBounds(147, 20, 400, 40);

        panel.add(loadLabel);
        panel.add(bar);

        fill();
        pack();
    }

    // MODIFIES: this
    // EFFECTS: Fills up the loading bar
    public void fill() {
        int counter = 0;
        while (counter <= 100) {
            add(progressLabel);
            bar.setValue(counter);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter += 5;
            progressLabel.setText(bar.getString());
        }
        progressLabel.setText("Done!");
        this.dispose();
    }
}
