package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetAudienceResponseJson extends EsJson<GetAudienceResponse>
{
  static final GetAudienceResponseJson INSTANCE = new GetAudienceResponseJson();

  private GetAudienceResponseJson()
  {
    super(GetAudienceResponse.class, new Object[] { "aclJson", TraceRecordsJson.class, "backendTrace", "gaiaAudienceCount", "nonGaiaAudienceCount", PersonJson.class, "person", "updateId" });
  }

  public static GetAudienceResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetAudienceResponseJson
 * JD-Core Version:    0.6.2
 */