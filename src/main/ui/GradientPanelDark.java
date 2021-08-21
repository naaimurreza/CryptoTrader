package ui;

import javax.swing.*;
import java.awt.*;

/*
  Represents a panel with gradients background color
  Citation: https://stackoverflow.com/questions/14364291/jpanel-gradient-background
 */
public class GradientPanelDark extends JPanel {
    private Color topColor;
    private Color bottomColor;

    public GradientPanelDark() {
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
        topColor = Color.BLACK;
        bottomColor = Color.GRAY;
        GradientPaint gradientPaint = new GradientPaint(0, 15, topColor, 0, height, bottomColor);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(0, 0, width, height);

    }
}