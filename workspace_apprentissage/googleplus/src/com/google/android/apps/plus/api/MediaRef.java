package com.google.android.apps.plus.api;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

public class MediaRef
  implements Parcelable
{
  public static final Parcelable.Creator<MediaRef> CREATOR = new Parcelable.Creator()
  {
  };
  private final String mDisplayName;
  private final Uri mLocalUri;
  private final String mOwnerGaiaId;
  private final long mPhotoId;
  private final MediaType mType;
  private final String mUrl;

  private MediaRef(Parcel paramParcel)
  {
    this.mOwnerGaiaId = paramParcel.readString();
    this.mPhotoId = paramParcel.readLong();
    this.mUrl = paramParcel.readString();
    String str = paramParcel.readString();
    if (str != null);
    for (this.mLocalUri = Uri.parse(str); ; this.mLocalUri = null)
    {
      this.mDisplayName = paramParcel.readString();
      this.mType = MediaType.valueOf(paramParcel.readInt());
      return;
    }
  }

  public MediaRef(String paramString1, long paramLong, String paramString2, Uri paramUri, MediaType paramMediaType)
  {
    this(paramString1, paramLong, paramString2, paramUri, null, paramMediaType);
  }

  public MediaRef(String paramString1, long paramLong, String paramString2, Uri paramUri, String paramString3, MediaType paramMediaType)
  {
    this.mOwnerGaiaId = paramString1;
    this.mPhotoId = paramLong;
    this.mUrl = paramString2;
    this.mLocalUri = paramUri;
    this.mDisplayName = paramString3;
    this.mType = paramMediaType;
  }

  public MediaRef(String paramString, MediaType paramMediaType)
  {
    this(null, 0L, paramString, null, null, paramMediaType);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof MediaRef;
    boolean bool2 = false;
    MediaRef localMediaRef;
    if (bool1)
    {
      localMediaRef = (MediaRef)paramObject;
      boolean bool3 = this.mPhotoId < localMediaRef.mPhotoId;
      bool2 = false;
      if (!bool3)
        break label38;
    }
    label38: boolean bool4;
    do
    {
      return bool2;
      bool4 = TextUtils.equals(this.mUrl, localMediaRef.mUrl);
      bool2 = false;
    }
    while (!bool4);
    Uri localUri1 = this.mLocalUri;
    Uri localUri2 = localMediaRef.mLocalUri;
    boolean bool5;
    if ((localUri1 != null) && (localUri2 != null))
      bool5 = localUri1.equals(localUri2);
    while (true)
    {
      bool2 = false;
      if (!bool5)
        break;
      MediaType localMediaType1 = this.mType;
      MediaType localMediaType2 = localMediaRef.mType;
      bool2 = false;
      if (localMediaType1 != localMediaType2)
        break;
      bool2 = true;
      break;
      if ((localUri1 == null) && (localUri2 == null))
        bool5 = true;
      else
        bool5 = false;
    }
  }

  public final String getDisplayName()
  {
    return this.mDisplayName;
  }

  public final Uri getLocalUri()
  {
    return this.mLocalUri;
  }

  public final String getOwnerGaiaId()
  {
    return this.mOwnerGaiaId;
  }

  public final long getPhotoId()
  {
    return this.mPhotoId;
  }

  public final MediaType getType()
  {
    return this.mType;
  }

  public final String getUrl()
  {
    return this.mUrl;
  }

  public final boolean hasLocalUri()
  {
    if (this.mLocalUri != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasPhotoId()
  {
    if (this.mPhotoId != 0L);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasUrl()
  {
    if (this.mUrl != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public int hashCode()
  {
    int i = (int)(this.mPhotoId ^ this.mPhotoId >>> 32);
    if (this.mOwnerGaiaId != null)
      i ^= this.mOwnerGaiaId.hashCode();
    if (this.mUrl != null)
      i ^= this.mUrl.hashCode();
    if (this.mLocalUri != null)
      i ^= this.mLocalUri.hashCode();
    return i;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append(super.toString()).append("( ");
    if (this.mDisplayName == null);
    for (String str = ""; ; str = this.mDisplayName)
      return str + " [g-" + this.mOwnerGaiaId + ", p-" + this.mPhotoId + "], " + this.mUrl + ", " + this.mLocalUri + ")";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mOwnerGaiaId);
    paramParcel.writeLong(this.mPhotoId);
    paramParcel.writeString(this.mUrl);
    if (this.mLocalUri != null)
      paramParcel.writeString(this.mLocalUri.toString());
    while (true)
    {
      paramParcel.writeString(this.mDisplayName);
      paramParcel.writeInt(this.mType.getValue());
      return;
      paramParcel.writeString(null);
    }
  }

  public static enum MediaType
  {
    private final int mValue;

    static
    {
      PANORAMA = new MediaType("PANORAMA", 2, 2);
      MediaType[] arrayOfMediaType = new MediaType[3];
      arrayOfMediaType[0] = IMAGE;
      arrayOfMediaType[1] = VIDEO;
      arrayOfMediaType[2] = PANORAMA;
    }

    private MediaType(int paramInt)
    {
      this.mValue = paramInt;
    }

    public static MediaType valueOf(int paramInt)
    {
      MediaType localMediaType;
      switch (paramInt)
      {
      default:
        localMediaType = null;
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        return localMediaType;
        localMediaType = IMAGE;
        continue;
        localMediaType = VIDEO;
        continue;
        localMediaType = PANORAMA;
      }
    }

    public final int getValue()
    {
      return this.mValue;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.MediaRef
 * JD-Core Version:    0.6.2
 */