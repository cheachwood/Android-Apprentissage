package com.google.android.apps.plus.realtimechat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import com.google.android.apps.plus.R.bool;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.hangout.HangoutNotifications;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.util.EsLog;

public final class RealTimeChatNotifications
{
  private static long RING_DELAY_MS = 2000L;
  private static long sLastRingTime = 0L;

  private static Notification buildNotification(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, String paramString1, String paramString2, String paramString3, boolean paramBoolean, Uri paramUri)
  {
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, paramIntent, 0);
    Notification localNotification = new Notification(R.drawable.ic_stat_messenger, paramString1, System.currentTimeMillis());
    localNotification.setLatestEventInfo(paramContext, paramString2, paramString3, localPendingIntent);
    localNotification.flags = (0x10 | localNotification.flags);
    localNotification.defaults = (0x4 | localNotification.defaults);
    localNotification.setLatestEventInfo(paramContext, paramString2, paramString3, PendingIntent.getActivity(paramContext, 0, paramIntent, 134217728));
    Intent localIntent = Intents.getClearRealTimeChatIntent(paramEsAccount);
    localNotification.deleteIntent = PendingIntent.getBroadcast(paramContext, (int)System.currentTimeMillis(), localIntent, 0);
    if ((SystemClock.uptimeMillis() - sLastRingTime > RING_DELAY_MS) && (!paramBoolean))
    {
      sLastRingTime = SystemClock.uptimeMillis();
      if (paramUri == null)
        break label196;
      localNotification.sound = paramUri;
    }
    while (true)
    {
      Resources localResources2 = paramContext.getResources();
      String str3 = localResources2.getString(R.string.realtimechat_vibrate_setting_key);
      boolean bool2 = localResources2.getBoolean(R.bool.realtimechat_vibrate_setting_default_value);
      if (PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean(str3, bool2))
        localNotification.defaults = (0x2 | localNotification.defaults);
      return localNotification;
      label196: Resources localResources1 = paramContext.getResources();
      String str1 = localResources1.getString(R.string.realtimechat_ringtone_setting_key);
      String str2 = localResources1.getString(R.string.realtimechat_ringtone_setting_default_value);
      boolean bool1 = PreferenceManager.getDefaultSharedPreferences(paramContext).getString(str1, str2).equals(str2);
      int i = 0;
      if (!bool1)
        i = 1;
      if (i != 0)
      {
        Resources localResources3 = paramContext.getResources();
        String str4 = localResources3.getString(R.string.realtimechat_ringtone_setting_key);
        String str5 = localResources3.getString(R.string.realtimechat_ringtone_setting_default_value);
        localNotification.sound = Uri.parse(PreferenceManager.getDefaultSharedPreferences(paramContext).getString(str4, str5));
      }
      else
      {
        localNotification.defaults = (0x1 | localNotification.defaults);
      }
    }
  }

  private static String buildNotificationTag(Context paramContext, EsAccount paramEsAccount)
  {
    return paramContext.getPackageName() + ":chat_notifications:" + paramEsAccount.getName();
  }

  public static void cancel(Context paramContext, EsAccount paramEsAccount)
  {
    try
    {
      String str = buildNotificationTag(paramContext, paramEsAccount);
      ((NotificationManager)paramContext.getSystemService("notification")).cancel(str, 1);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private static Notification createNotification(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    Resources localResources = paramContext.getResources();
    String str1 = localResources.getString(R.string.realtimechat_notify_setting_key);
    boolean bool1 = localResources.getBoolean(R.bool.realtimechat_notify_setting_default_value);
    Object localObject3;
    if (!PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean(str1, bool1))
    {
      if (EsLog.isLoggable("RTCNotification", 2))
        Log.d("RTCNotification", "notifications disabled");
      localObject3 = null;
      return localObject3;
    }
    Uri localUri1 = EsProvider.appendAccountParameter(EsProvider.MESSAGE_NOTIFICATIONS_URI, paramEsAccount);
    Cursor localCursor = null;
    while (true)
    {
      Uri localUri2;
      Object localObject4;
      Object localObject5;
      boolean bool3;
      String str3;
      int i;
      String str4;
      String str5;
      Object localObject2;
      label1132: boolean bool2;
      try
      {
        String[] arrayOfString = new String[1];
        arrayOfString[0] = paramEsAccount.getRealTimeChatParticipantId();
        localUri2 = null;
        localCursor = paramContext.getContentResolver().query(localUri1, MessageQuery.PROJECTION, "author_id!=? AND notification_seen!=1 AND conversation_visible=1 AND conversation_muted=0 AND conversation_pending_leave!=1 AND (status=3 OR status=4) ", arrayOfString, "conversation_id, timestamp DESC");
        if (!localCursor.moveToFirst())
        {
          if (EsLog.isLoggable("RTCNotification", 2))
            Log.d("RTCNotification", "no unseen notifications");
          if (localCursor != null)
            localCursor.close();
          localObject3 = null;
          break;
        }
        long l1 = localCursor.getLong(5);
        if (localCursor.moveToNext())
        {
          if (localCursor.getLong(5) == l1)
            continue;
          if (EsLog.isLoggable("RTCNotification", 2))
            Log.d("RTCNotification", "multiple conversations with unseen notification");
          localCursor.close();
          localCursor = paramContext.getContentResolver().query(localUri1, MessageQuery.PROJECTION, "author_id!=? AND notification_seen!=1 AND conversation_visible=1 AND conversation_muted=0 AND conversation_pending_leave!=1 AND (status=3 OR status=4) ", arrayOfString, "timestamp DESC");
          boolean bool4 = localCursor.moveToFirst();
          if (!bool4)
          {
            if (localCursor != null)
              localCursor.close();
            localObject3 = null;
            break;
          }
          String str11 = localCursor.getString(0);
          int k = localCursor.getInt(3);
          String str12 = Html.fromHtml(localCursor.getString(2)).toString();
          String str13 = localCursor.getString(4);
          String str14 = null;
          Uri localUri3 = null;
          if (!paramBoolean)
          {
            if (localCursor.getInt(9) <= 0)
              continue;
            String str17 = localCursor.getString(12);
            if (EsLog.isLoggable("RTCNotification", 2))
              Log.d("RTCNotification", "pending accept " + str17);
            str14 = null;
            localUri3 = null;
            if (str17 != null)
            {
              str14 = null;
              localUri3 = null;
              if (!paramBoolean)
                str14 = paramContext.getString(R.string.realtimechat_invitation_notification_summary_text, new Object[] { str17 });
            }
          }
          String str15 = paramContext.getString(R.string.realtimechat_launcher_title);
          int m = R.string.realtimechat_notification_new_messages;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(localCursor.getCount());
          String str16 = paramContext.getString(m, arrayOfObject2);
          Intent localIntent2 = Intents.getMessengerActivityIntent(paramContext, paramEsAccount);
          localIntent2.putExtra("reset_notifications", true);
          localIntent2.addFlags(872415232);
          Notification localNotification2 = buildNotification(paramContext, paramEsAccount, localIntent2, str14, str15, str16, paramBoolean, localUri3);
          localObject3 = localNotification2;
          if (localCursor == null)
            break;
          localCursor.close();
          break;
          if (k != 1)
          {
            str14 = str12;
            localUri3 = null;
            if (k != 6)
              continue;
            localUri3 = HangoutNotifications.getDingtone(paramContext);
            continue;
          }
          if (str13 == null)
          {
            str14 = paramContext.getString(R.string.realtimechat_name_and_message_text, new Object[] { str11, str12 });
            localUri3 = null;
            continue;
          }
          str14 = paramContext.getString(R.string.realtimechat_name_and_message_image, new Object[] { str11 });
          localUri3 = null;
          continue;
        }
        if (EsLog.isLoggable("RTCNotification", 3))
          Log.d("RTCNotification", "single conversation with unseen notification");
        localCursor.moveToFirst();
        Intent localIntent1;
        if (localCursor.getInt(9) > 0)
        {
          String str8 = localCursor.getString(12);
          String str9 = localCursor.getString(11);
          if (EsLog.isLoggable("RTCNotification", 2))
            Log.d("RTCNotification", "pending accept " + str8);
          if ((str8 == null) || (str9 == null))
          {
            if (localCursor != null)
              localCursor.close();
            localObject3 = null;
            break;
          }
          localObject4 = null;
          if (!paramBoolean)
            localObject4 = paramContext.getString(R.string.realtimechat_invitation_notification_summary_text, new Object[] { str8 });
          localObject6 = paramContext.getString(R.string.realtimechat_invitation_notification_content_text, new Object[] { str9 });
          if (localCursor.getInt(8) != 0)
          {
            localObject5 = localCursor.getString(6);
            if (localObject5 == null)
              localObject5 = localCursor.getString(7);
            long l3 = localCursor.getLong(5);
            String str10 = localCursor.getString(10);
            if (localCursor.getInt(8) == 0)
              break label1494;
            bool3 = true;
            localIntent1 = Intents.getConversationInvititationActivityIntent(paramContext, paramEsAccount, l3, str10, bool3);
            localIntent1.addFlags(67108864);
            Notification localNotification1 = buildNotification(paramContext, paramEsAccount, localIntent1, (String)localObject4, (String)localObject5, (String)localObject6, paramBoolean, localUri2);
            localObject3 = localNotification1;
            if (localCursor == null)
              break;
            localCursor.close();
            break;
          }
          localObject5 = paramContext.getString(R.string.realtimechat_invitation_notification_title_text);
          continue;
        }
        if (EsLog.isLoggable("RTCNotification", 3))
          Log.d("RTCNotification", "conversation accepted");
        String str2 = localCursor.getString(1);
        str3 = localCursor.getString(0);
        i = localCursor.getInt(3);
        str4 = Html.fromHtml(localCursor.getString(2)).toString();
        str5 = localCursor.getString(4);
        localObject2 = localCursor.getString(6);
        if (localObject2 == null)
        {
          String str7 = localCursor.getString(7);
          localObject2 = str7;
        }
        if ((str2 == null) || (str3 == null) || (str4 == null) || (localObject2 == null))
        {
          if (localCursor != null)
            localCursor.close();
          localObject3 = null;
          break;
        }
        if (localCursor.getCount() != 1)
          break label1307;
        if (EsLog.isLoggable("RTCNotification", 2))
          Log.d("RTCNotification", "single message needs notification");
        localObject4 = null;
        localUri2 = null;
        if (paramBoolean)
          break label1500;
        if (i != 1)
        {
          localObject4 = str4;
          localUri2 = null;
          if (i != 6)
            break label1500;
          localUri2 = HangoutNotifications.getDingtone(paramContext);
          break label1500;
          long l2 = localCursor.getLong(5);
          if (localCursor.getInt(8) == 0)
            break label1488;
          bool2 = true;
          label1157: localIntent1 = Intents.getConversationActivityIntent(paramContext, paramEsAccount, l2, bool2);
          localIntent1.addFlags(67108864);
          continue;
        }
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
      if (str5 == null)
      {
        localObject4 = paramContext.getString(R.string.realtimechat_name_and_message_text, new Object[] { str3, str4 });
        localUri2 = null;
      }
      else
      {
        localObject4 = paramContext.getString(R.string.realtimechat_name_and_message_image, new Object[] { str3 });
        localUri2 = null;
      }
      label1307: label1488: label1494: label1500: 
      do
      {
        if (str5 == null)
        {
          localObject6 = paramContext.getString(R.string.realtimechat_name_and_message_text, new Object[] { str3, str4 });
          break label1132;
        }
        localObject6 = paramContext.getString(R.string.realtimechat_name_and_message_image, new Object[] { str3 });
        break label1132;
        if (EsLog.isLoggable("RTCNotification", 2))
          Log.d("RTCNotification", "multiple messages needs notification");
        localObject4 = null;
        localUri2 = null;
        if (!paramBoolean)
        {
          if (i == 1)
            break label1426;
          localObject4 = str4;
          localUri2 = null;
          if (i == 6)
            localUri2 = HangoutNotifications.getDingtone(paramContext);
        }
        while (true)
        {
          localObject5 = localCursor.getString(6);
          if (localObject5 == null)
            localObject5 = localCursor.getString(7);
          int j = R.string.realtimechat_notification_new_messages;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(localCursor.getCount());
          localObject6 = paramContext.getString(j, arrayOfObject1);
          break;
          if (str5 == null)
          {
            localObject4 = paramContext.getString(R.string.realtimechat_name_and_message_text, new Object[] { str3, str4 });
            localUri2 = null;
          }
          else
          {
            String str6 = paramContext.getString(R.string.realtimechat_name_and_message_image, new Object[] { str3 });
            localObject4 = str6;
            localUri2 = null;
          }
        }
        bool2 = false;
        break label1157;
        bool3 = false;
        break;
        localObject5 = localObject2;
      }
      while (i == 1);
      label1426: Object localObject6 = str4;
    }
  }

  public static void update(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    try
    {
      NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
      String str = buildNotificationTag(paramContext, paramEsAccount);
      if (EsLog.isLoggable("RTCNotification", 2))
        Log.d("RTCNotification", "Update");
      Notification localNotification = createNotification(paramContext, paramEsAccount, paramBoolean);
      if (localNotification != null)
      {
        localNotification.flags = (0x10 | localNotification.flags);
        localNotification.defaults = (0x4 | localNotification.defaults);
        localNotificationManager.notify(str, 1, localNotification);
      }
      while (true)
      {
        return;
        localNotificationManager.cancel(str, 1);
      }
    }
    finally
    {
    }
  }

  public static abstract interface MessageQuery
  {
    public static final String[] PROJECTION = { "author_first_name", "author_full_name", "text", "type", "image_url", "conversation_id", "conversation_name", "generated_name", "conversation_group", "conversation_pending_accept", "inviter_id", "inviter_first_name", "inviter_full_name" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.RealTimeChatNotifications
 * JD-Core Version:    0.6.2
 */