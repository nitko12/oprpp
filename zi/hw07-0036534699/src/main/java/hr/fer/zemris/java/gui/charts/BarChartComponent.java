package hr.fer.zemris.java.gui.charts;

import javax.swing.JComponent;

import java.awt.*;

/**
 * Klasa koja predstavlja komponentu za crtanje bar charta.
 * 
 */
public class BarChartComponent extends JComponent {

    private BarChart chart;

    /**
     * Konstruktor koji prima bar chart.
     * 
     * @param chart
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;
    }

    /**
     * Crta bar chart.
     * 
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setColor(Color.BLACK);

        // draw x axis description

        int textWidth = graphics2D.getFontMetrics().stringWidth(chart.getXDescription());
        graphics2D.drawString(chart.getXDescription(), (getWidth() - textWidth) / 2, getHeight() - 10);

        // draw y axis description

        textWidth = graphics2D.getFontMetrics().stringWidth(chart.getYDescription());

        graphics2D.rotate(-Math.PI / 2);
        graphics2D.drawString(chart.getYDescription(), -getHeight() / 2 - textWidth / 2, 20);
        graphics2D.rotate(Math.PI / 2);

        // draw graph x axis, with a little arrow

        graphics2D.drawLine(50, getHeight() - 50, getWidth() - 50, getHeight() - 50);
        graphics2D.drawLine(getWidth() - 50, getHeight() - 50, getWidth() - 55, getHeight() - 45);
        graphics2D.drawLine(getWidth() - 50, getHeight() - 50, getWidth() - 55, getHeight() - 55);

        // draw graph y axis, with a little arrow

        graphics2D.drawLine(50, getHeight() - 50, 50, 50);
        graphics2D.drawLine(50, 50, 45, 55);
        graphics2D.drawLine(50, 50, 55, 55);

        // draw x axis values for bar chart

        int cellWidth = (getWidth() - 100) / (chart.getValues().length);

        for (int i = 0; i < chart.getValues().length; ++i) {
            int x = 50 + i * cellWidth;
            int y = getHeight() - 50;

            graphics2D.drawLine(x, y, x, y + 5);
            String text = Integer.toString(chart.getValues()[i].getX());
            textWidth = graphics2D.getFontMetrics().stringWidth(text);
            graphics2D.drawString(text, x - textWidth / 2 + cellWidth / 2, y + 20);

        }

        graphics2D.drawLine(50, getHeight() - 50, 50, 50);

        // draw y axis values for bar chart

        int cellHeight = (getHeight() - 100) / (chart.getYMax() - chart.getYMin()) * chart.getYStep();

        for (int i = chart.getYMin(); i <= chart.getYMax(); i += chart.getYStep()) {
            int x = 50;
            int y = getHeight() - 50 - (i - chart.getYMin()) * cellHeight / chart.getYStep();

            graphics2D.drawLine(x, y, x - 5, y);
            String text = Integer.toString(i);
            textWidth = graphics2D.getFontMetrics().stringWidth(text);
            graphics2D.drawString(text, x - textWidth - 10, y + 5);
        }

        // draw bars

        for (int i = 0; i < chart.getValues().length; ++i) {
            int x = 50 + i * cellWidth;
            int y = getHeight() - 50 - (chart.getValues()[i].getY() - chart.getYMin()) * cellHeight / chart.getYStep();

            graphics2D.setColor(Color.ORANGE);
            graphics2D.fillRect(x, y, cellWidth, getHeight() - 50 - y);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(x, y, cellWidth, getHeight() - 50 - y);
        }
    }
}
