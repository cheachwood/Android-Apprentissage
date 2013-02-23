package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.apps.plus.R.array;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsNetworkData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.views.BarGraphView;

public class NetworkStatisticsFragment extends EsFragment
  implements DialogInterface.OnClickListener, LoaderManager.LoaderCallbacks<Cursor>
{
  protected static final String[] sSortColumns = arrayOfString;
  protected static final int[][] sValueColumns = { { 2, 3 }, { 2 }, { 3 }, { 4, 5 }, { 4 }, { 5 }, { 6 } };
  protected EsAccount mAccount;
  protected BarGraphView mBarGraphView;
  protected int mPendingViewType;
  protected int mViewType = 0;

  static
  {
    String[] arrayOfString = new String[7];
    arrayOfString[0] = ("(" + NetworkStatisticsQuery.PROJECTION[2] + "+" + NetworkStatisticsQuery.PROJECTION[3] + ")");
    arrayOfString[1] = NetworkStatisticsQuery.PROJECTION[2];
    arrayOfString[2] = NetworkStatisticsQuery.PROJECTION[3];
    arrayOfString[3] = ("(" + NetworkStatisticsQuery.PROJECTION[4] + "+" + NetworkStatisticsQuery.PROJECTION[5] + ")");
    arrayOfString[4] = NetworkStatisticsQuery.PROJECTION[4];
    arrayOfString[5] = NetworkStatisticsQuery.PROJECTION[5];
    arrayOfString[6] = NetworkStatisticsQuery.PROJECTION[6];
  }

  private void updateTitle(EsFragmentActivity paramEsFragmentActivity)
  {
    CharSequence[] arrayOfCharSequence = getResources().getTextArray(R.array.network_statistics_types);
    if (Build.VERSION.SDK_INT >= 11)
      paramEsFragmentActivity.setTitle(arrayOfCharSequence[this.mViewType]);
    while (true)
    {
      return;
      paramEsFragmentActivity.setTitlebarTitle((String)arrayOfCharSequence[this.mViewType]);
    }
  }

  protected final boolean isEmpty()
  {
    return false;
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
      this.mPendingViewType = paramInt;
    case -1:
    case -2:
    }
    while (true)
    {
      return;
      if (this.mPendingViewType != this.mViewType)
      {
        this.mViewType = this.mPendingViewType;
        getLoaderManager().restartLoader(0, null, this);
        updateTitle((EsFragmentActivity)getActivity());
      }
      paramDialogInterface.dismiss();
      continue;
      paramDialogInterface.dismiss();
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if ((paramBundle != null) && (paramBundle.containsKey("view_type")))
    {
      this.mViewType = paramBundle.getInt("view_type");
      this.mPendingViewType = this.mViewType;
    }
    this.mAccount = ((EsAccount)getActivity().getIntent().getParcelableExtra("account"));
    setHasOptionsMenu(true);
    getLoaderManager().initLoader(0, null, this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default:
    case 0:
    }
    Uri localUri;
    for (Object localObject = null; ; localObject = new EsCursorLoader(getActivity(), localUri, NetworkStatisticsQuery.PROJECTION, null, null, sSortColumns[this.mViewType] + " DESC"))
    {
      return localObject;
      localUri = EsProvider.appendAccountParameter(EsProvider.NETWORK_DATA_STATS_URI, this.mAccount);
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.network_statistics_fragment, paramViewGroup, false);
    this.mBarGraphView = ((BarGraphView)localView.findViewById(R.id.bar_graph));
    return localView;
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onMenuItemSelected(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getItemId();
    if (i == R.id.customize)
    {
      this.mPendingViewType = this.mViewType;
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
      localBuilder.setTitle(R.string.menu_network_customize);
      localBuilder.setSingleChoiceItems(R.array.network_statistics_types, this.mViewType, this);
      localBuilder.setPositiveButton(R.string.ok, this);
      localBuilder.setNegativeButton(R.string.cancel, this);
      localBuilder.setCancelable(true);
      localBuilder.show();
    }
    while (true)
    {
      return;
      if (i == R.id.clear)
      {
        EsNetworkData.resetStatsData(getActivity(), this.mAccount);
        getLoaderManager().restartLoader(0, null, this);
      }
    }
  }

  public final void onResume()
  {
    super.onResume();
    updateTitle((EsFragmentActivity)getActivity());
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("view_type", this.mViewType);
  }

  public static abstract interface NetworkStatisticsQuery
  {
    public static final String[] PROJECTION = { "_id", "name", "sent", "recv", "network_duration", "process_duration", "req_count" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.NetworkStatisticsFragment
 * JD-Core Version:    0.6.2
 */