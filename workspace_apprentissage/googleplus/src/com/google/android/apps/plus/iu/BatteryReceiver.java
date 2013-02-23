package com.google.android.apps.plus.iu;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class BatteryReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramIntent.setComponent(new ComponentName(paramContext, AsyncService.class));
    paramContext.startService(paramIntent);
  }

  public static class AsyncService extends IntentService
  {
    public AsyncService()
    {
      super();
    }

    protected void onHandleIntent(Intent paramIntent)
    {
      boolean bool = "android.intent.action.ACTION_POWER_CONNECTED".equals(paramIntent.getAction());
      InstantUploadSyncManager.getInstance(this).onBatteryStateChanged(bool);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.BatteryReceiver
 * JD-Core Version:    0.6.2
 */