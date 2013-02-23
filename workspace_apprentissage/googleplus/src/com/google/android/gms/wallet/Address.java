package com.google.android.gms.wallet;

import android.os.Parcel;
import com.google.android.gms.internal.ag;
import com.google.android.gms.internal.al;

public final class Address
  implements al
{
  public static final ag CREATOR = new ag();
  public String N;
  public String O;
  public String P;
  public String Q;
  public String R;
  public String S;
  public String T;
  public String U;
  public boolean V;
  public String W;
  public int mVersionCode = 1;
  public String name;

  public final int describeContents()
  {
    return 0;
  }

  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ag.a$80dd846(this, paramParcel);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.wallet.Address
 * JD-Core Version:    0.6.2
 */