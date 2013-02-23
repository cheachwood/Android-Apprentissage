package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

public class SquareTargetData
  implements Parcelable, Cloneable
{
  public static final Parcelable.Creator<SquareTargetData> CREATOR = new Parcelable.Creator()
  {
  };
  private String mSquareId;
  private String mSquareName;
  private String mSquareStreamId;
  private String mSquareStreamName;

  private SquareTargetData(Parcel paramParcel)
  {
    this.mSquareId = paramParcel.readString();
    this.mSquareName = paramParcel.readString();
    this.mSquareStreamId = paramParcel.readString();
    this.mSquareStreamName = paramParcel.readString();
  }

  public SquareTargetData(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.mSquareId = paramString1;
    this.mSquareName = paramString2;
    this.mSquareStreamId = paramString3;
    this.mSquareStreamName = paramString4;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof SquareTargetData))
    {
      SquareTargetData localSquareTargetData = (SquareTargetData)paramObject;
      if ((!TextUtils.equals(this.mSquareId, localSquareTargetData.mSquareId)) || (!TextUtils.equals(this.mSquareName, localSquareTargetData.mSquareName)) || (!TextUtils.equals(this.mSquareStreamId, localSquareTargetData.mSquareStreamId)) || (!TextUtils.equals(this.mSquareStreamName, localSquareTargetData.mSquareStreamName)));
    }
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final String getSquareId()
  {
    return this.mSquareId;
  }

  public final String getSquareName()
  {
    return this.mSquareName;
  }

  public final String getSquareStreamId()
  {
    return this.mSquareStreamId;
  }

  public final String getSquareStreamName()
  {
    return this.mSquareStreamName;
  }

  public int hashCode()
  {
    int i = 17;
    if (this.mSquareId != null)
      i = 527 + this.mSquareId.hashCode();
    if (this.mSquareName != null)
      i = i * 31 + this.mSquareName.hashCode();
    if (this.mSquareStreamId != null)
      i = i * 31 + this.mSquareStreamId.hashCode();
    if (this.mSquareStreamName != null)
      i = i * 31 + this.mSquareStreamName.hashCode();
    return i;
  }

  public String toString()
  {
    return "{SquareStreamData name=" + this.mSquareName + " stream=" + this.mSquareStreamName + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mSquareId);
    paramParcel.writeString(this.mSquareName);
    paramParcel.writeString(this.mSquareStreamId);
    paramParcel.writeString(this.mSquareStreamName);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.SquareTargetData
 * JD-Core Version:    0.6.2
 */