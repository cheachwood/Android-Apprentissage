package com.google.android.apps.plus.content;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.service.ImageDownloader;
import com.google.android.apps.plus.util.ImageUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class EsAvatarData
{
  private static final String[] AVATAR_URL_PROJECTION;
  private static int sBackgroundColor;
  private static Bitmap sDefaultAvatarMedium;
  private static Bitmap sDefaultAvatarMediumRound;
  private static Bitmap sDefaultAvatarSmall;
  private static Bitmap sDefaultAvatarSmallRound;
  private static Bitmap sDefaultAvatarTiny;
  private static Bitmap sDefaultAvatarTinyRound;
  private static final HashMap<String, String> sFifeAbbrs;
  private static final HashMap<String, String> sFifeHosts;
  private static int sMediumAvatarSize;
  public static boolean sRoundAvatarsEnabled = true;
  private static int sSmallAvatarSize;
  private static int sTinyAvatarSize;

  static
  {
    AVATAR_URL_PROJECTION = new String[] { "gaia_id", "avatar" };
    sFifeHosts = new HashMap();
    sFifeAbbrs = new HashMap();
    sFifeHosts.put("lh3.googleusercontent.com", "~3");
    sFifeAbbrs.put("~3", "lh3.googleusercontent.com");
    sFifeHosts.put("lh4.googleusercontent.com", "~4");
    sFifeAbbrs.put("~4", "lh4.googleusercontent.com");
    sFifeHosts.put("lh5.googleusercontent.com", "~5");
    sFifeAbbrs.put("~5", "lh5.googleusercontent.com");
    sFifeHosts.put("lh6.googleusercontent.com", "~6");
    sFifeAbbrs.put("~6", "lh6.googleusercontent.com");
  }

  public static String compressAvatarUrl(String paramString)
  {
    String str2;
    if (TextUtils.isEmpty(paramString))
      str2 = null;
    while (true)
    {
      return str2;
      int i;
      label25: int j;
      int k;
      if (paramString.startsWith("https://"))
      {
        i = 8;
        j = paramString.length();
        if (paramString.endsWith("/photo.jpg"))
          j -= 9;
        k = paramString.indexOf('/', i);
        if (k != -1)
          break label128;
      }
      label128: for (String str1 = null; ; str1 = (String)sFifeHosts.get(paramString.substring(i, k)))
      {
        if (str1 == null)
          break label149;
        str2 = str1 + paramString.substring(k, j);
        break;
        if (paramString.startsWith("http://"))
        {
          i = 7;
          break label25;
        }
        boolean bool = paramString.startsWith("//");
        i = 0;
        if (!bool)
          break label25;
        i = 2;
        break label25;
      }
      label149: str2 = paramString.substring(i, j);
    }
  }

  private static int getAvatarBackgroundColor(Context paramContext)
  {
    if (sBackgroundColor == 0)
      sBackgroundColor = paramContext.getResources().getColor(R.color.avatar_background_color);
    return sBackgroundColor;
  }

  public static int getAvatarSizeInPx(Context paramContext, int paramInt)
  {
    int i;
    switch (paramInt)
    {
    default:
      i = 0;
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return i;
      i = getTinyAvatarSize(paramContext);
      continue;
      i = getSmallAvatarSize(paramContext);
      continue;
      i = getMediumAvatarSize(paramContext);
    }
  }

  public static int getAvatarUrlSignature(String paramString)
  {
    if (paramString == null);
    for (int i = 1; ; i = 2)
      do
      {
        return i;
        i = paramString.hashCode();
      }
      while ((i != 0) && (i != 1));
  }

  public static int getMediumAvatarSize(Context paramContext)
  {
    if (sMediumAvatarSize == 0)
      sMediumAvatarSize = paramContext.getApplicationContext().getResources().getDimensionPixelSize(R.dimen.medium_avatar_dimension);
    return sMediumAvatarSize;
  }

  public static Bitmap getMediumDefaultAvatar(Context paramContext)
  {
    if (sDefaultAvatarMedium == null)
      sDefaultAvatarMedium = ((BitmapDrawable)paramContext.getApplicationContext().getResources().getDrawable(R.drawable.ic_avatar)).getBitmap();
    return sDefaultAvatarMedium;
  }

  public static Bitmap getMediumDefaultAvatar(Context paramContext, boolean paramBoolean)
  {
    if ((paramBoolean) && (sRoundAvatarsEnabled))
      if (sDefaultAvatarMediumRound == null)
        sDefaultAvatarMediumRound = ImageUtils.getRoundedBitmap(paramContext, getMediumDefaultAvatar(paramContext));
    for (Bitmap localBitmap = sDefaultAvatarMediumRound; ; localBitmap = getMediumDefaultAvatar(paramContext))
      return localBitmap;
  }

  public static int getSmallAvatarSize(Context paramContext)
  {
    if (sSmallAvatarSize == 0)
      sSmallAvatarSize = paramContext.getApplicationContext().getResources().getDimensionPixelSize(R.dimen.avatar_dimension);
    return sSmallAvatarSize;
  }

  public static Bitmap getSmallDefaultAvatar(Context paramContext)
  {
    if (sDefaultAvatarSmall == null)
      sDefaultAvatarSmall = ImageUtils.resizeToSquareBitmap(getMediumDefaultAvatar(paramContext), getSmallAvatarSize(paramContext), 0);
    return sDefaultAvatarSmall;
  }

  public static Bitmap getSmallDefaultAvatar(Context paramContext, boolean paramBoolean)
  {
    if ((paramBoolean) && (sRoundAvatarsEnabled))
      if (sDefaultAvatarSmallRound == null)
        sDefaultAvatarSmallRound = ImageUtils.getRoundedBitmap(paramContext, getSmallDefaultAvatar(paramContext));
    for (Bitmap localBitmap = sDefaultAvatarSmallRound; ; localBitmap = getSmallDefaultAvatar(paramContext))
      return localBitmap;
  }

  public static int getTinyAvatarSize(Context paramContext)
  {
    if (sTinyAvatarSize == 0)
      sTinyAvatarSize = paramContext.getApplicationContext().getResources().getDimensionPixelSize(R.dimen.tiny_avatar_dimension);
    return sTinyAvatarSize;
  }

  public static Bitmap getTinyDefaultAvatar(Context paramContext)
  {
    if (sDefaultAvatarTiny == null)
      sDefaultAvatarTiny = ImageUtils.resizeToSquareBitmap(getMediumDefaultAvatar(paramContext), getTinyAvatarSize(paramContext), 0);
    return sDefaultAvatarTiny;
  }

  public static Bitmap getTinyDefaultAvatar(Context paramContext, boolean paramBoolean)
  {
    if ((paramBoolean) && (sRoundAvatarsEnabled))
      if (sDefaultAvatarTinyRound == null)
        sDefaultAvatarTinyRound = ImageUtils.getRoundedBitmap(paramContext, getTinyDefaultAvatar(paramContext));
    for (Bitmap localBitmap = sDefaultAvatarTinyRound; ; localBitmap = getTinyDefaultAvatar(paramContext))
      return localBitmap;
  }

  // ERROR //
  private static void loadAndroidContactAvatars(Context paramContext, List<AvatarRequest> paramList, HashMap<AvatarRequest, byte[]> paramHashMap)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 230	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore_3
    //   5: aload_1
    //   6: invokeinterface 235 1 0
    //   11: istore 4
    //   13: iconst_0
    //   14: istore 5
    //   16: iload 5
    //   18: iload 4
    //   20: if_icmpge +289 -> 309
    //   23: aload_1
    //   24: iload 5
    //   26: invokeinterface 238 2 0
    //   31: checkcast 240	com/google/android/apps/plus/content/AvatarRequest
    //   34: astore 6
    //   36: iconst_0
    //   37: ifeq +208 -> 245
    //   40: getstatic 245	android/os/Build$VERSION:SDK_INT	I
    //   43: bipush 14
    //   45: if_icmpge +218 -> 263
    //   48: lconst_0
    //   49: lstore 15
    //   51: aload_3
    //   52: getstatic 251	android/provider/ContactsContract$Contacts:CONTENT_LOOKUP_URI	Landroid/net/Uri;
    //   55: aconst_null
    //   56: invokestatic 257	android/net/Uri:withAppendedPath	(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   59: iconst_1
    //   60: anewarray 30	java/lang/String
    //   63: dup
    //   64: iconst_0
    //   65: ldc_w 259
    //   68: aastore
    //   69: aconst_null
    //   70: aconst_null
    //   71: aconst_null
    //   72: invokevirtual 265	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   75: astore 17
    //   77: aload 17
    //   79: invokeinterface 271 1 0
    //   84: ifeq +17 -> 101
    //   87: aload 17
    //   89: iconst_0
    //   90: invokeinterface 275 2 0
    //   95: lstore 19
    //   97: lload 19
    //   99: lstore 15
    //   101: aload 17
    //   103: invokeinterface 278 1 0
    //   108: lload 15
    //   110: lconst_0
    //   111: lcmp
    //   112: ifeq +198 -> 310
    //   115: getstatic 283	android/provider/ContactsContract$Data:CONTENT_URI	Landroid/net/Uri;
    //   118: lload 15
    //   120: invokestatic 289	android/content/ContentUris:withAppendedId	(Landroid/net/Uri;J)Landroid/net/Uri;
    //   123: astore 7
    //   125: aconst_null
    //   126: astore 8
    //   128: aload 7
    //   130: ifnull +62 -> 192
    //   133: aload_3
    //   134: aload 7
    //   136: iconst_1
    //   137: anewarray 30	java/lang/String
    //   140: dup
    //   141: iconst_0
    //   142: ldc_w 291
    //   145: aastore
    //   146: aconst_null
    //   147: aconst_null
    //   148: aconst_null
    //   149: invokevirtual 265	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   152: astore 11
    //   154: aload 11
    //   156: invokeinterface 294 1 0
    //   161: istore 13
    //   163: aconst_null
    //   164: astore 8
    //   166: iload 13
    //   168: ifeq +17 -> 185
    //   171: aload 11
    //   173: iconst_0
    //   174: invokeinterface 298 2 0
    //   179: astore 14
    //   181: aload 14
    //   183: astore 8
    //   185: aload 11
    //   187: invokeinterface 278 1 0
    //   192: aload 8
    //   194: ifnull +42 -> 236
    //   197: aload_0
    //   198: aload 6
    //   200: invokevirtual 301	com/google/android/apps/plus/content/AvatarRequest:getSize	()I
    //   203: invokestatic 303	com/google/android/apps/plus/content/EsAvatarData:getAvatarSizeInPx	(Landroid/content/Context;I)I
    //   206: istore 10
    //   208: aload 6
    //   210: invokevirtual 306	com/google/android/apps/plus/content/AvatarRequest:isRounded	()Z
    //   213: ifeq +80 -> 293
    //   216: getstatic 28	com/google/android/apps/plus/content/EsAvatarData:sRoundAvatarsEnabled	Z
    //   219: ifeq +74 -> 293
    //   222: aload_0
    //   223: aload 8
    //   225: iload 10
    //   227: aload_0
    //   228: invokestatic 308	com/google/android/apps/plus/content/EsAvatarData:getAvatarBackgroundColor	(Landroid/content/Context;)I
    //   231: invokestatic 312	com/google/android/apps/plus/util/ImageUtils:resizeToRoundBitmap	(Landroid/content/Context;[BII)[B
    //   234: astore 8
    //   236: aload_2
    //   237: aload 6
    //   239: aload 8
    //   241: invokevirtual 53	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   244: pop
    //   245: iinc 5 1
    //   248: goto -232 -> 16
    //   251: astore 18
    //   253: aload 17
    //   255: invokeinterface 278 1 0
    //   260: aload 18
    //   262: athrow
    //   263: getstatic 251	android/provider/ContactsContract$Contacts:CONTENT_LOOKUP_URI	Landroid/net/Uri;
    //   266: aconst_null
    //   267: invokestatic 257	android/net/Uri:withAppendedPath	(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   270: ldc_w 314
    //   273: invokestatic 257	android/net/Uri:withAppendedPath	(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   276: astore 7
    //   278: goto -153 -> 125
    //   281: astore 12
    //   283: aload 11
    //   285: invokeinterface 278 1 0
    //   290: aload 12
    //   292: athrow
    //   293: aload 8
    //   295: iload 10
    //   297: aload_0
    //   298: invokestatic 308	com/google/android/apps/plus/content/EsAvatarData:getAvatarBackgroundColor	(Landroid/content/Context;)I
    //   301: invokestatic 317	com/google/android/apps/plus/util/ImageUtils:resizeToSquareBitmap	([BII)[B
    //   304: astore 8
    //   306: goto -70 -> 236
    //   309: return
    //   310: aconst_null
    //   311: astore 7
    //   313: goto -188 -> 125
    //
    // Exception table:
    //   from	to	target	type
    //   77	97	251	finally
    //   154	181	281	finally
  }

  private static byte[] loadAvatar(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, int paramInt)
  {
    AvatarImageRequest localAvatarImageRequest = new AvatarImageRequest(paramString1, paramString2, paramInt, getAvatarSizeInPx(paramContext, paramInt));
    byte[] arrayOfByte1 = EsMediaCache.getMedia(paramContext, localAvatarImageRequest);
    byte[] arrayOfByte2;
    if (arrayOfByte1 != null)
    {
      arrayOfByte2 = arrayOfByte1;
      return arrayOfByte2;
    }
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      if (arrayOfByte1 == null)
        ImageDownloader.downloadImage(paramContext, paramEsAccount, localAvatarImageRequest);
      arrayOfByte2 = arrayOfByte1;
      break;
      arrayOfByte1 = loadAvatar(paramContext, paramEsAccount, paramString1, paramString2, 2);
      if (arrayOfByte1 == null)
        arrayOfByte1 = loadAvatar(paramContext, paramEsAccount, paramString1, paramString2, 1);
      if (arrayOfByte1 != null)
      {
        arrayOfByte1 = ImageUtils.resizeToSquareBitmap(arrayOfByte1, getTinyAvatarSize(paramContext));
        if (arrayOfByte1 != null)
        {
          EsMediaCache.insertMedia(paramContext, localAvatarImageRequest, arrayOfByte1);
          continue;
          arrayOfByte1 = loadAvatar(paramContext, paramEsAccount, paramString1, paramString2, 2);
          if (arrayOfByte1 != null)
          {
            arrayOfByte1 = ImageUtils.resizeToSquareBitmap(arrayOfByte1, getSmallAvatarSize(paramContext));
            if (arrayOfByte1 != null)
              EsMediaCache.insertMedia(paramContext, localAvatarImageRequest, arrayOfByte1);
          }
        }
      }
    }
  }

  public static String loadAvatarUrl(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    Cursor localCursor = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase().query("contacts", AVATAR_URL_PROJECTION, "gaia_id=?", new String[] { paramString }, null, null, null);
    try
    {
      if (localCursor.moveToNext())
      {
        String str2 = localCursor.getString(1);
        if (!TextUtils.isEmpty(str2))
        {
          String str3 = uncompressAvatarUrl(str2);
          str1 = str3;
          return str1;
        }
      }
      localCursor.close();
      String str1 = null;
    }
    finally
    {
      localCursor.close();
    }
  }

  public static Map<AvatarRequest, byte[]> loadAvatars(Context paramContext, List<AvatarRequest> paramList)
  {
    HashMap localHashMap = new HashMap();
    EsAccount localEsAccount = EsAccountsData.getActiveAccount(paramContext);
    if (localEsAccount == null);
    while (true)
    {
      return localHashMap;
      int i = paramList.size();
      for (int j = 0; j < i; j++)
        ((AvatarRequest)paramList.get(j));
      if (0 != 0)
      {
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        for (int k = 0; k < i; k++)
          localArrayList2.add((AvatarRequest)paramList.get(k));
        loadAndroidContactAvatars(paramContext, localArrayList1, localHashMap);
        if (!localArrayList2.isEmpty())
          loadGooglePlusAvatars(paramContext, localEsAccount, localArrayList2, localHashMap);
      }
      else
      {
        loadGooglePlusAvatars(paramContext, localEsAccount, paramList, localHashMap);
      }
    }
  }

  private static void loadGooglePlusAvatars(Context paramContext, EsAccount paramEsAccount, List<AvatarRequest> paramList, HashMap<AvatarRequest, byte[]> paramHashMap)
  {
    ArrayList localArrayList = null;
    int i = paramList.size();
    for (int j = 0; j < i; j++)
    {
      AvatarRequest localAvatarRequest2 = (AvatarRequest)paramList.get(j);
      if (localAvatarRequest2.getAvatarUrl() == null)
      {
        if (localArrayList == null)
          localArrayList = new ArrayList();
        localArrayList.add(localAvatarRequest2);
      }
    }
    HashMap localHashMap = null;
    if (localArrayList != null)
    {
      localHashMap = new HashMap();
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
      int k = localArrayList.size();
      StringBuilder localStringBuilder = new StringBuilder();
      String[] arrayOfString = new String[k];
      localStringBuilder.append("gaia_id IN (");
      for (int m = 0; m < k; m++)
      {
        localStringBuilder.append("?,");
        arrayOfString[m] = ((AvatarRequest)localArrayList.get(m)).getGaiaId();
      }
      localStringBuilder.setLength(-1 + localStringBuilder.length());
      localStringBuilder.append(')');
      Cursor localCursor = localSQLiteDatabase.query("contacts", AVATAR_URL_PROJECTION, localStringBuilder.toString(), arrayOfString, null, null, null);
      try
      {
        if (localCursor.moveToNext())
          localHashMap.put(localCursor.getString(0), uncompressAvatarUrl(localCursor.getString(1)));
      }
      finally
      {
        localCursor.close();
      }
    }
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      AvatarRequest localAvatarRequest1 = (AvatarRequest)localIterator.next();
      String str = localAvatarRequest1.getAvatarUrl();
      if ((str == null) && (localHashMap != null))
        str = (String)localHashMap.get(localAvatarRequest1.getGaiaId());
      if (str == null)
      {
        paramHashMap.put(localAvatarRequest1, null);
      }
      else
      {
        byte[] arrayOfByte = loadAvatar(paramContext, paramEsAccount, localAvatarRequest1.getGaiaId(), str, localAvatarRequest1.getSize());
        if (arrayOfByte != null)
        {
          if ((localAvatarRequest1.isRounded()) && (sRoundAvatarsEnabled))
            arrayOfByte = ImageUtils.getRoundedBitmap(paramContext, arrayOfByte);
          paramHashMap.put(localAvatarRequest1, arrayOfByte);
        }
      }
    }
  }

  public static String uncompressAvatarUrl(String paramString)
  {
    String str;
    if (TextUtils.isEmpty(paramString))
    {
      str = null;
      return str;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("https://");
    if (paramString.charAt(0) == '~')
    {
      int i = paramString.indexOf('/');
      localStringBuilder.append((String)sFifeAbbrs.get(paramString.substring(0, i)));
      localStringBuilder.append(paramString.substring(i));
    }
    while (true)
    {
      if (paramString.endsWith("/"))
        localStringBuilder.append("photo.jpg");
      str = localStringBuilder.toString();
      break;
      localStringBuilder.append(paramString);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsAvatarData
 * JD-Core Version:    0.6.2
 */