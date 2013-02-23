package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.fragments.OobSelectPlusPageFragment;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.views.ActionButton;

public class OobSelectPlusPageActivity extends OobDeviceActivity
{
  private EsAccount mAccount;
  private ActionButton mContinueButton;
  private OobSelectPlusPageFragment mFragment;

  protected final EsAccount getAccount()
  {
    if (this.mAccount != null);
    for (EsAccount localEsAccount = this.mAccount; ; localEsAccount = super.getAccount())
      return localEsAccount;
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
    case 1:
    }
    while (true)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      if (paramInt2 == 0)
      {
        EsService.removeAccount(this, getAccount());
        this.mAccount = null;
      }
    }
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof OobSelectPlusPageFragment))
      this.mFragment = ((OobSelectPlusPageFragment)paramFragment);
  }

  public final void onContinue()
  {
    this.mAccount = EsAccountsData.getActiveAccount(this);
    super.onContinue();
  }

  public final void onContinuePressed()
  {
    if (this.mFragment != null)
      this.mFragment.activateAccount();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.oob_select_plus_page_activity);
    showTitlebar(false);
    setTitlebarTitle(getString(R.string.app_name));
    if (paramBundle != null)
      this.mAccount = ((EsAccount)paramBundle.getParcelable("active_account"));
  }

  protected void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    this.mContinueButton = ((ActionButton)findViewById(16908314));
    if (this.mFragment != null)
      setContinueButtonEnabled(this.mFragment.isAccountSelected());
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mAccount != null)
      paramBundle.putParcelable("active_account", this.mAccount);
  }

  public final void setContinueButtonEnabled(boolean paramBoolean)
  {
    if (this.mContinueButton != null)
      this.mContinueButton.setEnabled(paramBoolean);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.OobSelectPlusPageActivity
 * JD-Core Version:    0.6.2
 */