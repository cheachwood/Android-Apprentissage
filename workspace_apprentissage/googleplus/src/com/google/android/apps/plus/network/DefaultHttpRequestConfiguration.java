package com.google.android.apps.plus.network;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsAccount;
import java.net.URI;
import org.apache.http.client.methods.HttpRequestBase;

public final class DefaultHttpRequestConfiguration
  implements HttpRequestConfiguration
{
  private static final String[] sEnabledFeatures = { "278", "296", "301", "342", "383" };
  private final EsAccount mAccount;
  private final String mAuthTokenType;
  private final Context mContext;

  public DefaultHttpRequestConfiguration(Context paramContext, EsAccount paramEsAccount)
  {
    this(paramContext, paramEsAccount, "webupdates");
  }

  public DefaultHttpRequestConfiguration(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    this.mContext = paramContext;
    this.mAccount = paramEsAccount;
    this.mAuthTokenType = paramString;
  }

  public final void addHeaders(HttpRequestBase paramHttpRequestBase)
  {
    paramHttpRequestBase.addHeader("Cache-Control", "no-cache, no-transform");
    paramHttpRequestBase.addHeader("X-Wap-Proxy-Cookie", "none");
    paramHttpRequestBase.addHeader("X-Mobile-Google-Client", "1");
    paramHttpRequestBase.addHeader("Accept-Encoding", "gzip");
    paramHttpRequestBase.addHeader("User-Agent", UserAgent.from(this.mContext) + " (gzip)");
    if ((paramHttpRequestBase.getURI().getScheme().equalsIgnoreCase("https")) || (paramHttpRequestBase.getURI().getHost().equals("10.0.2.2")));
    try
    {
      String str = AuthData.getAuthToken(this.mContext, this.mAccount.getName(), this.mAuthTokenType);
      paramHttpRequestBase.addHeader("Authorization", "GoogleLogin auth=" + str);
      paramHttpRequestBase.addHeader("X-Mobile-Google-Client-Version", Integer.toString(ClientVersion.from(this.mContext)));
      if ((sEnabledFeatures != null) && (sEnabledFeatures.length > 0))
        paramHttpRequestBase.addHeader("X-Mobile-Google-Features", TextUtils.join(",", sEnabledFeatures));
      return;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("Cannot obtain authentication token", localException);
    }
  }

  public final void invalidateAuthToken()
  {
    try
    {
      AuthData.invalidateAuthToken(this.mContext, this.mAccount.getName(), this.mAuthTokenType);
      return;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("Cannot invalidate authentication token", localException);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.DefaultHttpRequestConfiguration
 * JD-Core Version:    0.6.2
 */