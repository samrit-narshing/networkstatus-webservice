/*@Copyright :SiamSecure Consulting Co., Ltd.
2521/38 BizTown Soi 3,
Ladprao, Wangthonglang,
Bangkok 10310 THAILAND
Tel : (66) 2 539 5703
Fax : (66) 2 539 5704
*/

package com.project.core.util;

public class Base64Encoder
{
  static final String REV = "@(#)tribble/util/Base64Encoder.java $Revision: 1.2 $ $Date: 2005/04/10 20:05:52 $\n";
  public static final int SERIES = 102;

  public static String encodeAsString(byte[] paramArrayOfByte)
  {
    return encodeAsString(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static String encodeAsString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = encodeAsBytes(paramArrayOfByte, paramInt1, paramInt2);
    return new String(arrayOfByte);
  }

  public static byte[] encodeAsBytes(byte[] paramArrayOfByte)
  {
    return encodeAsBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static byte[] encodeAsBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = (paramArrayOfByte.length + 3 - 1) / 3 * 4;
    byte[] arrayOfByte = new byte[i];
    encodeAsBytes(paramArrayOfByte, paramInt1, paramInt2, arrayOfByte, 0);
    return arrayOfByte;
  }

  public static int encodeAsBytes(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
  {
    paramInt2 += paramInt1;
    int j = paramInt1;
    int k = paramInt3;
    int i;
    while (j < paramInt2 - 2)
    {
      i = (paramArrayOfByte1[(j + 0)] & 0xFF) << 16 | (paramArrayOfByte1[(j + 1)] & 0xFF) << 8 | paramArrayOfByte1[(j + 2)] & 0xFF;
      j += 3;
      paramArrayOfByte2[(k++)] = toBase64(i >> 18 & 0x3F);
      paramArrayOfByte2[(k++)] = toBase64(i >> 12 & 0x3F);
      paramArrayOfByte2[(k++)] = toBase64(i >> 6 & 0x3F);
      paramArrayOfByte2[(k++)] = toBase64(i & 0x3F);
    }
    if (j < paramInt2 - 1)
    {
      i = (paramArrayOfByte1[(j + 0)] & 0xFF) << 16 | (paramArrayOfByte1[(j + 1)] & 0xFF) << 8;
      paramArrayOfByte2[(k++)] = toBase64(i >> 18 & 0x3F);
      paramArrayOfByte2[(k++)] = toBase64(i >> 12 & 0x3F);
      paramArrayOfByte2[(k++)] = toBase64(i >> 6 & 0x3F);
      paramArrayOfByte2[(k++)] = 61;
    }
    else if (j < paramInt2)
    {
      i = (paramArrayOfByte1[(j + 0)] & 0xFF) << 16;
      paramArrayOfByte2[(k++)] = toBase64(i >> 18 & 0x3F);
      paramArrayOfByte2[(k++)] = toBase64(i >> 12 & 0x3F);
      paramArrayOfByte2[(k++)] = 61;
      paramArrayOfByte2[(k++)] = 61;
    }
    return k - paramInt3;
  }

  public static byte toBase64(int paramInt)
  {
    if (paramInt >= 52)
    {
      if (paramInt < 62)
        return (byte)(paramInt - 52 + 48);
      if (paramInt == 62)
        return 43;
      if (paramInt == 63)
        return 47;
      if (paramInt == 64)
        return 61;
      return 63;
    }
    if (paramInt >= 26)
      return (byte)(paramInt - 26 + 97);
    if (paramInt >= 0)
      return (byte)(paramInt - 0 + 65);
    return 63;
  }

  public String encode(byte[] paramArrayOfByte)
  {
    return encodeAsString(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
}