package com.google.android.apps.plus.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.content.PhotoTaggeeData.PhotoTaggee;
import com.google.android.apps.plus.phone.AlbumGridViewAdapter;
import com.google.android.apps.plus.phone.Pageable;
import com.google.android.apps.plus.phone.Pageable.LoadingListener;
import com.google.android.apps.plus.phone.PhotosSelectionLoader;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.ItemSelectionListener;
import com.google.android.apps.plus.views.ColumnGridView.OnScrollListener;
import com.google.android.apps.plus.views.PhotoAlbumView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PhotosSelectionFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, Pageable.LoadingListener
{
  private AlbumGridViewAdapter mAdapter;
  private PhotoAlbumView mAlbumView;
  private DateFormat mDateFormat = DateFormat.getDateInstance(2);
  private AudienceData mDefaultAudience;
  private ColumnGridView mGridView;
  private boolean mLoaderActive;
  private Map<MediaRef, List<PhotoTaggeeData.PhotoTaggee>> mMediaRefUserMap;
  private List<MediaRef> mMediaRefs;
  private View mNextButton;
  private String mOwnerId;
  private Pageable mPageableLoader;
  private final HashSet<MediaRef> mSelectedMediaRefs = new HashSet();

  private AudienceData createAudienceData()
  {
    AudienceData localAudienceData;
    if ((this.mSelectedMediaRefs == null) || (this.mSelectedMediaRefs.isEmpty()) || (this.mMediaRefUserMap == null) || (this.mMediaRefUserMap.isEmpty()))
      localAudienceData = this.mDefaultAudience;
    while (true)
    {
      return localAudienceData;
      HashSet localHashSet = new HashSet();
      ArrayList localArrayList = new ArrayList();
      Set localSet = this.mMediaRefUserMap.keySet();
      Iterator localIterator1 = this.mSelectedMediaRefs.iterator();
      while (localIterator1.hasNext())
      {
        MediaRef localMediaRef = (MediaRef)localIterator1.next();
        if (localSet.contains(localMediaRef))
        {
          List localList = (List)this.mMediaRefUserMap.get(localMediaRef);
          if ((localList != null) && (!localList.isEmpty()))
          {
            Iterator localIterator2 = localList.iterator();
            while (localIterator2.hasNext())
            {
              PhotoTaggeeData.PhotoTaggee localPhotoTaggee = (PhotoTaggeeData.PhotoTaggee)localIterator2.next();
              String str = localPhotoTaggee.getId();
              if (!localHashSet.contains(str))
              {
                localHashSet.add(str);
                localArrayList.add(new PersonData(str, localPhotoTaggee.getName(), null));
              }
            }
          }
        }
      }
      if (localArrayList.isEmpty())
        localAudienceData = this.mDefaultAudience;
      else
        localAudienceData = new AudienceData(localArrayList, null);
    }
  }

  private void updatePostUI()
  {
    View localView;
    if (this.mNextButton != null)
    {
      localView = this.mNextButton;
      if ((this.mSelectedMediaRefs == null) || (this.mSelectedMediaRefs.size() <= 0))
        break label44;
    }
    label44: for (boolean bool = true; ; bool = false)
    {
      localView.setEnabled(bool);
      this.mNextButton.invalidate();
      return;
    }
  }

  private void updateView(View paramView)
  {
    if (paramView == null)
      return;
    Cursor localCursor = this.mAdapter.getCursor();
    int i;
    if ((localCursor != null) && (localCursor.getCount() > 0))
    {
      i = 1;
      label28: if (i == 0)
        break label49;
      showContent(paramView);
    }
    while (true)
    {
      updateSpinner();
      break;
      i = 0;
      break label28;
      label49: if (this.mLoaderActive)
        showEmptyViewProgress(paramView);
      else
        showEmptyView(paramView, getString(R.string.no_photos));
    }
  }

  public final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PHOTOS_LIST;
  }

  protected final boolean isEmpty()
  {
    if ((this.mAdapter == null) || (this.mAdapter.isEmpty()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected final boolean isProgressIndicatorVisible()
  {
    if ((this.mLoaderActive) || (super.isProgressIndicatorVisible()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle.containsKey("mediarefs"))
    {
      Parcelable[] arrayOfParcelable2 = localBundle.getParcelableArray("mediarefs");
      this.mMediaRefs = new ArrayList(arrayOfParcelable2.length);
      for (int j = 0; j < arrayOfParcelable2.length; j++)
        this.mMediaRefs.add((MediaRef)arrayOfParcelable2[j]);
    }
    if (localBundle.containsKey("owner_id"))
      this.mOwnerId = localBundle.getString("owner_id");
    if (localBundle.containsKey("mediaref_user_map"));
    try
    {
      this.mMediaRefUserMap = ((Map)localBundle.getSerializable("mediaref_user_map"));
      if (localBundle.containsKey("audience"))
        localParcelable = localBundle.getParcelable("audience");
    }
    catch (ClassCastException localClassCastException2)
    {
      try
      {
        Parcelable localParcelable;
        this.mDefaultAudience = ((AudienceData)localParcelable);
        if (paramBundle != null)
        {
          if (!paramBundle.containsKey("SELECTED_ITEMS"))
            return;
          Parcelable[] arrayOfParcelable1 = paramBundle.getParcelableArray("SELECTED_ITEMS");
          int i = 0;
          while (i < arrayOfParcelable1.length)
          {
            this.mSelectedMediaRefs.add((MediaRef)arrayOfParcelable1[i]);
            i++;
            continue;
            localClassCastException2 = localClassCastException2;
            this.mMediaRefUserMap = null;
          }
        }
      }
      catch (ClassCastException localClassCastException1)
      {
        while (true)
          this.mDefaultAudience = null;
        if ((this.mMediaRefUserMap != null) && (!this.mMediaRefUserMap.isEmpty()))
          this.mSelectedMediaRefs.addAll(this.mMediaRefUserMap.keySet());
      }
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    this.mLoaderActive = true;
    PhotosSelectionLoader localPhotosSelectionLoader = new PhotosSelectionLoader(getActivity(), this.mAccount, this.mOwnerId, this.mMediaRefs);
    this.mPageableLoader = ((Pageable)localPhotosSelectionLoader);
    this.mPageableLoader.setLoadingListener(this);
    return localPhotosSelectionLoader;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView1 = paramLayoutInflater.inflate(R.layout.photos_selection_fragment, paramViewGroup, false);
    this.mAlbumView = ((PhotoAlbumView)localView1.findViewById(R.id.album_view));
    this.mGridView = ((ColumnGridView)localView1.findViewById(R.id.grid));
    ScreenMetrics localScreenMetrics = ScreenMetrics.getInstance(getActivity());
    this.mGridView.setItemMargin(localScreenMetrics.itemMargin);
    this.mGridView.setPadding(localScreenMetrics.itemMargin, localScreenMetrics.itemMargin, localScreenMetrics.itemMargin, localScreenMetrics.itemMargin);
    this.mAdapter = new AlbumGridViewAdapter(getActivity(), null, "from_my_phone", this.mGridView, null, null, null);
    this.mAdapter.setSelectedMediaRefs(this.mSelectedMediaRefs);
    this.mGridView.setAdapter(this.mAdapter);
    this.mGridView.setSelector(R.drawable.list_selected_holo);
    getLoaderManager().initLoader(0, null, this);
    this.mGridView.startSelectionMode();
    updateView(this.mAlbumView);
    setupEmptyView(this.mAlbumView, R.string.no_photos);
    this.mGridView.setOnScrollListener(new ColumnGridView.OnScrollListener()
    {
      int mCachedFirstVisibleIndex = -1;

      public final void onScroll(ColumnGridView paramAnonymousColumnGridView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5)
      {
        if ((paramAnonymousInt3 == 0) || (PhotosSelectionFragment.this.mAdapter == null));
        while (true)
        {
          return;
          int i = paramAnonymousInt1 + paramAnonymousInt2;
          if (this.mCachedFirstVisibleIndex != i)
          {
            int j = Math.min(i + paramAnonymousColumnGridView.getColumnCount(), paramAnonymousInt4 - 1);
            long l = PhotosSelectionFragment.this.mAdapter.getTimestampForItem(j);
            PhotosSelectionFragment.this.mAlbumView.setDate(PhotosSelectionFragment.this.mDateFormat.format(Long.valueOf(l)));
            this.mCachedFirstVisibleIndex = i;
          }
        }
      }

      public final void onScrollStateChanged(ColumnGridView paramAnonymousColumnGridView, int paramAnonymousInt)
      {
        int j;
        if (paramAnonymousInt != 0)
        {
          Cursor localCursor = PhotosSelectionFragment.this.mAdapter.getCursor();
          if ((localCursor != null) && (localCursor.getCount() > 0))
          {
            j = 1;
            if (j == 0)
              break label59;
          }
        }
        label59: for (int i = 0; ; i = 8)
        {
          PhotosSelectionFragment.this.mAlbumView.setDateVisibility(i);
          return;
          j = 0;
          break;
        }
      }
    });
    this.mGridView.registerSelectionListener(new ColumnGridView.ItemSelectionListener()
    {
      public final void onItemDeselected(View paramAnonymousView, int paramAnonymousInt)
      {
        MediaRef localMediaRef = null;
        if (paramAnonymousView != null)
          localMediaRef = (MediaRef)paramAnonymousView.getTag();
        if (localMediaRef == null)
          localMediaRef = PhotosSelectionFragment.this.mAdapter.getMediaRefForItem(paramAnonymousInt);
        PhotosSelectionFragment.this.mSelectedMediaRefs.remove(localMediaRef);
        PhotosSelectionFragment.this.updatePostUI();
      }

      public final void onItemSelected(View paramAnonymousView, int paramAnonymousInt)
      {
        MediaRef localMediaRef = null;
        if (paramAnonymousView != null)
          localMediaRef = (MediaRef)paramAnonymousView.getTag();
        if (localMediaRef == null)
        {
          localMediaRef = PhotosSelectionFragment.this.mAdapter.getMediaRefForItem(paramAnonymousInt);
          if (paramAnonymousView != null)
            paramAnonymousView.setTag(localMediaRef);
        }
        PhotosSelectionFragment.this.mSelectedMediaRefs.add(localMediaRef);
        PhotosSelectionFragment.this.updatePostUI();
      }
    });
    this.mAlbumView.enableDateDisplay(true);
    View localView2 = localView1.findViewById(R.id.cancel_button);
    if (localView2 != null)
      localView2.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PhotosSelectionFragment.this.getActivity().finish();
        }
      });
    this.mNextButton = localView1.findViewById(R.id.next_button);
    if (this.mNextButton != null)
      this.mNextButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          PhotosSelectionFragment.access$500(PhotosSelectionFragment.this);
        }
      });
    updatePostUI();
    return localView1;
  }

  public final void onDataSourceLoading(boolean paramBoolean)
  {
    this.mLoaderActive = paramBoolean;
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onPause()
  {
    super.onPause();
    this.mPageableLoader.setLoadingListener(null);
  }

  public final void onResume()
  {
    super.onResume();
    this.mPageableLoader = ((Pageable)getLoaderManager().getLoader(0));
    this.mPageableLoader.setLoadingListener(this);
    if ((this.mLoaderActive) && (!this.mPageableLoader.isDataSourceLoading()))
      this.mLoaderActive = false;
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mSelectedMediaRefs.size() > 0)
    {
      MediaRef[] arrayOfMediaRef = new MediaRef[this.mSelectedMediaRefs.size()];
      this.mSelectedMediaRefs.toArray(arrayOfMediaRef);
      paramBundle.putParcelableArray("SELECTED_ITEMS", arrayOfMediaRef);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PhotosSelectionFragment
 * JD-Core Version:    0.6.2
 */