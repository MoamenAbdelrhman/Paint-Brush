package mypaintbrush;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class FreeHand extends Shape {
    private List<int[]> points; // List of x, y coordinates for freehand drawing
    private int strokeWidth;
    public FreeHand(int x, int y, Color color) {
        super(x, y, color, false, false); // FreeHand ignores isDotted and isFilled
        points = new ArrayList<>();
        points.add(new int[]{x, y});
        this.strokeWidth = 2;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth; 
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }
    
    public void addPoint(int x, int y) {
        points.add(new int[]{x, y});
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(getColor());
        g2d.setStroke(new java.awt.BasicStroke(strokeWidth));
        for (int i = 1; i < points.size(); i++) {
            int[] p1 = points.get(i - 1);
            int[] p2 = points.get(i);
            g.drawLine(p1[0], p1[1], p2[0], p2[1]);
        }
    }
}

