package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

public class CircleData
  implements Parcelable, Cloneable
{
  public static final Parcelable.Creator<CircleData> CREATOR = new Parcelable.Creator()
  {
  };
  private int mCircleType;
  private String mId;
  private String mName;
  private int mSize;

  private CircleData(Parcel paramParcel)
  {
    this.mId = paramParcel.readString();
    this.mName = paramParcel.readString();
    this.mCircleType = paramParcel.readInt();
    this.mSize = paramParcel.readInt();
  }

  public CircleData(String paramString1, int paramInt1, String paramString2, int paramInt2)
  {
    this.mId = paramString1;
    this.mCircleType = paramInt1;
    this.mName = paramString2;
    this.mSize = paramInt2;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof CircleData))
    {
      CircleData localCircleData = (CircleData)paramObject;
      if ((!TextUtils.equals(this.mId, localCircleData.mId)) || (!TextUtils.equals(this.mName, localCircleData.mName)) || (this.mCircleType != localCircleData.mCircleType) || (this.mSize != localCircleData.mSize));
    }
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final String getId()
  {
    return this.mId;
  }

  public final String getName()
  {
    return this.mName;
  }

  public final int getSize()
  {
    return this.mSize;
  }

  public final int getType()
  {
    return this.mCircleType;
  }

  public int hashCode()
  {
    int i = 17;
    if (this.mId != null)
      i = 527 + this.mId.hashCode();
    if (this.mName != null)
      i = i * 31 + this.mName.hashCode();
    return 31 * (i * 31 + this.mCircleType) + this.mSize;
  }

  public String toString()
  {
    return "{CircleData id=" + this.mId + " name=" + this.mName + " type=" + this.mCircleType + " size=" + this.mSize + "}";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mId);
    paramParcel.writeString(this.mName);
    paramParcel.writeInt(this.mCircleType);
    paramParcel.writeInt(this.mSize);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.CircleData
 * JD-Core Version:    0.6.2
 */