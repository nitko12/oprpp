package hr.fer.oprpp1.hw05.Crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw05.crypto.Util;

public class UtilTest {

    @Test
    public void testHextobyte() {

        byte[] bytes = Util.hextobyte("01aE22");
        assertEquals(3, bytes.length);
        assertEquals(1, bytes[0]);
        assertEquals(-82, bytes[1]);
        assertEquals(34, bytes[2]);

        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01aE2"));
    }

    @Test
    public void testBytetohex() {

        byte[] bytes = new byte[] { 1, -82, 34 };
        String hex = Util.bytetohex(bytes);
        assertEquals("01ae22", hex);
    }

}
