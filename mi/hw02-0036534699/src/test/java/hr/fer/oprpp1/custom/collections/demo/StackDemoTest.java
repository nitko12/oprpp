package hr.fer.oprpp1.custom.collections.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StackDemoTest {
    @Test
    public void testEvaluate() {
        assertThrows(NullPointerException.class, () -> StackDemo.evaluate(null));

        String s = "8 2 /";

        int res = StackDemo.evaluate(s.split(" "));

        assertEquals(4, res);

        s = "-1 8 2 / +";

        res = StackDemo.evaluate(s.split(" "));

        assertEquals(3, res);

        s = "8 -2 / -1 *";

        res = StackDemo.evaluate(s.split(" "));

        assertEquals(4, res);

        s = "8 -2 / -1 * 1";

        final String[] s1 = s.split(" ");
        assertThrows(IllegalArgumentException.class, () -> StackDemo.evaluate(s1));

        s = "8 0 /";

        final String[] s2 = s.split(" ");
        assertThrows(IllegalArgumentException.class, () -> StackDemo.evaluate(s2));

        s = "8 0 %";

        final String[] s3 = s.split(" ");
        assertThrows(IllegalArgumentException.class, () -> StackDemo.evaluate(s3));

        s = "3 2 %";

        res = StackDemo.evaluate(s.split(" "));
        assertEquals(1, res);

        s = "3 2 /";

        res = StackDemo.evaluate(s.split(" "));
        assertEquals(1, res);

        s = "3 2 -";

        res = StackDemo.evaluate(s.split(" "));
        assertEquals(1, res);

        s = "3 2 t";

        final String[] s4 = s.split(" ");
        assertThrows(IllegalArgumentException.class, () -> StackDemo.evaluate(s4));
    }

}
