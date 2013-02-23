package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.NotificationSettingsData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.SettingsSetRequest;
import com.google.api.services.plusi.model.SettingsSetRequestJson;
import com.google.api.services.plusi.model.SettingsSetResponse;
import com.google.api.services.plusi.model.SettingsSetResponseJson;

public final class SetNotificationSettingsOperation extends PlusiOperation<SettingsSetRequest, SettingsSetResponse>
{
  private NotificationSettingsData mSettings;

  public SetNotificationSettingsOperation(Context paramContext, EsAccount paramEsAccount, NotificationSettingsData paramNotificationSettingsData, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "settingsset", SettingsSetRequestJson.getInstance(), SettingsSetResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mSettings = paramNotificationSettingsData;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SetNotificationSettingsOperation
 * JD-Core Version:    0.6.2
 */