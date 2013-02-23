package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetCelebritySuggestionsResponseJson extends EsJson<GetCelebritySuggestionsResponse>
{
  static final GetCelebritySuggestionsResponseJson INSTANCE = new GetCelebritySuggestionsResponseJson();

  private GetCelebritySuggestionsResponseJson()
  {
    super(GetCelebritySuggestionsResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataSuggestedCelebrityCategoryJson.class, "category", DataSuggestedPersonJson.class, "previewCeleb" });
  }

  public static GetCelebritySuggestionsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetCelebritySuggestionsResponseJson
 * JD-Core Version:    0.6.2
 */