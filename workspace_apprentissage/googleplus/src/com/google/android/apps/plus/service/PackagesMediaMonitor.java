package com.google.android.apps.plus.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.picasastore.PicasaStoreFacade;
import com.google.android.picasasync.PicasaFacade;

public class PackagesMediaMonitor extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramIntent.setClass(paramContext, AsyncService.class);
    paramContext.startService(paramIntent);
  }

  public static class AsyncService extends IntentService
  {
    private PowerManager.WakeLock mServiceLock;

    public AsyncService()
    {
      super();
      setIntentRedelivery(true);
    }

    protected void onHandleIntent(Intent paramIntent)
    {
      while (true)
      {
        PicasaFacade localPicasaFacade;
        String str;
        try
        {
          localPicasaFacade = PicasaFacade.get(this);
          PicasaStoreFacade localPicasaStoreFacade = PicasaStoreFacade.get(this);
          paramIntent.getData().getSchemeSpecificPart();
          str = paramIntent.getAction();
          if ("android.intent.action.PACKAGE_ADDED".equals(str))
          {
            localPicasaFacade.onPackageAdded$552c4e01();
            localPicasaStoreFacade.onPackageAdded$552c4e01();
            return;
          }
          if ("android.intent.action.PACKAGE_REMOVED".equals(str))
          {
            localPicasaFacade.onPackageRemoved$552c4e01();
            localPicasaStoreFacade.onPackageRemoved$552c4e01();
            continue;
          }
        }
        finally
        {
          this.mServiceLock.release();
        }
        EsAccount localEsAccount;
        if ("android.intent.action.PACKAGE_CHANGED".equals(str))
        {
          localEsAccount = EsAccountsData.getActiveAccount(this);
          boolean bool = EsEventData.validateInstantShare(this, localEsAccount);
          if (!bool)
            continue;
        }
        try
        {
          Thread.sleep(5000L);
          label119: EsEventData.validateInstantShare(this, localEsAccount);
          continue;
          if (!"android.intent.action.MEDIA_MOUNTED".equals(str))
            continue;
          localPicasaFacade.onMediaMounted();
        }
        catch (InterruptedException localInterruptedException)
        {
          break label119;
        }
      }
    }

    public void onStart(Intent paramIntent, int paramInt)
    {
      if (this.mServiceLock == null)
        this.mServiceLock = ((PowerManager)getSystemService("power")).newWakeLock(1, "AsyncService");
      this.mServiceLock.acquire();
      super.onStart(paramIntent, paramInt);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.PackagesMediaMonitor
 * JD-Core Version:    0.6.2
 */