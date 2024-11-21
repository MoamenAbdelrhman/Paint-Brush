package mypaintbrush;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Triangle extends Shape {
    private int x2, y2;
    private int x3, y3;

    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color, boolean isDotted, boolean isFilled) {
        super(x1, y1, color, isDotted, isFilled);
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getColor());

        // Set stroke for dotted or solid lines
        if (isDotted()) {
            float[] dashPattern = {5, 5};
            g2d.setStroke(new java.awt.BasicStroke(2, java.awt.BasicStroke.CAP_BUTT, 
                                                   java.awt.BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        } else {
            g2d.setStroke(new java.awt.BasicStroke(2));
        }

        int[] xPoints = {getX(), x2, x3};
        int[] yPoints = {getY(), y2, y3};

        if (isFilled()) {
            g2d.fillPolygon(xPoints, yPoints, 3);
        } else {
            g2d.drawPolygon(xPoints, yPoints, 3);
        }
    }



    // Setters to dynamically adjust triangle coordinates
    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public void setY3(int y3) {
        this.y3 = y3;
    }
}
