package com.google.android.apps.plus.phone;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.views.HostActionBar;

public abstract class HostedFragment extends Fragment
{
  private HostActionBar mActionBar;
  private boolean mCalled;
  private OzViews mEndView;
  private Bundle mEndViewExtras;
  private boolean mPaused;
  private boolean mRecorded;
  private long mStartTime;
  private OzViews mStartView;
  private Bundle mStartViewExtras;

  public final void attachActionBar(HostActionBar paramHostActionBar)
  {
    this.mActionBar = paramHostActionBar;
    onPrepareActionBar(paramHostActionBar);
  }

  public final void clearNavigationAction()
  {
    this.mRecorded = false;
    this.mStartView = null;
    this.mStartViewExtras = null;
    this.mStartTime = 0L;
    this.mEndView = null;
    this.mEndViewExtras = null;
  }

  public final void detachActionBar()
  {
    this.mActionBar = null;
  }

  public abstract EsAccount getAccount();

  public final HostActionBar getActionBar()
  {
    return this.mActionBar;
  }

  public Bundle getExtrasForLogging()
  {
    return null;
  }

  protected final Context getSafeContext()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity != null);
    for (Context localContext = localFragmentActivity.getApplicationContext(); ; localContext = null)
      return localContext;
  }

  public abstract OzViews getViewForLogging();

  protected final void invalidateActionBar()
  {
    if (this.mActionBar != null)
      this.mActionBar.invalidateActionBar();
  }

  protected final boolean isPaused()
  {
    return this.mPaused;
  }

  protected boolean needsAsyncData()
  {
    return false;
  }

  public void onActionButtonClicked(int paramInt)
  {
  }

  protected void onAsyncData()
  {
    EsAccount localEsAccount = getAccount();
    if ((localEsAccount != null) && (!this.mRecorded) && (this.mEndView != null))
    {
      EsAnalytics.recordNavigationEvent(getActivity(), localEsAccount, this.mStartView, this.mEndView, Long.valueOf(this.mStartTime), null, this.mStartViewExtras, this.mEndViewExtras);
      this.mRecorded = true;
    }
  }

  public boolean onBackPressed()
  {
    return false;
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mCalled = false;
    onSetArguments(getArguments());
    if (!this.mCalled)
      throw new IllegalStateException("Did you forget to call super.onSetArguments()?");
  }

  public void onPause()
  {
    super.onPause();
    this.mPaused = true;
  }

  protected void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
  }

  public void onPrimarySpinnerSelectionChange(int paramInt)
  {
  }

  public void onResume()
  {
    super.onResume();
    this.mPaused = false;
  }

  protected void onSetArguments(Bundle paramBundle)
  {
    this.mCalled = true;
  }

  public boolean onUpButtonClicked()
  {
    return false;
  }

  public void recordNavigationAction()
  {
    recordNavigationAction(null, getViewForLogging(), null, null, getExtrasForLogging());
  }

  public final void recordNavigationAction(OzViews paramOzViews, long paramLong, Bundle paramBundle)
  {
    recordNavigationAction(paramOzViews, getViewForLogging(), Long.valueOf(paramLong), paramBundle, getExtrasForLogging());
  }

  public final void recordNavigationAction(OzViews paramOzViews1, OzViews paramOzViews2, Long paramLong, Bundle paramBundle1, Bundle paramBundle2)
  {
    if (!needsAsyncData())
    {
      EsAccount localEsAccount = getAccount();
      if ((localEsAccount != null) && (!this.mRecorded) && (paramOzViews2 != null))
      {
        EsAnalytics.recordNavigationEvent(getActivity(), localEsAccount, paramOzViews1, paramOzViews2, paramLong, null, paramBundle1, paramBundle2);
        this.mRecorded = true;
      }
      return;
    }
    this.mStartView = paramOzViews1;
    this.mStartViewExtras = paramBundle1;
    if (paramLong == null);
    for (long l = System.currentTimeMillis(); ; l = paramLong.longValue())
    {
      this.mStartTime = l;
      this.mEndView = paramOzViews2;
      this.mEndViewExtras = paramBundle2;
      break;
    }
  }

  protected final void recordUserAction(OzActions paramOzActions)
  {
    EsAccount localEsAccount = getAccount();
    if (localEsAccount != null)
      EsAnalytics.recordActionEvent(getActivity(), localEsAccount, paramOzActions, getViewForLogging());
  }

  protected final void recordUserAction(OzActions paramOzActions, Bundle paramBundle)
  {
    EsAccount localEsAccount = getAccount();
    if (localEsAccount != null)
      EsAnalytics.recordActionEvent(getActivity(), localEsAccount, paramOzActions, getViewForLogging(), paramBundle);
  }

  public void refresh()
  {
    ImageCache.getInstance(getActivity()).clearFailedRequests();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.HostedFragment
 * JD-Core Version:    0.6.2
 */