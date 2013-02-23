package com.google.android.picasasync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StatFs;
import android.util.Log;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Utils;
import com.google.android.picasastore.Config;
import com.google.android.picasastore.MetricsUtils;
import com.google.android.picasastore.PicasaStoreFacade;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

final class PrefetchHelper
{
  private static final String ALBUM_TABLE_NAME = AlbumEntry.SCHEMA.getTableName();
  private static final String PHOTO_TABLE_NAME = PhotoEntry.SCHEMA.getTableName();
  private static final String[] PROJECTION_ID = { "_id" };
  private static final String[] PROJECTION_ID_CACHE_FLAG_STATUS_THUMBNAIL;
  private static final String[] PROJECTION_ID_ROTATION_CONTENT_URL_CONTENT_TYPE_SCREENNAIL_URL = { "_id", "rotation", "content_url", "content_type", "screennail_url" };
  private static final String[] PROJECTION_ID_SCREENNAIL_URL;
  private static final String[] PROJECTION_ID_THUMBNAIL_URL;
  private static final String QUERY_CACHE_STATUS_COUNT = String.format("SELECT count(*), %s.%s AS status FROM %s, %s WHERE %s.%s = %s.%s AND %s.%s = ? GROUP BY status", arrayOfObject);
  private static final String WHERE_ALBUM_ID_AND_CACHE_STATUS;
  private static final String WHERE_CACHE_STATUS_AND_USER_ID;
  private static final String WHERE_USER_ID_AND_CACHE_FLAG;
  private static PrefetchHelper sInstance;
  private AtomicInteger mCacheConfigVersion = new AtomicInteger(0);
  private String mCacheDir;
  private final Context mContext;
  private final PicasaDatabaseHelper mDbHelper;

  static
  {
    PROJECTION_ID_CACHE_FLAG_STATUS_THUMBNAIL = new String[] { "_id", "cache_flag", "cache_status", "thumbnail_url" };
    WHERE_USER_ID_AND_CACHE_FLAG = String.format("%s=? AND %s=?", new Object[] { "user_id", "cache_flag" });
    WHERE_ALBUM_ID_AND_CACHE_STATUS = String.format("%s=? AND %s=?", new Object[] { "album_id", "cache_status" });
    PROJECTION_ID_SCREENNAIL_URL = new String[] { "_id", "screennail_url" };
    WHERE_CACHE_STATUS_AND_USER_ID = String.format("%s = ? AND %s = ?", new Object[] { "cache_status", "user_id" });
    PROJECTION_ID_THUMBNAIL_URL = new String[] { "_id", "thumbnail_url" };
    Object[] arrayOfObject = new Object[10];
    arrayOfObject[0] = PHOTO_TABLE_NAME;
    arrayOfObject[1] = "cache_status";
    arrayOfObject[2] = PHOTO_TABLE_NAME;
    arrayOfObject[3] = ALBUM_TABLE_NAME;
    arrayOfObject[4] = PHOTO_TABLE_NAME;
    arrayOfObject[5] = "album_id";
    arrayOfObject[6] = ALBUM_TABLE_NAME;
    arrayOfObject[7] = "_id";
    arrayOfObject[8] = ALBUM_TABLE_NAME;
    arrayOfObject[9] = "cache_flag";
  }

  private PrefetchHelper(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mDbHelper = PicasaDatabaseHelper.get(paramContext);
  }

