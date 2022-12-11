package hr.fer.oprpp1.hw05.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    public static void main(String[] args) {

        System.out.println(args.length);
        System.out.println(args[0]);
        System.out.println(args[1]);

        if (args.length == 2 && args[0].equals("checksha")) {
            handleChecksha(args[1]);
        } else if (args.length == 3 && args[0].equals("encrypt")) {
            handleEncrypt(args[1], args[2]);
        } else if (args.length == 3 && args[0].equals("decrypt")) {
            handleDecrypt(args[1], args[2]);
        } else {
            System.out.println("Nepoznata naredba ili pogrešan broj argumenata!");
        }
    }

    private static void handleChecksha(String arg) {
        System.out.println("Please provide expected sha-256 digest for hw05test.bin:");
        System.out.print("> ");

        String testHash = System.console().readLine();

        String hashString = null;

        try {
            hashString = shaDigestStream(new FileInputStream(arg));
        } catch (IOException | NoSuchAlgorithmException e) {
            System.out.println("Error pri čitanju datoteke!");

            return;
        }

        if (hashString.equals(testHash)) {
            System.out.println("Digesting completed. Digest of " + arg + " matches expected digest.");
        } else {
            System.out.println(
                    "Digesting completed. Digest of " + arg + " does not match the expected digest. Digest was: "
                            + hashString);

        }

    }

    public static String shaDigestStream(InputStream is) throws IOException, NoSuchAlgorithmException {
        byte[] buffer = new byte[4096];

        MessageDigest digest = null;

        digest = MessageDigest.getInstance("SHA-256");

        int bytesRead = 0;
        while ((bytesRead = is.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }

        byte[] hash = digest.digest();

        return Util.bytetohex(hash);
    }

    private static void handleEncrypt(String from, String to) {

        System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
        String password = System.console().readLine();

        System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
        String iv = System.console().readLine();

        try (
                InputStream fis = new FileInputStream(from);
                OutputStream tos = new FileOutputStream(to);) {

            encryptDecryptStream(fis, tos, password, iv, true);

        } catch (IOException e) {
            System.out.println("Error pri čitanju datoteke! " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Error pri šifriranju! " + e.getMessage());
            return;
        }

        System.out.println("Encryption completed. Generated file " + to + " based on file " + from + ".");
    }

    private static void handleDecrypt(String from, String to) {

        System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
        String password = System.console().readLine();

        System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
        String iv = System.console().readLine();

        try (
                InputStream fis = new FileInputStream(from);
                OutputStream tos = new FileOutputStream(to);) {

            encryptDecryptStream(fis, tos, password, iv, false);

        } catch (IOException e) {
            System.out.println("Error pri čitanju datoteke! " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Error pri šifriranju! " + e.getMessage());
            return;
        }

        System.out.println("Decryption completed. Generated file " + to + " based on file " + from + ".");
    }

    public static void encryptDecryptStream(
            InputStream is, OutputStream to,
            String password, String iv,
            boolean encrypt)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {

        SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(password), "AES");
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(iv));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

        byte[] buffer = new byte[4096];

        int bytesRead = 0;
        try {
            while ((bytesRead = is.read(buffer)) != -1) {
                byte[] out = cipher.update(buffer, 0, bytesRead);
                to.write(out);
            }

            byte[] out = cipher.doFinal();
            to.write(out);
        } catch (IOException e) {
            System.out.println("Error pri čitanju datoteke!");
        }
    }
}
