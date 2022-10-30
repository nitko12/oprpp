package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DictionaryTest {

    @Test
    void testIsEmpty() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();
        assertTrue(dictionary.isEmpty());

        dictionary.put(1, 1);

        assertFalse(dictionary.isEmpty());
    }

    @Test
    void testSize() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();
        assertEquals(0, dictionary.size());

        dictionary.put(1, 1);
        dictionary.put(2, 2);
        dictionary.put(3, 3);

        assertEquals(3, dictionary.size());
    }

    @Test
    void testClear() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();
        dictionary.put(1, 1);
        dictionary.put(2, 2);
        dictionary.put(3, 3);

        dictionary.clear();

        assertEquals(0, dictionary.size());
    }

    @Test
    void testPut() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();
        dictionary.put(1, 1);
        dictionary.put(2, 2);
        dictionary.put(3, 3);

        assertEquals(3, dictionary.size());

        dictionary.put(1, 4);
        assertEquals(4, dictionary.get(1));

        assertThrows(NullPointerException.class, () -> dictionary.put(null, 1));
    }

    @Test
    void testGet() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();
        dictionary.put(1, 2);
        dictionary.put(2, 3);
        dictionary.put(3, 1);

        assertEquals(2, dictionary.get(1));
        assertEquals(3, dictionary.get(2));
        assertEquals(1, dictionary.get(3));

        assertEquals(null, dictionary.get(4));

        assertThrows(NullPointerException.class, () -> dictionary.get(null));
    }

    @Test
    void testRemove() {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();
        dictionary.put(1, 2);
        dictionary.put(2, 3);
        dictionary.put(3, 1);

        assertEquals(2, dictionary.remove(1));
        assertEquals(null, dictionary.remove(1));

        assertEquals(3, dictionary.remove(2));
        assertEquals(null, dictionary.remove(2));

        assertEquals(1, dictionary.remove(3));
        assertEquals(null, dictionary.remove(3));

        assertThrows(NullPointerException.class, () -> dictionary.remove(null));
    }
}
