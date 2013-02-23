package com.google.android.apps.plus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.HostedFragment;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.views.HostActionBar;

public abstract class HostedEsFragment extends HostedFragment
{
  protected EsAccount mAccount;
  private final Handler mHandler = new Handler()
  {
    public final void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 0)
        HostedEsFragment.this.doShowEmptyViewProgressDelayed();
    }
  };
  protected Integer mNewerReqId;
  protected Integer mOlderReqId;
  private boolean mRestoredFragment;

  protected static void setupEmptyView(View paramView, int paramInt)
  {
    ((TextView)paramView.findViewById(R.id.list_empty_text)).setText(paramInt);
  }

  protected void doShowEmptyView(View paramView, String paramString)
  {
    if (isEmpty())
    {
      View localView = paramView.findViewById(16908292);
      localView.setVisibility(0);
      TextView localTextView = (TextView)localView.findViewById(R.id.list_empty_text);
      localTextView.setText(paramString);
      localTextView.setVisibility(0);
      localView.findViewById(R.id.list_empty_progress).setVisibility(8);
    }
  }

  protected void doShowEmptyViewProgress(View paramView)
  {
    if (isEmpty())
    {
      View localView = paramView.findViewById(16908292);
      localView.setVisibility(0);
      localView.findViewById(R.id.list_empty_text).setVisibility(8);
      localView.findViewById(R.id.list_empty_progress).setVisibility(0);
    }
  }

  protected final void doShowEmptyViewProgressDelayed()
  {
    if ((isAdded()) && (!isPaused()))
    {
      View localView = getView();
      if (localView != null)
        doShowEmptyViewProgress(localView);
    }
  }

  public EsAccount getAccount()
  {
    return this.mAccount;
  }

  protected abstract boolean isEmpty();

  protected boolean isProgressIndicatorVisible()
  {
    if ((this.mNewerReqId != null) || (this.mOlderReqId != null));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

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

  public void onResume()
  {
    super.onResume();
    Integer localInteger = this.mNewerReqId;
    int i = 0;
    if (localInteger != null)
    {
      if (!EsService.isRequestPending(this.mNewerReqId.intValue()))
        break label125;
      boolean bool = isEmpty();
      i = 0;
      if (bool)
        showEmptyViewProgress(getView());
    }
    if (this.mOlderReqId != null)
    {
      if (!EsService.isRequestPending(this.mOlderReqId.intValue()))
        break label135;
      if (isEmpty())
        showEmptyViewProgress(getView());
    }
    while (true)
    {
      if ((i != 0) && (this.mNewerReqId == null) && (this.mOlderReqId == null))
      {
        onResumeContentFetched(getView());
        if (isEmpty())
          showEmptyView(getView(), null);
      }
      return;
      label125: this.mNewerReqId = null;
      i = 1;
      break;
      label135: this.mOlderReqId = null;
      i = 1;
    }
  }

  protected void onResumeContentFetched(View paramView)
  {
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mNewerReqId != null)
      paramBundle.putInt("n_pending_req", this.mNewerReqId.intValue());
    if (this.mOlderReqId != null)
      paramBundle.putInt("o_pending_req", this.mOlderReqId.intValue());
  }

  protected void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mAccount = ((EsAccount)paramBundle.getParcelable("account"));
  }

  protected final void removeProgressViewMessages()
  {
    this.mHandler.removeMessages(0);
  }

  protected void showContent(View paramView)
  {
    removeProgressViewMessages();
    paramView.findViewById(16908292).setVisibility(8);
  }

  protected void showEmptyView(View paramView, String paramString)
  {
    removeProgressViewMessages();
    doShowEmptyView(paramView, paramString);
  }

  protected void showEmptyViewProgress(View paramView)
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

  protected void showEmptyViewProgress(View paramView, String paramString)
  {
    if (isEmpty())
    {
      ((TextView)paramView.findViewById(R.id.list_empty_progress_text)).setText(paramString);
      showEmptyViewProgress(paramView);
    }
  }

  public final void startExternalActivity(Intent paramIntent)
  {
    paramIntent.addFlags(524288);
    startActivity(paramIntent);
  }

  protected void updateSpinner()
  {
    HostActionBar localHostActionBar = getActionBar();
    if (localHostActionBar != null)
    {
      if (!isProgressIndicatorVisible())
        break label21;
      localHostActionBar.showProgressIndicator();
    }
    while (true)
    {
      return;
      label21: localHostActionBar.hideProgressIndicator();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedEsFragment
 * JD-Core Version:    0.6.2
 */