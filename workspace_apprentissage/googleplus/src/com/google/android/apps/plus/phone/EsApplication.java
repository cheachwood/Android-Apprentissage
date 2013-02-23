package com.google.android.apps.plus.phone;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsDatabaseHelper;
import com.google.android.apps.plus.content.EsPhotosDataApiary;
import com.google.android.apps.plus.iu.InstantUploadFacade;
import com.google.android.apps.plus.service.PicasaNetworkReceiver;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.LinksRenderUtils;
import com.google.android.apps.plus.util.PlusBarUtils;
import com.google.android.picasastore.PicasaStoreFacade;

public class EsApplication extends Application
  implements Thread.UncaughtExceptionHandler
{
  public static int sMemoryClass;
  private Handler mHandler;
  private Thread.UncaughtExceptionHandler sSystemUncaughtExceptionHandler;

  public void onCreate()
  {
    super.onCreate();
    this.sSystemUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
    final Context localContext = getApplicationContext();
    EsAccountsData.loadExperiments(localContext);
    new AsyncTask()
    {
    }
    .execute(null);
    ActivityManager localActivityManager = (ActivityManager)localContext.getSystemService("activity");
    if (Build.VERSION.SDK_INT >= 11);
    for (sMemoryClass = localActivityManager.getLargeMemoryClass(); ; sMemoryClass = localActivityManager.getMemoryClass())
    {
      PlusBarUtils.init(localContext);
      LinksRenderUtils.init(localContext);
      EsPhotosDataApiary.setPhotosFromPostsAlbumName(localContext.getString(R.string.photo_view_default_title));
      com.google.android.picasasync.R.init(com.google.android.apps.plus.R.class);
      PicasaStoreFacade.setNetworkReceiver(PicasaNetworkReceiver.class);
      InstantUploadFacade.setNetworkReceiver(PicasaNetworkReceiver.class);
      return;
    }
  }

  public void uncaughtException(final Thread paramThread, final Throwable paramThrowable)
  {
    int i;
    if (getMainLooper().getThread() != paramThread)
    {
      i = 1;
      if (i == 0)
        break label124;
      if (EsLog.isLoggable("EsApplication", 6))
        Log.e("EsApplication", "Uncaught exception in background thread " + paramThread, paramThrowable);
      if (!EsDatabaseHelper.isDatabaseRecentlyDeleted())
        break label81;
      if (EsLog.isLoggable("EsApplication", 6))
        Log.e("EsApplication", "An account has just been deactivated, which put background threads at a risk of failure. Letting this thread live.", paramThrowable);
    }
    while (true)
    {
      return;
      i = 0;
      break;
      label81: if (this.mHandler == null)
        this.mHandler = new Handler(getMainLooper());
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          EsApplication.this.sSystemUncaughtExceptionHandler.uncaughtException(paramThread, paramThrowable);
        }
      });
      continue;
      label124: this.sSystemUncaughtExceptionHandler.uncaughtException(paramThread, paramThrowable);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EsApplication
 * JD-Core Version:    0.6.2
 */