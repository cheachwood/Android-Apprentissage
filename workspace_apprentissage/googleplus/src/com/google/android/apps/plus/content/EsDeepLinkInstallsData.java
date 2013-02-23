package com.google.android.apps.plus.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.android.apps.plus.util.EsLog;

public final class EsDeepLinkInstallsData
{
  private static final String[] PROJECTION = { "package_name", "name", "source_name", "embed_deep_link", "launch_source" };

  static void cleanupData$3105fef4(SQLiteDatabase paramSQLiteDatabase)
  {
    int i = paramSQLiteDatabase.delete("deep_link_installs", null, null);
    if (EsLog.isLoggable("DeepLinking", 3))
      Log.d("DeepLinking", "cleanupData deleted deep link installs: " + i);
  }

  public static DeepLinkInstall getByPackageName(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    Cursor localCursor = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase().query("deep_link_installs_view", PROJECTION, "package_name=?", new String[] { paramString }, null, null, null, null);
    if (localCursor != null);
    try
    {
      if (localCursor.moveToFirst())
      {
        DeepLinkInstall localDeepLinkInstall2 = DeepLinkInstall.access$000(localCursor);
        localDeepLinkInstall1 = localDeepLinkInstall2;
      }
      boolean bool;
      do
      {
        return localDeepLinkInstall1;
        localCursor.close();
        bool = EsLog.isLoggable("DeepLinking", 5);
        localDeepLinkInstall1 = null;
      }
      while (!bool);
      Log.w("DeepLinking", "no deep link install data found for " + paramString);
      DeepLinkInstall localDeepLinkInstall1 = null;
    }
    finally
    {
      localCursor.close();
    }
  }

  public static void insert(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
    localContentValues.put("package_name", paramString1);
    localContentValues.put("launch_source", paramString4);
    localContentValues.put("activity_id", paramString2);
    localContentValues.put("author_id", paramString3);
    if ((localSQLiteDatabase.replace("deep_link_installs", null, localContentValues) <= 0L) && (EsLog.isLoggable("DeepLinking", 5)))
      Log.w("DeepLinking", "failed to add deep link install data for " + paramString1);
  }

  public static void removeByPackageName(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      if ((localSQLiteDatabase.delete("deep_link_installs", "package_name=?", new String[] { paramString }) <= 0) && (EsLog.isLoggable("DeepLinking", 5)))
        Log.w("DeepLinking", "failed to delete deep link install data for " + paramString);
      localSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void removeStaleEntries(Context paramContext, EsAccount paramEsAccount)
  {
    if (paramEsAccount == null);
    while (true)
    {
      return;
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
      localSQLiteDatabase.beginTransaction();
      try
      {
        long l = System.currentTimeMillis() - 3600000L;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = Long.toString(l);
        int i = localSQLiteDatabase.delete("deep_link_installs", "timestamp<?", arrayOfString);
        if ((i > 0) && (EsLog.isLoggable("DeepLinking", 3)))
          Log.d("DeepLinking", i + " stale deep link install row(s) deleted");
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
    }
  }

  public static final class DeepLinkInstall
  {
    public final String authorName;
    public final String creationSource;
    public final String data;
    public final String launchSource;
    public final String packageName;

    private DeepLinkInstall(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    {
      this.authorName = paramString1;
      this.creationSource = paramString2;
      this.packageName = paramString3;
      this.launchSource = paramString4;
      this.data = paramString5;
    }

    public final String toString()
    {
      Object[] arrayOfObject = new Object[5];
      arrayOfObject[0] = this.authorName;
      arrayOfObject[1] = this.creationSource;
      arrayOfObject[2] = this.packageName;
      arrayOfObject[3] = this.launchSource;
      arrayOfObject[4] = this.data;
      return String.format("DeepLinkInstall: authorName=%s, appName=%s, packageName=%s, launchSource=%s, data=%s", arrayOfObject);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsDeepLinkInstallsData
 * JD-Core Version:    0.6.2
 */