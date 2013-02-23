package com.google.android.apps.plus.iu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.gallery3d.common.EntrySchema;

final class UploadsDatabaseHelper extends SQLiteOpenHelper
{
  private static UploadsDatabaseHelper sInstance;
  private Context mContext;

  private UploadsDatabaseHelper(Context paramContext)
  {
    super(paramContext.getApplicationContext(), "iu.upload.db", null, 7);
    this.mContext = paramContext;
  }

  public static UploadsDatabaseHelper getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new UploadsDatabaseHelper(paramContext);
      UploadsDatabaseHelper localUploadsDatabaseHelper = sInstance;
      return localUploadsDatabaseHelper;
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
        this.mContext.deleteDatabase("iu.upload.db");
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
        this.mContext.deleteDatabase("iu.upload.db");
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
    UploadTaskEntry.SCHEMA.createTables(paramSQLiteDatabase);
    MediaRecordEntry.SCHEMA.createTables(paramSQLiteDatabase);
  }

  public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 4)
      InstantUploadProvider.disableInstantShare(this.mContext);
    UploadTaskEntry.SCHEMA.dropTables(paramSQLiteDatabase);
    MediaRecordEntry.SCHEMA.dropTables(paramSQLiteDatabase);
    if (paramInt1 < 6);
    try
    {
      paramSQLiteDatabase.execSQL("DROP TABLE media_map");
      paramSQLiteDatabase.execSQL("DROP TABLE upload_records");
      label44: NewMediaTracker.clearPreferences(this.mContext);
      onCreate(paramSQLiteDatabase);
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      break label44;
    }
  }

  public final void reset()
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    localSQLiteDatabase.delete(UploadTaskEntry.SCHEMA.getTableName(), null, null);
    localSQLiteDatabase.delete(MediaRecordEntry.SCHEMA.getTableName(), null, null);
    NewMediaTracker.clearPreferences(this.mContext);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.UploadsDatabaseHelper
 * JD-Core Version:    0.6.2
 */