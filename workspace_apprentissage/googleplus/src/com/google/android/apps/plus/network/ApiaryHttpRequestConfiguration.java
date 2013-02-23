package com.google.android.apps.plus.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.util.EsLog;
import java.util.Locale;
import org.apache.http.client.methods.HttpRequestBase;

public class ApiaryHttpRequestConfiguration
  implements HttpRequestConfiguration
{
  private static ApiaryAuthDataFactory sAuthDataFactory = new ApiaryAuthDataFactory();
  private final EsAccount mAccount;
  private final String mBackendOverrideUrl;
  private final String mContentType;
  private final Context mContext;
  private final String mScope;

  public ApiaryHttpRequestConfiguration(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    this(paramContext, paramEsAccount, paramString1, paramString2, "application/json");
  }

  public ApiaryHttpRequestConfiguration(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3)
  {
    this.mContext = paramContext;
    this.mAccount = paramEsAccount;
    this.mScope = paramString1;
    this.mBackendOverrideUrl = paramString2;
    this.mContentType = paramString3;
  }

  public void addHeaders(HttpRequestBase paramHttpRequestBase)
  {
    paramHttpRequestBase.addHeader("Accept-Encoding", "gzip");
    paramHttpRequestBase.addHeader("Accept-Language", Locale.getDefault().toString());
    paramHttpRequestBase.addHeader("User-Agent", getUserAgentHeader(this.mContext));
    paramHttpRequestBase.addHeader("Content-Type", this.mContentType);
    if (this.mAccount != null);
    try
    {
      ApiaryAuthDataFactory.ApiaryAuthData localApiaryAuthData = ApiaryAuthDataFactory.getAuthData(this.mScope);
      String str2 = localApiaryAuthData.getAuthToken(this.mContext, this.mAccount.getName());
      String str3 = Long.toString(localApiaryAuthData.getAuthTime(str2).longValue());
      paramHttpRequestBase.addHeader("Authorization", "Bearer " + str2);
      paramHttpRequestBase.addHeader("X-Auth-Time", str3);
      String str1 = RealTimeChatService.getStickyC2dmId(this.mContext);
      if (str1 != null)
        paramHttpRequestBase.addHeader("X-Android-App-ID", str1);
      if (!TextUtils.isEmpty(this.mBackendOverrideUrl))
      {
        if (EsLog.isLoggable("HttpTransaction", 3))
          Log.d("HttpTransaction", "Setting backend override url " + this.mBackendOverrideUrl);
        paramHttpRequestBase.addHeader("X-Google-Backend-Override", this.mBackendOverrideUrl);
      }
      return;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("Cannot obtain authentication token", localException);
    }
  }

  protected String getUserAgentHeader(Context paramContext)
  {
    return UserAgent.from(paramContext) + " (gzip)";
  }

  public final void invalidateAuthToken()
  {
    if (this.mAccount != null);
    try
    {
      ApiaryAuthDataFactory.getAuthData(this.mScope).invalidateAuthToken(this.mContext, this.mAccount.getName());
      return;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("Cannot invalidate authentication token", localException);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ApiaryHttpRequestConfiguration
 * JD-Core Version:    0.6.2
 */