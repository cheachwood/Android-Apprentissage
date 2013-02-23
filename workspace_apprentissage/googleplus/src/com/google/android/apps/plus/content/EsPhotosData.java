package com.google.android.apps.plus.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.MediaStoreUtils;
import java.io.IOException;
import java.util.HashMap;

public class EsPhotosData
{
  static final int FINGERPRINT_STREAM_PREFIX_LENGTH = 6;

  static void cleanupData(SQLiteDatabase paramSQLiteDatabase, EsAccount paramEsAccount)
  {
    long l1 = DatabaseUtils.longForQuery(paramSQLiteDatabase, "SELECT count(*) FROM photo", null);
    long l2 = DatabaseUtils.longForQuery(paramSQLiteDatabase, "SELECT count(*) FROM album", null);
    String str = paramEsAccount.getGaiaId();
    paramSQLiteDatabase.delete("photos_of_user", "photo_of_user_id!=?", new String[] { str });
    paramSQLiteDatabase.delete("photos_in_stream", "photo_id IN ( SELECT photo_id FROM photos_by_stream_view WHERE owner_id!=? )", new String[] { str });
    paramSQLiteDatabase.delete("photos_in_album", "photo_id IN ( SELECT photo_id FROM photos_by_album_view WHERE owner_id!=? )", new String[] { str });
    paramSQLiteDatabase.delete("photos_in_event", "event_id NOT IN ( SELECT event_id FROM events )", null);
    paramSQLiteDatabase.delete("photo", "photo_id NOT IN ( SELECT photo_id FROM photos_in_album) AND photo_id NOT IN ( SELECT photo_id FROM photos_in_event) AND photo_id NOT IN ( SELECT photo_id FROM photos_in_stream) AND photo_id NOT IN ( SELECT photo_id FROM photos_of_user) AND photo_id NOT IN ( SELECT cover_photo_id FROM album) AND album_id NOT IN ( SELECT album_id FROM activities) ", null);
    paramSQLiteDatabase.delete("album", "owner_id != ? AND album_id NOT IN ( SELECT DISTINCT album_id FROM photo) ", new String[] { str });
    long l3 = DatabaseUtils.longForQuery(paramSQLiteDatabase, "SELECT count(*) FROM photo", null);
    long l4 = DatabaseUtils.longForQuery(paramSQLiteDatabase, "SELECT count(*) FROM album", null);
    if (EsLog.isLoggable("EsPhotosData", 3))
    {
      Log.d("EsPhotosData", "cleanupData removed dead photos; was: " + l1 + ", now: " + l3);
      Log.d("EsPhotosData", "cleanupData removed dead albums; was: " + l2 + ", now: " + l4);
    }
  }

  static HashMap<String, Long> getAlbumEntityMap(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    HashMap localHashMap = new HashMap();
    Cursor localCursor = paramSQLiteDatabase.query("album", new String[] { "album_id", "entity_version" }, "owner_id=? AND title IS NOT NULL", new String[] { paramString }, null, null, null);
    try
    {
      if (localCursor.moveToNext())
        localHashMap.put(localCursor.getString(0), Long.valueOf(localCursor.getLong(1)));
    }
    finally
    {
      localCursor.close();
    }
    return localHashMap;
  }

  static Long getAlbumRowId(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Cursor localCursor = paramSQLiteDatabase.query("album", new String[] { "_id" }, "album_id=?", new String[] { paramString }, null, null, null);
    try
    {
      boolean bool = localCursor.moveToNext();
      Object localObject2 = null;
      if (bool)
      {
        Long localLong = Long.valueOf(localCursor.getLong(0));
        localObject2 = localLong;
      }
      return localObject2;
    }
    finally
    {
      localCursor.close();
    }
  }

  static HashMap<Long, Long> getCurrentAlbumMap(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    Cursor localCursor = paramSQLiteDatabase.query("photos_by_album_view", new String[] { "photo_id", "entity_version" }, "owner_id=? AND album_id=?", new String[] { paramString2, paramString1 }, null, null, null);
    try
    {
      if (localCursor.moveToNext())
        localHashMap.put(Long.valueOf(localCursor.getLong(0)), Long.valueOf(localCursor.getLong(1)));
    }
    finally
    {
      localCursor.close();
    }
    return localHashMap;
  }

  static HashMap<Long, Long> getCurrentStreamMap(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    Cursor localCursor = paramSQLiteDatabase.query("photos_by_stream_view", new String[] { "photo_id", "entity_version" }, "owner_id=? AND stream_id=?", new String[] { paramString2, paramString1 }, null, null, null);
    try
    {
      if (localCursor.moveToNext())
        localHashMap.put(Long.valueOf(localCursor.getLong(0)), Long.valueOf(localCursor.getLong(1)));
    }
    finally
    {
      localCursor.close();
    }
    return localHashMap;
  }

