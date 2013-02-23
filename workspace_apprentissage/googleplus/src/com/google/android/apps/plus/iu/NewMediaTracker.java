package com.google.android.apps.plus.iu;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.ExifInterface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video.Media;
import android.text.format.DateFormat;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Fingerprint;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.GservicesConfig;
import com.google.android.apps.plus.util.Property;
import com.google.android.gsf.EsGservices;
import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class NewMediaTracker
{
  private static final Config[] ALL_CONFIGS = arrayOfConfig;
  private static final String[] EXIF_TAGS = { "FNumber", "DateTime", "ExposureTime", "Flash", "FocalLength", "GPSAltitude", "GPSAltitudeRef", "GPSDateStamp", "GPSLatitude", "GPSLatitudeRef", "GPSLongitude", "GPSLongitudeRef", "GPSProcessingMethod", "GPSTimeStamp", "ImageLength", "ImageWidth", "ISOSpeedRatings", "Make", "Model", "Orientation" };
  private static final String MEDIA_RECORD_TABLE;
  private static final String[] PROJECTION_ID;
  private static final String[] PROJECTION_MAX_ID = { "MAX(_id)" };
  private static final String SELECT_MEDIA_NOT_UPLOADED_BY_ACCOUNT;
  private static final int UPLOAD_ACCOUNT_INDEX;
  private static final int UPLOAD_REASON_INDEX;
  private static NewMediaTracker sMediaTracker;
  private final Context mContext;
  private final SharedPreferences mPreferences;
  private boolean mStopProcessing;
  private final TrackRecord[] mTrackRecords;
  private final UploadsDatabaseHelper mUploadsDbHelper;

  static
  {
    PROJECTION_ID = new String[] { "_id" };
    UPLOAD_REASON_INDEX = MediaRecordEntry.SCHEMA.getColumnIndex("upload_reason");
    UPLOAD_ACCOUNT_INDEX = MediaRecordEntry.SCHEMA.getColumnIndex("upload_account");
    MEDIA_RECORD_TABLE = MediaRecordEntry.SCHEMA.getTableName();
    SELECT_MEDIA_NOT_UPLOADED_BY_ACCOUNT = "upload_account IS NULL AND media_id NOT IN ( SELECT media_id FROM " + MEDIA_RECORD_TABLE + " WHERE upload_account" + " = ? )";
    Config[] arrayOfConfig = new Config[4];
    arrayOfConfig[0] = new Config("photo", MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "external");
    arrayOfConfig[1] = new Config("photo", MediaStore.Images.Media.getContentUri("phoneStorage"), "phoneStorage");
    arrayOfConfig[2] = new Config("video", MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "external");
    arrayOfConfig[3] = new Config("video", MediaStore.Video.Media.getContentUri("phoneStorage"), "phoneStorage");
  }

  private NewMediaTracker(Context paramContext)
  {
    this.mContext = paramContext;
    this.mPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    this.mUploadsDbHelper = UploadsDatabaseHelper.getInstance(paramContext);
    this.mTrackRecords = new TrackRecord[ALL_CONFIGS.length];
    loadStates();
  }

  public static void clearPreferences(Context paramContext)
  {
    try
    {
      if (sMediaTracker == null)
        clearPreferencesInternal(PreferenceManager.getDefaultSharedPreferences(paramContext));
      while (true)
      {
        return;
        sMediaTracker.mStopProcessing = true;
        sMediaTracker.resetPreferencesInternal();
      }
    }
    finally
    {
    }
  }

  private static void clearPreferencesInternal(SharedPreferences paramSharedPreferences)
  {
    SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
    Iterator localIterator = paramSharedPreferences.getAll().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.startsWith("media_scanner."))
        localEditor.remove(str);
    }
    localEditor.commit();
  }

  public static NewMediaTracker getInstance(Context paramContext)
  {
    try
    {
      if (sMediaTracker == null)
        sMediaTracker = new NewMediaTracker(paramContext);
      NewMediaTracker localNewMediaTracker = sMediaTracker;
      return localNewMediaTracker;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private static long getNextMediaId(ContentResolver paramContentResolver, Config paramConfig, long paramLong)
  {
    long l1 = -1L;
    Uri localUri = paramConfig.mMediaStoreUri;
    String[] arrayOfString1 = PROJECTION_ID;
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramLong);
    Cursor localCursor = paramContentResolver.query(localUri, arrayOfString1, "_id > ? AND _data LIKE '%/DCIM/%'", arrayOfString2, "_id");
    if (localCursor == null);
    while (true)
    {
      return l1;
      try
      {
        if (localCursor.moveToFirst())
        {
          long l3 = localCursor.getLong(0);
          l2 = l3;
          Utils.closeSilently(localCursor);
          l1 = l2;
          continue;
        }
        long l2 = l1;
      }
      finally
      {
        Utils.closeSilently(localCursor);
      }
    }
  }

  // ERROR //
  private static long getOptionalLong(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString, long paramLong)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aload_0
    //   4: aload_1
    //   5: aload_2
    //   6: aconst_null
    //   7: aconst_null
    //   8: aconst_null
    //   9: invokevirtual 267	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   12: astore 5
    //   14: aload 5
    //   16: invokeinterface 289 1 0
    //   21: ifeq +32 -> 53
    //   24: aload 5
    //   26: iconst_0
    //   27: invokeinterface 276 2 0
    //   32: lstore 10
    //   34: lload 10
    //   36: lstore 8
    //   38: aload 5
    //   40: ifnull +10 -> 50
    //   43: aload 5
    //   45: invokeinterface 292 1 0
    //   50: lload 8
    //   52: lreturn
    //   53: lconst_0
    //   54: lstore 8
    //   56: goto -18 -> 38
    //   59: astore 7
    //   61: aload 5
    //   63: ifnull +10 -> 73
    //   66: aload 5
    //   68: invokeinterface 292 1 0
    //   73: lconst_0
    //   74: lstore 8
    //   76: goto -26 -> 50
    //   79: astore 6
    //   81: aload 5
    //   83: ifnull +10 -> 93
    //   86: aload 5
    //   88: invokeinterface 292 1 0
    //   93: aload 6
    //   95: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   3	34	59	java/lang/Exception
    //   3	34	79	finally
  }

  // ERROR //
  private static String getOptionalString(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aload_0
    //   3: aload_1
    //   4: aload_2
    //   5: aconst_null
    //   6: aconst_null
    //   7: aconst_null
    //   8: invokevirtual 267	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   11: astore_3
    //   12: aload_3
    //   13: invokeinterface 289 1 0
    //   18: ifeq +29 -> 47
    //   21: aload_3
    //   22: iconst_0
    //   23: invokeinterface 298 2 0
    //   28: astore 7
    //   30: aload 7
    //   32: astore 6
    //   34: aload_3
    //   35: ifnull +9 -> 44
    //   38: aload_3
    //   39: invokeinterface 292 1 0
    //   44: aload 6
    //   46: areturn
    //   47: aconst_null
    //   48: astore 6
    //   50: goto -16 -> 34
    //   53: astore 5
    //   55: aload_3
    //   56: ifnull +9 -> 65
    //   59: aload_3
    //   60: invokeinterface 292 1 0
    //   65: aconst_null
    //   66: astore 6
    //   68: goto -24 -> 44
    //   71: astore 4
    //   73: aload_3
    //   74: ifnull +9 -> 83
    //   77: aload_3
    //   78: invokeinterface 292 1 0
    //   83: aload 4
    //   85: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   2	30	53	java/lang/Exception
    //   2	30	71	finally
  }

  private static boolean hasExif(ExifInterface paramExifInterface)
  {
    int i = -1 + EXIF_TAGS.length;
    if (i > 0)
      if (paramExifInterface.getAttribute(EXIF_TAGS[i]) == null);
    for (boolean bool = true; ; bool = false)
    {
      return bool;
      i--;
      break;
    }
  }

  private static boolean hasGoogleUploadExif(ContentResolver paramContentResolver, Uri paramUri)
  {
    String str1 = getOptionalString(paramContentResolver, paramUri, new String[] { "_data" });
    boolean bool1 = false;
    if (str1 != null)
    {
      String str2 = MimeTypeMap.getFileExtensionFromUrl(str1);
      if ("jpg".equalsIgnoreCase(str2))
        break label58;
      boolean bool2 = "jpeg".equalsIgnoreCase(str2);
      bool1 = false;
      if (bool2)
        break label58;
    }
    while (true)
    {
      return bool1;
      try
      {
        label58: String str3 = new ExifInterface(str1).getAttribute("Software");
        bool1 = false;
        if (str3 != null)
        {
          int i = str3.indexOf("Google");
          bool1 = false;
          if (i >= 0)
            bool1 = true;
        }
      }
      catch (Throwable localThrowable)
      {
        bool1 = false;
      }
    }
  }

  private static boolean isStorageAvailable(ContentResolver paramContentResolver, Config paramConfig)
  {
    Cursor localCursor = null;
    try
    {
      localCursor = paramContentResolver.query(paramConfig.mMediaStoreUri, PROJECTION_MAX_ID, null, null, null);
      boolean bool;
      if (localCursor != null)
        if (localCursor.moveToFirst())
        {
          long l = localCursor.getLong(0);
          if (l >= 0L)
            bool = true;
        }
      while (true)
      {
        return bool;
        bool = false;
        continue;
        bool = false;
        continue;
        if (EsLog.isLoggable("iu.UploadsManager", 5))
          Log.w("iu.UploadsManager", " ***** null cursor for " + paramConfig.mStorage);
        Utils.closeSilently(localCursor);
        bool = false;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        if (EsLog.isLoggable("iu.UploadsManager", 5))
          Log.w("iu.UploadsManager", " ***** exception for " + paramConfig.mStorage, localException);
        Utils.closeSilently(localCursor);
      }
    }
    finally
    {
      Utils.closeSilently(localCursor);
    }
  }

  private void loadStates()
  {
    for (int i = -1 + this.mTrackRecords.length; i >= 0; i--)
    {
      Config localConfig = ALL_CONFIGS[i];
      TrackRecord localTrackRecord = new TrackRecord(localConfig);
      localTrackRecord.mLastMediaId = this.mPreferences.getLong(localConfig.mKeyLastMediaId, 0L);
      this.mTrackRecords[i] = localTrackRecord;
    }
  }

  private boolean performSanityChecks(ContentResolver paramContentResolver, UploadSettings paramUploadSettings, Uri paramUri, boolean paramBoolean)
  {
    long l1 = getOptionalLong(paramContentResolver, paramUri, new String[] { "datetaken" }, 0L);
    boolean bool;
    if ((l1 > 0L) && ((l1 > paramUploadSettings.getEventEndTime()) || (l1 < paramUploadSettings.getEventStartTime())))
    {
      if (EsLog.isLoggable("iu.UploadsManager", 4))
      {
        Date localDate2 = new Date(l1);
        CharSequence localCharSequence2 = DateFormat.format("MMM dd, yyyy h:mmaa", localDate2);
        if (EsLog.isLoggable("iu.UploadsManager", 4))
          Log.i("iu.UploadsManager", "FAIL: bad taken time; taken: " + localCharSequence2);
      }
      bool = false;
    }
    while (true)
    {
      return bool;
      String str1 = getOptionalString(paramContentResolver, paramUri, new String[] { "_data" });
      if (str1 != null)
      {
        long l2 = new File(str1).lastModified();
        if ((l2 > 0L) && ((l2 > paramUploadSettings.getEventEndTime()) || (l2 < paramUploadSettings.getEventStartTime())))
        {
          if (EsLog.isLoggable("iu.UploadsManager", 4))
          {
            Date localDate1 = new Date(l2);
            CharSequence localCharSequence1 = DateFormat.format("MMM dd, yyyy h:mmaa", localDate1);
            if (EsLog.isLoggable("iu.UploadsManager", 4))
              Log.i("iu.UploadsManager", "FAIL: bad modify time; modified: " + localCharSequence1);
          }
          bool = false;
        }
      }
      else if ((str1 != null) && (str1.lastIndexOf("cache/com.google.android.googlephotos") != -1))
      {
        Log.w("iu.UploadsManager", "FAIL: file from cache directory; path: " + str1);
        bool = false;
      }
      else if ((paramBoolean) && (str1 != null))
      {
        String str2 = MimeTypeMap.getFileExtensionFromUrl(str1);
        if ((!"jpg".equalsIgnoreCase(str2)) && (!"jpeg".equalsIgnoreCase(str2)))
        {
          if (EsLog.isLoggable("iu.UploadsManager", 4))
            Log.i("iu.UploadsManager", "FAIL: " + str1 + " is not a jpeg");
          bool = false;
        }
        else
        {
          try
          {
            ExifInterface localExifInterface = new ExifInterface(str1);
            if (!hasExif(localExifInterface))
            {
              if (!EsLog.isLoggable("iu.UploadsManager", 4))
                break label622;
              Log.i("iu.UploadsManager", "FAIL: " + str1 + " does not contain any EXIF data");
              break label622;
            }
            String str3 = localExifInterface.getAttribute("Software");
            if ((str3 == null) || (str3.indexOf("Google") < 0))
              break label555;
            if (EsLog.isLoggable("iu.UploadsManager", 4))
              Log.i("iu.UploadsManager", "FAIL: " + str1 + " has GOOGLE_EXIF_TAG set");
            bool = false;
          }
          catch (Throwable localThrowable)
          {
            Log.w("iu.UploadsManager", "FAIL: could get EXIF for file: " + str1);
            bool = false;
          }
        }
      }
      else
      {
        label555: if (GservicesConfig.isInstantShareEnabled(this.mContext))
        {
          if (!paramBoolean)
            if ((!EsGservices.getBoolean(this.mContext.getContentResolver(), "plusone:enable_instant_share_video", true)) && (!Property.ENABLE_INSTANT_SHARE_VIDEO.getBoolean()))
              break label610;
          label610: for (int i = 1; ; i = 0)
          {
            if (i == 0)
              break label616;
            bool = true;
            break;
          }
        }
        label616: bool = false;
        continue;
        label622: bool = false;
      }
    }
  }

  private void resetPreferencesInternal()
  {
    try
    {
      clearPreferencesInternal(this.mPreferences);
      loadStates();
      this.mStopProcessing = false;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void saveStates()
  {
    SharedPreferences.Editor localEditor = this.mPreferences.edit();
    for (TrackRecord localTrackRecord : this.mTrackRecords)
      localEditor.putLong(localTrackRecord.mConfig.mKeyLastMediaId, localTrackRecord.mLastMediaId);
    localEditor.commit();
  }

  final void cancelUpload(String paramString, int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.mUploadsDbHelper.getReadableDatabase();
    String str = MEDIA_RECORD_TABLE;
    String[] arrayOfString1 = MediaRecordEntry.SCHEMA.getProjection();
    String[] arrayOfString2 = new String[2];
    arrayOfString2[0] = paramString;
    arrayOfString2[1] = Integer.toString(40);
    Cursor localCursor = localSQLiteDatabase.query(str, arrayOfString1, "upload_account = ? AND upload_reason = ? AND upload_state < 200", arrayOfString2, null, null, null);
    try
    {
      if (localCursor.moveToNext())
      {
        MediaRecordEntry localMediaRecordEntry = MediaRecordEntry.fromCursor(localCursor);
        MediaRecordEntry.SCHEMA.deleteWithId(this.mUploadsDbHelper.getWritableDatabase(), localMediaRecordEntry.id);
      }
    }
    finally
    {
      localCursor.close();
    }
  }

  final UploadTaskEntry getNextUpload(HashSet<Fingerprint> paramHashSet, PicasaSyncHelper.SyncContext paramSyncContext)
  {
    Cursor localCursor = this.mUploadsDbHelper.getReadableDatabase().query(MEDIA_RECORD_TABLE, MediaRecordEntry.SCHEMA.getProjection(), "upload_account NOT NULL AND upload_state < 200", null, null, null, "upload_reason ASC, is_image DESC");
    MediaRecordEntry localMediaRecordEntry;
    Uri localUri;
    String str2;
    Object localObject2;
    while (true)
    {
      FingerprintHelper localFingerprintHelper;
      String str1;
      int i;
      try
      {
        boolean bool1 = GservicesConfig.isInstantShareEnabled(this.mContext);
        localFingerprintHelper = FingerprintHelper.get(this.mContext);
        if ((!localCursor.moveToNext()) || ((paramSyncContext != null) && (paramSyncContext.syncInterrupted())))
          break label606;
        localMediaRecordEntry = MediaRecordEntry.fromCursor(localCursor);
        str1 = localMediaRecordEntry.getMediaUrl();
        localUri = Uri.parse(str1);
        i = localCursor.getInt(UPLOAD_REASON_INDEX);
        str2 = localCursor.getString(UPLOAD_ACCOUNT_INDEX);
        if (i == 20)
        {
          localObject2 = null;
          if (bool1)
            break;
          if (EsLog.isLoggable("iu.UploadsManager", 4))
            Log.i("iu.UploadsManager", "+++ SKIP record; instant share disabled; " + localMediaRecordEntry);
          localMediaRecordEntry.setState(400, 36);
          MediaRecordEntry.SCHEMA.insertOrReplace(this.mUploadsDbHelper.getWritableDatabase(), localMediaRecordEntry);
          continue;
        }
      }
      finally
      {
        localCursor.close();
      }
      if (i == 40)
      {
        Fingerprint localFingerprint = localFingerprintHelper.getCachedFingerprint(str1);
        localObject2 = null;
        if (localFingerprint == null)
        {
          localFingerprint = localFingerprintHelper.getFingerprint(str1, true);
          localObject2 = localFingerprint;
        }
        if (localFingerprint == null)
        {
          if (EsLog.isLoggable("iu.UploadsManager", 4))
            Log.i("iu.UploadsManager", "+++ SKIP record; no fingerprint; " + localMediaRecordEntry);
          if (EsLog.isLoggable("iu.UploadsManager", 5))
            Log.w("iu.UploadsManager", "no fingerprint; skip upload for " + str1);
          localMediaRecordEntry.setState(400, 35);
          MediaRecordEntry.SCHEMA.insertOrReplace(this.mUploadsDbHelper.getWritableDatabase(), localMediaRecordEntry);
        }
        else
        {
          if ((paramHashSet == null) || (!paramHashSet.contains(localFingerprint)))
            break;
          if (EsLog.isLoggable("iu.UploadsManager", 4))
            Log.i("iu.UploadsManager", "+++ SKIP record; duplicate upload; " + localMediaRecordEntry);
          localMediaRecordEntry.setState(300, 34);
          MediaRecordEntry.SCHEMA.insertOrReplace(this.mUploadsDbHelper.getWritableDatabase(), localMediaRecordEntry);
        }
      }
      else
      {
        boolean bool2 = localMediaRecordEntry.isImage();
        localObject2 = null;
        if (!bool2)
          break;
        boolean bool3 = hasGoogleUploadExif(this.mContext.getContentResolver(), localUri);
        localObject2 = null;
        if (!bool3)
          break;
        if (EsLog.isLoggable("iu.UploadsManager", 4))
          Log.i("iu.UploadsManager", "+++ SKIP record; has google exif; " + localMediaRecordEntry);
        localMediaRecordEntry.setState(400, 37);
        MediaRecordEntry.SCHEMA.insertOrReplace(this.mUploadsDbHelper.getWritableDatabase(), localMediaRecordEntry);
      }
    }
    UploadTaskEntry localUploadTaskEntry = UploadTaskEntry.createNew(UploadRequestHelper.createUploadTask(str2, localUri, localMediaRecordEntry.id, localMediaRecordEntry.getAlbumId(), localMediaRecordEntry.getEventId()));
    if (localObject2 != null)
      localUploadTaskEntry.setFingerprint(localObject2);
    if (EsLog.isLoggable("iu.UploadsManager", 4))
      Log.i("iu.UploadsManager", "+++ START; upload started; " + localMediaRecordEntry);
    localCursor.close();
    while (true)
    {
      return localUploadTaskEntry;
      label606: localCursor.close();
      localUploadTaskEntry = null;
    }
  }

  final int getUploadProgress(String paramString, int paramInt)
  {
    int i = 0;
    if (paramString == null);
    while (true)
    {
      return i;
      SQLiteDatabase localSQLiteDatabase = this.mUploadsDbHelper.getReadableDatabase();
      String str = MEDIA_RECORD_TABLE;
      String[] arrayOfString1 = { "COUNT(*)" };
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = paramString;
      arrayOfString2[1] = Integer.toString(paramInt);
      Cursor localCursor = localSQLiteDatabase.query(str, arrayOfString1, "upload_account = ? AND upload_reason = ? AND upload_state < 200", arrayOfString2, null, null, null);
      try
      {
        if (localCursor.moveToNext())
        {
          int k = localCursor.getInt(0);
          j = k;
          localCursor.close();
          i = j;
          continue;
        }
        int j = 0;
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  final int getUploadTotal$505cff29(int paramInt)
  {
    Cursor localCursor = this.mUploadsDbHelper.getReadableDatabase().query(true, MEDIA_RECORD_TABLE, new String[] { "COUNT(*)" }, "upload_account IS NULL", null, null, null, null, null);
    try
    {
      if (localCursor.moveToNext())
      {
        int j = localCursor.getInt(0);
        i = j;
        return i;
      }
      int i = 0;
    }
    finally
    {
      localCursor.close();
    }
  }

  final void onUploadComplete(UploadTaskEntry paramUploadTaskEntry)
  {
    MediaRecordEntry localMediaRecordEntry = MediaRecordEntry.fromId(this.mUploadsDbHelper.getReadableDatabase(), paramUploadTaskEntry.getMediaRecordId());
    if (localMediaRecordEntry == null)
      Log.w("iu.UploadsManager", "Could not get media record for task: " + paramUploadTaskEntry);
    while (true)
    {
      return;
      localMediaRecordEntry.setState(300);
      MediaRecordEntry.SCHEMA.insertOrReplace(this.mUploadsDbHelper.getWritableDatabase(), localMediaRecordEntry);
    }
  }

  // ERROR //
  public final int processNewMedia()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new 628	android/content/ContentValues
    //   5: dup
    //   6: invokespecial 629	android/content/ContentValues:<init>	()V
    //   9: astore_1
    //   10: aload_0
    //   11: getfield 163	com/google/android/apps/plus/iu/NewMediaTracker:mContext	Landroid/content/Context;
    //   14: invokestatic 632	com/google/android/apps/plus/iu/UploadSettings:getInstance	(Landroid/content/Context;)Lcom/google/android/apps/plus/iu/UploadSettings;
    //   17: astore_3
    //   18: aload_0
    //   19: getfield 163	com/google/android/apps/plus/iu/NewMediaTracker:mContext	Landroid/content/Context;
    //   22: invokevirtual 448	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   25: astore 4
    //   27: aload_0
    //   28: getfield 179	com/google/android/apps/plus/iu/NewMediaTracker:mUploadsDbHelper	Lcom/google/android/apps/plus/iu/UploadsDatabaseHelper;
    //   31: invokevirtual 501	com/google/android/apps/plus/iu/UploadsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   34: astore 5
    //   36: iconst_0
    //   37: istore 6
    //   39: invokestatic 637	java/lang/System:currentTimeMillis	()J
    //   42: lstore 7
    //   44: new 639	java/lang/StringBuffer
    //   47: dup
    //   48: invokespecial 640	java/lang/StringBuffer:<init>	()V
    //   51: astore 9
    //   53: aload_0
    //   54: getfield 171	com/google/android/apps/plus/iu/NewMediaTracker:mPreferences	Landroid/content/SharedPreferences;
    //   57: ldc_w 642
    //   60: iconst_0
    //   61: invokeinterface 645 3 0
    //   66: istore 10
    //   68: ldc_w 338
    //   71: iconst_4
    //   72: invokestatic 344	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   75: ifeq +13 -> 88
    //   78: ldc_w 338
    //   81: ldc_w 647
    //   84: invokestatic 409	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   87: pop
    //   88: iconst_m1
    //   89: aload_0
    //   90: getfield 183	com/google/android/apps/plus/iu/NewMediaTracker:mTrackRecords	[Lcom/google/android/apps/plus/iu/NewMediaTracker$TrackRecord;
    //   93: arraylength
    //   94: iadd
    //   95: istore 11
    //   97: iload 11
    //   99: iflt +866 -> 965
    //   102: aload_0
    //   103: getfield 195	com/google/android/apps/plus/iu/NewMediaTracker:mStopProcessing	Z
    //   106: ifne +859 -> 965
    //   109: aload_0
    //   110: getfield 183	com/google/android/apps/plus/iu/NewMediaTracker:mTrackRecords	[Lcom/google/android/apps/plus/iu/NewMediaTracker$TrackRecord;
    //   113: iload 11
    //   115: aaload
    //   116: astore 14
    //   118: aload 4
    //   120: aload 14
    //   122: getfield 469	com/google/android/apps/plus/iu/NewMediaTracker$TrackRecord:mConfig	Lcom/google/android/apps/plus/iu/NewMediaTracker$Config;
    //   125: invokestatic 649	com/google/android/apps/plus/iu/NewMediaTracker:isStorageAvailable	(Landroid/content/ContentResolver;Lcom/google/android/apps/plus/iu/NewMediaTracker$Config;)Z
    //   128: ifeq +911 -> 1039
    //   131: aload 14
    //   133: getfield 469	com/google/android/apps/plus/iu/NewMediaTracker$TrackRecord:mConfig	Lcom/google/android/apps/plus/iu/NewMediaTracker$Config;
    //   136: astore 15
    //   138: aload 4
    //   140: aload 15
    //   142: aload 14
    //   144: getfield 373	com/google/android/apps/plus/iu/NewMediaTracker$TrackRecord:mLastMediaId	J
    //   147: invokestatic 651	com/google/android/apps/plus/iu/NewMediaTracker:getNextMediaId	(Landroid/content/ContentResolver;Lcom/google/android/apps/plus/iu/NewMediaTracker$Config;J)J
    //   150: lstore 16
    //   152: lload 16
    //   154: ldc2_w 251
    //   157: lcmp
    //   158: ifne +44 -> 202
    //   161: ldc_w 338
    //   164: iconst_4
    //   165: invokestatic 344	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   168: ifeq +871 -> 1039
    //   171: ldc_w 338
    //   174: new 110	java/lang/StringBuilder
    //   177: dup
    //   178: ldc_w 653
    //   181: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   184: aload 15
    //   186: getfield 656	com/google/android/apps/plus/iu/NewMediaTracker$Config:mMediaType	Ljava/lang/String;
    //   189: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   195: invokestatic 409	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   198: pop
    //   199: goto +840 -> 1039
    //   202: ldc_w 338
    //   205: iconst_4
    //   206: invokestatic 344	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   209: ifeq +33 -> 242
    //   212: aload 9
    //   214: iconst_0
    //   215: invokevirtual 660	java/lang/StringBuffer:setLength	(I)V
    //   218: aload 9
    //   220: new 110	java/lang/StringBuilder
    //   223: dup
    //   224: ldc_w 662
    //   227: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   230: lload 16
    //   232: invokevirtual 665	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   235: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   238: invokevirtual 668	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   241: pop
    //   242: aload 14
    //   244: lload 16
    //   246: putfield 373	com/google/android/apps/plus/iu/NewMediaTracker$TrackRecord:mLastMediaId	J
    //   249: aload_0
    //   250: invokespecial 670	com/google/android/apps/plus/iu/NewMediaTracker:saveStates	()V
    //   253: aload 15
    //   255: getfield 255	com/google/android/apps/plus/iu/NewMediaTracker$Config:mMediaStoreUri	Landroid/net/Uri;
    //   258: invokevirtual 674	android/net/Uri:buildUpon	()Landroid/net/Uri$Builder;
    //   261: lload 16
    //   263: invokestatic 259	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   266: invokevirtual 680	android/net/Uri$Builder:appendEncodedPath	(Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   269: invokevirtual 684	android/net/Uri$Builder:build	()Landroid/net/Uri;
    //   272: astore 18
    //   274: ldc_w 686
    //   277: ldc 133
    //   279: invokevirtual 689	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   282: ifne +763 -> 1045
    //   285: aload 15
    //   287: getfield 656	com/google/android/apps/plus/iu/NewMediaTracker$Config:mMediaType	Ljava/lang/String;
    //   290: ldc 133
    //   292: invokevirtual 689	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   295: ifeq +756 -> 1051
    //   298: goto +747 -> 1045
    //   301: aload_1
    //   302: invokevirtual 692	android/content/ContentValues:clear	()V
    //   305: aload 5
    //   307: lload 16
    //   309: invokestatic 695	com/google/android/apps/plus/iu/MediaRecordEntry:fromMediaId	(Landroid/database/sqlite/SQLiteDatabase;J)Lcom/google/android/apps/plus/iu/MediaRecordEntry;
    //   312: ifnull +47 -> 359
    //   315: ldc_w 338
    //   318: new 110	java/lang/StringBuilder
    //   321: dup
    //   322: ldc_w 697
    //   325: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   328: lload 16
    //   330: invokevirtual 665	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   333: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   336: invokestatic 355	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   339: pop
    //   340: lload 16
    //   342: ldc2_w 251
    //   345: lcmp
    //   346: ifeq +693 -> 1039
    //   349: aload_0
    //   350: getfield 195	com/google/android/apps/plus/iu/NewMediaTracker:mStopProcessing	Z
    //   353: ifeq -215 -> 138
    //   356: goto +683 -> 1039
    //   359: aload_1
    //   360: ldc_w 699
    //   363: invokevirtual 702	android/content/ContentValues:putNull	(Ljava/lang/String;)V
    //   366: aload_1
    //   367: ldc_w 704
    //   370: invokevirtual 702	android/content/ContentValues:putNull	(Ljava/lang/String;)V
    //   373: aload_1
    //   374: ldc 100
    //   376: invokevirtual 702	android/content/ContentValues:putNull	(Ljava/lang/String;)V
    //   379: aload_1
    //   380: ldc_w 706
    //   383: iload 19
    //   385: invokestatic 711	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   388: invokevirtual 715	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Boolean;)V
    //   391: aload_1
    //   392: ldc_w 717
    //   395: lload 16
    //   397: invokestatic 722	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   400: invokevirtual 725	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   403: aload 4
    //   405: aload 18
    //   407: iconst_1
    //   408: anewarray 32	java/lang/String
    //   411: dup
    //   412: iconst_0
    //   413: ldc_w 377
    //   416: aastore
    //   417: lconst_0
    //   418: invokestatic 379	com/google/android/apps/plus/iu/NewMediaTracker:getOptionalLong	(Landroid/content/ContentResolver;Landroid/net/Uri;[Ljava/lang/String;J)J
    //   421: lstore 20
    //   423: lload 20
    //   425: lconst_0
    //   426: lcmp
    //   427: ifle +364 -> 791
    //   430: aload_1
    //   431: ldc_w 727
    //   434: lload 20
    //   436: invokestatic 722	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   439: invokevirtual 725	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   442: aload 4
    //   444: aload 18
    //   446: iconst_1
    //   447: anewarray 32	java/lang/String
    //   450: dup
    //   451: iconst_0
    //   452: ldc_w 312
    //   455: aastore
    //   456: invokestatic 314	com/google/android/apps/plus/iu/NewMediaTracker:getOptionalString	(Landroid/content/ContentResolver;Landroid/net/Uri;[Ljava/lang/String;)Ljava/lang/String;
    //   459: astore 22
    //   461: aload 22
    //   463: ifnonnull +10 -> 473
    //   466: aload 18
    //   468: invokevirtual 728	android/net/Uri:toString	()Ljava/lang/String;
    //   471: astore 22
    //   473: aload_1
    //   474: ldc_w 730
    //   477: aload 22
    //   479: invokevirtual 733	java/lang/String:hashCode	()I
    //   482: invokestatic 736	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   485: invokevirtual 739	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   488: aload_1
    //   489: ldc_w 741
    //   492: aload 4
    //   494: aload 18
    //   496: iconst_1
    //   497: anewarray 32	java/lang/String
    //   500: dup
    //   501: iconst_0
    //   502: ldc_w 743
    //   505: aastore
    //   506: lconst_0
    //   507: invokestatic 379	com/google/android/apps/plus/iu/NewMediaTracker:getOptionalLong	(Landroid/content/ContentResolver;Landroid/net/Uri;[Ljava/lang/String;J)J
    //   510: invokestatic 722	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   513: invokevirtual 725	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   516: iconst_1
    //   517: newarray long
    //   519: astore 38
    //   521: aload_1
    //   522: ldc_w 745
    //   525: aload 4
    //   527: aload 18
    //   529: invokevirtual 749	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   532: aload 38
    //   534: invokestatic 755	com/android/gallery3d/common/Fingerprint:fromInputStream	(Ljava/io/InputStream;[J)Lcom/android/gallery3d/common/Fingerprint;
    //   537: invokevirtual 759	com/android/gallery3d/common/Fingerprint:getBytes	()[B
    //   540: invokevirtual 762	android/content/ContentValues:put	(Ljava/lang/String;[B)V
    //   543: aload_1
    //   544: ldc_w 764
    //   547: aload 18
    //   549: invokevirtual 728	android/net/Uri:toString	()Ljava/lang/String;
    //   552: invokevirtual 767	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   555: aload_1
    //   556: ldc 90
    //   558: iconst_0
    //   559: invokestatic 736	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   562: invokevirtual 739	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   565: aload_1
    //   566: ldc_w 769
    //   569: sipush 200
    //   572: invokestatic 736	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   575: invokevirtual 739	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   578: getstatic 88	com/google/android/apps/plus/iu/MediaRecordEntry:SCHEMA	Lcom/android/gallery3d/common/EntrySchema;
    //   581: aload 5
    //   583: aload_1
    //   584: invokestatic 772	com/google/android/apps/plus/iu/MediaRecordEntry:createNew	(Landroid/content/ContentValues;)Lcom/google/android/apps/plus/iu/MediaRecordEntry;
    //   587: invokevirtual 547	com/android/gallery3d/common/EntrySchema:insertOrReplace	(Landroid/database/sqlite/SQLiteDatabase;Lcom/android/gallery3d/common/Entry;)J
    //   590: pop2
    //   591: iinc 6 1
    //   594: iload 10
    //   596: ifeq +165 -> 761
    //   599: aload_3
    //   600: invokevirtual 775	com/google/android/apps/plus/iu/UploadSettings:reloadSettings	()V
    //   603: aload_3
    //   604: invokevirtual 778	com/google/android/apps/plus/iu/UploadSettings:getSyncAccount	()Ljava/lang/String;
    //   607: astore 28
    //   609: aload_3
    //   610: invokevirtual 779	com/google/android/apps/plus/iu/UploadSettings:getEventId	()Ljava/lang/String;
    //   613: astore 29
    //   615: aload_3
    //   616: invokevirtual 782	com/google/android/apps/plus/iu/UploadSettings:getAutoUploadEnabled	()Z
    //   619: ifeq +438 -> 1057
    //   622: aload 28
    //   624: invokestatic 788	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   627: ifne +430 -> 1057
    //   630: iconst_1
    //   631: istore 30
    //   633: aload 29
    //   635: ifnull +428 -> 1063
    //   638: aload 28
    //   640: invokestatic 788	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   643: ifne +420 -> 1063
    //   646: aload_0
    //   647: aload 4
    //   649: aload_3
    //   650: aload 18
    //   652: iload 19
    //   654: invokespecial 790	com/google/android/apps/plus/iu/NewMediaTracker:performSanityChecks	(Landroid/content/ContentResolver;Lcom/google/android/apps/plus/iu/UploadSettings;Landroid/net/Uri;Z)Z
    //   657: ifeq +406 -> 1063
    //   660: iconst_1
    //   661: istore 31
    //   663: iload 31
    //   665: ifeq +198 -> 863
    //   668: ldc_w 338
    //   671: iconst_4
    //   672: invokestatic 344	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   675: ifeq +27 -> 702
    //   678: aload 9
    //   680: new 110	java/lang/StringBuilder
    //   683: dup
    //   684: ldc_w 792
    //   687: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   690: aload 28
    //   692: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   695: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   698: invokevirtual 668	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   701: pop
    //   702: aload_1
    //   703: ldc 80
    //   705: invokevirtual 794	android/content/ContentValues:remove	(Ljava/lang/String;)V
    //   708: aload_1
    //   709: ldc_w 704
    //   712: aload 29
    //   714: invokevirtual 767	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   717: aload_1
    //   718: ldc 100
    //   720: aload 28
    //   722: invokevirtual 767	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   725: aload_1
    //   726: ldc 90
    //   728: bipush 20
    //   730: invokestatic 736	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   733: invokevirtual 739	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   736: aload_1
    //   737: ldc_w 769
    //   740: bipush 100
    //   742: invokestatic 736	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   745: invokevirtual 739	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   748: getstatic 88	com/google/android/apps/plus/iu/MediaRecordEntry:SCHEMA	Lcom/android/gallery3d/common/EntrySchema;
    //   751: aload 5
    //   753: aload_1
    //   754: invokestatic 772	com/google/android/apps/plus/iu/MediaRecordEntry:createNew	(Landroid/content/ContentValues;)Lcom/google/android/apps/plus/iu/MediaRecordEntry;
    //   757: invokevirtual 547	com/android/gallery3d/common/EntrySchema:insertOrReplace	(Landroid/database/sqlite/SQLiteDatabase;Lcom/android/gallery3d/common/Entry;)J
    //   760: pop2
    //   761: ldc_w 338
    //   764: iconst_4
    //   765: invokestatic 344	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   768: ifeq -428 -> 340
    //   771: ldc_w 338
    //   774: aload 9
    //   776: invokevirtual 795	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   779: invokestatic 409	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   782: pop
    //   783: goto -443 -> 340
    //   786: astore_2
    //   787: aload_0
    //   788: monitorexit
    //   789: aload_2
    //   790: athrow
    //   791: aload 4
    //   793: aload 18
    //   795: iconst_1
    //   796: anewarray 32	java/lang/String
    //   799: dup
    //   800: iconst_0
    //   801: ldc_w 797
    //   804: aastore
    //   805: lconst_0
    //   806: invokestatic 379	com/google/android/apps/plus/iu/NewMediaTracker:getOptionalLong	(Landroid/content/ContentResolver;Landroid/net/Uri;[Ljava/lang/String;J)J
    //   809: lstore 20
    //   811: lload 20
    //   813: lconst_0
    //   814: lcmp
    //   815: ifgt -385 -> 430
    //   818: invokestatic 637	java/lang/System:currentTimeMillis	()J
    //   821: lstore 20
    //   823: goto -393 -> 430
    //   826: astore 23
    //   828: ldc_w 338
    //   831: new 110	java/lang/StringBuilder
    //   834: dup
    //   835: ldc_w 799
    //   838: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   841: lload 16
    //   843: invokevirtual 665	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   846: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   849: invokestatic 355	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   852: pop
    //   853: aload_1
    //   854: ldc_w 745
    //   857: invokevirtual 702	android/content/ContentValues:putNull	(Ljava/lang/String;)V
    //   860: goto -317 -> 543
    //   863: iload 30
    //   865: ifeq -104 -> 761
    //   868: aload 4
    //   870: aload 18
    //   872: invokestatic 572	com/google/android/apps/plus/iu/NewMediaTracker:hasGoogleUploadExif	(Landroid/content/ContentResolver;Landroid/net/Uri;)Z
    //   875: ifne -114 -> 761
    //   878: ldc_w 338
    //   881: iconst_4
    //   882: invokestatic 344	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   885: ifeq +27 -> 912
    //   888: aload 9
    //   890: new 110	java/lang/StringBuilder
    //   893: dup
    //   894: ldc_w 801
    //   897: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   900: aload 28
    //   902: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   905: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   908: invokevirtual 668	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   911: pop
    //   912: aload_1
    //   913: ldc 80
    //   915: invokevirtual 794	android/content/ContentValues:remove	(Ljava/lang/String;)V
    //   918: aload_1
    //   919: ldc 100
    //   921: aload 28
    //   923: invokevirtual 767	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   926: aload_1
    //   927: ldc 90
    //   929: bipush 30
    //   931: invokestatic 736	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   934: invokevirtual 739	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   937: aload_1
    //   938: ldc_w 769
    //   941: bipush 100
    //   943: invokestatic 736	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   946: invokevirtual 739	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   949: getstatic 88	com/google/android/apps/plus/iu/MediaRecordEntry:SCHEMA	Lcom/android/gallery3d/common/EntrySchema;
    //   952: aload 5
    //   954: aload_1
    //   955: invokestatic 772	com/google/android/apps/plus/iu/MediaRecordEntry:createNew	(Landroid/content/ContentValues;)Lcom/google/android/apps/plus/iu/MediaRecordEntry;
    //   958: invokevirtual 547	com/android/gallery3d/common/EntrySchema:insertOrReplace	(Landroid/database/sqlite/SQLiteDatabase;Lcom/android/gallery3d/common/Entry;)J
    //   961: pop2
    //   962: goto -201 -> 761
    //   965: ldc_w 338
    //   968: iconst_4
    //   969: invokestatic 344	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   972: ifeq +38 -> 1010
    //   975: ldc_w 338
    //   978: new 110	java/lang/StringBuilder
    //   981: dup
    //   982: ldc_w 803
    //   985: invokespecial 116	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   988: invokestatic 637	java/lang/System:currentTimeMillis	()J
    //   991: lload 7
    //   993: lsub
    //   994: invokevirtual 665	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   997: ldc_w 805
    //   1000: invokevirtual 120	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1003: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1006: invokestatic 409	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   1009: pop
    //   1010: aload_0
    //   1011: getfield 171	com/google/android/apps/plus/iu/NewMediaTracker:mPreferences	Landroid/content/SharedPreferences;
    //   1014: invokeinterface 204 1 0
    //   1019: ldc_w 642
    //   1022: iconst_1
    //   1023: invokeinterface 809 3 0
    //   1028: invokeinterface 245 1 0
    //   1033: pop
    //   1034: aload_0
    //   1035: monitorexit
    //   1036: iload 6
    //   1038: ireturn
    //   1039: iinc 11 255
    //   1042: goto -945 -> 97
    //   1045: iconst_1
    //   1046: istore 19
    //   1048: goto -747 -> 301
    //   1051: iconst_0
    //   1052: istore 19
    //   1054: goto -753 -> 301
    //   1057: iconst_0
    //   1058: istore 30
    //   1060: goto -427 -> 633
    //   1063: iconst_0
    //   1064: istore 31
    //   1066: goto -403 -> 663
    //
    // Exception table:
    //   from	to	target	type
    //   2	516	786	finally
    //   516	543	786	finally
    //   543	783	786	finally
    //   791	1034	786	finally
    //   516	543	826	java/io/IOException
  }

  final void startUpload(String paramString, int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.mUploadsDbHelper.getWritableDatabase();
    Cursor localCursor1 = this.mUploadsDbHelper.getReadableDatabase().query(true, MEDIA_RECORD_TABLE, MediaRecordEntry.SCHEMA.getProjection(), SELECT_MEDIA_NOT_UPLOADED_BY_ACCOUNT, new String[] { paramString }, null, null, null, null);
    try
    {
      if (localCursor1.moveToNext())
      {
        MediaRecordEntry localMediaRecordEntry2 = MediaRecordEntry.fromCursor(localCursor1);
        localMediaRecordEntry2.id = 0L;
        localMediaRecordEntry2.setUploadAccount(paramString);
        localMediaRecordEntry2.setUploadReason(40);
        localMediaRecordEntry2.setState(100);
        MediaRecordEntry.SCHEMA.insertOrReplace(localSQLiteDatabase, localMediaRecordEntry2);
      }
    }
    finally
    {
      localCursor1.close();
    }
    Cursor localCursor2 = this.mUploadsDbHelper.getReadableDatabase().query(true, MEDIA_RECORD_TABLE, MediaRecordEntry.SCHEMA.getProjection(), "upload_account = ? AND upload_state >= 400", new String[] { paramString }, null, null, null, null);
    try
    {
      if (localCursor2.moveToNext())
      {
        MediaRecordEntry localMediaRecordEntry1 = MediaRecordEntry.fromCursor(localCursor2);
        localMediaRecordEntry1.setUploadReason(40);
        localMediaRecordEntry1.setState(100);
        MediaRecordEntry.SCHEMA.insertOrReplace(localSQLiteDatabase, localMediaRecordEntry1);
      }
    }
    finally
    {
      localCursor2.close();
    }
  }

  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("NewMediaTracker:");
    for (TrackRecord localTrackRecord : this.mTrackRecords)
      localStringBuilder.append(";").append(localTrackRecord.toString());
    return localStringBuilder.toString();
  }

  private static final class Config
  {
    public final String mKeyLastMediaId;
    public final Uri mMediaStoreUri;
    public final String mMediaType;
    public final String mStorage;

    public Config(String paramString1, Uri paramUri, String paramString2)
    {
      this.mMediaType = paramString1;
      this.mStorage = paramString2;
      this.mMediaStoreUri = paramUri;
      this.mKeyLastMediaId = ("media_scanner." + paramString2 + "." + paramString1 + ".last_media_id");
    }

    public final String toString()
    {
      return this.mMediaType + "-" + this.mStorage;
    }
  }

  private static final class TrackRecord
  {
    final NewMediaTracker.Config mConfig;
    long mLastMediaId;

    TrackRecord(NewMediaTracker.Config paramConfig)
    {
      this.mConfig = paramConfig;
    }

    public final String toString()
    {
      return this.mConfig + "," + this.mLastMediaId;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.NewMediaTracker
 * JD-Core Version:    0.6.2
 */