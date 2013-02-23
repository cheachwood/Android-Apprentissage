package com.google.android.apps.plus.iu;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.util.Log;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Fingerprint;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.service.PicasaQuotaChangedReceiver;
import com.google.android.apps.plus.util.EsLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class InstantUploadProvider extends ContentProvider
{
  private static final Map<String, String> MEDIA_RECORD_MAP;
  private static final String MEDIA_RECORD_TABLE;
  private static final String PHOTO_TABLE_NAME;
  private static final String[] PROJECTION_ID;
  private static final Object SETTINGS_LOCK;
  private static final HashMap<String, String> SETTING_DEFAULTS;
  private static final HashMap<String, String> SETTING_DEPRECATED;
  private static final Map<String, String> UPLOAD_TASK_MAP;
  private static final String UPLOAD_TASK_TABLE = UploadTaskEntry.SCHEMA.getTableName();
  private static QuotaTask sQuotaTask;
  private String mAuthority;
  private final UriMatcher mUriMatcher = new UriMatcher(-1);

  static
  {
    MEDIA_RECORD_TABLE = MediaRecordEntry.SCHEMA.getTableName();
    PHOTO_TABLE_NAME = PhotoEntry.SCHEMA.getTableName();
    PROJECTION_ID = new String[] { "_id" };
    SETTINGS_LOCK = new Object();
    UPLOAD_TASK_MAP = new HashMap();
    MEDIA_RECORD_MAP = new HashMap();
    UPLOAD_TASK_MAP.put("_id", "_id");
    UPLOAD_TASK_MAP.put("upload_account", "account");
    UPLOAD_TASK_MAP.put("album_id", "album_id");
    UPLOAD_TASK_MAP.put("bytes_total", "bytes_total");
    UPLOAD_TASK_MAP.put("bytes_uploaded", "bytes_uploaded");
    UPLOAD_TASK_MAP.put("media_url", "content_uri");
    UPLOAD_TASK_MAP.put("event_id", "event_id");
    UPLOAD_TASK_MAP.put("fingerprint", "fingerprint");
    UPLOAD_TASK_MAP.put("upload_state", "state");
    UPLOAD_TASK_MAP.put("media_record_id", "media_record_id");
    MEDIA_RECORD_MAP.put("_id", "_id");
    MEDIA_RECORD_MAP.put("upload_account", "upload_account");
    MEDIA_RECORD_MAP.put("album_id", "album_id");
    MEDIA_RECORD_MAP.put("bytes_total", "bytes_total");
    MEDIA_RECORD_MAP.put("bytes_uploaded", "bytes_uploaded");
    MEDIA_RECORD_MAP.put("media_url", "media_url");
    MEDIA_RECORD_MAP.put("event_id", "event_id");
    MEDIA_RECORD_MAP.put("fingerprint", "fingerprint");
    MEDIA_RECORD_MAP.put("upload_state", "upload_state");
    MEDIA_RECORD_MAP.put("media_id", "media_id");
    SETTING_DEFAULTS = new HashMap();
    SETTING_DEPRECATED = new HashMap();
    SETTING_DEFAULTS.put("auto_upload_account_name", null);
    SETTING_DEFAULTS.put("auto_upload_account_type", null);
    SETTING_DEFAULTS.put("auto_upload_enabled", "0");
    SETTING_DEFAULTS.put("sync_on_wifi_only", "1");
    SETTING_DEFAULTS.put("video_upload_wifi_only", "1");
    SETTING_DEFAULTS.put("sync_on_roaming", "0");
    SETTING_DEFAULTS.put("sync_on_battery", "1");
    SETTING_DEFAULTS.put("instant_share_eventid", null);
    SETTING_DEFAULTS.put("instant_share_starttime", "0");
    SETTING_DEFAULTS.put("instant_share_endtime", "0");
    SETTING_DEFAULTS.put("upload_full_resolution", "1");
    SETTING_DEFAULTS.put("instant_upload_state", Integer.toString(0));
    SETTING_DEFAULTS.put("instant_share_state", Integer.toString(0));
    SETTING_DEFAULTS.put("upload_all_state", Integer.toString(0));
    SETTING_DEFAULTS.put("manual_upload_state", Integer.toString(0));
    SETTING_DEFAULTS.put("quota_limit", Long.toString(-1L));
    SETTING_DEFAULTS.put("quota_used", Long.toString(-1L));
    SETTING_DEFAULTS.put("full_size_disabled", "1");
    SETTING_DEPRECATED.put("sync_photo_on_mobile", "0");
  }

  private int cancelUploads(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    UploadsManager localUploadsManager = UploadsManager.getInstance(getContext());
    Cursor localCursor = queryUploads(UPLOAD_TASK_TABLE, UPLOAD_TASK_MAP, PROJECTION_ID, paramString, paramArrayOfString, null, paramUri.getQueryParameter("limit"));
    int i = 0;
    try
    {
      while (localCursor.moveToNext())
      {
        localUploadsManager.cancelTask(localCursor.getLong(0));
        i++;
      }
      return i;
    }
    finally
    {
      localCursor.close();
    }
  }

  static void disableInstantShare(Context paramContext)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    synchronized (SETTINGS_LOCK)
    {
      Settings.System.putString(localContentResolver, "com.google.android.picasasync.instant_share_eventid", null);
      return;
    }
  }

  private Cursor queryPhotos(Uri paramUri, String[] paramArrayOfString)
  {
    PicasaSyncHelper localPicasaSyncHelper = PicasaSyncHelper.getInstance(getContext());
    String str1 = paramUri.getQueryParameter("account");
    UserEntry localUserEntry = localPicasaSyncHelper.findUser(str1);
    PicasaSyncHelper.SyncContext localSyncContext = localPicasaSyncHelper.createSyncContext(new SyncResult(), Thread.currentThread());
    localSyncContext.setAccount(str1);
    localPicasaSyncHelper.syncUploadedPhotos(localSyncContext, localUserEntry);
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(new String[] { "uri", "photoid" });
    long[] arrayOfLong = new long[1];
    Context localContext = getContext();
    ContentResolver localContentResolver = localContext.getContentResolver();
    SQLiteDatabase localSQLiteDatabase = PicasaDatabaseHelper.get(localContext).getReadableDatabase();
    int i = paramArrayOfString.length;
    int j = 0;
    while (j < i)
    {
      String str2 = paramArrayOfString[j];
      Uri localUri = Uri.parse(str2);
      Cursor localCursor = null;
      try
      {
        int k = Fingerprint.fromInputStream(localContentResolver.openInputStream(localUri), arrayOfLong).hashCode();
        String str3 = PHOTO_TABLE_NAME;
        String[] arrayOfString1 = PROJECTION_ID;
        String[] arrayOfString2 = new String[2];
        arrayOfString2[0] = String.valueOf(k);
        arrayOfString2[1] = String.valueOf(localUserEntry.id);
        localCursor = localSQLiteDatabase.query(str3, arrayOfString1, "fingerprint_hash=? AND user_id=?", arrayOfString2, null, null, null);
        if (localCursor.moveToFirst())
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = str2;
          arrayOfObject[1] = Long.valueOf(localCursor.getLong(0));
          localEsMatrixCursor.addRow(arrayOfObject);
        }
        if (localCursor != null)
          localCursor.close();
        j++;
      }
      catch (Exception localException)
      {
        while (true)
        {
          if (EsLog.isLoggable("iu.IUProvider", 4))
            Log.i("iu.IUProvider", "problem retrieving photo id for uri:" + localUri);
          if (localCursor != null)
            localCursor.close();
        }
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
    return localEsMatrixCursor;
  }

  private Cursor querySettings(Uri paramUri, String[] paramArrayOfString)
  {
    String str1 = paramUri.getQueryParameter("account");
    int i = 0;
    EsMatrixCursor localEsMatrixCursor;
    Object[] arrayOfObject;
    ContentResolver localContentResolver;
    int j;
    String str2;
    synchronized (SETTINGS_LOCK)
    {
      localEsMatrixCursor = new EsMatrixCursor(paramArrayOfString);
      arrayOfObject = new Object[paramArrayOfString.length];
      localContentResolver = getContext().getContentResolver();
      j = 0;
      int k = paramArrayOfString.length;
      if (j >= k)
        break label277;
      str2 = paramArrayOfString[j];
      if (!SETTING_DEFAULTS.containsKey(str2))
      {
        if (SETTING_DEPRECATED.containsKey(str2))
        {
          arrayOfObject[j] = SETTING_DEPRECATED.get(str2);
          break label396;
        }
        throw new IllegalArgumentException("unknown column: " + str2);
      }
    }
    label169: int m;
    label189: StringBuilder localStringBuilder;
    if ((!"quota_limit".equals(str2)) && (!"quota_used".equals(str2)))
    {
      if (!"full_size_disabled".equals(str2))
        break label408;
      break label402;
      if ((m == 0) || (str1 != null))
        break label414;
      throw new IllegalArgumentException("Must specify account for quota details");
      localStringBuilder = new StringBuilder("com.google.android.picasasync.");
      if (m == 0)
        break label436;
    }
    label277: label408: label414: label436: for (String str3 = str1 + "."; ; str3 = "")
    {
      arrayOfObject[j] = Settings.System.getString(localContentResolver, str3 + str2);
      if (arrayOfObject[j] == null)
      {
        arrayOfObject[j] = SETTING_DEFAULTS.get(str2);
        break label396;
        localEsMatrixCursor.addRow(arrayOfObject);
        Context localContext;
        if (i != 0)
          localContext = getContext();
        synchronized (SETTINGS_LOCK)
        {
          if ((sQuotaTask == null) || (!TextUtils.equals(str1, sQuotaTask.getAccount())))
          {
            sQuotaTask = new QuotaTask(localContext, str1);
            if (Build.VERSION.SDK_INT < 11)
              sQuotaTask.execute(new Void[] { null });
          }
          else
          {
            return localEsMatrixCursor;
          }
          sQuotaTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[] { null });
        }
      }
      j++;
      break;
      m = 1;
      break label169;
      m = 0;
      break label169;
      if ((i != 0) || (m != 0))
      {
        i = 1;
        break label189;
      }
      i = 0;
      break label189;
    }
  }

  private Cursor queryUploads(String paramString1, Map<String, String> paramMap, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3, String paramString4)
  {
    SQLiteDatabase localSQLiteDatabase = UploadsDatabaseHelper.getInstance(getContext()).getReadableDatabase();
    SQLiteQueryBuilder localSQLiteQueryBuilder = new SQLiteQueryBuilder();
    localSQLiteQueryBuilder.setTables(paramString1);
    localSQLiteQueryBuilder.setProjectionMap(paramMap);
    return localSQLiteQueryBuilder.query(localSQLiteDatabase, paramArrayOfString1, paramString2, paramArrayOfString2, null, null, paramString3, paramString4);
  }

  static void updateQuotaSettings(Context paramContext, String paramString, GDataUploader.GDataQuota paramGDataQuota)
  {
    ContentValues localContentValues = new ContentValues(3);
    if (paramGDataQuota.quotaLimit != -1L)
      localContentValues.put("quota_limit", Long.toString(paramGDataQuota.quotaLimit));
    if (paramGDataQuota.quotaUsed != -1L)
      localContentValues.put("quota_used", Long.toString(paramGDataQuota.quotaUsed));
    if (paramGDataQuota.disableFullRes);
    for (String str = "1"; ; str = "0")
    {
      localContentValues.put("full_size_disabled", str);
      if (EsLog.isLoggable("iu.IUProvider", 4))
        Log.i("iu.IUProvider", "Update quota settings; " + paramGDataQuota.toString());
      if (updateSettings(paramContext, paramString, localContentValues))
      {
        Intent localIntent = new Intent(paramContext, PicasaQuotaChangedReceiver.class);
        localIntent.setAction("com.google.android.apps.plus.iu.QUOTA_CHANGED");
        localIntent.putExtra("quota_limit", (int)paramGDataQuota.quotaLimit);
        localIntent.putExtra("quota_used", (int)paramGDataQuota.quotaUsed);
        localIntent.putExtra("full_size_disabled", paramGDataQuota.disableFullRes);
        paramContext.sendBroadcast(localIntent);
      }
      return;
    }
  }

  private static boolean updateSettings(Context paramContext, String paramString, ContentValues paramContentValues)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    boolean bool = false;
    UploadsManager localUploadsManager = UploadsManager.getInstance(paramContext);
    Map.Entry localEntry;
    synchronized (SETTINGS_LOCK)
    {
      Iterator localIterator = paramContentValues.valueSet().iterator();
      do
      {
        if (!localIterator.hasNext())
          break label287;
        localEntry = (Map.Entry)localIterator.next();
        if (SETTING_DEFAULTS.containsKey(localEntry.getKey()))
          break;
      }
      while (SETTING_DEPRECATED.containsKey(localEntry.getKey()));
      throw new IllegalArgumentException("unknown setting: " + (String)localEntry.getKey());
    }
    String str1 = (String)localEntry.getKey();
    label174: StringBuilder localStringBuilder;
    int i;
    if ((!"quota_limit".equals(str1)) && (!"quota_used".equals(str1)))
    {
      if (!"full_size_disabled".equals(str1))
        break label339;
      break label324;
      localStringBuilder = new StringBuilder("com.google.android.picasasync.");
      if (i == 0)
        break label345;
    }
    label285: label287: label324: label339: label343: label345: for (String str2 = paramString + "."; ; str2 = "")
    {
      String str3 = str2 + str1;
      if (localEntry.getValue() == null);
      for (Object localObject3 = null; ; localObject3 = localEntry.getValue().toString())
      {
        if (Utils.equals(Settings.System.getString(localContentResolver, str3), localObject3))
          break label285;
        Settings.System.putString(localContentResolver, str3, (String)localObject3);
        bool = true;
        break;
      }
      break;
      if (bool)
        localUploadsManager.reloadSystemSettings();
      if (bool)
      {
        InstantUploadSyncManager.getInstance(paramContext).updateTasks(0L);
        localContentResolver.notifyChange(InstantUploadFacade.SETTINGS_URI, null);
      }
      return bool;
      for (i = 1; ; i = 0)
      {
        if (i == 0)
          break label343;
        if (paramString == null)
          break;
        break label174;
      }
      break label174;
    }
  }

  public void attachInfo(Context paramContext, ProviderInfo paramProviderInfo)
  {
    super.attachInfo(paramContext, paramProviderInfo);
    this.mAuthority = paramProviderInfo.authority;
    this.mUriMatcher.addURI(this.mAuthority, "uploads", 5);
    this.mUriMatcher.addURI(this.mAuthority, "upload_all", 9);
    this.mUriMatcher.addURI(this.mAuthority, "iu", 17);
    this.mUriMatcher.addURI(this.mAuthority, "settings", 11);
    this.mUriMatcher.addURI(this.mAuthority, "photos", 18);
  }

  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    int i;
    switch (this.mUriMatcher.match(paramUri))
    {
    default:
      throw new IllegalArgumentException("unsupported uri:" + paramUri);
    case 5:
      i = cancelUploads(paramUri, paramString, paramArrayOfString);
    case 9:
    case 11:
    }
    while (true)
    {
      return i;
      String str = paramUri.getQueryParameter("account");
      if (str != null)
        UploadsManager.getInstance(getContext()).cancelUploadExistingPhotos(str);
      i = 0;
      continue;
      ContentValues localContentValues = new ContentValues();
      Iterator localIterator = SETTING_DEFAULTS.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localContentValues.put((String)localEntry.getKey(), (String)localEntry.getValue());
      }
      if (updateSettings(getContext(), null, localContentValues))
        i = 1;
      else
        i = 0;
    }
  }

  public String getType(Uri paramUri)
  {
    String str;
    switch (this.mUriMatcher.match(paramUri))
    {
    default:
      throw new IllegalArgumentException("Invalid URI: " + paramUri);
    case 5:
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.iu.upload";
    case 9:
    case 17:
    case 18:
    }
    while (true)
    {
      return str;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.iu.upload_all";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.iu.iu";
      continue;
      str = "vnd.android.cursor.item/vnd.google.android.apps.plus.iu.photos_content_uri";
    }
  }

  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    int i = MetricsUtils.begin("INSERT " + paramUri);
    try
    {
      switch (this.mUriMatcher.match(paramUri))
      {
      default:
        throw new IllegalArgumentException("unsupported uri:" + paramUri);
      case 5:
      case 9:
      }
    }
    finally
    {
      MetricsUtils.end(i);
    }
    Uri localUri2 = InstantUploadFacade.getUploadUri(UploadsManager.getInstance(getContext()).addManualUpload(paramContentValues));
    Uri localUri1 = localUri2;
    MetricsUtils.end(i);
    while (true)
    {
      return localUri1;
      String str = paramContentValues.getAsString("account");
      UploadsManager.getInstance(getContext()).uploadExistingPhotos(str);
      localUri1 = InstantUploadFacade.UPLOAD_ALL_URI;
      MetricsUtils.end(i);
    }
  }

  public boolean onCreate()
  {
    return true;
  }

  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    int i = MetricsUtils.begin("QUERY " + paramUri.toString());
    try
    {
      switch (this.mUriMatcher.match(paramUri))
      {
      default:
        throw new IllegalArgumentException("Invalid URI: " + paramUri);
      case 5:
      case 11:
      case 9:
      case 17:
      case 18:
      }
    }
    finally
    {
      MetricsUtils.end(i);
    }
    Cursor localCursor = queryUploads(MEDIA_RECORD_TABLE, MEDIA_RECORD_MAP, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, paramUri.getQueryParameter("limit"));
    while (true)
    {
      MetricsUtils.incrementQueryResultCount(localCursor.getCount());
      MetricsUtils.end(i);
      return localCursor;
      localCursor = querySettings(paramUri, paramArrayOfString1);
      continue;
      String str = paramUri.getQueryParameter("account");
      localCursor = UploadsManager.getInstance(getContext()).getUploadAllStatus(str);
      continue;
      localCursor = UploadsManager.getInstance(getContext()).getInstantUploadStatus();
      continue;
      if (paramArrayOfString1 == null)
        break;
      localCursor = queryPhotos(paramUri, paramArrayOfString1);
    }
    throw new IllegalArgumentException("projection must include list of local uris");
  }

  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    switch (this.mUriMatcher.match(paramUri))
    {
    default:
      throw new IllegalArgumentException("unsupported uri:" + paramUri);
    case 11:
    }
    String str = paramUri.getQueryParameter("account");
    if (updateSettings(getContext(), str, paramContentValues));
    for (int i = 1; ; i = 0)
      return i;
  }

  static final class QuotaTask extends AsyncTask<Void, Void, Void>
  {
    private final String mAccount;
    private final Context mContext;

    QuotaTask(Context paramContext, String paramString)
    {
      this.mContext = paramContext;
      this.mAccount = paramString;
    }

    // ERROR //
    private Void doInBackground$10299ca()
    {
      // Byte code:
      //   0: new 22	com/google/android/apps/plus/iu/GDataUploader
      //   3: dup
      //   4: aload_0
      //   5: getfield 16	com/google/android/apps/plus/iu/InstantUploadProvider$QuotaTask:mContext	Landroid/content/Context;
      //   8: invokespecial 25	com/google/android/apps/plus/iu/GDataUploader:<init>	(Landroid/content/Context;)V
      //   11: astore_1
      //   12: aload_1
      //   13: aload_0
      //   14: getfield 18	com/google/android/apps/plus/iu/InstantUploadProvider$QuotaTask:mAccount	Ljava/lang/String;
      //   17: invokevirtual 29	com/google/android/apps/plus/iu/GDataUploader:getQuota	(Ljava/lang/String;)Lcom/google/android/apps/plus/iu/GDataUploader$GDataQuota;
      //   20: astore_3
      //   21: aload_3
      //   22: ifnull +15 -> 37
      //   25: aload_0
      //   26: getfield 16	com/google/android/apps/plus/iu/InstantUploadProvider$QuotaTask:mContext	Landroid/content/Context;
      //   29: aload_0
      //   30: getfield 18	com/google/android/apps/plus/iu/InstantUploadProvider$QuotaTask:mAccount	Ljava/lang/String;
      //   33: aload_3
      //   34: invokestatic 35	com/google/android/apps/plus/iu/InstantUploadProvider:updateQuotaSettings	(Landroid/content/Context;Ljava/lang/String;Lcom/google/android/apps/plus/iu/GDataUploader$GDataQuota;)V
      //   37: aload_1
      //   38: invokevirtual 38	com/google/android/apps/plus/iu/GDataUploader:close	()V
      //   41: invokestatic 42	com/google/android/apps/plus/iu/InstantUploadProvider:access$000	()Ljava/lang/Object;
      //   44: astore 4
      //   46: aload 4
      //   48: monitorenter
      //   49: invokestatic 46	com/google/android/apps/plus/iu/InstantUploadProvider:access$100	()Lcom/google/android/apps/plus/iu/InstantUploadProvider$QuotaTask;
      //   52: aload_0
      //   53: if_acmpne +8 -> 61
      //   56: aconst_null
      //   57: invokestatic 50	com/google/android/apps/plus/iu/InstantUploadProvider:access$102	(Lcom/google/android/apps/plus/iu/InstantUploadProvider$QuotaTask;)Lcom/google/android/apps/plus/iu/InstantUploadProvider$QuotaTask;
      //   60: pop
      //   61: aload 4
      //   63: monitorexit
      //   64: aconst_null
      //   65: areturn
      //   66: astore_2
      //   67: aload_1
      //   68: invokevirtual 38	com/google/android/apps/plus/iu/GDataUploader:close	()V
      //   71: aload_2
      //   72: athrow
      //   73: astore 5
      //   75: aload 4
      //   77: monitorexit
      //   78: aload 5
      //   80: athrow
      //
      // Exception table:
      //   from	to	target	type
      //   12	37	66	finally
      //   49	64	73	finally
    }

    final String getAccount()
    {
      return this.mAccount;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.InstantUploadProvider
 * JD-Core Version:    0.6.2
 */