package hr.fer.zemris.math;

import java.util.ArrayList;

/**
 * Klasa koja predstavlja polinom kompleksnih brojeva definiran koeficijentima.
 */
public class ComplexPolynomial {

    Complex[] factors;

    /**
     * Konstruktor koji tvori polinom kompleksnih brojeva preko niza koeficijenata.
     * 
     * @param factors
     */
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

    /**
     * Vraća razred polinoma.
     * 
     * @return short
     */
    public short order() {
        return (short) (factors.length - 1);
    }

    /**
     * Množi polinom s drugim polinomom.
     * 
     * @param p
     * @return ComplexPolynomial
     */
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

    /**
     * Racuna prvu derivaciju polinoma.
     * 
     * @return ComplexPolynomial
     */
    public ComplexPolynomial derive() {
        int order = this.order();

        Complex[] res = new Complex[order];

        for (int i = 0; i < order; i++) {
            res[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
        }

        return new ComplexPolynomial(res);
    }

    /**
     * Vraca vrijednost polinoma u točki z.
     * 
     * @param z
     * @return Complex
     */
    public Complex apply(Complex z) {
        Complex res = Complex.ZERO;

        for (int i = 0; i < factors.length; i++) {
            res = res.add(factors[i].multiply(z.power(i)));
        }

        return res;
    }

    /**
     * Vraca string reprezentaciju polinoma.
     * 
     * @return String
     */
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

    /**
     * Uspoređuje dva polinoma.
     * 
     * @param obj
     * @return boolean
     */
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
