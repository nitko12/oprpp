package hr.fer.zemris.fraktali;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NetwonBase {

    protected static List<Complex> roots = new ArrayList<>();

    protected static void calculate(double reMin, double reMax,
            double imMin, double imMax,
            int width, int height,
            int m,
            int yMin, int yMax,
            short[] data, AtomicBoolean cancel) {

        Complex args[] = new Complex[roots.size()];

        for (int i = 0; i < roots.size(); i++) {
            args[i] = roots.get(i);
        }

        ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(
                Complex.ONE,
                args);

        ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();

        ComplexPolynomial derived = polynomial.derive();

        for (int y = yMin; y <= yMax; y++) {
            if (cancel.get()) {
                break;
            }

            for (int x = 0; x < width; x++) {
                Complex c = mapToComplexPlain(x, y, width, height, reMin, reMax, imMin, imMax);

                Complex zn = c;
                Complex znold;
                int iter = 0;
                double module;

                do {
                    Complex numerator = polynomial.apply(zn);
                    Complex denominator = derived.apply(zn);

                    znold = zn;
                    Complex fraction = numerator.divide(denominator);

                    zn = zn.sub(fraction);

                    module = znold.sub(zn).module();

                    iter++;
                } while (module > 1E-6 && iter < 16);

                int index = rootedPolynomial.indexOfClosestRootFor(zn, 1E-3);
                data[y * width + x] = (short) (index);
            }
        }
    }

    private static Complex mapToComplexPlain(int x, int y,
            int width, int height,
            double reMin, double reMax,
            double imMin, double imMax) {

        double re = (reMax - reMin) * (1. * x / width) + reMin;
        double im = (imMax - imMin) * (1. * y / height) + imMin;

        return new Complex(re, im);
    }
}
