package com.google.android.apps.plus.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.List;

public final class GalleryUtils
{
  private static final String[] ACCOUNT_PROJECTION = { "account" };
  private static final String[] PHOTOID_PROJECTION = { "picasa_id" };
  private static final String[] USERID_PROJECTION;
  private static final String[] USER_ACCOUNT_PROJECTION = { "user_account" };

  static
  {
    USERID_PROJECTION = new String[] { "user_id" };
  }

  public static String getAccountName(Context paramContext, Uri paramUri)
  {
    try
    {
      String str2 = getAccountNameNew(paramContext, paramUri);
      str1 = str2;
      return str1;
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      while (true)
        String str1 = getAccountNameOld(paramContext, paramUri);
    }
  }

  private static String getAccountNameNew(Context paramContext, Uri paramUri)
    throws UnsupportedOperationException
  {
    Cursor localCursor = paramContext.getContentResolver().query(paramUri, USER_ACCOUNT_PROJECTION, null, null, null);
    if (localCursor != null);
    while (true)
    {
      try
      {
        if (localCursor.moveToNext())
        {
          boolean bool = localCursor.isNull(0);
          if (bool)
          {
            str = null;
            if (str != null)
              return str;
          }
          else
          {
            str = localCursor.getString(0);
            continue;
          }
          throw new UnsupportedOperationException("old version of Gallery");
        }
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
      String str = null;
    }
  }

  // ERROR //
  private static String getAccountNameOld(Context paramContext, Uri paramUri)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 78	com/google/android/apps/plus/util/GalleryUtils:getPhotoIdOld	(Landroid/net/Uri;)J
    //   4: lstore_2
    //   5: lload_2
    //   6: lconst_0
    //   7: lcmp
    //   8: ifne +9 -> 17
    //   11: aconst_null
    //   12: astore 9
    //   14: aload 9
    //   16: areturn
    //   17: aload_0
    //   18: invokevirtual 45	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   21: astore 4
    //   23: aload_0
    //   24: invokestatic 84	com/google/android/picasasync/PicasaFacade:get	(Landroid/content/Context;)Lcom/google/android/picasasync/PicasaFacade;
    //   27: invokevirtual 88	com/google/android/picasasync/PicasaFacade:getPhotosUri	()Landroid/net/Uri;
    //   30: astore 5
    //   32: getstatic 25	com/google/android/apps/plus/util/GalleryUtils:USERID_PROJECTION	[Ljava/lang/String;
    //   35: astore 6
    //   37: iconst_1
    //   38: anewarray 13	java/lang/String
    //   41: astore 7
    //   43: aload 7
    //   45: iconst_0
    //   46: lload_2
    //   47: invokestatic 94	java/lang/Long:toString	(J)Ljava/lang/String;
    //   50: aastore
    //   51: aload 4
    //   53: aload 5
    //   55: aload 6
    //   57: ldc 96
    //   59: aload 7
    //   61: aconst_null
    //   62: invokevirtual 51	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   65: astore 8
    //   67: aload 8
    //   69: ifnull +124 -> 193
    //   72: aload 8
    //   74: invokeinterface 99 1 0
    //   79: ifeq +114 -> 193
    //   82: aload 8
    //   84: iconst_0
    //   85: invokeinterface 103 2 0
    //   90: istore 11
    //   92: aload 8
    //   94: ifnull +10 -> 104
    //   97: aload 8
    //   99: invokeinterface 64 1 0
    //   104: aload_0
    //   105: invokestatic 84	com/google/android/picasasync/PicasaFacade:get	(Landroid/content/Context;)Lcom/google/android/picasasync/PicasaFacade;
    //   108: invokevirtual 106	com/google/android/picasasync/PicasaFacade:getUsersUri	()Landroid/net/Uri;
    //   111: astore 12
    //   113: getstatic 29	com/google/android/apps/plus/util/GalleryUtils:ACCOUNT_PROJECTION	[Ljava/lang/String;
    //   116: astore 13
    //   118: iconst_1
    //   119: anewarray 13	java/lang/String
    //   122: astore 14
    //   124: aload 14
    //   126: iconst_0
    //   127: iload 11
    //   129: invokestatic 110	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   132: aastore
    //   133: aload 4
    //   135: aload 12
    //   137: aload 13
    //   139: ldc 96
    //   141: aload 14
    //   143: aconst_null
    //   144: invokevirtual 51	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   147: astore 15
    //   149: aload 15
    //   151: ifnull +77 -> 228
    //   154: aload 15
    //   156: invokeinterface 99 1 0
    //   161: ifeq +67 -> 228
    //   164: aload 15
    //   166: iconst_0
    //   167: invokeinterface 68 2 0
    //   172: astore 17
    //   174: aload 17
    //   176: astore 9
    //   178: aload 15
    //   180: ifnull -166 -> 14
    //   183: aload 15
    //   185: invokeinterface 64 1 0
    //   190: goto -176 -> 14
    //   193: aload 8
    //   195: ifnull +10 -> 205
    //   198: aload 8
    //   200: invokeinterface 64 1 0
    //   205: aconst_null
    //   206: astore 9
    //   208: goto -194 -> 14
    //   211: astore 10
    //   213: aload 8
    //   215: ifnull +10 -> 225
    //   218: aload 8
    //   220: invokeinterface 64 1 0
    //   225: aload 10
    //   227: athrow
    //   228: aconst_null
    //   229: astore 9
    //   231: goto -53 -> 178
    //   234: astore 16
    //   236: aload 15
    //   238: ifnull +10 -> 248
    //   241: aload 15
    //   243: invokeinterface 64 1 0
    //   248: aload 16
    //   250: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   72	92	211	finally
    //   154	174	234	finally
  }

