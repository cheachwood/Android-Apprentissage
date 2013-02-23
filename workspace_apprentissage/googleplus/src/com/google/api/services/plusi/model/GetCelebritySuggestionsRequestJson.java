package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetCelebritySuggestionsRequestJson extends EsJson<GetCelebritySuggestionsRequest>
{
  static final GetCelebritySuggestionsRequestJson INSTANCE = new GetCelebritySuggestionsRequestJson();

  private GetCelebritySuggestionsRequestJson()
  {
    super(GetCelebritySuggestionsRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "maxPerCategory" });
  }

  public static GetCelebritySuggestionsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetCelebritySuggestionsRequestJson
 * JD-Core Version:    0.6.2
 */