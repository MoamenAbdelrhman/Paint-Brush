package mypaintbrush;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class MyPainter extends JPanel {
    private List<Shape> components = new ArrayList<>();
    private SelectShape selectedShape = SelectShape.FreeHand; // Default shape
    private Color color = Color.BLACK;                       // Default color
    private boolean isDotted = false;                        // Default is not dotted
    private boolean isFilled = false;                        // Default is not filled

    private int x1, y1; // Starting coordinates for shapes
    private FreeHand freehand;

    public MyPainter() {
        setBackground(Color.WHITE); // Background color of drawing area

        // Add mouse listeners for drawing
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();

                switch (selectedShape) {
                    case Line:
                        components.add(new Line(x1, y1, 0, 0, color, isDotted));
                        break;
                    case Rectangle:
                        components.add(new Rectangle(x1, y1, 0, 0, color, isDotted, isFilled));
                        break;
                    case Oval:
                        components.add(new Oval(x1, y1, 0, 0, color, isDotted, isFilled));
                        break;
                    case Triangle:
                        components.add(new Triangle(x1, y1, x1, y1, x1, y1, color, isDotted, isFilled)); // Temporary triangle
                        break;
                    case FreeHand:
                        freehand = new FreeHand(x1, y1, color);
                        components.add(freehand);
                        break;
                    case Eraser:
                        components.add(new Eraser(x1, y1, 20, getBackground())); // Default eraser size
                        break;
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseAdapter() {
    @Override
    public void mouseDragged(MouseEvent e) {
    int x2 = e.getX();
    int y2 = e.getY();

    // Handle shape updates on mouse dragging
    if (components.size() > 0) {
        Shape currentShape = components.get(components.size() - 1);

        if (currentShape instanceof Line) {
            Line line = (Line) currentShape;
            line.setWidth(x2 - line.getX());
            line.setHeight(y2 - line.getY());
        } else if (currentShape instanceof Rectangle) {
            Rectangle rect = (Rectangle) currentShape;
            // Calculate width and height
            int width = Math.abs(x2 - x1);
            int height = Math.abs(y2 - y1);

            // Adjust starting point if dragging in negative X or Y
            rect.setX(Math.min(x1, x2));
            rect.setY(Math.min(y1, y2));

            // Set dimensions
            rect.setWidth(width);
            rect.setHeight(height);
        } else if (currentShape instanceof Oval) {
            Oval oval = (Oval) currentShape;
            // Calculate width and height
            int width = Math.abs(x2 - x1);
            int height = Math.abs(y2 - y1);

            // Adjust starting point if dragging in negative X or Y
            oval.setX(Math.min(x1, x2));
            oval.setY(Math.min(y1, y2));

            // Set dimensions
            oval.setWidth(width);
            oval.setHeight(height);
        } else if (currentShape instanceof Triangle) {
            
            Triangle triangle = (Triangle) currentShape;

            // Set the second vertex to the current mouse position
            triangle.setX2(x2);
            triangle.setY2(y2);

            // Set the third vertex dynamically based on x1, y1, and the mouse
            int x3 = x1 + (x2 - x1); // Adjust x-coordinate to create the third point
            int y3 = y1;             // Keep the third point on the same horizontal plane as the first
            triangle.setX3(x3);
            triangle.setY3(y3);
        } else if (currentShape instanceof FreeHand) {
            FreeHand freehand = (FreeHand) currentShape;
            freehand.addPoint(x2, y2);
        } else if (currentShape instanceof Eraser) {
            Eraser eraser = (Eraser) currentShape;
            eraser.addPoint(x2, y2); // أضف نقطة جديدة للممحاة
        }
    }
    repaint();
}
    });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : components) {
            shape.draw(g);
        }
    }

    // Methods to update properties
    public void setColor(Color color) {
        this.color = color;
    }

    public void setSelectedShape(SelectShape selectedShape) {
        this.selectedShape = selectedShape;
    }

    public void setFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }

    public void setDotted(boolean isDotted) {
        this.isDotted = isDotted;
    }

    public void clearAll() {
        components.clear();
        repaint();
    }

    public void undo() {
        if (!components.isEmpty()) {
            components.remove(components.size() - 1);
            repaint();
        }
    }
}
