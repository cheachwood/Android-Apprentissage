package com.google.android.apps.plus.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.TelephonyManager;

public final class AndroidUtils
{
  private static final String[] ANDROID_ID_PROJECTION = { "android_id" };
  private static final Uri GSERVICES_CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");

  public static long getAndroidId(Context paramContext)
  {
    long l1 = 0L;
    Cursor localCursor = null;
    try
    {
      localCursor = paramContext.getContentResolver().query(GSERVICES_CONTENT_URI, null, null, ANDROID_ID_PROJECTION, null);
      String str;
      if ((localCursor != null) && (localCursor.moveToFirst()))
      {
        str = localCursor.getString(1);
        if (str == null)
          break label70;
      }
      try
      {
        long l2 = Long.parseLong(str);
        label70: for (l1 = l2; ; l1 = 0L)
          return l1;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        while (true)
          l1 = 0L;
      }
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  public static boolean hasTelephony(Context paramContext)
  {
    if (((TelephonyManager)paramContext.getSystemService("phone")).getPhoneType() != 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.AndroidUtils
 * JD-Core Version:    0.6.2
 */