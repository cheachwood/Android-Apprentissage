package com.google.android.apps.plus.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.service.EsService;

public abstract class EsFragment extends Fragment
{
  private final Handler mHandler = new Handler()
  {
    public final void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 0)
        EsFragment.this.doShowEmptyViewProgressDelayed();
    }
  };
  protected Integer mNewerReqId;
  protected Integer mOlderReqId;
  private boolean mPaused;
  private boolean mRestoredFragment;

  private void doShowEmptyViewProgress(View paramView)
  {
    if (isEmpty())
    {
      View localView = paramView.findViewById(16908292);
      localView.setVisibility(0);
      localView.findViewById(R.id.list_empty_text).setVisibility(8);
      localView.findViewById(R.id.list_empty_progress).setVisibility(0);
    }
  }

  private void removeProgressViewMessages()
  {
    this.mHandler.removeMessages(0);
  }

  protected static void setupEmptyView(View paramView, int paramInt)
  {
    ((TextView)paramView.findViewById(R.id.list_empty_text)).setText(paramInt);
  }

  protected final void doShowEmptyViewProgressDelayed()
  {
    if ((isAdded()) && (!this.mPaused))
    {
      View localView = getView();
      if (localView != null)
        doShowEmptyViewProgress(localView);
    }
  }

  protected abstract boolean isEmpty();

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mRestoredFragment = true;
      if (paramBundle.containsKey("n_pending_req"))
        this.mNewerReqId = Integer.valueOf(paramBundle.getInt("n_pending_req"));
      if (paramBundle.containsKey("o_pending_req"))
        this.mOlderReqId = Integer.valueOf(paramBundle.getInt("o_pending_req"));
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle, int paramInt)
  {
    return paramLayoutInflater.inflate(paramInt, paramViewGroup, false);
  }

  public void onPause()
  {
    super.onPause();
    this.mPaused = true;
  }

  public void onResume()
  {
    super.onResume();
    Integer localInteger = this.mNewerReqId;
    int i = 0;
    if (localInteger != null)
    {
      if (!EsService.isRequestPending(this.mNewerReqId.intValue()))
        break label128;
      boolean bool = isEmpty();
      i = 0;
      if (bool)
        showEmptyViewProgress(getView());
    }
    if (this.mOlderReqId != null)
    {
      if (!EsService.isRequestPending(this.mOlderReqId.intValue()))
        break label138;
      if (isEmpty())
        showEmptyViewProgress(getView());
    }
    while (true)
    {
      if ((i != 0) && (this.mNewerReqId == null) && (this.mOlderReqId == null))
      {
        getView();
        if (isEmpty())
          showEmptyView(getView());
      }
      this.mPaused = false;
      return;
      label128: this.mNewerReqId = null;
      i = 1;
      break;
      label138: this.mOlderReqId = null;
      i = 1;
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mNewerReqId != null)
      paramBundle.putInt("n_pending_req", this.mNewerReqId.intValue());
    if (this.mOlderReqId != null)
      paramBundle.putInt("o_pending_req", this.mOlderReqId.intValue());
  }

  protected final void showContent(View paramView)
  {
    removeProgressViewMessages();
    paramView.findViewById(16908292).setVisibility(8);
  }

  protected final void showEmptyView(View paramView)
  {
    removeProgressViewMessages();
    if (isEmpty())
    {
      View localView = paramView.findViewById(16908292);
      localView.setVisibility(0);
      localView.findViewById(R.id.list_empty_text).setVisibility(0);
      localView.findViewById(R.id.list_empty_progress).setVisibility(8);
    }
  }

  protected final void showEmptyViewProgress(View paramView)
  {
    if (this.mRestoredFragment)
      if ((!this.mHandler.hasMessages(0)) && (isEmpty()))
        this.mHandler.sendEmptyMessageDelayed(0, 800L);
    while (true)
    {
      return;
      doShowEmptyViewProgress(paramView);
    }
  }

  protected final void showEmptyViewProgress(View paramView, String paramString)
  {
    if (isEmpty())
    {
      ((TextView)paramView.findViewById(R.id.list_empty_progress_text)).setText(paramString);
      showEmptyViewProgress(paramView);
    }
  }

  protected void updateSpinner(ProgressBar paramProgressBar)
  {
    if (paramProgressBar == null)
      return;
    if ((this.mNewerReqId == null) && (this.mOlderReqId == null));
    for (int i = 8; ; i = 0)
    {
      paramProgressBar.setVisibility(i);
      break;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EsFragment
 * JD-Core Version:    0.6.2
 */