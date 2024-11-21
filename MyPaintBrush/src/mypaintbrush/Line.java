package mypaintbrush;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Line extends Shape {
    private int width;
    private int height;

    public Line(int x, int y, int width, int height, Color color, boolean isDotted) {
        super(x, y, color, isDotted, false); // Filled is false for Line
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getColor());

        if (isDotted()) {
            float[] dashPattern = {4, 4}; // Dash length and spacing
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        } else {
            g2d.setStroke(new BasicStroke(2)); // Regular solid line
        }

        g2d.drawLine(getX(), getY(), getX() + width, getY() + height);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
