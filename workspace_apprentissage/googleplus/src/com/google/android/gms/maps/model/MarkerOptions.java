package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.f;
import com.google.android.gms.internal.f.a;

public final class MarkerOptions
  implements al
{
  public static final MarkerOptionsCreator CREATOR = new MarkerOptionsCreator();
  private LatLng ak;
  private String al;
  private String am;
  private BitmapDescriptor an;
  private float ao = 0.5F;
  private float ap = 1.0F;
  private boolean aq;
  private boolean ar = true;
  private final int mVersionCode;

  public MarkerOptions()
  {
    this.mVersionCode = 1;
  }

  MarkerOptions(int paramInt, LatLng paramLatLng, String paramString1, String paramString2, IBinder paramIBinder, float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mVersionCode = paramInt;
    this.ak = paramLatLng;
    this.al = paramString1;
    this.am = paramString2;
    if (paramIBinder == null);
    for (BitmapDescriptor localBitmapDescriptor = null; ; localBitmapDescriptor = new BitmapDescriptor(f.a.E(paramIBinder)))
    {
      this.an = localBitmapDescriptor;
      this.ao = paramFloat1;
      this.ap = paramFloat2;
      this.aq = paramBoolean1;
      this.ar = paramBoolean2;
      return;
    }
  }

  public final int describeContents()
  {
    return 0;
  }

  final IBinder e()
  {
    if (this.an == null);
    for (IBinder localIBinder = null; ; localIBinder = this.an.G().asBinder())
      return localIBinder;
  }

  public final float getAnchorU()
  {
    return this.ao;
  }

  public final float getAnchorV()
  {
    return this.ap;
  }

  public final LatLng getPosition()
  {
    return this.ak;
  }

  public final String getSnippet()
  {
    return this.am;
  }

  public final String getTitle()
  {
    return this.al;
  }

  final int getVersionCode()
  {
    return this.mVersionCode;
  }

  public final boolean isDraggable()
  {
    return this.aq;
  }

  public final boolean isVisible()
  {
    return this.ar;
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    MarkerOptionsCreator.a(this, paramParcel, paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.MarkerOptions
 * JD-Core Version:    0.6.2
 */