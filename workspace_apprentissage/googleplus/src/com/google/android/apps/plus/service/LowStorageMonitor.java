package com.google.android.apps.plus.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.util.EsLog;
import java.util.Iterator;
import java.util.List;

public class LowStorageMonitor extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (!"android.intent.action.DEVICE_STORAGE_LOW".equals(paramIntent.getAction()));
    while (true)
    {
      return;
      new CleanupTask((byte)0).execute(new Context[] { paramContext });
    }
  }

  private static final class CleanupTask extends AsyncTask<Context, Void, Void>
  {
    private static Void doInBackground(Context[] paramArrayOfContext)
    {
      if (paramArrayOfContext.length != 1)
        throw new IllegalArgumentException("Must pass a single context");
      try
      {
        Context localContext = paramArrayOfContext[0];
        String str = localContext.getPackageName();
        Iterator localIterator = ((ActivityManager)localContext.getSystemService("activity")).getRunningAppProcesses().iterator();
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo;
        do
        {
          if (!localIterator.hasNext())
            break;
          localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        }
        while (!TextUtils.equals(str, localRunningAppProcessInfo.processName));
        if ((100 != localRunningAppProcessInfo.importance) && (200 != localRunningAppProcessInfo.importance))
        {
          EsAccount localEsAccount = EsAccountsData.getActiveAccount(localContext);
          if (localEsAccount != null)
            EsProvider.cleanupData(localContext, localEsAccount, true);
          else if (EsLog.isLoggable("LowStorageMonitor", 4))
            Log.i("LowStorageMonitor", "G+ account not set; skip cleanup");
        }
      }
      catch (Throwable localThrowable)
      {
        Log.w("LowStorageMonitor", "Could not perform cleanup", localThrowable);
      }
      if (EsLog.isLoggable("LowStorageMonitor", 4))
        Log.i("LowStorageMonitor", "G+ is active; skip cleanup");
      return null;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.LowStorageMonitor
 * JD-Core Version:    0.6.2
 */