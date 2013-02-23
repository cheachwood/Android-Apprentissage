package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.EventLocationFragment;
import com.google.android.apps.plus.fragments.EventLocationFragment.OnLocationSelectedListener;
import com.google.api.services.plusi.model.Place;
import com.google.api.services.plusi.model.PlaceJson;

public class EventLocationActivity extends EsFragmentActivity
  implements EventLocationFragment.OnLocationSelectedListener
{
  private String mInitialQuery;

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.LOCATION_PICKER;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof EventLocationFragment))
    {
      EventLocationFragment localEventLocationFragment = (EventLocationFragment)paramFragment;
      localEventLocationFragment.setOnLocationSelectedListener(this);
      if (this.mInitialQuery != null)
        localEventLocationFragment.setInitialQueryString(this.mInitialQuery);
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null)
    {
      byte[] arrayOfByte = getIntent().getByteArrayExtra("location");
      if (arrayOfByte != null)
        this.mInitialQuery = ((Place)PlaceJson.getInstance().fromByteArray(arrayOfByte)).name;
    }
    setContentView(R.layout.event_location_activity);
    showTitlebar(true);
    setTitlebarTitle(getString(R.string.event_location_activity_title));
  }

  public final void onLocationSelected(Place paramPlace)
  {
    Intent localIntent = new Intent();
    if (paramPlace != null)
      localIntent.putExtra("location", PlaceJson.getInstance().toByteArray(paramPlace));
    setResult(-1, localIntent);
    finish();
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
    case 16908332:
    }
    for (boolean bool = false; ; bool = true)
    {
      return bool;
      onBackPressed();
    }
  }

  public void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }

  protected final void onTitlebarLabelClick()
  {
    onBackPressed();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EventLocationActivity
 * JD-Core Version:    0.6.2
 */