package com.google.android.apps.plus.phone;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video.Media;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.MediaStoreUtils;

public final class PhotosHomeGridLoader extends EsCursorLoader
{
  private static final String[] CAMERA_PHOTO_PROJECTION = { "_id", "datetaken" };
  private static final Uri[] CAMERA_URI = arrayOfUri;
  public static final String[] PROJECTION = { "_id", "photo_count", "notification_count", "timestamp", "type", "album_id", "owner_id", "stream_id", "title", "photo_id_1", "url_1", "photo_id_2", "url_2", "photo_id_3", "url_3", "photo_id_4", "url_4", "photo_id_5", "url_5" };
  private static long sRowId;
  private final EsAccount mAccount;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  private boolean mObserverRegistered;
  private final String mOwnerGaiaId;
  private final boolean mPhotosHome;
  private final boolean mShowLocalCameraAlbum;
  private String mUserName;

  static
  {
    Uri[] arrayOfUri = new Uri[4];
    arrayOfUri[0] = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    arrayOfUri[1] = MediaStoreUtils.PHONE_STORAGE_IMAGES_URI;
    arrayOfUri[2] = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    arrayOfUri[3] = MediaStoreUtils.PHONE_STORAGE_VIDEO_URI;
  }

  public PhotosHomeGridLoader(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramContext, null);
    this.mAccount = paramEsAccount;
    this.mOwnerGaiaId = paramString1;
    this.mUserName = paramString2;
    this.mPhotosHome = paramBoolean1;
    this.mShowLocalCameraAlbum = paramBoolean2;
  }

  private void loadCameraAlbum(EsMatrixCursor paramEsMatrixCursor)
  {
    String str1 = getContext().getResources().getString(R.string.photos_home_local_label);
    Object localObject1 = null;
    ContentResolver localContentResolver = getContext().getContentResolver();
    int i = 0;
    Long[] arrayOfLong;
    while (true)
    {
      Cursor localCursor;
      if (i < CAMERA_URI.length)
      {
        localCursor = localContentResolver.query(CAMERA_URI[i], CAMERA_PHOTO_PROJECTION, null, null, "datetaken desc");
        if (localCursor == null);
      }
      try
      {
        if (localCursor.moveToFirst())
        {
          long l = localCursor.getLong(0);
          String str2 = ContentUris.withAppendedId(CAMERA_URI[i], l).toString();
          localObject1 = str2;
        }
        if (localCursor != null)
          localCursor.close();
        i++;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
    arrayOfLong[0] = Long.valueOf(0L);
    writeMatrix(paramEsMatrixCursor, null, null, null, "camera_photos", null, null, null, str1, arrayOfLong, new String[] { localObject1 });
  }

  private static String logDelta(long paramLong)
  {
    long l = System.currentTimeMillis() - paramLong;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(l / 1000L);
    localStringBuffer.append(".");
    localStringBuffer.append(l % 1000L);
    localStringBuffer.append(" sec");
    return localStringBuffer.toString();
  }

  private void processAlbumCursor(Cursor paramCursor, EsMatrixCursor paramEsMatrixCursor)
  {
    long l = System.currentTimeMillis();
    Object localObject = null;
    int i = 0;
    Long localLong1 = null;
    Long localLong2 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String[] arrayOfString = new String[1];
    Long[] arrayOfLong = new Long[1];
    while (paramCursor.moveToNext())
    {
      String str4 = paramCursor.getString(2);
      if (str4 != null)
      {
        label117: String str5;
        if (!TextUtils.equals(str4, (CharSequence)localObject))
        {
          if (localObject != null)
            writeMatrix(paramEsMatrixCursor, localLong1, null, localLong2, null, (String)localObject, str2, str3, str1, arrayOfLong, arrayOfString);
          localObject = str4;
          i = 0;
          if (paramCursor.isNull(1))
          {
            localLong1 = null;
            if (!paramCursor.isNull(5))
              break label250;
            localLong2 = null;
            label130: if (!paramCursor.isNull(6))
              break label265;
            str1 = null;
            label144: if (!paramCursor.isNull(3))
              break label278;
            str2 = null;
            label157: if (!paramCursor.isNull(4))
              break label290;
            str3 = null;
            label170: arrayOfString = new String[1];
            arrayOfLong = new Long[1];
          }
        }
        else
        {
          if (i > 0)
            continue;
          if (!paramCursor.isNull(8))
            break label302;
          str5 = null;
          label201: if (!paramCursor.isNull(7))
            break label315;
        }
        label265: label278: label290: label302: label315: for (Long localLong3 = null; ; localLong3 = Long.valueOf(paramCursor.getLong(7)))
        {
          arrayOfString[i] = str5;
          arrayOfLong[i] = localLong3;
          i++;
          break;
          localLong1 = Long.valueOf(paramCursor.getLong(1));
          break label117;
          label250: localLong2 = Long.valueOf(paramCursor.getLong(5));
          break label130;
          str1 = paramCursor.getString(6);
          break label144;
          str2 = paramCursor.getString(3);
          break label157;
          str3 = paramCursor.getString(4);
          break label170;
          str5 = paramCursor.getString(8);
          break label201;
        }
      }
    }
    if (localObject != null)
      writeMatrix(paramEsMatrixCursor, localLong1, null, localLong2, null, (String)localObject, str2, str3, str1, arrayOfLong, arrayOfString);
    if (EsLog.isLoggable("PhotosHomeLoader", 3))
      Log.d("PhotosHomeLoader", "#processAlbumCursor; " + logDelta(l));
  }

  private static void writeMatrix(EsMatrixCursor paramEsMatrixCursor, Long paramLong1, Long paramLong2, Long paramLong3, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Long[] paramArrayOfLong, String[] paramArrayOfString)
  {
    EsMatrixCursor.RowBuilder localRowBuilder = paramEsMatrixCursor.newRow();
    long l = sRowId;
    sRowId = 1L + l;
    localRowBuilder.add(Long.valueOf(l)).add(paramLong1).add(paramLong2).add(paramLong3).add(paramString1).add(paramString2).add(paramString3).add(paramString4).add(paramString5);
    if (paramArrayOfLong != null)
      for (int i = 0; i < paramArrayOfLong.length; i++)
        localRowBuilder.add(paramArrayOfLong[i]).add(paramArrayOfString[i]);
  }

  // ERROR //
  public final Cursor esLoadInBackground()
  {
    // Byte code:
    //   0: invokestatic 193	java/lang/System:currentTimeMillis	()J
    //   3: lstore_1
    //   4: new 256	com/google/android/apps/plus/phone/EsMatrixCursor
    //   7: dup
    //   8: getstatic 92	com/google/android/apps/plus/phone/PhotosHomeGridLoader:PROJECTION	[Ljava/lang/String;
    //   11: invokespecial 273	com/google/android/apps/plus/phone/EsMatrixCursor:<init>	([Ljava/lang/String;)V
    //   14: astore_3
    //   15: aload_0
    //   16: getfield 108	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mOwnerGaiaId	Ljava/lang/String;
    //   19: ifnull +1026 -> 1045
    //   22: aload_0
    //   23: getfield 112	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mPhotosHome	Z
    //   26: ifeq +1109 -> 1135
    //   29: invokestatic 193	java/lang/System:currentTimeMillis	()J
    //   32: lstore 37
    //   34: aload_0
    //   35: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   38: invokevirtual 142	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   41: getstatic 278	com/google/android/apps/plus/content/EsProvider:PHOTO_HOME_URI	Landroid/net/Uri;
    //   44: aload_0
    //   45: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   48: invokestatic 282	com/google/android/apps/plus/content/EsProvider:appendAccountParameter	(Landroid/net/Uri;Lcom/google/android/apps/plus/content/EsAccount;)Landroid/net/Uri;
    //   51: getstatic 285	com/google/android/apps/plus/phone/PhotosHomeQuery:PROJECTION	[Ljava/lang/String;
    //   54: aconst_null
    //   55: aconst_null
    //   56: ldc_w 287
    //   59: invokevirtual 150	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   62: astore 39
    //   64: aload 39
    //   66: ifnull +1609 -> 1675
    //   69: aload 39
    //   71: invokeinterface 291 1 0
    //   76: ifne +1599 -> 1675
    //   79: aload 39
    //   81: invokeinterface 173 1 0
    //   86: aconst_null
    //   87: astore 40
    //   89: aload 40
    //   91: ifnonnull +1577 -> 1668
    //   94: aload_0
    //   95: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   98: astore 64
    //   100: aload_0
    //   101: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   104: invokevirtual 296	com/google/android/apps/plus/content/EsAccount:getGaiaId	()Ljava/lang/String;
    //   107: astore 65
    //   109: new 298	com/google/android/apps/plus/api/PhotosOfUserOperation
    //   112: dup
    //   113: aload 64
    //   115: aload_0
    //   116: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   119: aconst_null
    //   120: aload 65
    //   122: iconst_1
    //   123: aconst_null
    //   124: aconst_null
    //   125: invokespecial 301	com/google/android/apps/plus/api/PhotosOfUserOperation:<init>	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;Ljava/lang/String;ZLandroid/content/Intent;Lcom/google/android/apps/plus/network/HttpOperation$OperationListener;)V
    //   128: invokevirtual 306	com/google/android/apps/plus/network/HttpOperation:start	()V
    //   131: new 308	com/google/android/apps/plus/api/PhotosInAlbumOperation
    //   134: dup
    //   135: aload 64
    //   137: aload_0
    //   138: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   141: aconst_null
    //   142: ldc_w 310
    //   145: aload 65
    //   147: iconst_1
    //   148: aconst_null
    //   149: aconst_null
    //   150: invokespecial 313	com/google/android/apps/plus/api/PhotosInAlbumOperation:<init>	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Lcom/google/android/apps/plus/service/EsSyncAdapterService$SyncState;Ljava/lang/String;Ljava/lang/String;ZLandroid/content/Intent;Lcom/google/android/apps/plus/network/HttpOperation$OperationListener;)V
    //   153: invokevirtual 306	com/google/android/apps/plus/network/HttpOperation:start	()V
    //   156: aload_0
    //   157: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   160: invokevirtual 142	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   163: getstatic 278	com/google/android/apps/plus/content/EsProvider:PHOTO_HOME_URI	Landroid/net/Uri;
    //   166: aload_0
    //   167: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   170: invokestatic 282	com/google/android/apps/plus/content/EsProvider:appendAccountParameter	(Landroid/net/Uri;Lcom/google/android/apps/plus/content/EsAccount;)Landroid/net/Uri;
    //   173: getstatic 285	com/google/android/apps/plus/phone/PhotosHomeQuery:PROJECTION	[Ljava/lang/String;
    //   176: aconst_null
    //   177: aconst_null
    //   178: ldc_w 287
    //   181: invokevirtual 150	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   184: astore 66
    //   186: aload 66
    //   188: astore 41
    //   190: aload 41
    //   192: ifnull +491 -> 683
    //   195: aload_0
    //   196: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   199: invokevirtual 126	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   202: astore 45
    //   204: aconst_null
    //   205: astore 46
    //   207: iconst_0
    //   208: istore 47
    //   210: aconst_null
    //   211: astore 48
    //   213: aconst_null
    //   214: astore 49
    //   216: aconst_null
    //   217: astore 50
    //   219: aconst_null
    //   220: astore 51
    //   222: aconst_null
    //   223: astore 52
    //   225: aconst_null
    //   226: astore 53
    //   228: iconst_1
    //   229: anewarray 27	java/lang/String
    //   232: astore 54
    //   234: iconst_1
    //   235: anewarray 175	java/lang/Long
    //   238: astore 55
    //   240: aload 41
    //   242: invokeinterface 216 1 0
    //   247: ifeq +408 -> 655
    //   250: aload 41
    //   252: iconst_4
    //   253: invokeinterface 217 2 0
    //   258: astore 56
    //   260: aload 56
    //   262: ldc_w 315
    //   265: invokestatic 223	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   268: ifeq +13 -> 281
    //   271: aload_0
    //   272: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   275: invokevirtual 318	com/google/android/apps/plus/content/EsAccount:isPlusPage	()Z
    //   278: ifne -38 -> 240
    //   281: aload 56
    //   283: aload 46
    //   285: invokestatic 223	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   288: ifne +1365 -> 1653
    //   291: aload 46
    //   293: ifnull +26 -> 319
    //   296: aload_3
    //   297: aload 48
    //   299: aload 49
    //   301: aload 50
    //   303: aload 46
    //   305: aconst_null
    //   306: aload 53
    //   308: aload 52
    //   310: aload 51
    //   312: aload 55
    //   314: aload 54
    //   316: invokestatic 185	com/google/android/apps/plus/phone/PhotosHomeGridLoader:writeMatrix	(Lcom/google/android/apps/plus/phone/EsMatrixCursor;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Long;[Ljava/lang/String;)V
    //   319: aconst_null
    //   320: astore 53
    //   322: aconst_null
    //   323: astore 52
    //   325: aload 41
    //   327: iconst_1
    //   328: invokeinterface 227 2 0
    //   333: ifeq +85 -> 418
    //   336: aconst_null
    //   337: astore 61
    //   339: aload 41
    //   341: iconst_2
    //   342: invokeinterface 227 2 0
    //   347: ifeq +87 -> 434
    //   350: aconst_null
    //   351: astore 62
    //   353: aload 41
    //   355: iconst_3
    //   356: invokeinterface 227 2 0
    //   361: ifeq +89 -> 450
    //   364: aconst_null
    //   365: astore 63
    //   367: aload 56
    //   369: ldc_w 320
    //   372: invokestatic 223	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   375: ifeq +91 -> 466
    //   378: aload 45
    //   380: getstatic 323	com/google/android/apps/plus/R$string:photos_home_of_you_label	I
    //   383: invokevirtual 138	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   386: astore 51
    //   388: aload 61
    //   390: astore 58
    //   392: iconst_1
    //   393: anewarray 27	java/lang/String
    //   396: astore 54
    //   398: iconst_1
    //   399: anewarray 175	java/lang/Long
    //   402: astore 55
    //   404: aload 63
    //   406: astore 50
    //   408: aload 62
    //   410: astore 49
    //   412: iconst_0
    //   413: istore 57
    //   415: goto +1267 -> 1682
    //   418: aload 41
    //   420: iconst_1
    //   421: invokeinterface 160 2 0
    //   426: invokestatic 179	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   429: astore 61
    //   431: goto -92 -> 339
    //   434: aload 41
    //   436: iconst_2
    //   437: invokeinterface 160 2 0
    //   442: invokestatic 179	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   445: astore 62
    //   447: goto -94 -> 353
    //   450: aload 41
    //   452: iconst_3
    //   453: invokeinterface 160 2 0
    //   458: invokestatic 179	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   461: astore 63
    //   463: goto -96 -> 367
    //   466: aload 56
    //   468: ldc_w 315
    //   471: invokestatic 223	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   474: ifeq +33 -> 507
    //   477: aload 45
    //   479: getstatic 326	com/google/android/apps/plus/R$string:photos_home_instant_upload_label	I
    //   482: invokevirtual 138	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   485: astore 51
    //   487: ldc_w 310
    //   490: astore 52
    //   492: aload_0
    //   493: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   496: invokevirtual 296	com/google/android/apps/plus/content/EsAccount:getGaiaId	()Ljava/lang/String;
    //   499: astore 53
    //   501: aconst_null
    //   502: astore 58
    //   504: goto -112 -> 392
    //   507: aload 56
    //   509: ldc 181
    //   511: invokestatic 223	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   514: ifeq +26 -> 540
    //   517: aload 45
    //   519: getstatic 132	com/google/android/apps/plus/R$string:photos_home_local_label	I
    //   522: invokevirtual 138	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   525: astore 51
    //   527: aload 61
    //   529: astore 58
    //   531: aconst_null
    //   532: astore 53
    //   534: aconst_null
    //   535: astore 52
    //   537: goto -145 -> 392
    //   540: aload 45
    //   542: getstatic 329	com/google/android/apps/plus/R$string:photos_home_unknown_label	I
    //   545: invokevirtual 138	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   548: astore 51
    //   550: aload 61
    //   552: astore 58
    //   554: aconst_null
    //   555: astore 53
    //   557: aconst_null
    //   558: astore 52
    //   560: goto -168 -> 392
    //   563: aload 41
    //   565: bipush 7
    //   567: invokeinterface 227 2 0
    //   572: ifeq +52 -> 624
    //   575: aconst_null
    //   576: astore 59
    //   578: aload 41
    //   580: bipush 6
    //   582: invokeinterface 227 2 0
    //   587: ifeq +51 -> 638
    //   590: aconst_null
    //   591: astore 60
    //   593: aload 54
    //   595: iload 57
    //   597: aload 59
    //   599: aastore
    //   600: aload 55
    //   602: iload 57
    //   604: aload 60
    //   606: aastore
    //   607: iload 57
    //   609: iconst_1
    //   610: iadd
    //   611: istore 47
    //   613: aload 58
    //   615: astore 48
    //   617: aload 56
    //   619: astore 46
    //   621: goto -381 -> 240
    //   624: aload 41
    //   626: bipush 7
    //   628: invokeinterface 217 2 0
    //   633: astore 59
    //   635: goto -57 -> 578
    //   638: aload 41
    //   640: bipush 6
    //   642: invokeinterface 160 2 0
    //   647: invokestatic 179	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   650: astore 60
    //   652: goto -59 -> 593
    //   655: aload 46
    //   657: ifnull +26 -> 683
    //   660: aload_3
    //   661: aload 48
    //   663: aload 49
    //   665: aload 50
    //   667: aload 46
    //   669: aconst_null
    //   670: aload 53
    //   672: aload 52
    //   674: aload 51
    //   676: aload 55
    //   678: aload 54
    //   680: invokestatic 185	com/google/android/apps/plus/phone/PhotosHomeGridLoader:writeMatrix	(Lcom/google/android/apps/plus/phone/EsMatrixCursor;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Long;[Ljava/lang/String;)V
    //   683: aload 41
    //   685: ifnull +10 -> 695
    //   688: aload 41
    //   690: invokeinterface 173 1 0
    //   695: ldc 229
    //   697: iconst_3
    //   698: invokestatic 235	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   701: ifeq +30 -> 731
    //   704: ldc 229
    //   706: new 237	java/lang/StringBuilder
    //   709: dup
    //   710: ldc_w 331
    //   713: invokespecial 242	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   716: lload 37
    //   718: invokestatic 244	com/google/android/apps/plus/phone/PhotosHomeGridLoader:logDelta	(J)Ljava/lang/String;
    //   721: invokevirtual 247	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   724: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   727: invokestatic 254	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   730: pop
    //   731: aload_0
    //   732: getfield 114	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mShowLocalCameraAlbum	Z
    //   735: ifeq +8 -> 743
    //   738: aload_0
    //   739: aload_3
    //   740: invokespecial 333	com/google/android/apps/plus/phone/PhotosHomeGridLoader:loadCameraAlbum	(Lcom/google/android/apps/plus/phone/EsMatrixCursor;)V
    //   743: invokestatic 193	java/lang/System:currentTimeMillis	()J
    //   746: lstore 9
    //   748: getstatic 336	com/google/android/apps/plus/content/EsProvider:ALBUM_VIEW_BY_OWNER_URI	Landroid/net/Uri;
    //   751: aload_0
    //   752: getfield 108	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mOwnerGaiaId	Ljava/lang/String;
    //   755: invokestatic 340	android/net/Uri:withAppendedPath	(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   758: aload_0
    //   759: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   762: invokestatic 282	com/google/android/apps/plus/content/EsProvider:appendAccountParameter	(Landroid/net/Uri;Lcom/google/android/apps/plus/content/EsAccount;)Landroid/net/Uri;
    //   765: astore 11
    //   767: aload_0
    //   768: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   771: invokevirtual 142	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   774: aload 11
    //   776: getstatic 343	com/google/android/apps/plus/phone/AlbumViewQuery:PROJECTION	[Ljava/lang/String;
    //   779: aconst_null
    //   780: aconst_null
    //   781: ldc_w 345
    //   784: invokevirtual 150	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   787: astore 12
    //   789: ldc 229
    //   791: iconst_3
    //   792: invokestatic 235	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   795: ifeq +52 -> 847
    //   798: new 237	java/lang/StringBuilder
    //   801: dup
    //   802: ldc_w 347
    //   805: invokespecial 242	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   808: aload 12
    //   810: invokevirtual 350	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   813: ldc_w 352
    //   816: invokevirtual 247	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   819: astore 23
    //   821: aload 12
    //   823: ifnonnull +661 -> 1484
    //   826: ldc_w 354
    //   829: astore 24
    //   831: ldc 229
    //   833: aload 23
    //   835: aload 24
    //   837: invokevirtual 350	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   840: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   843: invokestatic 254	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   846: pop
    //   847: aload 12
    //   849: ifnull +783 -> 1632
    //   852: aload 12
    //   854: invokeinterface 291 1 0
    //   859: ifne +773 -> 1632
    //   862: aload 12
    //   864: invokeinterface 173 1 0
    //   869: aconst_null
    //   870: astore 13
    //   872: aload 13
    //   874: ifnonnull +751 -> 1625
    //   877: new 356	com/google/android/apps/plus/api/UserPhotoAlbumsOperation
    //   880: dup
    //   881: aload_0
    //   882: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   885: aload_0
    //   886: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   889: aload_0
    //   890: getfield 108	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mOwnerGaiaId	Ljava/lang/String;
    //   893: aconst_null
    //   894: aconst_null
    //   895: invokespecial 359	com/google/android/apps/plus/api/UserPhotoAlbumsOperation:<init>	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Ljava/lang/String;Landroid/content/Intent;Lcom/google/android/apps/plus/network/HttpOperation$OperationListener;)V
    //   898: invokevirtual 306	com/google/android/apps/plus/network/HttpOperation:start	()V
    //   901: aload_0
    //   902: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   905: invokevirtual 142	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   908: aload 11
    //   910: getstatic 343	com/google/android/apps/plus/phone/AlbumViewQuery:PROJECTION	[Ljava/lang/String;
    //   913: aconst_null
    //   914: aconst_null
    //   915: ldc_w 345
    //   918: invokevirtual 150	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   921: astore 18
    //   923: aload 18
    //   925: astore 14
    //   927: ldc 229
    //   929: iconst_3
    //   930: invokestatic 235	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   933: ifeq +52 -> 985
    //   936: new 237	java/lang/StringBuilder
    //   939: dup
    //   940: ldc_w 361
    //   943: invokespecial 242	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   946: aload 14
    //   948: invokevirtual 350	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   951: ldc_w 352
    //   954: invokevirtual 247	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   957: astore 19
    //   959: aload 14
    //   961: ifnonnull +538 -> 1499
    //   964: ldc_w 354
    //   967: astore 21
    //   969: ldc 229
    //   971: aload 19
    //   973: aload 21
    //   975: invokevirtual 350	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   978: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   981: invokestatic 254	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   984: pop
    //   985: aload 14
    //   987: ifnull +10 -> 997
    //   990: aload_0
    //   991: aload 14
    //   993: aload_3
    //   994: invokespecial 363	com/google/android/apps/plus/phone/PhotosHomeGridLoader:processAlbumCursor	(Landroid/database/Cursor;Lcom/google/android/apps/plus/phone/EsMatrixCursor;)V
    //   997: aload 14
    //   999: ifnull +10 -> 1009
    //   1002: aload 14
    //   1004: invokeinterface 173 1 0
    //   1009: ldc 229
    //   1011: iconst_3
    //   1012: invokestatic 235	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1015: ifeq +30 -> 1045
    //   1018: ldc 229
    //   1020: new 237	java/lang/StringBuilder
    //   1023: dup
    //   1024: ldc_w 365
    //   1027: invokespecial 242	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1030: lload 9
    //   1032: invokestatic 244	com/google/android/apps/plus/phone/PhotosHomeGridLoader:logDelta	(J)Ljava/lang/String;
    //   1035: invokevirtual 247	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1038: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1041: invokestatic 254	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1044: pop
    //   1045: ldc 229
    //   1047: iconst_3
    //   1048: invokestatic 235	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1051: ifeq +29 -> 1080
    //   1054: ldc 229
    //   1056: new 237	java/lang/StringBuilder
    //   1059: dup
    //   1060: ldc_w 367
    //   1063: invokespecial 242	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1066: lload_1
    //   1067: invokestatic 244	com/google/android/apps/plus/phone/PhotosHomeGridLoader:logDelta	(J)Ljava/lang/String;
    //   1070: invokevirtual 247	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1073: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1076: invokestatic 254	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1079: pop
    //   1080: aload_3
    //   1081: areturn
    //   1082: astore 43
    //   1084: aload 39
    //   1086: ifnull +10 -> 1096
    //   1089: aload 39
    //   1091: invokeinterface 173 1 0
    //   1096: ldc 229
    //   1098: iconst_3
    //   1099: invokestatic 235	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1102: ifeq +30 -> 1132
    //   1105: ldc 229
    //   1107: new 237	java/lang/StringBuilder
    //   1110: dup
    //   1111: ldc_w 331
    //   1114: invokespecial 242	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1117: lload 37
    //   1119: invokestatic 244	com/google/android/apps/plus/phone/PhotosHomeGridLoader:logDelta	(J)Ljava/lang/String;
    //   1122: invokevirtual 247	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1125: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1128: invokestatic 254	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1131: pop
    //   1132: aload 43
    //   1134: athrow
    //   1135: aload_0
    //   1136: getfield 110	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mUserName	Ljava/lang/String;
    //   1139: ifnull -396 -> 743
    //   1142: getstatic 370	com/google/android/apps/plus/content/EsProvider:PHOTO_OF_USER_ID_URI	Landroid/net/Uri;
    //   1145: aload_0
    //   1146: getfield 108	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mOwnerGaiaId	Ljava/lang/String;
    //   1149: invokestatic 340	android/net/Uri:withAppendedPath	(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   1152: aload_0
    //   1153: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   1156: invokestatic 282	com/google/android/apps/plus/content/EsProvider:appendAccountParameter	(Landroid/net/Uri;Lcom/google/android/apps/plus/content/EsAccount;)Landroid/net/Uri;
    //   1159: astore 5
    //   1161: aload_0
    //   1162: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   1165: invokevirtual 142	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   1168: aload 5
    //   1170: getstatic 373	com/google/android/apps/plus/phone/PhotosOfUserQuery:PROJECTION	[Ljava/lang/String;
    //   1173: aconst_null
    //   1174: aconst_null
    //   1175: aconst_null
    //   1176: invokevirtual 150	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   1179: astore 6
    //   1181: aload 6
    //   1183: ifnull +463 -> 1646
    //   1186: aload 6
    //   1188: invokeinterface 291 1 0
    //   1193: ifne +453 -> 1646
    //   1196: aload 6
    //   1198: invokeinterface 173 1 0
    //   1203: aconst_null
    //   1204: astore 7
    //   1206: aload 7
    //   1208: ifnonnull +431 -> 1639
    //   1211: new 298	com/google/android/apps/plus/api/PhotosOfUserOperation
    //   1214: dup
    //   1215: aload_0
    //   1216: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   1219: aload_0
    //   1220: getfield 106	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mAccount	Lcom/google/android/apps/plus/content/EsAccount;
    //   1223: aload_0
    //   1224: getfield 108	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mOwnerGaiaId	Ljava/lang/String;
    //   1227: aconst_null
    //   1228: aconst_null
    //   1229: invokespecial 374	com/google/android/apps/plus/api/PhotosOfUserOperation:<init>	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Ljava/lang/String;Landroid/content/Intent;Lcom/google/android/apps/plus/network/HttpOperation$OperationListener;)V
    //   1232: invokevirtual 306	com/google/android/apps/plus/network/HttpOperation:start	()V
    //   1235: aload_0
    //   1236: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   1239: invokevirtual 142	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   1242: aload 5
    //   1244: getstatic 373	com/google/android/apps/plus/phone/PhotosOfUserQuery:PROJECTION	[Ljava/lang/String;
    //   1247: aconst_null
    //   1248: aconst_null
    //   1249: aconst_null
    //   1250: invokevirtual 150	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   1253: astore 36
    //   1255: aload 36
    //   1257: astore 8
    //   1259: aload 8
    //   1261: ifnull +138 -> 1399
    //   1264: aload 8
    //   1266: invokeinterface 216 1 0
    //   1271: ifeq +128 -> 1399
    //   1274: aload_0
    //   1275: invokevirtual 120	com/google/android/apps/plus/phone/PhotosHomeGridLoader:getContext	()Landroid/content/Context;
    //   1278: invokevirtual 126	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   1281: astore 28
    //   1283: aload 8
    //   1285: iconst_1
    //   1286: invokeinterface 227 2 0
    //   1291: ifeq +123 -> 1414
    //   1294: aconst_null
    //   1295: astore 29
    //   1297: aload 8
    //   1299: iconst_3
    //   1300: invokeinterface 227 2 0
    //   1305: ifeq +125 -> 1430
    //   1308: aconst_null
    //   1309: astore 30
    //   1311: aload 8
    //   1313: iconst_2
    //   1314: invokeinterface 227 2 0
    //   1319: ifeq +124 -> 1443
    //   1322: aconst_null
    //   1323: astore 32
    //   1325: getstatic 377	com/google/android/apps/plus/R$string:photos_of_user_label	I
    //   1328: istore 33
    //   1330: iconst_1
    //   1331: anewarray 379	java/lang/Object
    //   1334: astore 34
    //   1336: aload 34
    //   1338: iconst_0
    //   1339: aload_0
    //   1340: getfield 110	com/google/android/apps/plus/phone/PhotosHomeGridLoader:mUserName	Ljava/lang/String;
    //   1343: aastore
    //   1344: aload 28
    //   1346: iload 33
    //   1348: aload 34
    //   1350: invokevirtual 382	android/content/res/Resources:getString	(I[Ljava/lang/Object;)Ljava/lang/String;
    //   1353: astore 35
    //   1355: aload_3
    //   1356: aload 8
    //   1358: invokeinterface 291 1 0
    //   1363: i2l
    //   1364: invokestatic 179	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   1367: aconst_null
    //   1368: aload 29
    //   1370: ldc_w 320
    //   1373: aconst_null
    //   1374: aconst_null
    //   1375: aconst_null
    //   1376: aload 35
    //   1378: iconst_1
    //   1379: anewarray 175	java/lang/Long
    //   1382: dup
    //   1383: iconst_0
    //   1384: aload 32
    //   1386: aastore
    //   1387: iconst_1
    //   1388: anewarray 27	java/lang/String
    //   1391: dup
    //   1392: iconst_0
    //   1393: aload 30
    //   1395: aastore
    //   1396: invokestatic 185	com/google/android/apps/plus/phone/PhotosHomeGridLoader:writeMatrix	(Lcom/google/android/apps/plus/phone/EsMatrixCursor;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Long;[Ljava/lang/String;)V
    //   1399: aload 8
    //   1401: ifnull -658 -> 743
    //   1404: aload 8
    //   1406: invokeinterface 173 1 0
    //   1411: goto -668 -> 743
    //   1414: aload 8
    //   1416: iconst_1
    //   1417: invokeinterface 160 2 0
    //   1422: invokestatic 179	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   1425: astore 29
    //   1427: goto -130 -> 1297
    //   1430: aload 8
    //   1432: iconst_3
    //   1433: invokeinterface 217 2 0
    //   1438: astore 30
    //   1440: goto -129 -> 1311
    //   1443: aload 8
    //   1445: iconst_2
    //   1446: invokeinterface 160 2 0
    //   1451: invokestatic 179	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   1454: astore 31
    //   1456: aload 31
    //   1458: astore 32
    //   1460: goto -135 -> 1325
    //   1463: astore 26
    //   1465: aload 6
    //   1467: astore 27
    //   1469: aload 27
    //   1471: ifnull +10 -> 1481
    //   1474: aload 27
    //   1476: invokeinterface 173 1 0
    //   1481: aload 26
    //   1483: athrow
    //   1484: aload 12
    //   1486: invokeinterface 291 1 0
    //   1491: invokestatic 387	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1494: astore 24
    //   1496: goto -665 -> 831
    //   1499: aload 14
    //   1501: invokeinterface 291 1 0
    //   1506: invokestatic 387	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1509: astore 20
    //   1511: aload 20
    //   1513: astore 21
    //   1515: goto -546 -> 969
    //   1518: astore 15
    //   1520: aload 12
    //   1522: astore 14
    //   1524: aload 14
    //   1526: ifnull +10 -> 1536
    //   1529: aload 14
    //   1531: invokeinterface 173 1 0
    //   1536: ldc 229
    //   1538: iconst_3
    //   1539: invokestatic 235	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1542: ifeq +30 -> 1572
    //   1545: ldc 229
    //   1547: new 237	java/lang/StringBuilder
    //   1550: dup
    //   1551: ldc_w 365
    //   1554: invokespecial 242	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1557: lload 9
    //   1559: invokestatic 244	com/google/android/apps/plus/phone/PhotosHomeGridLoader:logDelta	(J)Ljava/lang/String;
    //   1562: invokevirtual 247	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1565: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1568: invokestatic 254	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1571: pop
    //   1572: aload 15
    //   1574: athrow
    //   1575: astore 15
    //   1577: aload 13
    //   1579: astore 14
    //   1581: goto -57 -> 1524
    //   1584: astore 15
    //   1586: goto -62 -> 1524
    //   1589: astore 26
    //   1591: aload 7
    //   1593: astore 27
    //   1595: goto -126 -> 1469
    //   1598: astore 26
    //   1600: aload 8
    //   1602: astore 27
    //   1604: goto -135 -> 1469
    //   1607: astore 43
    //   1609: aload 40
    //   1611: astore 39
    //   1613: goto -529 -> 1084
    //   1616: astore 43
    //   1618: aload 41
    //   1620: astore 39
    //   1622: goto -538 -> 1084
    //   1625: aload 13
    //   1627: astore 14
    //   1629: goto -644 -> 985
    //   1632: aload 12
    //   1634: astore 13
    //   1636: goto -764 -> 872
    //   1639: aload 7
    //   1641: astore 8
    //   1643: goto -384 -> 1259
    //   1646: aload 6
    //   1648: astore 7
    //   1650: goto -444 -> 1206
    //   1653: iload 47
    //   1655: istore 57
    //   1657: aload 46
    //   1659: astore 56
    //   1661: aload 48
    //   1663: astore 58
    //   1665: goto +17 -> 1682
    //   1668: aload 40
    //   1670: astore 41
    //   1672: goto -1482 -> 190
    //   1675: aload 39
    //   1677: astore 40
    //   1679: goto -1590 -> 89
    //   1682: iload 57
    //   1684: ifle -1121 -> 563
    //   1687: aload 58
    //   1689: astore 48
    //   1691: iload 57
    //   1693: istore 47
    //   1695: aload 56
    //   1697: astore 46
    //   1699: goto -1459 -> 240
    //
    // Exception table:
    //   from	to	target	type
    //   69	86	1082	finally
    //   1186	1203	1463	finally
    //   852	869	1518	finally
    //   877	923	1575	finally
    //   927	997	1584	finally
    //   1499	1511	1584	finally
    //   1211	1255	1589	finally
    //   1264	1399	1598	finally
    //   1414	1456	1598	finally
    //   94	186	1607	finally
    //   195	683	1616	finally
  }

  protected final void onAbandon()
  {
    if (this.mObserverRegistered)
    {
      getContext().getContentResolver().unregisterContentObserver(this.mObserver);
      this.mObserverRegistered = false;
    }
  }

  protected final void onReset()
  {
    onAbandon();
  }

  protected final void onStartLoading()
  {
    ContentResolver localContentResolver = getContext().getContentResolver();
    if (!this.mObserverRegistered)
    {
      if (this.mPhotosHome)
        localContentResolver.registerContentObserver(EsProvider.PHOTO_HOME_URI, false, this.mObserver);
      localContentResolver.registerContentObserver(Uri.withAppendedPath(EsProvider.PHOTO_OF_USER_ID_URI, this.mOwnerGaiaId), false, this.mObserver);
      localContentResolver.registerContentObserver(Uri.withAppendedPath(EsProvider.ALBUM_VIEW_BY_OWNER_URI, this.mOwnerGaiaId), false, this.mObserver);
      this.mObserverRegistered = true;
    }
    forceLoad();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotosHomeGridLoader
 * JD-Core Version:    0.6.2
 */