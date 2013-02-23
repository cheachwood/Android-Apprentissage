package com.google.android.apps.plus.fragments;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.PlusOnePeopleAdapter;
import com.google.android.apps.plus.phone.PlusOnePeopleLoader;

public class PlusOnePeopleFragment extends DialogFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, AdapterView.OnItemClickListener
{
  private EsAccount mAccount;
  private PlusOnePeopleAdapter mAdapter;

  public void onClick(View paramView)
  {
    dismiss();
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
      EsAccount localEsAccount = this.mAccount;
      localObject = null;
      if (localEsAccount != null)
      {
        String str = getArguments().getString("plus_one_id");
        localObject = new PlusOnePeopleLoader(getActivity(), this.mAccount, str);
      }
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.list_layout_acl, paramViewGroup);
    this.mAdapter = new PlusOnePeopleAdapter(getActivity(), null);
    ListView localListView = (ListView)localView.findViewById(16908298);
    localListView.setOnItemClickListener(this);
    localListView.setAdapter(this.mAdapter);
    localView.findViewById(R.id.ok).setOnClickListener(this);
    localView.findViewById(R.id.cancel).setVisibility(8);
    getDialog().setTitle(getString(R.string.plus_one_people_title));
    this.mAccount = ((EsAccount)getArguments().getParcelable("account"));
    getLoaderManager().initLoader(0, null, this);
    localView.findViewById(R.id.list_empty_progress).setVisibility(0);
    return localView;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mAdapter.isExtraPeopleViewIndex(paramInt));
    while (true)
    {
      return;
      Cursor localCursor = (Cursor)this.mAdapter.getItem(paramInt);
      if (localCursor != null)
      {
        String str = localCursor.getString(1);
        startActivity(Intents.getProfileActivityIntent(getActivity(), this.mAccount, str, null));
      }
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public static abstract interface PeopleSetQuery
  {
    public static final String[] PROJECTION = { "_id", "person_id", "gaia_id", "name", "avatar" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PlusOnePeopleFragment
 * JD-Core Version:    0.6.2
 */