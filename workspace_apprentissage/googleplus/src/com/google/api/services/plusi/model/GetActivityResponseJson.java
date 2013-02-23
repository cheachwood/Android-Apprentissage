package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetActivityResponseJson extends EsJson<GetActivityResponse>
{
  static final GetActivityResponseJson INSTANCE = new GetActivityResponseJson();

  private GetActivityResponseJson()
  {
    super(GetActivityResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", UpdateJson.class, "update" });
  }

  public static GetActivityResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetActivityResponseJson
 * JD-Core Version:    0.6.2
 */