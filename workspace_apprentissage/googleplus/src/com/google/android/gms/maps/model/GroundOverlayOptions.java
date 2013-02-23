package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.f;
import com.google.android.gms.internal.f.a;

public final class GroundOverlayOptions
  implements al
{
  public static final GroundOverlayOptionsCreator CREATOR = new GroundOverlayOptionsCreator();
  private float ao = 0.5F;
  private float ap = 0.5F;
  private boolean ar = true;
  private float bT;
  private float bp;
  private BitmapDescriptor dN;
  private LatLng dO;
  private float dP;
  private LatLngBounds dQ;
  private float dR = 0.0F;
  private final int mVersionCode;
  private float v;

  public GroundOverlayOptions()
  {
    this.mVersionCode = 1;
  }

  GroundOverlayOptions(int paramInt, IBinder paramIBinder, LatLng paramLatLng, float paramFloat1, float paramFloat2, LatLngBounds paramLatLngBounds, float paramFloat3, float paramFloat4, boolean paramBoolean, float paramFloat5, float paramFloat6, float paramFloat7)
  {
    this.mVersionCode = paramInt;
    this.dN = new BitmapDescriptor(f.a.E(paramIBinder));
    this.dO = paramLatLng;
    this.bT = paramFloat1;
    this.dP = paramFloat2;
    this.dQ = paramLatLngBounds;
    this.v = paramFloat3;
    this.bp = paramFloat4;
    this.ar = paramBoolean;
    this.dR = paramFloat5;
    this.ao = paramFloat6;
    this.ap = paramFloat7;
  }

  final IBinder ag()
  {
    return this.dN.G().asBinder();
  }

  public final int describeContents()
  {
    return 0;
  }

  public final float getAnchorU()
  {
    return this.ao;
  }

  public final float getAnchorV()
  {
    return this.ap;
  }

  public final float getBearing()
  {
    return this.v;
  }

  public final LatLngBounds getBounds()
  {
    return this.dQ;
  }

  public final float getHeight()
  {
    return this.dP;
  }

  public final LatLng getLocation()
  {
    return this.dO;
  }

  public final float getTransparency()
  {
    return this.dR;
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

  public final boolean isVisible()
  {
    return this.ar;
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    GroundOverlayOptionsCreator.a(this, paramParcel, paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.GroundOverlayOptions
 * JD-Core Version:    0.6.2
 */