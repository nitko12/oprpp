package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ObjectStackTest {
    @Test
    public void testConstructors() {
        ObjectStack stack = new ObjectStack();

        // jer unused warning, nije potreban test
        assertThrows(EmptyStackException.class, () -> stack.pop());
    }

    @Test
    public void testIsEmpty() {
        ObjectStack stack = new ObjectStack();

        assertEquals(true, stack.isEmpty());
    }

    @Test
    public void testSize() {
        ObjectStack stack = new ObjectStack();

        assertEquals(0, stack.size());

        stack.push("test1");
        assertEquals(1, stack.size());
    }

    @Test
    public void testPush() {
        ObjectStack stack = new ObjectStack();

        stack.push("test1");
        assertEquals(1, stack.size());

        assertThrows(NullPointerException.class, () -> stack.push(null));
    }

    @Test
    public void testPop() {
        ObjectStack stack = new ObjectStack();

        stack.push("test1");
        stack.push("test2");
        stack.push("test3");

        assertEquals("test3", stack.pop());
        assertEquals(2, stack.size());
        assertEquals("test2", stack.pop());
        assertEquals(1, stack.size());
        assertEquals("test1", stack.pop());
        assertEquals(0, stack.size());
        assertThrows(EmptyStackException.class, () -> stack.pop());
    }

    @Test
    public void testPeek() {
        ObjectStack stack = new ObjectStack();

        stack.push("test1");

        assertEquals("test1", stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    public void testClear() {
        ObjectStack stack = new ObjectStack();

        stack.push("test1");
        stack.push("test2");
        stack.push("test3");

        stack.clear();

        assertEquals(0, stack.size());
    }
}
