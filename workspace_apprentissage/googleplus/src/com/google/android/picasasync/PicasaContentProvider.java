package com.google.android.picasasync;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.ParcelFileDescriptor;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.util.Log;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Utils;
import com.google.android.picasastore.MetricsUtils;
import com.google.android.picasastore.PicasaMatrixCursor;
import com.google.android.picasastore.PicasaMatrixCursor.RowBuilder;
import com.google.android.picasastore.PicasaStoreFacade;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class PicasaContentProvider extends ContentProvider
{
  private static final String ALBUM_TABLE_NAME;
  private static final String ALBUM_TYPE_WHERE;
  private static final String PHOTO_TABLE_NAME;
  private static String[] PROJECTION_CONTENT_URL = { "content_url" };
  private static String[] PROJECTION_SCREENNAIL_URL = { "screennail_url" };
  private static String[] PROJECTION_THUMBNAIL_URL;
  private static final HashMap<String, String> SETTING_DEFAULTS;
  private static final HashMap<String, String> SETTING_DEPRECATED;
  private static final String USER_TABLE_NAME = UserEntry.SCHEMA.getTableName();
  private String mAuthority;
  private PicasaDatabaseHelper mDbHelper;
  private PicasaStoreFacade mPicasaStoreFacade = null;
  private final UriMatcher mUriMatcher = new UriMatcher(-1);

  static
  {
    ALBUM_TABLE_NAME = AlbumEntry.SCHEMA.getTableName();
    PHOTO_TABLE_NAME = PhotoEntry.SCHEMA.getTableName();
    ALBUM_TYPE_WHERE = "_id in (SELECT album_id FROM " + PHOTO_TABLE_NAME + " WHERE content_type" + " LIKE ?)";
    SETTING_DEFAULTS = new HashMap();
    SETTING_DEPRECATED = new HashMap();
    SETTING_DEFAULTS.put("sync_picasa_on_wifi_only", "1");
    SETTING_DEFAULTS.put("sync_on_roaming", "0");
    SETTING_DEFAULTS.put("sync_on_battery", "1");
    SETTING_DEPRECATED.put("sync_photo_on_mobile", "0");
    SETTING_DEPRECATED.put("auto_upload_enabled", "0");
    SETTING_DEPRECATED.put("auto_upload_account_name", null);
    SETTING_DEPRECATED.put("auto_upload_account_type", null);
    SETTING_DEPRECATED.put("sync_on_wifi_only", "1");
    SETTING_DEPRECATED.put("video_upload_wifi_only", "1");
    PROJECTION_THUMBNAIL_URL = new String[] { "thumbnail_url" };
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
        Log.w("gp.PicasaContentProvider", "cannot get id from: " + paramUri);
        long l1 = -1L;
      }
    }
  }

  private static long getLastSegmentAsLong(Uri paramUri, long paramLong)
  {
    long l1 = -1L;
    List localList = paramUri.getPathSegments();
    if (localList.size() == 0)
      Log.w("gp.PicasaContentProvider", "parse fail: " + paramUri);
    while (true)
    {
      return l1;
      String str = (String)localList.get(-1 + localList.size());
      try
      {
        long l2 = Long.parseLong(str);
        l1 = l2;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Log.w("gp.PicasaContentProvider", "pasre fail:" + paramUri, localNumberFormatException);
      }
    }
  }

  private String lookupAlbumCoverUrl(long paramLong)
  {
    Object localObject1 = null;
    SQLiteDatabase localSQLiteDatabase = PicasaDatabaseHelper.get(getContext()).getReadableDatabase();
    String str1 = ALBUM_TABLE_NAME;
    String[] arrayOfString1 = PROJECTION_THUMBNAIL_URL;
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramLong);
    Cursor localCursor = localSQLiteDatabase.query(str1, arrayOfString1, "_id=?", arrayOfString2, null, null, null);
    if (localCursor != null);
    try
    {
      if (localCursor.moveToNext())
      {
        boolean bool = localCursor.isNull(0);
        if (!bool)
          break label93;
      }
      while (true)
      {
        return localObject1;
        label93: String str2 = localCursor.getString(0);
        localObject1 = str2;
        Utils.closeSilently(localCursor);
      }
    }
    finally
    {
      Utils.closeSilently(localCursor);
    }
  }

  private String lookupContentUrl(long paramLong, String paramString)
  {
    Object localObject1 = null;
    if (paramString == null)
      paramString = "full";
    Context localContext = getContext();
    String[] arrayOfString1;
    if ("full".equals(paramString))
      arrayOfString1 = PROJECTION_CONTENT_URL;
    while (true)
    {
      SQLiteDatabase localSQLiteDatabase = PicasaDatabaseHelper.get(localContext).getReadableDatabase();
      String str1 = PHOTO_TABLE_NAME;
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = String.valueOf(paramLong);
      Cursor localCursor = localSQLiteDatabase.query(str1, arrayOfString1, "_id=?", arrayOfString2, null, null, null);
      if (localCursor != null);
      try
      {
        if (localCursor.moveToNext())
        {
          boolean bool = localCursor.isNull(0);
          if (!bool)
            break label123;
        }
        while (true)
        {
          return localObject1;
          arrayOfString1 = PROJECTION_SCREENNAIL_URL;
          break;
          label123: String str2 = localCursor.getString(0);
          localObject1 = str2;
          Utils.closeSilently(localCursor);
        }
      }
      finally
      {
        Utils.closeSilently(localCursor);
      }
    }
  }

  private Cursor querySettings$7be7850c(String[] paramArrayOfString)
  {
    while (true)
    {
      PicasaMatrixCursor localPicasaMatrixCursor;
      Object[] arrayOfObject;
      ContentResolver localContentResolver;
      int i;
      String str;
      try
      {
        localPicasaMatrixCursor = new PicasaMatrixCursor(paramArrayOfString);
        arrayOfObject = new Object[paramArrayOfString.length];
        localContentResolver = getContext().getContentResolver();
        i = 0;
        int j = paramArrayOfString.length;
        if (i >= j)
          break label168;
        str = paramArrayOfString[i];
        if (!SETTING_DEFAULTS.containsKey(str))
        {
          if (SETTING_DEPRECATED.containsKey(str))
          {
            arrayOfObject[i] = SETTING_DEPRECATED.get(str);
            break label178;
          }
          throw new IllegalArgumentException("unknown column: " + str);
        }
      }
      finally
      {
      }
      arrayOfObject[i] = Settings.System.getString(localContentResolver, "com.google.android.picasasync." + str);
      if (arrayOfObject[i] == null)
      {
        arrayOfObject[i] = SETTING_DEFAULTS.get(str);
        break label178;
        label168: localPicasaMatrixCursor.addRow(arrayOfObject);
        return localPicasaMatrixCursor;
      }
      label178: i++;
    }
  }

  private boolean resetSettings()
  {
    ContentValues localContentValues = new ContentValues();
    Iterator localIterator = SETTING_DEFAULTS.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localContentValues.put((String)localEntry.getKey(), (String)localEntry.getValue());
    }
    return updateSettings(localContentValues);
  }

  private boolean updateSettings(ContentValues paramContentValues)
  {
    ContentResolver localContentResolver = getContext().getContentResolver();
    boolean bool = false;
    label200: 
    while (true)
    {
      Map.Entry localEntry;
      try
      {
        Iterator localIterator = paramContentValues.valueSet().iterator();
        if (!localIterator.hasNext())
          break;
        localEntry = (Map.Entry)localIterator.next();
        if (!SETTING_DEFAULTS.containsKey(localEntry.getKey()))
        {
          if (SETTING_DEPRECATED.containsKey(localEntry.getKey()))
            continue;
          throw new IllegalArgumentException("unknown setting: " + (String)localEntry.getKey());
        }
      }
      finally
      {
      }
      String str = "com.google.android.picasasync." + (String)localEntry.getKey();
      if (localEntry.getValue() == null);
      for (Object localObject2 = null; ; localObject2 = localEntry.getValue().toString())
      {
        if (Utils.equals(Settings.System.getString(localContentResolver, str), localObject2))
          break label200;
        Settings.System.putString(localContentResolver, str, (String)localObject2);
        bool = true;
        break;
      }
    }
    if (bool)
    {
      Context localContext = getContext();
      PicasaSyncManager.get(localContext).updateTasks(0L);
      localContentResolver.notifyChange(PicasaFacade.get(localContext).getSettingsUri(), null);
    }
    return bool;
  }

  public void attachInfo(Context paramContext, ProviderInfo paramProviderInfo)
  {
    super.attachInfo(paramContext, paramProviderInfo);
    this.mAuthority = paramProviderInfo.authority;
    this.mUriMatcher.addURI(this.mAuthority, "photos", 1);
    this.mUriMatcher.addURI(this.mAuthority, "albums", 3);
    this.mUriMatcher.addURI(this.mAuthority, "posts", 15);
    this.mUriMatcher.addURI(this.mAuthority, "posts_album", 16);
    this.mUriMatcher.addURI(this.mAuthority, "users", 12);
    this.mUriMatcher.addURI(this.mAuthority, "photos/#", 2);
    this.mUriMatcher.addURI(this.mAuthority, "albums/#", 4);
    this.mUriMatcher.addURI(this.mAuthority, "users/#", 13);
    this.mUriMatcher.addURI(this.mAuthority, "settings", 9);
    this.mUriMatcher.addURI(this.mAuthority, "sync_request", 10);
    this.mUriMatcher.addURI(this.mAuthority, "sync_request/*", 11);
    this.mUriMatcher.addURI(this.mAuthority, "albumcovers/#", 14);
  }

  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    int i;
    switch (this.mUriMatcher.match(paramUri))
    {
    case 10:
    default:
      throw new IllegalArgumentException("unsupported uri:" + paramUri);
    case 11:
      List localList = paramUri.getPathSegments();
      if (localList.size() != 2)
        throw new IllegalArgumentException("Invalid URI: expect /sync_request/<task_ID>");
      String str = (String)localList.get(1);
      if (ImmediateSync.get(getContext()).cancelTask(str))
        i = 1;
      break;
    case 9:
    }
    while (true)
    {
      return i;
      i = 0;
      continue;
      if (resetSettings())
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
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    case 10:
    case 11:
    default:
      throw new IllegalArgumentException("Invalid URI: " + paramUri);
    case 1:
      str = "vnd.android.cursor.dir/vnd.google.android.picasasync.item";
    case 3:
    case 15:
    case 16:
    case 12:
    case 2:
    case 4:
    case 13:
    case 14:
    }
    while (true)
    {
      return str;
      str = "vnd.android.cursor.dir/vnd.google.android.picasasync.album";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.picasasync.post";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.picasasync.post_album";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.picasasync.user";
      continue;
      str = "vnd.android.cursor.item/vnd.google.android.picasasync.item";
      continue;
      str = "vnd.android.cursor.item/vnd.google.android.picasasync.album";
      continue;
      str = "vnd.android.cursor.item/vnd.google.android.picasasync.user";
      continue;
      str = "vnd.android.cursor.item/vnd.google.android.picasasync.album_cover";
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
      case 10:
      }
    }
    finally
    {
      MetricsUtils.end(i);
    }
    String str1 = paramContentValues.getAsString("task");
    if ("manual_metadata".equals(str1))
      PicasaSyncManager.get(getContext()).requestMetadataSync(true);
    while (true)
    {
      MetricsUtils.end(i);
      return paramUri;
      if ("metadata".equals(str1))
      {
        PicasaSyncManager.get(getContext()).requestMetadataSync(false);
      }
      else
      {
        if ("immediate_albums".equals(str1))
        {
          String str4 = paramContentValues.getAsString("account");
          Context localContext = getContext();
          if (str4 == null);
          for (String str5 = ImmediateSync.get(localContext).requestSyncAlbumListForAllAccounts(); ; str5 = ImmediateSync.get(localContext).requestSyncAlbumListForAccount(str4))
          {
            paramUri = paramUri.buildUpon().appendPath(str5).build();
            break;
          }
        }
        if ("immediate_photos".equals(str1))
        {
          String str2 = paramContentValues.getAsString("album_id");
          if (TextUtils.isEmpty(str2))
            throw new IllegalArgumentException("album ID missing");
          String str3 = ImmediateSync.get(getContext()).requestSyncAlbum(str2);
          Uri localUri = paramUri.buildUpon().appendPath(str3).build();
          paramUri = localUri;
        }
      }
    }
  }

  public boolean onCreate()
  {
    this.mDbHelper = PicasaDatabaseHelper.get(getContext());
    return true;
  }

  public ParcelFileDescriptor openFile(Uri paramUri, String paramString)
    throws FileNotFoundException
  {
    int i = MetricsUtils.begin("OPEN " + Utils.maskDebugInfo(paramUri.toString()));
    int j;
    try
    {
      j = this.mUriMatcher.match(paramUri);
      switch (j)
      {
      default:
        throw new IllegalArgumentException("unsupported uri: " + paramUri);
      case 2:
      case 14:
      }
    }
    finally
    {
      MetricsUtils.end(i);
    }
    Context localContext = getContext();
    Uri localUri1 = null;
    try
    {
      PicasaStoreFacade localPicasaStoreFacade = this.mPicasaStoreFacade;
      localUri1 = null;
      if (localPicasaStoreFacade == null)
        this.mPicasaStoreFacade = PicasaStoreFacade.get(localContext);
      localUri1 = paramUri.buildUpon().authority(this.mPicasaStoreFacade.getAuthority()).build();
      ParcelFileDescriptor localParcelFileDescriptor2 = localContext.getContentResolver().openFileDescriptor(localUri1, paramString);
      localObject2 = localParcelFileDescriptor2;
      MetricsUtils.end(i);
      return localObject2;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
      {
        String str1 = paramUri.getQueryParameter("content_url");
        if ((paramString.contains("w")) || (str1 != null))
          throw localFileNotFoundException;
        Log.d("gp.PicasaContentProvider", "FileNotFoundException, look up photo metadata for " + Utils.maskDebugInfo(paramUri.toString()));
        long l = getItemIdFromUri(paramUri);
        if (l == -1L)
          throw new FileNotFoundException(Utils.maskDebugInfo(paramUri.toString()));
        if (j == 14);
        for (String str2 = lookupAlbumCoverUrl(l); str2 == null; str2 = lookupContentUrl(l, paramUri.getQueryParameter("type")))
          throw localFileNotFoundException;
        Uri localUri2 = localUri1.buildUpon().appendQueryParameter("content_url", str2).build();
        ParcelFileDescriptor localParcelFileDescriptor1 = localContext.getContentResolver().openFileDescriptor(localUri2, paramString);
        Object localObject2 = localParcelFileDescriptor1;
        MetricsUtils.end(i);
      }
    }
  }

  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    int i = MetricsUtils.begin("QUERY " + Utils.maskDebugInfo(paramUri.toString()));
    try
    {
      switch (this.mUriMatcher.match(paramUri))
      {
      case 5:
      case 6:
      case 7:
      case 8:
      case 10:
      case 14:
      default:
        throw new IllegalArgumentException("Invalid URI: " + paramUri);
      case 12:
      case 3:
      case 15:
      case 16:
      case 1:
      case 13:
      case 4:
      case 2:
      case 9:
      case 11:
      }
    }
    finally
    {
      MetricsUtils.end(i);
    }
    String str9 = paramUri.getQueryParameter("limit");
    Object localObject2 = this.mDbHelper.getReadableDatabase().query(USER_TABLE_NAME, paramArrayOfString1, paramString1, paramArrayOfString2, null, null, paramString2, str9);
    MetricsUtils.incrementQueryResultCount(((Cursor)localObject2).getCount());
    MetricsUtils.end(i);
    return localObject2;
    String str6;
    String[] arrayOfString5;
    Object localObject3;
    if (paramString1 == null)
    {
      str6 = paramUri.getQueryParameter("type");
      if ("image".equals(str6))
      {
        String str8 = ALBUM_TYPE_WHERE;
        arrayOfString5 = new String[] { "image/%" };
        localObject3 = str8;
      }
    }
    while (true)
    {
      String str5 = paramUri.getQueryParameter("limit");
      localObject2 = this.mDbHelper.getReadableDatabase().query(ALBUM_TABLE_NAME, paramArrayOfString1, (String)localObject3, arrayOfString5, null, null, paramString2, str5);
      break;
      if ("video".equals(str6))
      {
        String str7 = ALBUM_TYPE_WHERE;
        arrayOfString5 = new String[] { "video/%" };
        localObject3 = str7;
        continue;
        WhereEntry localWhereEntry = new WhereEntry(paramUri, paramString2);
        localObject2 = this.mDbHelper.getReadableDatabase().query(PHOTO_TABLE_NAME, paramArrayOfString1, localWhereEntry.selection, localWhereEntry.selectionArgs, null, null, localWhereEntry.sortOrder, localWhereEntry.sortLimit);
        break;
        String str3 = "album_type = 'Buzz'";
        String str4 = paramUri.getQueryParameter("user_id");
        String[] arrayOfString4 = null;
        if (str4 != null)
        {
          str3 = "album_type = 'Buzz' AND user_id = ?";
          arrayOfString4 = new String[] { str4 };
        }
        localObject2 = this.mDbHelper.getReadableDatabase().query(ALBUM_TABLE_NAME, paramArrayOfString1, str3, arrayOfString4, null, null, null, null);
        break;
        String str2 = paramUri.getQueryParameter("limit");
        localObject2 = this.mDbHelper.getReadableDatabase().query(PHOTO_TABLE_NAME, paramArrayOfString1, paramString1, paramArrayOfString2, null, null, paramString2, str2);
        break;
        String[] arrayOfString3 = new String[1];
        arrayOfString3[0] = ((String)paramUri.getPathSegments().get(1));
        localObject2 = this.mDbHelper.getReadableDatabase().query(USER_TABLE_NAME, paramArrayOfString1, "_id=?", arrayOfString3, null, null, null);
        break;
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = ((String)paramUri.getPathSegments().get(1));
        localObject2 = this.mDbHelper.getReadableDatabase().query(ALBUM_TABLE_NAME, paramArrayOfString1, "_id=?", arrayOfString2, null, null, null);
        break;
        String[] arrayOfString1 = new String[1];
        arrayOfString1[0] = ((String)paramUri.getPathSegments().get(1));
        localObject2 = this.mDbHelper.getReadableDatabase().query(PHOTO_TABLE_NAME, paramArrayOfString1, "_id=?", arrayOfString1, null, null, null);
        break;
        localObject2 = querySettings$7be7850c(paramArrayOfString1);
        break;
        List localList = paramUri.getPathSegments();
        if (localList.size() != 2)
          throw new IllegalArgumentException("Invalid URI: expect /sync_request/<task_ID>");
        String str1 = (String)localList.get(1);
        int j = ImmediateSync.get(getContext()).getResult(str1);
        localObject2 = new PicasaMatrixCursor(new String[] { "immediate_sync_result" });
        ((PicasaMatrixCursor)localObject2).newRow().add(Integer.valueOf(j));
        break;
      }
      arrayOfString5 = paramArrayOfString2;
      localObject3 = paramString1;
    }
  }

  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    Integer localInteger;
    int i;
    switch (this.mUriMatcher.match(paramUri))
    {
    default:
      throw new IllegalArgumentException("unsupported uri:" + paramUri);
    case 4:
      localInteger = paramContentValues.getAsInteger("cache_flag");
      i = 0;
      if (localInteger != null)
        break;
    case 9:
    }
    while (true)
    {
      return i;
      long l = getLastSegmentAsLong(paramUri, -1L);
      if (l != -1L)
        PrefetchHelper.get(getContext()).setAlbumCachingFlag(l, localInteger.intValue());
      i = 1;
      continue;
      boolean bool = updateSettings(paramContentValues);
      i = 0;
      if (bool)
        i = 1;
    }
  }

  private static final class WhereEntry
  {
    private static final String TYPE_WHERE_CLAUSE = WHERE_CLAUSE + " AND content_type" + " LIKE ?";
    private static final String WHERE_CLAUSE = "album_id in (SELECT _id FROM " + PicasaContentProvider.ALBUM_TABLE_NAME + " WHERE album_type = 'Buzz' AND user_id = ?" + ")";
    public String selection;
    public String[] selectionArgs;
    public String sortLimit;
    public String sortOrder;

    public WhereEntry(Uri paramUri, String paramString)
    {
      String str1 = paramUri.getQueryParameter("user_id");
      String str2 = paramUri.getQueryParameter("type");
      if ("image".equals(str2))
      {
        this.selection = TYPE_WHERE_CLAUSE;
        this.selectionArgs = new String[] { str1, "image/%" };
      }
      while (true)
      {
        this.sortLimit = paramUri.getQueryParameter("limit");
        this.sortOrder = paramString;
        return;
        if ("video".equals(str2))
        {
          this.selection = TYPE_WHERE_CLAUSE;
          this.selectionArgs = new String[] { str1, "video/%" };
        }
        else
        {
          this.selection = WHERE_CLAUSE;
          this.selectionArgs = new String[] { str1 };
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.PicasaContentProvider
 * JD-Core Version:    0.6.2
 */