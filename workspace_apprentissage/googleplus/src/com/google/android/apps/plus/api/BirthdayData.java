package com.google.android.apps.plus.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class BirthdayData
  implements Parcelable
{
  public static final Parcelable.Creator<BirthdayData> CREATOR = new Parcelable.Creator()
  {
  };
  private String mGaiaId;
  private String mName;
  private int mYear;

  public BirthdayData(Parcel paramParcel)
  {
    this.mGaiaId = paramParcel.readString();
    this.mName = paramParcel.readString();
    this.mYear = paramParcel.readInt();
  }

  public BirthdayData(String paramString1, String paramString2, int paramInt)
  {
    this.mGaiaId = paramString1;
    this.mName = paramString2;
    this.mYear = paramInt;
  }

  public int describeContents()
  {
    return 0;
  }

  public final String getGaiaId()
  {
    return this.mGaiaId;
  }

  public final String getName()
  {
    return this.mName;
  }

  public final int getYear()
  {
    return this.mYear;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mGaiaId);
    paramParcel.writeString(this.mName);
    paramParcel.writeInt(this.mYear);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.BirthdayData
 * JD-Core Version:    0.6.2
 */