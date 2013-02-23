package com.google.android.apps.plus.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.CircleMembershipManager;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.views.SuggestionGridView;
import com.google.android.apps.plus.views.SuggestionGridView.ScrollPositions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SuggestedPeopleFragment extends EsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, SuggestionGridAdapter.SuggestionGridAdapterListener
{
  private static final String[] CIRCLES_PROJECTION = { "circle_id", "circle_name", "contact_count", "type", "semantic_hints" };
  private CircleSpinnerAdapter mCircleSpinnerAdapter;
  private Cursor mCirclesCursor;
  private boolean mDataLoaded;
  private final Handler mHandler = new Handler();
  private Integer mPendingRequestId;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onSetCircleMembershipComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      SuggestedPeopleFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }
  };
  private SuggestionGridAdapter mSuggestionAdapter;
  private SuggestionGridView mSuggestionGridView;
  private SuggestionGridView.ScrollPositions mSuggestionScrollPositions;
  private ScrollView mSuggestionScrollView;

  private EsAccount getAccount()
  {
    return (EsAccount)getActivity().getIntent().getExtras().get("account");
  }

  private void showCircleMembershipDialog(String paramString1, String paramString2)
  {
    startActivityForResult(Intents.getCircleMembershipActivityIntent(getActivity(), getAccount(), paramString1, paramString2, true), 0);
  }

  private void updateView(View paramView)
  {
    if (!this.mDataLoaded)
    {
      this.mSuggestionScrollView.setVisibility(8);
      showEmptyViewProgress(paramView, getString(R.string.loading_friend_suggestions));
    }
    while (true)
    {
      return;
      this.mSuggestionScrollView.setVisibility(0);
      showContent(paramView);
    }
  }

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

  protected final boolean isEmpty()
  {
    if ((!this.mDataLoaded) || (this.mCirclesCursor == null));
    for (boolean bool = true; ; bool = this.mSuggestionAdapter.isEmpty())
      return bool;
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt2 == -1) && (paramInt1 == 0))
    {
      final String str1 = paramIntent.getStringExtra("person_id");
      final String str2 = paramIntent.getStringExtra("display_name");
      final ArrayList localArrayList1 = paramIntent.getExtras().getStringArrayList("original_circle_ids");
      final ArrayList localArrayList2 = paramIntent.getExtras().getStringArrayList("selected_circle_ids");
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          SuggestedPeopleFragment.this.setCircleMembership(str1, str2, localArrayList1, localArrayList2);
        }
      });
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public final void onAddPersonToCirclesAction(String paramString1, String paramString2, boolean paramBoolean)
  {
    String str = EsPeopleData.getDefaultCircleId(getActivity(), this.mCirclesCursor, paramBoolean);
    if (str == null)
      showCircleMembershipDialog(paramString1, paramString2);
    while (true)
    {
      return;
      EsService.addPersonToCircle(getActivity(), getAccount(), paramString1, paramString2, str);
    }
  }

  public final void onChangeCirclesAction(String paramString1, String paramString2)
  {
    showCircleMembershipDialog(paramString1, paramString2);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("request_id"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
      this.mSuggestionScrollPositions = ((SuggestionGridView.ScrollPositions)paramBundle.getParcelable("scrollPositions"));
    }
    this.mCircleSpinnerAdapter = new CircleSpinnerAdapter(getActivity());
    this.mCircleSpinnerAdapter.setNotifyOnChange(false);
    LoaderManager localLoaderManager = getLoaderManager();
    this.mSuggestionAdapter = new SuggestionGridAdapter(getActivity(), localLoaderManager, getAccount(), 3);
    this.mSuggestionAdapter.setCircleSpinnerAdapter(this.mCircleSpinnerAdapter);
    this.mSuggestionAdapter.setListener(this);
    getLoaderManager().initLoader(1, null, this);
    getLoaderManager().initLoader(2, null, this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 1:
    case 2:
    }
    while (true)
    {
      return localObject;
      localObject = new CircleListLoader(getActivity(), getAccount(), 1, CIRCLES_PROJECTION);
      continue;
      localObject = new SuggestedPeopleListLoader(getActivity(), getAccount(), SuggestionGridAdapter.PROJECTION, true);
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.oob_suggested_people_fragment, paramViewGroup, false);
    this.mSuggestionScrollView = ((ScrollView)localView.findViewById(R.id.suggestion_scroll_view));
    this.mSuggestionGridView = ((SuggestionGridView)localView.findViewById(R.id.suggestion_grid));
    this.mSuggestionGridView.setAdapter(this.mSuggestionAdapter);
    return localView;
  }

  public final void onDismissSuggestionAction(String paramString1, String paramString2)
  {
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(paramString1);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(paramString2);
    EsService.dismissSuggestedPeople(getActivity(), getAccount(), "SIGNUP", localArrayList1, localArrayList2);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
    CircleMembershipManager.onPeopleListVisibilityChange(false);
  }

  public final void onPersonSelected(String paramString)
  {
    startActivity(Intents.getProfileActivityIntent(getActivity(), getAccount(), paramString, null));
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    CircleMembershipManager.onPeopleListVisibilityChange(true);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mPendingRequestId.intValue());
      handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult);
      this.mPendingRequestId = null;
    }
    updateView(getView());
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("request_id", this.mPendingRequestId.intValue());
    if (this.mSuggestionGridView != null)
      paramBundle.putParcelable("scrollPositions", this.mSuggestionGridView.getScrollPositions());
  }

  public final void onStart()
  {
    super.onStart();
    this.mSuggestionAdapter.onStart();
  }

  protected final void setCircleMembership(String paramString1, String paramString2, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2)
  {
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator1 = paramArrayList2.iterator();
    while (localIterator1.hasNext())
    {
      String str2 = (String)localIterator1.next();
      if (!paramArrayList1.contains(str2))
        localArrayList1.add(str2);
    }
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator2 = paramArrayList1.iterator();
    while (localIterator2.hasNext())
    {
      String str1 = (String)localIterator2.next();
      if (!paramArrayList2.contains(str1))
        localArrayList2.add(str1);
    }
    ProgressFragmentDialog.newInstance(null, getString(EsPeopleData.getMembershipChangeMessageId(localArrayList1, localArrayList2)), false).show(getFragmentManager(), "req_pending");
    this.mPendingRequestId = EsService.setCircleMembership(getActivity(), getAccount(), paramString1, paramString2, (String[])localArrayList1.toArray(new String[localArrayList1.size()]), (String[])localArrayList2.toArray(new String[localArrayList2.size()]));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SuggestedPeopleFragment
 * JD-Core Version:    0.6.2
 */