package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedRhsComponentJson extends EsJson<LoggedRhsComponent>
{
  static final LoggedRhsComponentJson INSTANCE = new LoggedRhsComponentJson();

  private LoggedRhsComponentJson()
  {
    super(LoggedRhsComponent.class, new Object[] { "barType", LoggedRhsComponentTypeJson.class, "componentType", "index", LoggedRhsComponentItemJson.class, "item", LoggedRhsComponentTypeJson.class, "neighborInfo", LoggedSuggestionSummaryInfoJson.class, "suggestionSummaryInfo" });
  }

  public static LoggedRhsComponentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedRhsComponentJson
 * JD-Core Version:    0.6.2
 */