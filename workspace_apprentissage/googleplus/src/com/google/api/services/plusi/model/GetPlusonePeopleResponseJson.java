package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetPlusonePeopleResponseJson extends EsJson<GetPlusonePeopleResponse>
{
  static final GetPlusonePeopleResponseJson INSTANCE = new GetPlusonePeopleResponseJson();

  private GetPlusonePeopleResponseJson()
  {
    super(GetPlusonePeopleResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataPersonJson.class, "person" });
  }

  public static GetPlusonePeopleResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetPlusonePeopleResponseJson
 * JD-Core Version:    0.6.2
 */