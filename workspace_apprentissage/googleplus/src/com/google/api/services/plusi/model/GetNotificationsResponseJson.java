package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetNotificationsResponseJson extends EsJson<GetNotificationsResponse>
{
  static final GetNotificationsResponseJson INSTANCE = new GetNotificationsResponseJson();

  private GetNotificationsResponseJson()
  {
    super(GetNotificationsResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataNotificationsDataJson.class, "notificationsData", "obfuscatedGaiaId", "refreshRequired", ViewerDataJson.class, "viewerData" });
  }

  public static GetNotificationsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetNotificationsResponseJson
 * JD-Core Version:    0.6.2
 */