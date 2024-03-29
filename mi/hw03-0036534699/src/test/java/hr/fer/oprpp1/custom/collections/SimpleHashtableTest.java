package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {
    @Test
    public void testConstructors() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        assertEquals(0, table.size());

        table = new SimpleHashtable<>(2);
        assertEquals(0, table.size());

        assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(0));
    }

    @Test
    public void testPut() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        table.put(1, 2);
        assertEquals(1, table.size());

        table.put(2, 2);
        assertEquals(2, table.size());

        table.put(3, 2);
        assertEquals(3, table.size());

        table.put(4, 5);
        assertEquals(4, table.size());

        table.put(4, 5);
        assertEquals(4, table.size());

        assertThrows(NullPointerException.class, () -> table.put(null, 5));

        table.put(4 + 16, 7);
        table.put(4 + 32, 8);
        table.put(4 + 64, 9);

        assertEquals(7, table.size());
        assertEquals(9, table.get(4 + 64));

    }

    @Test
    public void testGet() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        table.put(1, 2);
        table.put(2, 3);
        table.put(3, 4);
        table.put(4, 5);
        table.put(1, 6);

        assertEquals(6, table.get(1));
        assertEquals(3, table.get(2));
        assertEquals(4, table.get(3));
        assertEquals(5, table.get(4));
        assertNull(table.get(5));

        assertThrows(NullPointerException.class, () -> table.get(null));

        assertNull(table.get(1 + 16));
        assertNull(table.get(1 + 32));
    }

    @Test
    public void testContainsKey() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        table.put(1, 2);
        table.put(2, 3);
        table.put(3, 4);
        table.put(4, 5);
        table.put(1, 6);

        assertTrue(table.containsKey(1));
        assertTrue(table.containsKey(2));
        assertTrue(table.containsKey(3));
        assertTrue(table.containsKey(4));
        assertFalse(table.containsKey(5));

        assertThrows(NullPointerException.class, () -> table.containsKey(null));

        assertFalse(table.containsKey(1 + 16));
        assertFalse(table.containsKey(1 + 32));
    }

    @Test
    public void testContainsValue() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        table.put(1, 2);
        table.put(2, 3);
        table.put(3, 4);
        table.put(4, 5);
        table.put(1, 6);

        assertFalse(table.containsValue(2));
        assertTrue(table.containsValue(3));
        assertTrue(table.containsValue(4));
        assertTrue(table.containsValue(5));
        assertTrue(table.containsValue(6));
        assertFalse(table.containsValue(7));
    }

    @Test
    public void testRemove() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        table.put(1, 2);
        table.put(2, 3);
        table.put(3, 4);
        table.put(4, 5);
        table.put(1, 6);

        assertEquals(6, table.remove(1));
        assertEquals(3, table.size());
        assertNull(table.remove(1));
        assertNull(table.get(1));

        assertEquals(3, table.size());

        assertEquals(3, table.remove(2));
        assertEquals(2, table.size());

        assertEquals(4, table.remove(3));
        assertEquals(1, table.size());

        assertEquals(5, table.remove(4));
        assertEquals(0, table.size());

        table.put(1, 2);
        table.put(1 + 16, 2);
        table.put(1 + 32, 2);
        table.put(1 + 64, 2);

        table.remove(1 + 64);
        assertEquals(3, table.size());

        table.remove(1 + 16);
        assertEquals(2, table.size());

        table.remove(1 + 32);
        assertEquals(1, table.size());

        table.remove(1 + 128);
        assertEquals(1, table.size());
    }

    @Test
    public void testIsEmpty() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        assertTrue(table.isEmpty());

        table.put(1, 2);
        assertFalse(table.isEmpty());

        table.remove(1);
        assertTrue(table.isEmpty());
    }

    @Test
    public void testToString() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        assertEquals("[]", table.toString());

        table.put(1, 2);
        table.put(2, 3);
        table.put(3, 4);
        table.put(4, 5);
        table.put(1, 6);

        assertEquals("[1=6, 2=3, 3=4, 4=5]", table.toString());

        table.remove(1);

        assertEquals("[2=3, 3=4, 4=5]", table.toString());
    }

    @Test
    public void testToArray() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        SimpleHashtable.TableEntry<Integer, Integer>[] array = table.toArray();

        assertEquals(0, array.length);

        table.put(1, 2);
        table.put(2, 3);
        table.put(3, 4);
        table.put(4, 5);
        table.put(1, 6);

        array = table.toArray();

        assertEquals(4, array.length);

        for (int i = 0; i < array.length; i++) {
            assertTrue(array[i] instanceof SimpleHashtable.TableEntry);
        }

        assertEquals(1, array[0].getKey()); // redoslijed nebitan, no potrefilo se ovako
        assertEquals(2, array[1].getKey()); // vjerojatno jer je hash inta on sam
        assertEquals(3, array[2].getKey());
        assertEquals(4, array[3].getKey());
    }

    @Test
    void clear() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        table.put(1, 2);
        table.put(2, 3);
        table.put(3, 4);
        table.put(4, 5);
        table.put(1, 6);

        assertEquals(4, table.size());

        table.clear();

        assertEquals(0, table.size());
        assertTrue(table.isEmpty());
    }

    @Test
    void testRehash() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>(2);

        for (int i = 0; i < 512; ++i)
            table.put(i, i + 1);

        for (int i = 0; i < 512; ++i)
            assertEquals(i + 1, table.get(i));
    }

    @Test
    public void testIterable() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        table.put(1, 2);
        table.put(1 + 16, 3);
        table.put(1 + 32, 3);
        table.put(3, 4);
        table.put(4, 5);
        table.put(1, 6);

        final var iterator = table.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next().getKey());
        assertTrue(iterator.hasNext());
        assertEquals(1 + 32, iterator.next().getKey());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next().getKey());
        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next().getKey());
        assertTrue(iterator.hasNext());
        assertEquals(1 + 16, iterator.next().getKey());
        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, () -> iterator.next());

        // test remove

        final var iterator2 = table.iterator();

        assertTrue(iterator2.hasNext());
        assertEquals(1, iterator2.next().getKey());
        iterator2.remove();
        assertThrows(IllegalStateException.class, () -> iterator2.remove());
        assertFalse(table.containsKey(1));

        assertTrue(iterator2.hasNext());
        assertEquals(1 + 32, iterator2.next().getKey());
        iterator2.remove();
        assertFalse(table.containsKey(1 + 32));

        assertTrue(iterator2.hasNext());
        assertEquals(3, iterator2.next().getKey());
        iterator2.remove();
        assertFalse(table.containsKey(3));

        assertTrue(iterator2.hasNext());
        assertEquals(4, iterator2.next().getKey());
        iterator2.remove();
        assertFalse(table.containsKey(4));

    }

    @Test
    public void testConcurrentModification() {

        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        table.put(1, 2);
        table.put(1 + 16, 3);
        table.put(1 + 32, 3);
        table.put(3, 4);

        String s = "";

        Iterator<SimpleHashtable.TableEntry<Integer, Integer>> iter = table.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<Integer, Integer> pair = iter.next();
            if (pair.getKey().equals(17)) {
                iter.remove();
            } else {
                s += pair.getKey();
            }
        }

        SimpleHashtable<Integer, Integer> table2 = new SimpleHashtable<>();

        table2.put(1, 2);
        table2.put(1 + 16, 3);
        table2.put(1 + 32, 3);
        table2.put(3, 4);

        assertEquals("1333", s);

        final var iter2 = table2.iterator();

        assertEquals(1, iter2.next().getKey());

        table2.put(1 + 16, 5);

        assertDoesNotThrow(() -> iter2.next());

        table2.put(1 + 64, 5);

        assertThrows(ConcurrentModificationException.class, () -> iter2.next());
    }

    @Test
    public void testClear() {
        SimpleHashtable<Integer, Integer> table = new SimpleHashtable<>();

        table.put(1, 2);
        table.put(1 + 16, 3);
        table.put(1 + 32, 3);
        table.put(3, 4);
        table.put(4, 5);
        table.put(1, 6);

        assertEquals(5, table.size());

        table.clear();

        assertEquals(0, table.size());
        assertTrue(table.isEmpty());
    }
}
