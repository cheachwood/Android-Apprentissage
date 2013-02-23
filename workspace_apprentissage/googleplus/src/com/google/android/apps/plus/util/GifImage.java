package com.google.android.apps.plus.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class GifImage
{
  private static final byte[] sColorTableBuffer = new byte[768];
  int mBackgroundColor;
  int mBackgroundIndex;
  private final byte[] mData;
  boolean mError;
  int[] mGlobalColorTable = new int[256];
  int mGlobalColorTableSize;
  boolean mGlobalColorTableUsed;
  int mHeaderSize;
  private int mHeight;
  private int mWidth;

  public GifImage(byte[] paramArrayOfByte)
  {
    this.mData = paramArrayOfByte;
    GifHeaderStream localGifHeaderStream = new GifHeaderStream(paramArrayOfByte, (byte)0);
    try
    {
      if (localGifHeaderStream.read() == 71)
      {
        i = 1;
        if ((i == 0) || (localGifHeaderStream.read() != 73))
          break label104;
        j = 1;
        if ((j == 0) || (localGifHeaderStream.read() != 70))
          break label110;
        k = 1;
        if (k != 0)
          break label116;
        this.mError = true;
        this.mHeaderSize = localGifHeaderStream.getPosition();
      }
    }
    catch (IOException localIOException1)
    {
      try
      {
        while (true)
        {
          localGifHeaderStream.close();
          label97: return;
          int i = 0;
          continue;
          label104: int j = 0;
          continue;
          label110: int k = 0;
          continue;
          label116: localGifHeaderStream.skip(3L);
          this.mWidth = readShort(localGifHeaderStream);
          this.mHeight = readShort(localGifHeaderStream);
          int m = localGifHeaderStream.read();
          int n = m & 0x80;
          boolean bool = false;
          if (n != 0)
            bool = true;
          this.mGlobalColorTableUsed = bool;
          this.mGlobalColorTableSize = (2 << (m & 0x7));
          this.mBackgroundIndex = localGifHeaderStream.read();
          localGifHeaderStream.skip(1L);
          if ((this.mGlobalColorTableUsed) && (!this.mError))
          {
            readColorTable(localGifHeaderStream, this.mGlobalColorTable, this.mGlobalColorTableSize);
            this.mBackgroundColor = this.mGlobalColorTable[this.mBackgroundIndex];
            continue;
            localIOException1 = localIOException1;
            this.mError = true;
          }
        }
      }
      catch (IOException localIOException2)
      {
        break label97;
      }
    }
  }

  public static boolean isGif(byte[] paramArrayOfByte)
  {
    boolean bool = true;
    if ((paramArrayOfByte.length >= 3) && (paramArrayOfByte[0] == 71) && (paramArrayOfByte[bool] == 73) && (paramArrayOfByte[2] == 70));
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  private static boolean readColorTable(InputStream paramInputStream, int[] paramArrayOfInt, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte1 = sColorTableBuffer;
    int i = paramInt * 3;
    while (true)
    {
      boolean bool;
      try
      {
        if (paramInputStream.read(sColorTableBuffer, 0, i) < i)
        {
          bool = false;
          continue;
          if (j < paramInt)
          {
            byte[] arrayOfByte2 = sColorTableBuffer;
            int m = k + 1;
            int n = 0xFF & arrayOfByte2[k];
            byte[] arrayOfByte3 = sColorTableBuffer;
            int i1 = m + 1;
            int i2 = 0xFF & arrayOfByte3[m];
            byte[] arrayOfByte4 = sColorTableBuffer;
            int i3 = i1 + 1;
            int i4 = 0xFF & arrayOfByte4[i1];
            int i5 = j + 1;
            paramArrayOfInt[j] = (i4 | (0xFF000000 | n << 16 | i2 << 8));
            k = i3;
            j = i5;
            continue;
          }
          bool = true;
        }
      }
      finally
      {
      }
      int j = 0;
      int k = 0;
    }
  }

  private static int readShort(InputStream paramInputStream)
    throws IOException
  {
    return paramInputStream.read() | paramInputStream.read() << 8;
  }

  public final byte[] getData()
  {
    return this.mData;
  }

  public final int getHeight()
  {
    return this.mHeight;
  }

  public final int getSizeEstimate()
  {
    return this.mData.length + 4 * this.mGlobalColorTable.length;
  }

  public final int getWidth()
  {
    return this.mWidth;
  }

  private final class GifHeaderStream extends ByteArrayInputStream
  {
    private GifHeaderStream(byte[] arg2)
    {
      super();
    }

    public final int getPosition()
    {
      return this.pos;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.GifImage
 * JD-Core Version:    0.6.2
 */