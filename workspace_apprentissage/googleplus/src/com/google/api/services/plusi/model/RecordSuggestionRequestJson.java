package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RecordSuggestionRequestJson extends EsJson<RecordSuggestionRequest>
{
  static final RecordSuggestionRequestJson INSTANCE = new RecordSuggestionRequestJson();

  private RecordSuggestionRequestJson()
  {
    super(RecordSuggestionRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", DataSuggestionActionJson.class, "suggestionAction" });
  }

  public static RecordSuggestionRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RecordSuggestionRequestJson
 * JD-Core Version:    0.6.2
 */