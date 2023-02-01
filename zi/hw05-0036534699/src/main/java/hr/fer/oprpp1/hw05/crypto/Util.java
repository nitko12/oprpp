package hr.fer.oprpp1.hw05.crypto;

public class Util {
    public static byte[] hextobyte(String hex) {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Duljina ključa mora biti paran broj!");
        }

        byte[] res = new byte[hex.length() / 2];

        for (int i = 0; i < hex.length(); i += 2) {
            res[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }

        return res;
    }

    public static String bytetohex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
