package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.google.android.apps.plus.R.attr;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.phone.HostedFragment;
import com.google.android.apps.plus.util.SoftInput;

public class HostLayout extends FrameLayout
  implements HostActionBar.HostActionBarListener, SlidingPanelLayout.OnSlidingPanelStateChange
{
  private HostActionBar mActionBar;
  private FragmentManager mFragmentManager;
  private HostLayoutListener mListener;
  private ListView mNavigationBar;
  private int mNavigationItemHeight;
  private SlidingPanelLayout mPanel;
  private View mSlidingBackground;

  public HostLayout(Context paramContext)
  {
    super(paramContext);
    FragmentActivity localFragmentActivity = (FragmentActivity)getContext();
    this.mFragmentManager = localFragmentActivity.getSupportFragmentManager();
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.navigationItemHeight;
    TypedArray localTypedArray = localFragmentActivity.obtainStyledAttributes(arrayOfInt);
    this.mNavigationItemHeight = localTypedArray.getDimensionPixelSize(0, 1);
    localTypedArray.recycle();
  }

  public HostLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    FragmentActivity localFragmentActivity = (FragmentActivity)getContext();
    this.mFragmentManager = localFragmentActivity.getSupportFragmentManager();
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.navigationItemHeight;
    TypedArray localTypedArray = localFragmentActivity.obtainStyledAttributes(arrayOfInt);
    this.mNavigationItemHeight = localTypedArray.getDimensionPixelSize(0, 1);
    localTypedArray.recycle();
  }

  public HostLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    FragmentActivity localFragmentActivity = (FragmentActivity)getContext();
    this.mFragmentManager = localFragmentActivity.getSupportFragmentManager();
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.navigationItemHeight;
    TypedArray localTypedArray = localFragmentActivity.obtainStyledAttributes(arrayOfInt);
    this.mNavigationItemHeight = localTypedArray.getDimensionPixelSize(0, 1);
    localTypedArray.recycle();
  }

  public final void attachActionBar()
  {
    HostedFragment localHostedFragment = getCurrentHostedFragment();
    if (localHostedFragment != null)
    {
      this.mActionBar.reset();
      localHostedFragment.attachActionBar(this.mActionBar);
      this.mActionBar.commit();
    }
  }

  public final HostActionBar getActionBar()
  {
    return this.mActionBar;
  }

  public final int getCollapsedMenuItemCount()
  {
    WindowManager localWindowManager = (WindowManager)getContext().getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return (int)(0.6F * localDisplayMetrics.heightPixels / this.mNavigationItemHeight);
  }

  public final HostedFragment getCurrentHostedFragment()
  {
    return (HostedFragment)this.mFragmentManager.findFragmentByTag("hosted");
  }

  public final View getNavigationBar()
  {
    return this.mNavigationBar;
  }

  public final void hideNavigationBar()
  {
    if (!this.mPanel.isOpen());
    while (true)
    {
      return;
      if (this.mListener != null)
        this.mListener.onNavigationBarVisibilityChange(false);
      this.mPanel.close();
    }
  }

  public final boolean isNavigationBarVisible()
  {
    return this.mPanel.isOpen();
  }

  public final void onActionBarInvalidated()
  {
    attachActionBar();
  }

  public final void onActionButtonClicked(int paramInt)
  {
    HostedFragment localHostedFragment = getCurrentHostedFragment();
    if (localHostedFragment != null)
      localHostedFragment.onActionButtonClicked(paramInt);
  }

  public final void onAttachFragment(HostedFragment paramHostedFragment)
  {
    paramHostedFragment.attachActionBar(this.mActionBar);
    this.mActionBar.commit();
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mActionBar = ((HostActionBar)findViewById(R.id.title_bar));
    this.mActionBar.setHostActionBarListener(this);
    this.mNavigationBar = ((ListView)findViewById(R.id.navigation_bar));
    this.mSlidingBackground = findViewById(R.id.fragment_sliding_background);
    this.mPanel = ((SlidingPanelLayout)findViewById(R.id.panel));
    this.mPanel.setOnSlidingPanelStateChange(this);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mPanel.isOpen());
    for (final int i = 0; ; i = 8)
    {
      this.mNavigationBar.setVisibility(i);
      this.mSlidingBackground.post(new Runnable()
      {
        public final void run()
        {
          HostLayout.this.mSlidingBackground.setVisibility(i);
        }
      });
      if (i == 0)
        this.mNavigationBar.layout(0, 0, this.mNavigationBar.getMeasuredWidth(), this.mNavigationBar.getMeasuredHeight());
      return;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = getMeasuredHeight();
    if (this.mNavigationBar.getVisibility() == 0)
    {
      int j = this.mPanel.getNavigationBarWidth();
      this.mNavigationBar.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), View.MeasureSpec.makeMeasureSpec(i, 1073741824));
    }
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    HostedFragment localHostedFragment = getCurrentHostedFragment();
    if (localHostedFragment != null);
    for (boolean bool = localHostedFragment.onOptionsItemSelected(paramMenuItem); ; bool = false)
      return bool;
  }

  public final void onPanelClosed()
  {
    this.mNavigationBar.setVisibility(8);
    this.mSlidingBackground.post(new Runnable()
    {
      public final void run()
      {
        HostLayout.this.mSlidingBackground.setVisibility(8);
      }
    });
    if (this.mListener != null)
      this.mListener.onNavigationBarVisibilityChange(false);
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    HostedFragment localHostedFragment = getCurrentHostedFragment();
    if (localHostedFragment != null)
      localHostedFragment.onPrepareOptionsMenu(paramMenu);
  }

  public final void onPrimarySpinnerSelectionChange(int paramInt)
  {
    HostedFragment localHostedFragment = getCurrentHostedFragment();
    if (localHostedFragment != null)
      localHostedFragment.onPrimarySpinnerSelectionChange(paramInt);
  }

  public final void onRefreshButtonClicked()
  {
    HostedFragment localHostedFragment = getCurrentHostedFragment();
    if (localHostedFragment != null)
      localHostedFragment.refresh();
  }

  public final Fragment.SavedState saveHostedFragmentState()
  {
    HostedFragment localHostedFragment = getCurrentHostedFragment();
    if (localHostedFragment != null);
    for (Fragment.SavedState localSavedState = this.mFragmentManager.saveFragmentInstanceState(localHostedFragment); ; localSavedState = null)
      return localSavedState;
  }

  public void setListener(HostLayoutListener paramHostLayoutListener)
  {
    this.mListener = paramHostLayoutListener;
  }

  public final void showFragment(HostedFragment paramHostedFragment, boolean paramBoolean, Fragment.SavedState paramSavedState)
  {
    HostedFragment localHostedFragment = getCurrentHostedFragment();
    OzViews localOzViews;
    Bundle localBundle;
    long l;
    FragmentTransaction localFragmentTransaction;
    if (localHostedFragment != null)
    {
      localOzViews = localHostedFragment.getViewForLogging();
      localBundle = localHostedFragment.getExtrasForLogging();
      localHostedFragment.detachActionBar();
      l = System.currentTimeMillis();
      this.mActionBar.reset();
      if (paramSavedState != null)
        paramHostedFragment.setInitialSavedState(paramSavedState);
      localFragmentTransaction = this.mFragmentManager.beginTransaction();
      localFragmentTransaction.replace(R.id.fragment_container, paramHostedFragment, "hosted");
      if (!paramBoolean)
        break label122;
      localFragmentTransaction.setTransition(4099);
      label85: localFragmentTransaction.commitAllowingStateLoss();
      hideNavigationBar();
      this.mFragmentManager.executePendingTransactions();
      if (localOzViews != null)
        break label132;
      paramHostedFragment.recordNavigationAction();
    }
    while (true)
    {
      return;
      localOzViews = null;
      localBundle = null;
      break;
      label122: localFragmentTransaction.setTransition(0);
      break label85;
      label132: paramHostedFragment.recordNavigationAction(localOzViews, l, localBundle);
    }
  }

  public final void showNavigationBar()
  {
    if (this.mPanel.isOpen());
    while (true)
    {
      return;
      if (this.mListener != null)
        this.mListener.onNavigationBarVisibilityChange(true);
      this.mActionBar.dismissPopupMenus();
      View localView1 = this.mActionBar.getRootView();
      if (localView1 != null)
      {
        View localView2 = localView1.findFocus();
        if (localView2 != null)
          SoftInput.hide(localView2);
      }
      this.mNavigationBar.setVisibility(0);
      this.mSlidingBackground.post(new Runnable()
      {
        public final void run()
        {
          HostLayout.this.mSlidingBackground.setVisibility(0);
        }
      });
      this.mPanel.open();
    }
  }

  public final void showNavigationBarDelayed()
  {
    postDelayed(new Runnable()
    {
      public final void run()
      {
        HostLayout.this.showNavigationBar();
      }
    }
    , 500L);
  }

  public final void toggleNavigationBarVisibility()
  {
    if (this.mPanel.isOpen())
      hideNavigationBar();
    while (true)
    {
      return;
      showNavigationBar();
    }
  }

  public static abstract interface HostLayoutListener
  {
    public abstract void onNavigationBarVisibilityChange(boolean paramBoolean);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.HostLayout
 * JD-Core Version:    0.6.2
 */