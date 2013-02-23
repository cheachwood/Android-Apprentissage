package com.google.android.apps.plus.c2dm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.hangout.HangoutRingingActivity;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.EsLog;

public class C2DMReceiver extends BroadcastReceiver
{
  public static void requestC2DMRegistrationId(Context paramContext)
  {
    if (EsLog.isLoggable("C2DMReceiver", 4))
      Log.i("C2DMReceiver", "requestC2DMReg");
    Intent localIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
    localIntent.putExtra("app", PendingIntent.getBroadcast(paramContext, 0, new Intent(), 0));
    localIntent.putExtra("sender", "bunch.eng.c2dm@gmail.com");
    paramContext.startService(localIntent);
  }

  public static void unregisterC2DM(Context paramContext)
  {
    if (EsLog.isLoggable("C2DMReceiver", 4))
      Log.i("C2DMReceiver", "unregisterC2DMReg");
    Intent localIntent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
    localIntent.putExtra("app", PendingIntent.getBroadcast(paramContext, 0, new Intent(), 0));
    localIntent.putExtra("sender", "bunch.eng.c2dm@gmail.com");
    paramContext.startService(localIntent);
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (EsLog.isLoggable("C2DMReceiver", 4))
      Log.i("C2DMReceiver", "onReceive " + paramIntent + " " + paramIntent.getAction());
    if (paramIntent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION"))
    {
      str6 = paramIntent.getStringExtra("registration_id");
      if (paramIntent.getStringExtra("error") != null)
      {
        if (EsLog.isLoggable("C2DMReceiver", 4))
          Log.i("C2DMReceiver", "c2dm error");
        RealTimeChatService.handleC2DMRegistrationError(paramContext, paramIntent.getStringExtra("error"));
        setResultCode(-1);
      }
    }
    while (!paramIntent.getAction().equals("com.google.android.c2dm.intent.RECEIVE"))
      while (true)
      {
        String str6;
        return;
        if (paramIntent.getStringExtra("unregistered") != null)
        {
          if (EsLog.isLoggable("C2DMReceiver", 4))
            Log.i("C2DMReceiver", "c2dm unregistration");
          RealTimeChatService.handleC2DMUnregistration(paramContext);
        }
        else if (str6 != null)
        {
          if (EsLog.isLoggable("C2DMReceiver", 4))
            Log.i("C2DMReceiver", "c2dm registration");
          RealTimeChatService.handleC2DMRegistration(paramContext, str6);
        }
      }
    String str1 = paramIntent.getStringExtra("focus_account_id");
    EsAccount localEsAccount = EsService.getActiveAccount(paramContext);
    String str2;
    if ((localEsAccount != null) && (localEsAccount.hasGaiaId()))
    {
      if (str1.startsWith("g:"))
      {
        str2 = str1.substring(2);
        label223: if (localEsAccount.isMyGaiaId(str2))
          break label264;
      }
    }
    else if (EsLog.isLoggable("C2DMReceiver", 4))
      Log.i("C2DMReceiver", "c2dm message for wrong account");
    while (true)
    {
      setResultCode(-1);
      break;
      str2 = str1;
      break label223;
      label264: String str3 = paramIntent.getStringExtra("type");
      if (str3 == null)
      {
        String str4 = paramIntent.getStringExtra("message_timestamp");
        String str5 = paramIntent.getStringExtra("conversation_id");
        if (!TextUtils.isEmpty(str4));
        try
        {
          long l = Long.valueOf(str4).longValue();
          EsAnalytics.recordActionEvent(paramContext, localEsAccount, OzActions.C2DM_MESSAGE_RECEIVED, OzViews.NOTIFICATIONS_WIDGET, l);
          if (EsLog.isLoggable("C2DMReceiver", 4))
            Log.i("C2DMReceiver", "handleMessage " + str5 + " " + str4);
          RealTimeChatService.connectIfLoggedIn(paramContext, str1, str5, str4);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          while (true)
            Log.e("C2DMReceiver", "C2DM timestamp value is invalid " + str4);
        }
      }
      else if (str3.equals("hangout"))
      {
        HangoutRingingActivity.onC2DMReceive(paramContext, localEsAccount, paramIntent);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.c2dm.C2DMReceiver
 * JD-Core Version:    0.6.2
 */