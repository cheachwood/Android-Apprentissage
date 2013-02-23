package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetActivitiesRequestJson extends EsJson<GetActivitiesRequest>
{
  static final GetActivitiesRequestJson INSTANCE = new GetActivitiesRequestJson();

  private GetActivitiesRequestJson()
  {
    super(GetActivitiesRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "continuesToken", ClientEmbedOptionsJson.class, "embedOptions", "enableTracing", "isUserInitiated", "ownerId", "perspectiveId", "skipPopularMixin", StreamParamsJson.class, "streamParams" });
  }

  public static GetActivitiesRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetActivitiesRequestJson
 * JD-Core Version:    0.6.2
 */