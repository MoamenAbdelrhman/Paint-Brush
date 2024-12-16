package mypaintbrush;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class FreeHand extends Shape {
    private List<int[]> points; // List of x, y coordinates for freehand drawing
    private int strokeWidth;
    private float[] dashPattern = {5f, 5f};
    
    public FreeHand(int x, int y, Color color, boolean isDotted) {
        super(x, y, color, isDotted, false); // FreeHand ignores isDotted and isFilled
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
        if (!points.isEmpty()) {
            int[] lastPoint = points.get(points.size() - 1);
            if (lastPoint[0] == x && lastPoint[1] == y) return; // Skip duplicate points
        }
        points.add(new int[]{x, y});
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(getColor());
        if (isDotted()) {
            float[] dash = {5f, 5f}; // Define the dotted pattern
            g2d.setStroke(new BasicStroke(
                2,                      // Line thickness
                BasicStroke.CAP_ROUND,  // End-cap style
                BasicStroke.JOIN_ROUND, // Join style
                2.0f,                   // Miter limit
                dash,                   // Dash array
                1.0f                    // Dash phase
            ));
        } else {
            g2d.setStroke(new BasicStroke(2)); // Solid line
        }
        for (int i = 1; i < points.size(); i++) {
            int[] p1 = points.get(i - 1);
            int[] p2 = points.get(i);
            g.drawLine(p1[0], p1[1], p2[0], p2[1]);
        }
    }
}

