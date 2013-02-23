package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.SetMobileSettingsRequest;
import com.google.api.services.plusi.model.SetMobileSettingsRequestJson;
import com.google.api.services.plusi.model.SetMobileSettingsResponse;
import com.google.api.services.plusi.model.SetMobileSettingsResponseJson;

public final class SetSettingsOperation extends PlusiOperation<SetMobileSettingsRequest, SetMobileSettingsResponse>
{
  final long mWarmWelcomeTimestamp;

  public SetSettingsOperation(Context paramContext, EsAccount paramEsAccount, long paramLong, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "setmobilesettings", SetMobileSettingsRequestJson.getInstance(), SetMobileSettingsResponseJson.getInstance(), null, null);
    this.mWarmWelcomeTimestamp = paramLong;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SetSettingsOperation
 * JD-Core Version:    0.6.2
 */