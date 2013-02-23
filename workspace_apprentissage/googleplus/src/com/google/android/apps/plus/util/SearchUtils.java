package com.google.android.apps.plus.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsDatabaseHelper;

public final class SearchUtils
{
  public static String getContinuationToken(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    Object localObject1 = null;
    Cursor localCursor;
    if (paramString != null)
    {
      SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getReadableDatabase();
      String[] arrayOfString1 = { "continuation_token" };
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = getSearchKey(paramString);
      localCursor = localSQLiteDatabase.query("search", arrayOfString1, "search_key=?", arrayOfString2, null, null, null, null);
    }
    try
    {
      if (localCursor.moveToFirst())
      {
        String str = localCursor.getString(0);
        localObject1 = str;
        return localObject1;
      }
      localCursor.close();
      localObject1 = null;
    }
    finally
    {
      localCursor.close();
    }
  }

  public static String getSearchKey(String paramString)
  {
    return "com.google.android.apps.plus.search_key-" + paramString;
  }

  public static void insertSearchResults(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    SQLiteDatabase localSQLiteDatabase;
    if (paramString1 != null)
      localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    try
    {
      localSQLiteDatabase.beginTransaction();
      ContentValues localContentValues = new ContentValues(2);
      localContentValues.put("search_key", getSearchKey(paramString1));
      localContentValues.put("continuation_token", paramString2);
      localSQLiteDatabase.insertWithOnConflict("search", null, localContentValues, 5);
      localSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.SearchUtils
 * JD-Core Version:    0.6.2
 */