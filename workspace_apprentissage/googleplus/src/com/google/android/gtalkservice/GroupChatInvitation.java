package com.google.android.gtalkservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GroupChatInvitation
  implements Parcelable
{
  public static final Parcelable.Creator<GroupChatInvitation> CREATOR = new Parcelable.Creator()
  {
  };
  private long mGroupContactId;
  private String mInviter;
  private String mPassword;
  private String mReason;
  private String mRoomAddress;

  public GroupChatInvitation(Parcel paramParcel)
  {
    this.mRoomAddress = paramParcel.readString();
    this.mInviter = paramParcel.readString();
    this.mReason = paramParcel.readString();
    this.mPassword = paramParcel.readString();
    this.mGroupContactId = paramParcel.readLong();
  }

  public int describeContents()
  {
    return 0;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mRoomAddress);
    paramParcel.writeString(this.mInviter);
    paramParcel.writeString(this.mReason);
    paramParcel.writeString(this.mPassword);
    paramParcel.writeLong(this.mGroupContactId);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gtalkservice.GroupChatInvitation
 * JD-Core Version:    0.6.2
 */