/*@Copyright :SiamSecure Consulting Co., Ltd.
2521/38 BizTown Soi 3,
Ladprao, Wangthonglang,
Bangkok 10310 THAILAND
Tel : (66) 2 539 5703
Fax : (66) 2 539 5704
 */
package com.project.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class SHA1 {

    public static byte[] SHA1(String paramString)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
        localMessageDigest.reset();
        localMessageDigest.update(paramString.getBytes("iso-8859-1"), 0, paramString.length());
        byte[] arrayOfByte = localMessageDigest.digest();
        return arrayOfByte;
    }

    public static String getSH1EncodedString(String str) {
        try {
            ShaPasswordEncoder encoder = new ShaPasswordEncoder(1);
            String hashOutput = encoder.encodePassword(str, null);
            System.out.println(hashOutput);
            CryptUtil cryptUtil = new CryptUtil();
            str = (cryptUtil.asHex(SHA1.SHA1(str)));
            System.out.println(str);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }
}
