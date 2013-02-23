package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.apps.plus.analytics.InstrumentedActivity;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.ProfileEditFragment;

public class ProfileEditActivity extends InstrumentedActivity
{
  private EsAccount mAccount;
  private ProfileEditFragment mFragment;

  protected final Fragment createDefaultFragment()
  {
    this.mFragment = new ProfileEditFragment();
    return this.mFragment;
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PROFILE;
  }

  public void onBackPressed()
  {
    if (this.mFragment != null)
      this.mFragment.onDiscard();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAccount = ((EsAccount)getIntent().getParcelableExtra("account"));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ProfileEditActivity
 * JD-Core Version:    0.6.2
 */