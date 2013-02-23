package com.google.android.apps.plus.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;

public final class NotificationUtils
{
  private static final int INSTANT_SHARE_NOTIFICATION_ID = R.id.event_instant_share_notification;

  public static void cancelInstantShareEnabled(Context paramContext)
  {
    ((NotificationManager)paramContext.getSystemService("notification")).cancel("InstantShare", INSTANT_SHARE_NOTIFICATION_ID);
  }

  public static void notifyInstantShareEnabled(Context paramContext, CharSequence paramCharSequence, PendingIntent paramPendingIntent)
  {
    notifyInstantShareEnabled(paramContext, paramCharSequence, paramPendingIntent, true);
  }

  public static void notifyInstantShareEnabled(Context paramContext, CharSequence paramCharSequence, PendingIntent paramPendingIntent, boolean paramBoolean)
  {
    String str1 = paramContext.getString(R.string.event_instant_share_enabled_notification_subtitle);
    if (paramBoolean);
    for (String str2 = str1; ; str2 = null)
    {
      Notification localNotification = new Notification(R.drawable.ic_stat_instant_share, str2, System.currentTimeMillis());
      localNotification.setLatestEventInfo(paramContext, paramCharSequence, str1, paramPendingIntent);
      localNotification.flags = 34;
      ((NotificationManager)paramContext.getSystemService("notification")).notify("InstantShare", INSTANT_SHARE_NOTIFICATION_ID, localNotification);
      return;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.NotificationUtils
 * JD-Core Version:    0.6.2
 */