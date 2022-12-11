package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
    @Test
    public void testConstructors() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<Integer>(null));
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection<Integer>(-1));
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<Integer>(null, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new ArrayIndexedCollection<Integer>(new ArrayIndexedCollection<Integer>(), -1));

        ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
        assertEquals(0, col.size());

        col = new ArrayIndexedCollection<Integer>(1);
        assertEquals(0, col.size());

        col = new ArrayIndexedCollection<Integer>(new ArrayIndexedCollection<Integer>());
        assertEquals(0, col.size());

        col = new ArrayIndexedCollection<Integer>();
        for (int i = 0; i < 32; i++) {
            col.add(i);
        }

        ArrayIndexedCollection<Integer> col2 = new ArrayIndexedCollection<>(col);
        assertEquals(32, col2.size());

    }

    // nisam koristio nasljedivanje/nesto takvo za slucaj da pokoji test treba
    // dodatne provjere
    // na kraju nisu trebali :(

    @Test
    public void testIsEmpty() {
        new CollectionCommonTests<ArrayIndexedCollection<String>>(ArrayIndexedCollection<String>::new).testIsEmpty();
    }

    @Test
    public void testSize() {
        new CollectionCommonTests<ArrayIndexedCollection<String>>(ArrayIndexedCollection::new).testSize();
    }

    @Test
    public void testContains() {
        new CollectionCommonTests<ArrayIndexedCollection<String>>(ArrayIndexedCollection::new).testContains();
    }

    @Test
    public void testToArray() {
        new CollectionCommonTests<ArrayIndexedCollection<String>>(ArrayIndexedCollection::new).testToArray();
    }

    @Test
    public void testForEach() {
        new CollectionCommonTests<ArrayIndexedCollection<String>>(ArrayIndexedCollection::new).testForEach();
    }

    @Test
    public void testAddAll() {
        new CollectionCommonTests<ArrayIndexedCollection<String>>(ArrayIndexedCollection::new).testAddAll();
    }

    @Test
    public void testAdd() {
        new CollectionCommonTests<ArrayIndexedCollection<String>>(ArrayIndexedCollection::new).testAdd();
    }

    @Test
    public void testClear() {
        new CollectionCommonTests<ArrayIndexedCollection<String>>(ArrayIndexedCollection::new).testClear();
    }

    @Test
    public void testGet() {
        ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>();

        assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> col.get(0));

        col.add("test1");

        assertDoesNotThrow(() -> col.get(0));
        assertEquals("test1", col.get(0));

        assertThrows(IndexOutOfBoundsException.class, () -> col.get(1));
    }

    @Test
    public void testInsert() {
        ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>(2);

        assertThrows(IndexOutOfBoundsException.class, () -> col.insert("test1", 1));
        assertThrows(IndexOutOfBoundsException.class, () -> col.insert("test1", -1));

        col.add("test1");
        col.add("test2");

        assertDoesNotThrow(() -> col.insert("test3", 0));
        assertEquals(3, col.size());
        assertEquals("test3", col.get(0));
        assertEquals("test1", col.get(1));
        assertEquals("test2", col.get(2));

        assertDoesNotThrow(() -> col.insert("test4", 2));
        assertEquals(4, col.size());
        assertEquals("test3", col.get(0));
        assertEquals("test1", col.get(1));
        assertEquals("test4", col.get(2));
        assertEquals("test2", col.get(3));

        assertDoesNotThrow(() -> col.insert("test5", 4));
        assertEquals(5, col.size());
        assertEquals("test3", col.get(0));
        assertEquals("test1", col.get(1));
        assertEquals("test4", col.get(2));
        assertEquals("test2", col.get(3));
        assertEquals("test5", col.get(4));

        assertThrows(IndexOutOfBoundsException.class, () -> col.insert("test6", 6));

        assertThrows(NullPointerException.class, () -> col.insert(null, 1));
    }

    @Test
    public void testIndexOf() {
        ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>();

        assertEquals(-1, col.indexOf("test1"));

        col.add("test1");
        col.add("test2");

        assertEquals(0, col.indexOf("test1"));
        assertEquals(1, col.indexOf("test2"));
        assertEquals(-1, col.indexOf(null));
        assertEquals(-1, col.indexOf("test3"));
    }

    @Test
    public void testRemoveValue() {
        ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>();

        assertFalse(col.remove("test1"));

        col.add("test1");
        col.add("test2");

        assertTrue(col.remove("test1"));
        assertEquals(1, col.size());
        assertEquals("test2", col.get(0));

        assertTrue(col.remove("test2"));
        assertEquals(0, col.size());

        col.add("test3");
        col.add("test4");
        col.add("test5");

        assertTrue(col.remove("test4"));
    }

    @Test
    public void testRemoveIndex() {
        ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>();

        assertThrows(IndexOutOfBoundsException.class, () -> col.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> col.remove(0));

        col.add("test1");
        col.add("test2");

        assertDoesNotThrow(() -> col.remove(1));
        assertEquals(1, col.size());
        assertEquals("test1", col.get(0));

        assertDoesNotThrow(() -> col.remove(0));
        assertEquals(0, col.size());

        assertThrows(IndexOutOfBoundsException.class, () -> col.remove(0));
    }

    @Test
    public void testElementsGetter() {
        new CollectionCommonTests<ArrayIndexedCollection<String>>(ArrayIndexedCollection::new).testElementsGetter();
    }
}
