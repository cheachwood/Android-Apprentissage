package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetActivitiesResponseJson extends EsJson<GetActivitiesResponse>
{
  static final GetActivitiesResponseJson INSTANCE = new GetActivitiesResponseJson();

  private GetActivitiesResponseJson()
  {
    super(GetActivitiesResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", ShareboxSettingsJson.class, "shareboxSettings", StreamJson.class, "stream" });
  }

  public static GetActivitiesResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetActivitiesResponseJson
 * JD-Core Version:    0.6.2
 */