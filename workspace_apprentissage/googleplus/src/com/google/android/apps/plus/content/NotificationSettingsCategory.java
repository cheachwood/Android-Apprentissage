package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import java.util.List;

public class NotificationSettingsCategory
  implements Parcelable
{
  public static final Parcelable.Creator<NotificationSettingsCategory> CREATOR = new Parcelable.Creator()
  {
  };
  private final String mDescription;
  private final NotificationSetting[] mSettings;

  private NotificationSettingsCategory(Parcel paramParcel)
  {
    this.mDescription = paramParcel.readString();
    this.mSettings = ((NotificationSetting[])paramParcel.createTypedArray(NotificationSetting.CREATOR));
  }

  public NotificationSettingsCategory(String paramString, List<NotificationSetting> paramList)
  {
    this.mDescription = paramString;
    if (paramList != null)
    {
      this.mSettings = new NotificationSetting[paramList.size()];
      paramList.toArray(this.mSettings);
    }
    while (true)
    {
      return;
      this.mSettings = new NotificationSetting[0];
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public final String getDescription()
  {
    return this.mDescription;
  }

  public final NotificationSetting getSetting(int paramInt)
  {
    return this.mSettings[paramInt];
  }

  public final int getSettingsCount()
  {
    return this.mSettings.length;
  }

  public String toString()
  {
    return "Category: " + this.mDescription + " Settings: " + Arrays.asList(this.mSettings);
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mDescription);
    paramParcel.writeTypedArray(this.mSettings, 0);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.NotificationSettingsCategory
 * JD-Core Version:    0.6.2
 */