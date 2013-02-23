package com.google.android.gms.maps;

import com.google.android.gms.internal.bj;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;

public final class CameraUpdateFactory
{
  private static ICameraUpdateFactoryDelegate ch;

  static void a(ICameraUpdateFactoryDelegate paramICameraUpdateFactoryDelegate)
  {
    ch = (ICameraUpdateFactoryDelegate)bj.c(paramICameraUpdateFactoryDelegate);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.CameraUpdateFactory
 * JD-Core Version:    0.6.2
 */