package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SimpleIntegration {
    @Test
    public void assigmentSampleTest() {
        ArrayIndexedCollection col = new ArrayIndexedCollection(2);
        col.add(Integer.valueOf(20));
        col.add("New York");
        col.add("San Francisco");

        assertTrue(col.contains("New York"));

        col.remove(1);

        assertEquals("San Francisco", col.get(1));
        assertEquals(2, col.size());

        col.add("Los Angeles");

        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);

        class P extends Processor {

            private String s = "";

            public void process(Object o) {
                s += o + ",";
            }

            public String getS() {
                return "[" + s.substring(0, s.length() - 1) + "]";
            }
        }

        P p = new P();
        col.forEach(p);
        assertEquals("[20,San Francisco,Los Angeles]", p.getS());
        assertEquals("[20, San Francisco, Los Angeles]", Arrays.toString(col.toArray()));

        p = new P();
        col2.forEach(p);
        assertEquals("[20,San Francisco,Los Angeles]", p.getS());
        assertEquals("[20, San Francisco, Los Angeles]", Arrays.toString(col2.toArray()));

        assertTrue(col.contains(col2.get(1)));

        assertTrue(col2.contains(col.get(1)));

        col.remove(Integer.valueOf(20));
        assertEquals(2, col.size());

    }
}
