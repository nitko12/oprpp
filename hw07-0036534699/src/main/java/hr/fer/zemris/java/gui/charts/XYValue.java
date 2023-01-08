package hr.fer.zemris.java.gui.charts;

/**
 * Klasa koja predstavlja par x i y vrijednosti.
 * 
 */
public class XYValue {
    private int x;
    private int y;

    /**
     * Kontruktor koji prima x i y vrijednost.
     * 
     * @param x
     * @param y
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Vraca x vrijednost.
     * 
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Vraca y vrijednost.
     * 
     * @return int
     */
    public int getY() {
        return y;
    }
}
