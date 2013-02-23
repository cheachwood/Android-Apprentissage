package com.google.android.apps.plus.phone;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.NetworkStatisticsFragment;

public class NetworkStatisticsActivity extends EsFragmentActivity
{
  private EsAccount mAccount;

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.UNKNOWN;
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.network_statistics_activity);
    this.mAccount = ((EsAccount)getIntent().getParcelableExtra("account"));
    if (Build.VERSION.SDK_INT >= 11)
      getActionBar().setDisplayHomeAsUpEnabled(true);
    while (true)
    {
      return;
      showTitlebar(true);
      setTitlebarTitle(getString(R.string.preferences_network_bandwidth_title));
      createTitlebarButtons(R.menu.network_statistics_menu);
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.network_statistics_menu, paramMenu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getItemId();
    boolean bool;
    if (i == 16908332)
    {
      goHome(this.mAccount);
      bool = true;
    }
    while (true)
    {
      return bool;
      if ((i == R.id.clear) || (i == R.id.customize))
      {
        ((NetworkStatisticsFragment)getSupportFragmentManager().findFragmentById(R.id.network_statistics_fragment)).onMenuItemSelected(paramMenuItem);
        bool = true;
      }
      else
      {
        bool = super.onOptionsItemSelected(paramMenuItem);
      }
    }
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    if (Build.VERSION.SDK_INT < 11)
      paramMenu.findItem(R.id.clear).setVisible(false);
    return true;
  }

  public final void onPrepareTitlebarButtons(Menu paramMenu)
  {
  }

  public void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }

  protected final void onTitlebarLabelClick()
  {
    goHome(this.mAccount);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.NetworkStatisticsActivity
 * JD-Core Version:    0.6.2
 */