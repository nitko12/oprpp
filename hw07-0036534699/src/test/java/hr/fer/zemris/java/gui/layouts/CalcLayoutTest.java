package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

public class CalcLayoutTest {
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    // Napišite junit testove koji će provjeriti:
    // 1) da se iznimka baca ako se pokuša koristiti ograničenje (r,s) gdje je r<1
    // || r>5 ili s<1 || s>7, 2) da se iznimka baca ako se pokuša koristiti
    // ograničenje (1,s) gdje je s>1 && s<6,
    // 3) da se iznimka baca ako se uz isto ograničenje pokuša postaviti više
    // komponenti.

    @Test
    public void test1() {
        CalcLayout layout = new CalcLayout(3);
        assertThrows(CalcLayoutException.class, () -> {
            layout.addLayoutComponent(new JLabel("x"), new RCPosition(0, 1));
        });

        assertThrows(CalcLayoutException.class, () -> {
            layout.addLayoutComponent(new JLabel("x"), new RCPosition(6, 1));
        });

        assertThrows(CalcLayoutException.class, () -> {
            layout.addLayoutComponent(new JLabel("x"), new RCPosition(1, 0));
        });

        assertThrows(CalcLayoutException.class, () -> {
            layout.addLayoutComponent(new JLabel("x"), new RCPosition(1, 8));
        });

        assertThrows(CalcLayoutException.class, () -> {
            layout.addLayoutComponent(new JLabel("x"), new RCPosition(1, 2));
        });

        assertThrows(CalcLayoutException.class, () -> {
            layout.addLayoutComponent(new JLabel("x"), new RCPosition(1, 3));
        });

        assertThrows(CalcLayoutException.class, () -> {
            layout.addLayoutComponent(new JLabel("x"), new RCPosition(1, 4));
        });

        assertThrows(CalcLayoutException.class, () -> {
            layout.addLayoutComponent(new JLabel("x"), new RCPosition(1, 5));
        });

        assertDoesNotThrow(() -> {
            layout.addLayoutComponent(new JLabel("x"), new RCPosition(1, 1));
        });

        assertThrows(CalcLayoutException.class, () -> {
            layout.addLayoutComponent(new JLabel("x"), new RCPosition(1, 1));
        });
    }

    @Test
    public void test2() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(10, 30));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(20, 15));
        p.add(l1, new RCPosition(2, 2));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

    @Test
    public void test3() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(108, 15));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(16, 30));
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

}
