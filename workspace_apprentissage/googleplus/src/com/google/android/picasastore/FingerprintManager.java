package com.google.android.picasastore;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Fingerprint;
import com.android.gallery3d.common.Utils;

final class FingerprintManager
{
  private static final String FINGERPRINT_TABLE = FingerprintEntry.SCHEMA.getTableName();
  private static final String[] PROJECTION_FINGERPRINT = { "fingerprint" };
  private static FingerprintManager sInstance;
  private final Context mContext;
  private final FingerprintDatabaseHelper mDbHelper;

  private FingerprintManager(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mDbHelper = new FingerprintDatabaseHelper(this.mContext);
  }

  public static FingerprintManager get(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new FingerprintManager(paramContext);
      FingerprintManager localFingerprintManager = sInstance;
      return localFingerprintManager;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final Fingerprint getCachedFingerprint(String paramString)
  {
    Cursor localCursor = this.mDbHelper.getReadableDatabase().query(FINGERPRINT_TABLE, PROJECTION_FINGERPRINT, "content_uri=?", new String[] { paramString }, null, null, null);
    if (localCursor != null);
    try
    {
      Fingerprint localFingerprint2;
      if ((localCursor.moveToNext()) && (!localCursor.isNull(0)))
      {
        localFingerprint2 = new Fingerprint(localCursor.getBlob(0));
        Utils.closeSilently(localCursor);
      }
      for (localFingerprint1 = localFingerprint2; ; localFingerprint1 = null)
      {
        return localFingerprint1;
        Utils.closeSilently(localCursor);
      }
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        Log.w("FingerprintManager", "cannot get fingerprint from cache for: " + paramString, localThrowable);
        Utils.closeSilently(localCursor);
        Fingerprint localFingerprint1 = null;
      }
    }
    finally
    {
      Utils.closeSilently(localCursor);
    }
  }

  // ERROR //
  public final Fingerprint getFingerprint(String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokevirtual 126	com/google/android/picasastore/FingerprintManager:getCachedFingerprint	(Ljava/lang/String;)Lcom/android/gallery3d/common/Fingerprint;
    //   7: astore 4
    //   9: aload 4
    //   11: astore 5
    //   13: iload_2
    //   14: ifne +13 -> 27
    //   17: aload 5
    //   19: ifnull +8 -> 27
    //   22: aload_0
    //   23: monitorexit
    //   24: aload 5
    //   26: areturn
    //   27: aload_1
    //   28: invokestatic 132	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
    //   31: astore 8
    //   33: aload_0
    //   34: getfield 48	com/google/android/picasastore/FingerprintManager:mContext	Landroid/content/Context;
    //   37: invokevirtual 136	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   40: aload 8
    //   42: invokevirtual 142	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   45: aconst_null
    //   46: invokestatic 146	com/android/gallery3d/common/Fingerprint:fromInputStream	(Ljava/io/InputStream;[J)Lcom/android/gallery3d/common/Fingerprint;
    //   49: astore 9
    //   51: aload 5
    //   53: ifnull +13 -> 66
    //   56: aload 5
    //   58: aload 9
    //   60: invokevirtual 150	com/android/gallery3d/common/Fingerprint:equals	(Ljava/lang/Object;)Z
    //   63: ifne +27 -> 90
    //   66: getstatic 22	com/google/android/picasastore/FingerprintEntry:SCHEMA	Lcom/android/gallery3d/common/EntrySchema;
    //   69: aload_0
    //   70: getfield 54	com/google/android/picasastore/FingerprintManager:mDbHelper	Lcom/google/android/picasastore/FingerprintManager$FingerprintDatabaseHelper;
    //   73: invokevirtual 153	com/google/android/picasastore/FingerprintManager$FingerprintDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   76: new 18	com/google/android/picasastore/FingerprintEntry
    //   79: dup
    //   80: aload_1
    //   81: aload 9
    //   83: invokespecial 156	com/google/android/picasastore/FingerprintEntry:<init>	(Ljava/lang/String;Lcom/android/gallery3d/common/Fingerprint;)V
    //   86: invokevirtual 160	com/android/gallery3d/common/EntrySchema:insertOrReplace	(Landroid/database/sqlite/SQLiteDatabase;Lcom/android/gallery3d/common/Entry;)J
    //   89: pop2
    //   90: aload 9
    //   92: astore 5
    //   94: goto -72 -> 22
    //   97: astore 6
    //   99: ldc 102
    //   101: new 104	java/lang/StringBuilder
    //   104: dup
    //   105: ldc 162
    //   107: invokespecial 109	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   110: aload_1
    //   111: invokevirtual 113	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   117: aload 6
    //   119: invokestatic 165	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   122: pop
    //   123: aconst_null
    //   124: astore 5
    //   126: goto -104 -> 22
    //   129: astore 10
    //   131: ldc 102
    //   133: new 104	java/lang/StringBuilder
    //   136: dup
    //   137: ldc 167
    //   139: invokespecial 109	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   142: aload_1
    //   143: invokevirtual 113	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: invokevirtual 116	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   149: aload 10
    //   151: invokestatic 122	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   154: pop
    //   155: goto -65 -> 90
    //   158: astore_3
    //   159: aload_0
    //   160: monitorexit
    //   161: aload_3
    //   162: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   27	51	97	java/lang/Throwable
    //   56	90	129	java/lang/Throwable
    //   2	9	158	finally
    //   27	51	158	finally
    //   56	90	158	finally
    //   99	155	158	finally
  }

  public final int invalidate(String[] paramArrayOfString)
  {
    try
    {
      int i = this.mDbHelper.getWritableDatabase().delete(FINGERPRINT_TABLE, "content_uri=?", paramArrayOfString);
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final void reset()
  {
    this.mDbHelper.getWritableDatabase().delete(FingerprintEntry.SCHEMA.getTableName(), null, null);
  }

  private static final class FingerprintDatabaseHelper extends SQLiteOpenHelper
  {
    private Context mContext;

    FingerprintDatabaseHelper(Context paramContext)
    {
      super("fingerprint.db", null, 1);
      this.mContext = paramContext;
    }

    public final SQLiteDatabase getReadableDatabase()
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase2 = super.getReadableDatabase();
        localObject2 = localSQLiteDatabase2;
        return localObject2;
      }
      catch (Throwable localThrowable)
      {
        while (true)
        {
          this.mContext.deleteDatabase("fingerprint.db");
          SQLiteDatabase localSQLiteDatabase1 = super.getReadableDatabase();
          Object localObject2 = localSQLiteDatabase1;
        }
      }
      finally
      {
      }
    }

    public final SQLiteDatabase getWritableDatabase()
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase2 = super.getWritableDatabase();
        localObject2 = localSQLiteDatabase2;
        return localObject2;
      }
      catch (Throwable localThrowable)
      {
        while (true)
        {
          this.mContext.deleteDatabase("fingerprint.db");
          SQLiteDatabase localSQLiteDatabase1 = super.getWritableDatabase();
          Object localObject2 = localSQLiteDatabase1;
        }
      }
      finally
      {
      }
    }

    public final void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      FingerprintEntry.SCHEMA.createTables(paramSQLiteDatabase);
    }

    public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      FingerprintEntry.SCHEMA.dropTables(paramSQLiteDatabase);
      FingerprintEntry.SCHEMA.createTables(paramSQLiteDatabase);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasastore.FingerprintManager
 * JD-Core Version:    0.6.2
 */