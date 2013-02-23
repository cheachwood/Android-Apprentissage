package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CodedInputStream
{
  private final byte[] buffer;
  private int bufferPos;
  private int bufferSize;
  private int bufferSizeAfterLimit;
  private int currentLimit = 2147483647;
  private final InputStream input;
  private int lastTag;
  private int recursionDepth;
  private int recursionLimit = 64;
  private int sizeLimit = 67108864;
  private int totalBytesRetired;

  private CodedInputStream(InputStream paramInputStream)
  {
    this.buffer = new byte[4096];
    this.bufferSize = 0;
    this.bufferPos = 0;
    this.totalBytesRetired = 0;
    this.input = paramInputStream;
  }

  private CodedInputStream(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.buffer = paramArrayOfByte;
    this.bufferSize = (paramInt1 + paramInt2);
    this.bufferPos = paramInt1;
    this.totalBytesRetired = (-paramInt1);
    this.input = null;
  }

  public static CodedInputStream newInstance(InputStream paramInputStream)
  {
    return new CodedInputStream(paramInputStream);
  }

  public static CodedInputStream newInstance(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    CodedInputStream localCodedInputStream = new CodedInputStream(paramArrayOfByte, paramInt1, paramInt2);
    try
    {
      localCodedInputStream.pushLimit(paramInt2);
      return localCodedInputStream;
    }
    catch (InvalidProtocolBufferException localInvalidProtocolBufferException)
    {
      throw new IllegalArgumentException(localInvalidProtocolBufferException);
    }
  }

  private byte readRawByte()
    throws IOException
  {
    if (this.bufferPos == this.bufferSize)
      refillBuffer(true);
    byte[] arrayOfByte = this.buffer;
    int i = this.bufferPos;
    this.bufferPos = (i + 1);
    return arrayOfByte[i];
  }

  private byte[] readRawBytes(int paramInt)
    throws IOException
  {
    if (paramInt < 0)
      throw InvalidProtocolBufferException.negativeSize();
    if (paramInt + (this.totalBytesRetired + this.bufferPos) > this.currentLimit)
    {
      skipRawBytes(this.currentLimit - this.totalBytesRetired - this.bufferPos);
      throw InvalidProtocolBufferException.truncatedMessage();
    }
    byte[] arrayOfByte1;
    if (paramInt <= this.bufferSize - this.bufferPos)
    {
      arrayOfByte1 = new byte[paramInt];
      System.arraycopy(this.buffer, this.bufferPos, arrayOfByte1, 0, paramInt);
      this.bufferPos = (paramInt + this.bufferPos);
    }
    while (true)
    {
      return arrayOfByte1;
      if (paramInt < 4096)
      {
        arrayOfByte1 = new byte[paramInt];
        int i2 = this.bufferSize - this.bufferPos;
        System.arraycopy(this.buffer, this.bufferPos, arrayOfByte1, 0, i2);
        this.bufferPos = this.bufferSize;
        refillBuffer(true);
        while (paramInt - i2 > this.bufferSize)
        {
          System.arraycopy(this.buffer, 0, arrayOfByte1, i2, this.bufferSize);
          i2 += this.bufferSize;
          this.bufferPos = this.bufferSize;
          refillBuffer(true);
        }
        System.arraycopy(this.buffer, 0, arrayOfByte1, i2, paramInt - i2);
        this.bufferPos = (paramInt - i2);
      }
      else
      {
        int i = this.bufferPos;
        int j = this.bufferSize;
        this.totalBytesRetired += this.bufferSize;
        this.bufferPos = 0;
        this.bufferSize = 0;
        int k = paramInt - (j - i);
        ArrayList localArrayList = new ArrayList();
        while (k > 0)
        {
          byte[] arrayOfByte3 = new byte[Math.min(k, 4096)];
          int n = 0;
          while (n < arrayOfByte3.length)
          {
            if (this.input == null);
            for (int i1 = -1; i1 == -1; i1 = this.input.read(arrayOfByte3, n, arrayOfByte3.length - n))
              throw InvalidProtocolBufferException.truncatedMessage();
            this.totalBytesRetired = (i1 + this.totalBytesRetired);
            n += i1;
          }
          k -= arrayOfByte3.length;
          localArrayList.add(arrayOfByte3);
        }
        arrayOfByte1 = new byte[paramInt];
        int m = j - i;
        System.arraycopy(this.buffer, i, arrayOfByte1, 0, m);
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          byte[] arrayOfByte2 = (byte[])localIterator.next();
          System.arraycopy(arrayOfByte2, 0, arrayOfByte1, m, arrayOfByte2.length);
          m += arrayOfByte2.length;
        }
      }
    }
  }

  private void recomputeBufferSizeAfterLimit()
  {
    this.bufferSize += this.bufferSizeAfterLimit;
    int i = this.totalBytesRetired + this.bufferSize;
    if (i > this.currentLimit)
    {
      this.bufferSizeAfterLimit = (i - this.currentLimit);
      this.bufferSize -= this.bufferSizeAfterLimit;
    }
    while (true)
    {
      return;
      this.bufferSizeAfterLimit = 0;
    }
  }

  private boolean refillBuffer(boolean paramBoolean)
    throws IOException
  {
    if (this.bufferPos < this.bufferSize)
      throw new IllegalStateException("refillBuffer() called when buffer wasn't empty.");
    boolean bool;
    if (this.totalBytesRetired + this.bufferSize == this.currentLimit)
    {
      if (paramBoolean)
        throw InvalidProtocolBufferException.truncatedMessage();
      bool = false;
    }
    while (true)
    {
      return bool;
      this.totalBytesRetired += this.bufferSize;
      this.bufferPos = 0;
      if (this.input == null);
      for (int i = -1; ; i = this.input.read(this.buffer))
      {
        this.bufferSize = i;
        if ((this.bufferSize != 0) && (this.bufferSize >= -1))
          break;
        throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + this.bufferSize + "\nThe InputStream implementation is buggy.");
      }
      if (this.bufferSize == -1)
      {
        this.bufferSize = 0;
        if (paramBoolean)
          throw InvalidProtocolBufferException.truncatedMessage();
        bool = false;
      }
      else
      {
        recomputeBufferSizeAfterLimit();
        int j = this.totalBytesRetired + this.bufferSize + this.bufferSizeAfterLimit;
        if ((j > this.sizeLimit) || (j < 0))
          throw InvalidProtocolBufferException.sizeLimitExceeded();
        bool = true;
      }
    }
  }

  private void skipRawBytes(int paramInt)
    throws IOException
  {
    if (paramInt < 0)
      throw InvalidProtocolBufferException.negativeSize();
    if (paramInt + (this.totalBytesRetired + this.bufferPos) > this.currentLimit)
    {
      skipRawBytes(this.currentLimit - this.totalBytesRetired - this.bufferPos);
      throw InvalidProtocolBufferException.truncatedMessage();
    }
    if (paramInt <= this.bufferSize - this.bufferPos);
    int i;
    for (this.bufferPos = (paramInt + this.bufferPos); ; this.bufferPos = (paramInt - i))
    {
      return;
      i = this.bufferSize - this.bufferPos;
      this.bufferPos = this.bufferSize;
      refillBuffer(true);
      while (paramInt - i > this.bufferSize)
      {
        i += this.bufferSize;
        this.bufferPos = this.bufferSize;
        refillBuffer(true);
      }
    }
  }

  public final void checkLastTagWas(int paramInt)
    throws InvalidProtocolBufferException
  {
    if (this.lastTag != paramInt)
      throw InvalidProtocolBufferException.invalidEndTag();
  }

  public final int getBytesUntilLimit()
  {
    if (this.currentLimit == 2147483647);
    int i;
    for (int j = -1; ; j = this.currentLimit - i)
    {
      return j;
      i = this.totalBytesRetired + this.bufferPos;
    }
  }

  public final void popLimit(int paramInt)
  {
    this.currentLimit = paramInt;
    recomputeBufferSizeAfterLimit();
  }

  public final int pushLimit(int paramInt)
    throws InvalidProtocolBufferException
  {
    if (paramInt < 0)
      throw InvalidProtocolBufferException.negativeSize();
    int i = paramInt + (this.totalBytesRetired + this.bufferPos);
    int j = this.currentLimit;
    if (i > j)
      throw InvalidProtocolBufferException.truncatedMessage();
    this.currentLimit = i;
    recomputeBufferSizeAfterLimit();
    return j;
  }

  public final boolean readBool()
    throws IOException
  {
    if (readRawVarint32() != 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final ByteString readBytes()
    throws IOException
  {
    int i = readRawVarint32();
    ByteString localByteString;
    if (i == 0)
      localByteString = ByteString.EMPTY;
    while (true)
    {
      return localByteString;
      if ((i <= this.bufferSize - this.bufferPos) && (i > 0))
      {
        localByteString = ByteString.copyFrom(this.buffer, this.bufferPos, i);
        this.bufferPos = (i + this.bufferPos);
      }
      else
      {
        localByteString = ByteString.copyFrom(readRawBytes(i));
      }
    }
  }

  public final double readDouble()
    throws IOException
  {
    return Double.longBitsToDouble(readRawLittleEndian64());
  }

  public final int readEnum()
    throws IOException
  {
    return readRawVarint32();
  }

  public final int readFixed32()
    throws IOException
  {
    return readRawLittleEndian32();
  }

  public final float readFloat()
    throws IOException
  {
    return Float.intBitsToFloat(readRawLittleEndian32());
  }

  public final void readGroup(int paramInt, MessageLite.Builder paramBuilder, ExtensionRegistryLite paramExtensionRegistryLite)
    throws IOException
  {
    if (this.recursionDepth >= this.recursionLimit)
      throw InvalidProtocolBufferException.recursionLimitExceeded();
    this.recursionDepth = (1 + this.recursionDepth);
    paramBuilder.mergeFrom(this, paramExtensionRegistryLite);
    checkLastTagWas(WireFormat.makeTag(paramInt, 4));
    this.recursionDepth = (-1 + this.recursionDepth);
  }

  public final int readInt32()
    throws IOException
  {
    return readRawVarint32();
  }

  public final long readInt64()
    throws IOException
  {
    return readRawVarint64();
  }

  public final void readMessage(MessageLite.Builder paramBuilder, ExtensionRegistryLite paramExtensionRegistryLite)
    throws IOException
  {
    int i = readRawVarint32();
    if (this.recursionDepth >= this.recursionLimit)
      throw InvalidProtocolBufferException.recursionLimitExceeded();
    int j = pushLimit(i);
    this.recursionDepth = (1 + this.recursionDepth);
    paramBuilder.mergeFrom(this, paramExtensionRegistryLite);
    checkLastTagWas(0);
    this.recursionDepth = (-1 + this.recursionDepth);
    popLimit(j);
  }

  public final int readRawLittleEndian32()
    throws IOException
  {
    int i = readRawByte();
    int j = readRawByte();
    int k = readRawByte();
    int m = readRawByte();
    return i & 0xFF | (j & 0xFF) << 8 | (k & 0xFF) << 16 | (m & 0xFF) << 24;
  }

  public final long readRawLittleEndian64()
    throws IOException
  {
    int i = readRawByte();
    int j = readRawByte();
    int k = readRawByte();
    int m = readRawByte();
    int n = readRawByte();
    int i1 = readRawByte();
    int i2 = readRawByte();
    int i3 = readRawByte();
    return 0xFF & i | (0xFF & j) << 8 | (0xFF & k) << 16 | (0xFF & m) << 24 | (0xFF & n) << 32 | (0xFF & i1) << 40 | (0xFF & i2) << 48 | (0xFF & i3) << 56;
  }

  public final int readRawVarint32()
    throws IOException
  {
    int i = readRawByte();
    int i5;
    if (i >= 0)
      i5 = i;
    int i4;
    do
    {
      int i1;
      int i2;
      while (true)
      {
        return i5;
        int j = i & 0x7F;
        int k = readRawByte();
        if (k >= 0)
        {
          i5 = j | k << 7;
        }
        else
        {
          int m = j | (k & 0x7F) << 7;
          int n = readRawByte();
          if (n >= 0)
          {
            i5 = m | n << 14;
          }
          else
          {
            i1 = m | (n & 0x7F) << 14;
            i2 = readRawByte();
            if (i2 < 0)
              break;
            i5 = i1 | i2 << 21;
          }
        }
      }
      int i3 = i1 | (i2 & 0x7F) << 21;
      i4 = readRawByte();
      i5 = i3 | i4 << 28;
    }
    while (i4 >= 0);
    for (int i6 = 0; ; i6++)
    {
      if (i6 >= 5)
        break label168;
      if (readRawByte() >= 0)
        break;
    }
    label168: throw InvalidProtocolBufferException.malformedVarint();
  }

  public final long readRawVarint64()
    throws IOException
  {
    int i = 0;
    long l = 0L;
    while (i < 64)
    {
      int j = readRawByte();
      l |= (j & 0x7F) << i;
      if ((j & 0x80) == 0)
        return l;
      i += 7;
    }
    throw InvalidProtocolBufferException.malformedVarint();
  }

  public final String readString()
    throws IOException
  {
    int i = readRawVarint32();
    String str;
    if ((i <= this.bufferSize - this.bufferPos) && (i > 0))
    {
      str = new String(this.buffer, this.bufferPos, i, "UTF-8");
      this.bufferPos = (i + this.bufferPos);
    }
    while (true)
    {
      return str;
      str = new String(readRawBytes(i), "UTF-8");
    }
  }

  public final int readTag()
    throws IOException
  {
    int i = 0;
    int j;
    if ((this.bufferPos == this.bufferSize) && (!refillBuffer(false)))
    {
      j = 1;
      if (j == 0)
        break label39;
      this.lastTag = 0;
    }
    while (true)
    {
      return i;
      j = 0;
      break;
      label39: this.lastTag = readRawVarint32();
      if (WireFormat.getTagFieldNumber(this.lastTag) == 0)
        throw InvalidProtocolBufferException.invalidTag();
      i = this.lastTag;
    }
  }

  public final long readUInt64()
    throws IOException
  {
    return readRawVarint64();
  }

  public final boolean skipField(int paramInt)
    throws IOException
  {
    boolean bool = true;
    switch (WireFormat.getTagWireType(paramInt))
    {
    default:
      throw InvalidProtocolBufferException.invalidWireType();
    case 0:
      readRawVarint32();
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      return bool;
      readRawLittleEndian64();
      continue;
      skipRawBytes(readRawVarint32());
      continue;
      int i;
      do
        i = readTag();
      while ((i != 0) && (skipField(i)));
      checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(paramInt), 4));
      continue;
      bool = false;
      continue;
      readRawLittleEndian32();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.CodedInputStream
 * JD-Core Version:    0.6.2
 */