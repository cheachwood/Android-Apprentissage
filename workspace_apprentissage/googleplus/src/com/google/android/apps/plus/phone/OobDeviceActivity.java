package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AccountSettingsData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.views.BottomActionBar;

public abstract class OobDeviceActivity extends EsFragmentActivity
  implements View.OnClickListener
{
  protected EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public OzViews getViewForLogging()
  {
    return OzViews.UNKNOWN;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
    case 1:
    }
    while (true)
    {
      return;
      if (paramInt2 != 0)
      {
        setResult(paramInt2);
        finish();
        overridePendingTransition(0, 0);
      }
    }
  }

  public void onBackPressed()
  {
    EsAccount localEsAccount = getAccount();
    if (Intents.isInitialOobIntent$755b117a(getIntent()))
      EsService.removeAccount(this, localEsAccount);
    setResult(0);
    super.onBackPressed();
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
    case 16908313:
    case 16908314:
    }
    while (true)
    {
      return;
      onBackPressed();
      continue;
      onContinuePressed();
    }
  }

  public void onContinue()
  {
    Intent localIntent1 = getIntent();
    AccountSettingsData localAccountSettingsData = (AccountSettingsData)localIntent1.getParcelableExtra("plus_pages");
    Intent localIntent2 = Intents.getNextOobIntent(this, getAccount(), localAccountSettingsData, localIntent1);
    if (localIntent2 != null)
      startActivityForResult(localIntent2, 1);
    while (true)
    {
      return;
      EsAccountsData.setOobComplete(this, getAccount());
      setResult(-1);
      finish();
    }
  }

  public void onContinuePressed()
  {
    onContinue();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    EsAccountsData.setHasVisitedOob(this, true);
  }

  protected void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    EsAccount localEsAccount = getAccount();
    Intent localIntent = getIntent();
    BottomActionBar localBottomActionBar = (BottomActionBar)findViewById(R.id.bottom_bar);
    AccountSettingsData localAccountSettingsData = (AccountSettingsData)localIntent.getParcelableExtra("plus_pages");
    if (!Intents.isInitialOobIntent$755b117a(localIntent))
      localBottomActionBar.addButton(16908313, R.string.signup_back, this);
    if (!Intents.isLastOobIntent(this, localEsAccount, localAccountSettingsData, localIntent))
      localBottomActionBar.addButton(16908314, R.string.signup_continue, this);
    while (true)
    {
      return;
      localBottomActionBar.addButton(16908314, R.string.signup_done, this);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.OobDeviceActivity
 * JD-Core Version:    0.6.2
 */