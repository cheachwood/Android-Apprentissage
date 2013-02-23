package com.google.android.apps.plus.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.Intents.PhotosIntentBuilder;
import com.google.android.apps.plus.phone.PhotoAlbumsAdapter;
import com.google.android.apps.plus.phone.PhotosHomeGridLoader;
import com.google.android.apps.plus.phone.ProfileActivity;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.HostActionBar;

public class HostedAlbumsFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener
{
  private PhotoAlbumsAdapter mAdapter;
  private String mAuthkey;
  private boolean mControlPrimarySpinner = true;
  private int mCurrentSpinnerPosition;
  private final EsServiceListener mEsListener = new EsServiceListener()
  {
    public final void onGetAlbumListComplete$6a63df5(int paramAnonymousInt)
    {
      if ((HostedAlbumsFragment.this.mOlderReqId != null) && (HostedAlbumsFragment.this.mOlderReqId.intValue() == paramAnonymousInt))
      {
        HostedAlbumsFragment.this.mOlderReqId = null;
        HostedAlbumsFragment.this.updateView(HostedAlbumsFragment.this.getView());
      }
    }

    public final void onPhotosHomeComplete$6a63df5(int paramAnonymousInt)
    {
      if ((HostedAlbumsFragment.this.mOlderReqId != null) && (HostedAlbumsFragment.this.mOlderReqId.intValue() == paramAnonymousInt))
      {
        HostedAlbumsFragment.this.mOlderReqId = null;
        HostedAlbumsFragment.this.updateView(HostedAlbumsFragment.this.getView());
      }
    }
  };
  private Bundle mExtras;
  private String mGaiaId;
  private String mPersonId;
  private boolean mPhotosHome;
  private int mPickerMode;
  private boolean mShowLocalCameraAlbum;
  private String mUserName;

  private void updateView(View paramView)
  {
    Cursor localCursor = this.mAdapter.getCursor();
    int i;
    int j;
    if ((localCursor != null) && (localCursor.getCount() > 0))
    {
      i = 1;
      if ((this.mOlderReqId == null) && (localCursor != null))
        break label61;
      j = 1;
      label37: if ((j == 0) || (i != 0))
        break label67;
      showEmptyViewProgress(paramView);
    }
    while (true)
    {
      updateSpinner();
      return;
      i = 0;
      break;
      label61: j = 0;
      break label37;
      label67: if (i != 0)
        showContent(paramView);
      else
        showEmptyView(paramView, getString(R.string.no_albums));
    }
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.ALBUMS_OF_USER;
  }

  protected final boolean isEmpty()
  {
    return this.mAdapter.isEmpty();
  }

