package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalcLayout implements LayoutManager2 {

    private static int ROWS = 5;
    private static int COLUMNS = 7;
    private int gapSize;

    HashMap<RCPosition, Component> fields;

    public CalcLayout(int gapSize) {
        if (gapSize < 0) {
            throw new IllegalArgumentException("Gap size must be greater than 0");
        }

        this.gapSize = gapSize;
        this.fields = new HashMap<>();
    }

    public CalcLayout() {
        this(0);
    }

    public RCPosition parse(String name) {
        String[] parts = name.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Name must be in format row,column");
        }

        int row = Integer.parseInt(parts[0]);
        int column = Integer.parseInt(parts[1]);

        if (row < 1 || row > ROWS) {
            throw new IllegalArgumentException("Row must be between 1 and 5");
        }

        if (column < 1 || column > COLUMNS) {
            throw new IllegalArgumentException("Column must be between 1 and 7");
        }

        return new RCPosition(row, column);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }

        if (comp == null) {
            throw new IllegalArgumentException("Component must not be null");
        }

        if (!(comp instanceof JLabel)) {
            throw new IllegalArgumentException("Component must be a JLabel");
        }

        RCPosition position = RCPosition.parse(name);

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        // TODO Auto-generated method stub

    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {
        // TODO Auto-generated method stub

    }

}
