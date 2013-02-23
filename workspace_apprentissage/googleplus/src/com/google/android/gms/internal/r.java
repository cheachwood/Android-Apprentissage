package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.wallet.FullWallet;

public final class r
  implements Parcelable.Creator<FullWallet>
{
  public static void a(FullWallet paramFullWallet, Parcel paramParcel, int paramInt)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramFullWallet.mVersionCode);
    ac.a(paramParcel, 2, paramFullWallet.iss);
    ac.a(paramParcel, 3, paramFullWallet.aud);
    ac.a(paramParcel, 4, paramFullWallet.iat);
    ac.a(paramParcel, 5, paramFullWallet.exp);
    ac.a(paramParcel, 6, paramFullWallet.typ);
    ac.a(paramParcel, 7, paramFullWallet.googleTransactionId);
    ac.a(paramParcel, 8, paramFullWallet.merchantTransactionId);
    ac.a(paramParcel, 9, paramFullWallet.email);
    ac.a(paramParcel, 10, paramFullWallet.e, paramInt);
    ac.a(paramParcel, 11, paramFullWallet.billingAddress, paramInt);
    ac.a(paramParcel, 12, paramFullWallet.shippingAddress, paramInt);
    ac.c(paramParcel, i);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.r
 * JD-Core Version:    0.6.2
 */