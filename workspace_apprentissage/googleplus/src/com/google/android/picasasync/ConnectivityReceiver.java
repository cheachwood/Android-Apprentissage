package com.google.android.picasasync;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramContext.startService(new Intent(paramContext, AsyncService.class));
  }

  public static class AsyncService extends IntentService
  {
    public AsyncService()
    {
      super();
    }

    protected void onHandleIntent(Intent paramIntent)
    {
      if (PicasaFacade.get(this).isMaster())
        PicasaSyncManager.get(this).onEnvironmentChanged();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.ConnectivityReceiver
 * JD-Core Version:    0.6.2
 */