package ui;

import javax.swing.*;
import java.awt.*;

/*
  Represents a rounded panel
  Citation: https://www.codeproject.com/Articles/114959/Rounded-Border-JPanel-JPanel-graphics-improvements
 */
public class RoundedPanel extends JPanel {

    protected boolean highQuality = true;
    protected Dimension arcs = new Dimension(40, 40);
    protected int shadowGap = 5;


    // EFFECTS: Constructs a rounded panel object
    public RoundedPanel() {
        super();
        setOpaque(false);
    }


    // EFFECTS: Overrides the paintComponent() method so produce rounded panels
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowGap,
                height - shadowGap, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width - shadowGap,
                height - shadowGap, arcs.width, arcs.height);

    }
}
