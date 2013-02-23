package com.android.volley.toolbox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class PoolingByteArrayOutputStream extends ByteArrayOutputStream
{
  private final ByteArrayPool mPool;

  public PoolingByteArrayOutputStream(ByteArrayPool paramByteArrayPool)
  {
    this(paramByteArrayPool, 256);
  }

  public PoolingByteArrayOutputStream(ByteArrayPool paramByteArrayPool, int paramInt)
  {
    this.mPool = paramByteArrayPool;
    this.buf = this.mPool.getBuf(Math.max(paramInt, 256));
  }

  private void expand(int paramInt)
  {
    if (paramInt + this.count <= this.buf.length);
    while (true)
    {
      return;
      byte[] arrayOfByte = this.mPool.getBuf(2 * (paramInt + this.count));
      System.arraycopy(this.buf, 0, arrayOfByte, 0, this.count);
      this.mPool.returnBuf(this.buf);
      this.buf = arrayOfByte;
    }
  }

  public final void close()
    throws IOException
  {
    this.mPool.returnBuf(this.buf);
    this.buf = null;
    super.close();
  }

  public final void finalize()
  {
    this.mPool.returnBuf(this.buf);
  }

  public final void write(int paramInt)
  {
    try
    {
      expand(1);
      super.write(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      expand(paramInt2);
      super.write(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.PoolingByteArrayOutputStream
 * JD-Core Version:    0.6.2
 */