  protected final boolean needsAsyncData()
  {
    return true;
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
    case 1:
    }
    while (true)
    {
      return;
      if (paramInt2 != 0)
      {
        getActivity().setResult(paramInt2, paramIntent);
        getActivity().finish();
      }
    }
  }

  public void onClick(View paramView)
  {
    Cursor localCursor = (Cursor)this.mAdapter.getItem(((Integer)paramView.getTag()).intValue());
    if (localCursor == null);
    while (true)
    {
      return;
      String str1;
      label48: String str2;
      label61: Object localObject;
      label74: String str3;
      label88: String str4;
      label102: String str5;
      label115: int i;
      Intents.PhotosIntentBuilder localPhotosIntentBuilder;
      Integer localInteger1;
      if (localCursor.isNull(8))
      {
        str1 = getResources().getString(R.string.photos_home_unknown_label);
        if (!localCursor.isNull(5))
          break label282;
        str2 = null;
        if (!localCursor.isNull(4))
          break label294;
        localObject = null;
        if (!localCursor.isNull(6))
          break label306;
        str3 = null;
        if (!localCursor.isNull(7))
          break label319;
        str4 = null;
        if (TextUtils.equals((CharSequence)localObject, "photos_of_me"))
          break label332;
        str5 = null;
        i = this.mExtras.getInt("photo_picker_mode", 0);
        localPhotosIntentBuilder = Intents.newPhotosActivityIntentBuilder(getActivity()).setAccount(this.mAccount).setAlbumName(str1).setAlbumId(str2).setGaiaId(str3).setStreamId(str4).setPhotoOfUserId(str5).setAlbumType((String)localObject).setAuthkey(this.mAuthkey);
        if (i == 0)
          break label353;
        if (!this.mExtras.containsKey("photo_picker_crop_mode"))
          break label341;
        localInteger1 = Integer.valueOf(this.mExtras.getInt("photo_picker_crop_mode"));
        label210: if (!this.mExtras.containsKey("photo_picker_title"))
          break label347;
      }
      label282: label294: label306: label319: label332: label341: label347: for (Integer localInteger2 = Integer.valueOf(this.mExtras.getInt("photo_picker_title")); ; localInteger2 = null)
      {
        localPhotosIntentBuilder.setPhotoPickerMode(Integer.valueOf(i)).setPhotoPickerTitleResourceId(localInteger2).setCropMode(localInteger1);
        startActivityForResult(localPhotosIntentBuilder.build(), 1);
        break;
        str1 = localCursor.getString(8);
        break label48;
        str2 = localCursor.getString(5);
        break label61;
        localObject = localCursor.getString(4);
        break label74;
        str3 = localCursor.getString(6);
        break label88;
        str4 = localCursor.getString(7);
        break label102;
        str5 = this.mGaiaId;
        break label115;
        localInteger1 = null;
        break label210;
      }
      label353: startActivity(localPhotosIntentBuilder.build());
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mExtras = new Bundle();
      this.mExtras.putAll(paramBundle.getBundle("com.google.android.apps.plus.PhotosHomeFragment.INTENT"));
      this.mUserName = paramBundle.getString("com.google.android.apps.plus.PhotosHomeFragment.USER_NAME");
    }
    while (true)
    {
      this.mPersonId = this.mExtras.getString("person_id");
      this.mGaiaId = EsPeopleData.extractGaiaId(this.mPersonId);
      this.mPhotosHome = this.mExtras.getBoolean("photos_home", false);
      this.mShowLocalCameraAlbum = this.mExtras.getBoolean("photos_show_camera_album", false);
      this.mPickerMode = this.mExtras.getInt("photo_picker_mode", 0);
      this.mAuthkey = this.mExtras.getString("auth_key");
      if (this.mPickerMode != 0)
        invalidateActionBar();
      return;
      this.mExtras = getArguments();
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new PhotosHomeGridLoader(getActivity(), this.mAccount, this.mGaiaId, this.mUserName, this.mPhotosHome, this.mShowLocalCameraAlbum);
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.photo_home_view, paramViewGroup, false);
    ColumnGridView localColumnGridView = (ColumnGridView)localView.findViewById(R.id.grid);
    ScreenMetrics localScreenMetrics = ScreenMetrics.getInstance(getActivity());
    if (localScreenMetrics.screenDisplayType == 0);
    for (int i = 1; ; i = 2)
    {
      localColumnGridView.setColumnCount(i);
      localColumnGridView.setItemMargin(localScreenMetrics.itemMargin);
      localColumnGridView.setPadding(localScreenMetrics.itemMargin, localScreenMetrics.itemMargin, localScreenMetrics.itemMargin, localScreenMetrics.itemMargin);
      this.mAdapter = new PhotoAlbumsAdapter(getActivity(), null, localColumnGridView, this);
      localColumnGridView.setAdapter(this.mAdapter);
      getLoaderManager().initLoader(0, null, this);
      localColumnGridView.setOnClickListener(this);
      setupEmptyView(localView, R.string.no_albums);
      updateView(localView);
      return localView;
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mEsListener);
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    if (this.mControlPrimarySpinner)
    {
      if (this.mPickerMode != 0)
        break label45;
      ArrayAdapter localArrayAdapter = ProfileActivity.createSpinnerAdapter(getActivity());
      this.mCurrentSpinnerPosition = 1;
      paramHostActionBar.showPrimarySpinner(localArrayAdapter, this.mCurrentSpinnerPosition);
    }
    while (true)
    {
      paramHostActionBar.showRefreshButton();
      paramHostActionBar.showProgressIndicator();
      return;
      label45: paramHostActionBar.showTitle(R.string.photo_picker_photos_home_label);
    }
  }

  public final void onPrimarySpinnerSelectionChange(int paramInt)
  {
    if ((this.mControlPrimarySpinner) && (this.mCurrentSpinnerPosition != paramInt))
      switch (paramInt)
      {
      default:
      case 0:
      }
    while (true)
    {
      this.mCurrentSpinnerPosition = paramInt;
      return;
      startActivity(Intents.getHostedProfileIntent(getActivity(), this.mAccount, this.mPersonId, null, 0));
    }
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mEsListener);
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mExtras != null)
    {
      paramBundle.putParcelable("com.google.android.apps.plus.PhotosHomeFragment.INTENT", this.mExtras);
      paramBundle.putString("com.google.android.apps.plus.PhotosHomeFragment.USER_NAME", this.mUserName);
    }
  }

  public final void refresh()
  {
    super.refresh();
    if (this.mOlderReqId != null)
      return;
    if (this.mPhotosHome);
    for (this.mOlderReqId = Integer.valueOf(EsService.getPhotosHome(getActivity(), this.mAccount, this.mAuthkey)); ; this.mOlderReqId = Integer.valueOf(EsService.getAlbumList(getActivity(), this.mAccount, this.mGaiaId)))
    {
      updateView(getView());
      break;
    }
  }

  public final void relinquishPrimarySpinner()
  {
    this.mControlPrimarySpinner = false;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedAlbumsFragment
 * JD-Core Version:    0.6.2
 */