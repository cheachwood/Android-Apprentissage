package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedSuggestionSummaryInfoJson extends EsJson<LoggedSuggestionSummaryInfo>
{
  static final LoggedSuggestionSummaryInfoJson INSTANCE = new LoggedSuggestionSummaryInfoJson();

  private LoggedSuggestionSummaryInfoJson()
  {
    super(LoggedSuggestionSummaryInfo.class, new Object[] { "experimentNames", "numberOfSuggestionsLoaded", "portraitVersion" });
  }

  public static LoggedSuggestionSummaryInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedSuggestionSummaryInfoJson
 * JD-Core Version:    0.6.2
 */