package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.internal.al;
import java.util.ArrayList;
import java.util.List;

public final class PolygonOptions
  implements al
{
  public static final PolygonOptionsCreator CREATOR = new PolygonOptionsCreator();
  private boolean ar = true;
  private final List<LatLng> bk;
  private final List<List<LatLng>> bl;
  private float bm = 10.0F;
  private int bn = -16777216;
  private int bo = 0;
  private float bp = 0.0F;
  private boolean bq = false;
  private final int mVersionCode;

  public PolygonOptions()
  {
    this.mVersionCode = 1;
    this.bk = new ArrayList();
    this.bl = new ArrayList();
  }

  PolygonOptions(int paramInt1, List<LatLng> paramList, List paramList1, float paramFloat1, int paramInt2, int paramInt3, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mVersionCode = paramInt1;
    this.bk = paramList;
    this.bl = paramList1;
    this.bm = paramFloat1;
    this.bn = paramInt2;
    this.bo = paramInt3;
    this.bp = paramFloat2;
    this.ar = paramBoolean1;
    this.bq = paramBoolean2;
  }

  public final int describeContents()
  {
    return 0;
  }

  public final int getFillColor()
  {
    return this.bo;
  }

  public final List<LatLng> getPoints()
  {
    return this.bk;
  }

  public final int getStrokeColor()
  {
    return this.bn;
  }

  public final float getStrokeWidth()
  {
    return this.bm;
  }

  final int getVersionCode()
  {
    return this.mVersionCode;
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

  final List s()
  {
    return this.bl;
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    PolygonOptionsCreator.a$4c96f4fe(this, paramParcel);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.PolygonOptions
 * JD-Core Version:    0.6.2
 */