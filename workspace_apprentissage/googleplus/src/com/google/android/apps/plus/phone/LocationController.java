package com.google.android.apps.plus.phone;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.google.android.apps.plus.api.LocationQuery;
import com.google.android.apps.plus.api.SnapToPlaceOperation;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.Property;
import java.util.List;

public final class LocationController
{
  private final EsAccount mAccount;
  private final Context mContext;
  private final boolean mDisplayDebugToast;
  private LocationListener mGpsListener;
  private final Handler mHandler;
  private Location mLastSentLocation;
  private LocationListener mLastSuccessfulLocationListener;
  private LocationListener mListener;
  private Location mLocation;
  private Runnable mLocationAcquisitionTimer;
  private final LocationManager mLocationManager;
  private LocationListener mNetworkListener;
  private final boolean mReverseGeo;
  private final long mTimeout;

  public LocationController(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean, long paramLong, Location paramLocation, LocationListener paramLocationListener)
  {
    this.mContext = paramContext;
    this.mAccount = paramEsAccount;
    this.mListener = paramLocationListener;
    this.mHandler = new Handler();
    this.mReverseGeo = true;
    this.mLocation = paramLocation;
    this.mTimeout = 3000L;
    this.mLocationManager = ((LocationManager)paramContext.getSystemService("location"));
    List localList = this.mLocationManager.getProviders(true);
    int i = localList.size();
    int j = 0;
    if (j < i)
    {
      if (!"network".equals(localList.get(j)))
        break label211;
      this.mNetworkListener = new LocalLocationListener((byte)0);
      this.mLocationManager.requestLocationUpdates("network", 3000L, 0.0F, this.mNetworkListener);
    }
    for (int k = 0; ; k++)
      if (k < i)
      {
        if ("gps".equals(localList.get(k)))
        {
          this.mGpsListener = new LocalLocationListener((byte)0);
          this.mLocationManager.requestLocationUpdates("gps", 3000L, 0.0F, this.mGpsListener);
        }
      }
      else
      {
        this.mDisplayDebugToast = Property.LOCATION_DEBUGGING.get().equalsIgnoreCase("TRUE");
        return;
        label211: j++;
        break;
      }
  }

