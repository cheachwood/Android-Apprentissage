package com.google.android.apps.plus.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.phone.EsApplication;
import com.google.android.apps.plus.phone.FIFEUtil;
import com.google.android.apps.plus.phone.ImageProxyUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public final class ImageUtils
{
  private static final boolean USE_LOW_RES_IMAGES;
  private static Bitmap sAvatarOverlayBitmap;
  private static final Rect sAvatarOverlayRect;
  private static final Paint sCropPaint;
  private static final Paint sMaskPaint;
  private static int sMicroKindMaxDimension = 0;
  private static int sMiniKindMaxDimension = 0;
  private static final Paint sResizePaint;
  private static Bitmap sRoundMask;
  private static ArrayList<RoundMask> sRoundMasks;

  static
  {
    sAvatarOverlayRect = new Rect();
    sResizePaint = new Paint(2);
    Paint localPaint1 = new Paint();
    sCropPaint = localPaint1;
    localPaint1.setAntiAlias(true);
    sCropPaint.setFilterBitmap(true);
    sCropPaint.setDither(true);
    sRoundMasks = new ArrayList();
    Paint localPaint2 = new Paint(1);
    sMaskPaint = localPaint2;
    localPaint2.setColor(-16777216);
    sMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    if (Build.VERSION.SDK_INT >= 11)
      USE_LOW_RES_IMAGES = false;
    while (true)
    {
      return;
      if (EsApplication.sMemoryClass >= 24L)
        USE_LOW_RES_IMAGES = false;
      else
        USE_LOW_RES_IMAGES = true;
    }
  }

  public static byte[] compressBitmap(Bitmap paramBitmap)
  {
    return compressBitmap(paramBitmap, 90, true);
  }

  // ERROR //
  public static byte[] compressBitmap(Bitmap paramBitmap, int paramInt, boolean paramBoolean)
  {
    // Byte code:
    //   0: new 103	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: invokespecial 104	java/io/ByteArrayOutputStream:<init>	()V
    //   7: astore_3
    //   8: aload_0
    //   9: getstatic 110	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   12: iload_1
    //   13: aload_3
    //   14: invokevirtual 116	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   17: pop
    //   18: aload_3
    //   19: invokevirtual 119	java/io/ByteArrayOutputStream:flush	()V
    //   22: aload_3
    //   23: invokevirtual 122	java/io/ByteArrayOutputStream:close	()V
    //   26: iload_2
    //   27: ifeq +7 -> 34
    //   30: aload_0
    //   31: invokevirtual 125	android/graphics/Bitmap:recycle	()V
    //   34: aload_3
    //   35: invokevirtual 129	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   38: astore 8
    //   40: ldc 131
    //   42: iconst_3
    //   43: invokestatic 137	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   46: ifeq +27 -> 73
    //   49: ldc 131
    //   51: new 139	java/lang/StringBuilder
    //   54: dup
    //   55: ldc 141
    //   57: invokespecial 144	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   60: aload 8
    //   62: arraylength
    //   63: invokevirtual 148	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   66: invokevirtual 152	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   69: invokestatic 158	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   72: pop
    //   73: aload 8
    //   75: areturn
    //   76: astore 6
    //   78: aload_3
    //   79: invokevirtual 122	java/io/ByteArrayOutputStream:close	()V
    //   82: goto -56 -> 26
    //   85: astore 7
    //   87: goto -61 -> 26
    //   90: astore 4
    //   92: aload_3
    //   93: invokevirtual 122	java/io/ByteArrayOutputStream:close	()V
    //   96: aload 4
    //   98: athrow
    //   99: astore 11
    //   101: goto -75 -> 26
    //   104: astore 5
    //   106: goto -10 -> 96
    //
    // Exception table:
    //   from	to	target	type
    //   8	22	76	java/io/IOException
    //   78	82	85	java/io/IOException
    //   8	22	90	finally
    //   22	26	99	java/io/IOException
    //   92	96	104	java/io/IOException
  }

  public static Dialog createInsertCameraPhotoDialog(Context paramContext)
  {
    ProgressDialog localProgressDialog = new ProgressDialog(paramContext);
    localProgressDialog.setProgressStyle(0);
    localProgressDialog.setCancelable(false);
    localProgressDialog.setMessage(paramContext.getString(R.string.dialog_inserting_camera_photo));
    return localProgressDialog;
  }

  // ERROR //
  public static Bitmap createLocalBitmap(ContentResolver paramContentResolver, Uri paramUri, int paramInt)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: iload_2
    //   6: ifle +45 -> 51
    //   9: aload_0
    //   10: aload_1
    //   11: invokestatic 192	com/google/android/apps/plus/util/ImageUtils:getImageBounds	(Landroid/content/ContentResolver;Landroid/net/Uri;)Landroid/graphics/Point;
    //   14: astore 9
    //   16: new 194	android/graphics/BitmapFactory$Options
    //   19: dup
    //   20: invokespecial 195	android/graphics/BitmapFactory$Options:<init>	()V
    //   23: astore 10
    //   25: aload 10
    //   27: aload 9
    //   29: getfield 200	android/graphics/Point:x	I
    //   32: iload_2
    //   33: idiv
    //   34: aload 9
    //   36: getfield 203	android/graphics/Point:y	I
    //   39: iload_2
    //   40: idiv
    //   41: invokestatic 209	java/lang/Math:max	(II)I
    //   44: putfield 212	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   47: aload 10
    //   49: astore 4
    //   51: aload_0
    //   52: aload_1
    //   53: invokevirtual 218	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   56: astore_3
    //   57: aload_0
    //   58: aload_1
    //   59: aload_3
    //   60: aconst_null
    //   61: aload 4
    //   63: invokestatic 222	com/google/android/apps/plus/util/ImageUtils:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   66: invokestatic 226	com/google/android/apps/plus/util/ImageUtils:rotateBitmap	(Landroid/content/ContentResolver;Landroid/net/Uri;Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;
    //   69: astore 7
    //   71: aload_3
    //   72: ifnull +7 -> 79
    //   75: aload_3
    //   76: invokevirtual 229	java/io/InputStream:close	()V
    //   79: aload 7
    //   81: areturn
    //   82: astore 5
    //   84: aload_3
    //   85: ifnull +7 -> 92
    //   88: aload_3
    //   89: invokevirtual 229	java/io/InputStream:close	()V
    //   92: aload 5
    //   94: athrow
    //   95: astore 8
    //   97: goto -18 -> 79
    //   100: astore 6
    //   102: goto -10 -> 92
    //   105: astore 5
    //   107: aconst_null
    //   108: astore_3
    //   109: goto -25 -> 84
    //
    // Exception table:
    //   from	to	target	type
    //   9	25	82	finally
    //   51	71	82	finally
    //   75	79	95	java/io/IOException
    //   88	92	100	java/io/IOException
    //   25	47	105	finally
  }

  // ERROR //
  public static Bitmap createRotatedBitmap(Context paramContext, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aload_1
    //   4: ifnull +12 -> 16
    //   7: aload_1
    //   8: arraylength
    //   9: istore 6
    //   11: iload 6
    //   13: ifne +12 -> 25
    //   16: aconst_null
    //   17: astore 4
    //   19: ldc 2
    //   21: monitorexit
    //   22: aload 4
    //   24: areturn
    //   25: aconst_null
    //   26: astore 7
    //   28: getstatic 87	com/google/android/apps/plus/util/ImageUtils:USE_LOW_RES_IMAGES	Z
    //   31: istore 8
    //   33: new 194	android/graphics/BitmapFactory$Options
    //   36: dup
    //   37: invokespecial 195	android/graphics/BitmapFactory$Options:<init>	()V
    //   40: astore 9
    //   42: aload_1
    //   43: invokestatic 236	com/google/android/apps/plus/util/ImageUtils:getImageBounds	([B)Landroid/graphics/Point;
    //   46: astore 18
    //   48: ldc 131
    //   50: iconst_3
    //   51: invokestatic 137	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   54: istore 19
    //   56: aconst_null
    //   57: astore 7
    //   59: iload 19
    //   61: ifeq +51 -> 112
    //   64: ldc 131
    //   66: new 139	java/lang/StringBuilder
    //   69: dup
    //   70: ldc 238
    //   72: invokespecial 144	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   75: aload 18
    //   77: getfield 200	android/graphics/Point:x	I
    //   80: invokevirtual 148	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   83: ldc 240
    //   85: invokevirtual 243	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: aload 18
    //   90: getfield 203	android/graphics/Point:y	I
    //   93: invokevirtual 148	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   96: ldc 245
    //   98: invokevirtual 243	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   101: iload_2
    //   102: invokevirtual 148	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   105: invokevirtual 152	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   108: invokestatic 158	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   111: pop
    //   112: aload 9
    //   114: aload 18
    //   116: getfield 200	android/graphics/Point:x	I
    //   119: iload_2
    //   120: idiv
    //   121: aload 18
    //   123: getfield 203	android/graphics/Point:y	I
    //   126: iload_3
    //   127: idiv
    //   128: invokestatic 209	java/lang/Math:max	(II)I
    //   131: putfield 212	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   134: aconst_null
    //   135: astore 7
    //   137: iload 8
    //   139: ifeq +11 -> 150
    //   142: aload 9
    //   144: getstatic 251	android/graphics/Bitmap$Config:RGB_565	Landroid/graphics/Bitmap$Config;
    //   147: putfield 254	android/graphics/BitmapFactory$Options:inPreferredConfig	Landroid/graphics/Bitmap$Config;
    //   150: aload_1
    //   151: iconst_0
    //   152: aload_1
    //   153: arraylength
    //   154: aload 9
    //   156: invokestatic 260	android/graphics/BitmapFactory:decodeByteArray	([BIILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   159: astore 4
    //   161: aconst_null
    //   162: astore 7
    //   164: aload 4
    //   166: ifnonnull +9 -> 175
    //   169: aconst_null
    //   170: astore 4
    //   172: goto -153 -> 19
    //   175: new 262	java/io/File
    //   178: dup
    //   179: aload_0
    //   180: invokevirtual 266	android/content/Context:getFilesDir	()Ljava/io/File;
    //   183: ldc_w 268
    //   186: invokespecial 271	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   189: astore 20
    //   191: new 273	java/io/FileOutputStream
    //   194: dup
    //   195: aload 20
    //   197: invokespecial 276	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   200: astore 21
    //   202: aload 21
    //   204: aload_1
    //   205: invokevirtual 280	java/io/FileOutputStream:write	([B)V
    //   208: aload 21
    //   210: invokevirtual 281	java/io/FileOutputStream:flush	()V
    //   213: aload 21
    //   215: invokevirtual 282	java/io/FileOutputStream:close	()V
    //   218: aload 20
    //   220: invokevirtual 285	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   223: invokestatic 289	com/google/android/apps/plus/util/ImageUtils:getExifRotation	(Ljava/lang/String;)I
    //   226: istore 22
    //   228: iload 22
    //   230: ifeq +33 -> 263
    //   233: aload 4
    //   235: iload 22
    //   237: invokestatic 292	com/google/android/apps/plus/util/ImageUtils:rotateBitmap	(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;
    //   240: astore 24
    //   242: aload 24
    //   244: astore 4
    //   246: aload 20
    //   248: invokevirtual 296	java/io/File:delete	()Z
    //   251: pop
    //   252: goto -233 -> 19
    //   255: astore 5
    //   257: ldc 2
    //   259: monitorexit
    //   260: aload 5
    //   262: athrow
    //   263: aload 20
    //   265: invokevirtual 296	java/io/File:delete	()Z
    //   268: pop
    //   269: goto -250 -> 19
    //   272: astore 15
    //   274: ldc 131
    //   276: ldc_w 298
    //   279: aload 15
    //   281: invokestatic 302	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   284: pop
    //   285: aload 7
    //   287: ifnull +9 -> 296
    //   290: aload 7
    //   292: invokevirtual 296	java/io/File:delete	()Z
    //   295: pop
    //   296: aconst_null
    //   297: astore 4
    //   299: goto -280 -> 19
    //   302: astore 12
    //   304: ldc 131
    //   306: ldc_w 304
    //   309: aload 12
    //   311: invokestatic 302	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   314: pop
    //   315: aload 7
    //   317: ifnull -21 -> 296
    //   320: aload 7
    //   322: invokevirtual 296	java/io/File:delete	()Z
    //   325: pop
    //   326: goto -30 -> 296
    //   329: aload 7
    //   331: ifnull +9 -> 340
    //   334: aload 7
    //   336: invokevirtual 296	java/io/File:delete	()Z
    //   339: pop
    //   340: aload 10
    //   342: athrow
    //   343: astore 10
    //   345: aload 20
    //   347: astore 7
    //   349: goto -20 -> 329
    //   352: astore 12
    //   354: aload 20
    //   356: astore 7
    //   358: goto -54 -> 304
    //   361: astore 15
    //   363: aload 20
    //   365: astore 7
    //   367: goto -93 -> 274
    //   370: astore 10
    //   372: goto -43 -> 329
    //
    // Exception table:
    //   from	to	target	type
    //   7	11	255	finally
    //   28	33	255	finally
    //   246	252	255	finally
    //   263	269	255	finally
    //   290	296	255	finally
    //   320	343	255	finally
    //   33	191	272	java/io/IOException
    //   33	191	302	java/lang/OutOfMemoryError
    //   191	242	343	finally
    //   191	242	352	java/lang/OutOfMemoryError
    //   191	242	361	java/io/IOException
    //   33	191	370	finally
    //   274	285	370	finally
    //   304	315	370	finally
  }

  // ERROR //
  public static Bitmap createVideoThumbnail(Context paramContext, Uri paramUri, int paramInt)
  {
    // Byte code:
    //   0: new 312	android/media/MediaMetadataRetriever
    //   3: dup
    //   4: invokespecial 313	android/media/MediaMetadataRetriever:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: aload_0
    //   10: aload_1
    //   11: invokevirtual 317	android/media/MediaMetadataRetriever:setDataSource	(Landroid/content/Context;Landroid/net/Uri;)V
    //   14: aload_3
    //   15: ldc2_w 318
    //   18: invokevirtual 323	android/media/MediaMetadataRetriever:getFrameAtTime	(J)Landroid/graphics/Bitmap;
    //   21: astore 13
    //   23: aload 13
    //   25: astore 8
    //   27: aload_3
    //   28: invokevirtual 326	android/media/MediaMetadataRetriever:release	()V
    //   31: aload 8
    //   33: ifnonnull +58 -> 91
    //   36: aconst_null
    //   37: astore 10
    //   39: aload 10
    //   41: areturn
    //   42: astore 11
    //   44: aload_3
    //   45: invokevirtual 326	android/media/MediaMetadataRetriever:release	()V
    //   48: aconst_null
    //   49: astore 8
    //   51: goto -20 -> 31
    //   54: astore 12
    //   56: aconst_null
    //   57: astore 8
    //   59: goto -28 -> 31
    //   62: astore 6
    //   64: aload_3
    //   65: invokevirtual 326	android/media/MediaMetadataRetriever:release	()V
    //   68: aconst_null
    //   69: astore 8
    //   71: goto -40 -> 31
    //   74: astore 7
    //   76: aconst_null
    //   77: astore 8
    //   79: goto -48 -> 31
    //   82: astore 4
    //   84: aload_3
    //   85: invokevirtual 326	android/media/MediaMetadataRetriever:release	()V
    //   88: aload 4
    //   90: athrow
    //   91: aload_0
    //   92: iload_2
    //   93: invokestatic 330	com/google/android/apps/plus/util/ImageUtils:getThumbnailSize	(Landroid/content/Context;I)I
    //   96: istore 9
    //   98: aload 8
    //   100: iload 9
    //   102: iload 9
    //   104: iconst_2
    //   105: invokestatic 336	android/media/ThumbnailUtils:extractThumbnail	(Landroid/graphics/Bitmap;III)Landroid/graphics/Bitmap;
    //   108: astore 10
    //   110: goto -71 -> 39
    //   113: astore 14
    //   115: goto -84 -> 31
    //   118: astore 5
    //   120: goto -32 -> 88
    //
    // Exception table:
    //   from	to	target	type
    //   8	23	42	java/lang/IllegalArgumentException
    //   44	48	54	java/lang/RuntimeException
    //   8	23	62	java/lang/RuntimeException
    //   64	68	74	java/lang/RuntimeException
    //   8	23	82	finally
    //   27	31	113	java/lang/RuntimeException
    //   84	88	118	java/lang/RuntimeException
  }

  private static Bitmap decodeAndScaleBitmap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Bitmap localBitmap2;
    if (paramArrayOfByte == null)
      localBitmap2 = null;
    while (true)
    {
      return localBitmap2;
      BitmapFactory.Options localOptions1 = new BitmapFactory.Options();
      localOptions1.inJustDecodeBounds = true;
      decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length, localOptions1);
      int i = localOptions1.outWidth;
      int j = localOptions1.outHeight;
      if (EsLog.isLoggable("ImageUtils", 3))
        Log.d("ImageUtils", "resizeToSquareBitmap: Input: " + i + "x" + j + ", resize to: " + paramInt1);
      int k = Math.min(i / paramInt1, j / paramInt1);
      BitmapFactory.Options localOptions2;
      if (k > 1)
      {
        localOptions2 = new BitmapFactory.Options();
        localOptions2.inSampleSize = k;
      }
      for (Bitmap localBitmap1 = decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length, localOptions2); ; localBitmap1 = decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length))
      {
        if (localBitmap1 != null)
          break label167;
        localBitmap2 = null;
        break;
      }
      label167: localBitmap2 = resizeToSquareBitmap(localBitmap1, paramInt1, paramInt2);
      localBitmap1.recycle();
      if (localBitmap2 == null)
        localBitmap2 = null;
    }
  }

  public static Bitmap decodeByteArray(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      Bitmap localBitmap2 = BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramInt2);
      localBitmap1 = localBitmap2;
      return localBitmap1;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
      {
        Log.e("ImageUtils", "ImageUtils#decodeByteArray(byte[], int, int) threw an OOME", localOutOfMemoryError);
        Bitmap localBitmap1 = null;
      }
    }
  }

  private static Bitmap decodeByteArray(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BitmapFactory.Options paramOptions)
  {
    try
    {
      Bitmap localBitmap2 = BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramInt2, paramOptions);
      localBitmap1 = localBitmap2;
      return localBitmap1;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
      {
        Log.e("ImageUtils", "ImageUtils#decodeByteArray(byte[], int, int, Options) threw an OOME", localOutOfMemoryError);
        Bitmap localBitmap1 = null;
      }
    }
  }

  public static Object decodeMedia(byte[] paramArrayOfByte)
  {
    Object localObject;
    try
    {
      if (GifImage.isGif(paramArrayOfByte))
      {
        localObject = new GifDrawable(new GifImage(paramArrayOfByte));
      }
      else
      {
        Bitmap localBitmap = BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
        localObject = localBitmap;
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      Log.e("ImageUtils", "ImageUtils#decodeMedia(byte[]) threw an OOME", localOutOfMemoryError);
      localObject = null;
    }
    return localObject;
  }

  public static Bitmap decodeResource(Resources paramResources, int paramInt)
  {
    try
    {
      Bitmap localBitmap2 = BitmapFactory.decodeResource(paramResources, paramInt);
      localBitmap1 = localBitmap2;
      return localBitmap1;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
      {
        Log.e("ImageUtils", "ImageUtils#decodeResource(Resources, int) threw an OOME", localOutOfMemoryError);
        Bitmap localBitmap1 = null;
      }
    }
  }

  private static Bitmap decodeStream(InputStream paramInputStream, Rect paramRect, BitmapFactory.Options paramOptions)
  {
    try
    {
      Bitmap localBitmap2 = BitmapFactory.decodeStream(paramInputStream, null, paramOptions);
      localBitmap1 = localBitmap2;
      return localBitmap1;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
      {
        Log.e("ImageUtils", "ImageUtils#decodeStream(InputStream, Rect, Options) threw an OOME", localOutOfMemoryError);
        Bitmap localBitmap1 = null;
      }
    }
  }

  public static String getCenterCroppedAndResizedUrl(int paramInt1, int paramInt2, String paramString)
  {
    String str;
    if (paramString == null)
      str = null;
    while (true)
    {
      return str;
      if (FIFEUtil.isFifeHostedUrl(paramString))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("w").append(paramInt1).append("-h").append(paramInt2).append("-d-p");
        str = FIFEUtil.setImageUrlOptions(localStringBuilder.toString(), paramString).toString();
      }
      else
      {
        str = ImageProxyUtil.setImageUrlSize(paramInt1, paramInt2, paramString);
      }
    }
  }

  public static String getCroppedAndResizedUrl(int paramInt, String paramString)
  {
    if (FIFEUtil.isFifeHostedUrl(paramString));
    for (String str = FIFEUtil.setImageUrlSize(paramInt, paramString, true); ; str = ImageProxyUtil.setImageUrlSize(paramInt, paramString))
      return str;
  }

  private static int getExifRotation(String paramString)
  {
    try
    {
      ExifInterface localExifInterface1 = new ExifInterface(paramString);
      localExifInterface2 = localExifInterface1;
      if (localExifInterface2 == null)
      {
        i = 0;
        return i;
      }
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        int i;
        Log.w("ImageUtils", "failed to create ExifInterface for " + paramString);
        ExifInterface localExifInterface2 = null;
        continue;
        switch (localExifInterface2.getAttributeInt("Orientation", 1))
        {
        case 2:
        case 4:
        case 5:
        case 7:
        default:
          i = 0;
          break;
        case 1:
          i = 0;
          break;
        case 6:
          i = 90;
          break;
        case 3:
          i = 180;
          break;
        case 8:
          i = 270;
        }
      }
    }
  }

  // ERROR //
  private static android.graphics.Point getImageBounds(ContentResolver paramContentResolver, Uri paramUri)
    throws IOException
  {
    // Byte code:
    //   0: new 194	android/graphics/BitmapFactory$Options
    //   3: dup
    //   4: invokespecial 195	android/graphics/BitmapFactory$Options:<init>	()V
    //   7: astore_2
    //   8: aconst_null
    //   9: astore_3
    //   10: aload_2
    //   11: iconst_1
    //   12: putfield 341	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   15: aload_0
    //   16: aload_1
    //   17: invokevirtual 218	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   20: astore_3
    //   21: aload_3
    //   22: aconst_null
    //   23: aload_2
    //   24: invokestatic 222	com/google/android/apps/plus/util/ImageUtils:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   27: pop
    //   28: new 197	android/graphics/Point
    //   31: dup
    //   32: aload_2
    //   33: getfield 345	android/graphics/BitmapFactory$Options:outWidth	I
    //   36: aload_2
    //   37: getfield 348	android/graphics/BitmapFactory$Options:outHeight	I
    //   40: invokespecial 443	android/graphics/Point:<init>	(II)V
    //   43: astore 7
    //   45: aload_3
    //   46: ifnull +7 -> 53
    //   49: aload_3
    //   50: invokevirtual 229	java/io/InputStream:close	()V
    //   53: aload 7
    //   55: areturn
    //   56: astore 4
    //   58: aload_3
    //   59: ifnull +7 -> 66
    //   62: aload_3
    //   63: invokevirtual 229	java/io/InputStream:close	()V
    //   66: aload 4
    //   68: athrow
    //   69: astore 8
    //   71: goto -18 -> 53
    //   74: astore 5
    //   76: goto -10 -> 66
    //
    // Exception table:
    //   from	to	target	type
    //   10	45	56	finally
    //   49	53	69	java/io/IOException
    //   62	66	74	java/io/IOException
  }

  // ERROR //
  private static android.graphics.Point getImageBounds(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: new 194	android/graphics/BitmapFactory$Options
    //   3: dup
    //   4: invokespecial 195	android/graphics/BitmapFactory$Options:<init>	()V
    //   7: astore_1
    //   8: new 445	java/io/ByteArrayInputStream
    //   11: dup
    //   12: aload_0
    //   13: invokespecial 446	java/io/ByteArrayInputStream:<init>	([B)V
    //   16: astore_2
    //   17: aload_1
    //   18: iconst_1
    //   19: putfield 341	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   22: aload_2
    //   23: aconst_null
    //   24: aload_1
    //   25: invokestatic 222	com/google/android/apps/plus/util/ImageUtils:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   28: pop
    //   29: new 197	android/graphics/Point
    //   32: dup
    //   33: aload_1
    //   34: getfield 345	android/graphics/BitmapFactory$Options:outWidth	I
    //   37: aload_1
    //   38: getfield 348	android/graphics/BitmapFactory$Options:outHeight	I
    //   41: invokespecial 443	android/graphics/Point:<init>	(II)V
    //   44: astore 6
    //   46: aload_2
    //   47: invokevirtual 447	java/io/ByteArrayInputStream:close	()V
    //   50: aload 6
    //   52: areturn
    //   53: astore_3
    //   54: aload_2
    //   55: invokevirtual 447	java/io/ByteArrayInputStream:close	()V
    //   58: aload_3
    //   59: athrow
    //   60: astore 7
    //   62: goto -12 -> 50
    //   65: astore 4
    //   67: goto -9 -> 58
    //
    // Exception table:
    //   from	to	target	type
    //   17	46	53	finally
    //   46	50	60	java/io/IOException
    //   54	58	65	java/io/IOException
  }

  public static int getMaxThumbnailDimension(Context paramContext, int paramInt)
  {
    int i;
    switch (paramInt)
    {
    case 2:
    default:
      if (EsLog.isLoggable("ImageUtils", 3))
        Log.d("ImageUtils", "illegal kind=" + paramInt + " specified; using MINI_KIND");
      i = getThumbnailSize(paramContext, 1);
    case 3:
    case 1:
    }
    while (true)
    {
      return i;
      i = getThumbnailSize(paramContext, 3);
      continue;
      i = getThumbnailSize(paramContext, 1);
    }
  }

  public static String getMimeType(ContentResolver paramContentResolver, Uri paramUri)
  {
    Object localObject = null;
    try
    {
      localObject = safeGetMimeType(paramContentResolver, paramUri);
      if (TextUtils.isEmpty((CharSequence)localObject))
      {
        String str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(paramUri.toString()));
        localObject = str;
      }
      return localObject;
    }
    catch (Exception localException)
    {
      while (true)
        if (EsLog.isLoggable("ImageUtils", 5))
          Log.w("ImageUtils", "getMimeType failed for uri=" + paramUri, localException);
    }
  }

  public static String getResizedUrl(int paramInt1, int paramInt2, String paramString)
  {
    if (FIFEUtil.isFifeHostedUrl(paramString));
    for (String str = FIFEUtil.setImageUrlSize(paramInt1, paramInt2, paramString, false, false); ; str = ImageProxyUtil.setImageUrlSize(paramInt1, paramInt2, paramString))
      return str;
  }

  // ERROR //
  private static Bitmap getRoundMask(Context paramContext, int paramInt)
  {
    // Byte code:
    //   0: getstatic 59	com/google/android/apps/plus/util/ImageUtils:sRoundMasks	Ljava/util/ArrayList;
    //   3: invokevirtual 495	java/util/ArrayList:size	()I
    //   6: istore_2
    //   7: iconst_0
    //   8: istore_3
    //   9: iload_3
    //   10: iload_2
    //   11: if_icmpge +40 -> 51
    //   14: getstatic 59	com/google/android/apps/plus/util/ImageUtils:sRoundMasks	Ljava/util/ArrayList;
    //   17: iload_3
    //   18: invokevirtual 499	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   21: checkcast 501	com/google/android/apps/plus/util/ImageUtils$RoundMask
    //   24: astore 14
    //   26: aload 14
    //   28: getfield 503	com/google/android/apps/plus/util/ImageUtils$RoundMask:size	I
    //   31: iload_1
    //   32: if_icmpne +13 -> 45
    //   35: aload 14
    //   37: getfield 506	com/google/android/apps/plus/util/ImageUtils$RoundMask:bitmap	Landroid/graphics/Bitmap;
    //   40: astore 6
    //   42: aload 6
    //   44: areturn
    //   45: iinc 3 1
    //   48: goto -39 -> 9
    //   51: getstatic 508	com/google/android/apps/plus/util/ImageUtils:sRoundMask	Landroid/graphics/Bitmap;
    //   54: ifnonnull +22 -> 76
    //   57: aload_0
    //   58: invokevirtual 512	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   61: getstatic 517	com/google/android/apps/plus/R$drawable:round_mask	I
    //   64: invokevirtual 523	android/content/res/Resources:getDrawable	(I)Landroid/graphics/drawable/Drawable;
    //   67: checkcast 525	android/graphics/drawable/BitmapDrawable
    //   70: invokevirtual 529	android/graphics/drawable/BitmapDrawable:getBitmap	()Landroid/graphics/Bitmap;
    //   73: putstatic 508	com/google/android/apps/plus/util/ImageUtils:sRoundMask	Landroid/graphics/Bitmap;
    //   76: iload_1
    //   77: iload_1
    //   78: getstatic 532	android/graphics/Bitmap$Config:ARGB_8888	Landroid/graphics/Bitmap$Config;
    //   81: invokestatic 536	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
    //   84: astore 7
    //   86: new 538	android/graphics/Canvas
    //   89: dup
    //   90: aload 7
    //   92: invokespecial 541	android/graphics/Canvas:<init>	(Landroid/graphics/Bitmap;)V
    //   95: astore 8
    //   97: getstatic 508	com/google/android/apps/plus/util/ImageUtils:sRoundMask	Landroid/graphics/Bitmap;
    //   100: invokevirtual 544	android/graphics/Bitmap:getWidth	()I
    //   103: istore 9
    //   105: getstatic 41	com/google/android/apps/plus/util/ImageUtils:sResizePaint	Landroid/graphics/Paint;
    //   108: astore 10
    //   110: aload 10
    //   112: monitorenter
    //   113: aload 8
    //   115: getstatic 508	com/google/android/apps/plus/util/ImageUtils:sRoundMask	Landroid/graphics/Bitmap;
    //   118: new 29	android/graphics/Rect
    //   121: dup
    //   122: iconst_0
    //   123: iconst_0
    //   124: iload 9
    //   126: iload 9
    //   128: invokespecial 547	android/graphics/Rect:<init>	(IIII)V
    //   131: new 29	android/graphics/Rect
    //   134: dup
    //   135: iconst_0
    //   136: iconst_0
    //   137: iload_1
    //   138: iload_1
    //   139: invokespecial 547	android/graphics/Rect:<init>	(IIII)V
    //   142: getstatic 41	com/google/android/apps/plus/util/ImageUtils:sResizePaint	Landroid/graphics/Paint;
    //   145: invokevirtual 551	android/graphics/Canvas:drawBitmap	(Landroid/graphics/Bitmap;Landroid/graphics/Rect;Landroid/graphics/Rect;Landroid/graphics/Paint;)V
    //   148: aload 10
    //   150: monitorexit
    //   151: new 501	com/google/android/apps/plus/util/ImageUtils$RoundMask
    //   154: dup
    //   155: iconst_0
    //   156: invokespecial 554	com/google/android/apps/plus/util/ImageUtils$RoundMask:<init>	(B)V
    //   159: astore 12
    //   161: aload 12
    //   163: iload_1
    //   164: putfield 503	com/google/android/apps/plus/util/ImageUtils$RoundMask:size	I
    //   167: aload 12
    //   169: aload 7
    //   171: putfield 506	com/google/android/apps/plus/util/ImageUtils$RoundMask:bitmap	Landroid/graphics/Bitmap;
    //   174: getstatic 59	com/google/android/apps/plus/util/ImageUtils:sRoundMasks	Ljava/util/ArrayList;
    //   177: aload 12
    //   179: invokevirtual 558	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   182: pop
    //   183: aload 12
    //   185: getfield 506	com/google/android/apps/plus/util/ImageUtils$RoundMask:bitmap	Landroid/graphics/Bitmap;
    //   188: astore 6
    //   190: goto -148 -> 42
    //   193: astore 4
    //   195: ldc 131
    //   197: new 139	java/lang/StringBuilder
    //   200: dup
    //   201: ldc_w 560
    //   204: invokespecial 144	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   207: iload_1
    //   208: invokevirtual 148	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   211: invokevirtual 152	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   214: invokestatic 434	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   217: pop
    //   218: aconst_null
    //   219: astore 6
    //   221: goto -179 -> 42
    //   224: astore 11
    //   226: aload 10
    //   228: monitorexit
    //   229: aload 11
    //   231: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   76	86	193	java/lang/OutOfMemoryError
    //   113	151	224	finally
  }

  // ERROR //
  public static Bitmap getRoundedBitmap(Context paramContext, Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +9 -> 10
    //   4: aconst_null
    //   5: astore 5
    //   7: aload 5
    //   9: areturn
    //   10: aload_1
    //   11: invokevirtual 544	android/graphics/Bitmap:getWidth	()I
    //   14: istore_2
    //   15: aload_1
    //   16: invokevirtual 565	android/graphics/Bitmap:getHeight	()I
    //   19: istore_3
    //   20: aload_0
    //   21: iload_2
    //   22: invokestatic 567	com/google/android/apps/plus/util/ImageUtils:getRoundMask	(Landroid/content/Context;I)Landroid/graphics/Bitmap;
    //   25: astore 4
    //   27: aload 4
    //   29: ifnonnull +9 -> 38
    //   32: aconst_null
    //   33: astore 5
    //   35: goto -28 -> 7
    //   38: iload_2
    //   39: iload_3
    //   40: getstatic 532	android/graphics/Bitmap$Config:ARGB_8888	Landroid/graphics/Bitmap$Config;
    //   43: invokestatic 536	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
    //   46: astore 8
    //   48: aload 8
    //   50: astore 5
    //   52: getstatic 569	com/google/android/apps/plus/util/ImageUtils:sAvatarOverlayBitmap	Landroid/graphics/Bitmap;
    //   55: ifnonnull +19 -> 74
    //   58: aload_0
    //   59: invokevirtual 573	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   62: invokevirtual 512	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   65: getstatic 576	com/google/android/apps/plus/R$drawable:bg_taco_avatar	I
    //   68: invokestatic 577	com/google/android/apps/plus/util/ImageUtils:decodeResource	(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;
    //   71: putstatic 569	com/google/android/apps/plus/util/ImageUtils:sAvatarOverlayBitmap	Landroid/graphics/Bitmap;
    //   74: getstatic 34	com/google/android/apps/plus/util/ImageUtils:sAvatarOverlayRect	Landroid/graphics/Rect;
    //   77: iconst_0
    //   78: iconst_0
    //   79: iload_2
    //   80: iload_3
    //   81: invokevirtual 580	android/graphics/Rect:set	(IIII)V
    //   84: new 538	android/graphics/Canvas
    //   87: dup
    //   88: aload 5
    //   90: invokespecial 541	android/graphics/Canvas:<init>	(Landroid/graphics/Bitmap;)V
    //   93: astore 9
    //   95: aload 9
    //   97: aload_1
    //   98: fconst_0
    //   99: fconst_0
    //   100: aconst_null
    //   101: invokevirtual 583	android/graphics/Canvas:drawBitmap	(Landroid/graphics/Bitmap;FFLandroid/graphics/Paint;)V
    //   104: getstatic 61	com/google/android/apps/plus/util/ImageUtils:sMaskPaint	Landroid/graphics/Paint;
    //   107: astore 10
    //   109: aload 10
    //   111: monitorenter
    //   112: aload 9
    //   114: aload 4
    //   116: fconst_0
    //   117: fconst_0
    //   118: getstatic 61	com/google/android/apps/plus/util/ImageUtils:sMaskPaint	Landroid/graphics/Paint;
    //   121: invokevirtual 583	android/graphics/Canvas:drawBitmap	(Landroid/graphics/Bitmap;FFLandroid/graphics/Paint;)V
    //   124: aload 10
    //   126: monitorexit
    //   127: getstatic 41	com/google/android/apps/plus/util/ImageUtils:sResizePaint	Landroid/graphics/Paint;
    //   130: astore 12
    //   132: aload 12
    //   134: monitorenter
    //   135: aload 9
    //   137: getstatic 569	com/google/android/apps/plus/util/ImageUtils:sAvatarOverlayBitmap	Landroid/graphics/Bitmap;
    //   140: aconst_null
    //   141: getstatic 34	com/google/android/apps/plus/util/ImageUtils:sAvatarOverlayRect	Landroid/graphics/Rect;
    //   144: getstatic 41	com/google/android/apps/plus/util/ImageUtils:sResizePaint	Landroid/graphics/Paint;
    //   147: invokevirtual 551	android/graphics/Canvas:drawBitmap	(Landroid/graphics/Bitmap;Landroid/graphics/Rect;Landroid/graphics/Rect;Landroid/graphics/Paint;)V
    //   150: aload 12
    //   152: monitorexit
    //   153: goto -146 -> 7
    //   156: astore 13
    //   158: aload 12
    //   160: monitorexit
    //   161: aload 13
    //   163: athrow
    //   164: astore 6
    //   166: ldc 131
    //   168: new 139	java/lang/StringBuilder
    //   171: dup
    //   172: ldc_w 585
    //   175: invokespecial 144	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   178: iload_2
    //   179: invokevirtual 148	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   182: ldc_w 351
    //   185: invokevirtual 243	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: iload_3
    //   189: invokevirtual 148	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   192: invokevirtual 152	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   195: invokestatic 434	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   198: pop
    //   199: aconst_null
    //   200: astore 5
    //   202: goto -195 -> 7
    //   205: astore 11
    //   207: aload 10
    //   209: monitorexit
    //   210: aload 11
    //   212: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   135	153	156	finally
    //   38	48	164	java/lang/OutOfMemoryError
    //   112	127	205	finally
  }

  public static byte[] getRoundedBitmap(Context paramContext, byte[] paramArrayOfByte)
  {
    Bitmap localBitmap1 = decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
    byte[] arrayOfByte = null;
    if (localBitmap1 == null);
    while (true)
    {
      return arrayOfByte;
      Bitmap localBitmap2 = getRoundedBitmap(paramContext, localBitmap1);
      localBitmap1.recycle();
      arrayOfByte = null;
      if (localBitmap2 != null)
      {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        localBitmap2.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
        localBitmap2.recycle();
        arrayOfByte = localByteArrayOutputStream.toByteArray();
      }
    }
  }

  private static int getThumbnailSize(Context paramContext, int paramInt)
  {
    switch (paramInt)
    {
    default:
      if (sMiniKindMaxDimension == 0)
        sMiniKindMaxDimension = paramContext.getResources().getDimensionPixelSize(R.dimen.mini_kind_max_dimension);
      break;
    case 3:
    }
    for (int i = sMiniKindMaxDimension; ; i = sMicroKindMaxDimension)
    {
      return i;
      if (sMicroKindMaxDimension == 0)
        sMicroKindMaxDimension = paramContext.getResources().getDimensionPixelSize(R.dimen.micro_kind_max_dimension);
    }
  }

  // ERROR //
  public static String insertCameraPhoto(Context paramContext, String paramString)
    throws java.io.FileNotFoundException
  {
    // Byte code:
    //   0: new 262	java/io/File
    //   3: dup
    //   4: invokestatic 612	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   7: aload_1
    //   8: invokespecial 271	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   11: astore_2
    //   12: invokestatic 618	java/lang/System:currentTimeMillis	()J
    //   15: lstore_3
    //   16: new 620	java/util/Date
    //   19: dup
    //   20: lload_3
    //   21: invokespecial 623	java/util/Date:<init>	(J)V
    //   24: astore 5
    //   26: new 625	java/text/SimpleDateFormat
    //   29: dup
    //   30: aload_0
    //   31: getstatic 628	com/google/android/apps/plus/R$string:image_file_name_format	I
    //   34: invokevirtual 182	android/content/Context:getString	(I)Ljava/lang/String;
    //   37: invokespecial 629	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
    //   40: aload 5
    //   42: invokevirtual 633	java/text/SimpleDateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
    //   45: astore 6
    //   47: aload_0
    //   48: invokevirtual 637	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   51: astore 7
    //   53: new 639	android/content/ContentValues
    //   56: dup
    //   57: iconst_5
    //   58: invokespecial 640	android/content/ContentValues:<init>	(I)V
    //   61: astore 8
    //   63: aload_2
    //   64: invokevirtual 285	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   67: invokestatic 289	com/google/android/apps/plus/util/ImageUtils:getExifRotation	(Ljava/lang/String;)I
    //   70: istore 9
    //   72: aload 8
    //   74: ldc_w 642
    //   77: aload 6
    //   79: invokevirtual 646	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   82: aload 8
    //   84: ldc_w 648
    //   87: new 139	java/lang/StringBuilder
    //   90: dup
    //   91: invokespecial 402	java/lang/StringBuilder:<init>	()V
    //   94: aload 6
    //   96: invokevirtual 243	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: ldc_w 650
    //   102: invokevirtual 243	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: invokevirtual 152	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   108: invokevirtual 646	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   111: aload 8
    //   113: ldc_w 652
    //   116: lload_3
    //   117: invokestatic 658	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   120: invokevirtual 661	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   123: aload 8
    //   125: ldc_w 663
    //   128: ldc_w 665
    //   131: invokevirtual 646	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   134: aload 8
    //   136: ldc_w 667
    //   139: iload 9
    //   141: invokestatic 672	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   144: invokevirtual 675	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   147: aload 7
    //   149: getstatic 681	android/provider/MediaStore$Images$Media:EXTERNAL_CONTENT_URI	Landroid/net/Uri;
    //   152: aload 8
    //   154: invokevirtual 685	android/content/ContentResolver:insert	(Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
    //   157: astore 33
    //   159: aload 33
    //   161: astore 16
    //   163: aload 7
    //   165: aload 16
    //   167: invokevirtual 689	android/content/ContentResolver:openOutputStream	(Landroid/net/Uri;)Ljava/io/OutputStream;
    //   170: astore 25
    //   172: new 691	java/io/FileInputStream
    //   175: dup
    //   176: aload_2
    //   177: invokespecial 692	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   180: astore 26
    //   182: sipush 10240
    //   185: newarray byte
    //   187: astore 28
    //   189: aload 26
    //   191: aload 28
    //   193: invokevirtual 696	java/io/FileInputStream:read	([B)I
    //   196: istore 29
    //   198: iload 29
    //   200: iconst_m1
    //   201: if_icmpeq +118 -> 319
    //   204: aload 25
    //   206: aload 28
    //   208: iconst_0
    //   209: iload 29
    //   211: invokevirtual 701	java/io/OutputStream:write	([BII)V
    //   214: goto -25 -> 189
    //   217: astore 27
    //   219: aload 25
    //   221: invokevirtual 702	java/io/OutputStream:close	()V
    //   224: aload 26
    //   226: invokevirtual 703	java/io/FileInputStream:close	()V
    //   229: aload 27
    //   231: athrow
    //   232: astore 23
    //   234: ldc 131
    //   236: ldc_w 705
    //   239: aload 23
    //   241: invokestatic 302	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   244: pop
    //   245: aload 23
    //   247: athrow
    //   248: astore 21
    //   250: aload_2
    //   251: invokevirtual 296	java/io/File:delete	()Z
    //   254: pop
    //   255: aload 21
    //   257: athrow
    //   258: astore 10
    //   260: aload 7
    //   262: getstatic 708	android/provider/MediaStore$Images$Media:INTERNAL_CONTENT_URI	Landroid/net/Uri;
    //   265: aload 8
    //   267: invokevirtual 685	android/content/ContentResolver:insert	(Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
    //   270: astore 32
    //   272: aload 32
    //   274: astore 16
    //   276: goto -113 -> 163
    //   279: astore 11
    //   281: aload 7
    //   283: getstatic 713	com/google/android/apps/plus/util/MediaStoreUtils:PHONE_STORAGE_IMAGES_URI	Landroid/net/Uri;
    //   286: aload 8
    //   288: invokevirtual 685	android/content/ContentResolver:insert	(Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
    //   291: astore 15
    //   293: aload 15
    //   295: astore 16
    //   297: goto -134 -> 163
    //   300: astore 12
    //   302: ldc 131
    //   304: ldc_w 715
    //   307: aload 12
    //   309: invokestatic 302	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   312: pop
    //   313: aconst_null
    //   314: astore 14
    //   316: aload 14
    //   318: areturn
    //   319: aload 25
    //   321: invokevirtual 702	java/io/OutputStream:close	()V
    //   324: aload 26
    //   326: invokevirtual 703	java/io/FileInputStream:close	()V
    //   329: aload 16
    //   331: invokestatic 719	com/google/android/apps/plus/util/MediaStoreUtils:isExternalMediaStoreUri	(Landroid/net/Uri;)Z
    //   334: ifeq +22 -> 356
    //   337: aload_0
    //   338: aload 16
    //   340: iconst_1
    //   341: invokestatic 722	com/google/android/apps/plus/util/MediaStoreUtils:getThumbnail	(Landroid/content/Context;Landroid/net/Uri;I)Landroid/graphics/Bitmap;
    //   344: astore 31
    //   346: aload 31
    //   348: ifnull +8 -> 356
    //   351: aload 31
    //   353: invokevirtual 125	android/graphics/Bitmap:recycle	()V
    //   356: aload_2
    //   357: invokevirtual 296	java/io/File:delete	()Z
    //   360: pop
    //   361: aload 16
    //   363: ifnonnull +48 -> 411
    //   366: aconst_null
    //   367: astore 14
    //   369: goto -53 -> 316
    //   372: astore 17
    //   374: ldc 131
    //   376: ldc_w 724
    //   379: aload 17
    //   381: invokestatic 302	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   384: pop
    //   385: aload 16
    //   387: ifnull +16 -> 403
    //   390: aload 7
    //   392: aload 16
    //   394: aconst_null
    //   395: aconst_null
    //   396: invokevirtual 727	android/content/ContentResolver:delete	(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I
    //   399: pop
    //   400: aconst_null
    //   401: astore 16
    //   403: aload_2
    //   404: invokevirtual 296	java/io/File:delete	()Z
    //   407: pop
    //   408: goto -47 -> 361
    //   411: aload 16
    //   413: invokevirtual 415	android/net/Uri:toString	()Ljava/lang/String;
    //   416: astore 14
    //   418: goto -102 -> 316
    //
    // Exception table:
    //   from	to	target	type
    //   182	214	217	finally
    //   163	182	232	java/io/FileNotFoundException
    //   219	232	232	java/io/FileNotFoundException
    //   319	356	232	java/io/FileNotFoundException
    //   163	182	248	finally
    //   219	232	248	finally
    //   234	248	248	finally
    //   319	356	248	finally
    //   374	400	248	finally
    //   147	159	258	java/lang/Exception
    //   260	272	279	java/lang/Exception
    //   281	293	300	java/lang/Exception
    //   163	182	372	java/lang/Exception
    //   219	232	372	java/lang/Exception
    //   319	356	372	java/lang/Exception
  }

  public static boolean isFileUri(Uri paramUri)
  {
    if ((paramUri != null) && ("file".equals(paramUri.getScheme())));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static boolean isImageMimeType(String paramString)
  {
    if ((paramString != null) && (paramString.startsWith("image/")));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static boolean isVideoMimeType(String paramString)
  {
    if ((paramString != null) && (paramString.startsWith("video/")));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static Bitmap resizeAndCropBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if (paramBitmap == null)
      paramBitmap = null;
    while (true)
    {
      return paramBitmap;
      if (EsLog.isLoggable("ImageUtils", 3))
        Log.d("ImageUtils", "resizeAndCropBitmap: Input: " + paramBitmap.getWidth() + "x" + paramBitmap.getHeight() + ", output:" + paramInt1 + "x" + paramInt2);
      if ((paramBitmap.getWidth() == paramInt1) && (paramBitmap.getHeight() == paramInt2))
        continue;
      Bitmap localBitmap;
      try
      {
        localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
        if (localBitmap != null)
          break label152;
        paramBitmap = null;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        Log.w("ImageUtils", "resizeAndCropBitmap OutOfMemoryError for image size: " + paramInt1 + " x " + paramInt2, localOutOfMemoryError);
        paramBitmap = null;
      }
      continue;
      label152: Canvas localCanvas = new Canvas(localBitmap);
      int i = paramBitmap.getWidth();
      int j = paramBitmap.getHeight();
      Rect localRect1;
      Rect localRect2;
      if (paramInt2 * paramBitmap.getWidth() > paramInt1 * paramBitmap.getHeight())
      {
        i = paramInt1 * paramBitmap.getHeight() / paramInt2;
        int k = (paramBitmap.getWidth() - i) / 2;
        int m = (paramBitmap.getHeight() - j) / 2;
        localRect1 = new Rect(k, m, k + i, m + j);
        localRect2 = new Rect(0, 0, paramInt1, paramInt2);
      }
      synchronized (sResizePaint)
      {
        localCanvas.drawBitmap(paramBitmap, localRect1, localRect2, sResizePaint);
        paramBitmap = localBitmap;
        continue;
        j = paramInt2 * paramBitmap.getWidth() / paramInt1;
      }
    }
  }

  public static Bitmap resizeBitmap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    BitmapFactory.Options localOptions1 = new BitmapFactory.Options();
    localOptions1.inJustDecodeBounds = true;
    decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length, localOptions1);
    int i = localOptions1.outWidth;
    int j = localOptions1.outHeight;
    if (EsLog.isLoggable("ImageUtils", 3))
      Log.d("ImageUtils", "resizeBitmap: Input: " + i + "x" + j + ", resize to: " + paramInt1 + "x" + paramInt2);
    Bitmap localBitmap1;
    Object localObject1;
    if ((i > paramInt1) || (j > paramInt2))
    {
      float f1 = i * paramInt1 / j;
      float f2 = j * paramInt2 / i;
      if ((i / f1 > 1.0F) || (j / f2 > 1.0F))
      {
        BitmapFactory.Options localOptions2 = new BitmapFactory.Options();
        localOptions2.inSampleSize = Math.max(i / (int)f1, j / (int)f2);
        localBitmap1 = decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length, localOptions2);
        if (localBitmap1 != null)
          break label227;
        localObject1 = null;
      }
    }
    while (true)
    {
      return localObject1;
      localBitmap1 = decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
      break;
      localBitmap1 = decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
      break;
      try
      {
        label227: Bitmap localBitmap2 = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
        localObject1 = localBitmap2;
        if (localObject1 == null)
        {
          localBitmap1.recycle();
          localObject1 = null;
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        while (true)
        {
          Log.w("ImageUtils", "resizeBitmap OutOfMemoryError for image size: " + paramInt1 + " x " + paramInt2, localOutOfMemoryError);
          localObject1 = null;
        }
        int k = localBitmap1.getWidth();
        int m = localBitmap1.getHeight();
        int n = k;
        int i1 = m;
        Rect localRect1;
        Rect localRect2;
        Canvas localCanvas;
        if (i * paramInt2 > paramInt1 * j)
        {
          n = paramInt1 * localBitmap1.getHeight() / paramInt2;
          if (EsLog.isLoggable("ImageUtils", 3))
            Log.d("ImageUtils", "resizeBitmap: cropped: " + n + "x" + i1);
          int i2 = (k - n) / 2;
          int i3 = (m - i1) / 2;
          localRect1 = new Rect(i2, i3, i2 + n, i3 + i1);
          localRect2 = new Rect(0, 0, paramInt1, paramInt2);
          localCanvas = new Canvas((Bitmap)localObject1);
          localCanvas.drawColor(-2039584);
        }
        synchronized (sResizePaint)
        {
          Paint localPaint2 = sResizePaint;
          localCanvas.drawBitmap(localBitmap1, localRect1, localRect2, localPaint2);
          localBitmap1.recycle();
          continue;
          i1 = paramInt2 * localBitmap1.getWidth() / paramInt1;
        }
      }
    }
  }

  public static byte[] resizeBitmapToHeight(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte == null);
    while (true)
    {
      return paramArrayOfByte;
      BitmapFactory.Options localOptions1 = new BitmapFactory.Options();
      localOptions1.inJustDecodeBounds = true;
      int i = paramArrayOfByte.length;
      decodeByteArray(paramArrayOfByte, 0, i, localOptions1);
      int j = localOptions1.outWidth;
      int k = localOptions1.outHeight;
      if (EsLog.isLoggable("ImageUtils", 3))
        Log.d("ImageUtils", "scaleBitmap: Input: " + j + "x" + k + ", resize to: " + paramInt);
      if (k <= paramInt)
        continue;
      int m = (int)(j / k * paramInt);
      Bitmap localBitmap1;
      if ((j / m > 1) || (k / paramInt > 1))
      {
        BitmapFactory.Options localOptions2 = new BitmapFactory.Options();
        localOptions2.inSampleSize = Math.max(j / m, k / paramInt);
        int n = paramArrayOfByte.length;
        localBitmap1 = decodeByteArray(paramArrayOfByte, 0, n, localOptions2);
        if (localBitmap1 == null)
        {
          paramArrayOfByte = null;
          continue;
        }
        j = localBitmap1.getWidth();
        k = localBitmap1.getHeight();
      }
      try
      {
        do
        {
          Bitmap localBitmap3 = Bitmap.createBitmap(m, paramInt, Bitmap.Config.ARGB_8888);
          localBitmap2 = localBitmap3;
          if (localBitmap2 != null)
            break label294;
          localBitmap1.recycle();
          paramArrayOfByte = null;
          break;
          int i1 = paramArrayOfByte.length;
          localBitmap1 = decodeByteArray(paramArrayOfByte, 0, i1);
        }
        while (localBitmap1 != null);
        paramArrayOfByte = null;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        Bitmap localBitmap2;
        while (true)
        {
          Log.w("ImageUtils", "resizeBitmapToHeight OutOfMemoryError for image size: " + m + " x " + paramInt, localOutOfMemoryError);
          localBitmap2 = null;
        }
        label294: Canvas localCanvas = new Canvas(localBitmap2);
        synchronized (sResizePaint)
        {
          localCanvas.drawBitmap(localBitmap1, new Rect(0, 0, j, k), new Rect(0, 0, m, paramInt), sResizePaint);
          localBitmap1.recycle();
          ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
          localBitmap2.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
          localBitmap2.recycle();
          paramArrayOfByte = localByteArrayOutputStream.toByteArray();
        }
      }
    }
  }

  public static byte[] resizeToRoundBitmap(Context paramContext, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Bitmap localBitmap1 = decodeAndScaleBitmap(paramArrayOfByte, paramInt1, paramInt2);
    byte[] arrayOfByte = null;
    if (localBitmap1 == null);
    while (true)
    {
      return arrayOfByte;
      Bitmap localBitmap2 = getRoundedBitmap(paramContext, localBitmap1);
      localBitmap1.recycle();
      arrayOfByte = null;
      if (localBitmap2 != null)
      {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        localBitmap2.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
        localBitmap2.recycle();
        arrayOfByte = localByteArrayOutputStream.toByteArray();
      }
    }
  }

  public static Bitmap resizeToSquareBitmap(Bitmap paramBitmap, int paramInt)
  {
    return resizeToSquareBitmap(paramBitmap, paramInt, 0);
  }

  public static Bitmap resizeToSquareBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    Object localObject1;
    if (paramBitmap == null)
      localObject1 = null;
    while (true)
    {
      return localObject1;
      if (EsLog.isLoggable("ImageUtils", 3))
        Log.d("ImageUtils", "resizeToSquareBitmap: Input: " + paramBitmap.getWidth() + "x" + paramBitmap.getHeight() + ", output:" + paramInt1 + "x" + paramInt1);
      try
      {
        Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt1, Bitmap.Config.ARGB_8888);
        localObject1 = localBitmap;
        if (localObject1 != null)
          break label134;
        localObject1 = null;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        Log.w("ImageUtils", "resizeToSquareBitmap OutOfMemoryError for image size: " + paramInt1, localOutOfMemoryError);
        localObject1 = null;
      }
      continue;
      label134: Canvas localCanvas = new Canvas((Bitmap)localObject1);
      if (paramInt2 != 0)
        localCanvas.drawColor(paramInt2);
      if ((paramBitmap.getWidth() != paramInt1) || (paramBitmap.getHeight() != paramInt1))
      {
        Rect localRect1 = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
        Rect localRect2 = new Rect(0, 0, paramInt1, paramInt1);
        synchronized (sResizePaint)
        {
          localCanvas.drawBitmap(paramBitmap, localRect1, localRect2, sResizePaint);
        }
      }
      localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, null);
    }
  }

  public static byte[] resizeToSquareBitmap(byte[] paramArrayOfByte, int paramInt)
  {
    return resizeToSquareBitmap(paramArrayOfByte, paramInt, 0);
  }

  public static byte[] resizeToSquareBitmap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Bitmap localBitmap = decodeAndScaleBitmap(paramArrayOfByte, paramInt1, paramInt2);
    if (localBitmap == null);
    ByteArrayOutputStream localByteArrayOutputStream;
    for (byte[] arrayOfByte = null; ; arrayOfByte = localByteArrayOutputStream.toByteArray())
    {
      return arrayOfByte;
      localByteArrayOutputStream = new ByteArrayOutputStream();
      localBitmap.compress(Bitmap.CompressFormat.JPEG, 80, localByteArrayOutputStream);
      localBitmap.recycle();
    }
  }

  public static String rewriteYoutubeMediaUrl(String paramString)
  {
    if (paramString.startsWith("http://www.youtube.com/watch?v="))
    {
      String str = paramString.substring(31);
      int i = str.indexOf("&");
      if (i >= 0)
        str = str.substring(0, i);
      paramString = "https://img.youtube.com/vi/" + str + "/0.jpg";
    }
    return paramString;
  }

  public static Bitmap rotateBitmap(ContentResolver paramContentResolver, Uri paramUri, Bitmap paramBitmap)
  {
    if ((paramBitmap != null) && ((MediaStoreUtils.isMediaStoreUri(paramUri)) || (isFileUri(paramUri))))
      if (!isFileUri(paramUri))
        break label50;
    label50: for (String str = paramUri.getPath(); ; str = MediaStoreUtils.getFilePath(paramContentResolver, paramUri))
    {
      int i = getExifRotation(str);
      if (i != 0)
        paramBitmap = rotateBitmap(paramBitmap, i);
      return paramBitmap;
    }
  }

  private static Bitmap rotateBitmap(Bitmap paramBitmap, int paramInt)
  {
    Matrix localMatrix;
    int i;
    int j;
    Bitmap localBitmap1;
    if ((paramInt != 0) && (paramBitmap != null))
    {
      localMatrix = new Matrix();
      i = paramBitmap.getWidth();
      j = paramBitmap.getHeight();
      localMatrix.setRotate(paramInt, i / 2.0F, j / 2.0F);
      localBitmap1 = paramBitmap;
    }
    try
    {
      Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, 0, i, j, localMatrix, true);
      if (paramBitmap != localBitmap2)
      {
        paramBitmap.recycle();
        paramBitmap = localBitmap2;
      }
      label72: return paramBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      break label72;
    }
  }

  private static String safeGetMimeType(ContentResolver paramContentResolver, Uri paramUri)
  {
    try
    {
      String str2 = paramContentResolver.getType(paramUri);
      str1 = str2;
      return str1;
    }
    catch (Exception localException)
    {
      while (true)
      {
        boolean bool = EsLog.isLoggable("ImageUtils", 5);
        String str1 = null;
        if (bool)
        {
          Log.w("ImageUtils", "safeGetMimeType failed for uri=" + paramUri, localException);
          str1 = null;
        }
      }
    }
  }

  public static abstract interface InsertCameraPhotoDialogDisplayer
  {
    public abstract void hideInsertCameraPhotoDialog();

    public abstract void showInsertCameraPhotoDialog();
  }

  private static final class RoundMask
  {
    Bitmap bitmap;
    int size;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.ImageUtils
 * JD-Core Version:    0.6.2
 */