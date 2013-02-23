package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoadBlockedPeopleResponseJson extends EsJson<LoadBlockedPeopleResponse>
{
  static final LoadBlockedPeopleResponseJson INSTANCE = new LoadBlockedPeopleResponseJson();

  private LoadBlockedPeopleResponseJson()
  {
    super(LoadBlockedPeopleResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataCirclePersonJson.class, "person" });
  }

  public static LoadBlockedPeopleResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadBlockedPeopleResponseJson
 * JD-Core Version:    0.6.2
 */