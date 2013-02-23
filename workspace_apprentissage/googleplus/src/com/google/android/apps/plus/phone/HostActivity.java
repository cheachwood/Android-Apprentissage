package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.InstrumentedActivity;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.HostActionBar.HostActionBarListener;
import com.google.android.apps.plus.views.HostActionBar.OnUpButtonClickListener;

public abstract class HostActivity extends InstrumentedActivity
  implements HostActionBar.HostActionBarListener, HostActionBar.OnUpButtonClickListener
{
  private HostActionBar mActionBar;
  private HostedFragment mHostedFragment;

  private void attachActionBar()
  {
    if (this.mActionBar != null)
    {
      this.mActionBar.reset();
      this.mHostedFragment.attachActionBar(this.mActionBar);
      onAttachActionBar(this.mActionBar);
      this.mActionBar.commit();
    }
  }

  protected EsAccount getAccount()
  {
    return (EsAccount)getIntent().getExtras().getParcelable("account");
  }

  protected int getContentView()
  {
    return R.layout.host_activity;
  }

  protected final int getDefaultFragmentContainerViewId()
  {
    return R.id.fragment_container;
  }

  protected final boolean isIntentAccountActive()
  {
    EsAccount localEsAccount = (EsAccount)getIntent().getParcelableExtra("account");
    boolean bool1 = false;
    if (localEsAccount != null)
    {
      if (localEsAccount.equals(EsService.getActiveAccount(this)))
        break label74;
      boolean bool2 = EsLog.isLoggable("HostActivity", 6);
      bool1 = false;
      if (bool2)
        Log.e("HostActivity", "Activity finished because it is associated with a signed-out account: " + getClass().getName());
    }
    while (true)
    {
      return bool1;
      label74: bool1 = true;
    }
  }

  public final void onActionBarInvalidated()
  {
    attachActionBar();
  }

  public final void onActionButtonClicked(int paramInt)
  {
    if (this.mHostedFragment != null)
      this.mHostedFragment.onActionButtonClicked(paramInt);
  }

  protected void onAttachActionBar(HostActionBar paramHostActionBar)
  {
  }

  public void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof HostedFragment))
    {
      this.mHostedFragment = ((HostedFragment)paramFragment);
      attachActionBar();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(getContentView());
    this.mActionBar = ((HostActionBar)findViewById(R.id.title_bar));
    this.mActionBar.setOnUpButtonClickListener(this);
    this.mActionBar.setHostActionBarListener(this);
    this.mActionBar.setUpButtonContentDescription(getString(R.string.nav_up_content_description));
    if (this.mHostedFragment != null)
      attachActionBar();
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.host_menu, paramMenu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if ((this.mHostedFragment != null) && (this.mHostedFragment.onOptionsItemSelected(paramMenuItem)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    HostedFragment localHostedFragment = this.mHostedFragment;
    boolean bool = false;
    if (localHostedFragment == null);
    while (true)
    {
      return bool;
      int i = paramMenu.size();
      for (int j = 0; j < i; j++)
        paramMenu.getItem(j).setVisible(false);
      this.mHostedFragment.onPrepareOptionsMenu(paramMenu);
      bool = true;
    }
  }

  public void onPrimarySpinnerSelectionChange(int paramInt)
  {
    if (this.mHostedFragment != null)
      this.mHostedFragment.onPrimarySpinnerSelectionChange(paramInt);
  }

  public final void onRefreshButtonClicked()
  {
    if (this.mHostedFragment != null)
      this.mHostedFragment.refresh();
  }

  protected void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }

  public void onUpButtonClick()
  {
    if ((getIntent().getBooleanExtra("from_url_gateway", false)) || (getIntent().getBooleanExtra("com.google.plus.analytics.intent.extra.FROM_NOTIFICATION", false)))
    {
      onUpButtonLaunchNewTask();
      finish();
    }
    while (true)
    {
      return;
      onBackPressed();
    }
  }

  protected void onUpButtonLaunchNewTask()
  {
    TaskStackBuilder localTaskStackBuilder = TaskStackBuilder.create(this);
    localTaskStackBuilder.addNextIntent(Intents.getStreamActivityIntent(this, getAccount()));
    localTaskStackBuilder.startActivities();
  }

  protected final void replaceFragment(Fragment paramFragment)
  {
    super.replaceFragment(paramFragment);
    if (this.mHostedFragment != null)
      this.mHostedFragment.recordNavigationAction();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.HostActivity
 * JD-Core Version:    0.6.2
 */