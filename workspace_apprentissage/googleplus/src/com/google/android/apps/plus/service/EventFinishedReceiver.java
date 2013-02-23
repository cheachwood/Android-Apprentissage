package com.google.android.apps.plus.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.phone.InstantUpload;

public class EventFinishedReceiver extends BroadcastReceiver
{
  public static final Intent sIntent = new Intent("com.google.android.apps.plus.eventfinished");

  public void onReceive(final Context paramContext, Intent paramIntent)
  {
    final PowerManager.WakeLock localWakeLock = ((PowerManager)paramContext.getSystemService("power")).newWakeLock(1, "EventFinishedReceiver");
    localWakeLock.acquire();
    new Thread(new Runnable()
    {
      public final void run()
      {
        try
        {
          if (TextUtils.equals(this.val$eventId, InstantUpload.getInstantShareEventId(paramContext)))
            EsEventData.disableInstantShare(paramContext);
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
 * Qualified Name:     com.google.android.apps.plus.service.EventFinishedReceiver
 * JD-Core Version:    0.6.2
 */