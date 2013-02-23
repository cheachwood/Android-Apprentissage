package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.wallet.MaskedWallet;

public final class ah
  implements Parcelable.Creator<MaskedWallet>
{
  public static void a(MaskedWallet paramMaskedWallet, Parcel paramParcel, int paramInt)
  {
    int i = ac.b(paramParcel);
    ac.b(paramParcel, 1, paramMaskedWallet.mVersionCode);
    ac.a(paramParcel, 2, paramMaskedWallet.iss);
    ac.a(paramParcel, 3, paramMaskedWallet.aud);
    ac.a(paramParcel, 4, paramMaskedWallet.iat);
    ac.a(paramParcel, 5, paramMaskedWallet.exp);
    ac.a(paramParcel, 6, paramMaskedWallet.typ);
    ac.a(paramParcel, 7, paramMaskedWallet.googleTransactionId);
    ac.a(paramParcel, 8, paramMaskedWallet.merchantTransactionId);
    ac.a(paramParcel, 9, paramMaskedWallet.email);
    ac.a(paramParcel, 10, paramMaskedWallet.paymentDescriptions);
    ac.a(paramParcel, 11, paramMaskedWallet.billingAddress, paramInt);
    ac.a(paramParcel, 12, paramMaskedWallet.shippingAddress, paramInt);
    ac.c(paramParcel, i);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ah
 * JD-Core Version:    0.6.2
 */