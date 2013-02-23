package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedSuggestionInfoJson extends EsJson<LoggedSuggestionInfo>
{
  static final LoggedSuggestionInfoJson INSTANCE = new LoggedSuggestionInfoJson();

  private LoggedSuggestionInfoJson()
  {
    super(LoggedSuggestionInfo.class, arrayOfObject);
  }

  public static LoggedSuggestionInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedSuggestionInfoJson
 * JD-Core Version:    0.6.2
 */