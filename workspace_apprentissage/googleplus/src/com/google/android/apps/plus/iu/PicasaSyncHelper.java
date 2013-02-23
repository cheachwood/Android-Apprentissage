package com.google.android.apps.plus.iu;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.content.SyncStats;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.util.EsLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

final class PicasaSyncHelper
{
  private static final String[] PHOTO_PROJECTION_ID_DATE = { "_id", "date_updated" };
  private static final String PHOTO_TABLE_NAME = PhotoEntry.SCHEMA.getTableName();
  private static final String[] PROJECTION_ID_ACCOUNT;
  private static final String USER_TABLE_NAME = UserEntry.SCHEMA.getTableName();
  private static PicasaSyncHelper sInstance;
  private Context mContext;
  private PicasaDatabaseHelper mDbHelper;
  private final HashMap<String, String> mEtagMap = new HashMap();

  static
  {
    PROJECTION_ID_ACCOUNT = new String[] { "_id", "account" };
  }

  private PicasaSyncHelper(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mDbHelper = PicasaDatabaseHelper.get(this.mContext);
  }

  public static PicasaSyncHelper getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new PicasaSyncHelper(paramContext);
      PicasaSyncHelper localPicasaSyncHelper = sInstance;
      return localPicasaSyncHelper;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final SyncContext createSyncContext(SyncResult paramSyncResult, Thread paramThread)
  {
    return new SyncContext(paramSyncResult, paramThread);
  }

  public final UserEntry findUser(String paramString)
  {
    Cursor localCursor = this.mDbHelper.getReadableDatabase().query(USER_TABLE_NAME, UserEntry.SCHEMA.getProjection(), "account=?", new String[] { paramString }, null, null, null);
    try
    {
      if (localCursor.moveToNext())
      {
        localUserEntry = (UserEntry)UserEntry.SCHEMA.cursorToObject(localCursor, new UserEntry());
        return localUserEntry;
      }
      UserEntry localUserEntry = null;
    }
    finally
    {
      localCursor.close();
    }
  }

  public final SQLiteDatabase getWritableDatabase()
  {
    return this.mDbHelper.getWritableDatabase();
  }

