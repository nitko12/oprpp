package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComplexPolynomialTest {

    @Test
    public void testOrder() {
        // (7+2i)z^3+2z^2+5z+1

        ComplexPolynomial p = new ComplexPolynomial(
                new Complex(7, 2),
                new Complex(2, 0),
                new Complex(5, 0),
                new Complex(1, 0));

        assertEquals(3, p.order());

        ComplexPolynomial p2 = new ComplexPolynomial(
                new Complex(7, 2),
                new Complex(2, 0),
                new Complex(5, 0),
                new Complex(0, 0));

        assertEquals(2, p2.order());
    }

    @Test
    public void testMultiply() {
        // ((7+2i)z^3+2z^2+5z+1) * ((7+2i)z^3+2z^2+5z+1)

        ComplexPolynomial p = new ComplexPolynomial(
                new Complex(1, 0),
                new Complex(5, 0),
                new Complex(2, 0),
                new Complex(7, 2));

        ComplexPolynomial q = new ComplexPolynomial(
                new Complex(1, 0),
                new Complex(5, 0),
                new Complex(2, 0),
                new Complex(7, 2));

        // 1 + 10 z + 29 z^2 + (34 + 4 i) z^3 + (74 + 20 i) z^4 + (28 + 8 i) z^5 + (45 +
        // 28 i) z^6
        ComplexPolynomial res = new ComplexPolynomial(
                new Complex(1, 0),
                new Complex(10, 0),
                new Complex(29, 0),
                new Complex(34, 4),
                new Complex(74, 20),
                new Complex(28, 8),
                new Complex(45, 28));

        assertEquals(res, p.multiply(q));
    }

    @Test
    public void testDerive() {
        // computes first derivative of this polynomial; for example, for //
        // // (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5

        ComplexPolynomial p = new ComplexPolynomial(
                new Complex(1, 0),
                new Complex(5, 0),
                new Complex(2, 0),
                new Complex(7, 2));

        ComplexPolynomial q = new ComplexPolynomial(
                new Complex(5, 0),
                new Complex(4, 0),
                new Complex(21, 6));

        assertEquals(q, p.derive());

    }

    @Test
    public void testApply() {
        // computes polynomial value at given point z

        // ((7+2i)z^3+2z^2+5z+1)

        ComplexPolynomial p = new ComplexPolynomial(
                new Complex(1, 0),
                new Complex(5, 0),
                new Complex(2, 0),
                new Complex(7, 2));

        Complex z = new Complex(32, 11);

        Complex res = new Complex(85109, 270994);

        assertEquals(res, p.apply(z));
    }

    @Test
    public void testToString() {
        // (7+2i)z^3+2z^2+5z+1

        ComplexPolynomial p = new ComplexPolynomial(
                new Complex(2, 0),
                new Complex(5, 0),
                new Complex(1, 0),
                new Complex(7, 2));

        assertEquals("(7.0+i2.0)z^3+(1.0)z^2+(5.0)z^1+(2.0)", p.toString());

    }

    @Test
    public void testEquals() {
        ComplexPolynomial p = new ComplexPolynomial(Complex.ONE, Complex.ONE, Complex.ONE, Complex.ONE);
        ComplexPolynomial q = new ComplexPolynomial(Complex.ONE, Complex.ONE, Complex.ONE, Complex.ONE);
        ComplexPolynomial r = new ComplexPolynomial(Complex.ONE, Complex.ONE, Complex.ONE);
        assertEquals(p, q);
        assertNotEquals(p, r);
    }
}
