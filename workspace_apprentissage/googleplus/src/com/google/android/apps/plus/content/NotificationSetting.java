package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.api.services.plusi.model.DataNotificationSettingsDeliveryOption;

public class NotificationSetting
  implements Parcelable
{
  public static final Parcelable.Creator<NotificationSetting> CREATOR = new Parcelable.Creator()
  {
  };
  private final DataNotificationSettingsDeliveryOption mSetting;

  private NotificationSetting(Parcel paramParcel)
  {
    this.mSetting = new DataNotificationSettingsDeliveryOption();
    this.mSetting.bucketId = paramParcel.readString();
    this.mSetting.offnetworkBucketId = paramParcel.readString();
    this.mSetting.category = paramParcel.readString();
    this.mSetting.description = paramParcel.readString();
    this.mSetting.enabledForEmail = readBoolean(paramParcel);
    this.mSetting.enabledForPhone = readBoolean(paramParcel);
  }

  public NotificationSetting(NotificationSetting paramNotificationSetting)
  {
    this.mSetting = new DataNotificationSettingsDeliveryOption();
    this.mSetting.bucketId = paramNotificationSetting.mSetting.bucketId;
    this.mSetting.offnetworkBucketId = paramNotificationSetting.mSetting.offnetworkBucketId;
    this.mSetting.category = paramNotificationSetting.mSetting.category;
    this.mSetting.description = paramNotificationSetting.mSetting.description;
    this.mSetting.enabledForEmail = paramNotificationSetting.mSetting.enabledForEmail;
    this.mSetting.enabledForPhone = paramNotificationSetting.mSetting.enabledForPhone;
  }

  public NotificationSetting(DataNotificationSettingsDeliveryOption paramDataNotificationSettingsDeliveryOption)
  {
    this.mSetting = paramDataNotificationSettingsDeliveryOption;
  }

  private static Boolean readBoolean(Parcel paramParcel)
  {
    int i = paramParcel.readInt();
    Boolean localBoolean;
    if (i == 1)
      localBoolean = Boolean.TRUE;
    while (true)
    {
      return localBoolean;
      if (i == 0)
        localBoolean = Boolean.FALSE;
      else
        localBoolean = null;
    }
  }

  private static void writeBoolean(Parcel paramParcel, Boolean paramBoolean)
  {
    int i;
    if (paramBoolean == null)
      i = -1;
    while (true)
    {
      paramParcel.writeInt(i);
      return;
      if (paramBoolean.booleanValue())
        i = 1;
      else
        i = 0;
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public final DataNotificationSettingsDeliveryOption getDeliveryOption()
  {
    return this.mSetting;
  }

  public final String getDescription()
  {
    return this.mSetting.description;
  }

  public final boolean isEnabled()
  {
    if ((this.mSetting.enabledForPhone != null) && (this.mSetting.enabledForPhone.booleanValue()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void setEnabled(boolean paramBoolean)
  {
    this.mSetting.enabledForPhone = Boolean.valueOf(paramBoolean);
  }

  public String toString()
  {
    return "{Setting " + this.mSetting.description + " enabled=" + this.mSetting.enabledForPhone + " id=" + this.mSetting.bucketId + " offNetId=" + this.mSetting.offnetworkBucketId + "}";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mSetting.bucketId);
    paramParcel.writeString(this.mSetting.offnetworkBucketId);
    paramParcel.writeString(this.mSetting.category);
    paramParcel.writeString(this.mSetting.description);
    writeBoolean(paramParcel, this.mSetting.enabledForEmail);
    writeBoolean(paramParcel, this.mSetting.enabledForPhone);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.NotificationSetting
 * JD-Core Version:    0.6.2
 */