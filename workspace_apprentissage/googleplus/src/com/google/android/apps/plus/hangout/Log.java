package com.google.android.apps.plus.hangout;

import com.google.android.apps.plus.util.EsLog;

public final class Log
{
  public static void debug(String paramString)
  {
    if (EsLog.isLoggable("GoogleMeeting", 3))
      android.util.Log.d("GoogleMeeting", paramString);
  }

  public static void debug(String paramString, Object[] paramArrayOfObject)
  {
    if (EsLog.isLoggable("GoogleMeeting", 3))
      android.util.Log.d("GoogleMeeting", String.format(paramString, paramArrayOfObject));
  }

  public static void error(String paramString)
  {
    if (EsLog.isLoggable("GoogleMeeting", 6))
      android.util.Log.e("GoogleMeeting", paramString);
  }

  public static void error(String paramString, Object[] paramArrayOfObject)
  {
    if (EsLog.isLoggable("GoogleMeeting", 6))
      android.util.Log.e("GoogleMeeting", String.format(paramString, paramArrayOfObject));
  }

  public static void info(String paramString)
  {
    if (EsLog.isLoggable("GoogleMeeting", 4))
      android.util.Log.i("GoogleMeeting", paramString);
  }

  public static void info(String paramString, Object[] paramArrayOfObject)
  {
    if (EsLog.isLoggable("GoogleMeeting", 4))
      android.util.Log.i("GoogleMeeting", String.format(paramString, paramArrayOfObject));
  }

  public static void warn(String paramString)
  {
    if (EsLog.isLoggable("GoogleMeeting", 5))
      android.util.Log.w("GoogleMeeting", paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.Log
 * JD-Core Version:    0.6.2
 */