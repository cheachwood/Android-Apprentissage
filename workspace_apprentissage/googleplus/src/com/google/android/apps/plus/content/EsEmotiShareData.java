package com.google.android.apps.plus.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.text.TextUtils;
import com.google.android.apps.plus.R.array;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.Property;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class EsEmotiShareData
{
  public static final String[] EMOTISHARE_PROJECTION = { "_id", "type", "data", "generation" };
  private static final Object sEmotiShareSyncLock = new Object();

  public static void cleanupData$3105fef4(SQLiteDatabase paramSQLiteDatabase)
  {
    int i = paramSQLiteDatabase.delete("emotishare_data", null, null);
    EsLog.writeToLog(3, "EsEmotiShareData", "cleanupData deleted EmotiShares: " + i);
  }

  private static boolean doSync$1ef5a3b5(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState)
  {
    if (paramSyncState.isCanceled());
    for (boolean bool = false; ; bool = true)
    {
      return bool;
      paramSyncState.onStart("EmotiShare");
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      localSQLiteDatabase.beginTransaction();
      ArrayList localArrayList1;
      try
      {
        cleanupData$3105fef4(localSQLiteDatabase);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        Resources localResources = paramContext.getResources();
        int[] arrayOfInt1 = localResources.getIntArray(R.array.emotishare_id);
        int[] arrayOfInt2 = localResources.getIntArray(R.array.emotishare_release_generation);
        String[] arrayOfString1 = localResources.getStringArray(R.array.emotishare_name);
        String[] arrayOfString2 = localResources.getStringArray(R.array.emotishare_type);
        String[] arrayOfString3 = localResources.getStringArray(R.array.emotishare_category);
        String[] arrayOfString4 = localResources.getStringArray(R.array.emotishare_share_text);
        String[] arrayOfString5 = localResources.getStringArray(R.array.emotishare_description);
        String[] arrayOfString6 = localResources.getStringArray(R.array.emotishare_icon_uri);
        String[] arrayOfString7 = localResources.getStringArray(R.array.emotishare_image_uri);
        long l = System.currentTimeMillis();
        long[] arrayOfLong = new long[3];
        arrayOfLong[0] = Long.valueOf(Property.EMOTISHARE_GEN1_DATE.get()).longValue();
        arrayOfLong[1] = Long.valueOf(Property.EMOTISHARE_GEN2_DATE.get()).longValue();
        arrayOfLong[2] = Long.valueOf(Property.EMOTISHARE_GEN3_DATE.get()).longValue();
        localArrayList1 = new ArrayList();
        int i = 0;
        if (i < arrayOfInt1.length)
        {
          ArrayList localArrayList2 = new ArrayList();
          localArrayList2.add(arrayOfString3[i]);
          DbEmbedEmotishare localDbEmbedEmotishare = new DbEmbedEmotishare(arrayOfString2[i], arrayOfString1[i], arrayOfString7[i], arrayOfString5[i]);
          DbEmotishareMetadata localDbEmotishareMetadata = new DbEmotishareMetadata(arrayOfInt1[i], localArrayList2, arrayOfString4[i], arrayOfString6[i], localDbEmbedEmotishare, arrayOfInt2[i]);
          int j = -1 + arrayOfInt2[i];
          if ((j >= 0) && (j < arrayOfLong.length) && (l >= arrayOfLong[j]))
            localArrayList1.add(localDbEmotishareMetadata);
          i++;
        }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
    }
  }

  public static boolean ensureSynced(Context paramContext, EsAccount paramEsAccount)
  {
    EsSyncAdapterService.SyncState localSyncState = new EsSyncAdapterService.SyncState();
    localSyncState.onSyncStart("Exp sync");
    boolean bool = syncAll(paramContext, paramEsAccount, localSyncState, null, false);
    localSyncState.onSyncFinish();
    return bool;
  }

  private static int insertEmotiShares(Context paramContext, EsAccount paramEsAccount, List<DbEmotishareMetadata> paramList)
  {
    int i = 0;
    long l = System.currentTimeMillis();
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        DbEmotishareMetadata localDbEmotishareMetadata = (DbEmotishareMetadata)localIterator.next();
        ContentValues localContentValues = toContentValues(localDbEmotishareMetadata);
        if (localContentValues != null)
        {
          localSQLiteDatabase.insert("emotishare_data", null, localContentValues);
          i++;
        }
        EsLog.writeToLog(3, "EsEmotiShareData", "Insert: " + localDbEmotishareMetadata);
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
    localSQLiteDatabase.setTransactionSuccessful();
    localSQLiteDatabase.endTransaction();
    saveSyncTimestamp(paramContext, paramEsAccount, l);
    if (i != 0)
      paramContext.getContentResolver().notifyChange(EsProvider.EMOTISHARE_URI, null);
    return i;
  }

  private static long querySyncTimestamp(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
    try
    {
      long l2 = DatabaseUtils.longForQuery(localSQLiteDatabase, "SELECT last_emotishare_sync_time  FROM account_status", null);
      l1 = l2;
      return l1;
    }
    catch (SQLiteDoneException localSQLiteDoneException)
    {
      while (true)
        long l1 = -1L;
    }
  }

  private static void saveSyncTimestamp(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("last_emotishare_sync_time", Long.valueOf(paramLong));
    localSQLiteDatabase.update("account_status", localContentValues, null, null);
    paramContext.getContentResolver().notifyChange(EsProvider.ACCOUNT_STATUS_URI, null);
  }

  public static boolean syncAll(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, HttpOperation.OperationListener paramOperationListener, boolean paramBoolean)
  {
    Object localObject1 = sEmotiShareSyncLock;
    if (!paramBoolean);
    boolean bool;
    try
    {
      long l1 = querySyncTimestamp(paramContext, paramEsAccount);
      long l2 = System.currentTimeMillis() - l1;
      if ((l2 > 0L) && (l2 < 60000L))
      {
        bool = true;
      }
      else
      {
        bool = doSync$1ef5a3b5(paramContext, paramEsAccount, paramSyncState);
        if (bool)
          saveSyncTimestamp(paramContext, paramEsAccount, System.currentTimeMillis());
      }
    }
    finally
    {
      localObject2 = finally;
      throw localObject2;
    }
    return bool;
  }

  private static ContentValues toContentValues(DbEmotishareMetadata paramDbEmotishareMetadata)
  {
    ContentValues localContentValues = null;
    if (paramDbEmotishareMetadata == null);
    while (true)
    {
      return localContentValues;
      DbEmbedEmotishare localDbEmbedEmotishare = paramDbEmotishareMetadata.getEmbed();
      localContentValues = null;
      if (localDbEmbedEmotishare != null)
      {
        String str = localDbEmbedEmotishare.getType();
        boolean bool = TextUtils.isEmpty(str);
        localContentValues = null;
        if (!bool)
          try
          {
            byte[] arrayOfByte = DbEmotishareMetadata.serialize(paramDbEmotishareMetadata);
            localContentValues = null;
            if (arrayOfByte != null)
            {
              localContentValues = new ContentValues();
              localContentValues.put("type", str);
              localContentValues.put("data", arrayOfByte);
              localContentValues.put("generation", Integer.valueOf(paramDbEmotishareMetadata.getGeneration()));
            }
          }
          catch (IOException localIOException)
          {
            localContentValues = null;
          }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsEmotiShareData
 * JD-Core Version:    0.6.2
 */