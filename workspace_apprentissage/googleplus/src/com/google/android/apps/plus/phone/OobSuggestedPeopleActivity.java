package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;

public class OobSuggestedPeopleActivity extends OobDeviceActivity
{
  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.OOB_ADD_PEOPLE_VIEW;
  }

  public final void onContinue()
  {
    super.onContinue();
    EsAccountsData.setWarmWelcomeTimestamp(this, getAccount(), System.currentTimeMillis(), false);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.oob_suggested_people_activity);
    String str = getString(R.string.app_name);
    showTitlebar(false);
    setTitlebarTitle(str);
    createTitlebarButtons(R.menu.suggested_people_menu);
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.suggested_people_menu, paramMenu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == R.id.search)
      onSearchRequested();
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    if (Build.VERSION.SDK_INT < 11)
      paramMenu.findItem(R.id.search).setVisible(false);
    return true;
  }

  protected final void onPrepareTitlebarButtons(Menu paramMenu)
  {
    for (int i = 0; i < paramMenu.size(); i++)
      paramMenu.getItem(i).setVisible(false);
    paramMenu.findItem(R.id.search).setVisible(true);
  }

  public boolean onSearchRequested()
  {
    startActivity(Intents.getPeopleSearchActivityIntent(this, getAccount(), false, -1, true, false, true, false, false));
    return true;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.OobSuggestedPeopleActivity
 * JD-Core Version:    0.6.2
 */