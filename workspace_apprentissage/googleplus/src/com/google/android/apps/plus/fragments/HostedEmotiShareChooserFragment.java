package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.integer;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.DbEmotishareMetadata;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEmotiShareData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.LayoutParams;
import com.google.android.apps.plus.views.ImageResourceView;

public class HostedEmotiShareChooserFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener
{
  private EmotiShareGridViewAdapter mAdapter;
  private Bundle mExtras;
  private ColumnGridView mGridView;
  private View mMainView;
  private DbEmotishareMetadata mSelectedObject;

  private void updateView(View paramView)
  {
    if (paramView == null);
    while (true)
    {
      return;
      Cursor localCursor = this.mAdapter.getCursor();
      if ((localCursor != null) && (localCursor.getCount() > 0));
      for (int i = 1; ; i = 0)
      {
        if (i == 0)
          break label45;
        showContent(paramView);
        break;
      }
      label45: showEmptyViewProgress(paramView);
    }
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.COMPOSE;
  }

  protected final boolean isEmpty()
  {
    if (this.mAdapter == null);
    for (boolean bool = true; ; bool = this.mAdapter.isEmpty())
      return bool;
  }

  public void onClick(View paramView)
  {
    if (paramView == null);
    DbEmotishareMetadata localDbEmotishareMetadata;
    while (true)
    {
      return;
      localDbEmotishareMetadata = (DbEmotishareMetadata)paramView.getTag();
      if (localDbEmotishareMetadata == null)
      {
        Integer localInteger = (Integer)paramView.getTag(R.id.tag_position);
        if (localInteger != null)
          localDbEmotishareMetadata = this.mAdapter.getEmotiShareForItem(localInteger.intValue());
      }
      if (localDbEmotishareMetadata != null)
      {
        FragmentActivity localFragmentActivity = getActivity();
        if (!"android.intent.action.PICK".equals(getActivity().getIntent().getAction()))
          break;
        EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, OzActions.EMOTISHARE_SELECTED, OzViews.COMPOSE, EsAnalytics.addExtrasForLogging(getExtrasForLogging(), localDbEmotishareMetadata));
        Intent localIntent2 = new Intent();
        localIntent2.putExtra("typed_image_embed", localDbEmotishareMetadata);
        localFragmentActivity.setResult(-1, localIntent2);
        localFragmentActivity.finish();
      }
    }
    Intent localIntent1 = Intents.getPostActivityIntent(getActivity(), this.mAccount, localDbEmotishareMetadata);
    if ((getActivity() != null) && (getActivity().getIntent() != null));
    for (AudienceData localAudienceData = (AudienceData)getActivity().getIntent().getParcelableExtra("audience"); ; localAudienceData = null)
    {
      if (localAudienceData != null)
        localIntent1.putExtra("audience", localAudienceData);
      startActivity(localIntent1);
      break;
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mExtras = new Bundle();
      this.mExtras.putAll(paramBundle.getBundle("INTENT"));
    }
    while (true)
    {
      if (this.mExtras.containsKey("typed_image_embed"))
        this.mSelectedObject = ((DbEmotishareMetadata)this.mExtras.getParcelable("typed_image_embed"));
      getLoaderManager().initLoader(1, null, this);
      return;
      this.mExtras = getArguments();
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject = null;
    if (paramInt == 1)
    {
      EsAccount localEsAccount = this.mAccount;
      localObject = null;
      if (localEsAccount != null)
        break label22;
    }
    while (true)
    {
      return localObject;
      label22: localObject = new EsCursorLoader(getSafeContext(), EsProvider.appendAccountParameter(EsProvider.EMOTISHARE_URI, this.mAccount), EsEmotiShareData.EMOTISHARE_PROJECTION, null, null, null)
      {
        public final Cursor esLoadInBackground()
        {
          EsEmotiShareData.ensureSynced(HostedEmotiShareChooserFragment.this.getSafeContext(), HostedEmotiShareChooserFragment.this.mAccount);
          return super.esLoadInBackground();
        }
      };
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = paramLayoutInflater.inflate(R.layout.hosted_emotishare_chooser_view, paramViewGroup, false);
    this.mGridView = ((ColumnGridView)this.mMainView.findViewById(R.id.grid));
    Resources localResources = getSafeContext().getResources();
    int i = localResources.getInteger(R.integer.emotishare_icon_columns);
    int j = localResources.getDimensionPixelOffset(R.dimen.emotishare_item_margin);
    this.mGridView.setColumnCount(i);
    this.mGridView.setItemMargin(j);
    this.mGridView.setPadding(j, j, j, j);
    this.mAdapter = new EmotiShareGridViewAdapter(getActivity(), null, this.mGridView, this);
    this.mGridView.setAdapter(this.mAdapter);
    this.mGridView.setSelector(R.drawable.list_selected_holo);
    if (this.mGridView.isInSelectionMode())
      this.mGridView.endSelectionMode();
    setupEmptyView(this.mMainView, R.string.no_emotishares);
    updateView(this.mMainView);
    return this.mMainView;
  }

  public final void onDestroyView()
  {
    super.onDestroyView();
    this.mGridView.unregisterSelectionListener();
    this.mGridView.setOnScrollListener(null);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onPause()
  {
    super.onPause();
    EmotiShareGridViewAdapter.onPause();
  }

  public final void onResume()
  {
    super.onResume();
    this.mAdapter.onResume();
    updateView(getView());
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mExtras != null)
      paramBundle.putParcelable("INTENT", this.mExtras);
  }

  public final void onStop()
  {
    super.onStop();
    this.mAdapter.onStop();
  }

  private final class EmotiShareGridViewAdapter extends EsCursorAdapter
  {
    private final View.OnClickListener mClickListener;
    private final ColumnGridView mGrid;
    private final boolean mLandscape;

    public EmotiShareGridViewAdapter(Context paramCursor, Cursor paramColumnGridView, ColumnGridView paramOnClickListener, View.OnClickListener arg5)
    {
      super(null);
      int j;
      if (paramCursor.getResources().getConfiguration().orientation == 2)
      {
        j = i;
        this.mLandscape = j;
        if (!this.mLandscape)
          break label71;
      }
      while (true)
      {
        paramOnClickListener.setOrientation(i);
        Object localObject;
        this.mClickListener = localObject;
        this.mGrid = paramOnClickListener;
        return;
        j = 0;
        break;
        label71: i = 2;
      }
    }

    private static DbEmotishareMetadata createEmotiShareFromCursor(Cursor paramCursor)
    {
      DbEmotishareMetadata localDbEmotishareMetadata = null;
      if (paramCursor != null)
      {
        byte[] arrayOfByte = paramCursor.getBlob(2);
        localDbEmotishareMetadata = null;
        if (arrayOfByte != null)
          localDbEmotishareMetadata = DbEmotishareMetadata.deserialize(arrayOfByte);
      }
      return localDbEmotishareMetadata;
    }

    public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      paramView.setOnClickListener(this.mClickListener);
      int i = paramCursor.getPosition();
      int j = R.string.emotishare_in_list_count;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(i + 1);
      arrayOfObject[1] = Integer.valueOf(paramCursor.getCount());
      paramView.setContentDescription(paramContext.getString(j, arrayOfObject));
      paramView.setTag(R.id.tag_position, Integer.valueOf(i));
      DbEmotishareMetadata localDbEmotishareMetadata = createEmotiShareFromCursor(paramCursor);
      ImageResourceView localImageResourceView = (ImageResourceView)paramView.findViewById(R.id.image_view);
      if (localDbEmotishareMetadata != null)
      {
        localImageResourceView.setMediaRef(localDbEmotishareMetadata.getIconRef());
        if ((HostedEmotiShareChooserFragment.this.mSelectedObject != null) && (localDbEmotishareMetadata.getId() == HostedEmotiShareChooserFragment.this.mSelectedObject.getId()))
          paramView.findViewById(R.id.selector_view).setBackgroundResource(R.drawable.list_selected_holo);
      }
      localImageResourceView.setTag(localDbEmotishareMetadata);
      ((TextView)paramView.findViewById(R.id.image_label)).setText(localDbEmotishareMetadata.getName());
      if (this.mLandscape);
      for (int k = 1; ; k = 2)
      {
        paramView.setLayoutParams(new ColumnGridView.LayoutParams(k, -3));
        return;
      }
    }

    public final DbEmotishareMetadata getEmotiShareForItem(int paramInt)
    {
      Cursor localCursor = getCursor();
      if ((localCursor != null) && (localCursor.moveToPosition(paramInt)));
      for (DbEmotishareMetadata localDbEmotishareMetadata = createEmotiShareFromCursor(localCursor); ; localDbEmotishareMetadata = null)
        return localDbEmotishareMetadata;
    }

    public final boolean hasStableIds()
    {
      return false;
    }

    public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      return LayoutInflater.from(paramContext).inflate(R.layout.emotishare_view, null);
    }

    public final void onResume()
    {
      super.onResume();
      if (this.mGrid != null)
      {
        int i = 0;
        int j = this.mGrid.getChildCount();
        while (i < j)
        {
          ((ImageResourceView)this.mGrid.getChildAt(i).findViewById(R.id.image_view)).onResume();
          i++;
        }
        this.mGrid.onResume();
      }
    }

    public final void onStop()
    {
      super.onStop();
      int i = 0;
      int j = this.mGrid.getChildCount();
      while (i < j)
      {
        ((ImageResourceView)this.mGrid.getChildAt(i).findViewById(R.id.image_view)).onStop();
        i++;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedEmotiShareChooserFragment
 * JD-Core Version:    0.6.2
 */