package com.google.android.apps.plus.phone;

import android.support.v4.app.Fragment;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.fragments.LocalReviewFragment;

public class LocalReviewActivity extends HostActivity
{
  protected final Fragment createDefaultFragment()
  {
    return new LocalReviewFragment();
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.ACTIVITY;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.LocalReviewActivity
 * JD-Core Version:    0.6.2
 */