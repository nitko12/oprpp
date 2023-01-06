package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        fields.values().remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        if (parent == null) {
            throw new IllegalArgumentException("Parent must not be null");
        }

        return calculateSize(parent, WhatSize.PREFERRED);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        if (parent == null) {
            throw new IllegalArgumentException("Parent must not be null");
        }
        return calculateSize(parent, WhatSize.MINIMUM);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null");
        }
        return calculateSize(target, WhatSize.MAXIMUM);
    }

    private enum WhatSize {
        MINIMUM, PREFERRED, MAXIMUM
    }

    public Dimension calculateSize(Container parent, WhatSize what) {
        Insets insets = parent.getInsets();

        int maxWidth = 0;
        int maxHeight = 0;

        for (RCPosition position : fields.keySet()) {
            Component component = fields.get(position);

            Dimension size = null;
            if (what == WhatSize.MINIMUM) {
                size = component.getMinimumSize();
            } else if (what == WhatSize.PREFERRED) {
                size = component.getPreferredSize();
            } else if (what == WhatSize.MAXIMUM) {
                size = component.getMaximumSize();
            }

            if (position.getRow() == 1 && position.getColumn() == 1) {
                maxWidth = Math.max(maxWidth, (size.width - (ROWS - 1) * this.gapSize) / ROWS);
                maxHeight = Math.max(maxHeight, size.height);
            } else {
                maxWidth = Math.max(maxWidth, size.width);
                maxHeight = Math.max(maxHeight, size.height);
            }
        }

        return new Dimension(
                (maxWidth * COLUMNS) + (this.gapSize * (COLUMNS - 1)) + insets.left
                        + insets.right,
                (maxHeight * ROWS) + (this.gapSize * (ROWS - 1)) + insets.top
                        + insets.bottom);

    }

    @Override
    public void layoutContainer(Container parent) {
        if (parent == null) {
            throw new IllegalArgumentException("Parent must not be null");
        }

        int width = parent.getWidth();
        int height = parent.getHeight();

        Insets insets = parent.getInsets();

        int usableWidth = width - insets.left - insets.right;
        int usableHeight = height - insets.top - insets.bottom;

        int oddWidth = (usableWidth - (COLUMNS - 1) * gapSize) / COLUMNS;
        int oddHeight = (usableHeight - (ROWS - 1) * gapSize) / ROWS;

        int evenWidth = (int) ((usableWidth - (COLUMNS - 1) * gapSize) / COLUMNS + 0.5);
        int evenHeight = (int) ((usableHeight - (ROWS - 1) * gapSize) / ROWS + 0.5);

        for (RCPosition position : fields.keySet()) {
            Component component = fields.get(position);

            int row = position.getRow();
            int column = position.getColumn();

            int w = 0;
            int h = 0;

            if (row == 1 && column == 1) {
                w = 5 * evenWidth + 4 * gapSize;
                h = evenHeight;
            } else {
                w = oddWidth;
                h = oddHeight;
            }

            int x = insets.left;
            for (int i = 0; i < column - 1; i++) {
                x += oddWidth;
                if (i == 1) {
                    x += gapSize;
                }
            }
            x += (column - 1) * gapSize;

            int y = insets.top;
            for (int i = 0; i < row - 1; i++) {
                y += oddHeight;
                if (i == 0) {
                    y += gapSize;
                }
            }
            y += (row - 1) * gapSize;

            component.setBounds(x, y, w, h);
        }

    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {

        if (constraints == null) {
            throw new IllegalArgumentException("Constraints must not be null");
        }
        if (comp == null) {
            throw new IllegalArgumentException("Component must not be null");
        }

        RCPosition position = null;

        if (constraints instanceof String) {
            position = parse((String) constraints);
        } else if (constraints instanceof RCPosition) {
            position = (RCPosition) constraints;
        } else {
            throw new IllegalArgumentException("Constraints must be string or RCPosition");
        }

        int row = position.getRow();
        int column = position.getColumn();

        if (!(1 <= row && row <= ROWS) || !(1 <= column && column <= COLUMNS)) {
            throw new CalcLayoutException("Position is out of bounds");
        }

        if (row == 1 && (2 <= column && column <= 5)) {
            throw new CalcLayoutException("Position is out of bounds");
        }

        if (fields.containsKey(position)) {
            throw new CalcLayoutException("Position is already taken");
        }

        this.fields.put(position, comp);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null");
        }
    }

}
