package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.apps.plus.content.AccountSettingsData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.api.services.plusi.model.MobileOutOfBoxResponse;

public class AccountSelectionActivity extends BaseAccountSelectionActivity
{
  protected final void onAccountSet(MobileOutOfBoxResponse paramMobileOutOfBoxResponse, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData)
  {
    startActivity(Intents.getHomeOobActivityIntent(this, paramEsAccount, (Intent)getIntent().getParcelableExtra("intent"), paramMobileOutOfBoxResponse, paramAccountSettingsData));
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    showAccountSelectionOrUpgradeAccount(paramBundle);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.AccountSelectionActivity
 * JD-Core Version:    0.6.2
 */