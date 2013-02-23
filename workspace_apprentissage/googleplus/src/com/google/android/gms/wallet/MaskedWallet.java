package com.google.android.gms.wallet;

import android.os.Parcel;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.al;

public final class MaskedWallet
  implements al
{
  public static final ah CREATOR = new ah();
  public static final MaskedWallet DEFAULT_INSTANCE = new MaskedWallet();
  public String aud;
  public Address billingAddress;
  public String email;
  public long exp;
  public String googleTransactionId;
  public long iat;
  public String iss;
  public int mVersionCode = 1;
  public String merchantTransactionId;
  public String[] paymentDescriptions;
  public Address shippingAddress;
  public String typ;

  public final int describeContents()
  {
    return 0;
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ah.a(this, paramParcel, paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.wallet.MaskedWallet
 * JD-Core Version:    0.6.2
 */