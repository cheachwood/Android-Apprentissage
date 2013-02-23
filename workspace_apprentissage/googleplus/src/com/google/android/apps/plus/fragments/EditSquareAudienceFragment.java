package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.Toast;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.ApiUtils;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.PeopleListItemView;

public class EditSquareAudienceFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener, AdapterView.OnItemClickListener
{
  private static final String[] SQUARES_PROJECTION = { "_id", "square_id", "square_name", "photo_url" };
  private static Bitmap sDefaultSquareImage;
  private EditAudienceAdapter mAdapter;
  private ImageCache mAvatarCache;
  private ListView mListView;
  private boolean mLoaderError;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onGetSquaresComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if ((EditSquareAudienceFragment.this.mNewerReqId == null) || (paramAnonymousInt != EditSquareAudienceFragment.this.mNewerReqId.intValue()));
      while (true)
      {
        return;
        EditSquareAudienceFragment.this.mNewerReqId = null;
        if ((paramAnonymousServiceResult.hasError()) && (!EditSquareAudienceFragment.this.mLoaderError))
          Toast.makeText(EditSquareAudienceFragment.this.getActivity(), EditSquareAudienceFragment.this.getString(R.string.people_list_error), 0).show();
        EditSquareAudienceFragment.this.updateSpinner();
      }
    }
  };
  private boolean mSquaresLoaderActive = true;

  private boolean isLoading()
  {
    if ((this.mAdapter == null) || (this.mAdapter.getCursor() == null));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void updateView(View paramView)
  {
    View localView1 = paramView.findViewById(16908298);
    View localView2 = paramView.findViewById(R.id.server_error);
    if (this.mLoaderError)
    {
      localView1.setVisibility(8);
      localView2.setVisibility(0);
      showContent(paramView);
    }
    while (true)
    {
      return;
      if (isLoading())
      {
        localView1.setVisibility(8);
        localView2.setVisibility(8);
        showEmptyViewProgress(paramView);
      }
      else if (isEmpty())
      {
        localView1.setVisibility(8);
        localView2.setVisibility(8);
        showEmptyView(paramView, getString(R.string.no_squares));
      }
      else
      {
        localView1.setVisibility(0);
        localView2.setVisibility(8);
        showContent(paramView);
      }
    }
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PEOPLE_PICKER;
  }

  protected final boolean isEmpty()
  {
    if ((isLoading()) || (this.mAdapter.isEmpty()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean isProgressIndicatorVisible()
  {
    if ((super.isProgressIndicatorVisible()) || (this.mSquaresLoaderActive));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    FragmentActivity localFragmentActivity = getActivity();
    if ((paramInt1 == 0) && (paramInt2 == -1))
    {
      localFragmentActivity.setResult(paramInt2, paramIntent);
      localFragmentActivity.finish();
    }
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAdapter = new EditAudienceAdapter(paramActivity);
    this.mAvatarCache = ImageCache.getInstance(paramActivity);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getLoaderManager().initLoader(0, null, this);
    if (sDefaultSquareImage == null)
      sDefaultSquareImage = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_community_avatar)).getBitmap();
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default:
    case 0:
    }
    for (Object localObject = null; ; localObject = new SquareListLoader(getActivity(), getAccount(), SQUARES_PROJECTION))
      return localObject;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.edit_audience_fragment, paramViewGroup, false);
    this.mListView = ((ListView)localView.findViewById(16908298));
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
    this.mListView.setOnScrollListener(this);
    return localView;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if ((paramView instanceof PeopleListItemView))
    {
      PeopleListItemView localPeopleListItemView = (PeopleListItemView)paramView;
      String str1 = localPeopleListItemView.getGaiaId();
      String str2 = localPeopleListItemView.getContactName();
      startActivityForResult(Intents.getSelectSquareCategoryActivityIntent(getActivity(), this.mAccount, str2, str1, str2), 0);
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == R.id.refresh)
      refresh();
    for (boolean bool = true; ; bool = super.onOptionsItemSelected(paramMenuItem))
      return bool;
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    paramHostActionBar.showTitle(getActivity().getIntent().getStringExtra("title"));
    paramHostActionBar.showRefreshButton();
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    updateView(getView());
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

  public final void refresh()
  {
    super.refresh();
    if ((this.mNewerReqId == null) && (getActivity() != null))
      this.mNewerReqId = Integer.valueOf(EsService.getSquares(getActivity(), this.mAccount));
    updateSpinner();
  }

  private final class EditAudienceAdapter extends EsCursorAdapter
    implements SectionIndexer
  {
    private EsAlphabetIndexer mIndexer;

    public EditAudienceAdapter(Context arg2)
    {
      super(null);
    }

    public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      PeopleListItemView localPeopleListItemView = (PeopleListItemView)paramView;
      localPeopleListItemView.setGaiaIdAndAvatarUrl(paramCursor.getString(1), ApiUtils.prependProtocol(paramCursor.getString(3)));
      localPeopleListItemView.setContactName(paramCursor.getString(2));
      localPeopleListItemView.updateContentDescription();
    }

    public final void changeCursor(Cursor paramCursor)
    {
      if (paramCursor != null)
        this.mIndexer = new EsAlphabetIndexer(paramCursor, 2);
      super.changeCursor(paramCursor);
    }

    public final int getItemViewType(int paramInt)
    {
      return 0;
    }

    public final int getPositionForSection(int paramInt)
    {
      if (this.mIndexer == null);
      for (int i = 0; ; i = this.mIndexer.getPositionForSection(paramInt))
        return i;
    }

    public final int getSectionForPosition(int paramInt)
    {
      if ((this.mIndexer == null) || (paramInt < 0));
      for (int i = 0; ; i = this.mIndexer.getSectionForPosition(paramInt))
        return i;
    }

    public final Object[] getSections()
    {
      if (this.mIndexer == null);
      for (Object[] arrayOfObject = null; ; arrayOfObject = this.mIndexer.getSections())
        return arrayOfObject;
    }

    public final int getViewTypeCount()
    {
      return 1;
    }

    public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      PeopleListItemView localPeopleListItemView = PeopleListItemView.createInstance(paramContext);
      localPeopleListItemView.setCheckBoxVisible(false);
      localPeopleListItemView.setDefaultAvatar(EditSquareAudienceFragment.sDefaultSquareImage);
      return localPeopleListItemView;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EditSquareAudienceFragment
 * JD-Core Version:    0.6.2
 */