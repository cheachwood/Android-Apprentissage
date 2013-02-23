package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.internal.al;
import java.util.ArrayList;
import java.util.List;

public final class PolylineOptions
  implements al
{
  public static final PolylineOptionsCreator CREATOR = new PolylineOptionsCreator();
  private boolean ar = true;
  private float bT = 10.0F;
  private int bU = -16777216;
  private final List<LatLng> bk;
  private float bp = 0.0F;
  private boolean bq = false;
  private final int mVersionCode;

  public PolylineOptions()
  {
    this.mVersionCode = 1;
    this.bk = new ArrayList();
  }

  PolylineOptions(int paramInt1, List paramList, float paramFloat1, int paramInt2, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mVersionCode = paramInt1;
    this.bk = paramList;
    this.bT = paramFloat1;
    this.bU = paramInt2;
    this.bp = paramFloat2;
    this.ar = paramBoolean1;
    this.bq = paramBoolean2;
  }

  public final int describeContents()
  {
    return 0;
  }

  public final int getColor()
  {
    return this.bU;
  }

  public final List<LatLng> getPoints()
  {
    return this.bk;
  }

  final int getVersionCode()
  {
    return this.mVersionCode;
  }

  public final float getWidth()
  {
    return this.bT;
  }

  public final float getZIndex()
  {
    return this.bp;
  }

  public final boolean isGeodesic()
  {
    return this.bq;
  }

  public final boolean isVisible()
  {
    return this.ar;
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    PolylineOptionsCreator.a$37f6d9f8(this, paramParcel);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.PolylineOptions
 * JD-Core Version:    0.6.2
 */