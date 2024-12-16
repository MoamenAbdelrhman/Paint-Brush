package mypaintbrush;

import javax.swing.*;
import java.awt.*;

public class MyPaintBrush {

    public static void main(String[] args) {
        JFrame frame = new JFrame("My Paint Brush");
        MyPainter painter = new MyPainter();

        // Set the layout for the frame
        frame.setLayout(new BorderLayout());

        // Create the control panel (for buttons and checkboxes)
        JPanel controlPanel = createControlPanel(painter);

        // Add components to the frame
        frame.add(controlPanel, BorderLayout.NORTH); // Control panel on the left
        frame.add(painter, BorderLayout.CENTER);    // Drawing area in the center

        // Set the window properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 900);
        frame.setVisible(true);
    }

    // Method to create the control panel
    private static JPanel createControlPanel(MyPainter painter) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,0, 10, 10)); // Vertical layout with spacing
        panel.setPreferredSize(new Dimension(10, 35)); // Fixed width for control panel

        // Add buttons and checkboxes to the panel
        panel.add(colorButton("Black", Color.BLACK, painter));
        panel.add(colorButton("Red", Color.RED, painter));
        panel.add(colorButton("Green", Color.GREEN, painter));
        panel.add(colorButton("Blue", Color.BLUE, painter));
        panel.add(shapeButton("Oval", SelectShape.Oval, painter));
        panel.add(shapeButton("Rectangle", SelectShape.Rectangle, painter));
        panel.add(shapeButton("Triangle", SelectShape.Triangle, painter));
        panel.add(shapeButton("Line", SelectShape.Line, painter));
        panel.add(shapeButton("FreeHand", SelectShape.FreeHand, painter));
        panel.add(shapeButton("Eraser", SelectShape.Eraser, painter));
        panel.add(checkbox("Filled", painter));
        panel.add(checkbox("Dotted", painter));
        panel.add(actionButton("Undo", painter));
        panel.add(actionButton("Redo", painter));
        panel.add(actionButton("ClearAll", painter));

        return panel;
    }

    // Helper method to create color buttons
    private static JButton colorButton(String text, Color color, MyPainter painter) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.addActionListener(e -> painter.setColor(color));
        return button;
    }

    // Helper method to create shape buttons
    private static JButton shapeButton(String text, SelectShape shape, MyPainter painter) {
        JButton button = new JButton(text);
        button.addActionListener(e -> painter.setSelectedShape(shape));
        return button;
    }

    // Helper method to create checkboxes
    // Helper method to create checkboxes
    private static JCheckBox checkbox(String text, MyPainter painter) {
        JCheckBox checkbox = new JCheckBox(text);
        checkbox.addItemListener(e -> {
            if (text.equals("Filled")) {
                painter.setFilled(checkbox.isSelected());
                if (checkbox.isSelected()) {
                    painter.setDotted(false); // Uncheck Dotted if Filled is selected
                    for (Component comp : checkbox.getParent().getComponents()) {
                        if (comp instanceof JCheckBox && ((JCheckBox) comp).getText().equals("Dotted")) {
                            ((JCheckBox) comp).setSelected(false);
                        }
                    }
                }
            } else if (text.equals("Dotted")) {
                painter.setDotted(checkbox.isSelected());
                if (checkbox.isSelected()) {
                    painter.setFilled(false); // Uncheck Filled if Dotted is selected
                    for (Component comp : checkbox.getParent().getComponents()) {
                        if (comp instanceof JCheckBox && ((JCheckBox) comp).getText().equals("Filled")) {
                            ((JCheckBox) comp).setSelected(false);
                        }
                    }
                }
            }
        });
        return checkbox;
    }


    // Helper method to create action buttons
    private static JButton actionButton(String text, MyPainter painter) {
        JButton button = new JButton(text);
        button.addActionListener(e -> {
            if (text.equals("ClearAll")) {
                painter.clearAll();
            } else if (text.equals("Undo")) {
                painter.undo();
            } else if (text.equals("Redo")) {
                painter.redo(); // Handle redo action
            }
        });
        return button;
    }
}
