package ui;

import javax.swing.*;
import java.awt.*;

// grey
//   Color color1 = new Color(189, 195, 199);
//   Color color2 = new Color(44, 62, 80);

// Pink
//    Color color1 = new Color(94, 114, 235);
//     Color color2 = new Color(255, 145, 144);
public class GradientPanel extends JPanel {

    public GradientPanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
//        Color color1 = new Color(110, 69, 225);
//        Color color2 = new Color(137, 212, 207);
        Color color1 = new Color(189, 195, 199);
        Color color2 = new Color(44, 62, 80);
        GradientPaint gp = new GradientPaint(0, -250, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
