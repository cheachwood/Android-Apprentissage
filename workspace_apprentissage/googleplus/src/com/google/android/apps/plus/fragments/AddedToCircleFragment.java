package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.DbDataAction;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.AddedToCircleLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.PeopleCursorAdapter;
import com.google.android.apps.plus.views.PeopleListItemView;
import com.google.android.apps.plus.views.PeopleListItemView.OnActionButtonClickListener;

public class AddedToCircleFragment extends EsPeopleListFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, PeopleListItemView.OnActionButtonClickListener
{
  private PeopleCursorAdapter mAdapter;
  private boolean mDataLoaded = false;

  protected final ListAdapter getAdapter()
  {
    return this.mAdapter;
  }

  protected final int getEmptyText()
  {
    return R.string.no_added_to_circle_person;
  }

  protected final View inflateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    return paramLayoutInflater.inflate(R.layout.people_list_fragment, paramViewGroup, false);
  }

  protected final boolean isEmpty()
  {
    if ((this.mAdapter == null) || (this.mAdapter.getCursor() == null) || (this.mAdapter.getCursor().getCount() == 0) || (!this.mCircleNameResolver.isLoaded()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean isError()
  {
    if ((this.mDataLoaded) && (this.mAdapter != null) && (this.mAdapter.getCursor() == null));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean isLoaded()
  {
    return this.mDataLoaded;
  }

  public final void onActionButtonClick(PeopleListItemView paramPeopleListItemView, int paramInt)
  {
    if (paramInt == 0)
      showCircleMembershipDialog(paramPeopleListItemView.getPersonId(), null);
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAdapter = new PeopleCursorAdapter(paramActivity, 1, 2, 3, 4, 5, this.mCircleNameResolver);
    this.mAdapter.setShowAddButtonIfNeeded(true);
    this.mAdapter.setOnActionButtonClickListener(this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject = null;
    switch (paramInt)
    {
    default:
    case 0:
    }
    while (true)
    {
      return localObject;
      EsAccount localEsAccount = getAccount();
      localObject = null;
      if (localEsAccount != null)
      {
        FragmentActivity localFragmentActivity = getActivity();
        Bundle localBundle = localFragmentActivity.getIntent().getExtras();
        String str = localBundle.getString("notif_id");
        byte[] arrayOfByte = localBundle.getByteArray("circle_actor_data");
        localObject = new AddedToCircleLoader(localFragmentActivity, getAccount(), str, DbDataAction.deserializeDataActorList(arrayOfByte));
      }
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mAdapter.setAlwaysHideLetterSections(true);
    this.mListView.setFastScrollEnabled(false);
    return localView;
  }

  protected final void onInitLoaders(Bundle paramBundle)
  {
    getLoaderManager().initLoader(0, null, this);
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Cursor localCursor = (Cursor)this.mAdapter.getItem(paramInt);
    if (localCursor == null);
    while (true)
    {
      return;
      String str = localCursor.getString(1);
      startActivity(Intents.getProfileActivityIntent(getActivity(), getAccount(), str, null));
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public static abstract interface AddedToCircleQuery
  {
    public static final String[] PROJECTION = { "_id", "person_id", "gaia_id", "name", "packed_circle_ids", "avatar" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.AddedToCircleFragment
 * JD-Core Version:    0.6.2
 */