package com.google.android.apps.plus.util;

import android.text.TextUtils;
import com.google.api.services.plusi.model.EmbedsPostalAddress;
import com.google.api.services.plusi.model.GeoCoordinates;
import com.google.api.services.plusi.model.Location;
import com.google.api.services.plusi.model.Place;

public final class LocationUtils
{
  public static Place convertLocationToPlace(Location paramLocation)
  {
    Place localPlace;
    if (paramLocation == null)
      localPlace = null;
    label145: 
    while (true)
    {
      return localPlace;
      localPlace = new Place();
      if ((paramLocation.latitudeE7 != null) && (paramLocation.longitudeE7 != null))
      {
        localPlace.geo = new GeoCoordinates();
        localPlace.geo.latitude = Double.valueOf(paramLocation.latitudeE7.intValue() / 10000000.0D);
        localPlace.geo.longitude = Double.valueOf(paramLocation.longitudeE7.intValue() / 10000000.0D);
      }
      if (!TextUtils.isEmpty(paramLocation.locationTag));
      for (String str = paramLocation.locationTag; ; str = paramLocation.bestAddress)
      {
        if (str == null)
          break label145;
        localPlace.name = str;
        localPlace.description = str;
        localPlace.address = new EmbedsPostalAddress();
        localPlace.address.name = paramLocation.bestAddress;
        break;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.LocationUtils
 * JD-Core Version:    0.6.2
 */