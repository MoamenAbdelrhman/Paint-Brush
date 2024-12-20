package mypaintbrush;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JPanel;
import java.util.Stack;

public class MyPainter extends JPanel {
    private List<Shape> components = new ArrayList<>();
    private Stack<List<Shape>> undoStack = new Stack<>();
    private Stack<List<Shape>> redoStack = new Stack<>();
    private SelectShape selectedShape = SelectShape.FreeHand; 
    private Color color = Color.BLACK;                       
    private boolean isDotted = false;                         
    private boolean isFilled = false;                        

    private int x1, y1; 
    private FreeHand freehand;

    public MyPainter() {
        setBackground(Color.WHITE); 

        // Add mouse listeners for drawing
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();

                // Save current state to undo stack before making changes
                saveToUndoStack();

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
                        freehand = new FreeHand(x1, y1, color,isDotted);
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
                        eraser.addPoint(x2, y2); // Add new point to the eraser
                    }
                }
                repaint();
            }
        });
    }

    private void saveToUndoStack() {
        // Save the current state to undo stack before each change
        undoStack.push(new ArrayList<>(components));
        // Clear redo stack when new drawing is made (to avoid inconsistencies)
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new ArrayList<>(components)); // Save the current state for redo
            components = undoStack.pop(); // Restore the last undone state
            repaint();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new ArrayList<>(components)); // Save the current state for undo
            components = redoStack.pop(); // Restore the last redone state
            repaint();
        }
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
        undoStack.clear();
        redoStack.clear();
        repaint();
    }
}
