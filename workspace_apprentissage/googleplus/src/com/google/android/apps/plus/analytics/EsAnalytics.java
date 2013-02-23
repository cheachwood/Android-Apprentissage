package com.google.android.apps.plus.analytics;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.content.DbEmotishareMetadata;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAnalyticsData;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.EsLog;
import com.google.api.services.plusi.model.ClientOzEvent;
import com.google.api.services.plusi.model.FavaDiagnostics;
import com.google.api.services.plusi.model.FavaDiagnosticsNamespacedType;
import com.google.api.services.plusi.model.OzEvent;
import java.util.Iterator;
import java.util.Set;

public final class EsAnalytics
{
  public static Bundle addExtrasForLogging(Bundle paramBundle, DbEmotishareMetadata paramDbEmotishareMetadata)
  {
    if (paramDbEmotishareMetadata != null)
    {
      if (paramBundle == null)
        paramBundle = new Bundle();
      paramBundle.putBoolean("extra_has_emotishare", true);
      String str = paramDbEmotishareMetadata.getImageUrl();
      if (!TextUtils.isEmpty(str))
      {
        Uri.Builder localBuilder = Uri.parse(str).buildUpon();
        localBuilder.appendQueryParameter("type", paramDbEmotishareMetadata.getType());
        paramBundle.putString("extra_media_url", localBuilder.toString());
      }
    }
    return paramBundle;
  }

