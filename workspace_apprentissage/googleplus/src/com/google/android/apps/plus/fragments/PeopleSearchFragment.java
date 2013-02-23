package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.phone.DumpDatabase;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.views.PeopleListItemView;
import com.google.android.apps.plus.views.PeopleListItemView.OnActionButtonClickListener;
import com.google.android.apps.plus.views.SearchViewAdapter;
import com.google.android.apps.plus.views.SearchViewAdapter.OnQueryChangeListener;

public class PeopleSearchFragment extends EsPeopleListFragment
  implements PeopleSearchAdapter.SearchListAdapterListener, Refreshable, PeopleListItemView.OnActionButtonClickListener, SearchViewAdapter.OnQueryChangeListener
{
  private PeopleSearchListAdapter mAdapter;
  private boolean mAddToCirclesActionEnabled;
  private int mCircleUsageType = -1;
  private OnSelectionChangeListener mListener;
  private boolean mPeopleInCirclesEnabled;
  private boolean mPhoneOnlyContactsEnabled;
  private boolean mPlusPagesEnabled;
  private ProgressBar mProgressView;
  private boolean mPublicProfileSearchEnabled;
  private String mQuery;
  private SearchViewAdapter mSearchViewAdapter;

  protected final ListAdapter getAdapter()
  {
    return this.mAdapter;
  }

  protected final int getEmptyText()
  {
    return 0;
  }

  protected final View inflateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    return paramLayoutInflater.inflate(R.layout.people_search_fragment, paramViewGroup, false);
  }

  protected final boolean isEmpty()
  {
    if ((this.mAdapter == null) || (this.mAdapter.isSearchingForFirstResult()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean isError()
  {
    return this.mAdapter.isError();
  }

  protected final boolean isLoaded()
  {
    return this.mAdapter.isLoaded();
  }

  public final void onActionButtonClick(PeopleListItemView paramPeopleListItemView, int paramInt)
  {
  }

  public final void onAddPersonToCirclesAction(String paramString1, String paramString2, boolean paramBoolean)
  {
    showCircleMembershipDialog(paramString1, paramString2);
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAdapter = new PeopleSearchListAdapter(paramActivity, getFragmentManager(), getLoaderManager(), getAccount());
    this.mAdapter.setAddToCirclesActionEnabled(this.mAddToCirclesActionEnabled);
    this.mAdapter.setPublicProfileSearchEnabled(this.mPublicProfileSearchEnabled);
    this.mAdapter.setIncludePhoneNumberContacts(this.mPhoneOnlyContactsEnabled);
    this.mAdapter.setCircleUsageType(this.mCircleUsageType);
    this.mAdapter.setIncludePlusPages(this.mPlusPagesEnabled);
    this.mAdapter.setIncludePeopleInCircles(this.mPeopleInCirclesEnabled);
    this.mAdapter.setShowProgressWhenEmpty(false);
    this.mAdapter.setFilterNullGaiaIds(paramActivity.getIntent().getBooleanExtra("filter_null_gaia_ids", false));
    this.mAdapter.setListener(this);
    this.mAdapter.setQueryString(this.mQuery);
  }

  public final void onChangeCirclesAction(String paramString1, String paramString2)
  {
    showCircleMembershipDialog(paramString1, paramString2);
  }

  public final void onCircleSelected(String paramString, CircleData paramCircleData)
  {
    this.mListener.onCircleSelected(paramString, paramCircleData);
  }

  public final void onCreate(Bundle paramBundle)
  {
    if (paramBundle != null)
      this.mQuery = paramBundle.getString("query");
    super.onCreate(paramBundle);
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mSearchViewAdapter = SearchViewAdapter.createInstance(localView.findViewById(R.id.search_src_text));
    this.mSearchViewAdapter.setQueryHint(R.string.search_people_hint_text);
    this.mSearchViewAdapter.addOnChangeListener(this);
    updateView(localView);
    return localView;
  }

  public final void onDismissSuggestionAction(String paramString1, String paramString2)
  {
  }

  protected final void onInitLoaders(Bundle paramBundle)
  {
    this.mAdapter.onCreate(paramBundle);
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mAdapter.onItemClick(paramInt);
  }

  public final void onPersonSelected(String paramString1, String paramString2, PersonData paramPersonData)
  {
    this.mListener.onPersonSelected(paramString1, paramString2, paramPersonData);
  }

  public final void onQueryClose()
  {
    this.mSearchViewAdapter.setQueryText(null);
    this.mAdapter.setQueryString(null);
  }

  public final void onQueryTextChanged(CharSequence paramCharSequence)
  {
    String str;
    if (paramCharSequence == null)
    {
      str = null;
      this.mQuery = str;
      if ((EsLog.ENABLE_DOGFOOD_FEATURES) && (paramCharSequence != null))
      {
        if (!"*#*#dumpdb*#*#".equals(paramCharSequence.toString()))
          break label70;
        DumpDatabase.dumpNow(getActivity());
      }
    }
    while (true)
    {
      if (this.mAdapter != null)
        this.mAdapter.setQueryString(this.mQuery);
      return;
      str = paramCharSequence.toString().trim();
      break;
      label70: if ("*#*#cleandb*#*#".equals(paramCharSequence.toString()))
        DumpDatabase.cleanNow(getActivity());
    }
  }

  public final void onQueryTextSubmitted(CharSequence paramCharSequence)
  {
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mAdapter != null)
      this.mAdapter.onSaveInstanceState(paramBundle);
    paramBundle.putString("query", this.mQuery);
  }

  public final void onSearchListAdapterStateChange(PeopleSearchAdapter paramPeopleSearchAdapter)
  {
    View localView = getView();
    if (localView != null)
      updateView(localView);
  }

  public final void onStart()
  {
    super.onStart();
    this.mAdapter.onStart();
  }

  public final void onStop()
  {
    super.onStart();
    this.mAdapter.onStop();
  }

  public final void onUnblockPersonAction(String paramString, boolean paramBoolean)
  {
  }

  public final void setAddToCirclesActionEnabled(boolean paramBoolean)
  {
    this.mAddToCirclesActionEnabled = paramBoolean;
    if (this.mAdapter != null)
      this.mAdapter.setAddToCirclesActionEnabled(paramBoolean);
  }

  public final void setCircleUsageType(int paramInt)
  {
    this.mCircleUsageType = paramInt;
    if (this.mAdapter != null)
      this.mAdapter.setCircleUsageType(paramInt);
  }

  public final void setInitialQueryString(String paramString)
  {
    if (this.mQuery == null)
      this.mQuery = paramString;
  }

  public final void setOnSelectionChangeListener(OnSelectionChangeListener paramOnSelectionChangeListener)
  {
    this.mListener = paramOnSelectionChangeListener;
  }

  public final void setPeopleInCirclesEnabled(boolean paramBoolean)
  {
    this.mPeopleInCirclesEnabled = paramBoolean;
    if (this.mAdapter != null)
      this.mAdapter.setIncludePeopleInCircles(paramBoolean);
  }

  public final void setPhoneOnlyContactsEnabled(boolean paramBoolean)
  {
    this.mPhoneOnlyContactsEnabled = paramBoolean;
    if (this.mAdapter != null)
      this.mAdapter.setIncludePhoneNumberContacts(paramBoolean);
  }

  public final void setPlusPagesEnabled(boolean paramBoolean)
  {
    this.mPlusPagesEnabled = paramBoolean;
    if (this.mAdapter != null)
      this.mAdapter.setIncludePlusPages(paramBoolean);
  }

  public final void setProgressBar(ProgressBar paramProgressBar)
  {
    this.mProgressView = paramProgressBar;
    updateSpinner(this.mProgressView);
  }

  public final void setPublicProfileSearchEnabled(boolean paramBoolean)
  {
    this.mPublicProfileSearchEnabled = paramBoolean;
    if (this.mAdapter != null)
      this.mAdapter.setPublicProfileSearchEnabled(paramBoolean);
  }

  public final void startSearch()
  {
    if (this.mSearchViewAdapter != null)
      this.mSearchViewAdapter.setQueryText(this.mQuery);
  }

  protected final void updateView(View paramView)
  {
    if (this.mAdapter == null);
    while (true)
    {
      return;
      if (this.mAdapter.isSearchingForFirstResult())
      {
        paramView.findViewById(16908298).setVisibility(0);
        paramView.findViewById(R.id.shim).setVisibility(0);
        showEmptyViewProgress(paramView);
      }
      else if (!TextUtils.isEmpty(this.mQuery))
      {
        paramView.findViewById(16908298).setVisibility(0);
        paramView.findViewById(R.id.shim).setVisibility(8);
        showContent(paramView);
      }
      else
      {
        paramView.findViewById(16908298).setVisibility(8);
        paramView.findViewById(R.id.shim).setVisibility(8);
        showContent(paramView);
      }
    }
  }

  public static abstract interface OnSelectionChangeListener
  {
    public abstract void onCircleSelected(String paramString, CircleData paramCircleData);

    public abstract void onPersonSelected(String paramString1, String paramString2, PersonData paramPersonData);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PeopleSearchFragment
 * JD-Core Version:    0.6.2
 */