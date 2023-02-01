package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;

/**
 * Klasa koja predstavlja layout manager za kalkulator.
 */
public class CalcLayout implements LayoutManager2 {

    private static int ROWS = 5;
    private static int COLUMNS = 7;
    private int gapSize;

    HashMap<RCPosition, Component> fields;

    /**
     * Konstruktor koji prima velicinu razmaka medu komponentama.
     * 
     * @param gapSize
     */
    public CalcLayout(int gapSize) {
        if (gapSize < 0) {
            throw new IllegalArgumentException("Gap size must be greater than 0");
        }

        this.gapSize = gapSize;
        this.fields = new HashMap<>();
    }

    /**
     * Konstruktor koji ne prima velicinu razmaka medu komponentama.
     * 
     * Defaulta na 0.
     */
    public CalcLayout() {
        this(0);
    }

    /**
     * Parsira string i vraca RCPosition.
     * 
     * @param name
     * @return RCPosition
     */
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

    /**
     * Ne koristi se.
     * 
     * @param name
     * @param comp
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException();
    }

    /**
     * Mice komponentu iz layouta.
     * 
     * @param comp
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        fields.values().remove(comp);
    }

    /**
     * Vraca izracun preferirane velicine layouta.
     * 
     * @param parent
     * @return Dimension
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        if (parent == null) {
            throw new IllegalArgumentException("Parent must not be null");
        }

        return calculateSize(parent, WhatSize.PREFERRED);
    }

    /**
     * Vracanje minimalne velicine layouta.
     * 
     * @param parent
     * @return Dimension
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        if (parent == null) {
            throw new IllegalArgumentException("Parent must not be null");
        }
        return calculateSize(parent, WhatSize.MINIMUM);
    }

    /**
     * Vraca izracun maksimalne velicine layouta.
     * 
     * @param target
     * @return Dimension
     */
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

    /**
     * Racuna velicinu layouta prema enumu koji se proslijedi.
     * 
     * @param parent
     * @param what
     * @return Dimension
     */
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

    /**
     * Racuna velicine komponenti i postavlja ih na layout.
     * 
     * @param parent
     */
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

    /**
     * Metoda za dodanje komponenti na layout uz odredenu poziciju.
     * 
     * @param comp
     * @param constraints
     */
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

    /**
     * Vraca poziciju komponente.
     * 
     * @param target
     * @return float
     */
    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    /**
     * Vracanje poziciju komponente.
     * 
     * @param target
     * @return float
     */
    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    /**
     * Ne koristi se.
     * 
     * @param target
     */
    @Override
    public void invalidateLayout(Container target) {
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null");
        }

        // throw new UnsupportedOperationException("Not supported yet.");
    }

}
