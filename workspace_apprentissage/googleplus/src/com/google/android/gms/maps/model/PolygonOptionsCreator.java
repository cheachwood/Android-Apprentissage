package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.bm;
import com.google.android.gms.internal.bm.a;
import java.util.ArrayList;
import java.util.List;

public final class PolygonOptionsCreator
  implements Parcelable.Creator<PolygonOptions>
{
  static void a$4c96f4fe(PolygonOptions paramPolygonOptions, Parcel paramParcel)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramPolygonOptions.getVersionCode());
    ac.a(paramParcel, 2, paramPolygonOptions.getPoints());
    ac.b(paramParcel, 3, paramPolygonOptions.s());
    ac.a(paramParcel, 4, paramPolygonOptions.getStrokeWidth());
    ac.b(paramParcel, 5, paramPolygonOptions.getStrokeColor());
    ac.b(paramParcel, 6, paramPolygonOptions.getFillColor());
    ac.a(paramParcel, 7, paramPolygonOptions.getZIndex());
    ac.a(paramParcel, 8, paramPolygonOptions.isVisible());
    ac.a(paramParcel, 9, paramPolygonOptions.isGeodesic());
    ac.c(paramParcel, i);
  }

  public final PolygonOptions createFromParcel(Parcel paramParcel)
  {
    float f1 = 0.0F;
    boolean bool1 = false;
    int i = bm.g(paramParcel);
    List localList = null;
    ArrayList localArrayList = new ArrayList();
    boolean bool2 = false;
    int j = 0;
    int k = 0;
    float f2 = 0.0F;
    int m = 0;
    while (paramParcel.dataPosition() < i)
    {
      int n = paramParcel.readInt();
      switch (0xFFFF & n)
      {
      default:
        bm.e(paramParcel, n);
        break;
      case 1:
        m = bm.h(paramParcel, n);
        break;
      case 2:
        localList = bm.b(paramParcel, n, LatLng.CREATOR);
        break;
      case 3:
        ClassLoader localClassLoader = getClass().getClassLoader();
        int i1 = bm.d(paramParcel, n);
        int i2 = paramParcel.dataPosition();
        paramParcel.readList(localArrayList, localClassLoader);
        paramParcel.setDataPosition(i1 + i2);
        break;
      case 4:
        f2 = bm.j(paramParcel, n);
        break;
      case 5:
        k = bm.h(paramParcel, n);
        break;
      case 6:
        j = bm.h(paramParcel, n);
        break;
      case 7:
        f1 = bm.j(paramParcel, n);
        break;
      case 8:
        bool2 = bm.f(paramParcel, n);
        break;
      case 9:
        bool1 = bm.f(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new bm.a("Overread allowed size end=" + i, paramParcel);
    return new PolygonOptions(m, localList, localArrayList, f2, k, j, f1, bool2, bool1);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.PolygonOptionsCreator
 * JD-Core Version:    0.6.2
 */