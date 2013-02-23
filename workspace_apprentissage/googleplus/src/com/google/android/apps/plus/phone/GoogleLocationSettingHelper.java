package com.google.android.apps.plus.phone;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public final class GoogleLocationSettingHelper
{
  private static final Uri GOOGLE_SETTINGS_CONTENT_URI = Uri.parse("content://com.google.settings/partner");

  public static int getUseLocationForServices(Context paramContext)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    Cursor localCursor = null;
    while (true)
    {
      Object localObject2;
      int i;
      try
      {
        localCursor = localContentResolver.query(GOOGLE_SETTINGS_CONTENT_URI, new String[] { "value" }, "name=?", new String[] { "use_location_for_services" }, null);
        localObject2 = null;
        if (localCursor != null)
        {
          boolean bool = localCursor.moveToNext();
          localObject2 = null;
          if (bool)
          {
            String str = localCursor.getString(0);
            localObject2 = str;
          }
        }
        if (localCursor != null)
          localCursor.close();
        if (localObject2 == null)
        {
          i = 2;
          return i;
        }
      }
      catch (RuntimeException localRuntimeException)
      {
        Log.w("GoogleLocationSettingHelper", "Failed to get 'Use My Location' setting", localRuntimeException);
        localObject2 = null;
        if (localCursor == null)
          continue;
        localCursor.close();
        localObject2 = null;
        continue;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
      try
      {
        int j = Integer.parseInt(localObject2);
        i = j;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        i = 2;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.GoogleLocationSettingHelper
 * JD-Core Version:    0.6.2
 */