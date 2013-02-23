package com.google.android.apps.plus.network;

import java.io.Serializable;

public final class ApiaryApiInfo
  implements Serializable
{
  private static final long serialVersionUID = 3145206267302890026L;
  private final String mApiKey;
  private final String mCertificate;
  private final String mClientId;
  private final String mPackageName;
  private final String mSdkVersion;
  private final ApiaryApiInfo mSourceInfo;

  public ApiaryApiInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, null);
  }

  public ApiaryApiInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ApiaryApiInfo paramApiaryApiInfo)
  {
    this.mApiKey = paramString1;
    this.mClientId = paramString2;
    this.mPackageName = paramString3;
    this.mCertificate = paramString4;
    this.mSourceInfo = paramApiaryApiInfo;
    this.mSdkVersion = paramString5;
  }

  public final String getApiKey()
  {
    return this.mApiKey;
  }

  public final String getCertificate()
  {
    return this.mCertificate;
  }

  public final String getClientId()
  {
    return this.mClientId;
  }

  public final String getPackageName()
  {
    return this.mPackageName;
  }

  public final String getSdkVersion()
  {
    return this.mSdkVersion;
  }

  public final ApiaryApiInfo getSourceInfo()
  {
    return this.mSourceInfo;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ApiaryApiInfo
 * JD-Core Version:    0.6.2
 */