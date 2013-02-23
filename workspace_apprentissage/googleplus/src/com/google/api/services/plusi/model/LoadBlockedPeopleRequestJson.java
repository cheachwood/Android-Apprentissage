package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoadBlockedPeopleRequestJson extends EsJson<LoadBlockedPeopleRequest>
{
  static final LoadBlockedPeopleRequestJson INSTANCE = new LoadBlockedPeopleRequestJson();

  private LoadBlockedPeopleRequestJson()
  {
    super(LoadBlockedPeopleRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing" });
  }

  public static LoadBlockedPeopleRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadBlockedPeopleRequestJson
 * JD-Core Version:    0.6.2
 */