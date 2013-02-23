package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.wallet.Address;

public final class ag
  implements Parcelable.Creator<Address>
{
  public static void a$80dd846(Address paramAddress, Parcel paramParcel)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramAddress.mVersionCode);
    ac.a(paramParcel, 2, paramAddress.name);
    ac.a(paramParcel, 3, paramAddress.N);
    ac.a(paramParcel, 4, paramAddress.O);
    ac.a(paramParcel, 5, paramAddress.P);
    ac.a(paramParcel, 6, paramAddress.Q);
    ac.a(paramParcel, 7, paramAddress.R);
    ac.a(paramParcel, 8, paramAddress.S);
    ac.a(paramParcel, 9, paramAddress.T);
    ac.a(paramParcel, 10, paramAddress.U);
    ac.a(paramParcel, 11, paramAddress.V);
    ac.a(paramParcel, 12, paramAddress.W);
    ac.c(paramParcel, i);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ag
 * JD-Core Version:    0.6.2
 */