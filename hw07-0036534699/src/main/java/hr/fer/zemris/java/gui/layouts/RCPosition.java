package hr.fer.zemris.java.gui.layouts;

public class RCPosition {

    private final int row;
    private final int column;

    public RCPosition(int i, int j) {
        this.row = i;
        this.column = j;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "RCPosition [row=" + row + ", column=" + column + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RCPosition other = (RCPosition) obj;
        if (row != other.row)
            return false;
        if (column != other.column)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return row * 100 + column;
    }
}
