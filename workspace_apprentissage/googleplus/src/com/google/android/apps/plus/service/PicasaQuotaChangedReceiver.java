package com.google.android.apps.plus.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.phone.InstantUpload;

public class PicasaQuotaChangedReceiver extends BroadcastReceiver
{
  public void onReceive(final Context paramContext, Intent paramIntent)
  {
    final PowerManager.WakeLock localWakeLock = ((PowerManager)paramContext.getSystemService("power")).newWakeLock(1, "Quota Changed");
    final int i = paramIntent.getIntExtra("quota_limit", -1);
    final int j = paramIntent.getIntExtra("quota_used", -1);
    final boolean bool = paramIntent.getBooleanExtra("full_size_disabled", false);
    final EsAccount localEsAccount = EsAccountsData.getActiveAccount(paramContext);
    localWakeLock.acquire();
    new Thread(new Runnable()
    {
      public final void run()
      {
        try
        {
          InstantUpload.showOutOfQuotaNotification(paramContext, localEsAccount, i, j, bool);
          return;
        }
        finally
        {
          localWakeLock.release();
        }
      }
    }).start();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.PicasaQuotaChangedReceiver
 * JD-Core Version:    0.6.2
 */