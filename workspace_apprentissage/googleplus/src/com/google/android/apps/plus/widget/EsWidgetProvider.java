package com.google.android.apps.plus.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.util.EsLog;

public class EsWidgetProvider extends AppWidgetProvider
{
  public static void configureHeaderButtons(Context paramContext, EsAccount paramEsAccount, int paramInt, RemoteViews paramRemoteViews, String paramString, boolean paramBoolean)
  {
    Intent localIntent1 = Intents.getStreamActivityIntent(paramContext, paramEsAccount);
    localIntent1.setAction("com.google.android.apps.plus.widget.HOME_ACTION");
    PendingIntent localPendingIntent1 = PendingIntent.getActivity(paramContext, 0, localIntent1, 134217728);
    paramRemoteViews.setOnClickPendingIntent(R.id.home_icon, localPendingIntent1);
    int i;
    int j;
    label53: int m;
    label84: int i1;
    label105: int i3;
    label126: int i5;
    label157: int i6;
    if (paramEsAccount != null)
    {
      i = 1;
      if (TextUtils.isEmpty(paramString))
        break label300;
      j = 1;
      paramRemoteViews.setViewVisibility(R.id.refresh_progress, 8);
      paramRemoteViews.setViewVisibility(R.id.next_progress, 8);
      int k = R.id.post_icon;
      if (i == 0)
        break label306;
      m = 0;
      paramRemoteViews.setViewVisibility(k, m);
      int n = R.id.refresh_icon;
      if (!paramBoolean)
        break label313;
      i1 = 0;
      paramRemoteViews.setViewVisibility(n, i1);
      int i2 = R.id.next_icon;
      if (j == 0)
        break label320;
      i3 = 0;
      paramRemoteViews.setViewVisibility(i2, i3);
      int i4 = R.id.divider_1;
      if ((i == 0) || ((!paramBoolean) && (j == 0)))
        break label327;
      i5 = 0;
      paramRemoteViews.setViewVisibility(i4, i5);
      i6 = R.id.divider_2;
      if ((!paramBoolean) || (j == 0))
        break label334;
    }
    label300: label306: label313: label320: label327: label334: for (int i7 = 0; ; i7 = 8)
    {
      paramRemoteViews.setViewVisibility(i6, i7);
      if (i != 0)
      {
        Intent localIntent2 = Intents.getPostActivityIntent(paramContext, paramEsAccount, null);
        localIntent2.setAction("com.google.android.apps.plus.widget.POST_ACTION");
        PendingIntent localPendingIntent4 = PendingIntent.getActivity(paramContext, 0, localIntent2, 134217728);
        paramRemoteViews.setOnClickPendingIntent(R.id.post_icon, localPendingIntent4);
      }
      if (paramBoolean)
      {
        PendingIntent localPendingIntent3 = PendingIntent.getService(paramContext, 0, getWidgetUpdateIntent(paramContext, paramInt, null, true), 134217728);
        paramRemoteViews.setOnClickPendingIntent(R.id.refresh_icon, localPendingIntent3);
      }
      if (j != 0)
      {
        PendingIntent localPendingIntent2 = PendingIntent.getService(paramContext, 0, getWidgetUpdateIntent(paramContext, paramInt, paramString, false), 134217728);
        paramRemoteViews.setOnClickPendingIntent(R.id.next_icon, localPendingIntent2);
      }
      return;
      i = 0;
      break;
      j = 0;
      break label53;
      m = 8;
      break label84;
      i1 = 8;
      break label105;
      i3 = 8;
      break label126;
      i5 = 8;
      break label157;
    }
  }

  public static void configureWidget(Context paramContext, EsAccount paramEsAccount, int paramInt)
  {
    if (EsLog.isLoggable("EsWidget", 3))
      Log.d("EsWidget", "[" + paramInt + "] configureWidget");
    if (paramEsAccount == null)
      showTapToConfigure(paramContext, paramInt);
    while (true)
    {
      return;
      showLoadingView(paramContext, paramInt);
      updateWidget(paramContext, paramInt, null);
    }
  }

  private static Intent getWidgetUpdateIntent(Context paramContext, int paramInt, String paramString, boolean paramBoolean)
  {
    Intent localIntent = new Intent(paramContext, EsWidgetService.class);
    localIntent.putExtra("appWidgetId", paramInt);
    if (!TextUtils.isEmpty(paramString))
      localIntent.putExtra("activity_id", paramString);
    if (paramBoolean)
      localIntent.putExtra("refresh", true);
    localIntent.setData(Uri.parse(localIntent.toUri(1)));
    return localIntent;
  }

