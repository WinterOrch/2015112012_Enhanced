package com.ui.css;

import javax.swing.border.Border;
import java.awt.*;

/**
 * Usage：
 *      JButton close = new JButton("Start");           //Also Available For Other JSwing Component Like JTextArea
 *      close.setOpaque(false);
 *      close.setBorder(new RoundBorder());             //Black Border
 *      close.setBorder(new RoundBorder(Color.RED));    //Color Border
 * @author Monsoons
 * Modified by Frankel.Y
 */
public class RoundBorder implements Border {
    private Color color;
    private float strokeWidth;

    public RoundBorder(Color color, float width) {
        this.color = color;
        strokeWidth = width;
    }

    public RoundBorder() {
        this.color = Color.BLACK;
        strokeWidth = (float)1;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    // 实现Border（父类）方法
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(strokeWidth,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
        g2d.setColor(color);
        g2d.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 15, 15);
    }
}