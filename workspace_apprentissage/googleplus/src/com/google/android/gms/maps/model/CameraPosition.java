package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R.styleable;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.bb;
import com.google.android.gms.internal.bb.a;
import com.google.android.gms.internal.bj;
import java.util.Arrays;

public final class CameraPosition
  implements al
{
  public static final CameraPositionCreator CREATOR = new CameraPositionCreator();
  public final float bearing;
  private final int mVersionCode;
  public final LatLng target;
  public final float tilt;
  public final float zoom;

  CameraPosition(int paramInt, LatLng paramLatLng, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    bj.a(paramLatLng, "null camera target");
    if ((0.0F <= paramFloat2) && (paramFloat2 <= 90.0F));
    for (int i = 1; i == 0; i = 0)
      throw new IllegalArgumentException(String.valueOf("Tilt needs to be between 0 and 90 inclusive"));
    this.mVersionCode = paramInt;
    this.target = paramLatLng;
    this.zoom = paramFloat1;
    this.tilt = (paramFloat2 + 0.0F);
    if (paramFloat3 <= 0.0D)
      paramFloat3 = 360.0F + paramFloat3 % 360.0F;
    this.bearing = (paramFloat3 % 360.0F);
  }

  public CameraPosition(LatLng paramLatLng, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this(1, paramLatLng, paramFloat1, paramFloat2, paramFloat3);
  }

  public static CameraPosition createFromAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    CameraPosition localCameraPosition;
    if (paramAttributeSet == null)
    {
      localCameraPosition = null;
      return localCameraPosition;
    }
    TypedArray localTypedArray = paramContext.getResources().obtainAttributes(paramAttributeSet, R.styleable.MapAttrs);
    if (localTypedArray.hasValue(2));
    for (float f1 = localTypedArray.getFloat(2, 0.0F); ; f1 = 0.0F)
    {
      if (localTypedArray.hasValue(3));
      for (float f2 = localTypedArray.getFloat(3, 0.0F); ; f2 = 0.0F)
      {
        LatLng localLatLng = new LatLng(f1, f2);
        Builder localBuilder = new Builder();
        localBuilder.target(localLatLng);
        if (localTypedArray.hasValue(5))
          localBuilder.zoom(localTypedArray.getFloat(5, 0.0F));
        if (localTypedArray.hasValue(1))
          localBuilder.bearing(localTypedArray.getFloat(1, 0.0F));
        if (localTypedArray.hasValue(4))
          localBuilder.tilt(localTypedArray.getFloat(4, 0.0F));
        localCameraPosition = localBuilder.build();
        break;
      }
    }
  }

  public final int describeContents()
  {
    return 0;
  }

  public final boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject);
    while (true)
    {
      return bool;
      if (!(paramObject instanceof CameraPosition))
      {
        bool = false;
      }
      else
      {
        CameraPosition localCameraPosition = (CameraPosition)paramObject;
        if ((!this.target.equals(localCameraPosition.target)) || (Float.floatToIntBits(this.zoom) != Float.floatToIntBits(localCameraPosition.zoom)) || (Float.floatToIntBits(this.tilt) != Float.floatToIntBits(localCameraPosition.tilt)) || (Float.floatToIntBits(this.bearing) != Float.floatToIntBits(localCameraPosition.bearing)))
          bool = false;
      }
    }
  }

  final int getVersionCode()
  {
    return this.mVersionCode;
  }

  public final int hashCode()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this.target;
    arrayOfObject[1] = Float.valueOf(this.zoom);
    arrayOfObject[2] = Float.valueOf(this.tilt);
    arrayOfObject[3] = Float.valueOf(this.bearing);
    return Arrays.hashCode(arrayOfObject);
  }

  public final String toString()
  {
    return bb.d(this).a("target", this.target).a("zoom", Float.valueOf(this.zoom)).a("tilt", Float.valueOf(this.tilt)).a("bearing", Float.valueOf(this.bearing)).toString();
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    CameraPositionCreator.a(this, paramParcel, paramInt);
  }

  public static final class Builder
  {
    private LatLng s;
    private float t;
    private float u;
    private float v;

    public final Builder bearing(float paramFloat)
    {
      this.v = paramFloat;
      return this;
    }

    public final CameraPosition build()
    {
      return new CameraPosition(this.s, this.t, this.u, this.v);
    }

    public final Builder target(LatLng paramLatLng)
    {
      this.s = paramLatLng;
      return this;
    }

    public final Builder tilt(float paramFloat)
    {
      this.u = paramFloat;
      return this;
    }

    public final Builder zoom(float paramFloat)
    {
      this.t = paramFloat;
      return this;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.CameraPosition
 * JD-Core Version:    0.6.2
 */