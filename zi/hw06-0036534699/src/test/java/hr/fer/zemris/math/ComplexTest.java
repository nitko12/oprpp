package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ComplexTest {
    @Test
    public void testModule() {
        Complex c = new Complex(3, 4);
        assertEquals(5, c.module());
    }

    @Test
    public void testMultiply() {
        Complex c1 = new Complex(3, 4);
        Complex c2 = new Complex(5, 6);
        Complex c3 = c1.multiply(c2);

        Complex c4 = new Complex(-9, 38);

        assertEquals(c4, c3);
    }

    @Test
    public void testDivide() {
        Complex c1 = new Complex(3, 4);
        Complex c2 = new Complex(5, 6);
        Complex c3 = c1.divide(c2);

        Complex c4 = new Complex(39.0 / 61, 2.0 / 61);

        assertEquals(c4, c3);
    }

    @Test
    public void testAdd() {
        Complex c1 = new Complex(3, 4);
        Complex c2 = new Complex(5, 6);
        Complex c3 = c1.add(c2);

        Complex c4 = new Complex(8, 10);

        assertEquals(c4, c3);
    }

    @Test
    public void testSub() {
        Complex c1 = new Complex(3, 4);
        Complex c2 = new Complex(5, 6);
        Complex c3 = c1.sub(c2);

        Complex c4 = new Complex(-2, -2);

        assertEquals(c4, c3);
    }

    @Test
    public void testNegate() {
        Complex c1 = new Complex(3, 4);
        Complex c2 = c1.negate();

        Complex c3 = new Complex(-3, -4);

        assertEquals(c3, c2);
    }

    @Test
    public void testPower() {
        Complex c1 = new Complex(3, 4);
        Complex c2 = c1.power(3);

        Complex c3 = new Complex(-117, 44);

        assertEquals(c3, c2);
    }

    @Test
    public void testRoot() {
        Complex c1 = new Complex(1, 0);
        List<Complex> roots = c1.root(4);

        Complex c2 = new Complex(1, 0);
        Complex c3 = new Complex(0, 1);
        Complex c4 = new Complex(-1, 0);
        Complex c5 = new Complex(0, -1);

        assertEquals(c2, roots.get(0));
        assertEquals(c3, roots.get(1));
        assertEquals(c4, roots.get(2));
        assertEquals(c5, roots.get(3));
    }

    @Test
    public void testToString() {
        Complex c1 = new Complex(3, 4);
        assertEquals("3.0+i4.0", c1.toString());

        Complex c2 = new Complex(3, -4);
        assertEquals("3.0-i4.0", c2.toString());

        Complex c3 = new Complex(3, 0);
        assertEquals("3.0", c3.toString());

        Complex c4 = new Complex(0, 4);
        assertEquals("i4.0", c4.toString());
    }

    @Test
    public void testEquals() {
        Complex c1 = new Complex(3, 4);
        Complex c2 = new Complex(3, 4);
        Complex c3 = new Complex(3, 5);

        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
    }
}
