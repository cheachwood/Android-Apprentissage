package com.google.android.apps.plus.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Log;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import java.util.Iterator;
import java.util.List;

public final class PlayStoreInstaller
{
  private static final Uri PLAY_STORE_TEST_URI = Uri.parse("market://search?q=com.android.youtube");

  private static boolean canResolveIntent(PackageManager paramPackageManager, Intent paramIntent)
  {
    boolean bool1 = false;
    if (paramIntent == null);
    while (true)
    {
      return bool1;
      List localList = paramPackageManager.queryIntentActivities(paramIntent, 0);
      bool1 = false;
      if (localList != null)
      {
        boolean bool2 = localList.isEmpty();
        bool1 = false;
        if (!bool2)
          bool1 = true;
      }
    }
  }

  public static Intent getContinueIntent(PackageManager paramPackageManager, String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent("com.google.android.apps.plus.VIEW_DEEP_LINK");
    localIntent.setPackage(paramString1);
    localIntent.setData(Uri.parse("vnd.google.deeplink://link/").buildUpon().appendQueryParameter("deep_link_id", paramString2).appendQueryParameter("gplus_source", paramString3).build());
    localIntent.addFlags(335544320);
    if (canResolveIntent(paramPackageManager, localIntent));
    while (true)
    {
      return localIntent;
      localIntent = null;
    }
  }

  public static Intent getInstallIntent(String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(String.format("market://details?id=%1$s", new Object[] { paramString })));
    localIntent.putExtra("use_direct_purchase", true);
    localIntent.addFlags(524288);
    return localIntent;
  }

  public static boolean isPackageInstalled(PackageManager paramPackageManager, String paramString)
  {
    Iterator localIterator = paramPackageManager.getInstalledApplications(128).iterator();
    do
      if (!localIterator.hasNext())
        break;
    while (!((ApplicationInfo)localIterator.next()).packageName.equals(paramString));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static boolean isPlayStoreInstalled(PackageManager paramPackageManager)
  {
    try
    {
      ApplicationInfo localApplicationInfo = paramPackageManager.getApplicationInfo("com.android.vending", 0);
      bool2 = false;
      if (localApplicationInfo == null)
        return bool2;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
      {
        boolean bool1 = EsLog.isLoggable("DeepLinking", 3);
        boolean bool2 = false;
        if (bool1)
        {
          Log.d("DeepLinking", "com.android.vending not found: " + localNameNotFoundException.getMessage());
          bool2 = false;
          continue;
          bool2 = canResolveIntent(paramPackageManager, new Intent("android.intent.action.VIEW", PLAY_STORE_TEST_URI));
        }
      }
    }
  }

  public static void notifyCompletedInstall(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    Intent localIntent = getContinueIntent(localPackageManager, paramString3, paramString4, paramString5);
    if (localIntent == null)
    {
      localIntent = localPackageManager.getLaunchIntentForPackage(paramString3);
      localIntent.addFlags(335544320);
      if (!canResolveIntent(localPackageManager, localIntent))
        break label239;
    }
    while (true)
    {
      if (Log.isLoggable("DeepLinking", 3))
        Log.d("DeepLinking", "Could not resolve continue Intent for " + paramString3 + " falling back to launch " + localIntent);
      int i = (int)System.currentTimeMillis();
      long l = System.currentTimeMillis();
      String str = paramContext.getString(R.string.source_app_installed_notification, new Object[] { paramString2, paramString1 });
      Notification localNotification = new Notification(R.drawable.ic_stat_gplus, str, l);
      PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, i, localIntent, 0);
      localNotification.setLatestEventInfo(paramContext, paramContext.getString(R.string.app_name), str, localPendingIntent);
      localNotification.flags = (0x10 | localNotification.flags);
      localNotification.defaults = (0x4 | localNotification.defaults);
      NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramContext.getPackageName();
      arrayOfObject[1] = paramString3;
      localNotificationManager.notify(String.format("%s:notifications:%s", arrayOfObject), 1000, localNotification);
      return;
      label239: localIntent = null;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.PlayStoreInstaller
 * JD-Core Version:    0.6.2
 */