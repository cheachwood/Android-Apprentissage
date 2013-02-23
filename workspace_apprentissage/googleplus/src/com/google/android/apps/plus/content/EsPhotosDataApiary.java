package com.google.android.apps.plus.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import com.google.android.apps.plus.api.PhotosInAlbumOperation;
import com.google.android.apps.plus.api.PhotosOfUserOperation;
import com.google.android.apps.plus.api.UserPhotoAlbumsOperation;
import com.google.android.apps.plus.network.HttpOperation;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.android.apps.plus.util.EsLog;
import com.google.api.services.plusi.model.Comment;
import com.google.api.services.plusi.model.DataAlbum;
import com.google.api.services.plusi.model.DataComment;
import com.google.api.services.plusi.model.DataImage;
import com.google.api.services.plusi.model.DataPhoto;
import com.google.api.services.plusi.model.DataPlusOne;
import com.google.api.services.plusi.model.DataPlusOneJson;
import com.google.api.services.plusi.model.DataPoint32;
import com.google.api.services.plusi.model.DataRect32;
import com.google.api.services.plusi.model.DataRectRelativeJson;
import com.google.api.services.plusi.model.DataShape;
import com.google.api.services.plusi.model.DataUser;
import com.google.api.services.plusi.model.DataVideo;
import com.google.api.services.plusi.model.DataVideoJson;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class EsPhotosDataApiary extends EsPhotosData
{
  private static final String[] PHOTO_COMMENT_ID_COLUMN = { "plusone_data" };
  private static final DataPlusOneJson PLUS_ONE_JSON = DataPlusOneJson.getInstance();
  private static String sPhotosFromPostsAlbumName;

  public static void deletePhotoComment(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    long l1 = System.currentTimeMillis();
    String[] arrayOfString = { paramString };
    try
    {
      l2 = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT photo_id FROM photo_comment WHERE comment_id = ?", arrayOfString);
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      try
      {
        long l2;
        localSQLiteDatabase.beginTransaction();
        int i = localSQLiteDatabase.delete("photo_comment", "comment_id = ?", arrayOfString);
        updateCommentCount(localSQLiteDatabase, Long.toString(l2), -i);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        if (EsLog.isLoggable("EsPhotosData", 4))
          Log.i("EsPhotosData", "[DELETE_PHOTO_COMMENT], duration: " + getDeltaTime(l1));
        do
        {
          Uri localUri;
          return;
          localSQLiteDoneException = localSQLiteDoneException;
        }
        while (!EsLog.isLoggable("EsPhotosData", 5));
        Log.w("EsPhotosData", "WARNING: could not find photo for the comment: " + paramString);
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
        if (EsLog.isLoggable("EsPhotosData", 4))
          Log.i("EsPhotosData", "[DELETE_PHOTO_COMMENT], duration: " + getDeltaTime(l1));
      }
    }
  }

  private static void deletePhotoPlusOneRow(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    paramSQLiteDatabase.delete("photo_plusone", "photo_id=?", new String[] { paramString });
  }

  public static void deletePhotos$43585934(Context paramContext, EsAccount paramEsAccount, List<Long> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0));
    while (true)
    {
      return;
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      long l = System.currentTimeMillis();
      HashMap localHashMap;
      StringBuilder localStringBuilder;
      String[] arrayOfString1;
      try
      {
        localSQLiteDatabase.beginTransaction();
        localHashMap = new HashMap();
        localStringBuilder = new StringBuilder();
        arrayOfString1 = new String[paramList.size()];
        localStringBuilder.append("photo_id IN(");
        int i = -1 + paramList.size();
        while (true)
          if (i >= 0)
          {
            String str2 = Long.toString(((Long)paramList.get(i)).longValue());
            if (EsLog.isLoggable("EsPhotosData", 3))
              Log.d("EsPhotosData", ">> deletePhoto photo id: " + str2);
            String[] arrayOfString3 = { str2 };
            try
            {
              String str3 = DatabaseUtils.stringForQuery(localSQLiteDatabase, "SELECT album_id FROM photo WHERE photo_id = ?", arrayOfString3);
              Integer localInteger = (Integer)localHashMap.get(str3);
              if (localInteger == null)
                localInteger = Integer.valueOf(0);
              localHashMap.put(str3, Integer.valueOf(-1 + localInteger.intValue()));
              localStringBuilder.append("?,");
              arrayOfString1[i] = str2;
              i--;
            }
            catch (SQLiteDoneException localSQLiteDoneException2)
            {
              while (true)
                if (EsLog.isLoggable("EsPhotosData", 5))
                  Log.w("EsPhotosData", "Album not found for photo: " + str2);
            }
          }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
        if (EsLog.isLoggable("EsPhotosData", 4))
          Log.i("EsPhotosData", "[DELETE_PHOTOS], duration: " + getDeltaTime(l));
      }
      localStringBuilder.setLength(-1 + localStringBuilder.length());
      localStringBuilder.append(")");
      ContentValues localContentValues = new ContentValues();
      Iterator localIterator = localHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String[] arrayOfString2 = { str1 };
        int j = ((Integer)localHashMap.get(str1)).intValue();
        try
        {
          localContentValues.put("photo_count", Long.valueOf(Math.max(0L, DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT photo_count FROM album WHERE photo_count NOT NULL AND album_id = ?", arrayOfString2) + j)));
          localSQLiteDatabase.update("album", localContentValues, "album_id = ?", arrayOfString2);
        }
        catch (SQLiteDoneException localSQLiteDoneException1)
        {
        }
        if (EsLog.isLoggable("EsPhotosData", 4))
          Log.i("EsPhotosData", "Photo count not found; album id: " + str1);
      }
      localSQLiteDatabase.delete("photo", localStringBuilder.toString(), arrayOfString1);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      if (EsLog.isLoggable("EsPhotosData", 4))
        Log.i("EsPhotosData", "[DELETE_PHOTOS], duration: " + getDeltaTime(l));
      paramContext.getContentResolver().notifyChange(EsProvider.PHOTO_URI, null);
    }
  }

  private static void deletePhotosInTransaction(SQLiteDatabase paramSQLiteDatabase, Map<Long, Long> paramMap)
  {
    if (paramMap.size() > 0)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      ArrayList localArrayList = new ArrayList(paramMap.size());
      localStringBuilder.append("photo_id IN(");
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        Long localLong = (Long)localIterator.next();
        localStringBuilder.append("?,");
        localArrayList.add(Long.toString(localLong.longValue()));
      }
      localStringBuilder.setLength(-1 + localStringBuilder.length());
      localStringBuilder.append(")");
      paramSQLiteDatabase.delete("photo", localStringBuilder.toString(), (String[])localArrayList.toArray(new String[0]));
    }
  }

  private static String getAlbumId(DataAlbum paramDataAlbum)
  {
    try
    {
      Long.parseLong(paramDataAlbum.id);
      str = paramDataAlbum.id;
      return str;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        String str = paramDataAlbum.id + "_" + paramDataAlbum.owner.id;
    }
  }

  private static String getAlbumOutput(DataAlbum paramDataAlbum, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramInt > 0)
      for (int i = 0; i < paramInt; i++)
        localStringBuilder.append(' ');
    String str = localStringBuilder.toString();
    localStringBuilder.setLength(0);
    localStringBuilder.append(str).append("ALBUM [id: ").append(getAlbumId(paramDataAlbum)).append(", owner: ").append(paramDataAlbum.owner.id).append(", count: ").append(paramDataAlbum.photoCount);
    if (paramDataAlbum.albumType != null)
      localStringBuilder.append(",\n").append(str).append("       type: ").append(paramDataAlbum.albumType);
    if (paramDataAlbum.title != null)
      localStringBuilder.append(",\n").append(str).append("       title: ").append(paramDataAlbum.title);
    if (paramDataAlbum.cover != null)
      localStringBuilder.append("\n").append(getCoverPhotoOutput(paramDataAlbum.cover, paramInt + 2));
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }

  private static ContentValues getCommentContentValues(DataComment paramDataComment, String paramString)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("photo_id", paramString);
    localContentValues.put("comment_id", paramDataComment.id);
    localContentValues.put("author_id", paramDataComment.user.id);
    localContentValues.put("content", paramDataComment.text);
    if (paramDataComment.timestamp != null)
      localContentValues.put("create_time", Long.valueOf(()(1000.0D * Double.parseDouble(paramDataComment.timestamp))));
    if (paramDataComment.lastUpdateTimestamp != null)
      localContentValues.put("update_time", paramDataComment.lastUpdateTimestamp);
    if (paramDataComment.plusOne != null)
      localContentValues.put("plusone_data", PLUS_ONE_JSON.toString(paramDataComment.plusOne));
    while (true)
    {
      return localContentValues;
      localContentValues.putNull("plusone_data");
    }
  }

  private static DataPlusOne getCommentPlusOneData(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Cursor localCursor = paramSQLiteDatabase.query("photo_comment", PHOTO_COMMENT_ID_COLUMN, "comment_id=?", new String[] { paramString }, null, null, null);
    try
    {
      if ((localCursor.moveToFirst()) && (!localCursor.isNull(0)))
      {
        localDataPlusOne = (DataPlusOne)PLUS_ONE_JSON.fromByteArray(localCursor.getBlob(0));
        return localDataPlusOne;
      }
      localCursor.close();
      DataPlusOne localDataPlusOne = null;
    }
    finally
    {
      localCursor.close();
    }
  }

  private static String getCoverPhotoOutput(DataPhoto paramDataPhoto, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramInt > 0)
      for (int i = 0; i < paramInt; i++)
        localStringBuilder.append(' ');
    String str1 = localStringBuilder.toString();
    localStringBuilder.setLength(0);
    long l;
    if (paramDataPhoto.timestampSeconds == null)
    {
      l = 0L;
      localStringBuilder.append(str1).append("COVER PHOTO [id: ").append(paramDataPhoto.id).append(", owner: ");
      if (paramDataPhoto.owner != null)
        break label173;
    }
    label173: for (String str2 = "N/A"; ; str2 = paramDataPhoto.owner.id)
    {
      localStringBuilder.append(str2);
      if (l != 0L)
      {
        CharSequence localCharSequence = DateFormat.format("MMM dd, yyyy h:mmaa", new Date(l));
        localStringBuilder.append(", date: ").append(localCharSequence);
      }
      localStringBuilder.append("]");
      localStringBuilder.append("\n");
      return localStringBuilder.toString();
      l = ()(1000.0D * paramDataPhoto.timestampSeconds.doubleValue());
      break;
    }
  }

  private static byte[] getFingerPrint(DataPhoto paramDataPhoto)
  {
    String str;
    if (paramDataPhoto.streamId != null)
    {
      Iterator localIterator = paramDataPhoto.streamId.iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        str = (String)localIterator.next();
      }
      while (!str.startsWith("cs_01_"));
    }
    for (byte[] arrayOfByte = hexToBytes(str.substring(FINGERPRINT_STREAM_PREFIX_LENGTH)); ; arrayOfByte = null)
      return arrayOfByte;
  }

  private static ContentValues getPhotoCommentPlusOneContentValues(DataPlusOne paramDataPlusOne)
  {
    ContentValues localContentValues = new ContentValues();
    if (paramDataPlusOne == null)
      localContentValues.putNull("plusone_data");
    while (true)
    {
      return localContentValues;
      localContentValues.put("plusone_data", PLUS_ONE_JSON.toString(paramDataPlusOne));
    }
  }

  private static String getPhotoOutput(DataPhoto paramDataPhoto, int paramInt)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    String str1 = localStringBuilder1.toString();
    localStringBuilder1.setLength(0);
    DataPlusOne localDataPlusOne = paramDataPhoto.plusOne;
    DataVideo localDataVideo = paramDataPhoto.video;
    double d;
    String str2;
    label87: String str3;
    label178: boolean bool;
    label213: int i;
    label246: Object localObject1;
    label285: StringBuilder localStringBuilder9;
    StringBuilder localStringBuilder10;
    if (paramDataPhoto.timestampSeconds == null)
    {
      d = 0.0D;
      long l = 1000L * ()d;
      StringBuilder localStringBuilder2 = localStringBuilder1.append(str1).append("PHOTO [id: ").append(paramDataPhoto.id).append(", owner: ");
      if (paramDataPhoto.owner != null)
        break label426;
      str2 = "N/A";
      localStringBuilder2.append(str2).append(", version: ").append(paramDataPhoto.entityVersion);
      if (l != 0L)
      {
        CharSequence localCharSequence = DateFormat.format("MMM dd, yyyy h:mmaa", new Date(l));
        localStringBuilder1.append(", date: ").append(localCharSequence);
      }
      localStringBuilder1.append(", \n");
      StringBuilder localStringBuilder3 = localStringBuilder1.append(str1).append("      title: ");
      if (paramDataPhoto.title != null)
        break label438;
      str3 = "N/A";
      StringBuilder localStringBuilder4 = localStringBuilder3.append(str3).append(", ");
      StringBuilder localStringBuilder5 = new StringBuilder("video? ");
      if (localDataVideo == null)
        break label447;
      bool = true;
      StringBuilder localStringBuilder6 = localStringBuilder4.append(bool).append(", comments: ");
      if (paramDataPhoto.totalComments != null)
        break label453;
      i = 0;
      StringBuilder localStringBuilder7 = localStringBuilder6.append(i).append(", ");
      StringBuilder localStringBuilder8 = new StringBuilder("+1s: ");
      if (localDataPlusOne == null)
        break label465;
      localObject1 = localDataPlusOne.globalCount;
      localStringBuilder9 = localStringBuilder7.append(localObject1).append(", ");
      localStringBuilder10 = new StringBuilder("by me: ");
      if (localDataPlusOne == null)
        break label473;
    }
    label426: label438: label447: label453: label465: label473: for (Object localObject2 = localDataPlusOne.isPlusonedByViewer; ; localObject2 = "false")
    {
      localStringBuilder9.append(localObject2);
      if (paramDataPhoto.streamId == null)
        break label481;
      Iterator localIterator2 = paramDataPhoto.streamId.iterator();
      while (localIterator2.hasNext())
      {
        String str4 = (String)localIterator2.next();
        localStringBuilder1.append(", \n").append(str1).append("      stream: ").append(str4);
      }
      d = paramDataPhoto.timestampSeconds.doubleValue();
      break;
      str2 = paramDataPhoto.owner.id;
      break label87;
      str3 = paramDataPhoto.title;
      break label178;
      bool = false;
      break label213;
      i = paramDataPhoto.totalComments.intValue();
      break label246;
      localObject1 = "0";
      break label285;
    }
    label481: if (paramDataPhoto.album != null)
      localStringBuilder1.append("\n").append(getAlbumOutput(paramDataPhoto.album, 2));
    if (paramDataPhoto.shape != null)
    {
      Iterator localIterator1 = paramDataPhoto.shape.iterator();
      while (localIterator1.hasNext())
      {
        DataShape localDataShape = (DataShape)localIterator1.next();
        localStringBuilder1.append("\n").append(getShapeOutput(localDataShape, 2));
      }
    }
    localStringBuilder1.append("]");
    localStringBuilder1.append("\n");
    localStringBuilder1.append("\n");
    return localStringBuilder1.toString();
  }

  private static ContentValues getShapeContentValues(DataShape paramDataShape, String paramString1, String paramString2)
  {
    ContentValues localContentValues = new ContentValues();
    if (paramDataShape.relativeBounds != null)
      localContentValues.put("bounds", DataRectRelativeJson.getInstance().toString(paramDataShape.relativeBounds));
    localContentValues.put("creator_id", paramDataShape.creator.id);
    localContentValues.put("photo_id", Long.valueOf(Long.parseLong(paramString1)));
    localContentValues.put("shape_id", paramDataShape.id);
    localContentValues.put("status", paramDataShape.status);
    if (paramDataShape.user != null)
      localContentValues.put("subject_id", paramDataShape.user.id);
    while (true)
    {
      return localContentValues;
      if ((paramDataShape.suggestion != null) && (!paramDataShape.suggestion.isEmpty()))
      {
        Iterator localIterator = paramDataShape.suggestion.iterator();
        if (localIterator.hasNext())
        {
          DataUser localDataUser = (DataUser)localIterator.next();
          if ((localDataUser.id == null) || (!localDataUser.id.equals(paramString2)))
            break;
          localContentValues.put("subject_id", localDataUser.id);
        }
      }
    }
  }

  private static String getShapeOutput(DataShape paramDataShape, int paramInt)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    if (paramInt > 0)
      for (int i = 0; i < paramInt; i++)
        localStringBuilder1.append(' ');
    String str1 = localStringBuilder1.toString();
    localStringBuilder1.setLength(0);
    DataRect32 localDataRect32 = paramDataShape.bounds;
    StringBuilder localStringBuilder2 = localStringBuilder1.append(str1).append("SHAPE [(");
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = localDataRect32.upperLeft.x;
    arrayOfObject[1] = localDataRect32.upperLeft.y;
    arrayOfObject[2] = localDataRect32.lowerRight.x;
    arrayOfObject[3] = localDataRect32.lowerRight.y;
    StringBuilder localStringBuilder3 = localStringBuilder2.append(String.format("%d, %d, %d, %d", arrayOfObject)).append("), ").append("subjectId: ");
    String str2;
    StringBuilder localStringBuilder4;
    String str7;
    label236: String str6;
    label276: String str5;
    if (paramDataShape.user == null)
    {
      str2 = "N/A";
      localStringBuilder3.append(str2).append(", status: ").append(paramDataShape.status);
      Boolean localBoolean1 = Boolean.valueOf(false);
      Boolean localBoolean2 = Boolean.valueOf(false);
      Boolean localBoolean3 = paramDataShape.viewerCanEdit;
      Boolean localBoolean4 = paramDataShape.viewerCanApprove;
      localStringBuilder4 = new StringBuilder();
      if ((localBoolean1 != null) && (localBoolean1.booleanValue()))
      {
        if (localStringBuilder4.length() != 0)
          break label433;
        str7 = "";
        localStringBuilder4.append(str7).append("COMMENT");
      }
      if ((localBoolean2 != null) && (localBoolean2.booleanValue()))
      {
        if (localStringBuilder4.length() != 0)
          break label441;
        str6 = "";
        localStringBuilder4.append(str6).append("TAG");
      }
      if ((localBoolean3 != null) && (localBoolean3.booleanValue()))
      {
        if (localStringBuilder4.length() != 0)
          break label449;
        str5 = "";
        label316: localStringBuilder4.append(str5).append("EDIT");
      }
      if ((localBoolean4 != null) && (localBoolean4.booleanValue()))
        if (localStringBuilder4.length() != 0)
          break label457;
    }
    label433: label441: label449: label457: for (String str4 = ""; ; str4 = "|")
    {
      localStringBuilder4.append(str4).append("APPROVE");
      String str3 = localStringBuilder4.toString();
      if (!TextUtils.isEmpty(str3))
        localStringBuilder1.append(", \n").append(str1).append("       state: ").append(str3);
      localStringBuilder1.append("]");
      return localStringBuilder1.toString();
      str2 = paramDataShape.user.id;
      break;
      str7 = "|";
      break label236;
      str6 = "|";
      break label276;
      str5 = "|";
      break label316;
    }
  }

  private static void insertAlbumInTransaction(SQLiteDatabase paramSQLiteDatabase, DataAlbum paramDataAlbum, Long paramLong, List<Uri> paramList)
  {
    Long localLong1 = paramDataAlbum.entityVersion;
    if ((paramLong != null) && (paramLong.equals(localLong1)))
      if (EsLog.isLoggable("EsPhotosData", 3))
        Log.d("EsPhotosData", "Album not updated; id: " + paramDataAlbum.id);
    label313: 
    while (true)
    {
      return;
      Long localLong2 = insertOrUpdateAlbumRow(paramSQLiteDatabase, paramDataAlbum);
      if (localLong2 == null)
      {
        if (EsLog.isLoggable("EsPhotosData", 5))
          Log.w("EsPhotosData", "Could not insert album row");
      }
      else
      {
        boolean bool = "UPDATES_ALBUMS".equals(paramDataAlbum.albumType);
        int i;
        label117: DataPhoto localDataPhoto;
        long l;
        if (!TextUtils.equals(paramDataAlbum.id, getAlbumId(paramDataAlbum)))
        {
          i = 1;
          if ((!bool) || (i != 0))
          {
            localDataPhoto = paramDataAlbum.cover;
            l = localLong2.longValue();
            if ((localDataPhoto != null) && (localDataPhoto.original != null))
              break label189;
          }
        }
        while (true)
        {
          if (paramList == null)
            break label313;
          paramList.add(EsProvider.PHOTO_BY_ALBUM_URI.buildUpon().appendEncodedPath(getAlbumId(paramDataAlbum)).build());
          break;
          i = 0;
          break label117;
          label189: String[] arrayOfString = new String[1];
          arrayOfString[0] = Long.toString(l);
          paramSQLiteDatabase.delete("album_cover", "album_key=?", arrayOfString);
          ContentValues localContentValues = new ContentValues();
          localContentValues.put("album_key", Long.valueOf(l));
          localContentValues.put("url", localDataPhoto.original.url);
          localContentValues.put("width", localDataPhoto.original.width);
          localContentValues.put("height", localDataPhoto.original.height);
          localContentValues.put("size", localDataPhoto.fileSize);
          paramSQLiteDatabase.insertWithOnConflict("album_cover", null, localContentValues, 4);
        }
      }
    }
  }

  private static void insertAlbumListInTransaction(SQLiteDatabase paramSQLiteDatabase, List<DataAlbum> paramList, Map<String, Long> paramMap, List<Uri> paramList1, EsSyncAdapterService.SyncState paramSyncState)
  {
    int i = paramList.size();
    for (int j = 0; j < i; j++)
    {
      DataAlbum localDataAlbum = (DataAlbum)paramList.get(j);
      if (EsLog.isLoggable("EsPhotosData", 3))
        EsLog.writeToLog(3, "EsPhotosData", getAlbumOutput(localDataAlbum, 0));
      if (paramSyncState != null)
        paramSyncState.incrementCount();
      insertAlbumInTransaction(paramSQLiteDatabase, localDataAlbum, (Long)paramMap.remove(getAlbumId(localDataAlbum)), paramList1);
    }
  }

  public static void insertAlbumPhotos(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, DataAlbum paramDataAlbum, List<DataPhoto> paramList, Boolean paramBoolean)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    long l = System.currentTimeMillis();
    String str = getAlbumId(paramDataAlbum);
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = getCurrentAlbumMap(localSQLiteDatabase, str, paramDataAlbum.owner.id);
    if (EsLog.isLoggable("EsPhotosData", 3))
      EsLog.writeToLog(3, "EsPhotosData", getAlbumOutput(paramDataAlbum, 0));
    while (true)
    {
      try
      {
        localSQLiteDatabase.beginTransaction();
        if (paramSyncState != null)
          paramSyncState.incrementCount();
        String[] arrayOfString1 = { "entity_version" };
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = paramDataAlbum.id;
        localCursor = localSQLiteDatabase.query("album", arrayOfString1, "album_id=?", arrayOfString2, null, null, null);
      }
      finally
      {
        try
        {
          if (localCursor.moveToFirst())
          {
            Long localLong2 = Long.valueOf(localCursor.getLong(0));
            localLong1 = localLong2;
            localCursor.close();
            insertAlbumInTransaction(localSQLiteDatabase, paramDataAlbum, localLong1, localArrayList);
            ContentValues localContentValues = new ContentValues();
            localContentValues.put("album_id", str);
            insertPhotosInTransaction(localSQLiteDatabase, paramList, paramBoolean, paramDataAlbum, localHashMap, "photos_in_album", localContentValues, localArrayList, paramSyncState);
            deletePhotosInTransaction(localSQLiteDatabase, localHashMap);
            localSQLiteDatabase.setTransactionSuccessful();
            localSQLiteDatabase.endTransaction();
            if (EsLog.isLoggable("EsPhotosData", 4))
            {
              StringBuilder localStringBuilder2 = new StringBuilder("[INSERT_ALBUM_PHOTOS], album ID: ").append(str).append(", num photos: ");
              if (paramList == null)
                break label447;
              j = paramList.size();
              Log.i("EsPhotosData", j + ", duration: " + getDeltaTime(l));
            }
            ContentResolver localContentResolver = paramContext.getContentResolver();
            Iterator localIterator = localArrayList.iterator();
            if (!localIterator.hasNext())
              break;
            localContentResolver.notifyChange((Uri)localIterator.next(), null);
            continue;
          }
          Long localLong1 = null;
        }
        finally
        {
          Cursor localCursor;
          localCursor.close();
        }
        localSQLiteDatabase.endTransaction();
        if (EsLog.isLoggable("EsPhotosData", 4))
        {
          StringBuilder localStringBuilder1 = new StringBuilder("[INSERT_ALBUM_PHOTOS], album ID: ").append(str).append(", num photos: ");
          if (paramList == null)
            break label453;
          i = paramList.size();
          Log.i("EsPhotosData", i + ", duration: " + getDeltaTime(l));
        }
      }
      label447: int j = 0;
      continue;
      label453: int i = 0;
    }
  }

  public static void insertAlbums(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, String paramString, List<DataAlbum> paramList1, List<DataAlbum> paramList2)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    long l = System.currentTimeMillis();
    HashMap localHashMap = getAlbumEntityMap(localSQLiteDatabase, paramString);
    ArrayList localArrayList1 = new ArrayList();
    int i = 0;
    ArrayList localArrayList2;
    StringBuilder localStringBuilder;
    try
    {
      localSQLiteDatabase.beginTransaction();
      i = 0;
      if (paramList1 != null)
      {
        i = 0 + paramList1.size();
        insertAlbumListInTransaction(localSQLiteDatabase, paramList1, localHashMap, localArrayList1, paramSyncState);
      }
      if (paramList2 != null)
      {
        i += paramList2.size();
        insertAlbumListInTransaction(localSQLiteDatabase, paramList2, localHashMap, localArrayList1, paramSyncState);
      }
      int j = localHashMap.size();
      if (j <= 0)
        break label297;
      localArrayList2 = new ArrayList(j);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("album_type == 'ALL_OTHERS' AND album_id IN(");
      Iterator localIterator1 = localHashMap.keySet().iterator();
      while (localIterator1.hasNext())
      {
        String str2 = (String)localIterator1.next();
        localStringBuilder.append("?,");
        localArrayList2.add(str2);
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
      if (EsLog.isLoggable("EsPhotosData", 4))
        Log.i("EsPhotosData", "[INSERT_ALBUM_LIST], num albums: " + i + ", duration: " + getDeltaTime(l));
    }
    localStringBuilder.setLength(-1 + localStringBuilder.length());
    localStringBuilder.append(")");
    localSQLiteDatabase.delete("album", localStringBuilder.toString(), (String[])localArrayList2.toArray(new String[0]));
    label297: localSQLiteDatabase.setTransactionSuccessful();
    localSQLiteDatabase.endTransaction();
    if (EsLog.isLoggable("EsPhotosData", 4))
      Log.i("EsPhotosData", "[INSERT_ALBUM_LIST], num albums: " + i + ", duration: " + getDeltaTime(l));
    Iterator localIterator2 = localArrayList1.iterator();
    while (localIterator2.hasNext())
    {
      Uri localUri3 = (Uri)localIterator2.next();
      paramContext.getContentResolver().notifyChange(localUri3, null);
    }
    Iterator localIterator3 = localHashMap.keySet().iterator();
    while (localIterator3.hasNext())
    {
      String str1 = (String)localIterator3.next();
      Uri localUri2 = Uri.withAppendedPath(EsProvider.PHOTO_BY_ALBUM_URI, str1);
      paramContext.getContentResolver().notifyChange(localUri2, null);
    }
    if ((i > 0) || (localHashMap.size() > 0))
    {
      Uri localUri1 = Uri.withAppendedPath(EsProvider.ALBUM_VIEW_BY_OWNER_URI, paramString);
      paramContext.getContentResolver().notifyChange(localUri1, null);
    }
  }

  public static void insertEventPhotoInTransaction(SQLiteDatabase paramSQLiteDatabase, DataPhoto paramDataPhoto, String paramString, Map<String, Long> paramMap, List<Uri> paramList)
  {
    long l = System.currentTimeMillis();
    try
    {
      if (EsLog.isLoggable("EsPhotosData", 3))
        EsLog.writeToLog(3, "EsPhotosData", getPhotoOutput(paramDataPhoto, 0));
      if ((insertPhotoInTransaction(paramSQLiteDatabase, paramDataPhoto, null, true, paramMap, null, paramList, null) == null) && (EsLog.isLoggable("EsPhotosData", 5)))
        Log.w("EsPhotosData", "Could not insert row for event photo; id: " + paramDataPhoto.id);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("event_id", paramString);
      localContentValues.put("photo_id", Long.valueOf(Long.parseLong(paramDataPhoto.id)));
      String[] arrayOfString = new String[2];
      arrayOfString[0] = paramString;
      arrayOfString[1] = paramDataPhoto.id;
      if (DatabaseUtils.longForQuery(paramSQLiteDatabase, "SELECT count(*) FROM photos_in_event WHERE event_id=? AND photo_id=?", arrayOfString) == 0L)
        paramSQLiteDatabase.insert("photos_in_event", null, localContentValues);
      while (true)
      {
        return;
        paramSQLiteDatabase.update("photos_in_event", localContentValues, "event_id=? AND photo_id=?", arrayOfString);
      }
    }
    finally
    {
      if (EsLog.isLoggable("EsPhotosData", 4))
        Log.i("EsPhotosData", "[INSERT_EVENT_PHOTO], event: " + paramString + ", duration: " + getDeltaTime(l));
    }
  }

  private static Long insertOrUpdateAlbumRow(SQLiteDatabase paramSQLiteDatabase, DataAlbum paramDataAlbum)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("album_id", getAlbumId(paramDataAlbum));
    localContentValues.put("owner_id", paramDataAlbum.owner.id);
    if (!TextUtils.isEmpty(paramDataAlbum.title))
      localContentValues.put("title", paramDataAlbum.title);
    if (paramDataAlbum.timestampSeconds != null)
      localContentValues.put("timestamp", Long.valueOf(()(1000.0D * Double.parseDouble(paramDataAlbum.timestampSeconds))));
    localContentValues.put("album_type", paramDataAlbum.albumType);
    if (paramDataAlbum.entityVersion != null)
      localContentValues.put("entity_version", paramDataAlbum.entityVersion);
    label185: String str;
    Long localLong;
    if (!"ALL_OTHERS".equals(paramDataAlbum.albumType))
      if ("UPDATES_ALBUMS".equals(paramDataAlbum.albumType))
      {
        localContentValues.put("stream_id", "posts");
        localContentValues.put("sort_order", Integer.valueOf(40));
        if (!TextUtils.isEmpty(sPhotosFromPostsAlbumName))
          localContentValues.put("title", sPhotosFromPostsAlbumName);
        localContentValues.putNull("photo_count");
        if ((paramDataAlbum.cover != null) && (paramDataAlbum.cover.id != null))
          localContentValues.put("cover_photo_id", Long.valueOf(Long.parseLong(paramDataAlbum.cover.id)));
        str = getAlbumId(paramDataAlbum);
        localLong = getAlbumRowId(paramSQLiteDatabase, str);
        if (localLong != null)
          break label397;
        localLong = Long.valueOf(paramSQLiteDatabase.insertWithOnConflict("album", null, localContentValues, 4));
        if (localLong.longValue() == -1L)
          localLong = null;
      }
    while (true)
    {
      return localLong;
      if ("BUNCH_ALBUMS".equals(paramDataAlbum.albumType))
      {
        localContentValues.put("stream_id", "messenger");
        localContentValues.put("sort_order", Integer.valueOf(50));
        break;
      }
      if ("PROFILE_PHOTOS".equals(paramDataAlbum.albumType))
      {
        localContentValues.put("stream_id", "profile");
        localContentValues.put("sort_order", Integer.valueOf(60));
        break;
      }
      localContentValues.putNull("stream_id");
      break;
      localContentValues.put("sort_order", Integer.valueOf(100));
      localContentValues.putNull("stream_id");
      if (paramDataAlbum.photoCount == null)
        break label185;
      localContentValues.put("photo_count", paramDataAlbum.photoCount);
      break label185;
      label397: if (paramSQLiteDatabase.update("album", localContentValues, "album_id=?", new String[] { str }) == 0)
        localLong = null;
    }
  }

  private static boolean insertOrUpdatePhotoCommentRow$1e3cff3e(SQLiteDatabase paramSQLiteDatabase, String paramString, ContentValues paramContentValues)
  {
    boolean bool = true;
    String[] arrayOfString1 = new String[bool];
    arrayOfString1[0] = paramString;
    if (DatabaseUtils.longForQuery(paramSQLiteDatabase, "SELECT count(*) FROM photo_comment WHERE comment_id=?", arrayOfString1) == 0L)
      if (paramSQLiteDatabase.insertWithOnConflict("photo_comment", null, paramContentValues, 4) == -1L);
    while (true)
    {
      return bool;
      bool = false;
      continue;
      String[] arrayOfString2 = new String[bool];
      arrayOfString2[0] = paramString;
      if (paramSQLiteDatabase.update("photo_comment", paramContentValues, "comment_id=?", arrayOfString2) == 0)
        bool = false;
    }
  }

  private static boolean insertOrUpdatePhotoPlusOneRow(SQLiteDatabase paramSQLiteDatabase, DataPlusOne paramDataPlusOne, String paramString)
  {
    ContentValues localContentValues = new ContentValues();
    boolean bool1;
    int i;
    label28: boolean bool2;
    if (paramDataPlusOne.isPlusonedByViewer == null)
    {
      bool1 = false;
      if (paramDataPlusOne.globalCount != null)
        break label145;
      i = 0;
      localContentValues.put("plusone_data", PLUS_ONE_JSON.toString(paramDataPlusOne));
      localContentValues.put("plusone_by_me", Boolean.valueOf(bool1));
      localContentValues.put("plusone_count", Integer.valueOf(i));
      localContentValues.put("plusone_id", paramDataPlusOne.id);
      localContentValues.put("photo_id", paramString);
      if (DatabaseUtils.longForQuery(paramSQLiteDatabase, "SELECT count(*) FROM photo_plusone WHERE photo_id=?", new String[] { paramString }) != 0L)
        break label157;
      boolean bool3 = paramSQLiteDatabase.insertWithOnConflict("photo_plusone", null, localContentValues, 4) < -1L;
      bool2 = false;
      if (bool3)
        bool2 = true;
    }
    while (true)
    {
      return bool2;
      bool1 = paramDataPlusOne.isPlusonedByViewer.booleanValue();
      break;
      label145: i = paramDataPlusOne.globalCount.intValue();
      break label28;
      label157: int j = paramSQLiteDatabase.update("photo_plusone", localContentValues, "photo_id=?", new String[] { paramString });
      bool2 = false;
      if (j != 0)
        bool2 = true;
    }
  }

  public static void insertPhoto(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, DataPhoto paramDataPhoto, Boolean paramBoolean)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    long l = System.currentTimeMillis();
    ArrayList localArrayList = new ArrayList();
    try
    {
      localSQLiteDatabase.beginTransaction();
      if (EsLog.isLoggable("EsPhotosData", 3))
        EsLog.writeToLog(3, "EsPhotosData", getPhotoOutput(paramDataPhoto, 0));
      HashSet localHashSet = new HashSet();
      if ((insertPhotoInTransaction(localSQLiteDatabase, paramDataPhoto, paramBoolean, true, null, localHashSet, localArrayList, paramEsAccount.getGaiaId()) == null) && (EsLog.isLoggable("EsPhotosData", 5)))
        Log.w("EsPhotosData", "Could not insert row for photo of me; id: " + paramDataPhoto.id);
      EsPeopleData.replaceUsersInTransaction(localSQLiteDatabase, new ArrayList(localHashSet));
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      if (EsLog.isLoggable("EsPhotosData", 4))
        Log.i("EsPhotosData", "[INSERT_PHOTO], photo ID: " + paramDataPhoto.id + ", duration: " + getDeltaTime(l));
      Iterator localIterator = localArrayList.iterator();
      if (localIterator.hasNext())
      {
        Uri localUri = (Uri)localIterator.next();
        paramContext.getContentResolver().notifyChange(localUri, null);
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
      if (EsLog.isLoggable("EsPhotosData", 4))
        Log.i("EsPhotosData", "[INSERT_PHOTO], photo ID: " + paramDataPhoto.id + ", duration: " + getDeltaTime(l));
    }
  }

  private static Long insertPhotoInTransaction(SQLiteDatabase paramSQLiteDatabase, DataPhoto paramDataPhoto, Boolean paramBoolean, boolean paramBoolean1, Map<String, Long> paramMap, Set<DataUser> paramSet, List<Uri> paramList, String paramString)
  {
    Long localLong4;
    if (paramDataPhoto.album == null)
    {
      if (EsLog.isLoggable("EsPhotosData", 5))
        Log.w("EsPhotosData", "Cannot add photo that has no album; photo id: " + paramDataPhoto.id);
      localLong4 = null;
    }
    while (true)
    {
      return localLong4;
      DataAlbum localDataAlbum = paramDataPhoto.album;
      String str1 = getAlbumId(localDataAlbum);
      Long localLong1;
      if (paramMap != null)
      {
        localLong1 = (Long)paramMap.get(str1);
        if (localLong1 != null);
      }
      try
      {
        String[] arrayOfString7 = new String[1];
        arrayOfString7[0] = localDataAlbum.id;
        localLong1 = Long.valueOf(DatabaseUtils.longForQuery(paramSQLiteDatabase, "SELECT entity_version FROM album WHERE album_id = ?", arrayOfString7));
        if (paramMap != null)
          paramMap.put(str1, localLong1);
        localLong2 = localDataAlbum.entityVersion;
        if ((localLong1 == null) || ((localLong2 != null) && (!localLong1.equals(localLong2))))
          if (insertOrUpdateAlbumRow(paramSQLiteDatabase, localDataAlbum) == null)
          {
            if (EsLog.isLoggable("EsPhotosData", 5))
              Log.w("EsPhotosData", "Could not insert album row; album id: " + str1);
            localLong4 = null;
            continue;
            localLong1 = null;
          }
      }
      catch (SQLiteDoneException localSQLiteDoneException)
      {
        Long localLong2;
        while (true)
        {
          localLong1 = null;
          if (paramMap != null)
          {
            paramMap.put(str1, Long.valueOf(-1L));
            localLong1 = null;
          }
        }
        if (paramMap != null)
          paramMap.put(str1, localLong2);
        ContentValues localContentValues1 = new ContentValues();
        localContentValues1.put("photo_id", Long.valueOf(Long.parseLong(paramDataPhoto.id)));
        localContentValues1.put("plus_one_key", Integer.valueOf(0));
        localContentValues1.put("album_id", str1);
        localContentValues1.put("url", paramDataPhoto.original.url);
        localContentValues1.put("title", paramDataPhoto.title);
        if (!TextUtils.isEmpty(paramDataPhoto.caption))
          localContentValues1.put("description", paramDataPhoto.caption);
        Boolean localBoolean1 = paramDataPhoto.viewerCanComment;
        Boolean localBoolean2 = paramDataPhoto.viewerCanTag;
        Boolean localBoolean3 = Boolean.valueOf(false);
        Boolean localBoolean4 = Boolean.valueOf(false);
        int i;
        int k;
        label421: int n;
        label445: int i2;
        label469: int i7;
        label502: Long localLong3;
        if ((localBoolean1 != null) && (localBoolean1.booleanValue()))
        {
          i = 2;
          int j = i | 0x0;
          if ((localBoolean2 == null) || (!localBoolean2.booleanValue()))
            break label1030;
          k = 4;
          int m = j | k;
          if ((localBoolean3 == null) || (!localBoolean3.booleanValue()))
            break label1036;
          n = 8;
          int i1 = m | n;
          if ((localBoolean4 == null) || (!localBoolean4.booleanValue()))
            break label1042;
          i2 = 16;
          localContentValues1.put("action_state", Integer.valueOf(i2 | i1));
          if (paramDataPhoto.totalComments != null)
          {
            if (paramDataPhoto.comment != null)
              break label1048;
            i7 = 0;
            if (i7 == 0)
              break label1062;
            localContentValues1.put("comment_count", Integer.valueOf(i7));
            if ((EsLog.isLoggable("EsPhotosData", 5)) && (i7 != paramDataPhoto.totalComments.intValue()))
              Log.w("EsPhotosData", "WARN: comment mismatch; total: " + paramDataPhoto.totalComments + ", actual: " + i7);
          }
          label578: if (paramDataPhoto.owner != null)
            localContentValues1.put("owner_id", paramDataPhoto.owner.id);
          if ((paramDataPhoto.timestampSeconds == null) || (paramDataPhoto.timestampSeconds.doubleValue() <= 0.0D))
            break label1077;
          localLong3 = Long.valueOf(paramDataPhoto.timestampSeconds.longValue());
          label631: if (localLong3 != null)
            localContentValues1.put("timestamp", Long.valueOf(1000L * localLong3.longValue()));
          localContentValues1.put("entity_version", paramDataPhoto.entityVersion);
          byte[] arrayOfByte = getFingerPrint(paramDataPhoto);
          if (arrayOfByte != null)
            localContentValues1.put("fingerprint", arrayOfByte);
          if (paramDataPhoto.video != null)
            localContentValues1.put("video_data", DataVideoJson.getInstance().toString(paramDataPhoto.video));
          if ((paramDataPhoto.isPanorama != null) && (paramDataPhoto.isPanorama.booleanValue()))
            localContentValues1.put("is_panorama", Integer.valueOf(1));
          if (paramDataPhoto.uploadStatus == null)
            break label1125;
          localContentValues1.put("upload_status", paramDataPhoto.uploadStatus);
          label762: if (paramBoolean != null)
            localContentValues1.put("downloadable", paramBoolean);
          if (paramDataPhoto.original != null)
          {
            if (paramDataPhoto.original.width != null)
              localContentValues1.put("width", paramDataPhoto.original.width);
            if (paramDataPhoto.original.height != null)
              localContentValues1.put("height", paramDataPhoto.original.height);
          }
          localLong4 = getPhotoRowId(paramSQLiteDatabase, paramDataPhoto.id);
          if (localLong4 != null)
            break label1139;
          localLong4 = Long.valueOf(paramSQLiteDatabase.insertWithOnConflict("photo", null, localContentValues1, 4));
          if (localLong4.longValue() == -1L)
            localLong4 = null;
          label878: if (paramSet != null)
            paramSet.add(paramDataPhoto.owner);
          if (paramDataPhoto.plusOne == null)
            break label1175;
          insertOrUpdatePhotoPlusOneRow(paramSQLiteDatabase, paramDataPhoto.plusOne, paramDataPhoto.id);
        }
        HashMap localHashMap;
        Cursor localCursor;
        while (true)
        {
          localHashMap = new HashMap();
          String[] arrayOfString2 = { "comment_id", "update_time" };
          String[] arrayOfString3 = new String[1];
          arrayOfString3[0] = paramDataPhoto.id;
          localCursor = paramSQLiteDatabase.query("photo_comment", arrayOfString2, "photo_id=?", arrayOfString3, null, null, null, null);
          try
          {
            if (!localCursor.moveToNext())
              break label1186;
            localHashMap.put(localCursor.getString(0), Long.valueOf(localCursor.getLong(1)));
          }
          finally
          {
            localCursor.close();
          }
          break;
          label1030: k = 0;
          break label421;
          label1036: n = 0;
          break label445;
          label1042: i2 = 0;
          break label469;
          label1048: i7 = paramDataPhoto.comment.size();
          break label502;
          label1062: localContentValues1.put("comment_count", paramDataPhoto.totalComments);
          break label578;
          label1077: Double localDouble = paramDataPhoto.uploadTimestampSeconds;
          localLong3 = null;
          if (localDouble == null)
            break label631;
          boolean bool = paramDataPhoto.uploadTimestampSeconds.doubleValue() < 0.0D;
          localLong3 = null;
          if (!bool)
            break label631;
          localLong3 = Long.valueOf(paramDataPhoto.uploadTimestampSeconds.longValue());
          break label631;
          label1125: localContentValues1.put("upload_status", "ORIGINAL");
          break label762;
          label1139: String[] arrayOfString1 = new String[1];
          arrayOfString1[0] = paramDataPhoto.id;
          if (paramSQLiteDatabase.update("photo", localContentValues1, "photo_id=?", arrayOfString1) != 0)
            break label878;
          localLong4 = null;
          break label878;
          label1175: deletePhotoPlusOneRow(paramSQLiteDatabase, paramDataPhoto.id);
        }
        label1186: localCursor.close();
        List localList = paramDataPhoto.comment;
        int i3 = 0;
        if (localList != null)
        {
          Iterator localIterator2 = paramDataPhoto.comment.iterator();
          while (localIterator2.hasNext())
          {
            DataComment localDataComment = (DataComment)localIterator2.next();
            Long localLong5 = (Long)localHashMap.remove(localDataComment.id);
            ContentValues localContentValues3 = getCommentContentValues(localDataComment, paramDataPhoto.id);
            if ((localLong5 == null) || (!localLong5.equals(localDataComment.lastUpdateTimestamp)))
            {
              if (paramSet != null)
                paramSet.add(localDataComment.user);
              insertOrUpdatePhotoCommentRow$1e3cff3e(paramSQLiteDatabase, localDataComment.id, localContentValues3);
              i3 = 1;
            }
          }
        }
        if (localHashMap.size() > 0)
        {
          ArrayList localArrayList = new ArrayList(localHashMap.size());
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("comment_id IN(");
          Iterator localIterator1 = localHashMap.keySet().iterator();
          while (localIterator1.hasNext())
          {
            String str2 = (String)localIterator1.next();
            localStringBuilder.append("?,");
            localArrayList.add(str2);
          }
          localStringBuilder.setLength(-1 + localStringBuilder.length());
          localStringBuilder.append(")");
          paramSQLiteDatabase.delete("photo_comment", localStringBuilder.toString(), (String[])localArrayList.toArray(new String[0]));
        }
        if ((i3 != 0) || (localHashMap.size() > 0))
        {
          Uri localUri1 = Uri.withAppendedPath(EsProvider.PHOTO_COMMENTS_BY_PHOTO_ID_URI, paramDataPhoto.id);
          if (paramList != null)
            paramList.add(localUri1);
        }
        String[] arrayOfString4 = new String[1];
        arrayOfString4[0] = paramDataPhoto.id;
        paramSQLiteDatabase.delete("photo_shape", "photo_id=?", arrayOfString4);
        if (paramDataPhoto.shape != null)
        {
          int i4 = paramDataPhoto.shape.size();
          int i5 = 0;
          if (i5 < i4)
          {
            DataShape localDataShape = (DataShape)paramDataPhoto.shape.get(i5);
            DataUser localDataUser = localDataShape.user;
            int i6;
            label1625: ContentValues localContentValues2;
            if (((localDataUser != null) && (localDataUser.displayName != null) && (localDataUser.id != null) && (!"0".equals(localDataUser.id))) || (localDataShape.suggestion != null))
            {
              i6 = 1;
              if (i6 != 0)
              {
                if ((paramSet != null) && (localDataUser != null))
                  paramSet.add(localDataUser);
                localContentValues2 = getShapeContentValues(localDataShape, paramDataPhoto.id, paramString);
                if (localContentValues2 != null)
                {
                  String[] arrayOfString5 = new String[1];
                  arrayOfString5[0] = localDataShape.id;
                  if (DatabaseUtils.longForQuery(paramSQLiteDatabase, "SELECT count(*) FROM photo_shape WHERE shape_id=?", arrayOfString5) != 0L)
                    break label1727;
                  if (paramSQLiteDatabase.insertWithOnConflict("photo_shape", null, localContentValues2, 4) == -1L);
                }
              }
            }
            while (true)
            {
              i5++;
              break;
              i6 = 0;
              break label1625;
              label1727: String[] arrayOfString6 = new String[1];
              arrayOfString6[0] = localDataShape.id;
              if (paramSQLiteDatabase.update("photo_shape", localContentValues2, "shape_id=?", arrayOfString6) == 0);
            }
          }
          Uri localUri3 = Uri.withAppendedPath(EsProvider.PHOTO_SHAPES_BY_PHOTO_ID_URI, paramDataPhoto.id);
          if (paramList != null)
            paramList.add(localUri3);
        }
      }
      if (localLong4 != null)
      {
        Uri localUri2 = Uri.withAppendedPath(EsProvider.PHOTO_BY_PHOTO_ID_URI, paramDataPhoto.id);
        if (paramList != null)
          paramList.add(localUri2);
      }
    }
  }

  private static void insertPhotosInTransaction(SQLiteDatabase paramSQLiteDatabase, List<DataPhoto> paramList, Boolean paramBoolean, DataAlbum paramDataAlbum, Map<Long, Long> paramMap, String paramString, ContentValues paramContentValues, List<Uri> paramList1, EsSyncAdapterService.SyncState paramSyncState)
  {
    HashMap localHashMap = new HashMap();
    HashSet localHashSet = new HashSet();
    if (paramDataAlbum != null)
      localHashMap.put(paramDataAlbum.id, paramDataAlbum.entityVersion);
    int i;
    int j;
    label53: Long localLong1;
    Long localLong2;
    label136: Long localLong3;
    if (paramList != null)
    {
      i = paramList.size();
      j = 0;
      if (j >= i)
        break label307;
      DataPhoto localDataPhoto = (DataPhoto)paramList.get(j);
      if (EsLog.isLoggable("EsPhotosData", 3))
        EsLog.writeToLog(3, "EsPhotosData", getPhotoOutput(localDataPhoto, 0));
      if (paramSyncState != null)
        paramSyncState.incrementSubCount();
      localLong1 = Long.valueOf(Long.parseLong(localDataPhoto.id));
      if (paramMap == null)
        break label229;
      localLong2 = (Long)paramMap.remove(localLong1);
      if (localDataPhoto.entityVersion == null)
        break label235;
      localLong3 = localDataPhoto.entityVersion;
      label151: if ((localLong2 != null) && (localLong2.equals(localLong3)))
        break label241;
      if (insertPhotoInTransaction(paramSQLiteDatabase, localDataPhoto, paramBoolean, true, localHashMap, localHashSet, paramList1, null) != null)
        break label274;
      if (EsLog.isLoggable("EsPhotosData", 5))
        Log.w("EsPhotosData", "Could not insert row for photo of me; id: " + localLong1);
    }
    while (true)
    {
      j++;
      break label53;
      i = 0;
      break;
      label229: localLong2 = null;
      break label136;
      label235: localLong3 = null;
      break label151;
      label241: if (EsLog.isLoggable("EsPhotosData", 3))
        Log.d("EsPhotosData", "Photo not updated; id: " + localLong1);
      label274: if ((localLong2 == null) && (paramString != null))
      {
        paramContentValues.put("photo_id", localLong1);
        paramSQLiteDatabase.insert(paramString, null, paramContentValues);
      }
    }
    label307: ArrayList localArrayList = new ArrayList(localHashSet);
    EsPeopleData.replaceUsersInTransaction(paramSQLiteDatabase, localArrayList);
  }

  public static void insertStreamPhotos(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, String paramString1, String paramString2, List<DataPhoto> paramList, boolean paramBoolean)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    long l1 = System.currentTimeMillis();
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap = getCurrentStreamMap(localSQLiteDatabase, paramString1, paramString2);
    while (true)
    {
      StringBuilder localStringBuilder3;
      ArrayList localArrayList2;
      try
      {
        localSQLiteDatabase.beginTransaction();
        if (paramSyncState != null)
          paramSyncState.incrementCount();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("stream_id", paramString1);
        insertPhotosInTransaction(localSQLiteDatabase, paramList, null, null, localHashMap, "photos_in_stream", localContentValues, localArrayList1, paramSyncState);
        if ((paramBoolean) || (localHashMap.size() <= 0))
          break label326;
        localStringBuilder3 = new StringBuilder();
        localArrayList2 = new ArrayList(localHashMap.size());
        localStringBuilder3.append("stream_id=? AND photo_id IN(");
        localArrayList2.add(paramString1);
        Iterator localIterator2 = localHashMap.keySet().iterator();
        if (localIterator2.hasNext())
        {
          long l2 = ((Long)localIterator2.next()).longValue();
          localStringBuilder3.append("?,");
          localArrayList2.add(Long.toString(l2));
          continue;
        }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
        if (EsLog.isLoggable("EsPhotosData", 4))
        {
          StringBuilder localStringBuilder1 = new StringBuilder("[INSERT_STREAM_PHOTOS], stream: ").append(paramString1).append(", num photos: ");
          if (paramList == null)
            break label459;
          i = paramList.size();
          Log.i("EsPhotosData", i + ", duration: " + getDeltaTime(l1));
        }
      }
      localStringBuilder3.setLength(-1 + localStringBuilder3.length());
      localStringBuilder3.append(")");
      localSQLiteDatabase.delete("photos_in_stream", localStringBuilder3.toString(), (String[])localArrayList2.toArray(new String[0]));
      label326: localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      StringBuilder localStringBuilder2;
      if (EsLog.isLoggable("EsPhotosData", 4))
      {
        localStringBuilder2 = new StringBuilder("[INSERT_STREAM_PHOTOS], stream: ").append(paramString1).append(", num photos: ");
        if (paramList == null)
          break label453;
      }
      label453: for (int j = paramList.size(); ; j = 0)
      {
        Log.i("EsPhotosData", j + ", duration: " + getDeltaTime(l1));
        Iterator localIterator1 = localArrayList1.iterator();
        while (localIterator1.hasNext())
        {
          Uri localUri2 = (Uri)localIterator1.next();
          paramContext.getContentResolver().notifyChange(localUri2, null);
        }
      }
      label459: int i = 0;
    }
    Uri localUri1 = Uri.withAppendedPath(Uri.withAppendedPath(EsProvider.PHOTO_BY_STREAM_ID_AND_OWNER_ID_URI, paramString1), paramString2);
    paramContext.getContentResolver().notifyChange(localUri1, null);
    paramContext.getContentResolver().notifyChange(EsProvider.PHOTO_HOME_URI, null);
  }

  public static void insertUserPhotos(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, List<DataPhoto> paramList1, List<DataPhoto> paramList2, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    long l = System.currentTimeMillis();
    ArrayList localArrayList = new ArrayList();
    int i;
    if (paramList1 == null)
      i = 0;
    Uri localUri1;
    while (true)
    {
      int j;
      if (paramList2 == null)
        j = 0;
      try
      {
        localSQLiteDatabase.beginTransaction();
        localSQLiteDatabase.delete("photos_of_user", "photo_of_user_id=?", new String[] { paramString });
        if (EsLog.isLoggable("EsPhotosData", 3))
          Log.d("EsPhotosData", ">>>>> approved photos");
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("photo_of_user_id", paramString);
        insertPhotosInTransaction(localSQLiteDatabase, paramList1, null, null, null, "photos_of_user", localContentValues, localArrayList, paramSyncState);
        if (EsLog.isLoggable("EsPhotosData", 3))
          Log.d("EsPhotosData", ">>>>> unapproved photos");
        insertPhotosInTransaction(localSQLiteDatabase, paramList2, null, null, null, "photos_of_user", localContentValues, localArrayList, paramSyncState);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        if (EsLog.isLoggable("EsPhotosData", 4))
          Log.i("EsPhotosData", "[INSERT_USER_PHOTOS], userId: " + paramString + ", approved: " + i + ", unapproved: " + j + ", duration: " + getDeltaTime(l));
        Iterator localIterator = localArrayList.iterator();
        while (true)
          if (localIterator.hasNext())
          {
            Uri localUri2 = (Uri)localIterator.next();
            paramContext.getContentResolver().notifyChange(localUri2, null);
            continue;
            i = paramList1.size();
            break;
            j = paramList2.size();
          }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
        if (EsLog.isLoggable("EsPhotosData", 4))
          Log.i("EsPhotosData", "[INSERT_USER_PHOTOS], userId: " + paramString + ", approved: " + i + ", unapproved: " + j + ", duration: " + getDeltaTime(l));
      }
    }
    paramContext.getContentResolver().notifyChange(localUri1, null);
  }

  public static void setPhotosFromPostsAlbumName(String paramString)
  {
    sPhotosFromPostsAlbumName = paramString;
  }

  static boolean syncTopLevel(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState)
  {
    boolean bool;
    if (paramSyncState.isCanceled())
      bool = false;
    while (true)
    {
      return bool;
      if (EsLog.isLoggable("EsPhotosData", 3))
        Log.d("EsPhotosData", "    #syncTopLevel(); start");
      String str = paramEsAccount.getGaiaId();
      paramSyncState.onStart("Photos:TopLevel");
      bool = true;
      PhotosOfUserOperation localPhotosOfUserOperation = new PhotosOfUserOperation(paramContext, paramEsAccount, paramSyncState, str, false, null, null);
      localPhotosOfUserOperation.start();
      if (localPhotosOfUserOperation.hasError())
      {
        Log.w("EsPhotosData", "    #syncTopLevel(); failed user photo; code: " + localPhotosOfUserOperation.getErrorCode() + ", reason: " + localPhotosOfUserOperation.getReasonPhrase());
        bool = false;
      }
      UserPhotoAlbumsOperation localUserPhotoAlbumsOperation = new UserPhotoAlbumsOperation(paramContext, paramEsAccount, paramSyncState, str, null, null);
      localUserPhotoAlbumsOperation.start();
      if (localUserPhotoAlbumsOperation.hasError())
      {
        Log.w("EsPhotosData", "    #syncTopLevel(); failed photo albums; code: " + localUserPhotoAlbumsOperation.getErrorCode() + ", reason: " + localUserPhotoAlbumsOperation.getReasonPhrase());
        bool = false;
      }
      PhotosInAlbumOperation localPhotosInAlbumOperation1 = new PhotosInAlbumOperation(paramContext, paramEsAccount, paramSyncState, "camerasync", str, false, null, null);
      localPhotosInAlbumOperation1.start();
      if (localPhotosInAlbumOperation1.hasError())
      {
        Log.w("EsPhotosData", "    #syncTopLevel(); failed camera photos; code: " + localPhotosInAlbumOperation1.getErrorCode() + ", reason: " + localPhotosInAlbumOperation1.getReasonPhrase());
        bool = false;
      }
      PhotosInAlbumOperation localPhotosInAlbumOperation2 = new PhotosInAlbumOperation(paramContext, paramEsAccount, paramSyncState, "posts", str, false, null, null);
      localPhotosInAlbumOperation2.start();
      if (localPhotosInAlbumOperation2.hasError())
      {
        Log.w("EsPhotosData", "    #syncTopLevel(); failed post photos; code: " + localPhotosInAlbumOperation2.getErrorCode() + ", reason: " + localPhotosInAlbumOperation2.getReasonPhrase());
        bool = false;
      }
      if ((bool) && (EsLog.isLoggable("EsPhotosData", 3)))
        Log.d("EsPhotosData", "    #syncTopLevel(); completed");
      paramSyncState.onFinish();
    }
  }

  public static void updateInstantUploadCover(Context paramContext, EsAccount paramEsAccount, DataPhoto paramDataPhoto)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    long l1 = System.currentTimeMillis();
    try
    {
      localSQLiteDatabase.beginTransaction();
      ContentValues localContentValues = new ContentValues(6);
      localContentValues.put("type", "from_my_phone");
      localContentValues.put("sort_order", Integer.valueOf(30));
      localContentValues.putNull("photo_count");
      long l2 = getPhotosHomeRowId(localSQLiteDatabase, "from_my_phone");
      if (l2 != -1L)
        localSQLiteDatabase.update("photo_home", localContentValues, "type=?", new String[] { "from_my_phone" });
      while (true)
      {
        String[] arrayOfString = new String[1];
        arrayOfString[0] = Long.toString(l2);
        localSQLiteDatabase.delete("photo_home_cover", "photo_home_key=?", arrayOfString);
        if (paramDataPhoto != null)
        {
          localContentValues.clear();
          localContentValues.put("photo_home_key", Long.valueOf(l2));
          if (!TextUtils.isEmpty(paramDataPhoto.id))
            localContentValues.put("photo_id", paramDataPhoto.id);
          localContentValues.put("url", paramDataPhoto.original.url);
          localContentValues.put("width", paramDataPhoto.original.width);
          localContentValues.put("height", paramDataPhoto.original.height);
          localContentValues.put("size", paramDataPhoto.fileSize);
          localSQLiteDatabase.insertWithOnConflict("photo_home_cover", null, localContentValues, 4);
        }
        localSQLiteDatabase.setTransactionSuccessful();
        return;
        long l3 = localSQLiteDatabase.insertWithOnConflict("photo_home", null, localContentValues, 4);
        l2 = l3;
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
      if (EsLog.isLoggable("EsPhotosData", 4))
        Log.i("EsPhotosData", "[INSERT_COVER_INSTANT_UPLOAD], duration: " + getDeltaTime(l1));
    }
  }

  public static void updatePhotoComment(Context paramContext, EsAccount paramEsAccount, Comment paramComment)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    long l = System.currentTimeMillis();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramComment.commentId;
    try
    {
      String str1 = Long.toString(DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT photo_id FROM photo_comment WHERE comment_id = ?", arrayOfString));
      if (EsLog.isLoggable("EsPhotosData", 3))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        String str2 = localStringBuilder.toString();
        localStringBuilder.setLength(0);
        localStringBuilder.append(str2).append("COMMENT [id: ").append(paramComment.commentId).append(", content: ").append(paramComment.text);
        localStringBuilder.append("]");
        EsLog.writeToLog(3, "EsPhotosData", localStringBuilder.toString());
      }
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("photo_id", str1);
      localContentValues.put("comment_id", paramComment.commentId);
      localContentValues.put("author_id", paramComment.obfuscatedId);
      localContentValues.put("content", paramComment.text);
      if (paramComment.timestamp != null)
        localContentValues.put("create_time", paramComment.timestamp);
      if (paramComment.updatedTimestampUsec != null)
        localContentValues.put("update_time", paramComment.updatedTimestampUsec);
      if (paramComment.plusone != null)
        localContentValues.put("plusone_data", PLUS_ONE_JSON.toString(paramComment.plusone));
      insertOrUpdatePhotoCommentRow$1e3cff3e(localSQLiteDatabase, paramComment.commentId, localContentValues);
      if (EsLog.isLoggable("EsPhotosData", 4))
        Log.i("EsPhotosData", "[UPDATE_PHOTO_COMMENTS], photo ID: " + str1 + ", comment ID: " + paramComment.commentId + ", duration: " + getDeltaTime(l));
      Uri localUri = Uri.withAppendedPath(EsProvider.PHOTO_COMMENTS_BY_PHOTO_ID_URI, str1);
      paramContext.getContentResolver().notifyChange(localUri, null);
      paramContext.getContentResolver().notifyChange(EsProvider.PHOTO_URI, null);
      return;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        if (EsLog.isLoggable("EsPhotosData", 5))
          Log.w("EsPhotosData", "WARNING: could not find photo for the comment: " + paramComment.commentId);
    }
  }

  public static void updatePhotoCommentList(Context paramContext, EsAccount paramEsAccount, String paramString, List<DataComment> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    int i;
    if (paramList == null)
      i = 0;
    while (true)
    {
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      long l = System.currentTimeMillis();
      try
      {
        localSQLiteDatabase.beginTransaction();
        int j = 0;
        while (true)
          if (j < i)
          {
            DataComment localDataComment = (DataComment)paramList.get(j);
            ContentValues localContentValues = getCommentContentValues(localDataComment, paramString);
            if (EsLog.isLoggable("EsPhotosData", 3))
            {
              StringBuilder localStringBuilder = new StringBuilder();
              String str = localStringBuilder.toString();
              localStringBuilder.setLength(0);
              localStringBuilder.append(str).append("COMMENT [id: ").append(localDataComment.id).append(", content: ").append(localDataComment.text);
              localStringBuilder.append("]");
              EsLog.writeToLog(3, "EsPhotosData", localStringBuilder.toString());
            }
            if (insertOrUpdatePhotoCommentRow$1e3cff3e(localSQLiteDatabase, localDataComment.id, localContentValues))
              updateCommentCount(localSQLiteDatabase, paramString, 1);
            Uri localUri = Uri.withAppendedPath(EsProvider.PHOTO_COMMENTS_BY_PHOTO_ID_URI, paramString);
            paramContext.getContentResolver().notifyChange(localUri, null);
            paramContext.getContentResolver().notifyChange(EsProvider.PHOTO_URI, null);
            j++;
            continue;
            i = paramList.size();
            break;
          }
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        if (EsLog.isLoggable("EsPhotosData", 4))
          Log.i("EsPhotosData", "[INSERT_PHOTO_COMMENTS], photo ID: " + paramString + ", num comments: " + i + ", duration: " + getDeltaTime(l));
        int k = localArrayList.size();
        for (int m = 0; m < k; m++)
          paramContext.getContentResolver().notifyChange((Uri)localArrayList.get(m), null);
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
        if (EsLog.isLoggable("EsPhotosData", 4))
          Log.i("EsPhotosData", "[INSERT_PHOTO_COMMENTS], photo ID: " + paramString + ", num comments: " + i + ", duration: " + getDeltaTime(l));
      }
    }
  }

  public static boolean updatePhotoCommentPlusOne(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, DataPlusOne paramDataPlusOne, boolean paramBoolean)
  {
    boolean bool = true;
    if (EsLog.isLoggable("EsPhotosData", 3))
      Log.d("EsPhotosData", ">>>>> updatePhotoCommentPlusOneId photo id: " + paramString1 + ", commentId: " + paramString2 + ", plusOneId: " + paramDataPlusOne.id);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    DataPlusOne localDataPlusOne;
    if (paramBoolean)
    {
      localDataPlusOne = getCommentPlusOneData(localSQLiteDatabase, paramString2);
      if (localDataPlusOne == null);
      while (TextUtils.equals(localDataPlusOne.id, paramDataPlusOne.id))
        return bool;
      localDataPlusOne.id = paramDataPlusOne.id;
    }
    for (ContentValues localContentValues = getPhotoCommentPlusOneContentValues(localDataPlusOne); ; localContentValues = getPhotoCommentPlusOneContentValues(paramDataPlusOne))
    {
      bool = insertOrUpdatePhotoCommentRow$1e3cff3e(localSQLiteDatabase, paramString2, localContentValues);
      Uri localUri = Uri.withAppendedPath(EsProvider.PHOTO_COMMENTS_BY_PHOTO_ID_URI, paramString1);
      paramContext.getContentResolver().notifyChange(localUri, null);
      break;
    }
  }

  public static boolean updatePhotoCommentPlusOne(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean)
  {
    if (EsLog.isLoggable("EsPhotosData", 3))
      Log.d("EsPhotosData", ">>>>> updatePhotoCommentPlusOne photo id: " + paramString1 + ", commentId: " + paramString2 + " " + paramBoolean);
    return updatePhotoCommentPlusOne(paramContext, paramEsAccount, paramString1, paramString2, updatePlusOne(getCommentPlusOneData(EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase(), paramString2), paramBoolean), false);
  }

  public static void updatePhotoPlusOne$55b1eb27(Context paramContext, EsAccount paramEsAccount, String paramString, boolean paramBoolean)
  {
    StringBuilder localStringBuilder;
    String str;
    if (EsLog.isLoggable("EsPhotosData", 3))
    {
      localStringBuilder = new StringBuilder(">> updatePlusOne; photo id: ").append(paramString);
      if (!paramBoolean)
        break label200;
      str = "";
    }
    while (true)
    {
      Log.d("EsPhotosData", str + " +1'd");
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      Cursor localCursor = localSQLiteDatabase.query("photo_plusone", new String[] { "plusone_data" }, "photo_id=?", new String[] { paramString }, null, null, null);
      try
      {
        boolean bool1 = localCursor.moveToFirst();
        DataPlusOne localDataPlusOne1 = null;
        if (bool1)
        {
          boolean bool2 = localCursor.isNull(0);
          localDataPlusOne1 = null;
          if (!bool2)
            localDataPlusOne1 = (DataPlusOne)PLUS_ONE_JSON.fromByteArray(localCursor.getBlob(0));
        }
        DataPlusOne localDataPlusOne2 = updatePlusOne(localDataPlusOne1, paramBoolean);
        if (localDataPlusOne2 != null)
          insertOrUpdatePhotoPlusOneRow(localSQLiteDatabase, localDataPlusOne2, paramString);
        while (true)
        {
          Uri localUri = Uri.withAppendedPath(EsProvider.PHOTO_BY_PHOTO_ID_URI, paramString);
          paramContext.getContentResolver().notifyChange(localUri, null);
          return;
          label200: str = " (un)";
          break;
          deletePhotoPlusOneRow(localSQLiteDatabase, paramString);
        }
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  public static void updatePhotoPlusOne$95d6774(Context paramContext, EsAccount paramEsAccount, String paramString, DataPlusOne paramDataPlusOne)
  {
    if (EsLog.isLoggable("EsPhotosData", 3))
      Log.d("EsPhotosData", ">> updatePlusOne; photo id: " + paramString);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    if (paramDataPlusOne != null)
      insertOrUpdatePhotoPlusOneRow(localSQLiteDatabase, paramDataPlusOne, paramString);
    while (true)
    {
      Uri localUri = Uri.withAppendedPath(EsProvider.PHOTO_BY_PHOTO_ID_URI, paramString);
      paramContext.getContentResolver().notifyChange(localUri, null);
      return;
      deletePhotoPlusOneRow(localSQLiteDatabase, paramString);
    }
  }

  // ERROR //
  public static void updatePhotoShapeApproval(Context paramContext, EsAccount paramEsAccount, long paramLong1, long paramLong2, boolean paramBoolean)
  {
    // Byte code:
    //   0: ldc 85
    //   2: iconst_3
    //   3: invokestatic 91	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +37 -> 43
    //   9: ldc 85
    //   11: new 93	java/lang/StringBuilder
    //   14: dup
    //   15: ldc_w 1212
    //   18: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   21: lload_2
    //   22: invokevirtual 1215	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   25: ldc_w 1217
    //   28: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: iload 6
    //   33: invokevirtual 522	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   36: invokevirtual 109	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   39: invokestatic 180	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   42: pop
    //   43: aload_0
    //   44: aload_1
    //   45: invokestatic 36	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   48: invokevirtual 40	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   51: astore 7
    //   53: invokestatic 46	java/lang/System:currentTimeMillis	()J
    //   56: lstore 8
    //   58: aload 7
    //   60: invokevirtual 59	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   63: lconst_0
    //   64: lstore 12
    //   66: aload 7
    //   68: ldc_w 1219
    //   71: aconst_null
    //   72: invokestatic 54	android/database/DatabaseUtils:longForQuery	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;[Ljava/lang/String;)J
    //   75: lstore 31
    //   77: lload 31
    //   79: lstore 12
    //   81: lload 12
    //   83: lconst_0
    //   84: lcmp
    //   85: ifle +42 -> 127
    //   88: new 219	android/content/ContentValues
    //   91: dup
    //   92: invokespecial 220	android/content/ContentValues:<init>	()V
    //   95: astore 29
    //   97: aload 29
    //   99: ldc_w 1221
    //   102: lload 12
    //   104: lconst_1
    //   105: lsub
    //   106: invokestatic 253	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   109: invokevirtual 256	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   112: aload 7
    //   114: ldc_w 1127
    //   117: aload 29
    //   119: ldc_w 1223
    //   122: aconst_null
    //   123: invokevirtual 264	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   126: pop
    //   127: iload 6
    //   129: ifne +73 -> 202
    //   132: iconst_1
    //   133: anewarray 22	java/lang/String
    //   136: astore 22
    //   138: aload 22
    //   140: iconst_0
    //   141: lload 4
    //   143: invokestatic 73	java/lang/Long:toString	(J)Ljava/lang/String;
    //   146: aastore
    //   147: aload 7
    //   149: ldc_w 1225
    //   152: aload 22
    //   154: invokestatic 186	android/database/DatabaseUtils:stringForQuery	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;
    //   157: astore 28
    //   159: aload 28
    //   161: astore 25
    //   163: aload 25
    //   165: ifnull +37 -> 202
    //   168: iconst_2
    //   169: anewarray 22	java/lang/String
    //   172: astore 26
    //   174: aload 26
    //   176: iconst_0
    //   177: aload 25
    //   179: aastore
    //   180: aload 26
    //   182: iconst_1
    //   183: lload_2
    //   184: invokestatic 73	java/lang/Long:toString	(J)Ljava/lang/String;
    //   187: aastore
    //   188: aload 7
    //   190: ldc_w 1038
    //   193: ldc_w 1227
    //   196: aload 26
    //   198: invokevirtual 67	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   201: pop
    //   202: iconst_1
    //   203: anewarray 22	java/lang/String
    //   206: astore 16
    //   208: aload 16
    //   210: iconst_0
    //   211: lload 4
    //   213: invokestatic 73	java/lang/Long:toString	(J)Ljava/lang/String;
    //   216: aastore
    //   217: iload 6
    //   219: ifeq +214 -> 433
    //   222: new 219	android/content/ContentValues
    //   225: dup
    //   226: invokespecial 220	android/content/ContentValues:<init>	()V
    //   229: astore 17
    //   231: aload 17
    //   233: ldc_w 585
    //   236: ldc_w 1229
    //   239: invokevirtual 360	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   242: aload 7
    //   244: ldc_w 997
    //   247: aload 17
    //   249: ldc_w 1231
    //   252: aload 16
    //   254: invokevirtual 264	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   257: pop
    //   258: aload 7
    //   260: invokevirtual 80	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   263: aload 7
    //   265: invokevirtual 83	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   268: ldc 85
    //   270: iconst_4
    //   271: invokestatic 91	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   274: ifeq +30 -> 304
    //   277: ldc 85
    //   279: new 93	java/lang/StringBuilder
    //   282: dup
    //   283: ldc_w 1233
    //   286: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   289: lload 8
    //   291: invokestatic 102	com/google/android/apps/plus/content/EsPhotosDataApiary:getDeltaTime	(J)Ljava/lang/String;
    //   294: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: invokevirtual 109	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   300: invokestatic 115	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   303: pop
    //   304: getstatic 1009	com/google/android/apps/plus/content/EsProvider:PHOTO_SHAPES_BY_PHOTO_ID_URI	Landroid/net/Uri;
    //   307: lload_2
    //   308: invokestatic 127	android/content/ContentUris:withAppendedId	(Landroid/net/Uri;J)Landroid/net/Uri;
    //   311: astore 19
    //   313: aload_0
    //   314: invokevirtual 133	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   317: aload 19
    //   319: aconst_null
    //   320: invokevirtual 139	android/content/ContentResolver:notifyChange	(Landroid/net/Uri;Landroid/database/ContentObserver;)V
    //   323: aload_0
    //   324: invokevirtual 133	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   327: getstatic 1034	com/google/android/apps/plus/content/EsProvider:PHOTO_HOME_URI	Landroid/net/Uri;
    //   330: aconst_null
    //   331: invokevirtual 139	android/content/ContentResolver:notifyChange	(Landroid/net/Uri;Landroid/database/ContentObserver;)V
    //   334: aload_0
    //   335: invokevirtual 133	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   338: getstatic 142	com/google/android/apps/plus/content/EsProvider:PHOTO_URI	Landroid/net/Uri;
    //   341: aconst_null
    //   342: invokevirtual 139	android/content/ContentResolver:notifyChange	(Landroid/net/Uri;Landroid/database/ContentObserver;)V
    //   345: return
    //   346: astore 14
    //   348: ldc 85
    //   350: iconst_4
    //   351: invokestatic 91	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   354: ifeq -273 -> 81
    //   357: ldc 85
    //   359: ldc_w 1235
    //   362: aload 14
    //   364: invokestatic 1238	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   367: pop
    //   368: goto -287 -> 81
    //   371: astore 10
    //   373: aload 7
    //   375: invokevirtual 83	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   378: ldc 85
    //   380: iconst_4
    //   381: invokestatic 91	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   384: ifeq +30 -> 414
    //   387: ldc 85
    //   389: new 93	java/lang/StringBuilder
    //   392: dup
    //   393: ldc_w 1233
    //   396: invokespecial 99	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   399: lload 8
    //   401: invokestatic 102	com/google/android/apps/plus/content/EsPhotosDataApiary:getDeltaTime	(J)Ljava/lang/String;
    //   404: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   407: invokevirtual 109	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   410: invokestatic 115	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   413: pop
    //   414: aload 10
    //   416: athrow
    //   417: astore 23
    //   419: ldc 85
    //   421: aload 23
    //   423: invokestatic 1241	android/util/Log:w	(Ljava/lang/String;Ljava/lang/Throwable;)I
    //   426: pop
    //   427: aconst_null
    //   428: astore 25
    //   430: goto -267 -> 163
    //   433: aload 7
    //   435: ldc_w 997
    //   438: ldc_w 1231
    //   441: aload 16
    //   443: invokevirtual 67	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   446: pop
    //   447: goto -189 -> 258
    //
    // Exception table:
    //   from	to	target	type
    //   66	77	346	android/database/sqlite/SQLiteDoneException
    //   58	63	371	finally
    //   66	77	371	finally
    //   88	147	371	finally
    //   147	159	371	finally
    //   168	263	371	finally
    //   348	368	371	finally
    //   419	447	371	finally
    //   147	159	417	android/database/sqlite/SQLiteDoneException
  }

  public static void updatePhotosOfYouCover(Context paramContext, EsAccount paramEsAccount, DataPhoto paramDataPhoto)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    long l1 = System.currentTimeMillis();
    try
    {
      localSQLiteDatabase.beginTransaction();
      ContentValues localContentValues = new ContentValues(6);
      localContentValues.put("type", "photos_of_me");
      localContentValues.put("sort_order", Integer.valueOf(20));
      localContentValues.putNull("photo_count");
      long l2 = getPhotosHomeRowId(localSQLiteDatabase, "photos_of_me");
      if (l2 != -1L)
        localSQLiteDatabase.update("photo_home", localContentValues, "type=?", new String[] { "photos_of_me" });
      while (true)
      {
        String[] arrayOfString = new String[1];
        arrayOfString[0] = Long.toString(l2);
        localSQLiteDatabase.delete("photo_home_cover", "photo_home_key=?", arrayOfString);
        if (paramDataPhoto != null)
        {
          localContentValues.clear();
          localContentValues.put("photo_home_key", Long.valueOf(l2));
          if (!TextUtils.isEmpty(paramDataPhoto.id))
            localContentValues.put("photo_id", paramDataPhoto.id);
          localContentValues.put("url", paramDataPhoto.original.url);
          localContentValues.put("width", paramDataPhoto.original.width);
          localContentValues.put("height", paramDataPhoto.original.height);
          localContentValues.put("size", paramDataPhoto.fileSize);
          localSQLiteDatabase.insertWithOnConflict("photo_home_cover", null, localContentValues, 4);
        }
        localSQLiteDatabase.setTransactionSuccessful();
        return;
        long l3 = localSQLiteDatabase.insertWithOnConflict("photo_home", null, localContentValues, 4);
        l2 = l3;
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
      if (EsLog.isLoggable("EsPhotosData", 4))
        Log.i("EsPhotosData", "[INSERT_COVER_PHOTOS_OF_YOU], duration: " + getDeltaTime(l1));
    }
  }

  private static DataPlusOne updatePlusOne(DataPlusOne paramDataPlusOne, boolean paramBoolean)
  {
    DataPlusOne localDataPlusOne;
    if (paramDataPlusOne == null)
    {
      localDataPlusOne = null;
      if (paramBoolean)
      {
        localDataPlusOne = new DataPlusOne();
        localDataPlusOne.isPlusonedByViewer = Boolean.valueOf(true);
        localDataPlusOne.globalCount = Integer.valueOf(1);
      }
      return localDataPlusOne;
    }
    if (paramBoolean)
    {
      paramDataPlusOne.isPlusonedByViewer = Boolean.valueOf(true);
      paramDataPlusOne.globalCount = Integer.valueOf(1 + paramDataPlusOne.globalCount.intValue());
    }
    while (true)
    {
      paramDataPlusOne.id = null;
      localDataPlusOne = paramDataPlusOne;
      break;
      paramDataPlusOne.isPlusonedByViewer = Boolean.valueOf(false);
      if (paramDataPlusOne.globalCount.intValue() > 0)
        paramDataPlusOne.globalCount = Integer.valueOf(-1 + paramDataPlusOne.globalCount.intValue());
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsPhotosDataApiary
 * JD-Core Version:    0.6.2
 */