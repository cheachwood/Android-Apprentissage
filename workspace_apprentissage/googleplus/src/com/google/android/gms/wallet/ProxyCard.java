package com.google.android.gms.wallet;

import android.os.Parcel;
import com.google.android.gms.internal.al;
import com.google.android.gms.internal.an;

public final class ProxyCard
  implements al
{
  public static final an CREATOR = new an();
  public String E;
  public String F;
  public int G;
  public int H;
  public int mVersionCode = 1;

  public final int describeContents()
  {
    return 0;
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    an.a$4f52da30(this, paramParcel);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.wallet.ProxyCard
 * JD-Core Version:    0.6.2
 */