package com.google.android.apps.plus.content;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.RingtoneUtils;

public final class EsDatabaseHelper extends SQLiteOpenHelper
{
  private static final String[] MASTER_COLUMNS = { "name" };
  private static boolean sAlarmsInitialized;
  private static SparseArray<EsDatabaseHelper> sHelpers = new SparseArray();
  private static long sLastDatabaseDeletionTimestamp;
  private final Context mContext;
  private boolean mDeleted;
  private int mIndex;

  private EsDatabaseHelper(Context paramContext, int paramInt)
  {
    super(paramContext, "es" + paramInt + ".db", null, 1221);
    this.mContext = paramContext;
    this.mIndex = paramInt;
  }

  // ERROR //
  private void doDeleteDatabase()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 70	com/google/android/apps/plus/content/EsDatabaseHelper:mDeleted	Z
    //   6: istore_2
    //   7: iload_2
    //   8: ifeq +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: invokevirtual 74	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   18: astore_3
    //   19: iconst_0
    //   20: istore 4
    //   22: iload 4
    //   24: iconst_3
    //   25: if_icmpge +26 -> 51
    //   28: aload_3
    //   29: invokevirtual 79	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   32: aload_0
    //   33: iconst_1
    //   34: putfield 70	com/google/android/apps/plus/content/EsDatabaseHelper:mDeleted	Z
    //   37: invokestatic 85	java/lang/System:currentTimeMillis	()J
    //   40: putstatic 87	com/google/android/apps/plus/content/EsDatabaseHelper:sLastDatabaseDeletionTimestamp	J
    //   43: aload_3
    //   44: invokevirtual 90	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   47: aload_3
    //   48: invokevirtual 93	android/database/sqlite/SQLiteDatabase:close	()V
    //   51: new 95	java/io/File
    //   54: dup
    //   55: aload_3
    //   56: invokevirtual 98	android/database/sqlite/SQLiteDatabase:getPath	()Ljava/lang/String;
    //   59: invokespecial 99	java/io/File:<init>	(Ljava/lang/String;)V
    //   62: invokevirtual 103	java/io/File:delete	()Z
    //   65: pop
    //   66: goto -55 -> 11
    //   69: astore_1
    //   70: aload_0
    //   71: monitorexit
    //   72: aload_1
    //   73: athrow
    //   74: astore 6
    //   76: ldc 105
    //   78: ldc 107
    //   80: aload 6
    //   82: invokestatic 113	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   85: pop
    //   86: iinc 4 1
    //   89: goto -67 -> 22
    //
    // Exception table:
    //   from	to	target	type
    //   2	7	69	finally
    //   14	19	69	finally
    //   28	51	69	finally
    //   51	66	69	finally
    //   76	86	69	finally
    //   28	51	74	java/lang/Throwable
  }

  private static void dropAllViews(SQLiteDatabase paramSQLiteDatabase)
  {
    Cursor localCursor = paramSQLiteDatabase.query("sqlite_master", MASTER_COLUMNS, "type='view'", null, null, null, null);
    if (localCursor != null)
      try
      {
        if (localCursor.moveToNext())
        {
          String str = localCursor.getString(0);
          paramSQLiteDatabase.execSQL("DROP VIEW IF EXISTS " + str);
        }
      }
      finally
      {
        localCursor.close();
      }
  }

  public static EsDatabaseHelper getDatabaseHelper(Context paramContext, int paramInt)
  {
    if (paramContext == null)
      try
      {
        throw new NullPointerException("Context is null");
      }
      finally
      {
      }
    if (paramInt < 0)
      throw new IllegalArgumentException("Invalid account index: " + paramInt);
    EsDatabaseHelper localEsDatabaseHelper = (EsDatabaseHelper)sHelpers.get(paramInt);
    if (localEsDatabaseHelper == null)
    {
      localEsDatabaseHelper = new EsDatabaseHelper(paramContext, paramInt);
      sHelpers.put(paramInt, localEsDatabaseHelper);
    }
    if (!sAlarmsInitialized)
    {
      EsService.scheduleUnconditionalSyncAlarm(paramContext);
      EsService.scheduleSyncAlarm(paramContext);
      sAlarmsInitialized = true;
    }
    return localEsDatabaseHelper;
  }

  public static EsDatabaseHelper getDatabaseHelper(Context paramContext, EsAccount paramEsAccount)
  {
    return getDatabaseHelper(paramContext, paramEsAccount.getIndex());
  }

  static long getRowsCount(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String[] paramArrayOfString)
  {
    Cursor localCursor = paramSQLiteDatabase.query(paramString1, new String[] { "COUNT(*)" }, null, null, null, null, null);
    try
    {
      if (localCursor.moveToFirst())
      {
        long l2 = localCursor.getLong(0);
        l1 = l2;
        return l1;
      }
      long l1 = 0L;
    }
    finally
    {
      localCursor.close();
    }
  }

  public static boolean isDatabaseRecentlyDeleted()
  {
    boolean bool1 = sLastDatabaseDeletionTimestamp < 0L;
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      boolean bool3 = System.currentTimeMillis() - sLastDatabaseDeletionTimestamp < 60000L;
      bool2 = false;
      if (bool3)
        bool2 = true;
    }
  }

  private void rebuildTables(SQLiteDatabase paramSQLiteDatabase)
  {
    rebuildTables(paramSQLiteDatabase, EsAccountsData.getActiveAccount(this.mContext));
  }

  private static void upgradeViews(SQLiteDatabase paramSQLiteDatabase)
  {
    if (EsLog.isLoggable("EsDatabaseHelper", 3))
      Log.d("EsDatabaseHelper", "Upgrade database views");
    String[] arrayOfString1 = EsProvider.getViewNames();
    for (int i = 0; i < arrayOfString1.length; i++)
      paramSQLiteDatabase.execSQL("DROP VIEW IF EXISTS " + arrayOfString1[i]);
    String[] arrayOfString2 = EsProvider.getViewSQLs();
    for (int j = 0; j < arrayOfString2.length; j++)
      paramSQLiteDatabase.execSQL(arrayOfString2[j]);
  }

  public final void createNewDatabase()
  {
    this.mDeleted = false;
  }

  public final void deleteDatabase()
  {
    new AsyncTask()
    {
    }
    .execute(new Void[] { null });
  }

  public final SQLiteDatabase getReadableDatabase()
  {
    try
    {
      if (this.mDeleted)
        throw new SQLiteException("Database deleted");
    }
    finally
    {
    }
    SQLiteDatabase localSQLiteDatabase = super.getReadableDatabase();
    return localSQLiteDatabase;
  }

  public final SQLiteDatabase getWritableDatabase()
  {
    try
    {
      if (this.mDeleted)
        throw new SQLiteException("Database deleted");
    }
    finally
    {
    }
    SQLiteDatabase localSQLiteDatabase = super.getWritableDatabase();
    return localSQLiteDatabase;
  }

  public final void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    String[] arrayOfString1 = EsProvider.getTableSQLs();
    for (int i = 0; i < arrayOfString1.length; i++)
      paramSQLiteDatabase.execSQL(arrayOfString1[i]);
    String[] arrayOfString2 = EsProvider.getIndexSQLs();
    for (int j = 0; j < arrayOfString2.length; j++)
      paramSQLiteDatabase.execSQL(arrayOfString2[j]);
    String[] arrayOfString3 = EsProvider.getViewSQLs();
    for (int k = 0; k < arrayOfString3.length; k++)
      paramSQLiteDatabase.execSQL(arrayOfString3[k]);
    EsProvider.insertVirtualCircles(this.mContext, paramSQLiteDatabase);
    RingtoneUtils.registerHangoutRingtoneIfNecessary(this.mContext);
  }

  public final void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    rebuildTables(paramSQLiteDatabase);
  }

  public final void onOpen(SQLiteDatabase paramSQLiteDatabase)
  {
    if (!paramSQLiteDatabase.isReadOnly())
      paramSQLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
  }

  public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    if (EsLog.isLoggable("EsDatabaseHelper", 3))
      Log.d("EsDatabaseHelper", "Upgrade database: " + paramInt1 + " --> " + paramInt2);
    if (paramInt2 < paramInt1);
    try
    {
      rebuildTables(paramSQLiteDatabase);
      while (true)
      {
        EsAccount localEsAccount5;
        return;
        if (paramInt1 < 756)
        {
          rebuildTables(paramSQLiteDatabase);
          EsAccountsData.onAccountUpgradeRequired(this.mContext, this.mIndex);
          EsAccount localEsAccount4 = EsAccountsData.getActiveAccountUnsafe(this.mContext);
          if (localEsAccount4 != null)
            ContentResolver.requestSync(AccountsUtil.newAccount(localEsAccount4.getName()), "com.google.android.apps.plus.content.EsProvider", new Bundle());
        }
        else
        {
          if (paramInt1 < 911)
          {
            RingtoneUtils.registerHangoutRingtoneIfNecessary(this.mContext);
            paramInt1 = 911;
          }
          rebuildTables(paramSQLiteDatabase);
          upgradeViews(paramSQLiteDatabase);
          EsAccount localEsAccount3 = EsAccountsData.getActiveAccountUnsafe(this.mContext);
          if (localEsAccount3 != null)
            ContentResolver.requestSync(AccountsUtil.newAccount(localEsAccount3.getName()), "com.google.android.apps.plus.content.EsProvider", new Bundle());
        }
      }
    }
    catch (SQLiteException localSQLiteException)
    {
      while (true)
      {
        if (EsLog.isLoggable("EsDatabaseHelper", 6))
          Log.e("EsDatabaseHelper", "Failed to upgrade database: " + paramInt1 + " --> " + paramInt2, localSQLiteException);
        rebuildTables(paramSQLiteDatabase);
        EsAccount localEsAccount2 = EsAccountsData.getActiveAccountUnsafe(this.mContext);
        if (localEsAccount2 != null)
          ContentResolver.requestSync(AccountsUtil.newAccount(localEsAccount2.getName()), "com.google.android.apps.plus.content.EsProvider", new Bundle());
      }
    }
    finally
    {
      EsAccount localEsAccount1 = EsAccountsData.getActiveAccountUnsafe(this.mContext);
      if (localEsAccount1 != null)
        ContentResolver.requestSync(AccountsUtil.newAccount(localEsAccount1.getName()), "com.google.android.apps.plus.content.EsProvider", new Bundle());
    }
  }

  public final void rebuildTables(SQLiteDatabase paramSQLiteDatabase, EsAccount paramEsAccount)
  {
    Cursor localCursor = paramSQLiteDatabase.query("sqlite_master", MASTER_COLUMNS, "type='table'", null, null, null, null);
    if (localCursor != null)
      try
      {
        while (localCursor.moveToNext())
        {
          String str2 = localCursor.getString(0);
          if ((!str2.startsWith("android_")) && (!str2.startsWith("sqlite_")))
            paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + str2);
        }
      }
      finally
      {
        localCursor.close();
      }
    dropAllViews(paramSQLiteDatabase);
    onCreate(paramSQLiteDatabase);
    if (paramEsAccount != null)
    {
      String str1 = paramEsAccount.getGaiaId();
      paramSQLiteDatabase.execSQL("UPDATE account_status SET user_id='" + str1 + "' WHERE " + "user_id IS NULL");
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsDatabaseHelper
 * JD-Core Version:    0.6.2
 */