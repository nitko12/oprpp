package hr.fer.zemris.java.gui.charts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
// import java.util.Arrays;
import java.util.List;

/**
 * Klasa koja predstavlja barchart.
 */
public class BarChart {

    // public static void main(String[] args) {
    // BarChart model = new BarChart(
    // Arrays.asList(
    // new XYValue(1, 8),
    // new XYValue(2, 20),
    // new XYValue(3, 22),
    // new XYValue(4, 10),
    // new XYValue(5, 4)),
    // "Number of people in the car",
    // "Frequency",
    // 0, // y-os kreće od 0
    // 22, // y-os ide do 22
    // 2);
    // }

    private XYValue[] values;
    private String xDescription;
    private String yDescription;
    private int yMin;
    private int yMax;
    private int yStep;

    /**
     * Konstruktor koji prima listu XYValue, x opis, y opis, y min, y max i y step.
     * 
     * @param list
     * @param xDescription
     * @param yDescription
     * @param yMin
     * @param yMax
     * @param yStep
     */
    public BarChart(List<XYValue> list,
            String xDescription, String yDescription,
            int yMin, int yMax, int yStep) {
        if (list == null) {
            throw new IllegalArgumentException("Values cannot be null");
        }
        if (xDescription == null) {
            throw new IllegalArgumentException("X description cannot be null");
        }
        if (yDescription == null) {
            throw new IllegalArgumentException("Y description cannot be null");
        }
        if (yMin < 0) {
            throw new IllegalArgumentException("Y min should be greater than 0");
        }
        if (!(yMax > yMin)) {
            throw new IllegalArgumentException("Y max should be greater than Y min");
        }
        if (!(yStep > 0)) {
            throw new IllegalArgumentException("Y step should be greater than 0");
        }

        while ((yMax - yMin) % yStep != 0) {
            ++yStep;
        }

        for (XYValue value : list) {
            if (value.getY() < yMin) {
                throw new IllegalArgumentException("Y value should be greater than Y min");
            }
        }

        this.values = list.toArray(new XYValue[list.size()]);
        this.xDescription = xDescription;
        this.yDescription = yDescription;
        this.yMin = yMin;
        this.yMax = yMax;
        this.yStep = yStep;
    }

    /**
     * Vraća array XYValue-a
     * 
     * @return XYValue[]
     */
    public XYValue[] getValues() {
        return values;
    }

    /**
     * Vraca x opis.
     * 
     * @return String
     */
    public String getXDescription() {
        return xDescription;
    }

    /**
     * Vraca y opis.
     * 
     * @return String
     */
    public String getYDescription() {
        return yDescription;
    }

    /**
     * Vraca minimalnu y vrijednost.
     * 
     * @return int
     */
    public int getYMin() {
        return yMin;
    }

    /**
     * Vraca maksimalnu y vrijednost.
     * 
     * @return int
     */
    public int getYMax() {
        return yMax;
    }

    /**
     * Vraca y step.
     * 
     * @return int
     */
    public int getYStep() {
        return yStep;
    }

    /**
     * Parsira string i vraca barchart.
     * 
     * @param string
     * @return BarChart
     * @throws IOException
     */
    public static BarChart parse(String string) throws IOException {
        // open file and read it
        try (BufferedReader reader = new BufferedReader(new FileReader(string))) {

            String xDescription = reader.readLine();
            String yDescription = reader.readLine();

            List<XYValue> list = new ArrayList<>();

            String[] parts = reader.readLine().split(" ");

            for (String part : parts) {
                String[] values = part.split(",");
                list.add(new XYValue(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
            }

            int yMin = Integer.parseInt(reader.readLine());
            int yMax = Integer.parseInt(reader.readLine());
            int yStep = Integer.parseInt(reader.readLine());

            return new BarChart(list, xDescription, yDescription, yMin, yMax, yStep);
        }
    }
}
