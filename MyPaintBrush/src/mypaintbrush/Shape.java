package mypaintbrush;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {
    private int x;
    private int y;
    private Color color;
    private boolean isDotted;
    private boolean isFilled;

    public Shape(int x, int y, Color color, boolean isDotted, boolean isFilled) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isDotted = isDotted;
        this.isFilled = isFilled;
    }

    // Abstract method for drawing the shape
    public abstract void draw(Graphics g);

    // Getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDotted() {
        return isDotted;
    }

    public void setDotted(boolean isDotted) {
        this.isDotted = isDotted;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }
}
