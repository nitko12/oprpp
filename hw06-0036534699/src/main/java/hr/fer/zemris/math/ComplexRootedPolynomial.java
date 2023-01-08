package hr.fer.zemris.math;

/**
 * Klasa koja modelira polinom kompleksnih brojeva
 * prema njihovim null-točkama.
 * 
 * 
 */
public class ComplexRootedPolynomial {

    Complex[] roots;

    /**
     * Konstruktor koji prima polinom kompleksnih brojeva preko njegove
     * konstante i niza nul-točaka.
     * 
     * @param constant
     * @param roots
     */
    public ComplexRootedPolynomial(Complex constant, Complex... roots) {
        this.roots = new Complex[roots.length + 1];
        this.roots[0] = constant;

        for (int i = 0; i < roots.length; i++) {
            this.roots[i + 1] = roots[i];
        }
    }

    /**
     * Izračunava vrijednost polinoma u točki z.
     * 
     * @param z
     * @return Complex
     */
    // computes polynomial value at given point z
    public Complex apply(Complex z) {
        Complex res = Complex.ZERO;

        for (int i = 0; i < roots.length; i++) {
            res = res.multiply(z.sub(roots[i]));
        }

        return res;
    }

    /**
     * Pretvara polinom kompleksnih brojeva definiran nul-tockama u polinom
     * kompleksnih brojeva definiranim koeficijentima.
     * 
     * @return ComplexPolynomial
     */
    public ComplexPolynomial toComplexPolynom() {
        ComplexPolynomial res = new ComplexPolynomial(roots[0]);

        for (int i = 1; i < roots.length; i++) {
            ComplexPolynomial term = new ComplexPolynomial(roots[i].negate(), Complex.ONE);

            res = res.multiply(term);
        }

        return res;
    }

    /**
     * Vraca string reprezentaciju polinoma kompleksnih brojeva.
     * 
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("(").append(roots[0]).append(")");

        for (int i = 1; i < roots.length; i++) {
            sb.append("*").append("(z-(").append(roots[i]).append("))");
        }

        return sb.toString();
    }

    /**
     * Vraca najblizu nul-tocku za zadanu kompleksnu vrijednost z.
     * 
     * @param z
     * @param treshold
     * @return int
     */
    public int indexOfClosestRootFor(Complex z, double treshold) {
        int index = -1;
        double mnDist = Double.MAX_VALUE;

        for (int i = 0; i < roots.length; i++) {
            double distance = z.sub(roots[i]).module();

            if (distance < mnDist) {
                mnDist = distance;
                index = i;
            }
        }

        if (mnDist > treshold) {
            return -1;
        }

        return index;
    }
}
