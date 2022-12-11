package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SimpleIntegrationTest {
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

        class P implements Processor {

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

    @Test
    public void assigmentSampleTest2() {
        Collection col1 = new LinkedListIndexedCollection();
        Collection col2 = new ArrayIndexedCollection();

        col1.add(2);
        col1.add(3);
        col1.add(4);
        col1.add(5);
        col1.add(6);
        col1.add("string");
        col1.add(8);
        col2.add(12);

        class EvenIntegerTester implements Tester {
            @Override
            public boolean test(Object obj) {
                if (!(obj instanceof Integer))
                    return false;
                return (int) obj % 2 == 0;
            }
        }

        col2.addAllSatisfying(col1, new EvenIntegerTester());
        // col2.forEach(System.out::println);

        assertArrayEquals(new Object[] { 12, 2, 4, 6, 8 }, col2.toArray());

    }
}
