package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedRibbonOrderJson extends EsJson<LoggedRibbonOrder>
{
  static final LoggedRibbonOrderJson INSTANCE = new LoggedRibbonOrderJson();

  private LoggedRibbonOrderJson()
  {
    super(LoggedRibbonOrder.class, new Object[] { "componentId", "droppedArea", LoggedRibbonOrderOrderJson.class, "newOrder", LoggedRibbonOrderOrderJson.class, "prevOrder" });
  }

  public static LoggedRibbonOrderJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedRibbonOrderJson
 * JD-Core Version:    0.6.2
 */