package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.wallet.ProxyCard;

public final class an
  implements Parcelable.Creator<ProxyCard>
{
  public static void a$4f52da30(ProxyCard paramProxyCard, Parcel paramParcel)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramProxyCard.mVersionCode);
    ac.a(paramParcel, 2, paramProxyCard.E);
    ac.a(paramParcel, 3, paramProxyCard.F);
    ac.b(paramParcel, 4, paramProxyCard.G);
    ac.b(paramParcel, 5, paramProxyCard.H);
    ac.c(paramParcel, i);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.an
 * JD-Core Version:    0.6.2
 */