package com.google.android.picasastore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import android.os.SystemClock;
import android.util.Log;
import com.android.gallery3d.common.BitmapUtils;
import com.android.gallery3d.common.BlobCache;
import com.android.gallery3d.common.FileCache;
import com.android.gallery3d.common.FileCache.CacheEntry;
import com.android.gallery3d.common.Utils;
import com.android.gallery3d.util.ThreadPool;
import com.android.gallery3d.util.ThreadPool.Job;
import com.android.gallery3d.util.ThreadPool.JobContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.WeakHashMap;

final class PicasaStore
  implements UrlDownloader.Controller
{
  private BlobCache mBlobCache;
  private PipeDataWriter<byte[]> mBytesWriter;
  private File mCacheDir;
  private final Context mContext;
  private final Method mCreatePipe;
  private FileCache mFileCache;
  private PipeDataWriter<ImagePack> mImagePackWriter;
  private final WeakHashMap<ParcelFileDescriptor, Socket> mKeepAlive;
  private final BroadcastReceiver mMountListener;
  private final ServerSocket mServerSocket;
  private final ThreadPool mThreadPool;
  private final UrlDownloader mUrlDownloader;
  private boolean mUsingInternalStorage;
  private VersionInfo mVersionInfo;

  // ERROR //
  PicasaStore(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 45	java/lang/Object:<init>	()V
    //   4: aload_0
    //   5: iconst_0
    //   6: putfield 47	com/google/android/picasastore/PicasaStore:mUsingInternalStorage	Z
    //   9: aload_0
    //   10: new 49	java/util/WeakHashMap
    //   13: dup
    //   14: invokespecial 50	java/util/WeakHashMap:<init>	()V
    //   17: putfield 52	com/google/android/picasastore/PicasaStore:mKeepAlive	Ljava/util/WeakHashMap;
    //   20: aload_0
    //   21: new 54	com/android/gallery3d/util/ThreadPool
    //   24: dup
    //   25: invokespecial 55	com/android/gallery3d/util/ThreadPool:<init>	()V
    //   28: putfield 57	com/google/android/picasastore/PicasaStore:mThreadPool	Lcom/android/gallery3d/util/ThreadPool;
    //   31: aload_0
    //   32: new 59	com/google/android/picasastore/PicasaStore$2
    //   35: dup
    //   36: aload_0
    //   37: invokespecial 62	com/google/android/picasastore/PicasaStore$2:<init>	(Lcom/google/android/picasastore/PicasaStore;)V
    //   40: putfield 64	com/google/android/picasastore/PicasaStore:mImagePackWriter	Lcom/google/android/picasastore/PicasaStore$PipeDataWriter;
    //   43: aload_0
    //   44: new 66	com/google/android/picasastore/PicasaStore$3
    //   47: dup
    //   48: aload_0
    //   49: invokespecial 67	com/google/android/picasastore/PicasaStore$3:<init>	(Lcom/google/android/picasastore/PicasaStore;)V
    //   52: putfield 69	com/google/android/picasastore/PicasaStore:mBytesWriter	Lcom/google/android/picasastore/PicasaStore$PipeDataWriter;
    //   55: aload_0
    //   56: aload_1
    //   57: invokevirtual 75	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   60: putfield 77	com/google/android/picasastore/PicasaStore:mContext	Landroid/content/Context;
    //   63: aload_0
    //   64: new 79	com/google/android/picasastore/UrlDownloader
    //   67: dup
    //   68: aload_0
    //   69: invokespecial 82	com/google/android/picasastore/UrlDownloader:<init>	(Lcom/google/android/picasastore/UrlDownloader$Controller;)V
    //   72: putfield 84	com/google/android/picasastore/PicasaStore:mUrlDownloader	Lcom/google/android/picasastore/UrlDownloader;
    //   75: aload_0
    //   76: new 86	java/net/ServerSocket
    //   79: dup
    //   80: invokespecial 87	java/net/ServerSocket:<init>	()V
    //   83: putfield 89	com/google/android/picasastore/PicasaStore:mServerSocket	Ljava/net/ServerSocket;
    //   86: aload_0
    //   87: getfield 89	com/google/android/picasastore/PicasaStore:mServerSocket	Ljava/net/ServerSocket;
    //   90: aconst_null
    //   91: iconst_5
    //   92: invokevirtual 93	java/net/ServerSocket:bind	(Ljava/net/SocketAddress;I)V
    //   95: ldc 95
    //   97: ldc 97
    //   99: iconst_0
    //   100: anewarray 99	java/lang/Class
    //   103: invokevirtual 103	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   106: astore 7
    //   108: aload 7
    //   110: astore 4
    //   112: aload_0
    //   113: aload 4
    //   115: putfield 105	com/google/android/picasastore/PicasaStore:mCreatePipe	Ljava/lang/reflect/Method;
    //   118: aload_0
    //   119: new 107	com/google/android/picasastore/PicasaStore$1
    //   122: dup
    //   123: aload_0
    //   124: invokespecial 108	com/google/android/picasastore/PicasaStore$1:<init>	(Lcom/google/android/picasastore/PicasaStore;)V
    //   127: putfield 110	com/google/android/picasastore/PicasaStore:mMountListener	Landroid/content/BroadcastReceiver;
    //   130: new 112	android/content/IntentFilter
    //   133: dup
    //   134: invokespecial 113	android/content/IntentFilter:<init>	()V
    //   137: astore 5
    //   139: aload 5
    //   141: ldc 115
    //   143: invokevirtual 119	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   146: aload 5
    //   148: ldc 121
    //   150: invokevirtual 119	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   153: aload 5
    //   155: ldc 123
    //   157: invokevirtual 126	android/content/IntentFilter:addDataScheme	(Ljava/lang/String;)V
    //   160: aload_0
    //   161: getfield 77	com/google/android/picasastore/PicasaStore:mContext	Landroid/content/Context;
    //   164: aload_0
    //   165: getfield 110	com/google/android/picasastore/PicasaStore:mMountListener	Landroid/content/BroadcastReceiver;
    //   168: aload 5
    //   170: invokevirtual 130	android/content/Context:registerReceiver	(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;
    //   173: pop
    //   174: return
    //   175: astore_2
    //   176: new 132	java/lang/RuntimeException
    //   179: dup
    //   180: ldc 134
    //   182: aload_2
    //   183: invokespecial 137	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   186: athrow
    //   187: astore_3
    //   188: aconst_null
    //   189: astore 4
    //   191: goto -79 -> 112
    //
    // Exception table:
    //   from	to	target	type
    //   75	95	175	java/io/IOException
    //   95	108	187	java/lang/NoSuchMethodException
  }

  private boolean checkCacheVersion(String paramString, int paramInt)
  {
    if (this.mVersionInfo == null)
      this.mVersionInfo = new VersionInfo(getCacheDirectory() + "/cache_versions.info");
    if (this.mVersionInfo.getVersion(paramString) != paramInt)
    {
      this.mVersionInfo.setVersion(paramString, paramInt);
      this.mVersionInfo.sync();
    }
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private ParcelFileDescriptor[] createPipe()
    throws IOException
  {
    ParcelFileDescriptor[] arrayOfParcelFileDescriptor;
    if (this.mCreatePipe == null)
      arrayOfParcelFileDescriptor = createSocketPipe();
    while (true)
    {
      return arrayOfParcelFileDescriptor;
      try
      {
        arrayOfParcelFileDescriptor = (ParcelFileDescriptor[])this.mCreatePipe.invoke(null, new Object[0]);
      }
      catch (Throwable localThrowable)
      {
        Log.e("gp.PicasaStore", "fail to create pipe", localThrowable);
        if ((localThrowable instanceof IOException))
          throw ((IOException)localThrowable);
        throw new IOException(localThrowable.getMessage());
      }
    }
  }

  private ParcelFileDescriptor[] createSocketPipe()
    throws IOException
  {
    Socket[] arrayOfSocket = new Socket[2];
    try
    {
      arrayOfSocket[0] = new Socket(this.mServerSocket.getInetAddress(), this.mServerSocket.getLocalPort());
      arrayOfSocket[1] = this.mServerSocket.accept();
      ParcelFileDescriptor[] arrayOfParcelFileDescriptor = new ParcelFileDescriptor[2];
      arrayOfParcelFileDescriptor[0] = ParcelFileDescriptor.fromSocket(arrayOfSocket[0]);
      arrayOfParcelFileDescriptor[1] = ParcelFileDescriptor.fromSocket(arrayOfSocket[1]);
      this.mKeepAlive.put(arrayOfParcelFileDescriptor[0], arrayOfSocket[0]);
      this.mKeepAlive.put(arrayOfParcelFileDescriptor[1], arrayOfSocket[1]);
      return arrayOfParcelFileDescriptor;
    }
    finally
    {
    }
  }

  private ParcelFileDescriptor findInCacheOrDownload(long paramLong, String paramString, boolean paramBoolean)
    throws FileNotFoundException
  {
    try
    {
      FileCache localFileCache = getDownloadCache();
      if (localFileCache != null)
      {
        FileCache.CacheEntry localCacheEntry = localFileCache.lookup(paramString);
        if (localCacheEntry != null)
        {
          ParcelFileDescriptor localParcelFileDescriptor2 = ParcelFileDescriptor.open(localCacheEntry.cacheFile, 268435456);
          localObject2 = localParcelFileDescriptor2;
          return localObject2;
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      while (true)
      {
        Log.w("gp.PicasaStore", "open image from download cache", localThrowable1);
        Object localObject2 = null;
        if (paramBoolean)
          continue;
        try
        {
          ParcelFileDescriptor localParcelFileDescriptor1 = openPipeHelper(null, new InputStreamWriter(paramLong, this.mUrlDownloader.openInputStream(paramString)), true);
          localObject2 = localParcelFileDescriptor1;
        }
        catch (Throwable localThrowable2)
        {
          Log.w("gp.PicasaStore", "download fail", localThrowable2);
          throw new FileNotFoundException();
        }
      }
    }
    finally
    {
    }
  }

  private BlobCache getBlobCache()
  {
    try
    {
      File localFile;
      if (this.mBlobCache == null)
        localFile = getCacheDirectory();
      try
      {
        String str = localFile.getAbsolutePath() + "/picasa-cache";
        if (checkCacheVersion("picasa-image-cache-version", 5))
          BlobCache.deleteFiles(str);
        if (this.mUsingInternalStorage);
        for (this.mBlobCache = new BlobCache(str, 1250, 52428800, false, 5); ; this.mBlobCache = new BlobCache(str, 5000, 209715200, false, 5))
        {
          BlobCache localBlobCache = this.mBlobCache;
          return localBlobCache;
        }
      }
      catch (Throwable localThrowable)
      {
        while (true)
          Log.w("gp.PicasaStore", "fail to create blob cache", localThrowable);
      }
    }
    finally
    {
    }
  }

  private File getCacheDirectory()
  {
    try
    {
      File localFile1;
      if (this.mCacheDir != null)
        localFile1 = this.mCacheDir;
      while (true)
      {
        return localFile1;
        this.mCacheDir = PicasaStoreFacade.getCacheDirectory();
        this.mUsingInternalStorage = false;
        if (this.mCacheDir == null)
        {
          Log.v("gp.PicasaStore", "switch to internal storage for picasastore cache");
          this.mCacheDir = this.mContext.getCacheDir();
          this.mUsingInternalStorage = true;
        }
        try
        {
          File localFile2 = new File(this.mCacheDir, ".nomedia");
          if (!localFile2.exists())
            localFile2.createNewFile();
          Utils.checkNotNull(this.mCacheDir);
          localFile1 = this.mCacheDir;
        }
        catch (IOException localIOException)
        {
          while (true)
            Log.w("gp.PicasaStore", "fail to create '.nomedia' in " + this.mCacheDir);
        }
      }
    }
    finally
    {
    }
  }

  private FileCache getDownloadCache()
  {
    try
    {
      FileCache localFileCache1 = this.mFileCache;
      if (localFileCache1 == null);
      try
      {
        File localFile = new File(getCacheDirectory(), "download-cache");
        if (checkCacheVersion("picasa-download-cache-version", 1))
          FileCache.deleteFiles(this.mContext, localFile, "picasa-downloads");
        if (!this.mUsingInternalStorage);
        for (this.mFileCache = new FileCache(this.mContext, localFile, "picasa-downloads", 104857600L); ; this.mFileCache = new FileCache(this.mContext, localFile, "picasa-downloads", 20971520L))
        {
          FileCache localFileCache2 = this.mFileCache;
          return localFileCache2;
        }
      }
      catch (Throwable localThrowable)
      {
        while (true)
          Log.w("gp.PicasaStore", "fail to create file cache", localThrowable);
      }
    }
    finally
    {
    }
  }

  private static long getItemIdFromUri(Uri paramUri)
  {
    try
    {
      long l2 = Long.parseLong((String)paramUri.getPathSegments().get(1));
      l1 = l2;
      return l1;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
      {
        Log.w("gp.PicasaStore", "cannot get id from: " + paramUri);
        long l1 = -1L;
      }
    }
  }

  private static boolean isKeyTooLong(int paramInt)
  {
    if (paramInt > 32767);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private ImagePack lookupBlobCache(long paramLong, int paramInt, String paramString)
  {
    return lookupBlobCache(makeKey(paramLong, paramInt), makeAuxKey(paramString));
  }

  // ERROR //
  private ImagePack lookupBlobCache(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 417	com/google/android/picasastore/PicasaStore:getBlobCache	()Lcom/android/gallery3d/common/BlobCache;
    //   4: astore 6
    //   6: aload 6
    //   8: ifnonnull +9 -> 17
    //   11: aconst_null
    //   12: astore 5
    //   14: goto +304 -> 318
    //   17: aload_1
    //   18: invokestatic 421	com/android/gallery3d/common/Utils:crc64Long	([B)J
    //   21: lstore 7
    //   23: aload 6
    //   25: monitorenter
    //   26: aload 6
    //   28: lload 7
    //   30: invokevirtual 424	com/android/gallery3d/common/BlobCache:lookup	(J)[B
    //   33: astore 10
    //   35: aload 6
    //   37: monitorexit
    //   38: aload 10
    //   40: ifnull +286 -> 326
    //   43: aload_2
    //   44: ifnonnull +74 -> 118
    //   47: aload 10
    //   49: arraylength
    //   50: aload_1
    //   51: arraylength
    //   52: if_icmpge +34 -> 86
    //   55: iconst_0
    //   56: istore 12
    //   58: goto +263 -> 321
    //   61: astore 9
    //   63: aload 6
    //   65: monitorexit
    //   66: aload 9
    //   68: athrow
    //   69: astore_3
    //   70: ldc 214
    //   72: ldc_w 426
    //   75: aload_3
    //   76: invokestatic 280	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   79: pop
    //   80: aconst_null
    //   81: astore 5
    //   83: goto +235 -> 318
    //   86: iconst_0
    //   87: istore 21
    //   89: aload_1
    //   90: arraylength
    //   91: istore 22
    //   93: iload 21
    //   95: iload 22
    //   97: if_icmpge +259 -> 356
    //   100: aload_1
    //   101: iload 21
    //   103: baload
    //   104: aload 10
    //   106: iload 21
    //   108: baload
    //   109: if_icmpeq +223 -> 332
    //   112: iconst_0
    //   113: istore 12
    //   115: goto +206 -> 321
    //   118: iconst_3
    //   119: aload_1
    //   120: arraylength
    //   121: aload_2
    //   122: arraylength
    //   123: iadd
    //   124: iadd
    //   125: istore 11
    //   127: aload 10
    //   129: arraylength
    //   130: iload 11
    //   132: if_icmplt +206 -> 338
    //   135: iload 11
    //   137: invokestatic 428	com/google/android/picasastore/PicasaStore:isKeyTooLong	(I)Z
    //   140: ifeq +6 -> 146
    //   143: goto +195 -> 338
    //   146: iconst_0
    //   147: istore 13
    //   149: aload_1
    //   150: arraylength
    //   151: istore 14
    //   153: iload 13
    //   155: iload 14
    //   157: if_icmpge +21 -> 178
    //   160: aload_1
    //   161: iload 13
    //   163: baload
    //   164: aload 10
    //   166: iload 13
    //   168: baload
    //   169: if_icmpeq +175 -> 344
    //   172: iconst_0
    //   173: istore 12
    //   175: goto +146 -> 321
    //   178: aload_1
    //   179: arraylength
    //   180: istore 15
    //   182: iload 15
    //   184: iconst_1
    //   185: iadd
    //   186: istore 16
    //   188: aload 10
    //   190: iload 15
    //   192: baload
    //   193: iload 11
    //   195: i2b
    //   196: if_icmpeq +9 -> 205
    //   199: iconst_0
    //   200: istore 12
    //   202: goto +119 -> 321
    //   205: iload 16
    //   207: iconst_1
    //   208: iadd
    //   209: istore 17
    //   211: aload 10
    //   213: iload 16
    //   215: baload
    //   216: iload 11
    //   218: bipush 8
    //   220: iushr
    //   221: i2b
    //   222: if_icmpeq +9 -> 231
    //   225: iconst_0
    //   226: istore 12
    //   228: goto +93 -> 321
    //   231: iload 17
    //   233: iconst_1
    //   234: iadd
    //   235: istore 18
    //   237: iconst_0
    //   238: istore 19
    //   240: aload_2
    //   241: arraylength
    //   242: istore 20
    //   244: iload 19
    //   246: iload 20
    //   248: if_icmpge +108 -> 356
    //   251: aload_2
    //   252: iload 19
    //   254: baload
    //   255: aload 10
    //   257: iload 19
    //   259: iload 18
    //   261: iadd
    //   262: baload
    //   263: if_icmpeq +87 -> 350
    //   266: iconst_0
    //   267: istore 12
    //   269: goto +52 -> 321
    //   272: new 430	com/google/android/picasastore/PicasaStore$ImagePack
    //   275: dup
    //   276: sipush 255
    //   279: aload 10
    //   281: aload_1
    //   282: arraylength
    //   283: baload
    //   284: iand
    //   285: sipush 255
    //   288: aload 10
    //   290: iconst_1
    //   291: aload_1
    //   292: arraylength
    //   293: iadd
    //   294: baload
    //   295: iand
    //   296: bipush 8
    //   298: ishl
    //   299: iadd
    //   300: sipush 255
    //   303: aload 10
    //   305: iconst_2
    //   306: aload_1
    //   307: arraylength
    //   308: iadd
    //   309: baload
    //   310: iand
    //   311: aload 10
    //   313: invokespecial 433	com/google/android/picasastore/PicasaStore$ImagePack:<init>	(II[B)V
    //   316: astore 5
    //   318: aload 5
    //   320: areturn
    //   321: iload 12
    //   323: ifne -51 -> 272
    //   326: aconst_null
    //   327: astore 5
    //   329: goto -11 -> 318
    //   332: iinc 21 1
    //   335: goto -242 -> 93
    //   338: iconst_0
    //   339: istore 12
    //   341: goto -20 -> 321
    //   344: iinc 13 1
    //   347: goto -194 -> 153
    //   350: iinc 19 1
    //   353: goto -109 -> 244
    //   356: iconst_1
    //   357: istore 12
    //   359: goto -38 -> 321
    //
    // Exception table:
    //   from	to	target	type
    //   26	38	61	finally
    //   0	26	69	java/lang/Throwable
    //   47	69	69	java/lang/Throwable
    //   89	318	69	java/lang/Throwable
  }

  private static byte[] makeAuxKey(String paramString)
  {
    byte[] arrayOfByte;
    if (paramString == null)
    {
      arrayOfByte = null;
      return arrayOfByte;
    }
    String str;
    if (paramString.startsWith("https://"))
      str = paramString.substring(8);
    while (true)
    {
      arrayOfByte = str.getBytes();
      break;
      if (paramString.startsWith("http://"))
        str = paramString.substring(7);
      else
        str = paramString;
    }
  }

  private static byte[] makeKey(long paramLong, int paramInt)
  {
    byte[] arrayOfByte = new byte[9];
    for (int i = 0; i < 8; i++)
      arrayOfByte[i] = ((byte)(int)(paramLong >>> i * 8));
    arrayOfByte[8] = ((byte)paramInt);
    return arrayOfByte;
  }

  private void onMediaMountOrUnmount()
  {
    try
    {
      this.mCacheDir = null;
      if (this.mBlobCache != null)
      {
        Utils.closeSilently(this.mBlobCache);
        this.mBlobCache = null;
      }
      if (this.mFileCache != null)
      {
        Utils.closeSilently(this.mFileCache);
        this.mFileCache = null;
      }
      this.mVersionInfo = null;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private ParcelFileDescriptor openFullImage(long paramLong, String paramString, boolean paramBoolean)
    throws FileNotFoundException
  {
    try
    {
      File localFile = PicasaStoreFacade.getCacheFile(paramLong, ".full");
      if (localFile != null)
      {
        ParcelFileDescriptor localParcelFileDescriptor2 = ParcelFileDescriptor.open(localFile, 268435456);
        localParcelFileDescriptor1 = localParcelFileDescriptor2;
        return localParcelFileDescriptor1;
      }
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        Log.w("gp.PicasaStore", "openFullImage from cache", localThrowable);
        if (paramString == null)
          throw new FileNotFoundException();
        ParcelFileDescriptor localParcelFileDescriptor1 = findInCacheOrDownload(paramLong, paramString, paramBoolean);
      }
    }
  }

  private <T> ParcelFileDescriptor openPipeHelper(final T paramT, final PipeDataWriter<T> paramPipeDataWriter, final boolean paramBoolean)
    throws FileNotFoundException
  {
    try
    {
      final ParcelFileDescriptor[] arrayOfParcelFileDescriptor = createPipe();
      ThreadPool.Job local4 = new ThreadPool.Job()
      {
        private Void run(ThreadPool.JobContext paramAnonymousJobContext)
        {
          int i;
          if (paramBoolean)
            i = 2;
          while (true)
          {
            paramAnonymousJobContext.setMode(i);
            try
            {
              paramPipeDataWriter.writeDataToPipe(arrayOfParcelFileDescriptor[1], paramT);
              return null;
              i = 0;
            }
            finally
            {
              Utils.closeSilently(arrayOfParcelFileDescriptor[1]);
            }
          }
        }
      };
      this.mThreadPool.submit(local4);
      ParcelFileDescriptor localParcelFileDescriptor = arrayOfParcelFileDescriptor[0];
      return localParcelFileDescriptor;
    }
    catch (IOException localIOException)
    {
    }
    throw new FileNotFoundException("failure making pipe");
  }

  private ParcelFileDescriptor openScreenNail(long paramLong, String paramString, boolean paramBoolean)
    throws FileNotFoundException
  {
    ImagePack localImagePack = lookupBlobCache(paramLong, 0, paramString);
    Object localObject;
    if (localImagePack != null)
      localObject = openPipeHelper(localImagePack, this.mImagePackWriter, false);
    while (true)
    {
      return localObject;
      try
      {
        File localFile = PicasaStoreFacade.getCacheFile(paramLong, ".screen");
        if (localFile != null)
        {
          ParcelFileDescriptor localParcelFileDescriptor2 = ParcelFileDescriptor.open(localFile, 268435456);
          localObject = localParcelFileDescriptor2;
        }
      }
      catch (Throwable localThrowable1)
      {
        Log.w("gp.PicasaStore", "openScreenNail from cache", localThrowable1);
        localObject = null;
      }
      if (!paramBoolean)
      {
        if (paramString == null)
          throw new FileNotFoundException();
        try
        {
          ParcelFileDescriptor localParcelFileDescriptor1 = openPipeHelper(null, new DownloadWriter(paramLong, paramString, new BlobCacheRegister(paramLong, 0, 0, paramString)), true);
          localObject = localParcelFileDescriptor1;
        }
        catch (Throwable localThrowable2)
        {
          Log.w("gp.PicasaStore", "download fail", localThrowable2);
        }
      }
    }
    throw new FileNotFoundException();
  }

  private ParcelFileDescriptor openThumbNail(long paramLong, String paramString, boolean paramBoolean)
    throws FileNotFoundException
  {
    ImagePack localImagePack1 = lookupBlobCache(paramLong, 1, paramString);
    Object localObject;
    if ((localImagePack1 != null) && ((0x1 & localImagePack1.flags) == 0))
      localObject = openPipeHelper(localImagePack1, this.mImagePackWriter, false);
    while (true)
    {
      return localObject;
      try
      {
        File localFile = PicasaStoreFacade.getCacheFile(paramLong, ".screen");
        if (localFile != null)
        {
          String str = localFile.getAbsolutePath();
          BitmapFactory.Options localOptions2 = new BitmapFactory.Options();
          localOptions2.inJustDecodeBounds = true;
          BitmapFactory.decodeFile(str, localOptions2);
          localOptions2.inSampleSize = BitmapUtils.computeSampleSizeLarger(Config.sThumbNailSize / Math.min(localOptions2.outWidth, localOptions2.outHeight));
          localOptions2.inJustDecodeBounds = false;
          Bitmap localBitmap2 = BitmapFactory.decodeFile(str, localOptions2);
          if (localBitmap2 != null)
          {
            byte[] arrayOfByte2 = BitmapUtils.compressToBytes(BitmapUtils.resizeAndCropCenter(localBitmap2, Config.sThumbNailSize, true), 95);
            putBlobCache(paramLong, 1, paramString, 0, arrayOfByte2);
            localObject = openPipeHelper(arrayOfByte2, this.mBytesWriter, false);
          }
          else
          {
            Log.e("gp.PicasaStore", "invalid prefetch file: " + str + ", length: " + localFile.length());
            localFile.delete();
          }
        }
        else
        {
          ImagePack localImagePack2 = lookupBlobCache(paramLong, 0, paramString);
          if (localImagePack2 != null)
          {
            BitmapFactory.Options localOptions1 = new BitmapFactory.Options();
            localOptions1.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(localImagePack2.data, localImagePack2.offset, localImagePack2.data.length - localImagePack2.offset);
            localOptions1.inSampleSize = BitmapUtils.computeSampleSizeLarger(Config.sThumbNailSize / Math.min(localOptions1.outWidth, localOptions1.outHeight));
            localOptions1.inJustDecodeBounds = false;
            Bitmap localBitmap1 = BitmapFactory.decodeByteArray(localImagePack2.data, localImagePack2.offset, localImagePack2.data.length - localImagePack2.offset);
            if (localBitmap1 != null)
            {
              byte[] arrayOfByte1 = BitmapUtils.compressToBytes(BitmapUtils.resizeAndCropCenter(localBitmap1, Config.sThumbNailSize, true), 95);
              putBlobCache(paramLong, 1, paramString, 0, arrayOfByte1);
              localObject = openPipeHelper(arrayOfByte1, this.mBytesWriter, false);
            }
          }
        }
      }
      catch (Throwable localThrowable1)
      {
        while (true)
          Log.w("gp.PicasaStore", "openThumbNail from screennail", localThrowable1);
        if (localImagePack1 != null)
        {
          localObject = openPipeHelper(localImagePack1, this.mImagePackWriter, false);
        }
        else if (paramBoolean)
        {
          localObject = null;
        }
        else
        {
          if (paramString == null)
            throw new FileNotFoundException();
          try
          {
            ParcelFileDescriptor localParcelFileDescriptor = openPipeHelper(null, new DownloadWriter(paramLong, paramString, new BlobCacheRegister(paramLong, 1, 1, paramString)), true);
            localObject = localParcelFileDescriptor;
          }
          catch (Throwable localThrowable2)
          {
            Log.w("gp.PicasaStore", "download fail", localThrowable2);
          }
        }
      }
    }
    throw new FileNotFoundException();
  }

  private void putBlobCache(long paramLong, int paramInt1, String paramString, int paramInt2, byte[] paramArrayOfByte)
  {
    putBlobCache(makeKey(paramLong, paramInt1), makeAuxKey(paramString), 0, paramArrayOfByte);
  }

  private void putBlobCache(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt, byte[] paramArrayOfByte3)
  {
    int i = 0;
    int j;
    if (paramArrayOfByte2 == null)
    {
      j = 3 + (i + paramArrayOfByte1.length);
      if (!isKeyTooLong(j))
        break label32;
    }
    while (true)
    {
      return;
      i = paramArrayOfByte2.length;
      break;
      try
      {
        label32: BlobCache localBlobCache = getBlobCache();
        if (localBlobCache != null)
        {
          byte[] arrayOfByte = new byte[j + paramArrayOfByte3.length];
          System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
          arrayOfByte[paramArrayOfByte1.length] = ((byte)j);
          arrayOfByte[(1 + paramArrayOfByte1.length)] = ((byte)(j >>> 8));
          arrayOfByte[(2 + paramArrayOfByte1.length)] = ((byte)paramInt);
          if (i > 0)
            System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, 3 + paramArrayOfByte1.length, i);
          System.arraycopy(paramArrayOfByte3, 0, arrayOfByte, j, paramArrayOfByte3.length);
          long l = Utils.crc64Long(paramArrayOfByte1);
          try
          {
            localBlobCache.insert(l, arrayOfByte);
          }
          finally
          {
            localObject = finally;
            throw localObject;
          }
        }
      }
      catch (Throwable localThrowable)
      {
        Log.w("gp.PicasaStore", "cache insert fail", localThrowable);
      }
    }
  }

  public final File createTempFile()
  {
    Object localObject;
    try
    {
      FileCache localFileCache = getDownloadCache();
      if (localFileCache == null)
      {
        localObject = null;
      }
      else
      {
        File localFile = localFileCache.createFile();
        localObject = localFile;
      }
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
    return localObject;
  }

  public final void onDownloadComplete(String paramString, File paramFile)
  {
    try
    {
      FileCache localFileCache = getDownloadCache();
      if (localFileCache != null)
        localFileCache.store(paramString, paramFile);
      while (true)
      {
        return;
        paramFile.delete();
      }
    }
    finally
    {
    }
  }

  public final ParcelFileDescriptor openAlbumCover(Uri paramUri, String paramString)
    throws FileNotFoundException
  {
    if (paramString.contains("w"))
      throw new FileNotFoundException("invalid mode: " + paramString);
    long l = getItemIdFromUri(paramUri);
    String str = paramUri.getQueryParameter("content_url");
    ImagePack localImagePack = lookupBlobCache(l, 2, str);
    Object localObject1;
    if (localImagePack != null)
    {
      localObject1 = openPipeHelper(localImagePack, this.mImagePackWriter, false);
      return localObject1;
    }
    if (str == null)
      throw new FileNotFoundException(Utils.maskDebugInfo(paramUri.toString()));
    File localFile = PicasaStoreFacade.getAlbumCoverCacheFile(l, str, ".thumb");
    if (localFile.isFile());
    while (true)
    {
      while (true)
      {
        while (true)
        {
          if (localFile == null)
            break label323;
          if (localFile.length() < 524288L)
          {
            byte[] arrayOfByte = new byte[(int)localFile.length()];
            FileInputStream localFileInputStream = new FileInputStream(localFile);
            int i = 0;
            try
            {
              int m;
              for (int j = localFileInputStream.read(arrayOfByte, 0, 0 + arrayOfByte.length); j >= 0; j = localFileInputStream.read(arrayOfByte, i, m))
              {
                int k = arrayOfByte.length;
                if (i >= k)
                  break;
                i += j;
                m = arrayOfByte.length - i;
              }
              putBlobCache(l, 2, str, 0, arrayOfByte);
              ParcelFileDescriptor localParcelFileDescriptor2 = openPipeHelper(arrayOfByte, this.mBytesWriter, false);
              localObject1 = localParcelFileDescriptor2;
              Utils.closeSilently(localFileInputStream);
              break;
            }
            catch (IOException localIOException)
            {
              throw new FileNotFoundException(Utils.maskDebugInfo(paramUri.toString()) + ":" + localIOException);
            }
            finally
            {
              Utils.closeSilently(localFileInputStream);
            }
          }
        }
        localObject1 = ParcelFileDescriptor.open(localFile, 268435456);
        break;
        try
        {
          label323: ParcelFileDescriptor localParcelFileDescriptor1 = openPipeHelper(null, new DownloadWriter(l, PicasaStoreFacade.convertImageUrl(str, Config.sThumbNailSize, true), new BlobCacheRegister(l, 2, 0, str)), true);
          localObject1 = localParcelFileDescriptor1;
        }
        catch (Throwable localThrowable)
        {
          Log.w("gp.PicasaStore", "download fail", localThrowable);
          throw new FileNotFoundException();
        }
      }
      localFile = null;
    }
  }

  public final ParcelFileDescriptor openFile(Uri paramUri, String paramString)
    throws FileNotFoundException
  {
    if (paramString.contains("w"))
      throw new FileNotFoundException("invalid mode: " + paramString);
    String str1 = paramUri.getQueryParameter("type");
    boolean bool = "1".equals(paramUri.getQueryParameter("cache_only"));
    long l = getItemIdFromUri(paramUri);
    String str2 = paramUri.getQueryParameter("content_url");
    ParcelFileDescriptor localParcelFileDescriptor;
    if (l != 0L)
    {
      if ("screennail".equals(str1))
        localParcelFileDescriptor = openScreenNail(l, PicasaStoreFacade.convertImageUrl(str2, Config.sScreenNailSize, false), bool);
      while (true)
      {
        return localParcelFileDescriptor;
        if ("thumbnail".equals(str1))
          localParcelFileDescriptor = openThumbNail(l, PicasaStoreFacade.convertImageUrl(str2, Config.sThumbNailSize, true), bool);
        else
          localParcelFileDescriptor = openFullImage(l, str2, bool);
      }
    }
    if (str2 != null)
    {
      if ("thumbnail".equals(str1))
        str2 = PicasaStoreFacade.convertImageUrl(str2, Config.sThumbNailSize, true);
      while (true)
      {
        localParcelFileDescriptor = findInCacheOrDownload(-1L, str2, bool);
        break;
        if ("screennail".equals(str1))
          str2 = PicasaStoreFacade.convertImageUrl(str2, Config.sScreenNailSize, false);
      }
    }
    throw new FileNotFoundException(paramUri.toString());
  }

  private final class BlobCacheRegister
    implements PicasaStore.DownloadListener
  {
    private final byte[] mAuxKey;
    private ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
    private final int mFlags;
    private final byte[] mKey;

    public BlobCacheRegister(long arg2, int paramInt2, int paramString, String arg6)
    {
      this.mKey = PicasaStore.makeKey(PicasaStore.this, ???, paramInt2);
      String str1;
      this.mAuxKey = PicasaStore.makeAuxKey(PicasaStore.this, str1);
      this.mFlags = paramString;
    }

    public final void onDataAvailable(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      if (this.mBaos.size() < 524288)
        this.mBaos.write(paramArrayOfByte, 0, paramInt2);
    }

    public final void onDownloadComplete()
    {
      if (this.mBaos.size() < 524288)
        PicasaStore.this.putBlobCache(this.mKey, this.mAuxKey, this.mFlags, this.mBaos.toByteArray());
    }
  }

  private static abstract interface DownloadListener
  {
    public abstract void onDataAvailable(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

    public abstract void onDownloadComplete();
  }

  private final class DownloadWriter
    implements PicasaStore.PipeDataWriter<Object>
  {
    private PicasaStore.DownloadListener mDownloadListener;
    private String mDownloadUrl;
    private long mId;

    public DownloadWriter(long arg2, String paramDownloadListener, PicasaStore.DownloadListener arg5)
    {
      this.mId = ???;
      this.mDownloadUrl = paramDownloadListener;
      Object localObject;
      this.mDownloadListener = localObject;
    }

    public final void writeDataToPipe(ParcelFileDescriptor paramParcelFileDescriptor, Object paramObject)
    {
      int i = MetricsUtils.begin("PicasaStore.download " + Utils.maskDebugInfo(Long.valueOf(this.mId)));
      InputStream localInputStream = null;
      ParcelFileDescriptor.AutoCloseOutputStream localAutoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(paramParcelFileDescriptor);
      PicasaStore.DownloadListener localDownloadListener = this.mDownloadListener;
      try
      {
        byte[] arrayOfByte = new byte[2048];
        long l = SystemClock.elapsedRealtime();
        try
        {
          localInputStream = HttpUtils.openInputStream(this.mDownloadUrl);
          int k;
          for (int j = localInputStream.read(arrayOfByte); j > 0; j = k)
          {
            localAutoCloseOutputStream.write(arrayOfByte, 0, j);
            if (localDownloadListener != null)
              localDownloadListener.onDataAvailable(arrayOfByte, 0, j);
            k = localInputStream.read(arrayOfByte);
          }
          MetricsUtils.incrementNetworkOpDurationAndCount(SystemClock.elapsedRealtime() - l);
          if (localDownloadListener != null)
            localDownloadListener.onDownloadComplete();
          return;
        }
        finally
        {
          MetricsUtils.incrementNetworkOpDurationAndCount(SystemClock.elapsedRealtime() - l);
        }
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          HttpUtils.abortConnectionSilently(localInputStream);
          Log.d("gp.PicasaStore", "pipe closed early by caller? " + localIOException);
          Utils.closeSilently(localAutoCloseOutputStream);
          Utils.closeSilently(localInputStream);
          MetricsUtils.endWithReport(i, "picasa.download.photo_video");
        }
      }
      catch (Throwable localThrowable)
      {
        while (true)
        {
          HttpUtils.abortConnectionSilently(localInputStream);
          Log.w("gp.PicasaStore", "fail to write to pipe: " + Utils.maskDebugInfo(this.mDownloadUrl), localThrowable);
          Utils.closeSilently(localAutoCloseOutputStream);
          Utils.closeSilently(localInputStream);
          MetricsUtils.endWithReport(i, "picasa.download.photo_video");
        }
      }
      finally
      {
        Utils.closeSilently(localAutoCloseOutputStream);
        Utils.closeSilently(localInputStream);
        MetricsUtils.endWithReport(i, "picasa.download.photo_video");
      }
    }
  }

  private static final class ImagePack
  {
    public final byte[] data;
    public final int flags;
    public final int offset;

    ImagePack(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
    {
      this.offset = paramInt1;
      this.flags = paramInt2;
      this.data = paramArrayOfByte;
    }
  }

  private final class InputStreamWriter
    implements PicasaStore.PipeDataWriter<Object>
  {
    private long mId;
    private InputStream mInputStream;

    public InputStreamWriter(long arg2, InputStream arg4)
    {
      this.mId = ???;
      Object localObject;
      this.mInputStream = localObject;
    }

    public final void writeDataToPipe(ParcelFileDescriptor paramParcelFileDescriptor, Object paramObject)
    {
      int i = MetricsUtils.begin("PicasaStore.download " + Utils.maskDebugInfo(Long.valueOf(this.mId)));
      InputStream localInputStream = this.mInputStream;
      ParcelFileDescriptor.AutoCloseOutputStream localAutoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(paramParcelFileDescriptor);
      try
      {
        byte[] arrayOfByte = new byte[2048];
        int k;
        for (int j = localInputStream.read(arrayOfByte); j > 0; j = k)
        {
          localAutoCloseOutputStream.write(arrayOfByte, 0, j);
          k = localInputStream.read(arrayOfByte);
        }
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          Log.d("gp.PicasaStore", "pipe closed early by caller? " + localIOException);
          Utils.closeSilently(localAutoCloseOutputStream);
          Utils.closeSilently(localInputStream);
          MetricsUtils.end(i);
        }
      }
      catch (Throwable localThrowable)
      {
        while (true)
        {
          Log.w("gp.PicasaStore", "fail to write to pipe", localThrowable);
          Utils.closeSilently(localAutoCloseOutputStream);
          Utils.closeSilently(localInputStream);
          MetricsUtils.end(i);
        }
      }
      finally
      {
        Utils.closeSilently(localAutoCloseOutputStream);
        Utils.closeSilently(localInputStream);
        MetricsUtils.end(i);
      }
    }
  }

  private static abstract interface PipeDataWriter<T>
  {
    public abstract void writeDataToPipe(ParcelFileDescriptor paramParcelFileDescriptor, T paramT);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasastore.PicasaStore
 * JD-Core Version:    0.6.2
 */