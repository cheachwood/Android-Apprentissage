package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.api.services.plusi.model.GetMobileSettingsResponse;
import com.google.api.services.plusi.model.MobilePreference;
import com.google.api.services.plusi.model.MobileSettingsUser;
import com.google.api.services.plusi.model.MobileSettingsUserInfo;
import com.google.api.services.plusi.model.ShareboxSettings;
import com.google.api.services.plusi.model.ShareboxSettingsJson;
import java.util.List;

public class AccountSettingsData
  implements Parcelable
{
  public static final Parcelable.Creator<AccountSettingsData> CREATOR = new Parcelable.Creator()
  {
  };
  private boolean mIsChild;
  private String[] mPlusPageIds;
  private String[] mPlusPageNames;
  private String[] mPlusPagePhotoUrls;
  private ShareboxSettings mShareboxSettings;
  private String mUserDisplayName;
  private String mUserGaiaId;
  private Long mWarmWelcomeTimestamp;

  private AccountSettingsData(Parcel paramParcel)
  {
    this.mUserGaiaId = paramParcel.readString();
    this.mUserDisplayName = paramParcel.readString();
    boolean bool;
    String str;
    if (paramParcel.readInt() == 1)
    {
      bool = true;
      this.mIsChild = bool;
      str = paramParcel.readString();
      if (str == null)
        break label111;
    }
    label111: for (ShareboxSettings localShareboxSettings = (ShareboxSettings)ShareboxSettingsJson.getInstance().fromString(str); ; localShareboxSettings = null)
    {
      this.mShareboxSettings = localShareboxSettings;
      if (paramParcel.readInt() == 1)
        this.mWarmWelcomeTimestamp = Long.valueOf(paramParcel.readLong());
      this.mPlusPageNames = paramParcel.createStringArray();
      this.mPlusPageIds = paramParcel.createStringArray();
      this.mPlusPagePhotoUrls = paramParcel.createStringArray();
      return;
      bool = false;
      break;
    }
  }

  public AccountSettingsData(GetMobileSettingsResponse paramGetMobileSettingsResponse)
  {
    MobileSettingsUser localMobileSettingsUser;
    if (paramGetMobileSettingsResponse.user != null)
    {
      localMobileSettingsUser = paramGetMobileSettingsResponse.user;
      if ((localMobileSettingsUser.isChild == null) || (!localMobileSettingsUser.isChild.booleanValue()))
        break label104;
    }
    label104: for (boolean bool = true; ; bool = false)
    {
      this.mIsChild = bool;
      if (localMobileSettingsUser.info != null)
      {
        this.mUserGaiaId = localMobileSettingsUser.info.obfuscatedGaiaId;
        this.mUserDisplayName = localMobileSettingsUser.info.displayName;
      }
      setPlusPages(localMobileSettingsUser.plusPageInfo);
      if (paramGetMobileSettingsResponse.preference != null)
        this.mWarmWelcomeTimestamp = paramGetMobileSettingsResponse.preference.wwMainFlowAckTimestampMsec;
      this.mShareboxSettings = paramGetMobileSettingsResponse.shareboxSettings;
      return;
    }
  }

  private void setPlusPages(List<MobileSettingsUserInfo> paramList)
  {
    if (paramList != null);
    for (int i = paramList.size(); ; i = 0)
    {
      this.mPlusPageNames = new String[i];
      this.mPlusPageIds = new String[i];
      this.mPlusPagePhotoUrls = new String[i];
      for (int j = 0; j < i; j++)
      {
        MobileSettingsUserInfo localMobileSettingsUserInfo = (MobileSettingsUserInfo)paramList.get(j);
        this.mPlusPageNames[j] = localMobileSettingsUserInfo.displayName;
        this.mPlusPageIds[j] = localMobileSettingsUserInfo.obfuscatedGaiaId;
        this.mPlusPagePhotoUrls[j] = localMobileSettingsUserInfo.photoUrl;
      }
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public final int getNumPlusPages()
  {
    return this.mPlusPageIds.length;
  }

  public final String getPlusPageId(int paramInt)
  {
    return this.mPlusPageIds[paramInt];
  }

  public final String getPlusPageName(int paramInt)
  {
    return this.mPlusPageNames[paramInt];
  }

  public final String getPlusPagePhotoUrl(int paramInt)
  {
    return this.mPlusPagePhotoUrls[paramInt];
  }

  public final ShareboxSettings getShareboxSettings()
  {
    return this.mShareboxSettings;
  }

  public final String getUserDisplayName()
  {
    return this.mUserDisplayName;
  }

  public final String getUserGaiaId()
  {
    return this.mUserGaiaId;
  }

  public final String getUserPhotoUrl()
  {
    return null;
  }

  public final Long getWarmWelcomeTimestamp()
  {
    return this.mWarmWelcomeTimestamp;
  }

  public final boolean isChild()
  {
    return this.mIsChild;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mUserGaiaId);
    paramParcel.writeString(this.mUserDisplayName);
    int i;
    String str;
    if (this.mIsChild)
    {
      i = 1;
      paramParcel.writeInt(i);
      if (this.mShareboxSettings == null)
        break label108;
      str = ShareboxSettingsJson.getInstance().toString(this.mShareboxSettings);
      label49: paramParcel.writeString(str);
      if (this.mWarmWelcomeTimestamp == null)
        break label114;
      paramParcel.writeInt(1);
      paramParcel.writeLong(this.mWarmWelcomeTimestamp.longValue());
    }
    while (true)
    {
      paramParcel.writeStringArray(this.mPlusPageNames);
      paramParcel.writeStringArray(this.mPlusPageIds);
      paramParcel.writeStringArray(this.mPlusPagePhotoUrls);
      return;
      i = 0;
      break;
      label108: str = null;
      break label49;
      label114: paramParcel.writeInt(0);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.AccountSettingsData
 * JD-Core Version:    0.6.2
 */