  public static void showLoadingView(Context paramContext, int paramInt)
  {
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), R.layout.widget_layout);
    configureHeaderButtons(paramContext, null, paramInt, localRemoteViews, null, false);
    localRemoteViews.setViewVisibility(R.id.next_progress, 0);
    localRemoteViews.setViewVisibility(R.id.widget_image_layout, 8);
    localRemoteViews.setViewVisibility(R.id.widget_text_layout, 8);
    localRemoteViews.setViewVisibility(R.id.widget_empty_layout, 0);
    localRemoteViews.setTextViewText(R.id.empty_view, paramContext.getString(R.string.loading));
    AppWidgetManager.getInstance(paramContext).updateAppWidget(paramInt, localRemoteViews);
  }

  public static void showNoPostsFound(Context paramContext, EsAccount paramEsAccount, int paramInt)
  {
    if (EsLog.isLoggable("EsWidget", 3))
      Log.d("EsWidget", "[" + paramInt + "] showNoPostsFound");
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), R.layout.widget_layout);
    configureHeaderButtons(paramContext, paramEsAccount, paramInt, localRemoteViews, null, true);
    localRemoteViews.setViewVisibility(R.id.widget_image_layout, 8);
    localRemoteViews.setViewVisibility(R.id.widget_text_layout, 8);
    localRemoteViews.setViewVisibility(R.id.widget_empty_layout, 0);
    localRemoteViews.setTextViewText(R.id.empty_view, paramContext.getString(R.string.no_posts));
    String str = EsWidgetUtils.loadCircleId(paramContext, paramInt);
    if (TextUtils.isEmpty(str))
      str = "v.all.circles";
    Intent localIntent = Intents.getCirclePostsActivityIntent(paramContext, paramEsAccount, str);
    localIntent.setAction("com.google.android.apps.plus.widget.CIRCLE_ACTION" + paramInt);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, localIntent, 134217728);
    localRemoteViews.setOnClickPendingIntent(R.id.widget_main, localPendingIntent);
    AppWidgetManager.getInstance(paramContext).updateAppWidget(paramInt, localRemoteViews);
  }

  public static void showProgressIndicator(Context paramContext, int paramInt, boolean paramBoolean)
  {
    if (EsLog.isLoggable("EsWidget", 3))
      Log.d("EsWidget", "[" + paramInt + "] showProgressIndicator");
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), R.layout.widget_layout);
    localRemoteViews.setTextViewText(R.id.empty_view, paramContext.getString(R.string.loading));
    AppWidgetManager localAppWidgetManager;
    int i;
    if (paramBoolean)
    {
      localRemoteViews.setViewVisibility(R.id.refresh_icon, 8);
      localRemoteViews.setViewVisibility(R.id.refresh_progress, 0);
      PendingIntent localPendingIntent = PendingIntent.getService(paramContext, 0, new Intent(paramContext, EsWidgetService.class), 0);
      localRemoteViews.setOnClickPendingIntent(R.id.next_icon, localPendingIntent);
      localAppWidgetManager = AppWidgetManager.getInstance(paramContext);
      if (Build.VERSION.SDK_INT < 11)
        break label163;
      i = 1;
      label130: if (i == 0)
        break label169;
      localAppWidgetManager.partiallyUpdateAppWidget(paramInt, localRemoteViews);
    }
    while (true)
    {
      return;
      localRemoteViews.setViewVisibility(R.id.next_icon, 8);
      localRemoteViews.setViewVisibility(R.id.next_progress, 0);
      break;
      label163: i = 0;
      break label130;
      label169: localRemoteViews.setViewVisibility(R.id.widget_empty_layout, 0);
      localRemoteViews.setViewVisibility(R.id.widget_image_layout, 8);
      localRemoteViews.setViewVisibility(R.id.widget_text_layout, 8);
      localAppWidgetManager.updateAppWidget(paramInt, localRemoteViews);
    }
  }

  public static void showTapToConfigure(Context paramContext, int paramInt)
  {
    if (EsLog.isLoggable("EsWidget", 3))
      Log.d("EsWidget", "[" + paramInt + "] showTapToConfigure");
    RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), R.layout.widget_layout);
    configureHeaderButtons(paramContext, null, paramInt, localRemoteViews, null, false);
    localRemoteViews.setViewVisibility(R.id.widget_image_layout, 8);
    localRemoteViews.setViewVisibility(R.id.widget_text_layout, 8);
    localRemoteViews.setViewVisibility(R.id.widget_empty_layout, 0);
    localRemoteViews.setTextViewText(R.id.empty_view, paramContext.getString(R.string.widget_tap_to_configure));
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, Intents.getStreamActivityIntent(paramContext, null), 134217728);
    localRemoteViews.setOnClickPendingIntent(R.id.widget_main, localPendingIntent);
    AppWidgetManager.getInstance(paramContext).updateAppWidget(paramInt, localRemoteViews);
  }

  public static void updateWidget(Context paramContext, int paramInt, String paramString)
  {
    paramContext.startService(getWidgetUpdateIntent(paramContext, paramInt, null, false));
  }

  public void onDeleted(Context paramContext, int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    int j = 0;
    if (j < i)
    {
      int k = paramArrayOfInt[j];
      if (EsLog.isLoggable("EsWidget", 3))
        Log.d("EsWidget", "[" + k + "] onDeleted");
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.google.android.apps.plus.widget.EsWidgetUtils", 0).edit();
      localEditor.remove("circleId_" + k);
      localEditor.remove("circleName_" + k);
      if (Build.VERSION.SDK_INT < 9)
        localEditor.commit();
      while (true)
      {
        j++;
        break;
        localEditor.apply();
      }
    }
  }

  public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++)
    {
      int k = paramArrayOfInt[j];
      if (EsLog.isLoggable("EsWidget", 3))
        Log.d("EsWidget", "[" + k + "] onUpdate");
      showLoadingView(paramContext, k);
      updateWidget(paramContext, k, null);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.widget.EsWidgetProvider
 * JD-Core Version:    0.6.2
 */