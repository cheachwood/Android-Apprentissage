package com.google.android.apps.plus.iu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.gallery3d.common.EntrySchema;

final class PicasaDatabaseHelper extends SQLiteOpenHelper
{
  private static final String USER_ID_QUERY = "select _id from " + USER_TABLE + " where account" + "='%s' LIMIT 1";
  private static final String USER_TABLE = UserEntry.SCHEMA.getTableName();
  private static PicasaDatabaseHelper sInstance;
  private Context mContext;

  private PicasaDatabaseHelper(Context paramContext)
  {
    super(paramContext.getApplicationContext(), "iu.picasa.db", null, 1);
    this.mContext = paramContext.getApplicationContext();
  }

  public static PicasaDatabaseHelper get(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new PicasaDatabaseHelper(paramContext);
      PicasaDatabaseHelper localPicasaDatabaseHelper = sInstance;
      return localPicasaDatabaseHelper;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
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
        this.mContext.deleteDatabase("iu.picasa.db");
        SQLiteDatabase localSQLiteDatabase1 = super.getReadableDatabase();
        Object localObject2 = localSQLiteDatabase1;
      }
    }
    finally
    {
    }
  }

  final long getUserId(String paramString)
  {
    long l1 = -1L;
    String str = String.format(USER_ID_QUERY, new Object[] { paramString });
    Cursor localCursor = getReadableDatabase().rawQuery(str, null);
    if (localCursor == null);
    while (true)
    {
      return l1;
      try
      {
        if (localCursor.moveToNext())
        {
          long l2 = localCursor.getLong(0);
          l1 = l2;
          localCursor.close();
          continue;
        }
        localCursor.close();
      }
      finally
      {
        localCursor.close();
      }
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
        this.mContext.deleteDatabase("iu.picasa.db");
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
    PhotoEntry.SCHEMA.createTables(paramSQLiteDatabase);
    UserEntry.SCHEMA.createTables(paramSQLiteDatabase);
    InstantUploadSyncManager.getInstance(this.mContext).requestAccountSync();
  }

  public final void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    onUpgrade(paramSQLiteDatabase, paramInt1, paramInt2);
  }

  public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    PhotoEntry.SCHEMA.dropTables(paramSQLiteDatabase);
    UserEntry.SCHEMA.dropTables(paramSQLiteDatabase);
    onCreate(paramSQLiteDatabase);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.PicasaDatabaseHelper
 * JD-Core Version:    0.6.2
 */