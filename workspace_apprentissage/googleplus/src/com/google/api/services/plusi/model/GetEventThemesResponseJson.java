package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetEventThemesResponseJson extends EsJson<GetEventThemesResponse>
{
  static final GetEventThemesResponseJson INSTANCE = new GetEventThemesResponseJson();

  private GetEventThemesResponseJson()
  {
    super(GetEventThemesResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", ThemeJson.class, "themes" });
  }

  public static GetEventThemesResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetEventThemesResponseJson
 * JD-Core Version:    0.6.2
 */