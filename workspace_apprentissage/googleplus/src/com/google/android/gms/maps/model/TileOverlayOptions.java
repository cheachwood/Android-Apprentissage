package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.ap;
import com.google.android.gms.internal.ap.a;

public final class TileOverlayOptions
  implements al
{
  public static final TileOverlayOptionsCreator CREATOR = new TileOverlayOptionsCreator();
  private boolean ar = true;
  private ap bJ;
  private TileProvider bK;
  private float bp;
  private final int mVersionCode;

  public TileOverlayOptions()
  {
    this.mVersionCode = 1;
  }

  TileOverlayOptions(int paramInt, IBinder paramIBinder, boolean paramBoolean, float paramFloat)
  {
    this.mVersionCode = paramInt;
    this.bJ = ap.a.L(paramIBinder);
    if (this.bJ == null);
    for (TileProvider local1 = null; ; local1 = new TileProvider()
    {
      private final ap cj = TileOverlayOptions.a(TileOverlayOptions.this);
    })
    {
      this.bK = local1;
      this.ar = paramBoolean;
      this.bp = paramFloat;
      return;
    }
  }

  final IBinder E()
  {
    return this.bJ.asBinder();
  }

  public final int describeContents()
  {
    return 0;
  }

  final int getVersionCode()
  {
    return this.mVersionCode;
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
    TileOverlayOptionsCreator.a$4b899d8a(this, paramParcel);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.TileOverlayOptions
 * JD-Core Version:    0.6.2
 */