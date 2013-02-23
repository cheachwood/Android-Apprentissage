package com.google.android.gsf;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GoogleLoginCredentialsResult
  implements Parcelable
{
  public static final Parcelable.Creator<GoogleLoginCredentialsResult> CREATOR = new Parcelable.Creator()
  {
  };
  private String mAccount;
  private Intent mCredentialsIntent;
  private String mCredentialsString;

  public GoogleLoginCredentialsResult()
  {
    this.mCredentialsString = null;
    this.mCredentialsIntent = null;
    this.mAccount = null;
  }

  private GoogleLoginCredentialsResult(Parcel paramParcel)
  {
    this.mAccount = paramParcel.readString();
    this.mCredentialsString = paramParcel.readString();
    int i = paramParcel.readInt();
    this.mCredentialsIntent = null;
    if (i == 1)
    {
      this.mCredentialsIntent = new Intent();
      this.mCredentialsIntent.readFromParcel(paramParcel);
      this.mCredentialsIntent.setExtrasClassLoader(getClass().getClassLoader());
    }
  }

  public int describeContents()
  {
    if (this.mCredentialsIntent != null);
    for (int i = this.mCredentialsIntent.describeContents(); ; i = 0)
      return i;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mAccount);
    paramParcel.writeString(this.mCredentialsString);
    if (this.mCredentialsIntent != null)
    {
      paramParcel.writeInt(1);
      this.mCredentialsIntent.writeToParcel(paramParcel, 0);
    }
    while (true)
    {
      return;
      paramParcel.writeInt(0);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gsf.GoogleLoginCredentialsResult
 * JD-Core Version:    0.6.2
 */