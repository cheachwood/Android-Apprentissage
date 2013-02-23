package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.AccountSettingsData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetMobileSettingsRequest;
import com.google.api.services.plusi.model.GetMobileSettingsRequestJson;
import com.google.api.services.plusi.model.GetMobileSettingsResponse;
import com.google.api.services.plusi.model.GetMobileSettingsResponseJson;

public final class GetSettingsOperation extends PlusiOperation<GetMobileSettingsRequest, GetMobileSettingsResponse>
{
  private AccountSettingsData mSettings;
  private boolean mSetupAccount;

  public GetSettingsOperation(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getmobilesettings", GetMobileSettingsRequestJson.getInstance(), GetMobileSettingsResponseJson.getInstance(), null, null);
    this.mSetupAccount = paramBoolean;
  }

  public final AccountSettingsData getAccountSettings()
  {
    return this.mSettings;
  }

  public final boolean hasPlusPages()
  {
    if (this.mSettings != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetSettingsOperation
 * JD-Core Version:    0.6.2
 */