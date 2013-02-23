package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;

public class BlockFragment extends ProgressFragmentDialog
{
  private EsAccount mAccount;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onSetBlockedRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      BlockFragment.this.handleSetBlockedCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }
  };
  private int mSetBlockedRequestId;

  public static BlockFragment getInstance(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean2)
    {
      if (paramBoolean1);
      for (i = R.string.block_page_operation_pending; ; i = R.string.block_person_operation_pending)
      {
        BlockFragment localBlockFragment = new BlockFragment();
        String str = paramContext.getResources().getString(i);
        Bundle localBundle = new Bundle();
        localBundle.putString("message", str);
        localBlockFragment.setArguments(localBundle);
        localBlockFragment.setCancelable(false);
        localBlockFragment.mAccount = paramEsAccount;
        localBlockFragment.mSetBlockedRequestId = EsService.setPersonBlocked(paramContext, paramEsAccount, paramString1, paramString2, paramBoolean2).intValue();
        return localBlockFragment;
      }
    }
    if (paramBoolean1);
    for (int i = R.string.unblock_page_operation_pending; ; i = R.string.unblock_person_operation_pending)
      break;
  }

  protected final void handleSetBlockedCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if (this.mSetBlockedRequestId != paramInt);
    while (true)
    {
      return;
      dismiss();
      Listener localListener;
      if ((getTargetFragment() instanceof Listener))
        localListener = (Listener)getTargetFragment();
      while (true)
      {
        if (localListener == null)
          break label94;
        if ((paramServiceResult == null) || (!paramServiceResult.hasError()))
          break label96;
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
        localListener.onBlockCompleted(false);
        break;
        if ((getActivity() instanceof Listener))
          localListener = (Listener)getActivity();
        else
          localListener = null;
      }
      label94: continue;
      label96: localListener.onBlockCompleted(true);
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if ((paramBundle != null) && (paramBundle.containsKey("set_blocked_req_id")))
    {
      this.mSetBlockedRequestId = paramBundle.getInt("set_blocked_req_id");
      this.mAccount = ((EsAccount)paramBundle.getParcelable("set_account"));
    }
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public final void onResume()
  {
    super.onResume();
    EsAccount localEsAccount = this.mAccount;
    int i = 0;
    if (localEsAccount != null)
    {
      if (this.mAccount.equals(EsService.getActiveAccount(getActivity())))
        break label118;
      boolean bool = EsLog.isLoggable("BlockFragment", 6);
      i = 0;
      if (bool)
        Log.e("BlockFragment", "Activity finished because it is associated with a signed-out account: " + getActivity().getClass().getName());
    }
    if (i != 0)
    {
      EsService.registerListener(this.mServiceListener);
      if (!EsService.isRequestPending(this.mSetBlockedRequestId))
      {
        ServiceResult localServiceResult = EsService.removeResult(this.mSetBlockedRequestId);
        handleSetBlockedCallback(this.mSetBlockedRequestId, localServiceResult);
      }
    }
    while (true)
    {
      return;
      label118: i = 1;
      break;
      getActivity().finish();
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("set_blocked_req_id", this.mSetBlockedRequestId);
    paramBundle.putParcelable("set_account", this.mAccount);
  }

  public final void show(FragmentActivity paramFragmentActivity)
  {
    show(paramFragmentActivity.getSupportFragmentManager(), "block_pending");
  }

  public static abstract interface Listener
  {
    public abstract void onBlockCompleted(boolean paramBoolean);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.BlockFragment
 * JD-Core Version:    0.6.2
 */