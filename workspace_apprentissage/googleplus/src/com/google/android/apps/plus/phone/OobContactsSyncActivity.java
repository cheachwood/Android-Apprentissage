package com.google.android.apps.plus.phone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.fragments.OobContactsSyncFragment;

public class OobContactsSyncActivity extends OobDeviceActivity
{
  private OobContactsSyncFragment mSyncFragment;

  public final OzViews getViewForLogging()
  {
    return OzViews.OOB_IMPROVE_CONTACTS_VIEW;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
    if ((paramFragment instanceof OobContactsSyncFragment))
      this.mSyncFragment = ((OobContactsSyncFragment)paramFragment);
  }

  public final void onContinuePressed()
  {
    if (this.mSyncFragment != null)
    {
      this.mSyncFragment.commit();
      super.onContinuePressed();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.oob_contacts_sync_activity);
    showTitlebar(false);
    setTitlebarTitle(getString(R.string.app_name));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.OobContactsSyncActivity
 * JD-Core Version:    0.6.2
 */