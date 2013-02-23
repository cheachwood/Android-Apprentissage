package com.google.android.apps.plus.phone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.fragments.OobInstantUploadFragment;

public class OobInstantUploadActivity extends OobDeviceActivity
{
  private OobInstantUploadFragment mFragment;

  public final OzViews getViewForLogging()
  {
    return OzViews.OOB_CAMERA_SYNC;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof OobInstantUploadFragment))
      this.mFragment = ((OobInstantUploadFragment)paramFragment);
  }

  public final void onContinuePressed()
  {
    if ((this.mFragment == null) || (!this.mFragment.commit()));
    while (true)
    {
      return;
      super.onContinuePressed();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.oob_instant_upload_activity);
    showTitlebar(false);
    setTitlebarTitle(getString(R.string.app_name));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.OobInstantUploadActivity
 * JD-Core Version:    0.6.2
 */