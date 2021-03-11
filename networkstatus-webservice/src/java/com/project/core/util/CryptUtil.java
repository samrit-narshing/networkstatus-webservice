/*@Copyright :SiamSecure Consulting Co., Ltd.
2521/38 BizTown Soi 3,
Ladprao, Wangthonglang,
Bangkok 10310 THAILAND
Tel : (66) 2 539 5703
Fax : (66) 2 539 5704
*/

package com.project.core.util;

import javax.crypto.SecretKey;

public class CryptUtil
{
  private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

  public String formatKey(SecretKey paramSecretKey)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = paramSecretKey.getAlgorithm();
    String str2 = paramSecretKey.getFormat();
    byte[] arrayOfByte = paramSecretKey.getEncoded();
    localStringBuffer.append("Key[algorithm=" + str1 + ", format=" + str2 + ", bytes=" + arrayOfByte.length + "]\n");
    if (str2.equalsIgnoreCase("RAW"))
    {
      localStringBuffer.append("Key Material (in hex):: ");
      localStringBuffer.append(asHex(paramSecretKey.getEncoded()));
    }
    return localStringBuffer.toString();
  }

  public String asHex(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar = new char[2 * paramArrayOfByte.length];
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      arrayOfChar[(2 * i)] = HEX_CHARS[((paramArrayOfByte[i] & 0xF0) >>> 4)];
      arrayOfChar[(2 * i + 1)] = HEX_CHARS[(paramArrayOfByte[i] & 0xF)];
    }
    return new String(arrayOfChar);
  }

  public String asHex2(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      if ((paramArrayOfByte[i] & 0xFF) < 16)
        localStringBuffer.append("0");
      localStringBuffer.append(Long.toString(paramArrayOfByte[i] & 0xFF, 16));
    }
    return localStringBuffer.toString();
  }

  public byte[] addParity(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[8];
    int i = 1;
    int j = 0;
    for (int k = 0; k < 56; k++)
    {
      int m = (paramArrayOfByte[(6 - k / 8)] & 1 << k % 8) > 0 ? 1 : 0;
      if (m != 0)
      {
        int tmp61_60 = (7 - i / 8);
        byte[] tmp61_53 = arrayOfByte;
        tmp61_53[tmp61_60] = (byte)(tmp61_53[tmp61_60] | 1 << i % 8 & 0xFF);
        j++;
      }
      if ((k + 1) % 7 == 0)
      {
        if (j % 2 == 0)
        {
          int tmp104_103 = (7 - i / 8);
          byte[] tmp104_96 = arrayOfByte;
          tmp104_96[tmp104_103] = (byte)(tmp104_96[tmp104_103] | 0x1);
        }
        i++;
        j = 0;
      }
      i++;
    }
    return arrayOfByte;
  }

  public String BASE64_encode(byte[] paramArrayOfByte)
  {
    return new Base64Encoder().encode(paramArrayOfByte);
  }

  public byte[] BASE64_decode(String paramString)
  {
    try
    {
      return new Base64Decoder().decodeBuffer(paramString);
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}
