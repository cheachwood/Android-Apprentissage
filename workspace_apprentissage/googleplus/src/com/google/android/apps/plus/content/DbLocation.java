package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.api.services.plusi.model.Checkin;
import com.google.api.services.plusi.model.EmbedsPostalAddress;
import com.google.api.services.plusi.model.GeoCoordinates;
import com.google.api.services.plusi.model.Place;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class DbLocation extends DbSerializer
  implements Parcelable
{
  public static final Parcelable.Creator<DbLocation> CREATOR = new Parcelable.Creator()
  {
  };
  private final String mBestAddress;
  private final String mClusterId;
  private final boolean mHasCoordinates;
  private final int mLatitudeE7;
  private final int mLongitudeE7;
  private final String mName;
  private final double mPrecisionMeters;
  private final int mType;

  public DbLocation(int paramInt, android.location.Location paramLocation)
  {
    if (paramLocation == null)
      throw new IllegalArgumentException();
    this.mType = 0;
    this.mHasCoordinates = true;
    this.mLatitudeE7 = ((int)(10000000.0D * paramLocation.getLatitude()));
    this.mLongitudeE7 = ((int)(10000000.0D * paramLocation.getLongitude()));
    this.mClusterId = null;
    this.mBestAddress = null;
    this.mName = null;
    if (paramLocation.hasAccuracy());
    for (double d = paramLocation.getAccuracy(); ; d = -1.0D)
    {
      this.mPrecisionMeters = d;
      return;
    }
  }

  public DbLocation(int paramInt, com.google.api.services.plusi.model.Location paramLocation)
  {
    if ((paramInt < 0) || (paramInt > 3) || (paramLocation == null))
      throw new IllegalArgumentException();
    this.mType = paramInt;
    this.mName = paramLocation.locationTag;
    this.mBestAddress = paramLocation.bestAddress;
    this.mClusterId = paramLocation.clusterId;
    Integer localInteger1;
    Integer localInteger2;
    label79: boolean bool;
    if (paramLocation.latitudeE7 != null)
    {
      localInteger1 = paramLocation.latitudeE7;
      if (paramLocation.longitudeE7 == null)
        break label172;
      localInteger2 = paramLocation.longitudeE7;
      if ((localInteger1 == null) || (localInteger2 == null))
        break label206;
      bool = true;
      label91: this.mHasCoordinates = bool;
      if (!this.mHasCoordinates)
        break label212;
      this.mLatitudeE7 = localInteger1.intValue();
      this.mLongitudeE7 = localInteger2.intValue();
      label121: if (paramLocation.precisionMeters != null)
        break label225;
    }
    label172: label206: label212: label225: for (double d = -1.0D; ; d = paramLocation.precisionMeters.doubleValue())
    {
      this.mPrecisionMeters = d;
      return;
      if (paramLocation.latitude != null)
      {
        localInteger1 = Integer.valueOf((int)(10000000.0D * paramLocation.latitude.floatValue()));
        break;
      }
      localInteger1 = null;
      break;
      if (paramLocation.longitude != null)
      {
        localInteger2 = Integer.valueOf((int)(10000000.0D * paramLocation.longitude.floatValue()));
        break label79;
      }
      localInteger2 = null;
      break label79;
      bool = false;
      break label91;
      this.mLongitudeE7 = 0;
      this.mLatitudeE7 = 0;
      break label121;
    }
  }

  public DbLocation(int paramInt, Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2, String paramString3, double paramDouble)
  {
    if ((paramInt < 0) || (paramInt > 3))
      throw new IllegalArgumentException();
    this.mType = paramInt;
    this.mName = paramString1;
    this.mBestAddress = paramString2;
    this.mClusterId = paramString3;
    boolean bool;
    if ((paramInteger1 != null) && (paramInteger2 != null))
    {
      bool = true;
      this.mHasCoordinates = bool;
      if (!this.mHasCoordinates)
        break label97;
      this.mLatitudeE7 = paramInteger1.intValue();
      this.mLongitudeE7 = paramInteger2.intValue();
    }
    while (true)
    {
      this.mPrecisionMeters = paramDouble;
      return;
      bool = false;
      break;
      label97: this.mLongitudeE7 = 0;
      this.mLatitudeE7 = 0;
    }
  }

  private DbLocation(Parcel paramParcel)
  {
    this.mType = paramParcel.readInt();
    this.mName = paramParcel.readString();
    this.mBestAddress = paramParcel.readString();
    if (paramParcel.readInt() != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.mHasCoordinates = bool;
      this.mLatitudeE7 = paramParcel.readInt();
      this.mLongitudeE7 = paramParcel.readInt();
      this.mPrecisionMeters = paramParcel.readDouble();
      this.mClusterId = paramParcel.readString();
      return;
    }
  }

  private DbLocation(Checkin paramCheckin)
  {
    if (paramCheckin == null)
      throw new IllegalArgumentException();
    this.mType = 3;
    String str1;
    String str2;
    label60: boolean bool;
    if (paramCheckin.location != null)
      if (paramCheckin.location.name == null)
      {
        str1 = paramCheckin.name;
        this.mName = str1;
        if (paramCheckin.location.address != null)
          break label184;
        str2 = null;
        this.mBestAddress = str2;
        if (paramCheckin.location.geo == null)
          break label204;
        if ((paramCheckin.location.geo.latitude == null) || (paramCheckin.location.geo.longitude == null))
          break label198;
        bool = true;
        label104: this.mHasCoordinates = bool;
        this.mLatitudeE7 = ((int)(10000000.0D * PrimitiveUtils.safeDouble(paramCheckin.location.geo.latitude)));
        this.mLongitudeE7 = ((int)(10000000.0D * PrimitiveUtils.safeDouble(paramCheckin.location.geo.longitude)));
      }
    label154: for (this.mClusterId = paramCheckin.location.clusterId; ; this.mClusterId = null)
    {
      this.mPrecisionMeters = -1.0D;
      return;
      str1 = paramCheckin.location.name;
      break;
      label184: str2 = paramCheckin.location.address.name;
      break label60;
      label198: bool = false;
      break label104;
      label204: this.mHasCoordinates = false;
      this.mLongitudeE7 = 0;
      this.mLatitudeE7 = 0;
      break label154;
      this.mName = paramCheckin.name;
      this.mBestAddress = null;
      this.mHasCoordinates = false;
      this.mLongitudeE7 = 0;
      this.mLatitudeE7 = 0;
    }
  }

  private DbLocation(Place paramPlace)
  {
    if (paramPlace == null)
      throw new IllegalArgumentException();
    this.mType = 3;
    this.mName = paramPlace.name;
    String str;
    boolean bool;
    if (paramPlace.address == null)
    {
      str = null;
      this.mBestAddress = str;
      if (paramPlace.geo == null)
        break label144;
      if ((paramPlace.geo.latitude == null) || (paramPlace.geo.longitude == null))
        break label139;
      bool = true;
      label72: this.mHasCoordinates = bool;
      this.mLatitudeE7 = ((int)(10000000.0D * PrimitiveUtils.safeDouble(paramPlace.geo.latitude)));
      this.mLongitudeE7 = ((int)(10000000.0D * PrimitiveUtils.safeDouble(paramPlace.geo.longitude)));
    }
    while (true)
    {
      this.mPrecisionMeters = -1.0D;
      this.mClusterId = null;
      return;
      str = paramPlace.address.name;
      break;
      label139: bool = false;
      break label72;
      label144: this.mHasCoordinates = false;
      this.mLongitudeE7 = 0;
      this.mLatitudeE7 = 0;
    }
  }

  public static DbLocation deserialize(byte[] paramArrayOfByte)
  {
    DbLocation localDbLocation = null;
    if (paramArrayOfByte == null)
      return localDbLocation;
    ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
    int i = localByteBuffer.getInt();
    String str1 = getShortString(localByteBuffer);
    String str2 = getShortString(localByteBuffer);
    int j;
    label40: int k;
    int m;
    double d;
    String str3;
    if (localByteBuffer.getInt() != 0)
    {
      j = 1;
      k = localByteBuffer.getInt();
      m = localByteBuffer.getInt();
      d = localByteBuffer.getDouble();
      str3 = getShortString(localByteBuffer);
      if (j == 0)
        break label121;
    }
    label121: for (Integer localInteger1 = Integer.valueOf(k); ; localInteger1 = null)
    {
      Integer localInteger2 = null;
      if (j != 0)
        localInteger2 = Integer.valueOf(m);
      localDbLocation = new DbLocation(i, localInteger1, localInteger2, str1, str2, str3, d);
      break;
      j = 0;
      break label40;
    }
  }

  public static byte[] serialize(DbLocation paramDbLocation)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(32);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    localDataOutputStream.writeInt(paramDbLocation.mType);
    putShortString(localDataOutputStream, paramDbLocation.mName);
    putShortString(localDataOutputStream, paramDbLocation.mBestAddress);
    if (paramDbLocation.mHasCoordinates);
    for (int i = 1; ; i = 0)
    {
      localDataOutputStream.writeInt(i);
      localDataOutputStream.writeInt(paramDbLocation.mLatitudeE7);
      localDataOutputStream.writeInt(paramDbLocation.mLongitudeE7);
      localDataOutputStream.writeDouble(paramDbLocation.mPrecisionMeters);
      putShortString(localDataOutputStream, paramDbLocation.mClusterId);
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      localDataOutputStream.close();
      return arrayOfByte;
    }
  }

  public static byte[] serialize(Checkin paramCheckin)
    throws IOException
  {
    return serialize(new DbLocation(paramCheckin));
  }

  public static byte[] serialize(Place paramPlace)
    throws IOException
  {
    return serialize(new DbLocation(paramPlace));
  }

  public int describeContents()
  {
    return 0;
  }

  public final android.location.Location getAndroidLocation()
  {
    android.location.Location localLocation = new android.location.Location(null);
    if (this.mHasCoordinates)
    {
      localLocation.setLatitude(this.mLatitudeE7 / 10000000.0D);
      localLocation.setLongitude(this.mLongitudeE7 / 10000000.0D);
    }
    if (this.mPrecisionMeters >= 0.0D)
      localLocation.setAccuracy((float)this.mPrecisionMeters);
    return localLocation;
  }

  public final String getBestAddress()
  {
    return this.mBestAddress;
  }

  public final String getClusterId()
  {
    return this.mClusterId;
  }

  public final int getLatitudeE7()
  {
    return this.mLatitudeE7;
  }

  public final String getLocationName()
  {
    String str;
    if (!TextUtils.isEmpty(this.mName))
      str = this.mName;
    while (true)
    {
      return str;
      if (!TextUtils.isEmpty(this.mBestAddress))
        str = this.mBestAddress;
      else
        str = "";
    }
  }

  public final int getLongitudeE7()
  {
    return this.mLongitudeE7;
  }

  public final String getName()
  {
    return this.mName;
  }

  public final double getPrecisionMeters()
  {
    return this.mPrecisionMeters;
  }

  public final boolean hasCoordinates()
  {
    return this.mHasCoordinates;
  }

  public final boolean isCoarse()
  {
    if (this.mType == 2);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isPrecise()
  {
    int i = 1;
    if (this.mType == i);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final boolean isSamePlace(DbLocation paramDbLocation)
  {
    boolean bool = true;
    if (this == paramDbLocation);
    while (true)
    {
      return bool;
      if (paramDbLocation == null)
        bool = false;
      else if (((!isPrecise()) || (!paramDbLocation.isPrecise())) && ((!isCoarse()) || (!paramDbLocation.isCoarse())) && ((this.mType != 3) || (paramDbLocation.mType != 3) || (!TextUtils.equals(this.mName, paramDbLocation.mName)) || (!TextUtils.equals(this.mBestAddress, paramDbLocation.mBestAddress)) || (this.mHasCoordinates != paramDbLocation.mHasCoordinates) || (this.mLatitudeE7 != paramDbLocation.mLatitudeE7) || (this.mLongitudeE7 != paramDbLocation.mLongitudeE7)))
        bool = false;
    }
  }

  public final com.google.api.services.plusi.model.Location toProtocolObject()
  {
    com.google.api.services.plusi.model.Location localLocation = new com.google.api.services.plusi.model.Location();
    localLocation.locationTag = this.mName;
    localLocation.bestAddress = this.mBestAddress;
    localLocation.clusterId = this.mClusterId;
    if (this.mHasCoordinates)
    {
      localLocation.latitudeE7 = Integer.valueOf(this.mLatitudeE7);
      localLocation.longitudeE7 = Integer.valueOf(this.mLongitudeE7);
    }
    if (this.mPrecisionMeters >= 0.0D)
      localLocation.precisionMeters = Double.valueOf(this.mPrecisionMeters);
    return localLocation;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("LocationValue type: ");
    String str;
    switch (this.mType)
    {
    default:
      str = "unknown(" + this.mType + ")";
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return str + ", name: " + this.mName + ", addr: " + this.mBestAddress + ", hasCoord: " + this.mHasCoordinates + ", latE7: " + this.mLatitudeE7 + ", lngE7: " + this.mLongitudeE7 + ", cluster: " + this.mClusterId + ", precision: " + this.mPrecisionMeters;
      str = "precise";
      continue;
      str = "coarse";
      continue;
      str = "place";
    }
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mType);
    paramParcel.writeString(this.mName);
    paramParcel.writeString(this.mBestAddress);
    if (this.mHasCoordinates);
    for (int i = 1; ; i = 0)
    {
      paramParcel.writeInt(i);
      paramParcel.writeInt(this.mLatitudeE7);
      paramParcel.writeInt(this.mLongitudeE7);
      paramParcel.writeDouble(this.mPrecisionMeters);
      paramParcel.writeString(this.mClusterId);
      return;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbLocation
 * JD-Core Version:    0.6.2
 */