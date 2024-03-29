package hr.fer.zemris.fraktali;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;

/**
 * Progrma koji crta Newton-Raphson fraktal.
 * 
 */
public class Newton extends NetwonBase {

    /**
     * Funkcija koja se poziva prilikom pokretanja programa.
     * 
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
        System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

        roots = new ArrayList<>();

        while (true) {
            System.out.print("Root " + (roots.size() + 1) + "> ");
            String line = System.console().readLine();
            if (line.equals("done")) {
                break;
            }
            try {
                roots.add(Complex.parse(line));
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }

        FractalViewer.show(new MojProducer());
    }

    /**
     * Razred koji implementira sučelje IFractalProducer.
     * 
     */
    public static class MojProducer implements IFractalProducer {
        /**
         * Metoda koja računa fraktal.
         */
        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

            System.out.println("Zapocinjem izracun...");

            short[] data = new short[width * height];

            int m = roots.size();

            calculate(reMin, reMax, imMin, imMax, width, height, m, 0, height - 1, data, cancel);

            observer.acceptResult(data, (short) m, requestNo);
        }
    }

}
