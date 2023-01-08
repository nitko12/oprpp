package hr.fer.zemris.java.gui.layouts;

/**
 * Klasa koja predstavlja poziciju u layoutu.
 *
 */
public class RCPosition {

    private final int row;
    private final int column;

    /**
     * Konstruktor koji prima redak i stupac.
     * 
     * @param i
     * @param j
     */
    public RCPosition(int i, int j) {
        this.row = i;
        this.column = j;
    }

    /**
     * Vraća redak.
     * 
     * @return int
     */
    public int getRow() {
        return row;
    }

    /**
     * Vraća stupac.
     * 
     * @return int
     */
    public int getColumn() {
        return column;
    }

    /**
     * String ispis pozicije.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "RCPosition [row=" + row + ", column=" + column + "]";
    }

    /**
     * Automatski generirani equals.
     * 
     * @param obj
     * @return boolean
     */
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

    /**
     * Hashcode pod pretpostavkom da je maksimalan broj redaka i stupaca 100.
     * 
     * @return int
     */
    @Override
    public int hashCode() {
        return row * 100 + column;
    }
}
