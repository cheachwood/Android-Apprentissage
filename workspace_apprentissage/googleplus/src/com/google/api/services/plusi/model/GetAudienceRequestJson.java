package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetAudienceRequestJson extends EsJson<GetAudienceRequest>
{
  static final GetAudienceRequestJson INSTANCE = new GetAudienceRequestJson();

  private GetAudienceRequestJson()
  {
    super(GetAudienceRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "maxResults", "returnFullProfile", "updateId" });
  }

  public static GetAudienceRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetAudienceRequestJson
 * JD-Core Version:    0.6.2
 */