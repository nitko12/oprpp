package hr.fer.zemris.math;

public class ComplexRootedPolynomial {

    Complex[] roots;

    // constructor
    public ComplexRootedPolynomial(Complex constant, Complex... roots) {
        this.roots = new Complex[roots.length + 1];
        this.roots[0] = constant;

        for (int i = 0; i < roots.length; i++) {
            this.roots[i + 1] = roots[i];
        }
    }

    // computes polynomial value at given point z
    public Complex apply(Complex z) {
        Complex res = Complex.ZERO;

        for (int i = 0; i < roots.length; i++) {
            res = res.multiply(z.sub(roots[i]));
        }

        return res;
    }

    public ComplexPolynomial toComplexPolynom() {
        ComplexPolynomial res = new ComplexPolynomial(roots[0]);

        for (int i = 1; i < roots.length; i++) {
            ComplexPolynomial term = new ComplexPolynomial(roots[i].negate(), Complex.ONE);

            res = res.multiply(term);
        }

        return res;
    }

    @Override
    public String toString() {
        // (2.0+i0.0)*(z-(1.0+i0.0))*(z-(-1.0+i0.0))*(z-(0.0+i1.0))*(z-(0.0-i1.0))

        StringBuilder sb = new StringBuilder();

        sb.append("(").append(roots[0]).append(")");

        for (int i = 1; i < roots.length; i++) {
            sb.append("*").append("(z-(").append(roots[i]).append("))");
        }

        return sb.toString();
    }

    // finds index of closest root for given complex number z that is within //
    // treshold; if there is no such root, returns -1
    // first root has index 0, second index 1, etc
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
