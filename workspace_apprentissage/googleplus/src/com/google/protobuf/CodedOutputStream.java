package com.google.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public final class CodedOutputStream
{
  private final byte[] buffer;
  private final int limit;
  private final OutputStream output = null;
  private int position;

  private CodedOutputStream(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.buffer = paramArrayOfByte;
    this.position = paramInt1;
    this.limit = (paramInt1 + paramInt2);
  }

  public static int computeBoolSize(int paramInt, boolean paramBoolean)
  {
    return 1 + computeTagSize(paramInt);
  }

  public static int computeBytesSize(int paramInt, ByteString paramByteString)
  {
    return computeTagSize(paramInt) + computeBytesSizeNoTag(paramByteString);
  }

  public static int computeBytesSizeNoTag(ByteString paramByteString)
  {
    return computeRawVarint32Size(paramByteString.size()) + paramByteString.size();
  }

  public static int computeDoubleSize(int paramInt, double paramDouble)
  {
    return 8 + computeTagSize(paramInt);
  }

  public static int computeEnumSize(int paramInt1, int paramInt2)
  {
    return computeTagSize(paramInt1) + computeInt32SizeNoTag(paramInt2);
  }

  public static int computeFixed32Size(int paramInt1, int paramInt2)
  {
    return 4 + computeTagSize(paramInt1);
  }

  public static int computeFloatSize(int paramInt, float paramFloat)
  {
    return 4 + computeTagSize(2);
  }

  public static int computeGroupSize(int paramInt, MessageLite paramMessageLite)
  {
    return 2 * computeTagSize(paramInt) + paramMessageLite.getSerializedSize();
  }

  public static int computeInt32Size(int paramInt1, int paramInt2)
  {
    return computeTagSize(paramInt1) + computeInt32SizeNoTag(paramInt2);
  }

  public static int computeInt32SizeNoTag(int paramInt)
  {
    if (paramInt >= 0);
    for (int i = computeRawVarint32Size(paramInt); ; i = 10)
      return i;
  }

  public static int computeInt64Size(int paramInt, long paramLong)
  {
    return computeTagSize(paramInt) + computeRawVarint64Size(paramLong);
  }

  public static int computeMessageSize(int paramInt, MessageLite paramMessageLite)
  {
    return computeTagSize(paramInt) + computeMessageSizeNoTag(paramMessageLite);
  }

  public static int computeMessageSizeNoTag(MessageLite paramMessageLite)
  {
    int i = paramMessageLite.getSerializedSize();
    return i + computeRawVarint32Size(i);
  }

  public static int computeRawVarint32Size(int paramInt)
  {
    int i;
    if ((paramInt & 0xFFFFFF80) == 0)
      i = 1;
    while (true)
    {
      return i;
      if ((paramInt & 0xFFFFC000) == 0)
        i = 2;
      else if ((0xFFE00000 & paramInt) == 0)
        i = 3;
      else if ((0xF0000000 & paramInt) == 0)
        i = 4;
      else
        i = 5;
    }
  }

  public static int computeRawVarint64Size(long paramLong)
  {
    int i;
    if ((0xFFFFFF80 & paramLong) == 0L)
      i = 1;
    while (true)
    {
      return i;
      if ((0xFFFFC000 & paramLong) == 0L)
        i = 2;
      else if ((0xFFE00000 & paramLong) == 0L)
        i = 3;
      else if ((0xF0000000 & paramLong) == 0L)
        i = 4;
      else if ((0x0 & paramLong) == 0L)
        i = 5;
      else if ((0x0 & paramLong) == 0L)
        i = 6;
      else if ((0x0 & paramLong) == 0L)
        i = 7;
      else if ((0x0 & paramLong) == 0L)
        i = 8;
      else if ((0x0 & paramLong) == 0L)
        i = 9;
      else
        i = 10;
    }
  }

  public static int computeStringSizeNoTag(String paramString)
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes("UTF-8");
      int i = computeRawVarint32Size(arrayOfByte.length);
      int j = arrayOfByte.length;
      return i + j;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("UTF-8 not supported.", localUnsupportedEncodingException);
    }
  }

  public static int computeTagSize(int paramInt)
  {
    return computeRawVarint32Size(WireFormat.makeTag(paramInt, 0));
  }

  public static int computeUInt64Size(int paramInt, long paramLong)
  {
    return computeTagSize(2) + computeRawVarint64Size(paramLong);
  }

  public static int encodeZigZag32(int paramInt)
  {
    return paramInt << 1 ^ paramInt >> 31;
  }

  public static long encodeZigZag64(long paramLong)
  {
    return paramLong << 1 ^ paramLong >> 63;
  }

  public static CodedOutputStream newInstance(byte[] paramArrayOfByte)
  {
    return new CodedOutputStream(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  private void writeRawByte(int paramInt)
    throws IOException
  {
    int i = (byte)paramInt;
    if (this.position == this.limit)
      throw new OutOfSpaceException();
    byte[] arrayOfByte = this.buffer;
    int j = this.position;
    this.position = (j + 1);
    arrayOfByte[j] = i;
  }

  public final void checkNoSpaceLeft()
  {
    if (this.limit - this.position != 0)
      throw new IllegalStateException("Did not write as much data as expected.");
  }

  public final void writeBool(int paramInt, boolean paramBoolean)
    throws IOException
  {
    writeTag(paramInt, 0);
    writeBoolNoTag(paramBoolean);
  }

  public final void writeBoolNoTag(boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      writeRawByte(i);
      return;
    }
  }

  public final void writeBytes(int paramInt, ByteString paramByteString)
    throws IOException
  {
    writeTag(paramInt, 2);
    writeBytesNoTag(paramByteString);
  }

  public final void writeBytesNoTag(ByteString paramByteString)
    throws IOException
  {
    writeRawVarint32(paramByteString.size());
    int i = paramByteString.size();
    if (this.limit - this.position >= i)
    {
      paramByteString.copyTo(this.buffer, 0, this.position, i);
      this.position = (i + this.position);
      return;
    }
    int j = this.limit - this.position;
    paramByteString.copyTo(this.buffer, 0, this.position, j);
    (j + 0);
    (i - j);
    this.position = this.limit;
    throw new OutOfSpaceException();
  }

  public final void writeDouble(int paramInt, double paramDouble)
    throws IOException
  {
    writeTag(paramInt, 1);
    writeDoubleNoTag(paramDouble);
  }

  public final void writeDoubleNoTag(double paramDouble)
    throws IOException
  {
    writeRawLittleEndian64(Double.doubleToRawLongBits(paramDouble));
  }

  public final void writeEnum(int paramInt1, int paramInt2)
    throws IOException
  {
    writeTag(paramInt1, 0);
    writeInt32NoTag(paramInt2);
  }

  public final void writeEnumNoTag(int paramInt)
    throws IOException
  {
    writeInt32NoTag(paramInt);
  }

  public final void writeFixed32(int paramInt1, int paramInt2)
    throws IOException
  {
    writeTag(paramInt1, 5);
    writeRawLittleEndian32(paramInt2);
  }

  public final void writeFixed32NoTag(int paramInt)
    throws IOException
  {
    writeRawLittleEndian32(paramInt);
  }

  public final void writeFloat(int paramInt, float paramFloat)
    throws IOException
  {
    writeTag(2, 5);
    writeFloatNoTag(paramFloat);
  }

  public final void writeFloatNoTag(float paramFloat)
    throws IOException
  {
    writeRawLittleEndian32(Float.floatToRawIntBits(paramFloat));
  }

  public final void writeGroup(int paramInt, MessageLite paramMessageLite)
    throws IOException
  {
    writeTag(paramInt, 3);
    paramMessageLite.writeTo(this);
    writeTag(paramInt, 4);
  }

  public final void writeInt32(int paramInt1, int paramInt2)
    throws IOException
  {
    writeTag(paramInt1, 0);
    writeInt32NoTag(paramInt2);
  }

  public final void writeInt32NoTag(int paramInt)
    throws IOException
  {
    if (paramInt >= 0)
      writeRawVarint32(paramInt);
    while (true)
    {
      return;
      writeRawVarint64(paramInt);
    }
  }

  public final void writeInt64(int paramInt, long paramLong)
    throws IOException
  {
    writeTag(paramInt, 0);
    writeRawVarint64(paramLong);
  }

  public final void writeInt64NoTag(long paramLong)
    throws IOException
  {
    writeRawVarint64(paramLong);
  }

  public final void writeMessage(int paramInt, MessageLite paramMessageLite)
    throws IOException
  {
    writeTag(paramInt, 2);
    writeMessageNoTag(paramMessageLite);
  }

  public final void writeMessageNoTag(MessageLite paramMessageLite)
    throws IOException
  {
    writeRawVarint32(paramMessageLite.getSerializedSize());
    paramMessageLite.writeTo(this);
  }

  public final void writeRawLittleEndian32(int paramInt)
    throws IOException
  {
    writeRawByte(paramInt & 0xFF);
    writeRawByte(0xFF & paramInt >> 8);
    writeRawByte(0xFF & paramInt >> 16);
    writeRawByte(0xFF & paramInt >> 24);
  }

  public final void writeRawLittleEndian64(long paramLong)
    throws IOException
  {
    writeRawByte(0xFF & (int)paramLong);
    writeRawByte(0xFF & (int)(paramLong >> 8));
    writeRawByte(0xFF & (int)(paramLong >> 16));
    writeRawByte(0xFF & (int)(paramLong >> 24));
    writeRawByte(0xFF & (int)(paramLong >> 32));
    writeRawByte(0xFF & (int)(paramLong >> 40));
    writeRawByte(0xFF & (int)(paramLong >> 48));
    writeRawByte(0xFF & (int)(paramLong >> 56));
  }

  public final void writeRawVarint32(int paramInt)
    throws IOException
  {
    while (true)
    {
      if ((paramInt & 0xFFFFFF80) == 0)
      {
        writeRawByte(paramInt);
        return;
      }
      writeRawByte(0x80 | paramInt & 0x7F);
      paramInt >>>= 7;
    }
  }

  public final void writeRawVarint64(long paramLong)
    throws IOException
  {
    while (true)
    {
      if ((0xFFFFFF80 & paramLong) == 0L)
      {
        writeRawByte((int)paramLong);
        return;
      }
      writeRawByte(0x80 | 0x7F & (int)paramLong);
      paramLong >>>= 7;
    }
  }

  public final void writeStringNoTag(String paramString)
    throws IOException
  {
    byte[] arrayOfByte = paramString.getBytes("UTF-8");
    writeRawVarint32(arrayOfByte.length);
    int i = arrayOfByte.length;
    if (this.limit - this.position >= i)
    {
      System.arraycopy(arrayOfByte, 0, this.buffer, this.position, i);
      this.position = (i + this.position);
      return;
    }
    int j = this.limit - this.position;
    System.arraycopy(arrayOfByte, 0, this.buffer, this.position, j);
    (j + 0);
    (i - j);
    this.position = this.limit;
    throw new OutOfSpaceException();
  }

  public final void writeTag(int paramInt1, int paramInt2)
    throws IOException
  {
    writeRawVarint32(WireFormat.makeTag(paramInt1, paramInt2));
  }

  public final void writeUInt32NoTag(int paramInt)
    throws IOException
  {
    writeRawVarint32(paramInt);
  }

  public final void writeUInt64(int paramInt, long paramLong)
    throws IOException
  {
    writeTag(2, 0);
    writeRawVarint64(paramLong);
  }

  public final void writeUInt64NoTag(long paramLong)
    throws IOException
  {
    writeRawVarint64(paramLong);
  }

  public static final class OutOfSpaceException extends IOException
  {
    private static final long serialVersionUID = -6947486886997889499L;

    OutOfSpaceException()
    {
      super();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.CodedOutputStream
 * JD-Core Version:    0.6.2
 */