  private static String getDisplayTextForExtras(Bundle paramBundle)
  {
    if (paramBundle == null);
    StringBuilder localStringBuilder;
    for (String str1 = "none"; ; str1 = localStringBuilder.toString())
    {
      return str1;
      localStringBuilder = new StringBuilder();
      Iterator localIterator = paramBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        localStringBuilder.append("(" + str2 + ":" + paramBundle.get(str2) + ")");
      }
    }
  }

  public static void postRecordEvent(Context paramContext, EsAccount paramEsAccount, AnalyticsInfo paramAnalyticsInfo, OzActions paramOzActions)
  {
    postRecordEvent(paramContext, paramEsAccount, paramAnalyticsInfo, paramOzActions, null);
  }

  public static void postRecordEvent(Context paramContext, final EsAccount paramEsAccount, final AnalyticsInfo paramAnalyticsInfo, final OzActions paramOzActions, final Bundle paramBundle)
  {
    Context localContext = paramContext.getApplicationContext();
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public final void run()
      {
        Context localContext = this.val$context;
        EsAccount localEsAccount = paramEsAccount;
        if (paramAnalyticsInfo == null);
        for (AnalyticsInfo localAnalyticsInfo = new AnalyticsInfo(); ; localAnalyticsInfo = paramAnalyticsInfo)
        {
          EsAnalytics.recordEvent(localContext, localEsAccount, localAnalyticsInfo, paramOzActions, paramBundle);
          return;
        }
      }
    });
  }

  public static void recordActionEvent(Context paramContext, EsAccount paramEsAccount, OzActions paramOzActions, OzViews paramOzViews)
  {
    recordActionEvent(paramContext, paramEsAccount, paramOzActions, paramOzViews, null);
  }

  public static void recordActionEvent(Context paramContext, EsAccount paramEsAccount, OzActions paramOzActions, OzViews paramOzViews, long paramLong)
  {
    recordActionEvent(paramContext, paramEsAccount, paramOzActions, paramOzViews, paramLong, System.currentTimeMillis(), null);
  }

  private static void recordActionEvent(Context paramContext, EsAccount paramEsAccount, OzActions paramOzActions, OzViews paramOzViews, long paramLong1, long paramLong2, Bundle paramBundle)
  {
    ClientOzEvent localClientOzEvent = EsAnalyticsData.createClientOzEvent(paramOzActions, paramOzViews, null, paramLong1, paramLong2, paramBundle);
    if (EsLog.isLoggable("EsAnalytics", 3))
      Log.d("EsAnalytics", "recordActionEvent action: " + OzActions.getName(paramOzActions) + " startView: " + OzViews.getName(paramOzViews) + " startTime: " + paramLong1 + " endTime: " + paramLong2 + " extras: " + getDisplayTextForExtras(paramBundle));
    recordEvent(paramContext, paramEsAccount, localClientOzEvent);
  }

  public static void recordActionEvent(Context paramContext, EsAccount paramEsAccount, OzActions paramOzActions, OzViews paramOzViews, Bundle paramBundle)
  {
    long l = System.currentTimeMillis();
    recordActionEvent(paramContext, paramEsAccount, paramOzActions, paramOzViews, l, l, paramBundle);
  }

  public static long recordEvent(Context paramContext, EsAccount paramEsAccount, AnalyticsInfo paramAnalyticsInfo, OzActions paramOzActions)
  {
    return recordEvent(paramContext, paramEsAccount, paramAnalyticsInfo, paramOzActions, null);
  }

  public static long recordEvent(Context paramContext, EsAccount paramEsAccount, AnalyticsInfo paramAnalyticsInfo, OzActions paramOzActions, Bundle paramBundle)
  {
    long l = System.currentTimeMillis();
    if (EsLog.isLoggable("EsAnalytics", 3))
      Log.d("EsAnalytics", "recordEvent action: " + OzActions.getName(paramOzActions) + " startView: " + OzViews.getName(paramAnalyticsInfo.getStartView()) + " endView: " + OzViews.getName(paramAnalyticsInfo.getEndView()) + " startTime: " + paramAnalyticsInfo.getStartTimeMsec() + " endTime: " + l + " extras: " + getDisplayTextForExtras(paramBundle));
    recordEvent(paramContext, paramEsAccount, EsAnalyticsData.createClientOzEvent(paramOzActions, paramAnalyticsInfo.getStartView(), paramAnalyticsInfo.getEndView(), paramAnalyticsInfo.getStartTimeMsec(), l, paramBundle));
    return l;
  }

  private static void recordEvent(Context paramContext, EsAccount paramEsAccount, ClientOzEvent paramClientOzEvent)
  {
    if ((paramEsAccount != null) && (paramClientOzEvent != null))
    {
      EsService.insertEvent(paramContext, paramEsAccount, paramClientOzEvent);
      if ((EsLog.isLoggable("EsAnalytics", 3)) && (paramClientOzEvent != null) && (paramClientOzEvent.ozEvent != null) && (paramClientOzEvent.ozEvent.favaDiagnostics != null))
        break label45;
    }
    while (true)
    {
      return;
      label45: Log.d("EsAnalytics", "logAction clientTimeMsec: " + paramClientOzEvent.clientTimeMsec + " totalTimeMs: " + paramClientOzEvent.ozEvent.favaDiagnostics.totalTimeMs);
      FavaDiagnosticsNamespacedType localFavaDiagnosticsNamespacedType1 = paramClientOzEvent.ozEvent.favaDiagnostics.startView;
      if (localFavaDiagnosticsNamespacedType1 != null)
        Log.d("EsAnalytics", "> startView namespace: " + localFavaDiagnosticsNamespacedType1.namespace + " typeNum: " + localFavaDiagnosticsNamespacedType1.typeNum);
      FavaDiagnosticsNamespacedType localFavaDiagnosticsNamespacedType2 = paramClientOzEvent.ozEvent.favaDiagnostics.endView;
      if (localFavaDiagnosticsNamespacedType2 != null)
        Log.d("EsAnalytics", "> endView namespace: " + localFavaDiagnosticsNamespacedType2.namespace + " typeNum: " + localFavaDiagnosticsNamespacedType2.typeNum);
      FavaDiagnosticsNamespacedType localFavaDiagnosticsNamespacedType3 = paramClientOzEvent.ozEvent.favaDiagnostics.actionType;
      if (localFavaDiagnosticsNamespacedType3 != null)
        Log.d("EsAnalytics", "> action namespace: " + localFavaDiagnosticsNamespacedType3.namespace + " typeNum: " + localFavaDiagnosticsNamespacedType3.typeNum);
    }
  }

  public static void recordImproveSuggestionsPreferenceChange(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean, OzViews paramOzViews)
  {
    OzActions localOzActions;
    String str;
    if (paramBoolean)
    {
      localOzActions = OzActions.ENABLE_IMPROVE_SUGGESTIONS;
      recordActionEvent(paramContext, paramEsAccount, localOzActions, paramOzViews);
      if (paramBoolean)
      {
        if (paramContext == null)
          break label75;
        TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
        if (localTelephonyManager == null)
          break label75;
        str = localTelephonyManager.getLine1Number();
        label49: if (TextUtils.isEmpty(str))
          break label81;
      }
    }
    label75: label81: 
    while (true)
    {
      recordActionEvent(paramContext, paramEsAccount, OzActions.ENABLE_FIND_BY_PHONE_NUMBER, paramOzViews);
      return;
      localOzActions = OzActions.DISABLE_IMPROVE_SUGGESTIONS;
      break;
      str = null;
      break label49;
    }
  }

  public static void recordNavigationEvent(Context paramContext, EsAccount paramEsAccount, OzViews paramOzViews1, OzViews paramOzViews2, Long paramLong1, Long paramLong2, Bundle paramBundle1, Bundle paramBundle2)
  {
    recordNavigationEvent(paramContext, paramEsAccount, paramOzViews1, paramOzViews2, paramLong1, null, paramBundle1, paramBundle2, null);
  }

  public static void recordNavigationEvent(Context paramContext, EsAccount paramEsAccount, OzViews paramOzViews1, OzViews paramOzViews2, Long paramLong1, Long paramLong2, Bundle paramBundle1, Bundle paramBundle2, Bundle paramBundle3)
  {
    long l1 = System.currentTimeMillis();
    if (paramLong1 == null)
      paramLong1 = Long.valueOf(l1);
    if (paramLong2 == null)
      paramLong2 = Long.valueOf(l1);
    if (paramBundle3 == null)
      paramBundle3 = new Bundle();
    if ((paramBundle1 != null) && (!paramBundle1.isEmpty()))
      paramBundle3.putBundle("extra_start_view_extras", paramBundle1);
    if ((paramBundle2 != null) && (!paramBundle2.isEmpty()))
      paramBundle3.putBundle("extra_end_view_extras", paramBundle2);
    long l2 = paramLong1.longValue();
    long l3 = paramLong2.longValue();
    if (paramBundle3.isEmpty());
    for (Bundle localBundle = null; ; localBundle = paramBundle3)
    {
      ClientOzEvent localClientOzEvent = EsAnalyticsData.createClientOzEvent(null, paramOzViews1, paramOzViews2, l2, l3, localBundle);
      if (EsLog.isLoggable("EsAnalytics", 3))
        Log.d("EsAnalytics", "recordNavigationEvent startView: " + OzViews.getName(paramOzViews1) + " endView: " + OzViews.getName(paramOzViews2) + " startTime: " + paramLong1 + " endTime: " + paramLong2);
      recordEvent(paramContext, paramEsAccount, localClientOzEvent);
      return;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.analytics.EsAnalytics
 * JD-Core Version:    0.6.2
 */