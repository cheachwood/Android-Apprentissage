package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedRibbonOrderOrderJson extends EsJson<LoggedRibbonOrderOrder>
{
  static final LoggedRibbonOrderOrderJson INSTANCE = new LoggedRibbonOrderOrderJson();

  private LoggedRibbonOrderOrderJson()
  {
    super(LoggedRibbonOrderOrder.class, new Object[] { "item", "morePosition" });
  }

  public static LoggedRibbonOrderOrderJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedRibbonOrderOrderJson
 * JD-Core Version:    0.6.2
 */