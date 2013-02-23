package com.google.android.apps.plus.api;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;

public class CallToActionData
  implements Parcelable
{
  public static final Parcelable.Creator<CallToActionData> CREATOR = new Parcelable.Creator()
  {
  };
  public final String mDeepLinkId;
  public final String mLabel;
  public final String mUrl;

  public CallToActionData(Parcel paramParcel)
  {
    this.mLabel = paramParcel.readString();
    this.mUrl = paramParcel.readString();
    this.mDeepLinkId = paramParcel.readString();
  }

  public CallToActionData(String paramString1, String paramString2, String paramString3)
  {
    if ((TextUtils.isEmpty(paramString2)) && (TextUtils.isEmpty(paramString3)))
      throw new IllegalArgumentException("At least one of url or deepLinkId is required.");
    this.mLabel = paramString1;
    this.mUrl = paramString2;
    this.mDeepLinkId = paramString3;
  }

  public static CallToActionData fromExtras(Bundle paramBundle)
  {
    CallToActionData localCallToActionData = null;
    if (paramBundle == null);
    while (true)
    {
      return localCallToActionData;
      String str1 = paramBundle.getString("label");
      String str2 = paramBundle.getString("url");
      String str3 = paramBundle.getString("deepLinkId");
      if (TextUtils.isEmpty(str2))
      {
        boolean bool = TextUtils.isEmpty(str3);
        localCallToActionData = null;
        if (bool);
      }
      else
      {
        localCallToActionData = new CallToActionData(str1, str2, str3);
      }
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject);
    while (true)
    {
      return bool;
      if (!(paramObject instanceof CallToActionData))
      {
        bool = false;
      }
      else
      {
        CallToActionData localCallToActionData = (CallToActionData)paramObject;
        if ((!TextUtils.equals(this.mLabel, localCallToActionData.mLabel)) || (!TextUtils.equals(this.mUrl, localCallToActionData.mUrl)) || (!TextUtils.equals(this.mDeepLinkId, localCallToActionData.mDeepLinkId)))
          bool = false;
      }
    }
  }

  public int hashCode()
  {
    int i;
    int k;
    label27: int m;
    int n;
    if (this.mLabel == null)
    {
      i = 0;
      int j = 31 * (i + 527);
      if (this.mUrl != null)
        break label66;
      k = 0;
      m = 31 * (j + k);
      String str = this.mDeepLinkId;
      n = 0;
      if (str != null)
        break label77;
    }
    while (true)
    {
      return m + n;
      i = this.mLabel.hashCode();
      break;
      label66: k = this.mUrl.hashCode();
      break label27;
      label77: n = this.mDeepLinkId.hashCode();
    }
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mLabel);
    paramParcel.writeString(this.mUrl);
    paramParcel.writeString(this.mDeepLinkId);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.CallToActionData
 * JD-Core Version:    0.6.2
 */