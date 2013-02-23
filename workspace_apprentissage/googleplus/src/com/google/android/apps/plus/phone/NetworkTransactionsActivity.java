package com.google.android.apps.plus.phone;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.NetworkTransactionsListFragment;

public class NetworkTransactionsActivity extends EsFragmentActivity
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

  public void onAttachedToWindow()
  {
    if (Build.VERSION.SDK_INT < 11);
    for (ProgressBar localProgressBar = (ProgressBar)findViewById(R.id.progress_spinner); ; localProgressBar = (ProgressBar)findViewById(R.id.action_bar_progress_spinner_view))
    {
      ((NetworkTransactionsListFragment)getSupportFragmentManager().findFragmentById(R.id.network_transactions_fragment)).setProgressBar(localProgressBar);
      return;
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.network_transactions);
    this.mAccount = ((EsAccount)getIntent().getParcelableExtra("account"));
    if (Build.VERSION.SDK_INT >= 11)
      getActionBar().setDisplayHomeAsUpEnabled(true);
    while (true)
    {
      return;
      showTitlebar(true);
      setTitlebarTitle(getString(R.string.preferences_network_transactions_title));
      createTitlebarButtons(R.menu.network_transactions_menu);
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.network_transactions_menu, paramMenu);
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
      if (i == R.id.clear)
      {
        ((NetworkTransactionsListFragment)getSupportFragmentManager().findFragmentById(R.id.network_transactions_fragment)).clear();
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
 * Qualified Name:     com.google.android.apps.plus.phone.NetworkTransactionsActivity
 * JD-Core Version:    0.6.2
 */