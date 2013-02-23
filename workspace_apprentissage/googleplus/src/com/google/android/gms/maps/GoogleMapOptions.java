package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R.styleable;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.bl;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptions
  implements al
{
  public static final GoogleMapOptionsCreator CREATOR = new GoogleMapOptionsCreator();
  private Boolean bA;
  private Boolean bB;
  private Boolean bC;
  private Boolean bD;
  private Boolean bu;
  private Boolean bv;
  private int bw = -1;
  private CameraPosition bx;
  private Boolean by;
  private Boolean bz;
  private final int mVersionCode;

  public GoogleMapOptions()
  {
    this.mVersionCode = 1;
  }

  GoogleMapOptions(int paramInt1, byte paramByte1, byte paramByte2, int paramInt2, CameraPosition paramCameraPosition, byte paramByte3, byte paramByte4, byte paramByte5, byte paramByte6, byte paramByte7, byte paramByte8)
  {
    this.mVersionCode = paramInt1;
    this.bu = bl.a(paramByte1);
    this.bv = bl.a(paramByte2);
    this.bw = paramInt2;
    this.bx = paramCameraPosition;
    this.by = bl.a(paramByte3);
    this.bz = bl.a(paramByte4);
    this.bA = bl.a(paramByte5);
    this.bB = bl.a(paramByte6);
    this.bC = bl.a(paramByte7);
    this.bD = bl.a(paramByte8);
  }

  public static GoogleMapOptions createFromAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    GoogleMapOptions localGoogleMapOptions;
    if (paramAttributeSet == null)
      localGoogleMapOptions = null;
    while (true)
    {
      return localGoogleMapOptions;
      TypedArray localTypedArray = paramContext.getResources().obtainAttributes(paramAttributeSet, R.styleable.MapAttrs);
      localGoogleMapOptions = new GoogleMapOptions();
      if (localTypedArray.hasValue(0))
        localGoogleMapOptions.bw = localTypedArray.getInt(0, -1);
      if (localTypedArray.hasValue(13))
        localGoogleMapOptions.bu = Boolean.valueOf(localTypedArray.getBoolean(13, false));
      if (localTypedArray.hasValue(12))
        localGoogleMapOptions.bv = Boolean.valueOf(localTypedArray.getBoolean(12, false));
      if (localTypedArray.hasValue(6))
        localGoogleMapOptions.bz = Boolean.valueOf(localTypedArray.getBoolean(6, true));
      if (localTypedArray.hasValue(7))
        localGoogleMapOptions.bD = Boolean.valueOf(localTypedArray.getBoolean(7, true));
      if (localTypedArray.hasValue(8))
        localGoogleMapOptions.bA = Boolean.valueOf(localTypedArray.getBoolean(8, true));
      if (localTypedArray.hasValue(9))
        localGoogleMapOptions.bC = Boolean.valueOf(localTypedArray.getBoolean(9, true));
      if (localTypedArray.hasValue(11))
        localGoogleMapOptions.bB = Boolean.valueOf(localTypedArray.getBoolean(11, true));
      if (localTypedArray.hasValue(10))
        localGoogleMapOptions.by = Boolean.valueOf(localTypedArray.getBoolean(10, true));
      localGoogleMapOptions.bx = CameraPosition.createFromAttributes(paramContext, paramAttributeSet);
      localTypedArray.recycle();
    }
  }

  final byte A()
  {
    return bl.b(this.bC);
  }

  final byte B()
  {
    return bl.b(this.bD);
  }

  public final int describeContents()
  {
    return 0;
  }

  public final CameraPosition getCamera()
  {
    return this.bx;
  }

  public final int getMapType()
  {
    return this.bw;
  }

  final int getVersionCode()
  {
    return this.mVersionCode;
  }

  final byte u()
  {
    return bl.b(this.bu);
  }

  final byte v()
  {
    return bl.b(this.bv);
  }

  final byte w()
  {
    return bl.b(this.by);
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    GoogleMapOptionsCreator.a(this, paramParcel, paramInt);
  }

  final byte x()
  {
    return bl.b(this.bz);
  }

  final byte y()
  {
    return bl.b(this.bA);
  }

  final byte z()
  {
    return bl.b(this.bB);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.GoogleMapOptions
 * JD-Core Version:    0.6.2
 */