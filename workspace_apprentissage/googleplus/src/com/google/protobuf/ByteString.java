package com.google.protobuf;

import java.io.UnsupportedEncodingException;

public final class ByteString
{
  public static final ByteString EMPTY = new ByteString(new byte[0]);
  private final byte[] bytes;
  private volatile int hash = 0;

  private ByteString(byte[] paramArrayOfByte)
  {
    this.bytes = paramArrayOfByte;
  }

  public static ByteString copyFrom(byte[] paramArrayOfByte)
  {
    return copyFrom(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static ByteString copyFrom(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    return new ByteString(arrayOfByte);
  }

  public static ByteString copyFromUtf8(String paramString)
  {
    try
    {
      ByteString localByteString = new ByteString(paramString.getBytes("UTF-8"));
      return localByteString;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("UTF-8 not supported?", localUnsupportedEncodingException);
    }
  }

  static CodedBuilder newCodedBuilder(int paramInt)
  {
    return new CodedBuilder(paramInt, (byte)0);
  }

  public final byte byteAt(int paramInt)
  {
    return this.bytes[paramInt];
  }

  public final void copyTo(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    System.arraycopy(this.bytes, paramInt1, paramArrayOfByte, paramInt2, paramInt3);
  }

  public final boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this);
    label92: 
    while (true)
    {
      return bool;
      if (!(paramObject instanceof ByteString))
      {
        bool = false;
      }
      else
      {
        ByteString localByteString = (ByteString)paramObject;
        int i = this.bytes.length;
        if (i != localByteString.bytes.length)
        {
          bool = false;
        }
        else
        {
          byte[] arrayOfByte1 = this.bytes;
          byte[] arrayOfByte2 = localByteString.bytes;
          for (int j = 0; ; j++)
          {
            if (j >= i)
              break label92;
            if (arrayOfByte1[j] != arrayOfByte2[j])
            {
              bool = false;
              break;
            }
          }
        }
      }
    }
  }

  public final int hashCode()
  {
    int i = this.hash;
    if (i == 0)
    {
      byte[] arrayOfByte = this.bytes;
      int j = this.bytes.length;
      i = j;
      for (int k = 0; k < j; k++)
        i = i * 31 + arrayOfByte[k];
      if (i == 0)
        i = 1;
      this.hash = i;
    }
    return i;
  }

  public final int size()
  {
    return this.bytes.length;
  }

  public final String toStringUtf8()
  {
    try
    {
      String str = new String(this.bytes, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("UTF-8 not supported?", localUnsupportedEncodingException);
    }
  }

  static final class CodedBuilder
  {
    private final byte[] buffer;
    private final CodedOutputStream output;

    private CodedBuilder(int paramInt)
    {
      this.buffer = new byte[paramInt];
      this.output = CodedOutputStream.newInstance(this.buffer);
    }

    public final ByteString build()
    {
      this.output.checkNoSpaceLeft();
      return new ByteString(this.buffer, (byte)0);
    }

    public final CodedOutputStream getCodedOutput()
    {
      return this.output;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.ByteString
 * JD-Core Version:    0.6.2
 */