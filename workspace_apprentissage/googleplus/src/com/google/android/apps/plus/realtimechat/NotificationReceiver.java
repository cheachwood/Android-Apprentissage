package com.google.android.apps.plus.realtimechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;

public class NotificationReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    RealTimeChatService.resetNotifications(paramContext, (EsAccount)paramIntent.getParcelableExtra("account"));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.NotificationReceiver
 * JD-Core Version:    0.6.2
 */