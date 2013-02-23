package com.google.android.apps.plus.analytics;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsAccount;
import java.util.ArrayList;

public abstract class InstrumentedActivity extends FragmentActivity
{
  private boolean mExited;
  private boolean mRecorded;
  private boolean mStartingActivity;

  private static long getStartTime(Intent paramIntent)
  {
    return paramIntent.getLongExtra("com.google.plus.analytics.intent.extra.START_TIME", System.currentTimeMillis());
  }

  private static OzViews getStartView(Intent paramIntent)
  {
    return OzViews.valueOf(paramIntent.getIntExtra("com.google.plus.analytics.intent.extra.START_VIEW", -1));
  }

  // ERROR //
  private Intent instrument(Intent paramIntent)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 50	com/google/android/apps/plus/analytics/InstrumentedActivity:getViewForLogging	()Lcom/google/android/apps/plus/analytics/OzViews;
    //   4: astore 5
    //   6: aload 5
    //   8: ifnull +100 -> 108
    //   11: new 24	android/content/Intent
    //   14: dup
    //   15: aload_1
    //   16: invokespecial 53	android/content/Intent:<init>	(Landroid/content/Intent;)V
    //   19: astore 6
    //   21: aload 6
    //   23: ldc 32
    //   25: aload 5
    //   27: invokevirtual 57	com/google/android/apps/plus/analytics/OzViews:ordinal	()I
    //   30: invokevirtual 61	android/content/Intent:putExtra	(Ljava/lang/String;I)Landroid/content/Intent;
    //   33: pop
    //   34: aload 6
    //   36: ldc 16
    //   38: invokestatic 22	java/lang/System:currentTimeMillis	()J
    //   41: invokevirtual 64	android/content/Intent:putExtra	(Ljava/lang/String;J)Landroid/content/Intent;
    //   44: pop
    //   45: aload 5
    //   47: getstatic 68	com/google/android/apps/plus/analytics/OzViews:PLATFORM_THIRD_PARTY_APP	Lcom/google/android/apps/plus/analytics/OzViews;
    //   50: invokevirtual 72	com/google/android/apps/plus/analytics/OzViews:equals	(Ljava/lang/Object;)Z
    //   53: ifne +16 -> 69
    //   56: aload_0
    //   57: invokevirtual 76	com/google/android/apps/plus/analytics/InstrumentedActivity:getIntent	()Landroid/content/Intent;
    //   60: ldc 78
    //   62: iconst_0
    //   63: invokevirtual 82	android/content/Intent:getBooleanExtra	(Ljava/lang/String;Z)Z
    //   66: ifeq +12 -> 78
    //   69: aload 6
    //   71: ldc 78
    //   73: iconst_1
    //   74: invokevirtual 85	android/content/Intent:putExtra	(Ljava/lang/String;Z)Landroid/content/Intent;
    //   77: pop
    //   78: aload_0
    //   79: invokevirtual 89	com/google/android/apps/plus/analytics/InstrumentedActivity:getExtrasForLogging	()Landroid/os/Bundle;
    //   82: astore 10
    //   84: aload 10
    //   86: ifnull +19 -> 105
    //   89: aload 10
    //   91: invokevirtual 95	android/os/Bundle:isEmpty	()Z
    //   94: ifne +11 -> 105
    //   97: aload 6
    //   99: aload 10
    //   101: invokevirtual 99	android/content/Intent:putExtras	(Landroid/os/Bundle;)Landroid/content/Intent;
    //   104: pop
    //   105: aload 6
    //   107: astore_1
    //   108: aload_1
    //   109: astore_3
    //   110: aload_3
    //   111: areturn
    //   112: astore_2
    //   113: ldc 101
    //   115: iconst_5
    //   116: invokestatic 107	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   119: ifeq +12 -> 131
    //   122: ldc 101
    //   124: ldc 109
    //   126: aload_2
    //   127: invokestatic 115	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   130: pop
    //   131: aload_1
    //   132: astore_3
    //   133: goto -23 -> 110
    //   136: astore_2
    //   137: aload 6
    //   139: astore_1
    //   140: goto -27 -> 113
    //
    // Exception table:
    //   from	to	target	type
    //   0	21	112	android/os/BadParcelableException
    //   21	105	136	android/os/BadParcelableException
  }

  public static boolean isFromThirdPartyApp(Intent paramIntent)
  {
    boolean bool = false;
    if (paramIntent != null)
      bool = paramIntent.getBooleanExtra("com.google.plus.analytics.intent.extra.FROM_THIRD_PARTY_APP", false);
    return bool;
  }

  private void recordNotificationActionEvent(Intent paramIntent)
  {
    boolean bool;
    Bundle localBundle;
    String str2;
    OzActions localOzActions;
    label68: ArrayList localArrayList1;
    label85: ArrayList localArrayList2;
    if (paramIntent == null)
    {
      bool = false;
      if (bool)
      {
        localBundle = new Bundle();
        String str1 = paramIntent.getStringExtra("notif_id");
        str2 = paramIntent.getStringExtra("coalescing_id");
        if (TextUtils.isEmpty(str1))
          break label181;
        localOzActions = OzActions.SINGLE_NOTIFICATION_CLICKED;
        localBundle.putString("extra_notification_id", str1);
        localBundle.putBoolean("extra_notification_read", paramIntent.getBooleanExtra("com.google.plus.analytics.intent.extra.NOTIFICATION_READ", false));
        if (!paramIntent.hasExtra("notif_types"))
          break label205;
        localArrayList1 = paramIntent.getIntegerArrayListExtra("notif_types");
        if (!paramIntent.hasExtra("coalescing_codes"))
          break label218;
        localArrayList2 = paramIntent.getStringArrayListExtra("coalescing_codes");
        label102: if ((!localArrayList1.isEmpty()) && (localArrayList1.size() == localArrayList2.size()))
        {
          localBundle.putIntegerArrayList("extra_notification_types", localArrayList1);
          localBundle.putStringArrayList("extra_coalescing_codes", localArrayList2);
        }
        if (!paramIntent.hasExtra("com.google.plus.analytics.intent.extra.START_VIEW"))
          break label231;
      }
    }
    label181: label205: label218: label231: for (OzViews localOzViews = OzViews.NOTIFICATIONS_WIDGET; ; localOzViews = OzViews.NOTIFICATIONS_SYSTEM)
    {
      EsAnalytics.recordActionEvent(getApplicationContext(), getAccount(), localOzActions, localOzViews, localBundle);
      return;
      bool = paramIntent.getBooleanExtra("com.google.plus.analytics.intent.extra.FROM_NOTIFICATION", false);
      break;
      if (!TextUtils.isEmpty(str2))
      {
        localOzActions = OzActions.SINGLE_NOTIFICATION_CLICKED;
        break label68;
      }
      localOzActions = OzActions.AGGREGATED_NOTIFICATION_CLICKED;
      break label68;
      localArrayList1 = new ArrayList(0);
      break label85;
      localArrayList2 = new ArrayList(0);
      break label102;
    }
  }

  public static void recordReverseViewNavigation(Activity paramActivity, EsAccount paramEsAccount, OzViews paramOzViews, Bundle paramBundle)
  {
    Intent localIntent = paramActivity.getIntent();
    OzViews localOzViews = getStartView(localIntent);
    Bundle localBundle1;
    Bundle localBundle2;
    if (localOzViews != null)
    {
      localBundle1 = localIntent.getBundleExtra("com.google.plus.analytics.intent.extra.EXTRA_START_VIEW_EXTRAS");
      if ((localIntent == null) || (!localIntent.getBooleanExtra("com.google.plus.analytics.intent.extra.FROM_THIRD_PARTY_APP", false)))
        break label76;
      localBundle2 = new Bundle();
      localBundle2.putBoolean("extra_platform_event", true);
    }
    while (true)
    {
      EsAnalytics.recordNavigationEvent(paramActivity, paramEsAccount, paramOzViews, localOzViews, null, null, paramBundle, localBundle1, localBundle2);
      return;
      label76: localBundle2 = null;
    }
  }

  private void recordViewNavigation()
  {
    if (this.mRecorded);
    while (true)
    {
      return;
      OzViews localOzViews = getViewForLogging();
      EsAccount localEsAccount = getAccount();
      if ((localEsAccount != null) && (localOzViews != null))
      {
        recordViewNavigation(this, localEsAccount, localOzViews);
        this.mRecorded = true;
      }
    }
  }

  public static void recordViewNavigation(Activity paramActivity, EsAccount paramEsAccount, OzViews paramOzViews)
  {
    Intent localIntent = paramActivity.getIntent();
    OzViews localOzViews = getStartView(localIntent);
    Bundle localBundle1 = localIntent.getBundleExtra("com.google.plus.analytics.intent.extra.EXTRA_START_VIEW_EXTRAS");
    long l = getStartTime(localIntent);
    Bundle localBundle2;
    if ((localIntent != null) && (localIntent.getBooleanExtra("com.google.plus.analytics.intent.extra.FROM_THIRD_PARTY_APP", false)))
    {
      localBundle2 = new Bundle();
      localBundle2.putBoolean("extra_platform_event", true);
    }
    while (true)
    {
      EsAnalytics.recordNavigationEvent(paramActivity, paramEsAccount, localOzViews, paramOzViews, Long.valueOf(l), null, localBundle1, null, localBundle2);
      return;
      localBundle2 = null;
    }
  }

  protected Fragment createDefaultFragment()
  {
    return null;
  }

  public void finish()
  {
    if ((!isFinishing()) && (!this.mStartingActivity))
    {
      if (!isTaskRoot())
        break label33;
      recordUserAction(OzActions.EXIT);
    }
    while (true)
    {
      super.finish();
      return;
      label33: OzViews localOzViews = getViewForLogging();
      EsAccount localEsAccount = getAccount();
      if ((localEsAccount != null) && (localOzViews != null))
        recordReverseViewNavigation(this, localEsAccount, localOzViews, getExtrasForLogging());
    }
  }

  protected abstract EsAccount getAccount();

  public final AnalyticsInfo getAnalyticsInfo$7d6d37aa()
  {
    return new AnalyticsInfo(getStartView(getIntent()), getViewForLogging(), getStartTime(getIntent()));
  }

  protected int getDefaultFragmentContainerViewId()
  {
    return 16908290;
  }

  protected Bundle getExtrasForLogging()
  {
    return null;
  }

  public abstract OzViews getViewForLogging();

  protected boolean needsAsyncData()
  {
    return false;
  }

  public final void onAsyncData()
  {
    recordViewNavigation();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
      this.mRecorded = paramBundle.getBoolean("analytics:recorded");
    for (this.mExited = paramBundle.getBoolean("analytics:exited"); ; this.mExited = true)
    {
      recordNotificationActionEvent(getIntent());
      return;
      Fragment localFragment = createDefaultFragment();
      if (localFragment != null)
        replaceFragment(localFragment);
    }
  }

  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    recordNotificationActionEvent(paramIntent);
    OzViews localOzViews1 = getStartView(paramIntent);
    Bundle localBundle1 = paramIntent.getBundleExtra("com.google.plus.analytics.intent.extra.EXTRA_START_VIEW_EXTRAS");
    OzViews localOzViews2 = getViewForLogging();
    Bundle localBundle2 = getExtrasForLogging();
    Bundle localBundle3;
    if ((paramIntent != null) && (paramIntent.getBooleanExtra("com.google.plus.analytics.intent.extra.FROM_THIRD_PARTY_APP", false)))
    {
      localBundle3 = new Bundle();
      localBundle3.putBoolean("extra_platform_event", true);
    }
    while (true)
    {
      EsAccount localEsAccount = getAccount();
      if ((localEsAccount != null) && (localOzViews1 != null) && (localOzViews2 != null) && (localOzViews1 != localOzViews2))
        EsAnalytics.recordNavigationEvent(this, localEsAccount, localOzViews1, localOzViews2, null, null, localBundle1, localBundle2, localBundle3);
      return;
      localBundle3 = null;
    }
  }

  protected void onPause()
  {
    super.onPause();
    this.mStartingActivity = false;
    KeyguardManager localKeyguardManager = (KeyguardManager)getSystemService("keyguard");
    PowerManager localPowerManager = (PowerManager)getSystemService("power");
    if ((localKeyguardManager.inKeyguardRestrictedInputMode()) || (!localPowerManager.isScreenOn()));
    for (int i = 1; ; i = 0)
    {
      if (i != 0)
      {
        recordUserAction(OzActions.EXIT);
        this.mExited = true;
      }
      return;
    }
  }

  protected void onResume()
  {
    super.onResume();
    if ((this.mExited) && (isTaskRoot()))
    {
      recordUserAction(OzActions.LAUNCH);
      this.mExited = false;
    }
    if (!needsAsyncData())
      recordViewNavigation();
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("analytics:recorded", this.mRecorded);
    paramBundle.putBoolean("analytics:exited", this.mExited);
  }

  protected void onUserLeaveHint()
  {
    super.onUserLeaveHint();
    if (!this.mStartingActivity)
    {
      recordUserAction(OzActions.EXIT);
      this.mExited = true;
    }
  }

  protected final void recordUserAction(AnalyticsInfo paramAnalyticsInfo, OzActions paramOzActions)
  {
    recordUserAction(paramAnalyticsInfo, paramOzActions, null);
  }

  protected final void recordUserAction(AnalyticsInfo paramAnalyticsInfo, OzActions paramOzActions, Bundle paramBundle)
  {
    EsAccount localEsAccount = getAccount();
    if (localEsAccount != null)
      EsAnalytics.recordEvent(this, localEsAccount, paramAnalyticsInfo, paramOzActions, paramBundle);
  }

  protected final void recordUserAction(OzActions paramOzActions)
  {
    EsAccount localEsAccount = getAccount();
    if (localEsAccount != null)
      EsAnalytics.recordActionEvent(this, localEsAccount, paramOzActions, getViewForLogging());
  }

  protected void replaceFragment(Fragment paramFragment)
  {
    int i = getDefaultFragmentContainerViewId();
    paramFragment.setArguments(getIntent().getExtras());
    FragmentManager localFragmentManager = getSupportFragmentManager();
    FragmentTransaction localFragmentTransaction = localFragmentManager.beginTransaction();
    localFragmentTransaction.replace(i, paramFragment, "default");
    localFragmentTransaction.setTransition(0);
    localFragmentTransaction.commitAllowingStateLoss();
    localFragmentManager.executePendingTransactions();
  }

  public void startActivity(Intent paramIntent)
  {
    super.startActivity(instrument(paramIntent));
    this.mStartingActivity = true;
  }

  public void startActivityForResult(Intent paramIntent, int paramInt)
  {
    super.startActivityForResult(instrument(paramIntent), paramInt);
    this.mStartingActivity = true;
  }

  public final void startActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt)
  {
    super.startActivityFromFragment(paramFragment, instrument(paramIntent), paramInt);
    this.mStartingActivity = true;
  }

  public final void startExternalActivity(Intent paramIntent)
  {
    paramIntent.addFlags(524288);
    startActivity(paramIntent);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.analytics.InstrumentedActivity
 * JD-Core Version:    0.6.2
 */