package hr.fer.zemris.fraktali;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonParallel extends NetwonBase {

    private static int workers = 1;
    private static int tracks = 12;

    public static void main(String[] args) {

        // --workers=2 --tracks=10

        for (String arg : args) {
            if (arg.startsWith("--workers=")) {
                workers = Integer.parseInt(arg.substring(10));
            } else if (arg.startsWith("--tracks=")) {
                tracks = Integer.parseInt(arg.substring(9));
            }
        }

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

    public static class PosaoIzracuna implements Runnable {
        double reMin;
        double reMax;
        double imMin;
        double imMax;
        int width;
        int height;
        int yMin;
        int yMax;
        int m;
        short[] data;
        AtomicBoolean cancel;
        public static PosaoIzracuna NO_JOB = new PosaoIzracuna();

        private PosaoIzracuna() {
        }

        public PosaoIzracuna(double reMin, double reMax, double imMin,
                double imMax, int width, int height, int yMin, int yMax,
                int m, short[] data, AtomicBoolean cancel) {
            super();
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.m = m;
            this.data = data;
            this.cancel = cancel;
        }

        @Override
        public void run() {

            calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel);

        }
    }

    public static class MojProducer implements IFractalProducer {
        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

            System.out.println("Zapocinjem izracun...");

            System.out.println("Broj traka: " + tracks);
            System.out.println("Broj radnika: " + workers);

            short[] data = new short[width * height];

            int m = roots.size();

            final int brojTraka = tracks;
            int brojYPoTraci = height / brojTraka;

            final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

            Thread[] radnici = new Thread[workers];
            for (int i = 0; i < radnici.length; i++) {
                radnici[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            PosaoIzracuna p = null;
                            try {
                                p = queue.take();
                                if (p == PosaoIzracuna.NO_JOB)
                                    break;
                            } catch (InterruptedException e) {
                                continue;
                            }
                            p.run();
                        }
                    }
                });
            }
            for (int i = 0; i < radnici.length; i++) {
                radnici[i].start();
            }

            for (int i = 0; i < brojTraka; i++) {
                int yMin = i * brojYPoTraci;
                int yMax = (i + 1) * brojYPoTraci - 1;
                if (i == brojTraka - 1) {
                    yMax = height - 1;
                }
                PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
                        cancel);
                while (true) {
                    try {
                        queue.put(posao);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }
            for (int i = 0; i < radnici.length; i++) {
                while (true) {
                    try {
                        queue.put(PosaoIzracuna.NO_JOB);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            for (int i = 0; i < radnici.length; i++) {
                while (true) {
                    try {
                        radnici[i].join();
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short) m, requestNo);
        }
    }
}
