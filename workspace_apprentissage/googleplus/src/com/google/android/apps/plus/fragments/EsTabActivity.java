package com.google.android.apps.plus.fragments;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.views.TabContainer;
import com.google.android.apps.plus.views.TabContainer.OnTabChangeListener;
import java.util.ArrayList;

public abstract class EsTabActivity extends EsFragmentActivity
  implements View.OnClickListener, TabContainer.OnTabChangeListener
{
  private int mCurrentTab = 0;
  private ProgressBar mProgressBar;
  private boolean mSwipeEnabled = true;
  private TabContainer mTabContainer;
  private int mTabContainerId;
  private final ArrayList<Tab> mTabs = new ArrayList();

  protected EsTabActivity(int paramInt1, int paramInt2)
  {
    this.mTabContainerId = paramInt2;
  }

  private void createTab(int paramInt)
  {
    Tab localTab = getTab(paramInt);
    localTab.fragment = onCreateTab(paramInt);
    FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
    localFragmentTransaction.setTransition(0);
    String str = "tab." + Integer.toString(paramInt);
    localFragmentTransaction.add(localTab.containerView.getId(), localTab.fragment, str);
    localFragmentTransaction.commitAllowingStateLoss();
  }

  private Tab getTab(int paramInt)
  {
    while (paramInt >= this.mTabs.size())
      this.mTabs.add(new Tab((byte)0));
    return (Tab)this.mTabs.get(paramInt);
  }

  protected static int getTabIndexForFragment(Fragment paramFragment)
  {
    String str = paramFragment.getTag();
    if ((str != null) && (str.startsWith("tab.")));
    while (true)
    {
      try
      {
        int j = Integer.parseInt(str.substring(4));
        i = j;
        return i;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        if (EsLog.isLoggable("EsEvents", 5))
          Log.w("EsEvents", "Unknown format for fragment tag; " + str);
      }
      int i = -1;
    }
  }

  private void onPrepareSelectedTab()
  {
    Fragment localFragment = getTab(this.mCurrentTab).fragment;
    if ((localFragment instanceof Refreshable))
      ((Refreshable)localFragment).setProgressBar(this.mProgressBar);
  }

  private void selectTab(int paramInt)
  {
    if (paramInt == this.mCurrentTab);
    while (true)
    {
      return;
      if (this.mCurrentTab != -1)
      {
        Fragment localFragment = getTab(this.mCurrentTab).fragment;
        if ((localFragment instanceof Refreshable))
        {
          if (this.mProgressBar != null)
            this.mProgressBar.setVisibility(8);
          ((Refreshable)localFragment).setProgressBar(null);
        }
      }
      EsAccount localEsAccount = getAccount();
      if (localEsAccount != null)
      {
        OzViews localOzViews1 = getViewForLogging$65a8335d();
        OzViews localOzViews2 = getViewForLogging$65a8335d();
        if (localOzViews2 != localOzViews1)
          EsAnalytics.recordNavigationEvent(this, localEsAccount, localOzViews1, localOzViews2, null, null, null, null);
      }
      this.mCurrentTab = paramInt;
      updateViewVisibility();
      onPrepareSelectedTab();
    }
  }

  private void updateViewVisibility()
  {
    if (this.mTabContainer == null)
    {
      this.mTabContainer = ((TabContainer)findViewById(this.mTabContainerId));
      this.mTabContainer.setScrollEnabled(this.mSwipeEnabled);
      this.mTabContainer.setOnTabChangeListener(this);
    }
    this.mTabContainer.setSelectedPanel(this.mCurrentTab);
    int i = 0;
    if (i < this.mTabs.size())
    {
      Tab localTab = (Tab)this.mTabs.get(i);
      if (i == this.mCurrentTab)
      {
        localTab.tabButton.setSelected(true);
        localTab.containerView.setVisibility(0);
        if (localTab.fragment == null)
          createTab(i);
      }
      while (true)
      {
        i++;
        break;
        localTab.tabButton.setSelected(false);
      }
    }
  }

  protected final void addTab(int paramInt1, int paramInt2, int paramInt3)
  {
    Tab localTab = getTab(paramInt1);
    localTab.tabButton = findViewById(paramInt2);
    localTab.tabButton.setOnClickListener(this);
    localTab.containerView = findViewById(paramInt3);
  }

  public final OzViews getViewForLogging()
  {
    return getViewForLogging$65a8335d();
  }

  protected abstract OzViews getViewForLogging$65a8335d();

  protected final void onAttachFragment(int paramInt, Fragment paramFragment)
  {
    getTab(paramInt).fragment = paramFragment;
  }

  public void onClick(View paramView)
  {
    for (int i = 0; ; i++)
      if (i < this.mTabs.size())
      {
        if (paramView == ((Tab)this.mTabs.get(i)).tabButton)
          selectTab(i);
      }
      else
        return;
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      getMenuInflater().inflate(R.menu.progress_bar_menu, paramMenu);
      this.mProgressBar = ((ProgressBar)paramMenu.findItem(R.id.action_bar_progress_spinner).getActionView().findViewById(R.id.action_bar_progress_spinner_view));
      if (this.mCurrentTab != -1)
      {
        Fragment localFragment = getTab(this.mCurrentTab).fragment;
        if ((localFragment instanceof Refreshable))
          ((Refreshable)localFragment).setProgressBar(this.mProgressBar);
      }
    }
    return super.onCreateOptionsMenu(paramMenu);
  }

  protected abstract Fragment onCreateTab(int paramInt);

  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    this.mCurrentTab = paramBundle.getInt("currentTab");
  }

  public void onResume()
  {
    super.onResume();
    updateViewVisibility();
    if (this.mCurrentTab != -1)
    {
      if ((Build.VERSION.SDK_INT < 11) && (this.mProgressBar == null))
        this.mProgressBar = ((ProgressBar)findViewById(R.id.progress_spinner));
      onPrepareSelectedTab();
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("currentTab", this.mCurrentTab);
  }

  public final void onTabSelected(int paramInt)
  {
    selectTab(paramInt);
  }

  public final void onTabVisibilityChange(int paramInt, boolean paramBoolean)
  {
    Tab localTab = (Tab)this.mTabs.get(paramInt);
    View localView = localTab.containerView;
    if (paramBoolean);
    for (int i = 0; ; i = 4)
    {
      localView.setVisibility(i);
      if ((paramBoolean) && (localTab.fragment == null))
        createTab(paramInt);
      return;
    }
  }

  private static final class Tab
  {
    public View containerView;
    public Fragment fragment;
    public View tabButton;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EsTabActivity
 * JD-Core Version:    0.6.2
 */