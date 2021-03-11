/*@Copyright :SiamSecure Consulting Co., Ltd.
2521/38 BizTown Soi 3,
Ladprao, Wangthonglang,
Bangkok 10310 THAILAND
Tel : (66) 2 539 5703
Fax : (66) 2 539 5704
 */

 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptAlogrithm {

    private static final String AES_ALGORITHM = "AES";
    private static final int ITERATIONS = 2;
//    private static final byte[] keyValue_AES
//            = new byte[]{'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
    private static final byte[] keyValue_AES
            = new byte[]{'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y', 'N', 'o', ':', '0', '0', '0', '1'};

    public static String encrypt_AES(String value, String salt) throws Exception {
        Key key = generateKey_AES();
        Cipher c = Cipher.getInstance(AES_ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        String valueToEnc = null;
        String eValue = value;
        for (int i = 0; i < ITERATIONS; i++) {
            valueToEnc = salt + eValue;
            byte[] encValue = c.doFinal(valueToEnc.getBytes());
            // eValue = new BASE64Encoder().encode(encValue);
            eValue = new Base64Encoder().encode(encValue);
        }
        return eValue;
    }

    public static String decrypt_AES(String value, String salt) throws Exception {
        Key key = generateKey_AES();
        Cipher c = Cipher.getInstance(AES_ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        String dValue = null;
        String valueToDecrypt = value;
        for (int i = 0; i < ITERATIONS; i++) {
            // byte[] decordedValue = new BASE64Decoder().decodeBuffer(valueToDecrypt);
            byte[] decordedValue = new Base64Decoder().decodeBuffer(valueToDecrypt);
            byte[] decValue = c.doFinal(decordedValue);
            dValue = new String(decValue).substring(salt.length());
            valueToDecrypt = dValue;
        }
        return dValue;
    }

    private static Key generateKey_AES() throws Exception {
        Key key = new SecretKeySpec(keyValue_AES, AES_ALGORITHM);
        // SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(AES_ALGORITHM);
        // key = keyFactory.generateSecret(new DESKeySpec(keyValue_AES));
        return key;
    }

    public static String encrypt_SHA1(String text) throws Exception {
        String sha1 = new CryptUtil().asHex(SHA1.SHA1(text));
        return sha1;
    }

    public static String encrypt_MD5(String text) throws Exception {
        String md5 = new CryptUtil().asHex(MD5.MD5(text));
        return md5;
    }

    public static void main(String[] args) throws Exception {
        String password = "my name";
        String salt = "aasa";
        String passwordEnc = CryptAlogrithm.encrypt_AES(password, salt);
        String passwordDec = CryptAlogrithm.decrypt_AES(passwordEnc, salt);
        System.out.println("Salt Text : " + salt);
        System.out.println("Plain Text : " + password);
        System.out.println("Encrypted : " + passwordEnc);
        System.out.println("Decrypted : " + passwordDec);
    }
}
