package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

    @Test
    public void testConstructors() {
        assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));

        LinkedListIndexedCollection col = new LinkedListIndexedCollection();
        assertEquals(0, col.size());

        col = new LinkedListIndexedCollection(new LinkedListIndexedCollection());
        assertEquals(0, col.size());

        col = new LinkedListIndexedCollection();
        for (int i = 0; i < 32; i++) {
            col.add(i);
        }

        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
        assertEquals(32, col2.size());

    }

    @Test
    public void testIsEmpty() {
        new CollectionCommonTests<LinkedListIndexedCollection>(LinkedListIndexedCollection::new).testIsEmpty();
    }

    @Test
    public void testSize() {
        new CollectionCommonTests<LinkedListIndexedCollection>(LinkedListIndexedCollection::new).testSize();
    }

    @Test
    public void testContains() {
        new CollectionCommonTests<LinkedListIndexedCollection>(LinkedListIndexedCollection::new).testContains();
    }

    @Test
    public void testToArray() {
        new CollectionCommonTests<LinkedListIndexedCollection>(LinkedListIndexedCollection::new).testToArray();
    }

    @Test
    public void testForEach() {
        new CollectionCommonTests<LinkedListIndexedCollection>(LinkedListIndexedCollection::new).testForEach();
    }

    @Test
    public void testAddAll() {
        new CollectionCommonTests<LinkedListIndexedCollection>(LinkedListIndexedCollection::new).testAddAll();
    }

    @Test
    public void testAdd() {
        new CollectionCommonTests<LinkedListIndexedCollection>(LinkedListIndexedCollection::new).testAdd();
    }

    @Test
    public void testClear() {
        new CollectionCommonTests<LinkedListIndexedCollection>(LinkedListIndexedCollection::new).testClear();
    }

    @Test
    public void testGet() {
        LinkedListIndexedCollection col = new LinkedListIndexedCollection();

        assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> col.get(0));

        col.add("test1");

        assertDoesNotThrow(() -> col.get(0));
        assertEquals("test1", col.get(0));

        assertThrows(IndexOutOfBoundsException.class, () -> col.get(1));
    }

    @Test
    public void testInsert() {
        LinkedListIndexedCollection col = new LinkedListIndexedCollection();

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
        LinkedListIndexedCollection col = new LinkedListIndexedCollection();

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
        LinkedListIndexedCollection col = new LinkedListIndexedCollection();

        assertFalse(col.remove("test1"));

        col.add("test1");
        col.add("test2");

        assertTrue(col.remove("test1"));
        assertEquals(1, col.size());
        assertEquals("test2", col.get(0));

        assertTrue(col.remove("test2"));
        assertEquals(0, col.size());
    }

    @Test
    public void testRemoveIndex() {
        LinkedListIndexedCollection col = new LinkedListIndexedCollection();

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

        col.add("test2");
        col.add("test3");
        col.add("test4");

        assertDoesNotThrow(() -> col.remove(1));
    }
}
