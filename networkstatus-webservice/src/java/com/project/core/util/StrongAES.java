/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

/**
 *
 * @author Samrit
 */
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class StrongAES {

    private static final String key = "Bar12345Bar12345"; // 128 bit key

    public static String getEncryptText(String text) {
        String encryptText = "";

        try {

            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : encrypted) {
                sb.append((char) b);
            }

            // the encrypted String
            String enc = sb.toString();
            System.out.println("encrypted:" + enc);
            encryptText = enc;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptText;
    }

    public static String getDecryptText(String text) {
        String decryptText = "";

        try {

            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            // the encrypted String
            String enc = text;

            // now convert the string to byte array
            // for decryption
            byte[] bb = new byte[enc.length()];
            for (int i = 0; i < enc.length(); i++) {
                bb[i] = (byte) enc.charAt(i);
            }

            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(bb));
            System.err.println("decrypted:" + decrypted);
            decryptText = decrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptText;
    }

    public void run() {
        try {
            String text = "Hello World";
            String key = "Bar12345Bar12345"; // 128 bit key
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : encrypted) {
                sb.append((char) b);
            }

            // the encrypted String
            String enc = sb.toString();
            System.out.println("encrypted:" + enc);

            // now convert the string to byte array
            // for decryption
            byte[] bb = new byte[enc.length()];
            for (int i = 0; i < enc.length(); i++) {
                bb[i] = (byte) enc.charAt(i);
            }

            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(bb));
            System.err.println("decrypted:" + decrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StrongAES app = new StrongAES();
        app.run();
    }
}
