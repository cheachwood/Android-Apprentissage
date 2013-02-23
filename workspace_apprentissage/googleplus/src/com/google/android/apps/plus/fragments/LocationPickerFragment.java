package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.LocationQuery;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.LocationController;
import com.google.android.apps.plus.phone.PlacesAdapter;
import com.google.android.apps.plus.phone.PlacesAdapter.LocationQuery;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.ImageResourceView;
import com.google.android.apps.plus.views.SearchViewAdapter;
import com.google.android.apps.plus.views.SearchViewAdapter.OnQueryChangeListener;

public class LocationPickerFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener, AdapterView.OnItemClickListener, TextView.OnEditorActionListener, SearchViewAdapter.OnQueryChangeListener
{
  private static final Object ITEM_KEEP_LOCATION = new Object();
  private PlacesAdapter mCurrentAdapter;
  private DbLocation mCurrentLocation;
  private String mCurrentMapUrl;
  private boolean mIsLandscapeMode;
  protected ListView mListView;
  private boolean mLoadPlacesNeeded;
  private boolean mLoadSearchNeeded;
  private LocationController mLocationController;
  private CheckinLocationListener mLocationListener = new CheckinLocationListener((byte)0);
  private LocationQuery mLocationQuery;
  private ImageResourceView mMapView;
  private PlacesAdapter mPlacesAdapter;
  private int mPrevScrollItemCount = -1;
  private int mPrevScrollPosition = -1;
  private String mQuery;
  private int mScrollOffset;
  private int mScrollPos;
  private PlacesAdapter mSearchAdapter;
  private boolean mSearchMode;
  private final EsServiceListener mServiceListener = new ServiceListener((byte)0);

  private String createStaticMapUrl(Location paramLocation, boolean paramBoolean)
  {
    int i = getResources().getDimensionPixelSize(R.dimen.location_picker_map_size);
    int j = Math.max(ScreenMetrics.getInstance(getActivity()).shortDimension, i);
    Uri.Builder localBuilder1 = Uri.parse("https://maps.googleapis.com/maps/api/staticmap").buildUpon();
    Uri.Builder localBuilder2 = localBuilder1.appendQueryParameter("zoom", String.valueOf(18));
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(j);
    arrayOfObject1[1] = Integer.valueOf(j);
    Uri.Builder localBuilder3 = localBuilder2.appendQueryParameter("size", String.format("%dx%d", arrayOfObject1)).appendQueryParameter("format", "png").appendQueryParameter("maptype", "roadmap").appendQueryParameter("sensor", String.valueOf(true));
    Object[] arrayOfObject2 = new Object[3];
    if (paramBoolean);
    for (String str1 = "red"; ; str1 = "blue")
    {
      arrayOfObject2[0] = str1;
      arrayOfObject2[1] = Double.valueOf(paramLocation.getLatitude());
      arrayOfObject2[2] = Double.valueOf(paramLocation.getLongitude());
      localBuilder3.appendQueryParameter("markers", String.format("color:%s|%.6f,%.6f", arrayOfObject2));
      String str2 = Property.PLUS_STATICMAPS_API_KEY.get();
      if (!TextUtils.isEmpty(str2))
        localBuilder1.appendQueryParameter("key", str2);
      return localBuilder1.build().toString();
    }
  }

  private void doSearch()
  {
    if (this.mLocationQuery != null)
      if (TextUtils.isEmpty(this.mQuery))
        break label102;
    label102: for (this.mLocationQuery = new LocationQuery(this.mLocationQuery.getLocation(), this.mQuery); ; this.mLocationQuery = new LocationQuery(this.mLocationQuery.getLocation(), null))
    {
      this.mLoadPlacesNeeded = false;
      this.mLoadSearchNeeded = true;
      this.mNewerReqId = Integer.valueOf(EsService.getNearbyLocations(getActivity(), this.mAccount, this.mLocationQuery, this.mCurrentLocation));
      showProgress(getView(), getString(R.string.loading));
      getLoaderManager().restartLoader(1, null, this);
      return;
    }
  }

