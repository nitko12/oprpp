package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Supplier;

public class CollectionCommonTests<T extends Collection> {

    private Supplier<T> factory;

    public CollectionCommonTests(Supplier<T> factory) {
        this.factory = factory;
    }

    public void testIsEmpty() {
        T col = factory.get();

        assertTrue(col.isEmpty());

        col.add("test1");
        assertFalse(col.isEmpty());
    }

    public void testSize() {
        T col = factory.get();

        assertEquals(0, col.size());

        col.add("test1");
        assertEquals(1, col.size());

        col.add("test2");
        assertEquals(2, col.size());
    }

    public void testContains() {
        T col = factory.get();

        assertFalse(col.contains("test1"));

        col.add("test1");
        assertTrue(col.contains("test1"));
    }

    public void testToArray() {
        T col = factory.get();

        assertArrayEquals(new Object[0], col.toArray());

        col.add("test1");
        assertArrayEquals(new Object[] { "test1" }, col.toArray());

        col.add("test2");
        assertArrayEquals(new Object[] { "test1", "test2" }, col.toArray());

    }

    public void testForEach() {
        T col = factory.get();

        class SumProcessor implements Processor {
            int sum = 0;

            @Override
            public void process(Object value) {
                sum += (int) value;
            }

            public int getSum() {
                return sum;
            }
        }

        SumProcessor processor = new SumProcessor();

        assertDoesNotThrow(() -> col.forEach(processor));

        assertEquals(0, processor.getSum());

        col.add(1);
        col.add(2);
        col.add(3);

        assertDoesNotThrow(() -> col.forEach(processor));

        assertEquals(6, processor.getSum());
    }

    public void testAddAll() {
        T col = factory.get();

        T col2 = factory.get();

        assertDoesNotThrow(() -> col.addAll(col2));

        assertEquals(0, col.size());

        col2.add("test1");
        col2.add("test2");

        assertDoesNotThrow(() -> col.addAll(col2));

        assertEquals(2, col.size());
    }

    public void testAdd() {
        T col = factory.get();

        assertDoesNotThrow(() -> col.add("test1"));

        assertEquals(1, col.size());

        assertThrows(NullPointerException.class, () -> col.add(null));
    }

    public void testClear() {
        T col = factory.get();

        assertDoesNotThrow(() -> col.clear());

        col.add("test1");
        col.add("test2");

        assertDoesNotThrow(() -> col.clear());

        assertEquals(0, col.size());
    }

}
