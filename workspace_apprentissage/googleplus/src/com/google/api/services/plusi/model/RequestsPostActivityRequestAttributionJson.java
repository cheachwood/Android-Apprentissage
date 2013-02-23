package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RequestsPostActivityRequestAttributionJson extends EsJson<RequestsPostActivityRequestAttribution>
{
  static final RequestsPostActivityRequestAttributionJson INSTANCE = new RequestsPostActivityRequestAttributionJson();

  private RequestsPostActivityRequestAttributionJson()
  {
    super(RequestsPostActivityRequestAttribution.class, new Object[] { "androidAppName", "appName", "clientId", "iosBundleId" });
  }

  public static RequestsPostActivityRequestAttributionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RequestsPostActivityRequestAttributionJson
 * JD-Core Version:    0.6.2
 */