package com.google.android.apps.plus.api;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LocationQuery
  implements Parcelable
{
  public static final Parcelable.Creator<LocationQuery> CREATOR = new Parcelable.Creator()
  {
  };
  private final String mKey;
  private final Location mLocation;
  private final String mQuery;

  public LocationQuery(Location paramLocation, String paramString)
  {
    if (paramLocation == null)
      throw new NullPointerException("Location is null");
    this.mLocation = paramLocation;
    this.mQuery = paramString;
    this.mKey = buildKey();
  }

  private LocationQuery(Parcel paramParcel)
  {
    this.mLocation = ((Location)paramParcel.readParcelable(LocationQuery.class.getClassLoader()));
    this.mQuery = paramParcel.readString();
    this.mKey = buildKey();
  }

  private String buildKey()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mLocation.getLatitude()).append('|');
    localStringBuilder.append(this.mLocation.getLongitude()).append('|');
    localStringBuilder.append(this.mLocation.getAccuracy()).append('|');
    if (hasQueryString())
      localStringBuilder.append(this.mQuery);
    return localStringBuilder.toString();
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = false;
    if (paramObject == null);
    while (true)
    {
      return bool1;
      boolean bool2 = paramObject instanceof LocationQuery;
      bool1 = false;
      if (bool2)
        bool1 = this.mKey.equals(((LocationQuery)paramObject).mKey);
    }
  }

  public final String getKey()
  {
    return this.mKey;
  }

  public final Location getLocation()
  {
    return this.mLocation;
  }

  public final String getQueryString()
  {
    return this.mQuery;
  }

  public final boolean hasQueryString()
  {
    if ((this.mQuery != null) && (this.mQuery.length() > 0));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public int hashCode()
  {
    return this.mKey.hashCode();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeParcelable(this.mLocation, paramInt);
    paramParcel.writeString(this.mQuery);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.LocationQuery
 * JD-Core Version:    0.6.2
 */