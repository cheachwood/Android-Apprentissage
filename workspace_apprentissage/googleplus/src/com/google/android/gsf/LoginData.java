package com.google.android.gsf;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LoginData
  implements Parcelable
{
  public static final Parcelable.Creator<LoginData> CREATOR = new Parcelable.Creator()
  {
  };
  public String mAuthtoken = null;
  public String mCaptchaAnswer = null;
  public byte[] mCaptchaData = null;
  public String mCaptchaMimeType = null;
  public String mCaptchaToken = null;
  public String mEncryptedPassword = null;
  public int mFlags = 0;
  public String mJsonString = null;
  public String mOAuthAccessToken = null;
  public String mPassword = null;
  public String mService = null;
  public String mSid = null;
  public Status mStatus = null;
  public String mUsername = null;

  public LoginData()
  {
  }

  private LoginData(Parcel paramParcel)
  {
    this.mUsername = paramParcel.readString();
    this.mEncryptedPassword = paramParcel.readString();
    this.mPassword = paramParcel.readString();
    this.mService = paramParcel.readString();
    this.mCaptchaToken = paramParcel.readString();
    int i = paramParcel.readInt();
    String str;
    if (i == -1)
    {
      this.mCaptchaData = null;
      this.mCaptchaMimeType = paramParcel.readString();
      this.mCaptchaAnswer = paramParcel.readString();
      this.mFlags = paramParcel.readInt();
      str = paramParcel.readString();
      if (str != null)
        break label218;
    }
    label218: for (this.mStatus = null; ; this.mStatus = Status.valueOf(str))
    {
      this.mJsonString = paramParcel.readString();
      this.mSid = paramParcel.readString();
      this.mAuthtoken = paramParcel.readString();
      this.mOAuthAccessToken = paramParcel.readString();
      return;
      this.mCaptchaData = new byte[i];
      paramParcel.readByteArray(this.mCaptchaData);
      break;
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mUsername);
    paramParcel.writeString(this.mEncryptedPassword);
    paramParcel.writeString(this.mPassword);
    paramParcel.writeString(this.mService);
    paramParcel.writeString(this.mCaptchaToken);
    if (this.mCaptchaData == null)
    {
      paramParcel.writeInt(-1);
      paramParcel.writeString(this.mCaptchaMimeType);
      paramParcel.writeString(this.mCaptchaAnswer);
      paramParcel.writeInt(this.mFlags);
      if (this.mStatus != null)
        break label141;
      paramParcel.writeString(null);
    }
    while (true)
    {
      paramParcel.writeString(this.mJsonString);
      paramParcel.writeString(this.mSid);
      paramParcel.writeString(this.mAuthtoken);
      paramParcel.writeString(this.mOAuthAccessToken);
      return;
      paramParcel.writeInt(this.mCaptchaData.length);
      paramParcel.writeByteArray(this.mCaptchaData);
      break;
      label141: paramParcel.writeString(this.mStatus.name());
    }
  }

  public static enum Status
  {
    static
    {
      ACCOUNT_DISABLED = new Status("ACCOUNT_DISABLED", 1);
      BAD_USERNAME = new Status("BAD_USERNAME", 2);
      BAD_REQUEST = new Status("BAD_REQUEST", 3);
      LOGIN_FAIL = new Status("LOGIN_FAIL", 4);
      SERVER_ERROR = new Status("SERVER_ERROR", 5);
      MISSING_APPS = new Status("MISSING_APPS", 6);
      NO_GMAIL = new Status("NO_GMAIL", 7);
      NETWORK_ERROR = new Status("NETWORK_ERROR", 8);
      CAPTCHA = new Status("CAPTCHA", 9);
      CANCELLED = new Status("CANCELLED", 10);
      DELETED_GMAIL = new Status("DELETED_GMAIL", 11);
      OAUTH_MIGRATION_REQUIRED = new Status("OAUTH_MIGRATION_REQUIRED", 12);
      DMAGENT = new Status("DMAGENT", 13);
      Status[] arrayOfStatus = new Status[14];
      arrayOfStatus[0] = SUCCESS;
      arrayOfStatus[1] = ACCOUNT_DISABLED;
      arrayOfStatus[2] = BAD_USERNAME;
      arrayOfStatus[3] = BAD_REQUEST;
      arrayOfStatus[4] = LOGIN_FAIL;
      arrayOfStatus[5] = SERVER_ERROR;
      arrayOfStatus[6] = MISSING_APPS;
      arrayOfStatus[7] = NO_GMAIL;
      arrayOfStatus[8] = NETWORK_ERROR;
      arrayOfStatus[9] = CAPTCHA;
      arrayOfStatus[10] = CANCELLED;
      arrayOfStatus[11] = DELETED_GMAIL;
      arrayOfStatus[12] = OAUTH_MIGRATION_REQUIRED;
      arrayOfStatus[13] = DMAGENT;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gsf.LoginData
 * JD-Core Version:    0.6.2
 */