  public static boolean areSameLocations(Location paramLocation1, Location paramLocation2)
  {
    if ((paramLocation1.getLatitude() == paramLocation2.getLatitude()) && (paramLocation1.getLongitude() == paramLocation2.getLongitude()) && ((int)paramLocation1.getAccuracy() - paramLocation2.getAccuracy() == 0.0F));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private boolean canFindLocation()
  {
    if ((this.mLocationManager.isProviderEnabled("network")) || (this.mLocationManager.isProviderEnabled("gps")));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private static boolean canFindLocation(Context paramContext)
  {
    LocationManager localLocationManager = (LocationManager)paramContext.getSystemService("location");
    if ((localLocationManager.isProviderEnabled("network")) || (localLocationManager.isProviderEnabled("gps")));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static DbLocation getCityLevelLocation(Location paramLocation)
  {
    return (DbLocation)getExtras(paramLocation).getParcelable("coarse_location");
  }

  private static Bundle getExtras(Location paramLocation)
  {
    Bundle localBundle = paramLocation.getExtras();
    if (localBundle != null);
    while (true)
    {
      return localBundle;
      localBundle = Bundle.EMPTY;
    }
  }

  public static String getFormattedAddress(Location paramLocation)
  {
    Bundle localBundle = getExtras(paramLocation);
    localBundle.setClassLoader(DbLocation.class.getClassLoader());
    return localBundle.getString("location_description");
  }

  public static DbLocation getStreetLevelLocation(Location paramLocation)
  {
    return (DbLocation)getExtras(paramLocation).getParcelable("finest_location");
  }

  public static boolean isProviderEnabled(Context paramContext)
  {
    boolean bool;
    if (Build.VERSION.SDK_INT < 11)
      bool = canFindLocation(paramContext);
    while (true)
    {
      return bool;
      switch (GoogleLocationSettingHelper.getUseLocationForServices(paramContext))
      {
      default:
        bool = canFindLocation(paramContext);
        break;
      case 0:
        bool = false;
      }
    }
  }

  public final void init()
  {
    if (this.mLocationAcquisitionTimer != null)
    {
      this.mHandler.removeCallbacks(this.mLocationAcquisitionTimer);
      this.mLocationAcquisitionTimer = null;
    }
    this.mLastSentLocation = null;
    if ((this.mTimeout > 0L) && (this.mNetworkListener != null) && (this.mGpsListener != null))
    {
      this.mLocationAcquisitionTimer = new Runnable()
      {
        public final void run()
        {
          if (LocationController.this.mLocation != null)
          {
            if (EsLog.isLoggable("LocationController", 3))
              Log.d("LocationController", "----> locationAcquisitionTimer: timeout, with location");
            if ((LocationController.this.mLastSentLocation == null) || (!LocationController.areSameLocations(LocationController.this.mLastSentLocation, LocationController.this.mLocation)))
            {
              if (!LocationController.this.mReverseGeo)
                break label97;
              LocationController.access$900(LocationController.this, LocationController.this.mLocation);
            }
          }
          label170: 
          while (true)
          {
            LocationController.access$702(LocationController.this, LocationController.this.mLocation);
            while (true)
            {
              return;
              label97: if (LocationController.this.mListener == null)
                break label170;
              LocationController.this.mListener.onLocationChanged(LocationController.this.mLocation);
              break;
              if (EsLog.isLoggable("LocationController", 3))
                Log.d("LocationController", "----> locationAcquisitionTimer: timeout, without location");
              LocationController.this.mHandler.postDelayed(this, LocationController.this.mTimeout / 2L);
            }
          }
        }
      };
      this.mHandler.postDelayed(this.mLocationAcquisitionTimer, this.mTimeout);
    }
  }

  public final boolean isProviderEnabled()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT < 11)
      bool = canFindLocation();
    while (true)
    {
      return bool;
      switch (GoogleLocationSettingHelper.getUseLocationForServices(this.mContext))
      {
      default:
        bool = canFindLocation();
        break;
      case 0:
        bool = false;
      }
    }
  }

  public final void release()
  {
    if (this.mLocationAcquisitionTimer != null)
    {
      this.mHandler.removeCallbacks(this.mLocationAcquisitionTimer);
      this.mLocationAcquisitionTimer = null;
    }
    if (this.mNetworkListener != null)
      this.mLocationManager.removeUpdates(this.mNetworkListener);
    if (this.mGpsListener != null)
      this.mLocationManager.removeUpdates(this.mGpsListener);
    this.mListener = null;
  }

  private final class LocalLocationListener
    implements LocationListener
  {
    private LocalLocationListener()
    {
    }

    private void triggerLocationListener()
    {
      String str;
      if (((LocationController.this.mLastSuccessfulLocationListener != null) && (LocationController.this.mLastSuccessfulLocationListener != this)) || ((LocationController.this.mLocationAcquisitionTimer == null) && ((LocationController.this.mLastSentLocation == null) || (!LocationController.areSameLocations(LocationController.this.mLastSentLocation, LocationController.this.mLocation)))))
      {
        if (EsLog.isLoggable("LocationController", 3))
        {
          StringBuilder localStringBuilder = new StringBuilder("----> onLocationChanged: triggering location change because ");
          if (LocationController.this.mLocationAcquisitionTimer != null)
            break label156;
          str = "only this location listener was registered";
          Log.d("LocationController", str);
        }
        if (!LocationController.this.mReverseGeo)
          break label162;
        LocationController.access$900(LocationController.this, LocationController.this.mLocation);
      }
      while (true)
      {
        LocationController.access$702(LocationController.this, LocationController.this.mLocation);
        LocationController.access$502(LocationController.this, this);
        return;
        label156: str = "a previous location listener was successful";
        break;
        label162: if (LocationController.this.mListener != null)
          LocationController.this.mListener.onLocationChanged(LocationController.this.mLocation);
      }
    }

    public final void onLocationChanged(Location paramLocation)
    {
      if (EsLog.isLoggable("LocationController", 3))
        Log.d("LocationController", "====> onLocationChanged: " + paramLocation.getTime() + " from provider: " + paramLocation.getProvider());
      if (!LocationController.access$100(LocationController.this, paramLocation, LocationController.this.mLocation))
      {
        if (LocationController.this.mLocation != null)
          triggerLocationListener();
        return;
      }
      if (EsLog.isLoggable("LocationController", 3))
        Log.d("LocationController", "----> onLocationChanged: new location: " + paramLocation.getAccuracy());
      LocationController.access$002(LocationController.this, paramLocation);
      if (LocationController.this.mDisplayDebugToast)
        if (this != LocationController.this.mNetworkListener)
          break label169;
      label169: for (String str = "net: "; ; str = "gps: ")
      {
        LocationController.this.mLocation.getExtras().putString("location_source", str);
        triggerLocationListener();
        break;
      }
    }

    public final void onProviderDisabled(String paramString)
    {
      if (LocationController.this.mListener != null)
        LocationController.this.mListener.onProviderDisabled(paramString);
    }

    public final void onProviderEnabled(String paramString)
    {
      if (LocationController.this.mListener != null)
        LocationController.this.mListener.onProviderEnabled(paramString);
    }

    public final void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
      if (LocationController.this.mListener != null)
        LocationController.this.mListener.onStatusChanged(paramString, paramInt, paramBundle);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.LocationController
 * JD-Core Version:    0.6.2
 */