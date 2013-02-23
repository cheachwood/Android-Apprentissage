package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.NotificationSettingsData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.SettingsFetchRequest;
import com.google.api.services.plusi.model.SettingsFetchRequestJson;
import com.google.api.services.plusi.model.SettingsFetchResponse;
import com.google.api.services.plusi.model.SettingsFetchResponseJson;

public final class GetNotificationSettingsOperation extends PlusiOperation<SettingsFetchRequest, SettingsFetchResponse>
{
  private NotificationSettingsData mNotificationSettings;

  public GetNotificationSettingsOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "settingsfetch", SettingsFetchRequestJson.getInstance(), SettingsFetchResponseJson.getInstance(), paramIntent, paramOperationListener);
  }

  public final NotificationSettingsData getNotificationSettings()
  {
    return this.mNotificationSettings;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetNotificationSettingsOperation
 * JD-Core Version:    0.6.2
 */