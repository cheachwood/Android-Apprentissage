package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.service.EsService;

public class AddedToCircleActivity extends EsFragmentActivity
{
  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.NOTIFICATIONS_CIRCLE;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.added_to_circle_activity);
    if (paramBundle == null)
    {
      String str = getIntent().getStringExtra("notif_id");
      if (str != null)
        EsService.markNotificationAsRead(this, getAccount(), str);
    }
    showTitlebar(false, true);
    setTitlebarTitle(getString(R.string.added_to_circle_title));
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
      goHome(getAccount());
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
    goHome(getAccount());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.AddedToCircleActivity
 * JD-Core Version:    0.6.2
 */