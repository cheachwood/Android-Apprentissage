package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

public class PersonData
  implements Parcelable, Cloneable
{
  public static final Parcelable.Creator<PersonData> CREATOR = new Parcelable.Creator()
  {
  };
  private String mCompressedPhotoUrl;
  private String mEmail;
  private String mName;
  private String mObfuscatedId;

  private PersonData(Parcel paramParcel)
  {
    this.mObfuscatedId = paramParcel.readString();
    this.mName = paramParcel.readString();
    this.mEmail = paramParcel.readString();
    this.mCompressedPhotoUrl = paramParcel.readString();
  }

  public PersonData(String paramString1, String paramString2, String paramString3)
  {
    this(paramString1, paramString2, paramString3, null);
  }

  public PersonData(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.mObfuscatedId = paramString1;
    this.mName = paramString2;
    this.mEmail = paramString3;
    this.mCompressedPhotoUrl = paramString4;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof PersonData))
    {
      PersonData localPersonData = (PersonData)paramObject;
      if ((!TextUtils.equals(this.mObfuscatedId, localPersonData.mObfuscatedId)) || (!TextUtils.equals(this.mName, localPersonData.mName)) || (!TextUtils.equals(this.mEmail, localPersonData.mEmail)) || (!TextUtils.equals(this.mCompressedPhotoUrl, localPersonData.mCompressedPhotoUrl)));
    }
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final String getCompressedPhotoUrl()
  {
    return this.mCompressedPhotoUrl;
  }

  public final String getEmail()
  {
    return this.mEmail;
  }

  public final String getName()
  {
    return this.mName;
  }

  public final String getObfuscatedId()
  {
    return this.mObfuscatedId;
  }

  public int hashCode()
  {
    int i = 17;
    if (this.mObfuscatedId != null)
      i = 527 + this.mObfuscatedId.hashCode();
    if (this.mName != null)
      i = i * 31 + this.mName.hashCode();
    if (this.mEmail != null)
      i = i * 31 + this.mEmail.hashCode();
    if (this.mCompressedPhotoUrl != null)
      i = i * 31 + this.mCompressedPhotoUrl.hashCode();
    return i;
  }

  public String toString()
  {
    return "{PersonData id=" + this.mObfuscatedId + " name=" + this.mName + " email=" + this.mEmail + " compressed photo url=" + this.mCompressedPhotoUrl + "}";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mObfuscatedId);
    paramParcel.writeString(this.mName);
    paramParcel.writeString(this.mEmail);
    paramParcel.writeString(this.mCompressedPhotoUrl);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.PersonData
 * JD-Core Version:    0.6.2
 */