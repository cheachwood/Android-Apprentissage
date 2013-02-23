package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.views.CheckableListItemView;
import com.google.android.apps.plus.views.CheckableListItemView.OnItemCheckedChangeListener;
import com.google.android.apps.plus.views.PeopleListItemView;
import java.util.ArrayList;

public class RemovePeopleFragment extends EsPeopleListFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, CheckableListItemView.OnItemCheckedChangeListener
{
  private RemovePeopleCursorAdapter mAdapter;
  private String mCircleId;
  private boolean mDataLoaded;
  private ArrayList<String> mSelectedPersonIds = new ArrayList();

  protected final EsAccount getAccount()
  {
    return (EsAccount)getActivity().getIntent().getExtras().get("account");
  }

  protected final ListAdapter getAdapter()
  {
    return this.mAdapter;
  }

  protected final int getEmptyText()
  {
    return R.string.empty_circle;
  }

  protected final View inflateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    return paramLayoutInflater.inflate(R.layout.people_list_fragment, paramViewGroup, false);
  }

  protected final boolean isEmpty()
  {
    if ((this.mAdapter == null) || (this.mAdapter.getCursor() == null) || (this.mAdapter.getCursor().getCount() == 0));
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

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAdapter = new RemovePeopleCursorAdapter(paramActivity);
  }

  public final void onCreate(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      this.mCircleId = paramBundle.getString("circleId");
      this.mSelectedPersonIds = paramBundle.getStringArrayList("selected_ids");
      if (paramBundle.containsKey("request_id"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
    }
    super.onCreate(paramBundle);
    setHasOptionsMenu(true);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default:
    case 1:
    }
    for (Object localObject = null; ; localObject = new PeopleListLoader(getActivity(), getAccount(), new String[] { "_id", "person_id", "gaia_id", "name", "packed_circle_ids" }, this.mCircleId))
      return localObject;
  }

  protected final void onInitLoaders(Bundle paramBundle)
  {
    getLoaderManager().initLoader(1, null, this);
  }

  public final void onItemCheckedChanged(CheckableListItemView paramCheckableListItemView, boolean paramBoolean)
  {
    String str = ((PeopleListItemView)paramCheckableListItemView).getPersonId();
    if ((paramBoolean) && (!this.mSelectedPersonIds.contains(str)))
      this.mSelectedPersonIds.add(str);
    while (true)
    {
      return;
      if ((!paramBoolean) && (this.mSelectedPersonIds.contains(str)))
        this.mSelectedPersonIds.remove(str);
    }
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if ((Cursor)this.mAdapter.getItem(paramInt) == null);
    while (true)
    {
      return;
      if ((paramView instanceof PeopleListItemView))
        ((PeopleListItemView)paramView).toggle();
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("circleId", this.mCircleId);
    paramBundle.putStringArrayList("selected_ids", this.mSelectedPersonIds);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("request_id", this.mPendingRequestId.intValue());
  }

  private final class RemovePeopleCursorAdapter extends CursorAdapter
  {
    public RemovePeopleCursorAdapter(Context arg2)
    {
      super(null, 0);
    }

    public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      PeopleListItemView localPeopleListItemView = (PeopleListItemView)paramView;
      localPeopleListItemView.setCircleNameResolver(RemovePeopleFragment.this.mCircleNameResolver);
      String str = paramCursor.getString(1);
      localPeopleListItemView.setPersonId(str);
      localPeopleListItemView.setGaiaId(paramCursor.getString(2));
      localPeopleListItemView.setContactName(paramCursor.getString(3));
      localPeopleListItemView.setPackedCircleIds(paramCursor.getString(4));
      localPeopleListItemView.setCheckBoxVisible(true);
      localPeopleListItemView.setOnItemCheckedChangeListener(RemovePeopleFragment.this);
      localPeopleListItemView.setChecked(RemovePeopleFragment.this.mSelectedPersonIds.contains(str));
      localPeopleListItemView.updateContentDescription();
    }

    public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      return PeopleListItemView.createInstance(paramContext);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.RemovePeopleFragment
 * JD-Core Version:    0.6.2
 */