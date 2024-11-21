package mypaintbrush;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Eraser extends Shape {
    private List<int[]> points; // List to store the erase points
    private int diameter; // The diameter size of the eraser

    public Eraser(int x, int y, int diameter, Color backgroundColor) {
        super(x, y, backgroundColor, false, false); // Ignore isDotted and isFilled
        this.diameter = diameter;
        points = new ArrayList<>();
        points.add(new int[]{x, y}); // Add the first point
    }

    public void addPoint(int x, int y) {
        points.add(new int[]{x, y}); // Add new points while dragging
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter; // Dynamically adjust the diameter
    }

    public int getDiameter() {
        return diameter; // Return the current diameter value
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor()); // Set the color for the background (erase color)
        for (int[] point : points) {
            // Draw a small circle at each point to appear as a continuous line
            g.fillOval(point[0] - diameter / 2, point[1] - diameter / 2, diameter, diameter);
        }
    }
}
