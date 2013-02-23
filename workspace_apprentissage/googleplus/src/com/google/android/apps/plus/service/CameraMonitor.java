package com.google.android.apps.plus.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video.Media;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.iu.InstantUploadFacade;
import com.google.android.apps.plus.iu.NewMediaTracker;
import com.google.android.apps.plus.phone.InstantUpload;
import com.google.android.apps.plus.util.MediaStoreUtils;

public class CameraMonitor extends BroadcastReceiver
{
  private static final Uri[] MEDIA_STORE_URIS = arrayOfUri;
  private static final Intent sIntent = new Intent("com.google.android.apps.plus.NEW_PICTURE");
  private static ContentObserver sMediaObserver;

  static
  {
    Uri[] arrayOfUri = new Uri[6];
    arrayOfUri[0] = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    arrayOfUri[1] = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
    arrayOfUri[2] = MediaStoreUtils.PHONE_STORAGE_IMAGES_URI;
    arrayOfUri[3] = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    arrayOfUri[4] = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
    arrayOfUri[5] = MediaStoreUtils.PHONE_STORAGE_VIDEO_URI;
  }

  public static void registerObservers(Context paramContext)
  {
    if (sMediaObserver != null);
    while (true)
    {
      return;
      sMediaObserver = new MediaObserver(paramContext, null);
      ContentResolver localContentResolver = paramContext.getContentResolver();
      Uri[] arrayOfUri = MEDIA_STORE_URIS;
      int i = arrayOfUri.length;
      for (int j = 0; j < i; j++)
        localContentResolver.registerContentObserver(arrayOfUri[j], true, sMediaObserver);
    }
  }

  public void onReceive(final Context paramContext, Intent paramIntent)
  {
    final PowerManager.WakeLock localWakeLock = ((PowerManager)paramContext.getSystemService("power")).newWakeLock(1, "Camera Monitor");
    localWakeLock.acquire();
    new Thread(new Runnable()
    {
      public final void run()
      {
        try
        {
          NewMediaTracker.getInstance(paramContext).processNewMedia();
          EsAccount localEsAccount = EsAccountsData.getActiveAccount(paramContext);
          if (localEsAccount == null);
          while (true)
          {
            return;
            EsEventData.validateInstantShare(paramContext, localEsAccount);
            if ((InstantUpload.isEnabled(paramContext)) || (InstantUpload.isInstantShareEnabled(paramContext)))
              InstantUploadFacade.requestUploadSync(paramContext);
            localWakeLock.release();
          }
        }
        finally
        {
          localWakeLock.release();
        }
      }
    }).start();
  }

  static final class MediaObserver extends ContentObserver
  {
    private final Context context;

    public MediaObserver(Context paramContext, Handler paramHandler)
    {
      super();
      this.context = paramContext;
    }

    public final void onChange(boolean paramBoolean)
    {
      PendingIntent localPendingIntent = PendingIntent.getBroadcast(this.context, 0, CameraMonitor.sIntent, 0);
      ((AlarmManager)this.context.getSystemService("alarm")).set(0, 12000L + System.currentTimeMillis(), localPendingIntent);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.CameraMonitor
 * JD-Core Version:    0.6.2
 */