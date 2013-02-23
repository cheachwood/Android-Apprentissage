package com.google.android.apps.plus.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.util.EsLog;

public class EsFroyoWidgetProvider extends AppWidgetProvider
{
  public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfInt)
  {
    if (EsLog.isLoggable("EsFroyoWidgetProvider", 3))
      Log.d("EsFroyoWidgetProvider", "onUpdate");
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      int j = paramArrayOfInt[i];
      if (EsLog.isLoggable("EsFroyoWidgetProvider", 3))
        Log.d("EsFroyoWidgetProvider", "configureWidget(" + j + ")");
      RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), R.layout.widget_froyo_layout);
      Intent localIntent1 = Intents.getStreamActivityIntent(paramContext, null);
      localIntent1.setAction("com.google.android.apps.plus.widget.HOME_ACTION");
      PendingIntent localPendingIntent1 = PendingIntent.getActivity(paramContext, 0, localIntent1, 134217728);
      localRemoteViews.setOnClickPendingIntent(R.id.home_icon, localPendingIntent1);
      Intent localIntent2 = Intents.getPostActivityIntent(paramContext, null, null);
      localIntent2.setAction("com.google.android.apps.plus.widget.POST_ACTION");
      localIntent2.removeExtra("account");
      PendingIntent localPendingIntent2 = PendingIntent.getActivity(paramContext, 0, localIntent2, 134217728);
      localRemoteViews.setOnClickPendingIntent(R.id.new_post, localPendingIntent2);
      Intent localIntent3 = Intents.getWidgetCameraLauncherActivityIntent(paramContext, null);
      localIntent3.setAction("com.google.android.apps.plus.widget.CAMERA_ACTION");
      localIntent3.removeExtra("account");
      PendingIntent localPendingIntent3 = PendingIntent.getActivity(paramContext, 0, localIntent3, 134217728);
      localRemoteViews.setOnClickPendingIntent(R.id.camera_icon, localPendingIntent3);
      AppWidgetManager.getInstance(paramContext).updateAppWidget(j, localRemoteViews);
    }
    super.onUpdate(paramContext, paramAppWidgetManager, paramArrayOfInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.widget.EsFroyoWidgetProvider
 * JD-Core Version:    0.6.2
 */