  public final void syncAccounts()
  {
    if (EsLog.isLoggable("iu.PicasaSync", 3))
      Log.d("iu.PicasaSync", "sync account database");
    SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
    HashMap localHashMap = new HashMap();
    Cursor localCursor = localSQLiteDatabase.query(USER_TABLE_NAME, PROJECTION_ID_ACCOUNT, null, null, null, null, null);
    try
    {
      if (localCursor.moveToNext())
      {
        String str2 = localCursor.getString(0);
        localHashMap.put(localCursor.getString(1), str2);
      }
    }
    finally
    {
      localCursor.close();
    }
    Account[] arrayOfAccount = AccountManager.get(this.mContext).getAccountsByType("com.google");
    if (EsLog.isLoggable("iu.PicasaSync", 3))
      Log.d("iu.PicasaSync", "accounts in DB=" + localHashMap.size());
    if (arrayOfAccount != null)
    {
      int i = arrayOfAccount.length;
      int j = 0;
      if (j < i)
      {
        Account localAccount = arrayOfAccount[j];
        int k;
        if (localHashMap.remove(localAccount.name) != null)
        {
          k = 1;
          label191: if (ContentResolver.getIsSyncable(localAccount, "com.google.android.apps.plus.iu.EsGoogleIuProvider") <= 0)
            break label287;
        }
        label287: for (int m = 1; ; m = 0)
        {
          if ((k == 0) && (m != 0))
          {
            if (EsLog.isLoggable("iu.PicasaSync", 3))
              Log.d("iu.PicasaSync", "add account to DB:" + Utils.maskDebugInfo(localAccount));
            UserEntry.SCHEMA.insertOrReplace(this.mDbHelper.getWritableDatabase(), new UserEntry(localAccount.name));
          }
          j++;
          break;
          k = 0;
          break label191;
        }
      }
    }
    if (!localHashMap.isEmpty())
    {
      Iterator localIterator = localHashMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (EsLog.isLoggable("iu.PicasaSync", 3))
          Log.d("iu.PicasaSync", "remove account:" + Utils.maskDebugInfo(localEntry.getKey()));
        String str1 = (String)localEntry.getValue();
        localSQLiteDatabase.beginTransaction();
        String[] arrayOfString = { str1 };
        try
        {
          localSQLiteDatabase.delete(PHOTO_TABLE_NAME, "user_id=?", arrayOfString);
          localSQLiteDatabase.delete(USER_TABLE_NAME, "_id=?", arrayOfString);
          localSQLiteDatabase.setTransactionSuccessful();
          localSQLiteDatabase.endTransaction();
        }
        finally
        {
          localSQLiteDatabase.endTransaction();
        }
      }
    }
  }

  public final void syncUploadedPhotos(final SyncContext paramSyncContext, UserEntry paramUserEntry)
  {
    int i = MetricsUtils.begin("PicasaSyncHelper.syncPhotosForUpload");
    String str1;
    final ArrayList localArrayList;
    SQLiteDatabase localSQLiteDatabase;
    Cursor localCursor;
    try
    {
      boolean bool = paramSyncContext.syncInterrupted();
      if (bool)
        return;
      str1 = paramUserEntry.account;
      localArrayList = new ArrayList();
      localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
      localSQLiteDatabase.beginTransaction();
      try
      {
        String str2 = PHOTO_TABLE_NAME;
        String[] arrayOfString1 = PHOTO_PROJECTION_ID_DATE;
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = String.valueOf(paramUserEntry.id);
        localCursor = localSQLiteDatabase.query(str2, arrayOfString1, "user_id=?", arrayOfString2, null, null, null);
        if (localCursor == null)
          break label182;
      }
      finally
      {
        try
        {
          if (!localCursor.moveToNext())
            break label175;
          localArrayList.add(new EntryMetadata(localCursor.getLong(0), localCursor.getLong(1)));
        }
        finally
        {
          localCursor.close();
        }
        localSQLiteDatabase.endTransaction();
      }
    }
    finally
    {
      MetricsUtils.end(i);
    }
    label175: localCursor.close();
    label182: localSQLiteDatabase.endTransaction();
    PicasaApi.EntryHandler local1 = new PicasaApi.EntryHandler()
    {
      private PicasaSyncHelper.EntryMetadata mKey = new PicasaSyncHelper.EntryMetadata();

      public final void handleEntry(ContentValues paramAnonymousContentValues)
      {
        PicasaSyncHelper.EntryMetadata localEntryMetadata1 = this.mKey.updateId(paramAnonymousContentValues.getAsLong("_id").longValue());
        int i = Collections.binarySearch(localArrayList, localEntryMetadata1);
        if (i >= 0);
        for (PicasaSyncHelper.EntryMetadata localEntryMetadata2 = (PicasaSyncHelper.EntryMetadata)localArrayList.get(i); ; localEntryMetadata2 = null)
        {
          Long localLong = paramAnonymousContentValues.getAsLong("date_updated");
          if ((localEntryMetadata2 == null) || (localLong == null) || (localEntryMetadata2.dateEdited < localLong.longValue()))
          {
            paramAnonymousContentValues.put("user_id", Long.valueOf(this.val$userId));
            paramSyncContext.replace(PicasaSyncHelper.PHOTO_TABLE_NAME, "_id", paramAnonymousContentValues);
            SyncStats localSyncStats = this.val$context.result.stats;
            localSyncStats.numUpdates = (1L + localSyncStats.numUpdates);
          }
          if (localEntryMetadata2 != null)
            localEntryMetadata2.survived = true;
          return;
        }
      }
    };
    String[] arrayOfString3 = { (String)this.mEtagMap.get(str1) };
    int j = 0;
    int k = 0;
    while (k <= 1)
    {
      j = paramSyncContext.api.getUploadedPhotos(str1, arrayOfString3, local1);
      this.mEtagMap.put(str1, arrayOfString3[0]);
      if (j == 2)
      {
        paramSyncContext.refreshAuthToken();
        k++;
        continue;
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          EntryMetadata localEntryMetadata = (EntryMetadata)localIterator.next();
          if (!localEntryMetadata.survived)
          {
            PhotoEntry.SCHEMA.deleteWithId(localSQLiteDatabase, localEntryMetadata.id);
            SyncStats localSyncStats3 = paramSyncContext.result.stats;
            localSyncStats3.numDeletes = (1L + localSyncStats3.numDeletes);
            continue;
            SyncStats localSyncStats2 = paramSyncContext.result.stats;
            localSyncStats2.numAuthExceptions = (1L + localSyncStats2.numAuthExceptions);
          }
        }
        while (true)
        {
          MetricsUtils.end(i);
          break;
          SyncStats localSyncStats1 = paramSyncContext.result.stats;
          localSyncStats1.numParseExceptions = (1L + localSyncStats1.numParseExceptions);
        }
      }
    }
    switch (j)
    {
    default:
    case 2:
    case 1:
    case 3:
    }
  }

  private static final class EntryMetadata
    implements Comparable<EntryMetadata>
  {
    public long dateEdited;
    public long id;
    public boolean survived = false;

    public EntryMetadata()
    {
    }

    public EntryMetadata(long paramLong1, long paramLong2)
    {
      this.id = paramLong1;
      this.dateEdited = paramLong2;
    }

    public final EntryMetadata updateId(long paramLong)
    {
      this.id = paramLong;
      return this;
    }
  }

  public final class SyncContext
  {
    public PicasaApi api;
    private Account mAccount;
    private String mAuthToken;
    private volatile boolean mStopSync;
    private Thread mThread;
    public SyncResult result;

    public SyncContext(SyncResult paramThread, Thread arg3)
    {
      this.result = ((SyncResult)Utils.checkNotNull(paramThread));
      this.api = new PicasaApi(PicasaSyncHelper.this.mContext.getContentResolver());
      Object localObject;
      this.mThread = localObject;
    }

    public final void refreshAuthToken()
    {
      AccountManager localAccountManager = AccountManager.get(PicasaSyncHelper.this.mContext);
      if (this.mAuthToken != null)
        localAccountManager.invalidateAuthToken("com.google", this.mAuthToken);
      this.mAuthToken = null;
      try
      {
        this.mAuthToken = localAccountManager.blockingGetAuthToken(this.mAccount, "lh2", true);
        this.api.setAuthToken(this.mAuthToken);
        if (this.mAuthToken == null)
        {
          if (EsLog.isLoggable("iu.PicasaSync", 5))
            Log.w("iu.PicasaSync", "cannot get auth token: " + Utils.maskDebugInfo(this.mAccount.name));
          SyncStats localSyncStats = this.result.stats;
          localSyncStats.numAuthExceptions = (1L + localSyncStats.numAuthExceptions);
        }
        return;
      }
      catch (Exception localException)
      {
        while (true)
          if (EsLog.isLoggable("iu.PicasaSync", 5))
            Log.w("iu.PicasaSync", "getAuthToken fail", localException);
      }
    }

    public final boolean setAccount(String paramString)
    {
      if ((this.mAccount == null) || (!this.mAccount.name.equals(paramString)))
      {
        this.mAccount = new Account(paramString, "com.google");
        this.mAuthToken = null;
        refreshAuthToken();
      }
      if (this.mAuthToken != null);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final void stopSync()
    {
      this.mStopSync = true;
      if (this.mThread != null)
        this.mThread.interrupt();
    }

    public final boolean syncInterrupted()
    {
      return this.mStopSync;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.PicasaSyncHelper
 * JD-Core Version:    0.6.2
 */