package com.android.volley;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class VolleyLog
{
  public static final boolean DEBUG = Log.isLoggable("Volley", 2);
  public static String TAG = "Volley";

  private static String buildMessage(String paramString, Object[] paramArrayOfObject)
  {
    String str1;
    StackTraceElement[] arrayOfStackTraceElement;
    String str2;
    if (paramArrayOfObject == null)
    {
      str1 = paramString;
      arrayOfStackTraceElement = new Throwable().fillInStackTrace().getStackTrace();
      str2 = "<unknown>";
    }
    for (int i = 2; ; i++)
      if (i < arrayOfStackTraceElement.length)
      {
        if (!arrayOfStackTraceElement[i].getClass().equals(VolleyLog.class))
        {
          String str3 = arrayOfStackTraceElement[i].getClassName();
          String str4 = str3.substring(1 + str3.lastIndexOf('.'));
          String str5 = str4.substring(1 + str4.lastIndexOf('$'));
          str2 = str5 + "." + arrayOfStackTraceElement[i].getMethodName();
        }
      }
      else
      {
        Locale localLocale = Locale.US;
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = Long.valueOf(Thread.currentThread().getId());
        arrayOfObject[1] = str2;
        arrayOfObject[2] = str1;
        return String.format(localLocale, "[%d] %s: %s", arrayOfObject);
        str1 = String.format(Locale.US, paramString, paramArrayOfObject);
        break;
      }
  }

  public static void d(String paramString, Object[] paramArrayOfObject)
  {
    Log.d(TAG, buildMessage(paramString, paramArrayOfObject));
  }

  public static void e(String paramString, Object[] paramArrayOfObject)
  {
    Log.e(TAG, buildMessage(paramString, paramArrayOfObject));
  }

  public static void v(String paramString, Object[] paramArrayOfObject)
  {
    if (DEBUG)
      Log.v(TAG, buildMessage(paramString, paramArrayOfObject));
  }

  static final class MarkerLog
  {
    public static final boolean ENABLED = VolleyLog.DEBUG;
    private boolean mFinished = false;
    private final List<Marker> mMarkers = new ArrayList();

    public final void add(String paramString, long paramLong)
    {
      try
      {
        if (this.mFinished)
          throw new IllegalStateException("Marker added to finished log");
      }
      finally
      {
      }
      this.mMarkers.add(new Marker(paramString, paramLong, SystemClock.elapsedRealtime()));
    }

    protected final void finalize()
      throws Throwable
    {
      if (!this.mFinished)
      {
        finish("Request on the loose");
        VolleyLog.e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
      }
    }

    public final void finish(String paramString)
    {
      try
      {
        this.mFinished = true;
        int i = this.mMarkers.size();
        long l2;
        if (i == 0)
        {
          l2 = 0L;
          if (l2 > 0L)
            break label86;
        }
        while (true)
        {
          return;
          long l1 = ((Marker)this.mMarkers.get(0)).time;
          l2 = ((Marker)this.mMarkers.get(-1 + this.mMarkers.size())).time - l1;
          break;
          label86: long l3 = ((Marker)this.mMarkers.get(0)).time;
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = Long.valueOf(l2);
          arrayOfObject1[1] = paramString;
          VolleyLog.d("(%-4d ms) %s", arrayOfObject1);
          Iterator localIterator = this.mMarkers.iterator();
          while (localIterator.hasNext())
          {
            Marker localMarker = (Marker)localIterator.next();
            long l4 = localMarker.time;
            Object[] arrayOfObject2 = new Object[3];
            arrayOfObject2[0] = Long.valueOf(l4 - l3);
            arrayOfObject2[1] = Long.valueOf(localMarker.thread);
            arrayOfObject2[2] = localMarker.name;
            VolleyLog.d("(+%-4d) [%2d] %s", arrayOfObject2);
            l3 = l4;
          }
        }
      }
      finally
      {
      }
    }

    private static final class Marker
    {
      public final String name;
      public final long thread;
      public final long time;

      public Marker(String paramString, long paramLong1, long paramLong2)
      {
        this.name = paramString;
        this.thread = paramLong1;
        this.time = paramLong2;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.VolleyLog
 * JD-Core Version:    0.6.2
 */