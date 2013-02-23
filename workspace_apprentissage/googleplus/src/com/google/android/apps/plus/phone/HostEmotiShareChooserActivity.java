package com.google.android.apps.plus.phone;

import android.support.v4.app.Fragment;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.fragments.HostedEmotiShareChooserFragment;

public class HostEmotiShareChooserActivity extends HostActivity
{
  protected final Fragment createDefaultFragment()
  {
    return new HostedEmotiShareChooserFragment();
  }

  protected final int getContentView()
  {
    return R.layout.host_emotishare_chooser_activity;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.COMPOSE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.HostEmotiShareChooserActivity
 * JD-Core Version:    0.6.2
 */