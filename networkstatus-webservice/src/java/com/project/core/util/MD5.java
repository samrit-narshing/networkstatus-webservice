/*@Copyright :SiamSecure Consulting Co., Ltd.
2521/38 BizTown Soi 3,
Ladprao, Wangthonglang,
Bangkok 10310 THAILAND
Tel : (66) 2 539 5703
Fax : (66) 2 539 5704
*/
 
package com.project.core.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
  public static byte[] MD5(String paramString)
    throws NoSuchAlgorithmException, UnsupportedEncodingException
  {
    MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
    localMessageDigest.reset();
    localMessageDigest.update(paramString.getBytes("iso-8859-1"), 0, paramString.length());
    byte[] arrayOfByte = localMessageDigest.digest();
    return arrayOfByte;
  }
  
   public static byte[] createChecksumFile(String filename) throws Exception {
       InputStream fis =  new FileInputStream(filename);

       byte[] buffer = new byte[5024];
       MessageDigest complete = MessageDigest.getInstance("MD5");
       int numRead;

       do {
           numRead = fis.read(buffer);
           if (numRead > 0) {
               complete.update(buffer, 0, numRead);
           }
       } while (numRead != -1);

       fis.close();
       return complete.digest();
   }
   
      // see this How-to for a faster way to convert
   // a byte array to a HEX string
   public static String getMD5ChecksumFile(String filename) throws Exception {
       byte[] b = createChecksumFile(filename);
       String result = "";

       for (int i=0; i < b.length; i++) {
           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
       }
       return result;
   }
  
}
