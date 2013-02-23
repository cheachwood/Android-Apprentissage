package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.views.Recyclable;
import java.util.ArrayList;

public abstract class EsPeopleListFragment extends EsFragment
  implements AbsListView.OnScrollListener, AbsListView.RecyclerListener, AdapterView.OnItemClickListener
{
  protected ImageCache mAvatarCache;
  private final DataSetObserver mCircleContentObserver = new DataSetObserver()
  {
    public final void onChanged()
    {
      if (EsPeopleListFragment.this.mListView != null)
        EsPeopleListFragment.this.mListView.invalidate();
      EsPeopleListFragment.this.updateView(EsPeopleListFragment.this.getView());
    }
  };
  protected CircleNameResolver mCircleNameResolver;
  private final Handler mHandler = new Handler();
  protected ListView mListView;
  protected Integer mPendingRequestId;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onAddPeopleToCirclesComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      EsPeopleListFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onCircleSyncComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      EsPeopleListFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onDismissSuggestedPeopleRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      EsPeopleListFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onEventManageGuestComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      EsPeopleListFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onRemovePeopleRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      EsPeopleListFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onSetCircleMembershipComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      EsPeopleListFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }
  };

  protected final void addCircleMembership(String paramString1, String paramString2, ArrayList<String> paramArrayList)
  {
    this.mPendingRequestId = EsService.setCircleMembership(getActivity(), getAccount(), paramString1, paramString2, (String[])paramArrayList.toArray(new String[0]), null);
    ProgressFragmentDialog.newInstance(null, getString(EsPeopleData.getMembershipChangeMessageId(paramArrayList, null)), false).show(getFragmentManager(), "req_pending");
  }

  protected EsAccount getAccount()
  {
    return (EsAccount)getActivity().getIntent().getParcelableExtra("account");
  }

  protected abstract ListAdapter getAdapter();

  protected abstract int getEmptyText();

  protected final void handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingRequestId == null) || (paramInt != this.mPendingRequestId.intValue()));
    while (true)
    {
      return;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      this.mPendingRequestId = null;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
    }
  }

  protected abstract View inflateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup);

  protected abstract boolean isError();

  protected abstract boolean isLoaded();

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt2 == -1) && (paramInt1 == 0))
    {
      final String str1 = paramIntent.getStringExtra("person_id");
      final String str2 = paramIntent.getStringExtra("display_name");
      final ArrayList localArrayList = paramIntent.getStringArrayListExtra("selected_circle_ids");
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          EsPeopleListFragment.this.addCircleMembership(str1, str2, localArrayList);
        }
      });
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAvatarCache = ImageCache.getInstance(paramActivity);
    this.mCircleNameResolver = new CircleNameResolver(paramActivity, getLoaderManager(), getAccount());
    this.mCircleNameResolver.registerObserver(this.mCircleContentObserver);
  }

  public void onCreate(Bundle paramBundle)
  {
    if ((paramBundle != null) && (paramBundle.containsKey("request_id")))
      this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
    super.onCreate(paramBundle);
    this.mCircleNameResolver.initLoader();
    onInitLoaders(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = inflateView(paramLayoutInflater, paramViewGroup);
    this.mListView = ((ListView)localView.findViewById(16908298));
    this.mListView.setAdapter(getAdapter());
    this.mListView.setOnScrollListener(this);
    this.mListView.setOnItemClickListener(this);
    this.mListView.setRecyclerListener(this);
    return localView;
  }

  protected abstract void onInitLoaders(Bundle paramBundle);

  public void onMovedToScrapHeap(View paramView)
  {
    if ((paramView instanceof Recyclable))
      ((Recyclable)paramView).onRecycle();
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mPendingRequestId.intValue());
      handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult);
      this.mPendingRequestId = null;
    }
    updateView(getView());
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("request_id", this.mPendingRequestId.intValue());
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    if (paramInt == 2)
      this.mAvatarCache.pause();
    while (true)
    {
      return;
      this.mAvatarCache.resume();
    }
  }

  protected final void showCircleMembershipDialog(String paramString1, String paramString2)
  {
    startActivityForResult(Intents.getCircleMembershipActivityIntent(getActivity(), getAccount(), paramString1, paramString2, false), 0);
  }

  protected void updateView(View paramView)
  {
    int i;
    if ((!isLoaded()) || (!this.mCircleNameResolver.isLoaded()))
    {
      i = 1;
      if (i == 0)
        break label34;
      showEmptyViewProgress(paramView);
    }
    while (true)
    {
      return;
      i = 0;
      break;
      label34: if (isError())
        paramView.findViewById(R.id.server_error).setVisibility(0);
      do
      {
        showContent(paramView);
        break;
        paramView.findViewById(R.id.server_error).setVisibility(8);
      }
      while (!isEmpty());
      ((TextView)paramView.findViewById(R.id.list_empty_text)).setText(getEmptyText());
      showEmptyView(paramView);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EsPeopleListFragment
 * JD-Core Version:    0.6.2
 */