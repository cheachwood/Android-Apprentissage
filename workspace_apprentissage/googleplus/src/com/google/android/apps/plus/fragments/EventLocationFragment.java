package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.LocationQuery;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.phone.LocationController;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.LocationUtils;
import com.google.android.apps.plus.util.SoftInput;
import com.google.api.services.plusi.model.Place;

public class EventLocationFragment extends EsListFragment<ListView, EsCursorAdapter>
  implements LoaderManager.LoaderCallbacks<Cursor>, TextWatcher, AdapterView.OnItemClickListener
{
  private static final String[] LOCATION_PROJECTION = { "_id", "type", "title", "description", "location" };
  private double mCurrentLatitude;
  private double mCurrentLongitude;
  private String mInitialQuery;
  private OnLocationSelectedListener mListener;
  private LocationController mLocationController;
  private LocationListener mLocationListener = new LocationListener()
  {
    public final void onLocationChanged(Location paramAnonymousLocation)
    {
      EventLocationFragment.this.removeLocationListener();
      EventLocationFragment.access$100(EventLocationFragment.this, paramAnonymousLocation.getLatitude(), paramAnonymousLocation.getLongitude());
    }

    public final void onProviderDisabled(String paramAnonymousString)
    {
    }

    public final void onProviderEnabled(String paramAnonymousString)
    {
    }

    public final void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
    }
  };
  private LocationQuery mLocationQuery;
  private EditText mLocationText;
  private String mQuery;

  private void buildLocationQuery()
  {
    Location localLocation;
    if ((!TextUtils.isEmpty(this.mQuery)) && (isCurrentLocationKnown()))
    {
      localLocation = new Location(null);
      localLocation.setLatitude(this.mCurrentLatitude);
      localLocation.setLongitude(this.mCurrentLongitude);
    }
    for (this.mLocationQuery = new LocationQuery(localLocation, this.mQuery); ; this.mLocationQuery = null)
      return;
  }

  private EsAccount getAccount()
  {
    return (EsAccount)getActivity().getIntent().getExtras().get("account");
  }

  private boolean isCurrentLocationKnown()
  {
    if ((this.mCurrentLatitude != 0.0D) && (this.mCurrentLongitude != 0.0D));
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

  private void runQuery()
  {
    if (this.mLocationQuery != null)
    {
      getLoaderManager().restartLoader(0, null, this);
      EsService.getNearbyLocations(getActivity(), getAccount(), this.mLocationQuery);
    }
  }

  private void updateAdapter(Cursor paramCursor)
  {
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(LOCATION_PROJECTION);
    if (TextUtils.isEmpty(this.mQuery))
    {
      Object[] arrayOfObject4 = new Object[5];
      arrayOfObject4[0] = Integer.valueOf(1);
      arrayOfObject4[1] = Integer.valueOf(0);
      arrayOfObject4[2] = getString(R.string.event_location_none_title);
      arrayOfObject4[3] = getString(R.string.event_location_none_description);
      arrayOfObject4[4] = null;
      localEsMatrixCursor.addRow(arrayOfObject4);
    }
    while (true)
    {
      this.mAdapter.swapCursor(localEsMatrixCursor);
      return;
      Object[] arrayOfObject1 = new Object[5];
      int i = 1 + 1;
      arrayOfObject1[0] = Integer.valueOf(1);
      arrayOfObject1[1] = Integer.valueOf(1);
      int j = R.string.event_location_add;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mQuery;
      arrayOfObject1[2] = getString(j, arrayOfObject2);
      arrayOfObject1[3] = null;
      arrayOfObject1[4] = null;
      localEsMatrixCursor.addRow(arrayOfObject1);
      if ((paramCursor != null) && (paramCursor.moveToFirst()))
        do
        {
          byte[] arrayOfByte = paramCursor.getBlob(0);
          DbLocation localDbLocation = DbLocation.deserialize(arrayOfByte);
          if (localDbLocation != null)
          {
            Object[] arrayOfObject3 = new Object[5];
            int k = i + 1;
            arrayOfObject3[0] = Integer.valueOf(i);
            arrayOfObject3[1] = Integer.valueOf(2);
            arrayOfObject3[2] = localDbLocation.getName();
            arrayOfObject3[3] = localDbLocation.getBestAddress();
            arrayOfObject3[4] = arrayOfByte;
            localEsMatrixCursor.addRow(arrayOfObject3);
            i = k;
          }
        }
        while (paramCursor.moveToNext());
    }
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!isCurrentLocationKnown())
    {
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
      if (localSharedPreferences.contains("event.current.latitude"))
      {
        this.mCurrentLatitude = Double.parseDouble(localSharedPreferences.getString("event.current.latitude", "0"));
        this.mCurrentLongitude = Double.parseDouble(localSharedPreferences.getString("event.current.longitude", "0"));
      }
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mQuery = paramBundle.getString("query");
      this.mCurrentLatitude = paramBundle.getDouble("latitude");
      this.mCurrentLongitude = paramBundle.getDouble("longitude");
      buildLocationQuery();
    }
    getLoaderManager().initLoader(0, null, this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (this.mLocationQuery == null);
    for (String str = "no_location_stream_key"; ; str = this.mLocationQuery.getKey())
    {
      Uri localUri = EsProvider.buildLocationQueryUri(getAccount(), str);
      return new EsCursorLoader(getActivity(), localUri, new String[] { "location" }, null, null, null);
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle, R.layout.event_location_fragment);
    this.mAdapter = new EventLocationAdapter(getActivity());
    ((ListView)this.mListView).setAdapter(this.mAdapter);
    ((ListView)this.mListView).setOnItemClickListener(this);
    this.mLocationText = ((EditText)localView.findViewById(R.id.location_text));
    this.mLocationText.addTextChangedListener(this);
    this.mLocationText.setText(this.mInitialQuery);
    return localView;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Cursor localCursor = (Cursor)this.mAdapter.getItem(paramInt);
    Place localPlace;
    switch (localCursor.getInt(1))
    {
    default:
      byte[] arrayOfByte = localCursor.getBlob(4);
      if (arrayOfByte != null)
        localPlace = LocationUtils.convertLocationToPlace(DbLocation.deserialize(arrayOfByte).toProtocolObject());
      break;
    case 0:
    case 1:
    }
    while (true)
    {
      SoftInput.hide(getView());
      if (this.mListener != null)
        this.mListener.onLocationSelected(localPlace);
      return;
      localPlace = null;
      continue;
      localPlace = new Place();
      localPlace.name = this.mQuery;
      continue;
      localPlace = null;
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onPause()
  {
    super.onPause();
    removeLocationListener();
  }

  public final void onResume()
  {
    super.onResume();
    if (this.mLocationController == null)
      this.mLocationController = new LocationController(getActivity(), getAccount(), true, 3000L, null, this.mLocationListener);
    if (this.mLocationController.isProviderEnabled())
      this.mLocationController.init();
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("query", this.mQuery);
    paramBundle.putDouble("latitude", this.mCurrentLatitude);
    paramBundle.putDouble("longitude", this.mCurrentLongitude);
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    String str = this.mLocationText.getText().toString().trim();
    if (!TextUtils.equals(this.mQuery, str))
    {
      this.mQuery = str;
      if (isAdded())
      {
        updateAdapter(null);
        buildLocationQuery();
        runQuery();
      }
    }
  }

  public final void setInitialQueryString(String paramString)
  {
    this.mInitialQuery = paramString;
  }

  public final void setOnLocationSelectedListener(OnLocationSelectedListener paramOnLocationSelectedListener)
  {
    this.mListener = paramOnLocationSelectedListener;
  }

  private static final class EventLocationAdapter extends EsCursorAdapter
  {
    public EventLocationAdapter(Context paramContext)
    {
      super(null);
    }

    public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      ImageView localImageView = (ImageView)paramView.findViewById(16908294);
      TextView localTextView1 = (TextView)paramView.findViewById(16908310);
      TextView localTextView2 = (TextView)paramView.findViewById(16908293);
      switch (paramCursor.getInt(1))
      {
      default:
        localTextView1.setText(paramCursor.getString(2));
        String str = paramCursor.getString(3);
        if (!TextUtils.isEmpty(str))
        {
          localTextView2.setVisibility(0);
          localTextView2.setText(str);
        }
        break;
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        return;
        localImageView.setVisibility(0);
        localImageView.setImageResource(R.drawable.ic_location_none);
        break;
        localImageView.setVisibility(8);
        break;
        localImageView.setVisibility(0);
        localImageView.setImageResource(R.drawable.ic_location_grey);
        break;
        localTextView2.setVisibility(8);
      }
    }

    public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      return LayoutInflater.from(paramContext).inflate(R.layout.location_row_layout, paramViewGroup, false);
    }
  }

  public static abstract interface OnLocationSelectedListener
  {
    public abstract void onLocationSelected(Place paramPlace);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EventLocationFragment
 * JD-Core Version:    0.6.2
 */