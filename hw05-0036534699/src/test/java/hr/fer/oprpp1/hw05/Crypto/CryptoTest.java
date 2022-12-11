package hr.fer.oprpp1.hw05.Crypto;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw05.crypto.Crypto;

public class CryptoTest {
    @Test
    public void testShaDigestStream() {
        Crypto crypto = new Crypto();

        try {
            ByteArrayInputStream is = new ByteArrayInputStream("test123".getBytes());

            String hash = crypto.shaDigestStream(is);

            assertEquals("ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae", hash);

        } catch (Exception e) {
            fail("Exception thrown!");
        }
    }

    @Test
    public void testEncryptDecryptStream() {
        String testString = "test123";

        ByteArrayInputStream is = new ByteArrayInputStream(testString.getBytes());
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        String password = "e52217e3ee213ef1ffdee3a192e2ac7e";
        String iv = "000102030405060708090a0b0c0d0e0f";

        try {
            Crypto.encryptDecryptStream(
                    is,
                    os,
                    password,
                    iv,
                    true);

            byte[] encrypted = os.toByteArray();

            is = new ByteArrayInputStream(encrypted);
            os = new ByteArrayOutputStream();

            Crypto.encryptDecryptStream(
                    is,
                    os,
                    password,
                    iv,
                    false);

            byte[] decrypted = os.toByteArray();

            assertEquals(testString, new String(decrypted));

            final ByteArrayInputStream is2 = new ByteArrayInputStream(testString.getBytes());
            final ByteArrayOutputStream os2 = new ByteArrayOutputStream();

            assertThrows(IllegalArgumentException.class, () -> Crypto.encryptDecryptStream(
                    is2,
                    os2,
                    password,
                    "000102030405060708090a0b0c0d0e0",
                    true));

            final ByteArrayInputStream is3 = new ByteArrayInputStream(testString.getBytes());
            final ByteArrayOutputStream os3 = new ByteArrayOutputStream();

            assertThrows(IllegalArgumentException.class, () -> Crypto.encryptDecryptStream(
                    is3,
                    os3,
                    "e52217e3ee213ef1ffdee3a192e2ac7",
                    iv,
                    true));

        } catch (Exception e) {
            fail("Exception thrown!");
        }
    }
}
