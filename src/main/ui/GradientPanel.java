package ui;

import javax.swing.*;
import java.awt.*;

/*
  Represents a panel with gradients background color
  Citation: https://stackoverflow.com/questions/14364291/jpanel-gradient-background
 */
public class GradientPanel extends JPanel {
    // EFFECTS: Constructs a gradientPanel object
    public GradientPanel() {
        super();
    }

    // EFFECTS: Overrides the paintComponent() method to produce a gradient panel
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Color topColor = Color.WHITE;
        Color bottomColor = new Color(4, 93, 233);
        GradientPaint gradientPaint = new GradientPaint(0, 15, topColor, 0, height, bottomColor);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(0, 0, width, height);

    }
}
