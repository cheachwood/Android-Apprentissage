package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EsAccount
  implements Parcelable
{
  public static final Parcelable.Creator<EsAccount> CREATOR = new Parcelable.Creator()
  {
  };
  private final String mDisplayName;
  private final String mGaiaId;
  private final int mIndex;
  private final boolean mIsChild;
  private final boolean mIsPlusPage;
  private final String mName;
  private final String mRealTimeChatParticipantId;

  private EsAccount(Parcel paramParcel)
  {
    this.mName = paramParcel.readString();
    this.mGaiaId = paramParcel.readString();
    this.mRealTimeChatParticipantId = ("g:" + this.mGaiaId);
    this.mDisplayName = paramParcel.readString();
    this.mIndex = paramParcel.readInt();
    int j;
    if (paramParcel.readInt() == i)
    {
      j = i;
      this.mIsChild = j;
      if (paramParcel.readInt() != i)
        break label95;
    }
    while (true)
    {
      this.mIsPlusPage = i;
      return;
      j = 0;
      break;
      label95: i = 0;
    }
  }

  public EsAccount(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    this.mName = paramString1;
    this.mGaiaId = paramString2;
    this.mRealTimeChatParticipantId = ("g:" + this.mGaiaId);
    this.mDisplayName = paramString3;
    this.mIsChild = paramBoolean1;
    this.mIsPlusPage = paramBoolean2;
    this.mIndex = paramInt;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = false;
    if (paramObject == null);
    while (true)
    {
      return bool1;
      boolean bool2 = paramObject instanceof EsAccount;
      bool1 = false;
      if (bool2)
      {
        EsAccount localEsAccount = (EsAccount)paramObject;
        boolean bool3 = this.mName.equals(localEsAccount.mName);
        bool1 = false;
        if (bool3)
          if ((this.mGaiaId == null) || (localEsAccount.mGaiaId == null))
            bool1 = true;
          else
            bool1 = this.mGaiaId.equals(localEsAccount.mGaiaId);
      }
    }
  }

  public final String getDisplayName()
  {
    return this.mDisplayName;
  }

  public final String getGaiaId()
  {
    if (this.mGaiaId == null)
      throw new IllegalStateException("Gaia id not yet set. Out of box not yet done?");
    return this.mGaiaId;
  }

  public final int getIndex()
  {
    return this.mIndex;
  }

  public final String getName()
  {
    return this.mName;
  }

  public final String getPersonId()
  {
    return "g:" + this.mGaiaId;
  }

  public final String getRealTimeChatParticipantId()
  {
    return this.mRealTimeChatParticipantId;
  }

  public final boolean hasGaiaId()
  {
    if (this.mGaiaId != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public int hashCode()
  {
    return this.mName.hashCode();
  }

  public final boolean isChild()
  {
    return this.mIsChild;
  }

  public final boolean isMyGaiaId(String paramString)
  {
    if (paramString == null);
    for (boolean bool = false; ; bool = paramString.equals(this.mGaiaId))
      return bool;
  }

  public final boolean isPlusPage()
  {
    return this.mIsPlusPage;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(64);
    localStringBuilder.append("Account name: ").append(this.mName);
    localStringBuilder.append(", Gaia id: ").append(this.mGaiaId);
    localStringBuilder.append(", Display name: ").append(this.mDisplayName);
    localStringBuilder.append(", Plotnikov index: ").append(this.mIndex);
    localStringBuilder.append(", isPlusPage: ").append(this.mIsPlusPage);
    return localStringBuilder.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = 1;
    paramParcel.writeString(this.mName);
    paramParcel.writeString(this.mGaiaId);
    paramParcel.writeString(this.mDisplayName);
    paramParcel.writeInt(this.mIndex);
    int j;
    if (this.mIsChild)
    {
      j = i;
      paramParcel.writeInt(j);
      if (!this.mIsPlusPage)
        break label69;
    }
    while (true)
    {
      paramParcel.writeInt(i);
      return;
      j = 0;
      break;
      label69: i = 0;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsAccount
 * JD-Core Version:    0.6.2
 */