  private boolean isSearchWithNoEntry()
  {
    if ((this.mSearchMode) && (TextUtils.isEmpty(this.mQuery)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void removeLocationListener()
  {
    if (this.mLocationController != null)
    {
      this.mLocationController.release();
      this.mLocationController = null;
    }
  }

  private void sendResult(DbLocation paramDbLocation)
  {
    FragmentActivity localFragmentActivity = getActivity();
    Intent localIntent = new Intent();
    localIntent.putExtra("location", paramDbLocation);
    localFragmentActivity.setResult(-1, localIntent);
    localFragmentActivity.finish();
  }

  private void setupAndShowEmptyView(View paramView)
  {
    Resources localResources = getResources();
    if (isSearchWithNoEntry());
    for (String str = localResources.getString(R.string.enter_location_name); ; str = localResources.getString(R.string.no_locations))
    {
      showEmptyView(paramView, str);
      return;
    }
  }

  private void showProgress(View paramView, String paramString)
  {
    if (isSearchWithNoEntry())
      setupAndShowEmptyView(paramView);
    while (true)
    {
      return;
      showEmptyViewProgress(paramView, paramString);
    }
  }

  private void updateView$3c7ec8c3()
  {
    int i;
    if ((this.mSearchMode) && (!this.mIsLandscapeMode))
    {
      i = 8;
      this.mMapView.setVisibility(i);
      if ((this.mCurrentAdapter == null) || (this.mCurrentAdapter.getCursor() == null) || (this.mCurrentAdapter.getCursor().getCount() <= 0))
        break label71;
      showContent(getView());
    }
    while (true)
    {
      return;
      i = 0;
      break;
      label71: if (this.mNewerReqId != null)
        showProgress(getView(), getString(R.string.loading));
      else
        setupAndShowEmptyView(getView());
    }
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.LOCATION_PICKER;
  }

  protected final boolean isEmpty()
  {
    if ((this.mCurrentAdapter == null) || (this.mCurrentAdapter.getCursor() == null) || (this.mCurrentAdapter.getCount() == 0));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onActionButtonClicked(int paramInt)
  {
    if (paramInt == 0)
      setSearchMode(true);
  }

  public final boolean onBackPressed()
  {
    if (this.mSearchMode)
      setSearchMode(false);
    for (boolean bool = true; ; bool = super.onBackPressed())
      return bool;
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getActivity().getIntent();
    this.mAccount = ((EsAccount)localIntent.getParcelableExtra("account"));
    if (paramBundle != null)
    {
      this.mLocationQuery = ((LocationQuery)paramBundle.getParcelable("location"));
      this.mCurrentLocation = ((DbLocation)paramBundle.getParcelable("current_location"));
      this.mSearchMode = paramBundle.getBoolean("search_mode");
      this.mQuery = paramBundle.getString("query");
      this.mCurrentMapUrl = paramBundle.getString("current_map_url");
      this.mScrollPos = paramBundle.getInt("scroll_pos");
      this.mScrollOffset = paramBundle.getInt("scroll_off");
      if (this.mLocationQuery != null)
      {
        LoaderManager localLoaderManager = getLoaderManager();
        localLoaderManager.restartLoader(0, null, this);
        if (this.mSearchMode)
          localLoaderManager.restartLoader(1, null, this);
      }
    }
    while (true)
    {
      invalidateActionBar();
      return;
      this.mScrollPos = 0;
      this.mScrollOffset = 0;
      if (localIntent.hasExtra("location"))
      {
        DbLocation localDbLocation = (DbLocation)localIntent.getParcelableExtra("location");
        this.mLocationQuery = new LocationQuery(localDbLocation.getAndroidLocation(), null);
        this.mCurrentLocation = localDbLocation;
      }
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    LocationQuery localLocationQuery;
    String str1;
    label32: Uri localUri;
    if (paramInt == 0)
    {
      localLocationQuery = new LocationQuery(this.mLocationQuery.getLocation(), null);
      if (!isSearchWithNoEntry())
        break label94;
      str1 = "no_location_stream_key";
      localUri = EsProvider.buildLocationQueryUri(this.mAccount, str1);
      if (!getActivity().getIntent().getBooleanExtra("places_only", false))
        break label103;
    }
    label94: label103: for (String str2 = "name IS NOT NULL"; ; str2 = null)
    {
      return new EsCursorLoader(getActivity(), localUri, PlacesAdapter.LocationQuery.PROJECTION, str2, null, null);
      localLocationQuery = this.mLocationQuery;
      break;
      str1 = localLocationQuery.getKey();
      break label32;
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView1 = paramLayoutInflater.inflate(R.layout.checkin_list, paramViewGroup, false);
    boolean bool;
    TextView localTextView1;
    String str;
    label180: int i;
    if (getActivity().getResources().getConfiguration().orientation == 2)
    {
      bool = true;
      this.mIsLandscapeMode = bool;
      this.mListView = ((ListView)localView1.findViewById(16908298));
      this.mListView.setOnScrollListener(this);
      this.mMapView = ((ImageResourceView)localView1.findViewById(R.id.map));
      this.mMapView.setImageResourceFlags(1);
      if (this.mCurrentLocation == null)
        break label437;
      View localView2 = getActivity().getLayoutInflater().inflate(R.layout.location_row_layout, this.mListView, false);
      ((ImageView)localView2.findViewById(16908294)).setImageResource(R.drawable.ic_location_active);
      localTextView1 = (TextView)localView2.findViewById(16908310);
      TextView localTextView2 = (TextView)localView2.findViewById(16908293);
      if (!this.mCurrentLocation.isPrecise())
        break label383;
      localTextView1.setText(R.string.my_location);
      str = this.mCurrentLocation.getLocationName();
      localTextView2.setText(str);
      View localView3 = localView2.findViewById(R.id.remove_button);
      localView3.setVisibility(0);
      localView3.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          LocationPickerFragment.this.sendResult(null);
        }
      });
      this.mListView.addHeaderView(localView2, ITEM_KEEP_LOCATION, true);
      this.mCurrentMapUrl = createStaticMapUrl(this.mCurrentLocation.getAndroidLocation(), true);
      i = 1;
      label248: if (i == 0)
        break label459;
      this.mMapView.setMediaRef(new MediaRef(this.mCurrentMapUrl, MediaRef.MediaType.IMAGE), true);
      label275: if ((this.mSearchMode) && (!this.mIsLandscapeMode))
        this.mMapView.setVisibility(8);
      this.mPlacesAdapter = new PlacesAdapter(getActivity());
      this.mSearchAdapter = new PlacesAdapter(getActivity());
      if (!this.mSearchMode)
        break label471;
    }
    label437: label459: label471: for (PlacesAdapter localPlacesAdapter = this.mSearchAdapter; ; localPlacesAdapter = this.mPlacesAdapter)
    {
      this.mCurrentAdapter = localPlacesAdapter;
      this.mListView.setAdapter(this.mCurrentAdapter);
      setupEmptyView(localView1, R.string.no_locations);
      this.mListView.setOnItemClickListener(this);
      return localView1;
      bool = false;
      break;
      label383: if (this.mCurrentLocation.isCoarse())
      {
        localTextView1.setText(R.string.my_city);
        str = this.mCurrentLocation.getLocationName();
        break label180;
      }
      localTextView1.setText(this.mCurrentLocation.getName());
      str = this.mCurrentLocation.getBestAddress();
      break label180;
      if (!TextUtils.isEmpty(this.mCurrentMapUrl))
      {
        i = 1;
        break label248;
      }
      i = 0;
      break label248;
      this.mMapView.setVisibility(8);
      break label275;
    }
  }

  public final void onDestroyView()
  {
    super.onDestroyView();
    if (this.mListView != null)
    {
      this.mListView.setOnScrollListener(null);
      this.mListView = null;
    }
  }

  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 3)
      doSearch();
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Object localObject = paramAdapterView.getItemAtPosition(paramInt);
    if (localObject == null)
      return;
    Cursor localCursor = this.mCurrentAdapter.getCursor();
    if (localObject == ITEM_KEEP_LOCATION);
    for (DbLocation localDbLocation = this.mCurrentLocation; ; localDbLocation = PlacesAdapter.getLocation(localCursor))
    {
      FragmentActivity localFragmentActivity = getActivity();
      Intent localIntent1 = localFragmentActivity.getIntent();
      if (!"android.intent.action.PICK".equals(localIntent1.getAction()))
      {
        Intent localIntent2 = Intents.getPostActivityIntent(localFragmentActivity, this.mAccount, localDbLocation);
        AudienceData localAudienceData = (AudienceData)localIntent1.getParcelableExtra("audience");
        if (localAudienceData != null)
          localIntent2.putExtra("audience", localAudienceData);
        startActivity(localIntent2);
      }
      sendResult(localDbLocation);
      break;
      if (localObject != localCursor)
        break;
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onPause()
  {
    super.onPause();
    if ((this.mPlacesAdapter != null) && (this.mPlacesAdapter.getCursor() != null))
      PlacesAdapter.onPause();
    if ((this.mSearchAdapter != null) && (this.mSearchAdapter.getCursor() != null))
      PlacesAdapter.onPause();
    EsService.unregisterListener(this.mServiceListener);
    removeLocationListener();
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    super.onPrepareActionBar(paramHostActionBar);
    if (this.mSearchMode)
    {
      paramHostActionBar.showSearchView();
      SearchViewAdapter localSearchViewAdapter = paramHostActionBar.getSearchViewAdapter();
      localSearchViewAdapter.setQueryHint(R.string.search_location_hint_text);
      localSearchViewAdapter.addOnChangeListener(this);
    }
    label71: 
    while (true)
    {
      return;
      paramHostActionBar.showTitle(R.string.post_checkin_title);
      if (this.mLocationQuery != null);
      for (int i = 1; ; i = 0)
      {
        if (i == 0)
          break label71;
        paramHostActionBar.showActionButton(0, R.drawable.ic_menu_search, R.string.menu_search);
        break;
      }
    }
  }

  public final void onQueryClose()
  {
    getActionBar().getSearchViewAdapter().setQueryText(null);
  }

  public final void onQueryTextChanged(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null);
    for (String str = null; ; str = paramCharSequence.toString().trim())
    {
      this.mQuery = str;
      doSearch();
      return;
    }
  }

  public final void onQueryTextSubmitted(CharSequence paramCharSequence)
  {
  }

  public final void onResume()
  {
    super.onResume();
    if ((this.mPlacesAdapter != null) && (this.mPlacesAdapter.getCursor() != null))
      this.mPlacesAdapter.onResume();
    if ((this.mSearchAdapter != null) && (this.mSearchAdapter.getCursor() != null))
      this.mSearchAdapter.onResume();
    EsService.registerListener(this.mServiceListener);
    if (this.mCurrentLocation != null)
      if (this.mCurrentAdapter.getCount() == 0)
      {
        showProgress(getView(), getString(R.string.loading));
        this.mLoadPlacesNeeded = true;
        this.mLoadSearchNeeded = false;
        this.mNewerReqId = Integer.valueOf(EsService.getNearbyLocations(getActivity(), this.mAccount, this.mLocationQuery, this.mCurrentLocation));
      }
    while (true)
    {
      return;
      Location localLocation;
      if (this.mLocationController == null)
      {
        FragmentActivity localFragmentActivity = getActivity();
        EsAccount localEsAccount = this.mAccount;
        if (this.mLocationQuery != null)
        {
          localLocation = this.mLocationQuery.getLocation();
          label160: this.mLocationController = new LocationController(localFragmentActivity, localEsAccount, true, 3000L, localLocation, this.mLocationListener);
        }
      }
      else
      {
        if (this.mLocationController.isProviderEnabled())
          break label228;
        getActivity().showDialog(29341608);
      }
      while (true)
      {
        if (this.mLocationController.isProviderEnabled())
          break label238;
        setupAndShowEmptyView(getView());
        break;
        localLocation = null;
        break label160;
        label228: this.mLocationController.init();
      }
      label238: if (this.mLocationQuery == null)
        showProgress(getView(), getString(R.string.finding_your_location));
      else
        showProgress(getView(), getString(R.string.loading));
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if ((!getActivity().isFinishing()) && (this.mListView != null))
      if (this.mListView != null)
      {
        this.mScrollPos = this.mListView.getFirstVisiblePosition();
        if (this.mCurrentAdapter == null)
          break label168;
        View localView = this.mListView.getChildAt(0);
        if (localView == null)
          break label160;
        this.mScrollOffset = localView.getTop();
      }
    while (true)
    {
      paramBundle.putInt("scroll_pos", this.mScrollPos);
      paramBundle.putInt("scroll_off", this.mScrollOffset);
      if (this.mLocationQuery != null)
      {
        paramBundle.putParcelable("location", this.mLocationQuery);
        paramBundle.putBoolean("search_mode", this.mSearchMode);
      }
      if (this.mCurrentLocation != null)
        paramBundle.putParcelable("current_location", this.mCurrentLocation);
      paramBundle.putString("current_map_url", this.mCurrentMapUrl);
      paramBundle.putString("query", this.mQuery);
      return;
      label160: this.mScrollOffset = 0;
      continue;
      label168: this.mScrollOffset = 0;
    }
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 > 0)
    {
      int i = paramInt1 + paramInt2;
      if ((i >= paramInt3) && (i == this.mPrevScrollPosition));
      this.mPrevScrollPosition = i;
      this.mPrevScrollItemCount = paramInt3;
    }
  }

  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
  }

  public final boolean onUpButtonClicked()
  {
    if (this.mSearchMode)
      setSearchMode(false);
    for (boolean bool = true; ; bool = super.onUpButtonClicked())
      return bool;
  }

  public final void setSearchMode(boolean paramBoolean)
  {
    if (paramBoolean == this.mSearchMode)
      return;
    this.mSearchMode = paramBoolean;
    PlacesAdapter localPlacesAdapter;
    if (this.mSearchMode)
    {
      localPlacesAdapter = this.mSearchAdapter;
      label26: this.mCurrentAdapter = localPlacesAdapter;
      this.mListView.setAdapter(this.mCurrentAdapter);
      getActionBar().getSearchViewAdapter().setQueryText(null);
      if (!paramBoolean)
        break label85;
      doSearch();
    }
    while (true)
    {
      invalidateActionBar();
      getView();
      updateView$3c7ec8c3();
      break;
      localPlacesAdapter = this.mPlacesAdapter;
      break label26;
      label85: getLoaderManager().restartLoader(0, null, this);
    }
  }

  private final class CheckinLocationListener
    implements LocationListener
  {
    private CheckinLocationListener()
    {
    }

    public final void onLocationChanged(Location paramLocation)
    {
      boolean bool1 = LocationPickerFragment.this.mLocationController.isProviderEnabled();
      LocationPickerFragment.this.removeLocationListener();
      boolean bool2 = false;
      if (!TextUtils.isEmpty(LocationPickerFragment.this.mQuery))
        LocationPickerFragment.access$702(LocationPickerFragment.this, new LocationQuery(paramLocation, LocationPickerFragment.this.mQuery));
      for (boolean bool3 = true; ; bool3 = false)
      {
        LocationPickerFragment.this.invalidateActionBar();
        if (bool1)
        {
          LocationPickerFragment.this.showProgress(LocationPickerFragment.this.getView(), LocationPickerFragment.this.getString(R.string.loading));
          LocationPickerFragment.access$102(LocationPickerFragment.this, bool2);
          LocationPickerFragment.access$202(LocationPickerFragment.this, bool3);
          LocationPickerFragment.this.mNewerReqId = Integer.valueOf(EsService.getNearbyLocations(LocationPickerFragment.this.getActivity(), LocationPickerFragment.this.mAccount, LocationPickerFragment.this.mLocationQuery, LocationPickerFragment.this.mCurrentLocation));
        }
        if ((LocationPickerFragment.this.mMapView != null) && (LocationPickerFragment.this.mCurrentLocation == null))
        {
          LocationPickerFragment.access$1202(LocationPickerFragment.this, LocationPickerFragment.this.createStaticMapUrl(paramLocation, false));
          LocationPickerFragment.this.mMapView.setMediaRef(new MediaRef(LocationPickerFragment.this.mCurrentMapUrl, MediaRef.MediaType.IMAGE), false);
          LocationPickerFragment.this.updateView$3c7ec8c3(LocationPickerFragment.this.getView());
        }
        return;
        LocationPickerFragment.access$702(LocationPickerFragment.this, new LocationQuery(paramLocation, null));
        bool2 = true;
      }
    }

    public final void onProviderDisabled(String paramString)
    {
    }

    public final void onProviderEnabled(String paramString)
    {
    }

    public final void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
    }
  }

  private final class ServiceListener extends EsServiceListener
  {
    private ServiceListener()
    {
    }

    public final void onLocationQuery$260d7f24(int paramInt, ServiceResult paramServiceResult)
    {
      if ((LocationPickerFragment.this.mNewerReqId != null) && (LocationPickerFragment.this.mNewerReqId.intValue() == paramInt))
      {
        LocationPickerFragment.this.mNewerReqId = null;
        if (paramServiceResult.hasError())
          Toast.makeText(LocationPickerFragment.this.getActivity(), R.string.checkin_places_error, 0).show();
        LoaderManager localLoaderManager = LocationPickerFragment.this.getLoaderManager();
        if (LocationPickerFragment.this.mLoadPlacesNeeded)
        {
          LocationPickerFragment.access$102(LocationPickerFragment.this, false);
          localLoaderManager.restartLoader(0, null, LocationPickerFragment.this);
        }
        if (LocationPickerFragment.this.mLoadSearchNeeded)
        {
          LocationPickerFragment.access$202(LocationPickerFragment.this, false);
          localLoaderManager.restartLoader(1, null, LocationPickerFragment.this);
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.LocationPickerFragment
 * JD-Core Version:    0.6.2
 */