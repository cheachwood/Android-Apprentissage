package com.google.android.gms.wallet;

import android.os.Parcel;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.r;

public final class FullWallet
  implements al
{
  public static final r CREATOR = new r();
  public static final FullWallet DEFAULT_INSTANCE = new FullWallet();
  public String aud;
  public Address billingAddress;
  public ProxyCard e;
  public String email;
  public long exp;
  public String googleTransactionId;
  public long iat;
  public String iss;
  public int mVersionCode = 1;
  public String merchantTransactionId;
  public Address shippingAddress;
  public String typ;

  public final int describeContents()
  {
    return 0;
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    r.a(this, paramParcel, paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.wallet.FullWallet
 * JD-Core Version:    0.6.2
 */