package com.google.android.apps.plus.xmpp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;

public final class LogInputStream extends InputStream
{
  private static final LinkedList<StringBuffer> mLogs = new LinkedList();
  private final InputStream mInputStream;
  private final StringBuffer mLogBuffer;

  public LogInputStream(InputStream paramInputStream)
  {
    this.mInputStream = paramInputStream;
    this.mLogBuffer = new StringBuffer();
  }

  public static String getLog()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = mLogs.iterator();
    while (localIterator.hasNext())
    {
      localStringBuffer.append((StringBuffer)localIterator.next());
      localStringBuffer.append("\n");
    }
    return localStringBuffer.toString();
  }

  public final int available()
    throws IOException
  {
    return this.mInputStream.available();
  }

  public final void close()
    throws IOException
  {
    this.mInputStream.close();
    mLogs.add(this.mLogBuffer);
    if (mLogs.size() > 3)
      mLogs.removeFirst();
  }

  public final void mark(int paramInt)
  {
    this.mInputStream.mark(paramInt);
  }

  public final boolean markSupported()
  {
    return this.mInputStream.markSupported();
  }

  public final int read()
    throws IOException
  {
    try
    {
      int i = this.mInputStream.read();
      this.mLogBuffer.append((char)i);
      return i;
    }
    catch (IOException localIOException)
    {
      throw localIOException;
    }
  }

  public final int read(byte[] paramArrayOfByte)
    throws IOException
  {
    int i;
    try
    {
      i = this.mInputStream.read(paramArrayOfByte);
      for (int j = 0; j < i; j++)
        this.mLogBuffer.append((char)paramArrayOfByte[j]);
    }
    catch (IOException localIOException)
    {
      throw localIOException;
    }
    return i;
  }

  public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i;
    try
    {
      i = this.mInputStream.read(paramArrayOfByte, paramInt1, paramInt2);
      for (int j = paramInt1; j < paramInt1 + i; j++)
        this.mLogBuffer.append((char)paramArrayOfByte[j]);
    }
    catch (IOException localIOException)
    {
      throw localIOException;
    }
    return i;
  }

  public final void reset()
    throws IOException
  {
    try
    {
      this.mInputStream.reset();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final long skip(long paramLong)
    throws IOException
  {
    return this.mInputStream.skip(paramLong);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.xmpp.LogInputStream
 * JD-Core Version:    0.6.2
 */