  static String getDeltaTime(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    long l = System.currentTimeMillis() - paramLong;
    localStringBuilder.append(l / 1000L).append(".").append(l % 1000L).append(" seconds");
    return localStringBuilder.toString();
  }

  static Long getPhotoRowId(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Cursor localCursor = paramSQLiteDatabase.query("photo", new String[] { "_id" }, "photo_id=?", new String[] { paramString }, null, null, null);
    try
    {
      boolean bool = localCursor.moveToNext();
      Object localObject2 = null;
      if (bool)
      {
        Long localLong = Long.valueOf(localCursor.getLong(0));
        localObject2 = localLong;
      }
      return localObject2;
    }
    finally
    {
      localCursor.close();
    }
  }

  static long getPhotosHomeRowId(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Cursor localCursor = paramSQLiteDatabase.query("photo_home", new String[] { "_id" }, "type=?", new String[] { paramString }, null, null, null);
    try
    {
      if (localCursor.moveToNext())
      {
        long l2 = localCursor.getLong(0);
        l1 = l2;
        return l1;
      }
      long l1 = -1L;
    }
    finally
    {
      localCursor.close();
    }
  }

  static final byte[] hexToBytes(CharSequence paramCharSequence)
  {
    if ((paramCharSequence == null) || (paramCharSequence.length() == 0))
    {
      arrayOfByte = null;
      return arrayOfByte;
    }
    byte[] arrayOfByte = new byte[(1 + paramCharSequence.length()) / 2];
    arrayOfByte[0] = 0;
    int i = paramCharSequence.length() % 2;
    int j = 0;
    label45: char c;
    if (j < paramCharSequence.length())
    {
      c = paramCharSequence.charAt(j);
      if (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F')));
      for (int k = 1; k == 0; k = 0)
        throw new IllegalArgumentException("string contains non-hex chars");
      if (i % 2 != 0)
        break label158;
      arrayOfByte[(i >> 1)] = ((byte)(hexValue(c) << 4));
    }
    while (true)
    {
      i++;
      j++;
      break label45;
      break;
      label158: int m = i >> 1;
      arrayOfByte[m] = ((byte)(arrayOfByte[m] + (byte)hexValue(c)));
    }
  }

  private static final int hexValue(char paramChar)
  {
    int i;
    if ((paramChar >= '0') && (paramChar <= '9'))
      i = paramChar - '0';
    while (true)
    {
      return i;
      if ((paramChar >= 'a') && (paramChar <= 'f'))
        i = 10 + (paramChar - 'a');
      else
        i = 10 + (paramChar - 'A');
    }
  }

  public static Bitmap loadLocalBitmap(Context paramContext, LocalImageRequest paramLocalImageRequest)
  {
    Uri localUri = paramLocalImageRequest.getUri();
    int i = paramLocalImageRequest.getWidth();
    int j = paramLocalImageRequest.getHeight();
    ContentResolver localContentResolver = paramContext.getContentResolver();
    String str = ImageUtils.getMimeType(localContentResolver, localUri);
    Bitmap localBitmap;
    try
    {
      if (ImageUtils.isImageMimeType(str))
      {
        localBitmap = ImageUtils.createLocalBitmap(localContentResolver, localUri, Math.max(i, j));
      }
      else if (ImageUtils.isVideoMimeType(str))
      {
        localBitmap = MediaStoreUtils.getThumbnail(paramContext, localUri, i, j);
      }
      else
      {
        if (EsLog.isLoggable("EsPhotosData", 5))
          Log.w("EsPhotosData", "LocalImageRequest#loadBytes: unknown mimeType=" + str);
        localBitmap = null;
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      if (EsLog.isLoggable("EsPhotosData", 6))
        Log.e("EsPhotosData", "Could not load image", localOutOfMemoryError);
      localBitmap = null;
    }
    catch (IOException localIOException)
    {
      label138: break label138;
    }
    return localBitmap;
  }

  public static void syncPhotos(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState)
  {
    EsPhotosDataApiary.syncTopLevel(paramContext, paramEsAccount, paramSyncState);
  }

  static void updateCommentCount(SQLiteDatabase paramSQLiteDatabase, String paramString, int paramInt)
  {
    if (paramInt == 0);
    while (true)
    {
      return;
      String[] arrayOfString = { paramString };
      try
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("SELECT comment_count").append(" FROM photo").append(" WHERE photo_id=?");
        long l2 = DatabaseUtils.longForQuery(paramSQLiteDatabase, localStringBuilder.toString(), arrayOfString);
        l1 = l2;
        if (l1 < 0L)
          continue;
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("comment_count", Long.valueOf(Math.max(l1 + paramInt, 0L)));
        paramSQLiteDatabase.update("photo", localContentValues, "photo_id=?", arrayOfString);
      }
      catch (SQLiteDoneException localSQLiteDoneException)
      {
        while (true)
          long l1 = -1L;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsPhotosData
 * JD-Core Version:    0.6.2
 */