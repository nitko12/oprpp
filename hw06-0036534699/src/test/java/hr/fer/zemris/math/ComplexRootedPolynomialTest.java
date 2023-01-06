package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComplexRootedPolynomialTest {

    @Test
    public void testApply() {
        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
                new Complex(2, 0),
                new Complex(1, 0),
                new Complex(-1, 0));

        Complex z1 = new Complex(2, 0);

        assertEquals(Complex.ZERO, crp.apply(z1));

        Complex z2 = new Complex(1, 0);

        assertEquals(Complex.ZERO, crp.apply(z2));

        Complex z3 = new Complex(-1, 0);

        assertEquals(Complex.ZERO, crp.apply(z3));

    }

    @Test
    public void testToComplexPolynom() {
        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
                new Complex(2, 0),
                new Complex(1, 1),
                new Complex(-1, 2));

        ComplexPolynomial cp = new ComplexPolynomial(
                new Complex(-6, 2),
                new Complex(0, -6),
                new Complex(2, 0));

        // (-6 + 2 i) - 6 i z + 2 z^2

        assertEquals(cp, crp.toComplexPolynom());
    }

    @Test
    public void testToString() {
        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
                new Complex(2, 0),
                new Complex(1, 1),
                new Complex(-1, 0));

        assertEquals("(2.0)*(z-(1.0+i1.0))*(z-(-1.0))", crp.toString());
    }

    @Test
    public void testIndexOfClosestRootFor() {
        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
                new Complex(2, 0),
                new Complex(1, 1),
                new Complex(-1, 0));

        Complex z1 = new Complex(2, 0);

        assertEquals(0, crp.indexOfClosestRootFor(z1, 0.1));

        Complex z2 = new Complex(1, 1);

        assertEquals(1, crp.indexOfClosestRootFor(z2, 0.1));

        Complex z3 = new Complex(-1, 0);

        assertEquals(2, crp.indexOfClosestRootFor(z3, 0.1));

        Complex z4 = new Complex(1, 0);

        assertEquals(-1, crp.indexOfClosestRootFor(z4, 0.1));
    }
}
