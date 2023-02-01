package hr.fer.zemris.java.gui.charts;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Klasa koja predstavlja demo program za crtanje grafova.
 */
public class BarChartDemo extends JFrame {

    /**
     * Funkcija koja pokreće program.
     * 
     * @param args
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Invalid number of arguments.");
            return;
        }

        SwingUtilities.invokeLater(() -> {

            BarChart chart = null;

            try {
                chart = BarChart.parse(args[0]);
            } catch (IOException e) {
                System.out.println("Error while reading file.");
                return;
            }

            BarChartDemo demo = new BarChartDemo(chart);
            demo.setVisible(true);
        });

    }

    /**
     * Konstruktor koji prima graf koji se crta.
     * 
     * @param chart
     */
    public BarChartDemo(BarChart chart) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        BarChartComponent component = new BarChartComponent(chart);
        add(component);
    }
}
