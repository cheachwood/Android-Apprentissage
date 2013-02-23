package com.google.android.apps.plus.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.google.android.apps.plus.api.GetDoritosCookieOperation;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.phone.EsMatrixCursor.RowBuilder;
import com.google.android.apps.plus.util.EsLog;
import java.util.Date;
import org.apache.http.cookie.Cookie;

public class AdsProvider extends ContentProvider
{
  private static final UriMatcher URI_MATCHER;

  static
  {
    UriMatcher localUriMatcher = new UriMatcher(-1);
    URI_MATCHER = localUriMatcher;
    localUriMatcher.addURI("com.google.plus.platform", "token", 3);
  }

  private static Cursor toDoritosCookieCursor(String[] paramArrayOfString, String paramString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length != 1))
      throw new IllegalArgumentException("Expected a single column projection");
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(paramArrayOfString);
    localEsMatrixCursor.newRow().add(paramString);
    return localEsMatrixCursor;
  }

  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    return 0;
  }

  public String getType(Uri paramUri)
  {
    switch (URI_MATCHER.match(paramUri))
    {
    default:
      throw new IllegalArgumentException("Unknown URI " + paramUri);
    case 3:
    }
    return "vnd.android.cursor.item/vnd.com.google.plus.platform.token";
  }

  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    return null;
  }

  public boolean onCreate()
  {
    return true;
  }

  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    String str1 = paramUri.getQueryParameter("account");
    EsAccount localEsAccount;
    Cursor localCursor;
    if (str1 == null)
    {
      localEsAccount = EsAccountsData.getActiveAccount(getContext());
      if (localEsAccount == null)
        localEsAccount = null;
      if (localEsAccount != null)
        break label71;
      Log.w("AdsProvider", "No active account or specified account could not be found; aborting");
      localCursor = null;
    }
    label71: SharedPreferences localSharedPreferences;
    label320: Cookie localCookie;
    while (true)
    {
      return localCursor;
      localEsAccount = EsAccountsData.getAccountByName(getContext(), str1);
      if (localEsAccount != null)
        break;
      localEsAccount = null;
      break;
      if (EsLog.isLoggable("AdsProvider", 3))
        Log.d("AdsProvider", ">>>>> Ads query by " + localEsAccount + ", uri " + paramUri + ", projection " + paramArrayOfString1 + ", selection " + paramString1 + ", selectionArgs " + paramArrayOfString2);
      switch (URI_MATCHER.match(paramUri))
      {
      default:
        Log.w("AdsProvider", "Unknown URI " + paramUri);
        localCursor = null;
        break;
      case 3:
        localSharedPreferences = getContext().getSharedPreferences("drt_" + localEsAccount.getName(), 0);
        String str2 = localSharedPreferences.getString("drt", null);
        if (str2 != null)
        {
          Long localLong = Long.valueOf(localSharedPreferences.getLong("drt_expiry", -1L));
          if ((localLong.longValue() >= 0L) && (System.currentTimeMillis() < localLong.longValue()));
          for (int i = 1; ; i = 0)
          {
            if (i == 0)
              break label320;
            if (EsLog.isLoggable("AdsProvider", 3))
              Log.d("AdsProvider", "Returning cached doritos cookie");
            localCursor = toDoritosCookieCursor(paramArrayOfString1, str2);
            break;
          }
        }
        if (EsLog.isLoggable("AdsProvider", 3))
          Log.d("AdsProvider", "Fetching new doritos cookie");
        GetDoritosCookieOperation localGetDoritosCookieOperation = new GetDoritosCookieOperation(getContext(), localEsAccount);
        localGetDoritosCookieOperation.start();
        localCookie = localGetDoritosCookieOperation.getDoritosCookie();
        if (localCookie != null)
          break label380;
        localCursor = toDoritosCookieCursor(paramArrayOfString1, "");
      }
    }
    label380: String str3 = "_drt_=" + localCookie.getValue();
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putString("drt", str3);
    Date localDate = localCookie.getExpiryDate();
    if (localDate == null);
    for (long l = 0L; ; l = localDate.getTime())
    {
      localEditor.putLong("drt_expiry", l);
      localEditor.commit();
      localCursor = toDoritosCookieCursor(paramArrayOfString1, str3);
      break;
    }
  }

  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    return 0;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.AdsProvider
 * JD-Core Version:    0.6.2
 */