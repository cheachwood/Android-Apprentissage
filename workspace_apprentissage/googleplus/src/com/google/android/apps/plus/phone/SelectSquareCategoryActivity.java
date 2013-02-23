package com.google.android.apps.plus.phone;

import android.support.v4.app.Fragment;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.fragments.SelectSquareCategoryFragment;

public class SelectSquareCategoryActivity extends HostActivity
{
  protected final Fragment createDefaultFragment()
  {
    return new SelectSquareCategoryFragment();
  }

  protected final int getContentView()
  {
    return R.layout.edit_square_audience_activity;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PEOPLE_PICKER;
  }

  protected void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.SelectSquareCategoryActivity
 * JD-Core Version:    0.6.2
 */