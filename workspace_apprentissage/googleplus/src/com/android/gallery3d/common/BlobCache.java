package com.android.gallery3d.common;

import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.zip.Adler32;

public final class BlobCache
  implements Closeable
{
  private int mActiveBytes;
  private RandomAccessFile mActiveDataFile;
  private int mActiveEntries;
  private int mActiveHashStart;
  private int mActiveRegion;
  private Adler32 mAdler32 = new Adler32();
  private byte[] mBlobHeader = new byte[20];
  private RandomAccessFile mDataFile0;
  private RandomAccessFile mDataFile1;
  private int mFileOffset;
  private RandomAccessFile mInactiveDataFile;
  private int mInactiveHashStart;
  private MappedByteBuffer mIndexBuffer;
  private FileChannel mIndexChannel;
  private RandomAccessFile mIndexFile;
  private byte[] mIndexHeader = new byte[32];
  private LookupRequest mLookupRequest = new LookupRequest();
  private int mMaxBytes;
  private int mMaxEntries;
  private int mSlotOffset;
  private int mVersion;

  public BlobCache(String paramString, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
    throws IOException
  {
    this.mIndexFile = new RandomAccessFile(paramString + ".idx", "rw");
    this.mDataFile0 = new RandomAccessFile(paramString + ".0", "rw");
    this.mDataFile1 = new RandomAccessFile(paramString + ".1", "rw");
    this.mVersion = 5;
    if (loadIndex());
    do
    {
      return;
      this.mIndexFile.setLength(0L);
      this.mIndexFile.setLength(32 + 2 * (paramInt1 * 12));
      this.mIndexFile.seek(0L);
      byte[] arrayOfByte = this.mIndexHeader;
      writeInt(arrayOfByte, 0, -1289277392);
      writeInt(arrayOfByte, 4, paramInt1);
      writeInt(arrayOfByte, 8, paramInt2);
      writeInt(arrayOfByte, 12, 0);
      writeInt(arrayOfByte, 16, 0);
      writeInt(arrayOfByte, 20, 4);
      writeInt(arrayOfByte, 24, this.mVersion);
      writeInt(arrayOfByte, 28, checkSum(arrayOfByte, 0, 28));
      this.mIndexFile.write(arrayOfByte);
      this.mDataFile0.setLength(0L);
      this.mDataFile1.setLength(0L);
      this.mDataFile0.seek(0L);
      this.mDataFile1.seek(0L);
      writeInt(arrayOfByte, 0, -1121680112);
      this.mDataFile0.write(arrayOfByte, 0, 4);
      this.mDataFile1.write(arrayOfByte, 0, 4);
    }
    while (loadIndex());
    closeAll();
    throw new IOException("unable to load index");
  }

  private int checkSum(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.mAdler32.reset();
    this.mAdler32.update(paramArrayOfByte, 0, paramInt2);
    return (int)this.mAdler32.getValue();
  }

  private void closeAll()
  {
    closeSilently(this.mIndexChannel);
    closeSilently(this.mIndexFile);
    closeSilently(this.mDataFile0);
    closeSilently(this.mDataFile1);
  }

  private static void closeSilently(Closeable paramCloseable)
  {
    if (paramCloseable == null);
    while (true)
    {
      return;
      try
      {
        paramCloseable.close();
      }
      catch (Throwable localThrowable)
      {
      }
    }
  }

  private static void deleteFileSilently(String paramString)
  {
    try
    {
      new File(paramString).delete();
      label12: return;
    }
    catch (Throwable localThrowable)
    {
      break label12;
    }
  }

  public static void deleteFiles(String paramString)
  {
    deleteFileSilently(paramString + ".idx");
    deleteFileSilently(paramString + ".0");
    deleteFileSilently(paramString + ".1");
  }

  private void flipRegion()
    throws IOException
  {
    this.mActiveRegion = (1 - this.mActiveRegion);
    this.mActiveEntries = 0;
    this.mActiveBytes = 4;
    writeInt(this.mIndexHeader, 12, this.mActiveRegion);
    writeInt(this.mIndexHeader, 16, this.mActiveEntries);
    writeInt(this.mIndexHeader, 20, this.mActiveBytes);
    updateIndexHeader();
    setActiveVariables();
    int i = this.mActiveHashStart;
    byte[] arrayOfByte = new byte[1024];
    this.mIndexBuffer.position(i);
    int j = 12 * this.mMaxEntries;
    while (j > 0)
    {
      int k = Math.min(j, 1024);
      this.mIndexBuffer.put(arrayOfByte, 0, k);
      j -= k;
    }
    syncIndex();
  }

  private boolean getBlob(RandomAccessFile paramRandomAccessFile, int paramInt, LookupRequest paramLookupRequest)
    throws IOException
  {
    byte[] arrayOfByte1 = this.mBlobHeader;
    long l1 = paramRandomAccessFile.getFilePointer();
    long l2 = paramInt;
    try
    {
      paramRandomAccessFile.seek(l2);
      if (paramRandomAccessFile.read(arrayOfByte1) != 20)
      {
        Log.w("BlobCache", "cannot read blob header");
        paramRandomAccessFile.seek(l1);
        bool = false;
      }
      while (true)
      {
        return bool;
        long l3 = 0xFF & arrayOfByte1[7];
        for (int i = 6; i >= 0; i--)
          l3 = l3 << 8 | 0xFF & arrayOfByte1[(i + 0)];
        if (l3 != paramLookupRequest.key)
        {
          Log.w("BlobCache", "blob key does not match: " + l3);
          paramRandomAccessFile.seek(l1);
          bool = false;
        }
        else
        {
          int j = readInt(arrayOfByte1, 8);
          int k = readInt(arrayOfByte1, 12);
          if (k != paramInt)
          {
            Log.w("BlobCache", "blob offset does not match: " + k);
            paramRandomAccessFile.seek(l1);
            bool = false;
          }
          else
          {
            int m = readInt(arrayOfByte1, 16);
            if ((m < 0) || (m > -20 + (this.mMaxBytes - paramInt)))
            {
              Log.w("BlobCache", "invalid blob length: " + m);
              paramRandomAccessFile.seek(l1);
              bool = false;
            }
            else
            {
              if ((paramLookupRequest.buffer == null) || (paramLookupRequest.buffer.length < m))
                paramLookupRequest.buffer = new byte[m];
              byte[] arrayOfByte2 = paramLookupRequest.buffer;
              paramLookupRequest.length = m;
              if (paramRandomAccessFile.read(arrayOfByte2, 0, m) != m)
              {
                Log.w("BlobCache", "cannot read blob data");
                paramRandomAccessFile.seek(l1);
                bool = false;
              }
              else if (checkSum(arrayOfByte2, 0, m) != j)
              {
                Log.w("BlobCache", "blob checksum does not match: " + j);
                paramRandomAccessFile.seek(l1);
                bool = false;
              }
              else
              {
                paramRandomAccessFile.seek(l1);
                bool = true;
              }
            }
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        Log.e("BlobCache", "getBlob failed.", localThrowable);
        paramRandomAccessFile.seek(l1);
        boolean bool = false;
      }
    }
    finally
    {
      paramRandomAccessFile.seek(l1);
    }
  }

  private void insertInternal(long paramLong, byte[] paramArrayOfByte, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = this.mBlobHeader;
    this.mAdler32.reset();
    this.mAdler32.update(paramArrayOfByte);
    int i = (int)this.mAdler32.getValue();
    int j = 0;
    long l = paramLong;
    while (j < 8)
    {
      arrayOfByte[(j + 0)] = ((byte)(int)(0xFF & l));
      l >>= 8;
      j++;
    }
    writeInt(arrayOfByte, 8, i);
    writeInt(arrayOfByte, 12, this.mActiveBytes);
    writeInt(arrayOfByte, 16, paramInt);
    this.mActiveDataFile.write(arrayOfByte);
    this.mActiveDataFile.write(paramArrayOfByte, 0, paramInt);
    this.mIndexBuffer.putLong(this.mSlotOffset, paramLong);
    this.mIndexBuffer.putInt(8 + this.mSlotOffset, this.mActiveBytes);
    this.mActiveBytes += paramInt + 20;
    writeInt(this.mIndexHeader, 20, this.mActiveBytes);
  }

  private boolean loadIndex()
  {
    boolean bool;
    try
    {
      this.mIndexFile.seek(0L);
      this.mDataFile0.seek(0L);
      this.mDataFile1.seek(0L);
      byte[] arrayOfByte1 = this.mIndexHeader;
      if (this.mIndexFile.read(arrayOfByte1) != 32)
      {
        Log.w("BlobCache", "cannot read header");
        bool = false;
      }
      else if (readInt(arrayOfByte1, 0) != -1289277392)
      {
        Log.w("BlobCache", "cannot read header magic");
        bool = false;
      }
      else if (readInt(arrayOfByte1, 24) != this.mVersion)
      {
        Log.w("BlobCache", "version mismatch");
        bool = false;
      }
      else
      {
        this.mMaxEntries = readInt(arrayOfByte1, 4);
        this.mMaxBytes = readInt(arrayOfByte1, 8);
        this.mActiveRegion = readInt(arrayOfByte1, 12);
        this.mActiveEntries = readInt(arrayOfByte1, 16);
        this.mActiveBytes = readInt(arrayOfByte1, 20);
        int i = readInt(arrayOfByte1, 28);
        if (checkSum(arrayOfByte1, 0, 28) != i)
        {
          Log.w("BlobCache", "header checksum does not match");
          bool = false;
        }
        else if (this.mMaxEntries <= 0)
        {
          Log.w("BlobCache", "invalid max entries");
          bool = false;
        }
        else if (this.mMaxBytes <= 0)
        {
          Log.w("BlobCache", "invalid max bytes");
          bool = false;
        }
        else if ((this.mActiveRegion != 0) && (this.mActiveRegion != 1))
        {
          Log.w("BlobCache", "invalid active region");
          bool = false;
        }
        else if ((this.mActiveEntries < 0) || (this.mActiveEntries > this.mMaxEntries))
        {
          Log.w("BlobCache", "invalid active entries");
          bool = false;
        }
        else if ((this.mActiveBytes < 4) || (this.mActiveBytes > this.mMaxBytes))
        {
          Log.w("BlobCache", "invalid active bytes");
          bool = false;
        }
        else if (this.mIndexFile.length() != 32 + 2 * (12 * this.mMaxEntries))
        {
          Log.w("BlobCache", "invalid index file length");
          bool = false;
        }
        else
        {
          byte[] arrayOfByte2 = new byte[4];
          if (this.mDataFile0.read(arrayOfByte2) != 4)
          {
            Log.w("BlobCache", "cannot read data file magic");
            bool = false;
          }
          else if (readInt(arrayOfByte2, 0) != -1121680112)
          {
            Log.w("BlobCache", "invalid data file magic");
            bool = false;
          }
          else if (this.mDataFile1.read(arrayOfByte2) != 4)
          {
            Log.w("BlobCache", "cannot read data file magic");
            bool = false;
          }
          else if (readInt(arrayOfByte2, 0) != -1121680112)
          {
            Log.w("BlobCache", "invalid data file magic");
            bool = false;
          }
          else
          {
            this.mIndexChannel = this.mIndexFile.getChannel();
            this.mIndexBuffer = this.mIndexChannel.map(FileChannel.MapMode.READ_WRITE, 0L, this.mIndexFile.length());
            this.mIndexBuffer.order(ByteOrder.LITTLE_ENDIAN);
            setActiveVariables();
            bool = true;
          }
        }
      }
    }
    catch (IOException localIOException)
    {
      Log.e("BlobCache", "loadIndex failed.", localIOException);
      bool = false;
    }
    return bool;
  }

  private boolean lookup(LookupRequest paramLookupRequest)
    throws IOException
  {
    boolean bool = true;
    if ((lookupInternal(paramLookupRequest.key, this.mActiveHashStart)) && (getBlob(this.mActiveDataFile, this.mFileOffset, paramLookupRequest)));
    while (true)
    {
      return bool;
      int i = this.mSlotOffset;
      if ((lookupInternal(paramLookupRequest.key, this.mInactiveHashStart)) && (getBlob(this.mInactiveDataFile, this.mFileOffset, paramLookupRequest)))
      {
        if ((20 + this.mActiveBytes + paramLookupRequest.length <= this.mMaxBytes) && (2 * this.mActiveEntries < this.mMaxEntries))
        {
          this.mSlotOffset = i;
          try
          {
            insertInternal(paramLookupRequest.key, paramLookupRequest.buffer, paramLookupRequest.length);
            this.mActiveEntries = (1 + this.mActiveEntries);
            writeInt(this.mIndexHeader, 16, this.mActiveEntries);
            updateIndexHeader();
          }
          catch (Throwable localThrowable)
          {
            Log.e("BlobCache", "cannot copy over");
          }
        }
      }
      else
        bool = false;
    }
  }

  private boolean lookupInternal(long paramLong, int paramInt)
  {
    boolean bool = false;
    int i = (int)(paramLong % this.mMaxEntries);
    if (i < 0)
      i += this.mMaxEntries;
    int j = i;
    while (true)
    {
      int k = paramInt + i * 12;
      long l = this.mIndexBuffer.getLong(k);
      int m = this.mIndexBuffer.getInt(k + 8);
      if (m == 0)
        this.mSlotOffset = k;
      while (true)
      {
        return bool;
        if (l != paramLong)
          break;
        this.mSlotOffset = k;
        this.mFileOffset = m;
        bool = true;
      }
      i++;
      if (i >= this.mMaxEntries)
        i = 0;
      if (i == j)
      {
        Log.w("BlobCache", "corrupted index: clear the slot.");
        this.mIndexBuffer.putInt(8 + (paramInt + i * 12), 0);
      }
    }
  }

  private static int readInt(byte[] paramArrayOfByte, int paramInt)
  {
    return 0xFF & paramArrayOfByte[paramInt] | (0xFF & paramArrayOfByte[(paramInt + 1)]) << 8 | (0xFF & paramArrayOfByte[(paramInt + 2)]) << 16 | (0xFF & paramArrayOfByte[(paramInt + 3)]) << 24;
  }

  private void setActiveVariables()
    throws IOException
  {
    RandomAccessFile localRandomAccessFile1;
    RandomAccessFile localRandomAccessFile2;
    if (this.mActiveRegion == 0)
    {
      localRandomAccessFile1 = this.mDataFile0;
      this.mActiveDataFile = localRandomAccessFile1;
      if (this.mActiveRegion != 1)
        break label103;
      localRandomAccessFile2 = this.mDataFile0;
      label30: this.mInactiveDataFile = localRandomAccessFile2;
      this.mActiveDataFile.setLength(this.mActiveBytes);
      this.mActiveDataFile.seek(this.mActiveBytes);
      this.mActiveHashStart = 32;
      this.mInactiveHashStart = 32;
      if (this.mActiveRegion != 0)
        break label111;
      this.mInactiveHashStart += 12 * this.mMaxEntries;
    }
    while (true)
    {
      return;
      localRandomAccessFile1 = this.mDataFile1;
      break;
      label103: localRandomAccessFile2 = this.mDataFile1;
      break label30;
      label111: this.mActiveHashStart += 12 * this.mMaxEntries;
    }
  }

  private void syncIndex()
  {
    try
    {
      this.mIndexBuffer.force();
      return;
    }
    catch (Throwable localThrowable)
    {
      while (true)
        Log.w("BlobCache", "sync index failed", localThrowable);
    }
  }

  private void updateIndexHeader()
  {
    writeInt(this.mIndexHeader, 28, checkSum(this.mIndexHeader, 0, 28));
    this.mIndexBuffer.position(0);
    this.mIndexBuffer.put(this.mIndexHeader);
  }

  private static void writeInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    for (int i = 0; i < 4; i++)
    {
      paramArrayOfByte[(paramInt1 + i)] = ((byte)(paramInt2 & 0xFF));
      paramInt2 >>= 8;
    }
  }

  public final void close()
  {
    syncIndex();
    try
    {
      this.mDataFile0.getFD().sync();
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        while (true)
        {
          this.mDataFile1.getFD().sync();
          closeAll();
          return;
          localThrowable1 = localThrowable1;
          Log.w("BlobCache", "sync data file 0 failed", localThrowable1);
        }
      }
      catch (Throwable localThrowable2)
      {
        while (true)
          Log.w("BlobCache", "sync data file 1 failed", localThrowable2);
      }
    }
  }

  public final void insert(long paramLong, byte[] paramArrayOfByte)
    throws IOException
  {
    if (24 + paramArrayOfByte.length > this.mMaxBytes)
      throw new RuntimeException("blob is too large!");
    if ((20 + this.mActiveBytes + paramArrayOfByte.length > this.mMaxBytes) || (2 * this.mActiveEntries >= this.mMaxEntries))
      flipRegion();
    if (!lookupInternal(paramLong, this.mActiveHashStart))
    {
      this.mActiveEntries = (1 + this.mActiveEntries);
      writeInt(this.mIndexHeader, 16, this.mActiveEntries);
    }
    insertInternal(paramLong, paramArrayOfByte, paramArrayOfByte.length);
    updateIndexHeader();
  }

  public final byte[] lookup(long paramLong)
    throws IOException
  {
    this.mLookupRequest.key = paramLong;
    this.mLookupRequest.buffer = null;
    boolean bool = lookup(this.mLookupRequest);
    byte[] arrayOfByte = null;
    if (bool)
      arrayOfByte = this.mLookupRequest.buffer;
    return arrayOfByte;
  }

  public static final class LookupRequest
  {
    public byte[] buffer;
    public long key;
    public int length;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.gallery3d.common.BlobCache
 * JD-Core Version:    0.6.2
 */