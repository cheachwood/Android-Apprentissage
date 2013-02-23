package com.google.android.apps.plus.network;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.external.PlatformContractUtils;
import org.apache.http.client.methods.HttpRequestBase;

public final class PlatformHttpRequestConfiguration extends ApiaryHttpRequestConfiguration
{
  private final ApiaryApiInfo mApiInfo;

  public PlatformHttpRequestConfiguration(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, ApiaryApiInfo paramApiaryApiInfo)
  {
    super(paramContext, paramEsAccount, paramString1, paramString2);
    this.mApiInfo = paramApiaryApiInfo;
  }

  public final void addHeaders(HttpRequestBase paramHttpRequestBase)
  {
    super.addHeaders(paramHttpRequestBase);
    paramHttpRequestBase.addHeader("X-Container-Url", PlatformContractUtils.getContainerUrl(this.mApiInfo));
  }

  protected final String getUserAgentHeader(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder(UserAgent.from(paramContext));
    localStringBuilder.append("; G+ SDK/");
    if (this.mApiInfo.getSdkVersion() == null);
    for (String str = "1.0.0"; ; str = this.mApiInfo.getSdkVersion())
    {
      localStringBuilder.append(str);
      localStringBuilder.append(";");
      return localStringBuilder.toString();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.PlatformHttpRequestConfiguration
 * JD-Core Version:    0.6.2
 */