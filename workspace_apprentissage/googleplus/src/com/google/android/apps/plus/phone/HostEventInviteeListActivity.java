package com.google.android.apps.plus.phone;

import android.support.v4.app.Fragment;
import com.google.android.apps.plus.analytics.OzViews;

public class HostEventInviteeListActivity extends HostActivity
{
  protected final Fragment createDefaultFragment()
  {
    return new HostedEventInviteeListFragment();
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.ACTIVITY;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.HostEventInviteeListActivity
 * JD-Core Version:    0.6.2
 */