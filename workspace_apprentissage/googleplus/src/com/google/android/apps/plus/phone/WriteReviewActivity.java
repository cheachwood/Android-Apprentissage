package com.google.android.apps.plus.phone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.fragments.WriteReviewFragment;
import com.google.android.apps.plus.util.Property;

public class WriteReviewActivity extends HostActivity
{
  protected final Fragment createDefaultFragment()
  {
    return new WriteReviewFragment();
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.UNKNOWN;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (!Property.ENABLE_REWIEWS.getBoolean())
    {
      finish();
      Log.e("WriteReviewActivity", "Writing reviews is not enabled yet, this activity should not be used.");
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.WriteReviewActivity
 * JD-Core Version:    0.6.2
 */