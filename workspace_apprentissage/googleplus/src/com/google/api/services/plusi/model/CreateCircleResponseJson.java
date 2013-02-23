package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CreateCircleResponseJson extends EsJson<CreateCircleResponse>
{
  static final CreateCircleResponseJson INSTANCE = new CreateCircleResponseJson();

  private CreateCircleResponseJson()
  {
    super(CreateCircleResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "category", DataCircleIdJson.class, "circleId", DataCirclePersonJson.class, "circlePerson", "nameSortKey", DataRevertCookieJson.class, "revertCookie", "versionInfo" });
  }

  public static CreateCircleResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CreateCircleResponseJson
 * JD-Core Version:    0.6.2
 */