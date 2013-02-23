package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedRibbonClickJson extends EsJson<LoggedRibbonClick>
{
  static final LoggedRibbonClickJson INSTANCE = new LoggedRibbonClickJson();

  private LoggedRibbonClickJson()
  {
    super(LoggedRibbonClick.class, new Object[] { "clickArea", "componentId" });
  }

  public static LoggedRibbonClickJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedRibbonClickJson
 * JD-Core Version:    0.6.2
 */