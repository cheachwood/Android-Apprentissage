package com.google.android.apps.plus.content;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.util.Log;
import com.google.android.apps.plus.api.DownloadImageOperation;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageDownloader;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.ImageUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class EsMediaCache
{
  private static ImageCache sImageCache;
  private static long sMaxCacheSize;
  private static File sMediaCacheDir;

  public static void cleanup()
  {
    if (sMediaCacheDir == null);
    while (true)
    {
      return;
      File[] arrayOfFile1 = sMediaCacheDir.listFiles();
      if ((arrayOfFile1 != null) && (arrayOfFile1.length != 0))
      {
        long l1 = System.currentTimeMillis();
        ArrayList localArrayList = new ArrayList();
        long l2 = 0L;
        for (int i = 0; i < arrayOfFile1.length; i++)
        {
          File[] arrayOfFile2 = arrayOfFile1[i].listFiles();
          for (int n = 0; n < arrayOfFile2.length; n++)
          {
            MediaCacheFile localMediaCacheFile3 = new MediaCacheFile(arrayOfFile2[n], l1);
            localArrayList.add(localMediaCacheFile3);
            l2 += localMediaCacheFile3.size;
          }
        }
        if (sMaxCacheSize == 0L)
        {
          if (Build.VERSION.SDK_INT < 9)
            break label309;
          long l3 = ()(0.25D * (l2 + sMediaCacheDir.getUsableSpace()));
          sMaxCacheSize = l3;
          if (l3 < 5242880L)
            sMaxCacheSize = 5242880L;
          if (sMaxCacheSize > 104857600L)
            sMaxCacheSize = 104857600L;
        }
        label181: int j;
        int m;
        label220: MediaCacheFile localMediaCacheFile2;
        StringBuilder localStringBuilder;
        if (l2 > sMaxCacheSize)
        {
          Collections.sort(localArrayList);
          j = localArrayList.size();
          if (!EsLog.isLoggable("ResourceCache", 3))
            break label325;
          Log.d("ResourceCache", "Media cache");
          m = 0;
          if (m >= j)
            break label325;
          localMediaCacheFile2 = (MediaCacheFile)localArrayList.get(m);
          localStringBuilder = new StringBuilder();
          if (!localMediaCacheFile2.recent)
            break label318;
        }
        label309: label318: for (String str = "R "; ; str = "  ")
        {
          Log.d("ResourceCache", str + (l1 - localMediaCacheFile2.timestamp) + " ms, " + localMediaCacheFile2.size + " bytes");
          m++;
          break label220;
          sMaxCacheSize = 15728640L;
          break label181;
          break;
        }
        label325: for (int k = 0; (k < j) && (l2 > sMaxCacheSize); k++)
        {
          MediaCacheFile localMediaCacheFile1 = (MediaCacheFile)localArrayList.get(k);
          if (localMediaCacheFile1.file.delete())
            l2 -= localMediaCacheFile1.size;
        }
      }
    }
  }

  public static Bitmap cropPhoto$5d96f1cd(Bitmap paramBitmap, float paramFloat1, float paramFloat2)
  {
    float f1;
    float f2;
    int j;
    int i;
    Bitmap localBitmap2;
    float f3;
    float f4;
    if (paramBitmap != null)
    {
      f1 = paramBitmap.getWidth();
      f2 = paramBitmap.getHeight();
      if (f1 / f2 > paramFloat1 / paramFloat2)
      {
        j = (int)(f1 * paramFloat2 / f2);
        i = (int)paramFloat2;
        localBitmap2 = Bitmap.createScaledBitmap(paramBitmap, j, i, true);
        f3 = localBitmap2.getWidth() - paramFloat1;
        f4 = localBitmap2.getHeight() - paramFloat2;
      }
    }
    for (Bitmap localBitmap1 = Bitmap.createBitmap(localBitmap2, (int)(f3 / 2.0F), (int)(f4 / 2.0F), (int)paramFloat1, (int)paramFloat2); ; localBitmap1 = null)
    {
      return localBitmap1;
      i = (int)(f2 * paramFloat1 / f1);
      j = (int)paramFloat1;
      break;
    }
  }

  public static boolean exists(Context paramContext, String paramString1, String paramString2)
  {
    return getMediaCacheFile(paramContext, paramString1, paramString2).exists();
  }

  public static byte[] getMedia(Context paramContext, CachedImageRequest paramCachedImageRequest)
  {
    return read(paramContext, paramCachedImageRequest.getCacheDir(), paramCachedImageRequest.getCacheFileName());
  }

  private static File getMediaCacheDir(Context paramContext)
  {
    if (sMediaCacheDir == null)
      sMediaCacheDir = new File(paramContext.getCacheDir(), "media");
    if (!sMediaCacheDir.exists());
    try
    {
      sMediaCacheDir.mkdir();
      return sMediaCacheDir;
    }
    catch (Exception localException)
    {
      while (true)
        Log.e("ResourceCache", "Cannot create cache media directory: " + sMediaCacheDir, localException);
    }
  }

  public static File getMediaCacheFile(Context paramContext, String paramString1, String paramString2)
  {
    return new File(new File(getMediaCacheDir(paramContext), paramString1), paramString2);
  }

  public static void insertMedia(Context paramContext, CachedImageRequest paramCachedImageRequest, byte[] paramArrayOfByte)
  {
    if (sImageCache == null)
      sImageCache = ImageCache.getInstance(paramContext);
    write(paramContext, paramCachedImageRequest.getCacheDir(), paramCachedImageRequest.getCacheFileName(), paramArrayOfByte);
    sImageCache.notifyMediaImageChange(paramCachedImageRequest, paramArrayOfByte);
  }

  public static Map<CachedImageRequest, byte[]> loadMedia(Context paramContext, ArrayList<CachedImageRequest> paramArrayList)
  {
    HashMap localHashMap = new HashMap();
    EsAccount localEsAccount = EsAccountsData.getActiveAccount(paramContext);
    if (localEsAccount == null);
    while (true)
    {
      return localHashMap;
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        CachedImageRequest localCachedImageRequest = (CachedImageRequest)localIterator.next();
        byte[] arrayOfByte = getMedia(paramContext, localCachedImageRequest);
        if (arrayOfByte != null)
          localHashMap.put(localCachedImageRequest, arrayOfByte);
        else
          ImageDownloader.downloadImage(paramContext, localEsAccount, localCachedImageRequest);
      }
    }
  }

  public static Bitmap obtainAvatar(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean)
  {
    Bitmap localBitmap1 = obtainImage(paramContext, paramEsAccount, new AvatarImageRequest(paramString1, paramString2, 2, EsAvatarData.getMediumAvatarSize(paramContext)));
    Bitmap localBitmap2;
    if ((localBitmap1 != null) && (paramBoolean))
    {
      localBitmap2 = ImageUtils.getRoundedBitmap(paramContext, localBitmap1);
      localBitmap1.recycle();
    }
    while (true)
    {
      return localBitmap2;
      localBitmap2 = localBitmap1;
    }
  }

  public static Bitmap obtainAvatarForMultipleUsers(List<Bitmap> paramList)
  {
    if ((paramList == null) || (paramList.isEmpty()));
    int i;
    int j;
    int k;
    Bitmap localBitmap1;
    Canvas localCanvas;
    Paint localPaint;
    for (Object localObject = null; ; localObject = (Bitmap)paramList.get(0))
    {
      return localObject;
      i = paramList.size();
      j = ((Bitmap)paramList.get(0)).getWidth();
      k = ((Bitmap)paramList.get(0)).getHeight();
      localBitmap1 = Bitmap.createBitmap(j, k, Bitmap.Config.ARGB_8888);
      localCanvas = new Canvas(localBitmap1);
      localPaint = new Paint();
      localPaint.setColor(-16777216);
      localPaint.setStrokeWidth(2.0F);
      if (i != 1)
        break;
    }
    if (i == 2)
    {
      Bitmap localBitmap9 = obtainAvatarWithHalfHeight((Bitmap)paramList.get(0));
      Bitmap localBitmap10 = obtainAvatarWithHalfHeight((Bitmap)paramList.get(1));
      localCanvas.drawBitmap(localBitmap9, 0.0F, 0.0F, localPaint);
      localCanvas.drawBitmap(localBitmap10, 0.0F, k / 2, localPaint);
      localCanvas.drawLine(0.0F, k / 2, j, k / 2, localPaint);
    }
    while (true)
    {
      localObject = localBitmap1;
      break;
      if (i == 3)
      {
        Bitmap localBitmap6 = obtainAvatarWithHalfHeight((Bitmap)paramList.get(0));
        Bitmap localBitmap7 = obtainAvatarWithHalfHeightAndHalfWidth((Bitmap)paramList.get(1));
        Bitmap localBitmap8 = obtainAvatarWithHalfHeightAndHalfWidth((Bitmap)paramList.get(2));
        localCanvas.drawBitmap(localBitmap6, 0.0F, 0.0F, localPaint);
        localCanvas.drawBitmap(localBitmap7, 0.0F, k / 2, localPaint);
        localCanvas.drawBitmap(localBitmap8, j / 2, k / 2, localPaint);
        localCanvas.drawLine(j / 2, k / 2, j / 2, k, localPaint);
        localCanvas.drawLine(0.0F, k / 2, j, k / 2, localPaint);
      }
      else if (i >= 4)
      {
        Bitmap localBitmap2 = obtainAvatarWithHalfHeightAndHalfWidth((Bitmap)paramList.get(0));
        Bitmap localBitmap3 = obtainAvatarWithHalfHeightAndHalfWidth((Bitmap)paramList.get(1));
        Bitmap localBitmap4 = obtainAvatarWithHalfHeightAndHalfWidth((Bitmap)paramList.get(2));
        Bitmap localBitmap5 = obtainAvatarWithHalfHeightAndHalfWidth((Bitmap)paramList.get(3));
        localCanvas.drawBitmap(localBitmap2, 0.0F, 0.0F, localPaint);
        localCanvas.drawBitmap(localBitmap3, j / 2, 0.0F, localPaint);
        localCanvas.drawBitmap(localBitmap4, 0.0F, k / 2, localPaint);
        localCanvas.drawBitmap(localBitmap5, j / 2, k / 2, localPaint);
        localCanvas.drawLine(j / 2, 0.0F, j / 2, k, localPaint);
        localCanvas.drawLine(0.0F, k / 2, j, k / 2, localPaint);
      }
    }
  }

  private static Bitmap obtainAvatarWithHalfHeight(Bitmap paramBitmap)
  {
    if (paramBitmap == null);
    int i;
    int j;
    for (Bitmap localBitmap = null; ; localBitmap = Bitmap.createBitmap(paramBitmap, 0, j / 4, i, j / 2))
    {
      return localBitmap;
      i = paramBitmap.getWidth();
      j = paramBitmap.getHeight();
    }
  }

  private static Bitmap obtainAvatarWithHalfHeightAndHalfWidth(Bitmap paramBitmap)
  {
    if (paramBitmap == null);
    int i;
    int j;
    for (Bitmap localBitmap = null; ; localBitmap = Bitmap.createScaledBitmap(paramBitmap, i / 2, j / 2, false))
    {
      return localBitmap;
      i = paramBitmap.getWidth();
      j = paramBitmap.getHeight();
    }
  }

  public static Bitmap obtainImage(Context paramContext, EsAccount paramEsAccount, CachedImageRequest paramCachedImageRequest)
  {
    byte[] arrayOfByte = getMedia(paramContext, paramCachedImageRequest);
    if (arrayOfByte == null)
    {
      DownloadImageOperation localDownloadImageOperation = new DownloadImageOperation(paramContext, paramEsAccount, paramCachedImageRequest, null, null);
      localDownloadImageOperation.start();
      arrayOfByte = localDownloadImageOperation.getImageBytes();
    }
    Bitmap localBitmap = null;
    if (arrayOfByte != null)
      localBitmap = ImageUtils.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
    return localBitmap;
  }

  // ERROR //
  public static byte[] read(Context paramContext, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokestatic 151	com/google/android/apps/plus/content/EsMediaCache:getMediaCacheFile	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   6: astore_3
    //   7: aconst_null
    //   8: astore 4
    //   10: invokestatic 26	java/lang/System:currentTimeMillis	()J
    //   13: lstore 14
    //   15: lload 14
    //   17: aload_3
    //   18: invokevirtual 353	java/io/File:lastModified	()J
    //   21: lsub
    //   22: ldc2_w 354
    //   25: lcmp
    //   26: istore 16
    //   28: aconst_null
    //   29: astore 4
    //   31: iload 16
    //   33: ifle +10 -> 43
    //   36: aload_3
    //   37: lload 14
    //   39: invokevirtual 359	java/io/File:setLastModified	(J)Z
    //   42: pop
    //   43: new 361	java/io/FileInputStream
    //   46: dup
    //   47: aload_3
    //   48: invokespecial 364	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   51: astore 17
    //   53: aload_3
    //   54: invokevirtual 367	java/io/File:length	()J
    //   57: l2i
    //   58: istore 19
    //   60: iload 19
    //   62: newarray byte
    //   64: astore 7
    //   66: iconst_0
    //   67: istore 20
    //   69: iload 19
    //   71: istore 21
    //   73: iload 21
    //   75: ifle +68 -> 143
    //   78: aload 17
    //   80: aload 7
    //   82: iload 20
    //   84: iload 21
    //   86: invokevirtual 370	java/io/FileInputStream:read	([BII)I
    //   89: istore 23
    //   91: iload 23
    //   93: ifge +33 -> 126
    //   96: new 350	java/io/IOException
    //   99: dup
    //   100: invokespecial 371	java/io/IOException:<init>	()V
    //   103: athrow
    //   104: astore 18
    //   106: aload 17
    //   108: astore 6
    //   110: aload 6
    //   112: ifnull +8 -> 120
    //   115: aload 6
    //   117: invokevirtual 374	java/io/FileInputStream:close	()V
    //   120: aconst_null
    //   121: astore 7
    //   123: aload 7
    //   125: areturn
    //   126: iload 20
    //   128: iload 23
    //   130: iadd
    //   131: istore 20
    //   133: iload 21
    //   135: iload 23
    //   137: isub
    //   138: istore 21
    //   140: goto -67 -> 73
    //   143: aload 17
    //   145: invokevirtual 374	java/io/FileInputStream:close	()V
    //   148: goto -25 -> 123
    //   151: astore 11
    //   153: ldc 71
    //   155: new 91	java/lang/StringBuilder
    //   158: dup
    //   159: ldc_w 376
    //   162: invokespecial 189	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   165: aload_3
    //   166: invokevirtual 379	java/io/File:getPath	()Ljava/lang/String;
    //   169: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   175: aload 11
    //   177: invokestatic 196	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   180: pop
    //   181: aload 4
    //   183: ifnull +8 -> 191
    //   186: aload 4
    //   188: invokevirtual 374	java/io/FileInputStream:close	()V
    //   191: aconst_null
    //   192: astore 7
    //   194: goto -71 -> 123
    //   197: astore 9
    //   199: aload 4
    //   201: ifnull +8 -> 209
    //   204: aload 4
    //   206: invokevirtual 374	java/io/FileInputStream:close	()V
    //   209: aload 9
    //   211: athrow
    //   212: astore 22
    //   214: goto -66 -> 148
    //   217: astore 8
    //   219: goto -99 -> 120
    //   222: astore 13
    //   224: goto -33 -> 191
    //   227: astore 10
    //   229: goto -20 -> 209
    //   232: astore 9
    //   234: aload 17
    //   236: astore 4
    //   238: goto -39 -> 199
    //   241: astore 11
    //   243: aload 17
    //   245: astore 4
    //   247: goto -94 -> 153
    //   250: astore 5
    //   252: aconst_null
    //   253: astore 6
    //   255: goto -145 -> 110
    //
    // Exception table:
    //   from	to	target	type
    //   53	104	104	java/io/FileNotFoundException
    //   10	53	151	java/io/IOException
    //   10	53	197	finally
    //   153	181	197	finally
    //   143	148	212	java/io/IOException
    //   115	120	217	java/io/IOException
    //   186	191	222	java/io/IOException
    //   204	209	227	java/io/IOException
    //   53	104	232	finally
    //   53	104	241	java/io/IOException
    //   10	53	250	java/io/FileNotFoundException
  }

  public static void write(Context paramContext, String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    File localFile1 = new File(getMediaCacheDir(paramContext), paramString1);
    if (!localFile1.exists());
    try
    {
      localFile1.mkdir();
      localFile2 = new File(localFile1, paramString2);
      if (paramArrayOfByte == null);
    }
    catch (Exception localException)
    {
      while (true)
      {
        File localFile2;
        try
        {
          FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
          localFileOutputStream.write(paramArrayOfByte);
          localFileOutputStream.close();
          return;
          localException = localException;
          Log.e("ResourceCache", "Cannot create cache directory: " + localFile1, localException);
        }
        catch (IOException localIOException)
        {
          Log.e("ResourceCache", "Cannot write file to cache: " + localFile2.getPath(), localIOException);
          continue;
        }
        localFile2.delete();
      }
    }
  }

  private static final class MediaCacheFile
    implements Comparable<MediaCacheFile>
  {
    File file;
    boolean recent;
    long size;
    long timestamp;

    public MediaCacheFile(File paramFile, long paramLong)
    {
      this.file = paramFile;
      this.timestamp = paramFile.lastModified();
      this.size = paramFile.length();
      if (paramLong - this.timestamp < 1800000L);
      for (boolean bool = true; ; bool = false)
      {
        this.recent = bool;
        return;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsMediaCache
 * JD-Core Version:    0.6.2
 */