  private static void collectKeepSet(SQLiteDatabase paramSQLiteDatabase, long paramLong, HashMap<Long, Integer> paramHashMap, Integer paramInteger)
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong);
    Cursor localCursor = paramSQLiteDatabase.query(PHOTO_TABLE_NAME, PROJECTION_ID, "album_id=?", arrayOfString, null, null, null);
    try
    {
      if (localCursor.moveToNext())
        paramHashMap.put(Long.valueOf(localCursor.getLong(0)), paramInteger);
    }
    finally
    {
      localCursor.close();
    }
  }

  private void deleteUnusedAlbumCovers$3ec14b17(HashSet<String> paramHashSet)
    throws IOException
  {
    File localFile = new File(getCacheDirectory(), "picasa_covers");
    String[] arrayOfString = localFile.list();
    if (arrayOfString == null)
      return;
    int i = arrayOfString.length;
    int j = 0;
    label31: String str1;
    int k;
    if (j < i)
    {
      str1 = arrayOfString[j];
      k = str1.lastIndexOf('.');
      if (k >= 0)
        break label116;
    }
    label116: for (String str2 = str1; ; str2 = str1.substring(0, k))
    {
      if ((!paramHashSet.contains(str2)) && (!new File(localFile, str1).delete()))
        Log.w("PrefetchHelper", "cannot delete album cover: " + str1);
      j++;
      break label31;
      break;
    }
  }

  private void deleteUnusedCacheFiles(PrefetchContext paramPrefetchContext, HashMap<Long, Integer> paramHashMap)
    throws IOException
  {
    String str1 = getCacheDirectory();
    String[] arrayOfString1 = new File(str1).list();
    int i = arrayOfString1.length;
    int j = 0;
    String str2;
    if (j < i)
    {
      str2 = arrayOfString1[j];
      if (!paramPrefetchContext.syncInterrupted())
        if (!str2.startsWith("picasa-"))
          break label174;
    }
    while (true)
    {
      int m;
      try
      {
        File localFile = new File(str1, str2);
        String[] arrayOfString2 = localFile.list();
        if (arrayOfString2 == null)
          break label174;
        int k = arrayOfString2.length;
        m = 0;
        if (m < k)
        {
          String str3 = arrayOfString2[m];
          if (!paramPrefetchContext.syncInterrupted())
          {
            if (keepCacheFile(localFile, str3, paramHashMap))
              break label180;
            new File(localFile, str3).delete();
            break label180;
          }
        }
        if (localFile.list().length != 0)
          break label174;
        localFile.delete();
      }
      catch (Throwable localThrowable)
      {
        Log.w("PrefetchHelper", localThrowable);
      }
      return;
      label174: j++;
      break;
      label180: m++;
    }
  }

  // ERROR //
  private static boolean downloadPhoto(PrefetchContext paramPrefetchContext, String paramString, File paramFile)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: invokestatic 255	android/os/SystemClock:elapsedRealtime	()J
    //   5: lstore 4
    //   7: aconst_null
    //   8: astore 6
    //   10: aconst_null
    //   11: astore 7
    //   13: aload_1
    //   14: invokestatic 261	com/google/android/picasastore/HttpUtils:openInputStream	(Ljava/lang/String;)Ljava/io/InputStream;
    //   17: astore 7
    //   19: new 263	java/io/FileOutputStream
    //   22: dup
    //   23: aload_2
    //   24: invokespecial 266	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   27: astore 11
    //   29: sipush 4096
    //   32: newarray byte
    //   34: astore 12
    //   36: aload 7
    //   38: aload 12
    //   40: iconst_0
    //   41: aload 12
    //   43: arraylength
    //   44: invokevirtual 272	java/io/InputStream:read	([BII)I
    //   47: istore 13
    //   49: iload 13
    //   51: ifle +66 -> 117
    //   54: aload_0
    //   55: invokevirtual 234	com/google/android/picasasync/PrefetchHelper$PrefetchContext:syncInterrupted	()Z
    //   58: ifeq +29 -> 87
    //   61: aload 7
    //   63: invokestatic 276	com/google/android/picasastore/HttpUtils:abortConnectionSilently	(Ljava/io/InputStream;)V
    //   66: aload 7
    //   68: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   71: aload 11
    //   73: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   76: invokestatic 255	android/os/SystemClock:elapsedRealtime	()J
    //   79: lload 4
    //   81: lsub
    //   82: invokestatic 288	com/google/android/picasastore/MetricsUtils:incrementNetworkOpDurationAndCount	(J)V
    //   85: iload_3
    //   86: ireturn
    //   87: aload 11
    //   89: aload 12
    //   91: iconst_0
    //   92: iload 13
    //   94: invokevirtual 294	java/io/OutputStream:write	([BII)V
    //   97: aload 7
    //   99: aload 12
    //   101: iconst_0
    //   102: aload 12
    //   104: arraylength
    //   105: invokevirtual 272	java/io/InputStream:read	([BII)I
    //   108: istore 14
    //   110: iload 14
    //   112: istore 13
    //   114: goto -65 -> 49
    //   117: aload 7
    //   119: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   122: aload 11
    //   124: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   127: invokestatic 255	android/os/SystemClock:elapsedRealtime	()J
    //   130: lload 4
    //   132: lsub
    //   133: invokestatic 288	com/google/android/picasastore/MetricsUtils:incrementNetworkOpDurationAndCount	(J)V
    //   136: iconst_1
    //   137: istore_3
    //   138: goto -53 -> 85
    //   141: astore 9
    //   143: aload 7
    //   145: invokestatic 276	com/google/android/picasastore/HttpUtils:abortConnectionSilently	(Ljava/io/InputStream;)V
    //   148: ldc 200
    //   150: new 202	java/lang/StringBuilder
    //   153: dup
    //   154: ldc_w 296
    //   157: invokespecial 207	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   160: aload_1
    //   161: invokestatic 300	com/android/gallery3d/common/Utils:maskDebugInfo	(Ljava/lang/Object;)Ljava/lang/String;
    //   164: invokevirtual 211	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   170: aload 9
    //   172: invokestatic 303	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   175: pop
    //   176: aload 7
    //   178: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   181: aload 6
    //   183: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   186: invokestatic 255	android/os/SystemClock:elapsedRealtime	()J
    //   189: lload 4
    //   191: lsub
    //   192: invokestatic 288	com/google/android/picasastore/MetricsUtils:incrementNetworkOpDurationAndCount	(J)V
    //   195: iconst_0
    //   196: istore_3
    //   197: goto -112 -> 85
    //   200: astore 8
    //   202: aload 7
    //   204: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   207: aload 6
    //   209: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   212: invokestatic 255	android/os/SystemClock:elapsedRealtime	()J
    //   215: lload 4
    //   217: lsub
    //   218: invokestatic 288	com/google/android/picasastore/MetricsUtils:incrementNetworkOpDurationAndCount	(J)V
    //   221: aload 8
    //   223: athrow
    //   224: astore 8
    //   226: aload 11
    //   228: astore 6
    //   230: goto -28 -> 202
    //   233: astore 9
    //   235: aload 11
    //   237: astore 6
    //   239: goto -96 -> 143
    //
    // Exception table:
    //   from	to	target	type
    //   13	29	141	java/io/IOException
    //   13	29	200	finally
    //   143	176	200	finally
    //   29	66	224	finally
    //   87	110	224	finally
    //   29	66	233	java/io/IOException
    //   87	110	233	java/io/IOException
  }

  // ERROR //
  private void generateScreennail(long paramLong, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 308	com/google/android/picasasync/PrefetchHelper:getAvailableStorage	()J
    //   4: lstore 4
    //   6: lload 4
    //   8: ldc2_w 309
    //   11: lcmp
    //   12: ifge +35 -> 47
    //   15: new 312	java/lang/RuntimeException
    //   18: dup
    //   19: new 202	java/lang/StringBuilder
    //   22: dup
    //   23: ldc_w 314
    //   26: invokespecial 207	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   29: lload 4
    //   31: invokevirtual 317	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   34: ldc_w 319
    //   37: invokevirtual 211	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   43: invokespecial 320	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   46: athrow
    //   47: aconst_null
    //   48: astore 6
    //   50: lload_1
    //   51: ldc_w 322
    //   54: invokestatic 328	com/google/android/picasastore/PicasaStoreFacade:getCacheFile	(JLjava/lang/String;)Ljava/io/File;
    //   57: astore 10
    //   59: lload_1
    //   60: ldc_w 330
    //   63: invokestatic 333	com/google/android/picasastore/PicasaStoreFacade:createCacheFile	(JLjava/lang/String;)Ljava/io/File;
    //   66: astore 11
    //   68: aload 10
    //   70: ifnull +8 -> 78
    //   73: aload 11
    //   75: ifnonnull +8 -> 83
    //   78: aconst_null
    //   79: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   82: return
    //   83: aload 10
    //   85: invokevirtual 336	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   88: astore 12
    //   90: new 338	android/graphics/BitmapFactory$Options
    //   93: dup
    //   94: invokespecial 339	android/graphics/BitmapFactory$Options:<init>	()V
    //   97: astore 13
    //   99: aload 13
    //   101: iconst_1
    //   102: putfield 343	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   105: aload 12
    //   107: aload 13
    //   109: invokestatic 349	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   112: pop
    //   113: aload 13
    //   115: getfield 353	android/graphics/BitmapFactory$Options:outWidth	I
    //   118: istore 15
    //   120: aload 13
    //   122: getfield 356	android/graphics/BitmapFactory$Options:outHeight	I
    //   125: istore 16
    //   127: getstatic 361	com/google/android/picasastore/Config:sScreenNailSize	I
    //   130: istore 17
    //   132: iload 15
    //   134: iload 17
    //   136: idiv
    //   137: iload 16
    //   139: iload 17
    //   141: idiv
    //   142: invokestatic 367	java/lang/Math:max	(II)I
    //   145: istore 18
    //   147: aconst_null
    //   148: astore 6
    //   150: iload 18
    //   152: iconst_1
    //   153: if_icmpgt +40 -> 193
    //   156: iconst_1
    //   157: istore 19
    //   159: aload 13
    //   161: iload 19
    //   163: putfield 370	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   166: aload 13
    //   168: iconst_0
    //   169: putfield 343	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   172: aload 12
    //   174: aload 13
    //   176: invokestatic 349	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   179: astore 20
    //   181: aload 20
    //   183: ifnonnull +46 -> 229
    //   186: aconst_null
    //   187: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   190: goto -108 -> 82
    //   193: iload 18
    //   195: bipush 8
    //   197: if_icmpgt +16 -> 213
    //   200: iload 18
    //   202: invokestatic 373	com/android/gallery3d/common/Utils:prevPowerOf2	(I)I
    //   205: istore 19
    //   207: aconst_null
    //   208: astore 6
    //   210: goto -51 -> 159
    //   213: bipush 8
    //   215: iload 18
    //   217: bipush 8
    //   219: idiv
    //   220: imul
    //   221: istore 19
    //   223: aconst_null
    //   224: astore 6
    //   226: goto -67 -> 159
    //   229: aload 20
    //   231: getstatic 361	com/google/android/picasastore/Config:sScreenNailSize	I
    //   234: iconst_1
    //   235: invokestatic 379	com/android/gallery3d/common/BitmapUtils:resizeDownBySideLength	(Landroid/graphics/Bitmap;IZ)Landroid/graphics/Bitmap;
    //   238: astore 21
    //   240: aconst_null
    //   241: astore 6
    //   243: iload_3
    //   244: ifne +42 -> 286
    //   247: aload 21
    //   249: bipush 95
    //   251: invokestatic 383	com/android/gallery3d/common/BitmapUtils:compressToBytes	(Landroid/graphics/Bitmap;I)[B
    //   254: astore 27
    //   256: new 263	java/io/FileOutputStream
    //   259: dup
    //   260: aload 11
    //   262: invokespecial 266	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   265: astore 28
    //   267: aload 28
    //   269: aload 27
    //   271: iconst_0
    //   272: aload 27
    //   274: arraylength
    //   275: invokevirtual 384	java/io/FileOutputStream:write	([BII)V
    //   278: aload 28
    //   280: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   283: goto -201 -> 82
    //   286: aload 21
    //   288: invokevirtual 390	android/graphics/Bitmap:getWidth	()I
    //   291: istore 22
    //   293: aload 21
    //   295: invokevirtual 393	android/graphics/Bitmap:getHeight	()I
    //   298: istore 23
    //   300: new 395	android/graphics/Matrix
    //   303: dup
    //   304: invokespecial 396	android/graphics/Matrix:<init>	()V
    //   307: astore 24
    //   309: aload 24
    //   311: iload_3
    //   312: i2f
    //   313: invokevirtual 400	android/graphics/Matrix:postRotate	(F)Z
    //   316: pop
    //   317: aload 21
    //   319: iconst_0
    //   320: iconst_0
    //   321: iload 22
    //   323: iload 23
    //   325: aload 24
    //   327: iconst_1
    //   328: invokestatic 404	android/graphics/Bitmap:createBitmap	(Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;
    //   331: astore 26
    //   333: aload 21
    //   335: invokevirtual 407	android/graphics/Bitmap:recycle	()V
    //   338: aload 26
    //   340: astore 21
    //   342: goto -95 -> 247
    //   345: astore 8
    //   347: ldc 200
    //   349: new 202	java/lang/StringBuilder
    //   352: dup
    //   353: ldc_w 409
    //   356: invokespecial 207	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   359: aload 8
    //   361: invokevirtual 412	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   364: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   367: invokestatic 415	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   370: pop
    //   371: aload 6
    //   373: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   376: goto -294 -> 82
    //   379: astore 7
    //   381: aload 6
    //   383: invokestatic 282	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   386: aload 7
    //   388: athrow
    //   389: astore 7
    //   391: aload 28
    //   393: astore 6
    //   395: goto -14 -> 381
    //   398: astore 8
    //   400: aload 28
    //   402: astore 6
    //   404: goto -57 -> 347
    //
    // Exception table:
    //   from	to	target	type
    //   50	68	345	java/lang/Throwable
    //   83	181	345	java/lang/Throwable
    //   200	267	345	java/lang/Throwable
    //   286	338	345	java/lang/Throwable
    //   50	68	379	finally
    //   83	181	379	finally
    //   200	267	379	finally
    //   286	338	379	finally
    //   347	371	379	finally
    //   267	278	389	finally
    //   267	278	398	java/lang/Throwable
  }

  public static PrefetchHelper get(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new PrefetchHelper(paramContext);
      PrefetchHelper localPrefetchHelper = sInstance;
      return localPrefetchHelper;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private static File getAlbumCoverCacheFile(long paramLong, String paramString1, String paramString2)
    throws IOException
  {
    File localFile = PicasaStoreFacade.getAlbumCoverCacheFile(paramLong, paramString1, paramString2);
    if (localFile == null)
      throw new IOException("external storage not present");
    return localFile;
  }

  private long getAvailableStorage()
  {
    try
    {
      StatFs localStatFs = new StatFs(getCacheDirectory());
      long l2 = localStatFs.getAvailableBlocks();
      int i = localStatFs.getBlockSize();
      l1 = l2 * i;
      return l1;
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        Log.w("PrefetchHelper", "Fail to getAvailableStorage", localThrowable);
        long l1 = 0L;
      }
    }
  }

  private String getCacheDirectory()
    throws IOException
  {
    if (this.mCacheDir == null)
    {
      File localFile = PicasaStoreFacade.getCacheDirectory();
      if (localFile == null)
        throw new IOException("external storage is not present");
      this.mCacheDir = localFile.getAbsolutePath();
    }
    return this.mCacheDir;
  }

  private static boolean keepCacheFile(File paramFile, String paramString, HashMap<Long, Integer> paramHashMap)
  {
    int i = paramString.lastIndexOf('.');
    boolean bool1 = false;
    if (i == -1);
    while (true)
    {
      return bool1;
      String str1 = paramString.substring(0, i);
      String str2 = paramString.substring(i);
      try
      {
        long l = Long.parseLong(str1);
        Integer localInteger = (Integer)paramHashMap.get(Long.valueOf(l));
        bool1 = false;
        if (localInteger == null)
          continue;
        int j = localInteger.intValue();
        bool1 = false;
        if (2 == j)
        {
          boolean bool3 = ".full".equals(str2);
          bool1 = false;
          if (!bool3)
            break label260;
          boolean bool4 = new File(paramFile, str1 + ".screen").length() < 0L;
          bool1 = false;
          if (!bool4)
            break label260;
          paramHashMap.remove(Long.valueOf(l));
          break label260;
        }
        int k = localInteger.intValue();
        bool1 = false;
        if (1 == k)
        {
          boolean bool2 = ".screen".equals(str2);
          bool1 = false;
          if (!bool2)
            continue;
          paramHashMap.remove(Long.valueOf(l));
          bool1 = true;
          continue;
        }
        Log.w("PrefetchHelper", "remove unknown cache file: " + paramString);
        bool1 = false;
      }
      catch (Throwable localThrowable)
      {
        Log.w("PrefetchHelper", "cannot parse id: " + paramString);
      }
      continue;
      label260: bool1 = true;
    }
  }

  private void notifyAlbumsChange()
  {
    this.mContext.getContentResolver().notifyChange(PicasaFacade.get(this.mContext).getAlbumsUri(), null, false);
  }

  private static void setCacheStatus(SQLiteDatabase paramSQLiteDatabase, HashMap<Long, Integer> paramHashMap)
  {
    paramSQLiteDatabase.beginTransaction();
    while (true)
    {
      try
      {
        ContentValues localContentValues = new ContentValues();
        String[] arrayOfString = new String[1];
        Iterator localIterator = paramHashMap.entrySet().iterator();
        if (!localIterator.hasNext())
          break;
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (((Integer)localEntry.getValue()).intValue() == 2)
        {
          i = 2;
          localContentValues.put("cache_status", Integer.valueOf(i));
          arrayOfString[0] = String.valueOf(localEntry.getKey());
          paramSQLiteDatabase.update(PHOTO_TABLE_NAME, localContentValues, "_id=?", arrayOfString);
          continue;
        }
      }
      finally
      {
        paramSQLiteDatabase.endTransaction();
      }
      int i = 1;
    }
    paramSQLiteDatabase.setTransactionSuccessful();
    paramSQLiteDatabase.endTransaction();
  }

  private boolean syncOnePhoto(PrefetchContext paramPrefetchContext, long paramLong, String paramString1, String paramString2)
    throws IOException
  {
    long l = getAvailableStorage();
    if (l < 1073741824L)
      throw new RuntimeException("space not enough: " + l + ", stop sync");
    File localFile = PicasaStoreFacade.createCacheFile(paramLong, ".download");
    if (localFile == null)
      throw new IOException("external storage absent?");
    if ((Log.isLoggable("PrefetchHelper", 2)) && (paramString2 == ".full"))
      Log.v("PrefetchHelper", "download full image for " + paramLong + ": " + Utils.maskDebugInfo(paramString1));
    boolean bool;
    if (!downloadPhoto(paramPrefetchContext, paramString1, localFile))
    {
      localFile.delete();
      paramPrefetchContext.onDownloadFinish(paramLong, false);
      bool = false;
    }
    while (true)
    {
      return bool;
      if (!localFile.renameTo(PicasaStoreFacade.createCacheFile(paramLong, paramString2)))
      {
        Log.e("PrefetchHelper", "cannot rename file: " + localFile);
        localFile.delete();
        paramPrefetchContext.onDownloadFinish(paramLong, false);
        bool = false;
      }
      else
      {
        paramPrefetchContext.onDownloadFinish(paramLong, true);
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("cache_status", Integer.valueOf(0));
        SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
        String str = PHOTO_TABLE_NAME;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = String.valueOf(paramLong);
        localSQLiteDatabase.update(str, localContentValues, "_id=?", arrayOfString);
        bool = true;
      }
    }
  }

  private void updateAlbumCacheStatus(SQLiteDatabase paramSQLiteDatabase, long paramLong, int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("cache_status", Integer.valueOf(paramInt));
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong);
    paramSQLiteDatabase.update(ALBUM_TABLE_NAME, localContentValues, "_id=?", arrayOfString);
    notifyAlbumsChange();
  }

  public final void cleanCache(PrefetchContext paramPrefetchContext)
    throws IOException
  {
    int i = MetricsUtils.begin("PrefetchHelper.cleanCache");
    HashMap localHashMap = new HashMap();
    HashSet localHashSet = new HashSet();
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("cache_status", Integer.valueOf(0));
    localSQLiteDatabase.update(PHOTO_TABLE_NAME, localContentValues, "cache_status <> 0", null);
    Cursor localCursor = localSQLiteDatabase.query(ALBUM_TABLE_NAME, PROJECTION_ID_CACHE_FLAG_STATUS_THUMBNAIL, null, null, null, null, null);
    while (true)
    {
      long l;
      int j;
      int k;
      try
      {
        if (!localCursor.moveToNext())
          break label275;
        boolean bool = paramPrefetchContext.syncInterrupted();
        if (bool)
          return;
        l = localCursor.getLong(0);
        j = localCursor.getInt(1);
        k = localCursor.getInt(2);
        localHashSet.add(PicasaStoreFacade.getAlbumCoverKey(l, localCursor.getString(3)));
        if (j == 2)
        {
          if ((k != 3) && (k != 1))
            updateAlbumCacheStatus(localSQLiteDatabase, l, 1);
          collectKeepSet(localSQLiteDatabase, l, localHashMap, Integer.valueOf(2));
          continue;
        }
      }
      finally
      {
        localCursor.close();
      }
      if (j == 1)
      {
        if (k != 0)
          updateAlbumCacheStatus(localSQLiteDatabase, l, 0);
        collectKeepSet(localSQLiteDatabase, l, localHashMap, Integer.valueOf(1));
      }
      else if ((j == 0) && (k != 0))
      {
        updateAlbumCacheStatus(localSQLiteDatabase, l, 0);
        continue;
        label275: localCursor.close();
        deleteUnusedAlbumCovers$3ec14b17(localHashSet);
        deleteUnusedCacheFiles(paramPrefetchContext, localHashMap);
        setCacheStatus(localSQLiteDatabase, localHashMap);
        MetricsUtils.end(i);
      }
    }
  }

  public final PrefetchContext createPrefetchContext(SyncResult paramSyncResult, Thread paramThread)
  {
    return new PrefetchContext(paramSyncResult, paramThread);
  }

  public final CacheStats getCacheStatistics(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getReadableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(2);
    Cursor localCursor = localSQLiteDatabase.rawQuery(QUERY_CACHE_STATUS_COUNT, arrayOfString);
    CacheStats localCacheStats = new CacheStats();
    if (localCursor == null);
    while (true)
    {
      return localCacheStats;
      try
      {
        if (localCursor.moveToNext())
        {
          int i = localCursor.getInt(0);
          if (localCursor.getInt(1) != 0)
            localCacheStats.pendingCount = (i + localCacheStats.pendingCount);
          localCacheStats.totalCount = (i + localCacheStats.totalCount);
        }
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  public final void setAlbumCachingFlag(long paramLong, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("cache_flag", Integer.valueOf(paramInt));
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramLong);
      if (this.mDbHelper.getWritableDatabase().update(ALBUM_TABLE_NAME, localContentValues, "_id=?", arrayOfString) > 0)
      {
        this.mCacheConfigVersion.incrementAndGet();
        notifyAlbumsChange();
        PicasaSyncManager.get(this.mContext).requestPrefetchSync();
      }
    }
  }

  public final void syncAlbumCoversForUser(PrefetchContext paramPrefetchContext, UserEntry paramUserEntry)
    throws IOException
  {
    File localFile1 = new File(getCacheDirectory(), "picasa_covers");
    if ((!localFile1.isDirectory()) && (!localFile1.mkdirs()))
      Log.e("PrefetchHelper", "cannot create album-cover folder");
    while (true)
    {
      return;
      SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramUserEntry.id);
      Cursor localCursor = localSQLiteDatabase.query(ALBUM_TABLE_NAME, PROJECTION_ID_THUMBNAIL_URL, "user_id=?", arrayOfString, null, null, null);
      while (true)
      {
        long l1;
        String str;
        try
        {
          if (!localCursor.moveToNext())
            break label316;
          boolean bool = paramPrefetchContext.syncInterrupted();
          if (bool)
          {
            localCursor.close();
            break;
          }
          l1 = localCursor.getLong(0);
          str = localCursor.getString(1);
          localFile2 = getAlbumCoverCacheFile(l1, str, ".thumb");
          if (localFile2.isFile())
          {
            if (localFile2 != null)
              continue;
            long l2 = getAvailableStorage();
            if (l2 >= 1073741824L)
              break label226;
            throw new RuntimeException("space not enough: " + l2 + ", stop sync");
          }
        }
        finally
        {
          localCursor.close();
        }
        File localFile2 = null;
        continue;
        label226: File localFile3 = getAlbumCoverCacheFile(l1, str, ".download");
        if (!downloadPhoto(paramPrefetchContext, PicasaApi.convertImageUrl(str, Config.sThumbNailSize, true), localFile3))
        {
          localFile3.delete();
        }
        else if (!localFile3.renameTo(getAlbumCoverCacheFile(l1, str, ".thumb")))
        {
          Log.e("PrefetchHelper", "cannot rename file: " + localFile3);
          localFile3.delete();
        }
      }
      label316: localCursor.close();
    }
  }

  // ERROR //
  public final void syncFullImagesForUser(PrefetchContext paramPrefetchContext, UserEntry paramUserEntry)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 124	com/google/android/picasasync/PrefetchHelper:mDbHelper	Lcom/google/android/picasasync/PicasaDatabaseHelper;
    //   4: invokevirtual 585	com/google/android/picasasync/PicasaDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   7: astore_3
    //   8: iconst_2
    //   9: anewarray 49	java/lang/String
    //   12: astore 4
    //   14: aload 4
    //   16: iconst_0
    //   17: aload_2
    //   18: getfield 680	com/google/android/picasasync/UserEntry:id	J
    //   21: invokestatic 132	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   24: aastore
    //   25: aload 4
    //   27: iconst_1
    //   28: iconst_2
    //   29: invokestatic 639	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   32: aastore
    //   33: aload_3
    //   34: getstatic 42	com/google/android/picasasync/PrefetchHelper:ALBUM_TABLE_NAME	Ljava/lang/String;
    //   37: getstatic 53	com/google/android/picasasync/PrefetchHelper:PROJECTION_ID	[Ljava/lang/String;
    //   40: getstatic 81	com/google/android/picasasync/PrefetchHelper:WHERE_USER_ID_AND_CACHE_FLAG	Ljava/lang/String;
    //   43: aload 4
    //   45: aconst_null
    //   46: aconst_null
    //   47: aconst_null
    //   48: invokevirtual 140	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   51: astore 5
    //   53: aload 5
    //   55: invokeinterface 146 1 0
    //   60: ifeq +415 -> 475
    //   63: aload_1
    //   64: invokevirtual 234	com/google/android/picasasync/PrefetchHelper$PrefetchContext:syncInterrupted	()Z
    //   67: istore 7
    //   69: iload 7
    //   71: ifeq +11 -> 82
    //   74: aload 5
    //   76: invokeinterface 164 1 0
    //   81: return
    //   82: aload 5
    //   84: iconst_0
    //   85: invokeinterface 150 2 0
    //   90: lstore 8
    //   92: aload_0
    //   93: getfield 124	com/google/android/picasasync/PrefetchHelper:mDbHelper	Lcom/google/android/picasasync/PicasaDatabaseHelper;
    //   96: invokevirtual 585	com/google/android/picasasync/PicasaDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   99: astore 10
    //   101: iconst_2
    //   102: anewarray 49	java/lang/String
    //   105: astore 11
    //   107: aload 11
    //   109: iconst_0
    //   110: lload 8
    //   112: invokestatic 132	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   115: aastore
    //   116: aload 11
    //   118: iconst_1
    //   119: iconst_2
    //   120: invokestatic 639	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   123: aastore
    //   124: aload 10
    //   126: getstatic 47	com/google/android/picasasync/PrefetchHelper:PHOTO_TABLE_NAME	Ljava/lang/String;
    //   129: getstatic 63	com/google/android/picasasync/PrefetchHelper:PROJECTION_ID_ROTATION_CONTENT_URL_CONTENT_TYPE_SCREENNAIL_URL	[Ljava/lang/String;
    //   132: getstatic 85	com/google/android/picasasync/PrefetchHelper:WHERE_ALBUM_ID_AND_CACHE_STATUS	Ljava/lang/String;
    //   135: aload 11
    //   137: aconst_null
    //   138: aconst_null
    //   139: aconst_null
    //   140: invokevirtual 140	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   143: astore 12
    //   145: aload 12
    //   147: ifnull -94 -> 53
    //   150: aload 12
    //   152: invokeinterface 701 1 0
    //   157: istore 14
    //   159: iload 14
    //   161: ifne +25 -> 186
    //   164: aload 12
    //   166: invokeinterface 164 1 0
    //   171: goto -118 -> 53
    //   174: astore 6
    //   176: aload 5
    //   178: invokeinterface 164 1 0
    //   183: aload 6
    //   185: athrow
    //   186: aload_0
    //   187: aload 10
    //   189: lload 8
    //   191: iconst_1
    //   192: invokespecial 616	com/google/android/picasasync/PrefetchHelper:updateAlbumCacheStatus	(Landroid/database/sqlite/SQLiteDatabase;JI)V
    //   195: aload 12
    //   197: invokeinterface 146 1 0
    //   202: ifeq +254 -> 456
    //   205: aload_1
    //   206: invokevirtual 704	com/google/android/picasasync/PrefetchHelper$PrefetchContext:checkCacheConfigVersion	()Z
    //   209: ifne +16 -> 225
    //   212: ldc 200
    //   214: ldc_w 706
    //   217: invokestatic 220	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   220: pop
    //   221: aload_1
    //   222: invokevirtual 709	com/google/android/picasasync/PrefetchHelper$PrefetchContext:stopSync	()V
    //   225: aload_1
    //   226: invokevirtual 234	com/google/android/picasasync/PrefetchHelper$PrefetchContext:syncInterrupted	()Z
    //   229: istore 15
    //   231: iload 15
    //   233: ifeq +13 -> 246
    //   236: aload 12
    //   238: invokeinterface 164 1 0
    //   243: goto -190 -> 53
    //   246: aload 12
    //   248: iconst_0
    //   249: invokeinterface 150 2 0
    //   254: lstore 16
    //   256: aload 12
    //   258: iconst_1
    //   259: invokeinterface 604 2 0
    //   264: istore 18
    //   266: aload 12
    //   268: iconst_2
    //   269: invokeinterface 607 2 0
    //   274: astore 19
    //   276: aload 12
    //   278: iconst_3
    //   279: invokeinterface 607 2 0
    //   284: astore 20
    //   286: aload 12
    //   288: iconst_4
    //   289: invokeinterface 607 2 0
    //   294: astore 21
    //   296: new 170	java/io/File
    //   299: dup
    //   300: new 202	java/lang/StringBuilder
    //   303: dup
    //   304: invokespecial 464	java/lang/StringBuilder:<init>	()V
    //   307: lload 16
    //   309: invokevirtual 317	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   312: ldc_w 322
    //   315: invokevirtual 211	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   321: invokespecial 229	java/io/File:<init>	(Ljava/lang/String;)V
    //   324: invokevirtual 467	java/io/File:length	()J
    //   327: lconst_0
    //   328: lcmp
    //   329: ifne +49 -> 378
    //   332: aload_0
    //   333: aload_1
    //   334: lload 16
    //   336: aload 19
    //   338: ldc_w 322
    //   341: invokespecial 711	com/google/android/picasasync/PrefetchHelper:syncOnePhoto	(Lcom/google/android/picasasync/PrefetchHelper$PrefetchContext;JLjava/lang/String;Ljava/lang/String;)Z
    //   344: ifne +34 -> 378
    //   347: aload_1
    //   348: invokevirtual 714	com/google/android/picasasync/PrefetchHelper$PrefetchContext:getDownloadFailCount	()I
    //   351: iconst_3
    //   352: if_icmple +26 -> 378
    //   355: new 312	java/lang/RuntimeException
    //   358: dup
    //   359: ldc_w 716
    //   362: invokespecial 320	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   365: athrow
    //   366: astore 13
    //   368: aload 12
    //   370: invokeinterface 164 1 0
    //   375: aload 13
    //   377: athrow
    //   378: aload 20
    //   380: ldc_w 718
    //   383: invokevirtual 240	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   386: ifeq +14 -> 400
    //   389: aload_0
    //   390: lload 16
    //   392: iload 18
    //   394: invokespecial 720	com/google/android/picasasync/PrefetchHelper:generateScreennail	(JI)V
    //   397: goto -202 -> 195
    //   400: aload 20
    //   402: ldc_w 722
    //   405: invokevirtual 240	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   408: ifeq -213 -> 195
    //   411: aload_0
    //   412: aload_1
    //   413: lload 16
    //   415: aload 21
    //   417: ldc_w 330
    //   420: invokespecial 711	com/google/android/picasasync/PrefetchHelper:syncOnePhoto	(Lcom/google/android/picasasync/PrefetchHelper$PrefetchContext;JLjava/lang/String;Ljava/lang/String;)Z
    //   423: ifne -228 -> 195
    //   426: ldc 200
    //   428: new 202	java/lang/StringBuilder
    //   431: dup
    //   432: ldc_w 724
    //   435: invokespecial 207	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   438: aload 21
    //   440: invokestatic 300	com/android/gallery3d/common/Utils:maskDebugInfo	(Ljava/lang/Object;)Ljava/lang/String;
    //   443: invokevirtual 211	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   446: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   449: invokestatic 220	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   452: pop
    //   453: goto -258 -> 195
    //   456: aload_0
    //   457: aload 10
    //   459: lload 8
    //   461: iconst_3
    //   462: invokespecial 616	com/google/android/picasasync/PrefetchHelper:updateAlbumCacheStatus	(Landroid/database/sqlite/SQLiteDatabase;JI)V
    //   465: aload 12
    //   467: invokeinterface 164 1 0
    //   472: goto -419 -> 53
    //   475: aload 5
    //   477: invokeinterface 164 1 0
    //   482: goto -401 -> 81
    //
    // Exception table:
    //   from	to	target	type
    //   53	69	174	finally
    //   82	145	174	finally
    //   164	171	174	finally
    //   236	243	174	finally
    //   368	378	174	finally
    //   465	472	174	finally
    //   150	159	366	finally
    //   186	231	366	finally
    //   246	366	366	finally
    //   378	465	366	finally
  }

  public final void syncScreenNailsForUser(PrefetchContext paramPrefetchContext, UserEntry paramUserEntry)
    throws IOException
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = String.valueOf(1);
    arrayOfString[1] = String.valueOf(paramUserEntry.id);
    Cursor localCursor = this.mDbHelper.getWritableDatabase().query(PHOTO_TABLE_NAME, PROJECTION_ID_SCREENNAIL_URL, WHERE_CACHE_STATUS_AND_USER_ID, arrayOfString, null, null, "display_index");
    while (true)
    {
      try
      {
        if (localCursor.moveToNext())
        {
          if (!paramPrefetchContext.checkCacheConfigVersion())
          {
            Log.w("PrefetchHelper", "cache config has changed, stop sync");
            paramPrefetchContext.stopSync();
          }
          boolean bool = paramPrefetchContext.syncInterrupted();
          if (bool)
            return;
          if ((syncOnePhoto(paramPrefetchContext, localCursor.getLong(0), PicasaApi.convertImageUrl(localCursor.getString(1), Config.sScreenNailSize, false), ".screen")) || (paramPrefetchContext.getDownloadFailCount() <= 3))
            continue;
          throw new RuntimeException("too many fail downloads");
        }
      }
      finally
      {
        Utils.closeSilently(localCursor);
      }
      Utils.closeSilently(localCursor);
    }
  }

  public static final class CacheStats
  {
    public int pendingCount;
    public int totalCount;
  }

  public final class PrefetchContext
  {
    private PrefetchHelper.PrefetchListener mCacheListener;
    private int mDownloadFailCount;
    private int mLastVersion;
    private volatile boolean mStopSync;
    private Thread mThread;
    public SyncResult result;

    public PrefetchContext(SyncResult paramThread, Thread arg3)
    {
      this.result = ((SyncResult)Utils.checkNotNull(paramThread));
      Object localObject;
      this.mThread = localObject;
    }

    public final boolean checkCacheConfigVersion()
    {
      if (this.mLastVersion == PrefetchHelper.this.mCacheConfigVersion.get());
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final int getDownloadFailCount()
    {
      return this.mDownloadFailCount;
    }

    public final void onDownloadFinish(long paramLong, boolean paramBoolean)
    {
      if (paramBoolean);
      for (int i = 0; ; i = 1 + this.mDownloadFailCount)
      {
        this.mDownloadFailCount = i;
        if (this.mCacheListener != null)
          this.mCacheListener.onDownloadFinish$256a6c5();
        return;
      }
    }

    public final void setCacheDownloadListener(PrefetchHelper.PrefetchListener paramPrefetchListener)
    {
      this.mCacheListener = paramPrefetchListener;
    }

    public final void stopSync()
    {
      this.mStopSync = true;
      this.mThread.interrupt();
    }

    public final boolean syncInterrupted()
    {
      return this.mStopSync;
    }

    public final void updateCacheConfigVersion()
    {
      this.mLastVersion = PrefetchHelper.this.mCacheConfigVersion.get();
    }
  }

  public static abstract interface PrefetchListener
  {
    public abstract void onDownloadFinish$256a6c5();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.PrefetchHelper
 * JD-Core Version:    0.6.2
 */