  public static long getPhotoId(Context paramContext, Uri paramUri)
  {
    try
    {
      long l2 = getPhotoIdNew(paramContext, paramUri);
      l1 = l2;
      return l1;
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      while (true)
        long l1 = getPhotoIdOld(paramUri);
    }
  }

  private static long getPhotoIdNew(Context paramContext, Uri paramUri)
    throws UnsupportedOperationException
  {
    Cursor localCursor = paramContext.getContentResolver().query(paramUri, PHOTOID_PROJECTION, null, null, null);
    if (localCursor != null);
    while (true)
    {
      try
      {
        if (localCursor.moveToNext())
        {
          if (localCursor.isNull(0))
          {
            localLong = null;
            if (localLong != null)
            {
              long l = localLong.longValue();
              return l;
            }
          }
          else
          {
            localLong = Long.valueOf(localCursor.getLong(0));
            continue;
          }
          throw new UnsupportedOperationException("old version of Gallery");
        }
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
      Long localLong = null;
    }
  }

  private static long getPhotoIdOld(Uri paramUri)
  {
    long l1;
    if (!isGalleryContentUri(paramUri))
      l1 = 0L;
    while (true)
    {
      return l1;
      List localList = paramUri.getPathSegments();
      if ((localList == null) || (localList.size() != 3))
        l1 = 0L;
      else if ((!"picasa".equals(localList.get(0))) || (!"item".equals(localList.get(1))))
        l1 = 0L;
      else
        try
        {
          long l2 = Long.parseLong((String)localList.get(2));
          l1 = l2;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          if (EsLog.isLoggable("GalleryUtils", 4))
            Log.i("GalleryUtils", "Invalid photo ID; uri: " + paramUri.toString());
          l1 = 0L;
        }
    }
  }

  public static boolean isGalleryContentUri(Uri paramUri)
  {
    if ((paramUri != null) && ("content".equals(paramUri.getScheme())) && ("com.google.android.gallery3d.provider".equals(paramUri.getAuthority())));
    for (boolean bool = true; ; bool = false)
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.GalleryUtils
 * JD-Core Version:    0.6.2
 */