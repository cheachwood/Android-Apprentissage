package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import java.util.List;

public class NotificationSettingsData
  implements Parcelable
{
  public static final Parcelable.Creator<NotificationSettingsData> CREATOR = new Parcelable.Creator()
  {
  };
  private final NotificationSettingsCategory[] mCategories;
  private final String mEmailAddress;
  private final String mMobileNotificationType;

  private NotificationSettingsData(Parcel paramParcel)
  {
    this.mEmailAddress = paramParcel.readString();
    this.mMobileNotificationType = paramParcel.readString();
    this.mCategories = ((NotificationSettingsCategory[])paramParcel.createTypedArray(NotificationSettingsCategory.CREATOR));
  }

  public NotificationSettingsData(String paramString1, String paramString2, List<NotificationSettingsCategory> paramList)
  {
    this.mEmailAddress = paramString1;
    this.mMobileNotificationType = paramString2;
    if (paramList != null)
    {
      this.mCategories = new NotificationSettingsCategory[paramList.size()];
      paramList.toArray(this.mCategories);
    }
    while (true)
    {
      return;
      this.mCategories = new NotificationSettingsCategory[0];
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public final int getCategoriesCount()
  {
    return this.mCategories.length;
  }

  public final NotificationSettingsCategory getCategory(int paramInt)
  {
    return this.mCategories[paramInt];
  }

  public final String getEmailAddress()
  {
    return this.mEmailAddress;
  }

  public final String getMobileNotificationType()
  {
    return this.mMobileNotificationType;
  }

  public String toString()
  {
    return Arrays.asList(this.mCategories).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mEmailAddress);
    paramParcel.writeString(this.mMobileNotificationType);
    paramParcel.writeTypedArray(this.mCategories, 0);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.NotificationSettingsData
 * JD-Core Version:    0.6.2
 */