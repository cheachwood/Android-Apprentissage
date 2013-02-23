package com.google.android.apps.plus.hangout.crash;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class CrashTriggerActivity extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
    {
      public final void run()
      {
        CrashTriggerActivity.access$000(CrashTriggerActivity.this);
      }
    }
    , 1000L);
  }

  private static final class JavaCrashOnNativeThreadException extends RuntimeException
  {
    JavaCrashOnNativeThreadException(String paramString)
    {
      super();
    }
  }

  private static final class NativeCrashException extends RuntimeException
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.crash.CrashTriggerActivity
 * JD-Core Version:    0.6.2
 */