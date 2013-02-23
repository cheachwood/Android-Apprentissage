package com.google.android.apps.plus.network;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.json.EsJson;
import com.google.android.apps.plus.json.GenericJson;
import com.google.android.apps.plus.util.Property;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class PlusiOperation<Request extends GenericJson, Response extends GenericJson> extends ApiaryOperation<Request, Response>
{
  private final String mLogTag;

  public PlusiOperation(Context paramContext, EsAccount paramEsAccount, String paramString, EsJson<Request> paramEsJson, EsJson<Response> paramEsJson1, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, createPlusiRequestUrl(paramString), Property.PLUS_BACKEND_URL.get(), "oauth2:https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.stream.read https://www.googleapis.com/auth/plus.stream.write https://www.googleapis.com/auth/plus.circles.write https://www.googleapis.com/auth/plus.circles.read https://www.googleapis.com/auth/plus.photos.readwrite https://www.googleapis.com/auth/plus.native", paramEsJson, paramEsJson1, paramIntent, paramOperationListener);
    this.mLogTag = truncateLogTagIfNecessary(paramString);
  }

  protected PlusiOperation(Context paramContext, EsAccount paramEsAccount, String paramString, EsJson<Request> paramEsJson, EsJson<Response> paramEsJson1, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, HttpRequestConfiguration paramHttpRequestConfiguration)
  {
    super(paramContext, paramEsAccount, createPlusiRequestUrl(paramString), Property.PLUS_BACKEND_URL.get(), "oauth2:https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.stream.read https://www.googleapis.com/auth/plus.stream.write https://www.googleapis.com/auth/plus.circles.write https://www.googleapis.com/auth/plus.circles.read https://www.googleapis.com/auth/plus.photos.readwrite https://www.googleapis.com/auth/plus.native", paramEsJson, paramEsJson1, paramIntent, paramOperationListener, paramHttpRequestConfiguration);
    this.mLogTag = truncateLogTagIfNecessary(paramString);
  }

  protected PlusiOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, Bundle paramBundle, EsJson<Request> paramEsJson, EsJson<Response> paramEsJson1, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, HttpRequestConfiguration paramHttpRequestConfiguration)
  {
    super(paramContext, paramEsAccount, createPlusiRequestUrl(paramString1, paramString2, paramBundle), Property.PLUS_BACKEND_URL.get(), "oauth2:https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.stream.read https://www.googleapis.com/auth/plus.stream.write https://www.googleapis.com/auth/plus.circles.write https://www.googleapis.com/auth/plus.circles.read https://www.googleapis.com/auth/plus.photos.readwrite https://www.googleapis.com/auth/plus.native", paramEsJson, paramEsJson1, paramIntent, paramOperationListener, paramHttpRequestConfiguration);
    this.mLogTag = truncateLogTagIfNecessary(paramString1);
  }

  private static String createPlusiRequestUrl(String paramString)
  {
    return createPlusiRequestUrl(paramString, null, null);
  }

  private static String createPlusiRequestUrl(String paramString1, String paramString2, Bundle paramBundle)
  {
    if (paramString1.startsWith("/"))
      paramString1 = paramString1.substring(1);
    Uri.Builder localBuilder;
    if ((Property.ENABLE_DOGFOOD_FEATURES.getBoolean()) && (Property.PLUS_FRONTEND_URL.get().startsWith("http:")))
    {
      localBuilder = Uri.parse(Property.PLUS_FRONTEND_URL.get()).buildUpon();
      if (paramString2 == null)
        break label141;
      localBuilder.path(paramString2);
    }
    while (true)
    {
      if (paramBundle == null)
        break label155;
      Iterator localIterator = paramBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        localBuilder.appendQueryParameter(str4, paramBundle.getString(str4));
      }
      localBuilder = new Uri.Builder();
      localBuilder.scheme("https").authority(Property.PLUS_FRONTEND_URL.get());
      break;
      label141: localBuilder.path(Property.PLUS_FRONTEND_PATH.get());
    }
    label155: localBuilder.appendEncodedPath(paramString1);
    String str1 = Property.TRACING_TOKEN.get();
    if (!TextUtils.isEmpty(str1))
    {
      String str2 = Property.TRACING_TOKEN_2.get();
      if (!TextUtils.isEmpty(str2))
        str1 = str1 + str2;
      String str3 = Property.TRACING_PATH.get();
      if ((!TextUtils.isEmpty(str3)) && (Pattern.matches(str3, paramString1)))
      {
        localBuilder.appendQueryParameter("trace", "token:" + str1);
        if (!TextUtils.isEmpty(Property.TRACING_LEVEL.get()))
          localBuilder.appendQueryParameter("trace.deb", Property.TRACING_LEVEL.get());
      }
    }
    return localBuilder.build().toString();
  }

  private static String truncateLogTagIfNecessary(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 23))
      paramString = paramString.substring(0, 23);
    return paramString;
  }

  protected final String getLogTag()
  {
    return this.mLogTag;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.PlusiOperation
 * JD-Core Version:    0.6.2
 */