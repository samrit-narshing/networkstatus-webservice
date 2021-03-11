/*@Copyright :SiamSecure Consulting Co., Ltd.
2521/38 BizTown Soi 3,
Ladprao, Wangthonglang,
Bangkok 10310 THAILAND
Tel : (66) 2 539 5703
Fax : (66) 2 539 5704
*/

package com.project.core.util;

import java.text.ParseException;

public class Base64Decoder
{
  static final String REV = "@(#)tribble/util/Base64Decoder.java $Revision: 1.4 $ $Date: 2005/04/16 16:05:55 $\n";
  public static final int SERIES = 104;

  public static byte[] decodeString(String paramString)
    throws ParseException
  {
    int i = paramString.length();
    int j = 0;
    if (paramString.charAt(i - 1) == '=')
      j++;
    if ((i >= 2) && (paramString.charAt(i - 2) == '='))
      j++;
    int k = i * 3 / 4;
    byte[] arrayOfByte = new byte[k - j];
    decodeString(paramString, arrayOfByte, 0);
    return arrayOfByte;
  }

  public static int decodeString(String paramString, byte[] paramArrayOfByte, int paramInt)
    throws ParseException
  {
    int i = paramString.length();
    i += paramInt - 1;
    while ((i >= 0) && (paramString.charAt(i) == '='))
      i--;
    i++;
    int j = paramInt;
    int k = paramInt;
    while (j < i)
    {
      int i1 = 65;
      int i2 = 65;
      int m = paramString.charAt(j + 0);
      int n = paramString.charAt(j + 1);
      if (j + 2 < i)
      {
        i1 = paramString.charAt(j + 2);
        if (j + 3 < i)
          i2 = paramString.charAt(j + 3);
      }
      int i3 = fromBase64(m & 0xFF) << 18 | fromBase64(n & 0xFF) << 12 | fromBase64(i1 & 0xFF) << 6 | fromBase64(i2 & 0xFF);
      paramArrayOfByte[(k++)] = (byte)(i3 >> 16 & 0xFF);
      if (j + 2 < i)
        paramArrayOfByte[(k++)] = (byte)(i3 >> 8 & 0xFF);
      if (j + 3 < i)
        paramArrayOfByte[(k++)] = (byte)(i3 & 0xFF);
      j += 4;
    }
    return k - paramInt;
  }

  public static byte[] decodeBytes(byte[] paramArrayOfByte)
    throws ParseException
  {
    return decodeBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static byte[] decodeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws ParseException
  {
    paramInt2 = paramArrayOfByte.length;
    int j = 0;
    if (paramArrayOfByte[(paramInt2 - 1)] == 61)
      j++;
    if ((paramInt2 >= 2) && (paramArrayOfByte[(paramInt2 - 2)] == 61))
      j++;
    int i = paramInt2 / 4 * 3 - j;
    byte[] arrayOfByte = new byte[i];
    decodeBytes(paramArrayOfByte, paramInt1, paramInt2 - j, arrayOfByte, 0);
    return arrayOfByte;
  }

  public static int decodeBytes(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws ParseException
  {
    paramInt2 += paramInt1 - 1;
    while ((paramInt2 >= 0) && (paramArrayOfByte1[paramInt2] == 61))
      paramInt2--;
    paramInt2++;
    int i = paramInt1;
    int j = paramInt3;
    while (i < paramInt2)
    {
      int n = 65;
      int i1 = 65;
      int k = paramArrayOfByte1[(i + 0)];
      int m = paramArrayOfByte1[(i + 1)];
      if (i + 2 < paramInt2)
      {
        n = paramArrayOfByte1[(i + 2)];
        if (i + 3 < paramInt2)
          i1 = paramArrayOfByte1[(i + 3)];
      }
      int i2 = fromBase64(k & 0xFF) << 18 | fromBase64(m & 0xFF) << 12 | fromBase64(n & 0xFF) << 6 | fromBase64(i1 & 0xFF);
      paramArrayOfByte2[(j++)] = (byte)(i2 >> 16 & 0xFF);
      if (i + 2 < paramInt2)
        paramArrayOfByte2[(j++)] = (byte)(i2 >> 8 & 0xFF);
      if (i + 3 < paramInt2)
        paramArrayOfByte2[(j++)] = (byte)(i2 & 0xFF);
      i += 4;
    }
    return j - paramInt3;
  }

  public static int fromBase64(int paramInt)
  {
    if (paramInt >= 97)
    {
      if (paramInt <= 122)
        return paramInt - 97 + 26;
      return -1;
    }
    if (paramInt >= 65)
    {
      if (paramInt <= 90)
        return paramInt - 65 + 0;
      return -1;
    }
    if (paramInt >= 48)
    {
      if (paramInt <= 57)
        return paramInt - 48 + 52;
      if (paramInt == 61)
        return 64;
      return -1;
    }
    if (paramInt == 43)
      return 62;
    if (paramInt == 47)
      return 63;
    return -1;
  }

  public byte[] decodeBuffer(String paramString)
  {
    try
    {
      return decodeString(paramString);
    }
    catch (ParseException localParseException)
    {
        throw new IllegalArgumentException(localParseException.getMessage());
    }
    
  }
}