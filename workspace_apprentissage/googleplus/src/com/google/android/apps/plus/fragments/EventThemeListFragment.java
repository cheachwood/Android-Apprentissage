package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.views.EsImageView.OnImageLoadedListener;
import com.google.android.apps.plus.views.EventThemeView;

public class EventThemeListFragment extends EsListFragment<ListView, EsCursorAdapter>
  implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener, Refreshable
{
  private static final String[] EVENT_THEME_COLUMNS = { "_id", "theme_id", "image_url", "placeholder_path" };
  private boolean mDataLoaded;
  private int mFilter;
  private OnThemeSelectedListener mListener;
  private ProgressBar mProgressBarView;

  public EventThemeListFragment()
  {
  }

  public EventThemeListFragment(int paramInt)
  {
    this.mFilter = paramInt;
  }

  private void updateProgressBarVisibility()
  {
    ProgressBar localProgressBar;
    if (this.mProgressBarView != null)
    {
      localProgressBar = this.mProgressBarView;
      if (!this.mDataLoaded)
        break label28;
    }
    label28: for (int i = 8; ; i = 0)
    {
      localProgressBar.setVisibility(i);
      return;
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
      this.mFilter = paramBundle.getInt("filter");
    getLoaderManager().initLoader(0, null, this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    final FragmentActivity localFragmentActivity = getActivity();
    final EsAccount localEsAccount = (EsAccount)getActivity().getIntent().getExtras().get("account");
    switch (paramInt)
    {
    default:
    case 0:
    }
    for (Object localObject = null; ; localObject = new EsCursorLoader(localFragmentActivity)
    {
      public final Cursor esLoadInBackground()
      {
        return EsEventData.getEventThemes(localFragmentActivity, localEsAccount, EventThemeListFragment.this.mFilter, EventThemeListFragment.EVENT_THEME_COLUMNS);
      }
    })
      return localObject;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle, R.layout.event_theme_list_fragment);
    this.mAdapter = new EventThemeListAdapter(getActivity(), null);
    ((ListView)this.mListView).setAdapter(this.mAdapter);
    ((ListView)this.mListView).setOnItemClickListener(this);
    ((ListView)this.mListView).setRecyclerListener(new AbsListView.RecyclerListener()
    {
      public final void onMovedToScrapHeap(View paramAnonymousView)
      {
        ((EventThemeView)paramAnonymousView.findViewById(R.id.image)).onRecycle();
      }
    });
    setupEmptyView(localView, R.string.event_theme_list_empty);
    return localView;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Cursor localCursor = (Cursor)this.mAdapter.getItem(paramInt);
    int i = localCursor.getInt(1);
    String str = localCursor.getString(2);
    if (this.mListener != null)
      this.mListener.onThemeSelected(i, str);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("filter", this.mFilter);
  }

  public final void setOnThemeSelectedListener(OnThemeSelectedListener paramOnThemeSelectedListener)
  {
    this.mListener = paramOnThemeSelectedListener;
  }

  public final void setProgressBar(ProgressBar paramProgressBar)
  {
    this.mProgressBarView = paramProgressBar;
    updateProgressBarVisibility();
  }

  final class EventThemeListAdapter extends EsCursorAdapter
  {
    public EventThemeListAdapter(Context paramCursor, Cursor arg3)
    {
      super(null);
    }

    public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      ProgressBar localProgressBar = (ProgressBar)paramView.findViewById(R.id.progress_bar);
      localProgressBar.setVisibility(0);
      EventThemeView localEventThemeView = (EventThemeView)paramView.findViewById(R.id.image);
      localEventThemeView.setOnImageLoadedListener((EsImageView.OnImageLoadedListener)paramView.getTag());
      String str1 = paramCursor.getString(2);
      String str2 = paramCursor.getString(3);
      if (!TextUtils.isEmpty(str2))
      {
        Uri.Builder localBuilder = new Uri.Builder();
        localBuilder.path(str2);
        localEventThemeView.setDefaultImageUri(localBuilder.build());
        localProgressBar.setVisibility(8);
      }
      localEventThemeView.setImageUrl(str1);
    }

    public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      View localView = LayoutInflater.from(paramContext).inflate(R.layout.event_theme_list_item, paramViewGroup, false);
      localView.setTag(new EsImageView.OnImageLoadedListener()
      {
        public final void onImageLoaded$7ad36aad()
        {
          this.val$progressBar.setVisibility(8);
        }
      });
      return localView;
    }
  }

  public static abstract interface OnThemeSelectedListener
  {
    public abstract void onThemeSelected(int paramInt, String paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EventThemeListFragment
 * JD-Core Version:    0.6.2
 */