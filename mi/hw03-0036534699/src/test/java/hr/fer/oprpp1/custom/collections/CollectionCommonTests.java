package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class CollectionCommonTests<T extends Collection<String>> {

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

        assertArrayEquals(new String[0], col.toArray());

        col.add("test1");
        assertArrayEquals(new String[] { "test1" }, col.toArray());

        col.add("test2");
        assertArrayEquals(new String[] { "test1", "test2" }, col.toArray());

    }

    public void testForEach() {
        T col = factory.get();

        class SumProcessor implements Processor<String> {
            String s = "";

            @Override
            public void process(String value) {
                s += (String) value;
            }

            public String getS() {
                return s;
            }
        }

        SumProcessor processor = new SumProcessor();

        assertDoesNotThrow(() -> col.forEach(processor));

        assertEquals("", processor.getS());

        col.add("1");
        col.add("2");
        col.add("3");

        assertDoesNotThrow(() -> col.forEach(processor));

        assertEquals("123", processor.getS());
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

    public void testElementsGetter() {
        Collection<String> col = factory.get();

        col.add("Ana");
        col.add("Jasna");

        final ElementsGetter<String> getter = col.createElementsGetter();

        assertTrue(getter.hasNextElement());
        assertEquals("Ana", getter.getNextElement());

        assertTrue(getter.hasNextElement());
        assertEquals("Jasna", getter.getNextElement());

        assertFalse(getter.hasNextElement());
        assertThrows(NoSuchElementException.class, () -> getter.getNextElement());

        col.add("Ana");

        final ElementsGetter<String> getter2 = col.createElementsGetter();
        assertTrue(getter2.hasNextElement());

        col.add("Jasna");
        assertThrows(ConcurrentModificationException.class, () -> getter2.getNextElement());

        final ElementsGetter<String> getter3 = col.createElementsGetter();

        assertTrue(getter3.hasNextElement());

        col.clear();
        assertThrows(ConcurrentModificationException.class, () -> getter3.getNextElement());

        col.add("Josip");
        col.add("Josip2");

        final ElementsGetter<String> getter4 = col.createElementsGetter();
        class LocalProcessor implements Processor<String> {
            private String s = "";

            @Override
            public void process(String value) {
                s += value;
            }

            public String getOut() {
                return s;
            }
        }

        LocalProcessor processor = new LocalProcessor();

        assertDoesNotThrow(() -> getter4.processRemaining(processor));

        assertEquals("JosipJosip2", processor.getOut());
    }
}
