package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoadPeopleRequestJson extends EsJson<LoadPeopleRequest>
{
  static final LoadPeopleRequestJson INSTANCE = new LoadPeopleRequestJson();

  private LoadPeopleRequestJson()
  {
    super(LoadPeopleRequest.class, new Object[] { DataCircleMemberIdJson.class, "circleMemberId", ApiaryFieldsJson.class, "commonFields", "enableTracing", "includeIsFollowing", "includeMemberships" });
  }

  public static LoadPeopleRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadPeopleRequestJson
 * JD-Core Version:    0.6.2
 */