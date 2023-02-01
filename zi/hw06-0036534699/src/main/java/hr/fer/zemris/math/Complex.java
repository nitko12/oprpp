package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa koja predstavlja prosti broj.
 */
public class Complex {
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex ONE_NEG = new Complex(-1, 0);
    public static final Complex IM = new Complex(0, 1);
    public static final Complex IM_NEG = new Complex(0, -1);

    private double real;
    private double imaginary;

    /**
     * Konstruktor koji inicializira kompleksni broj na 0+0i.
     */
    public Complex() {
        this.real = 0;
        this.imaginary = 0;
    }

    /**
     * Konstruktor koji inicijalizira kompleksni broj na zadanu vrijednost.
     * 
     * 
     * @param re
     * @param im
     */
    public Complex(double re, double im) {
        this.real = re;
        this.imaginary = im;
    }

    /**
     * Vraća realni dio kompleksnog broja.
     * 
     * @return double
     */
    public double module() {
        return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }

    /**
     * Množi dva kompleksna broja.
     * Vraća novi kompleksni broj.
     * 
     * @param c
     * @return Complex
     */
    public Complex multiply(Complex c) {
        double re = this.real * c.real - this.imaginary * c.imaginary;
        double im = this.real * c.imaginary + this.imaginary * c.real;
        return new Complex(re, im);
    }

    /**
     * Djeli dva kompleksna broja.
     * Vraća novi kompleksni broj.
     * 
     * @param c
     * @return Complex
     */
    public Complex divide(Complex c) {
        double re = (this.real * c.real + this.imaginary * c.imaginary) /
                (c.real * c.real + c.imaginary * c.imaginary);
        double im = (this.imaginary * c.real - this.real * c.imaginary) /
                (c.real * c.real + c.imaginary * c.imaginary);
        return new Complex(re, im);
    }

    /**
     * Zbraja dva kompleksna broja.
     * 
     * @param c
     * @return Complex
     */
    public Complex add(Complex c) {
        double re = this.real + c.real;
        double im = this.imaginary + c.imaginary;
        return new Complex(re, im);
    }

    /**
     * Oduzima dva kompleksna broja.
     * 
     * @param c
     * @return Complex
     */
    public Complex sub(Complex c) {
        double re = this.real - c.real;
        double im = this.imaginary - c.imaginary;
        return new Complex(re, im);
    }

    /**
     * Negira kompleksni broj.
     * 
     * @return Complex
     */
    public Complex negate() {
        return new Complex(-this.real, -this.imaginary);
    }

    /**
     * Potencira kompleksni broj.
     * 
     * @param n
     * @return Complex
     */
    public Complex power(int n) {
        if (n == 0) {
            return Complex.ONE;
        }

        double r = Math.pow(this.module(), n);
        double th = n * Math.atan2(this.imaginary, this.real);

        return new Complex(r * Math.cos(th), r * Math.sin(th));

    }

    /**
     * Vraća n-ti korijen kompleksnog broja.
     * 
     * @param n
     * @return List<Complex>
     */
    public List<Complex> root(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive integer");
        }

        List<Complex> roots = new ArrayList<>();
        double r = Math.pow(this.module(), 1.0 / n);
        double th = Math.atan2(this.imaginary, this.real);

        for (int k = 0; k < n; k++) {
            roots.add(new Complex(r * Math.cos((th + 2 * k * Math.PI) / n),
                    r * Math.sin((th + 2 * k * Math.PI) / n)));
        }

        return roots;
    }

    /**
     * Parsira string u kompleksni broj.
     * 
     * @param s
     * @return Complex
     */
    public static Complex parse(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String must not be null");
        }

        s = s.trim();

        if (s.isEmpty()) {
            throw new IllegalArgumentException("String must not be empty");
        }

        if (s.equals("i")) {
            return Complex.IM;
        }

        if (s.equals("-i")) {
            return Complex.IM_NEG;
        }

        if (s.equals("1")) {
            return Complex.ONE;
        }

        if (s.equals("-1")) {
            return Complex.ONE_NEG;
        }

        if (s.equals("0")) {
            return Complex.ZERO;
        }

        if (s.charAt(0) == 'i') {
            return new Complex(0, Double.parseDouble(s.substring(1)));
        }

        if (s.charAt(s.length() - 1) == 'i') {
            return new Complex(Double.parseDouble(s.substring(0, s.length() - 1)), 1);
        }

        if (s.charAt(0) == '-') {
            if (s.charAt(1) == 'i') {
                return new Complex(0, -Double.parseDouble(s.substring(2)));
            }

            if (s.charAt(s.length() - 1) == 'i') {
                return new Complex(-Double.parseDouble(s.substring(1, s.length() - 1)), 1);
            }
        }

        if (s.charAt(0) == '+') {
            if (s.charAt(1) == 'i') {
                return new Complex(0, Double.parseDouble(s.substring(2)));
            }

            if (s.charAt(s.length() - 1) == 'i') {
                return new Complex(Double.parseDouble(s.substring(1, s.length() - 1)), 1);
            }
        }

        if (s.contains("+")) {
            String[] parts = s.split("\\+");
            return new Complex(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
        }

        if (s.contains("-")) {
            String[] parts = s.split("-");
            return new Complex(Double.parseDouble(parts[0]), -Double.parseDouble(parts[1]));
        }

        throw new IllegalArgumentException("Samo su a+ib or a-ib legalne forme kompleksnog broja.");
    }

    /**
     * Vraća kompleksni broj u obliku stringa.
     * 
     * @return String
     */
    @Override
    public String toString() {

        if (this.imaginary == 0) {
            return String.valueOf(this.real);
        }

        if (this.real == 0) {
            return "i" + this.imaginary;
        }

        return this.real + (this.imaginary >= 0 ? "+" : "-") + "i" + Math.abs(this.imaginary);
    }

    /**
     * Uspoređuje dva kompleksna broja.
     * 
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        double eps = 1e-6;

        Complex complex = (Complex) o;

        if (Math.abs(this.real - complex.real) > eps)
            return false;

        return Math.abs(this.imaginary - complex.imaginary) <= eps;
    }
}
