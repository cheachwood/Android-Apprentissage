package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RecordSuggestionResponseJson extends EsJson<RecordSuggestionResponse>
{
  static final RecordSuggestionResponseJson INSTANCE = new RecordSuggestionResponseJson();

  private RecordSuggestionResponseJson()
  {
    super(RecordSuggestionResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace" });
  }

  public static RecordSuggestionResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RecordSuggestionResponseJson
 * JD-Core Version:    0.6.2
 */