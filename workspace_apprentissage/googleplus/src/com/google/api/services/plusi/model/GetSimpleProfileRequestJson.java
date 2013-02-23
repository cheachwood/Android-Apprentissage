package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetSimpleProfileRequestJson extends EsJson<GetSimpleProfileRequest>
{
  static final GetSimpleProfileRequestJson INSTANCE = new GetSimpleProfileRequestJson();

  private GetSimpleProfileRequestJson()
  {
    super(GetSimpleProfileRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "includeAclData", "includeIncomingMembership", "includeViewerCircles", "ownerId", "useCachedDataForCircles" });
  }

  public static GetSimpleProfileRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetSimpleProfileRequestJson
 * JD-Core Version:    0.6.2
 */