package com.android.gallery3d.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class Fingerprint
{
  private static final MessageDigest DIGESTER;
  private static final int FINGERPRINT_BYTE_LENGTH;
  private static final int STREAM_ID_CS_01_LENGTH;
  private final byte[] mMd5Digest;

  static
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("md5");
      DIGESTER = localMessageDigest;
      FINGERPRINT_BYTE_LENGTH = localMessageDigest.getDigestLength();
      STREAM_ID_CS_01_LENGTH = 6 + 2 * FINGERPRINT_BYTE_LENGTH;
      return;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new IllegalStateException(localNoSuchAlgorithmException);
    }
  }

  public Fingerprint(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length != FINGERPRINT_BYTE_LENGTH))
      throw new IllegalArgumentException();
    this.mMd5Digest = paramArrayOfByte;
  }

  private static void appendHexFingerprint(StringBuilder paramStringBuilder, byte[] paramArrayOfByte)
  {
    for (int i = 0; i < FINGERPRINT_BYTE_LENGTH; i++)
    {
      int j = paramArrayOfByte[i];
      paramStringBuilder.append(Integer.toHexString(0xF & j >> 4));
      paramStringBuilder.append(Integer.toHexString(j & 0xF));
    }
  }

  public static Fingerprint extractFingerprint(List<String> paramList)
  {
    Iterator localIterator = paramList.iterator();
    byte[] arrayOfByte;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.startsWith("cs_01_"))
      {
        if ((str == null) || (!str.startsWith("cs_01_")) || (str.length() != STREAM_ID_CS_01_LENGTH))
          throw new IllegalArgumentException("bad streamId: " + str);
        arrayOfByte = new byte[FINGERPRINT_BYTE_LENGTH];
        int i = 0;
        int j = 6;
        while (j < STREAM_ID_CS_01_LENGTH)
        {
          int k = toDigit(str, j) << 4 | toDigit(str, j + 1);
          int m = i + 1;
          arrayOfByte[i] = ((byte)(k & 0xFF));
          j += 2;
          i = m;
        }
      }
    }
    for (Fingerprint localFingerprint = new Fingerprint(arrayOfByte); ; localFingerprint = null)
      return localFingerprint;
  }

  // ERROR //
  public static Fingerprint fromInputStream(java.io.InputStream paramInputStream, long[] paramArrayOfLong)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: lconst_0
    //   3: lstore_3
    //   4: new 112	java/security/DigestInputStream
    //   7: dup
    //   8: aload_0
    //   9: getstatic 25	com/android/gallery3d/common/Fingerprint:DIGESTER	Ljava/security/MessageDigest;
    //   12: invokespecial 115	java/security/DigestInputStream:<init>	(Ljava/io/InputStream;Ljava/security/MessageDigest;)V
    //   15: astore 5
    //   17: sipush 8192
    //   20: newarray byte
    //   22: astore 7
    //   24: aload 5
    //   26: aload 7
    //   28: invokevirtual 119	java/security/DigestInputStream:read	([B)I
    //   31: istore 8
    //   33: iload 8
    //   35: iflt +12 -> 47
    //   38: lload_3
    //   39: iload 8
    //   41: i2l
    //   42: ladd
    //   43: lstore_3
    //   44: goto -20 -> 24
    //   47: aload 5
    //   49: invokevirtual 122	java/security/DigestInputStream:close	()V
    //   52: aload_1
    //   53: ifnull +12 -> 65
    //   56: aload_1
    //   57: arraylength
    //   58: ifle +7 -> 65
    //   61: aload_1
    //   62: iconst_0
    //   63: lload_3
    //   64: lastore
    //   65: new 2	com/android/gallery3d/common/Fingerprint
    //   68: dup
    //   69: aload 5
    //   71: invokevirtual 126	java/security/DigestInputStream:getMessageDigest	()Ljava/security/MessageDigest;
    //   74: invokevirtual 130	java/security/MessageDigest:digest	()[B
    //   77: invokespecial 106	com/android/gallery3d/common/Fingerprint:<init>	([B)V
    //   80: areturn
    //   81: astore 6
    //   83: aload_2
    //   84: ifnull +7 -> 91
    //   87: aload_2
    //   88: invokevirtual 122	java/security/DigestInputStream:close	()V
    //   91: aload 6
    //   93: athrow
    //   94: astore 6
    //   96: aload 5
    //   98: astore_2
    //   99: goto -16 -> 83
    //
    // Exception table:
    //   from	to	target	type
    //   4	17	81	finally
    //   17	33	94	finally
  }

  private static int toDigit(String paramString, int paramInt)
  {
    int i = Character.digit(paramString.charAt(paramInt), 16);
    if (i < 0)
      throw new IllegalArgumentException("illegal hex digit in " + paramString);
    return i;
  }

  public final boolean equals(Object paramObject)
  {
    boolean bool;
    if (this == paramObject)
      bool = true;
    while (true)
    {
      return bool;
      if (!(paramObject instanceof Fingerprint))
      {
        bool = false;
      }
      else
      {
        Fingerprint localFingerprint = (Fingerprint)paramObject;
        bool = Arrays.equals(this.mMd5Digest, localFingerprint.mMd5Digest);
      }
    }
  }

  public final boolean equals(byte[] paramArrayOfByte)
  {
    return Arrays.equals(this.mMd5Digest, paramArrayOfByte);
  }

  public final byte[] getBytes()
  {
    return this.mMd5Digest;
  }

  public final int hashCode()
  {
    return Arrays.hashCode(this.mMd5Digest);
  }

  public final String toStreamId()
  {
    StringBuilder localStringBuilder = new StringBuilder("cs_01_");
    appendHexFingerprint(localStringBuilder, this.mMd5Digest);
    return localStringBuilder.toString();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.gallery3d.common.Fingerprint
 * JD-Core Version:    0.6.2
 */