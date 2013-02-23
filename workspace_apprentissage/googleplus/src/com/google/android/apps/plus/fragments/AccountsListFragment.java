package com.google.android.apps.plus.fragments;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.phone.AccountsAdapter;
import com.google.android.apps.plus.phone.BaseAccountSelectionActivity.AccountsAdder;
import com.google.android.apps.plus.util.AccountsUtil;
import java.util.Collections;
import java.util.List;

public class AccountsListFragment extends EsFragment
  implements LoaderManager.LoaderCallbacks<List<Account>>, AdapterView.OnItemClickListener
{
  private BaseAccountSelectionActivity.AccountsAdder mAccountsAdder;
  private AccountsAdapter mAdapter;
  private boolean mAddAccountShown;
  private ListView mListView;

  protected final boolean isEmpty()
  {
    if ((this.mAdapter == null) || (this.mAdapter.isEmpty()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
      this.mAddAccountShown = paramBundle.getBoolean("add_account_shown");
  }

  public final Loader<List<Account>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new AccountsLoader(getActivity());
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle, R.layout.list_layout);
    this.mListView = ((ListView)localView.findViewById(16908298));
    this.mListView.setOnItemClickListener(this);
    this.mAdapter = new AccountsAdapter(getActivity());
    this.mListView.setAdapter(this.mAdapter);
    return localView;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (paramInt == -1 + paramAdapterView.getCount())
      AccountsUtil.addAccount(getActivity());
    while (true)
    {
      return;
      String str = (String)this.mListView.getItemAtPosition(paramInt);
      if (this.mAccountsAdder != null)
        this.mAccountsAdder.addAccount(str);
    }
  }

  public final void onLoaderReset(Loader<List<Account>> paramLoader)
  {
  }

  public final void onResume()
  {
    super.onResume();
    getLoaderManager().restartLoader(0, null, this);
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("add_account_shown", this.mAddAccountShown);
  }

  public final void setAccountsAdder(BaseAccountSelectionActivity.AccountsAdder paramAccountsAdder)
  {
    this.mAccountsAdder = paramAccountsAdder;
  }

  public final void showList()
  {
    View localView = getView();
    if (this.mListView.getAdapter().getCount() > 0)
      localView.findViewById(16908292).setVisibility(8);
    while (true)
    {
      this.mListView.setVisibility(0);
      return;
      localView.findViewById(16908292).setVisibility(0);
    }
  }

  private static final class AccountsLoader extends AsyncTaskLoader<List<Account>>
  {
    public AccountsLoader(Context paramContext)
    {
      super();
    }

    private List<Account> loadInBackground()
    {
      try
      {
        List localList2 = AccountsUtil.getAccounts(getContext());
        localList1 = localList2;
        return localList1;
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          List localList1 = Collections.emptyList();
        }
      }
    }

    protected final void onStartLoading()
    {
      forceLoad();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.AccountsListFragment
 * JD-Core Version:    0.6.2
 */