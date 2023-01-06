package hr.fer.zemris.math;

import java.util.ArrayList;

public class ComplexPolynomial {

    Complex[] factors;

    // ...
    // constructor
    public ComplexPolynomial(Complex... factors) {

        int non_zero = 0;

        for (int i = 0; i < factors.length; i++) {
            if (!factors[i].equals(Complex.ZERO)) {
                non_zero = i + 1;
            }
        }

        this.factors = new Complex[non_zero];

        for (int i = 0; i < non_zero; i++) {
            this.factors[i] = factors[i];
        }
    }

    // returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
    public short order() {
        return (short) (factors.length - 1);
    }

    // computes a new polynomial this*p
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Complex[] res = new Complex[this.order() + p.order() + 1];

        for (int i = 0; i < res.length; i++) {
            res[i] = Complex.ZERO;
        }

        for (int i = 0; i < this.factors.length; i++) {
            for (int j = 0; j < p.factors.length; j++) {
                res[i + j] = res[i + j].add(this.factors[i].multiply(p.factors[j]));
            }
        }

        return new ComplexPolynomial(res);
    }

    // computes first derivative of this polynomial; for example, for //
    // (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
    public ComplexPolynomial derive() {
        int order = this.order();

        Complex[] res = new Complex[order];

        for (int i = 0; i < order; i++) {
            res[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
        }

        return new ComplexPolynomial(res);
    }

    // computes polynomial value at given point z
    public Complex apply(Complex z) {
        Complex res = Complex.ZERO;

        for (int i = 0; i < factors.length; i++) {
            res = res.add(factors[i].multiply(z.power(i)));
        }

        return res;
    }

    @Override
    public String toString() {

        ArrayList<String> terms = new ArrayList<>();

        for (int i = factors.length - 1; i >= 0; i--) {
            StringBuilder term = new StringBuilder();

            if (factors[i].equals(Complex.ZERO)) {
                continue;
            }

            term.append("(").append(factors[i]).append(")");

            if (i != 0) {
                term.append("z^").append(i);
            }

            terms.add(term.toString());
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < terms.size(); i++) {
            sb.append(terms.get(i));

            if (i != terms.size() - 1) {
                sb.append("+");
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof ComplexPolynomial)) {
            return false;
        }

        ComplexPolynomial other = (ComplexPolynomial) obj;
        if (this.factors.length != other.factors.length) {
            return false;
        }

        for (int i = 0; i < factors.length; i++) {
            if (!this.factors[i].equals(other.factors[i])) {
                return false;
            }
        }
        return true;
    }
}
