package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;

public final class TileOverlayOptionsCreator
  implements Parcelable.Creator<TileOverlayOptions>
{
  static void a$4b899d8a(TileOverlayOptions paramTileOverlayOptions, Parcel paramParcel)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramTileOverlayOptions.getVersionCode());
    ac.a(paramParcel, 2, paramTileOverlayOptions.E());
    ac.a(paramParcel, 3, paramTileOverlayOptions.isVisible());
    ac.a(paramParcel, 4, paramTileOverlayOptions.getZIndex());
    ac.c(paramParcel, i);
  }

  public static TileOverlayOptions createFromParcel(Parcel paramParcel)
  {
    boolean bool = false;
    int i = bm.g(paramParcel);
    IBinder localIBinder = null;
    float f = 0.0F;
    int j = 0;
    while (paramParcel.dataPosition() < i)
    {
      int k = paramParcel.readInt();
      switch (0xFFFF & k)
      {
      default:
        bm.e(paramParcel, k);
        break;
      case 1:
        j = bm.h(paramParcel, k);
        break;
      case 2:
        localIBinder = bm.m(paramParcel, k);
        break;
      case 3:
        bool = bm.f(paramParcel, k);
        break;
      case 4:
        f = bm.j(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new bm.a("Overread allowed size end=" + i, paramParcel);
    return new TileOverlayOptions(j, localIBinder, bool, f);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.TileOverlayOptionsCreator
 * JD-Core Version:    0.6.2
 */