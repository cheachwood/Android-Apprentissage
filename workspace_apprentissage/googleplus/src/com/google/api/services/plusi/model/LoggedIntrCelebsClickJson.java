package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedIntrCelebsClickJson extends EsJson<LoggedIntrCelebsClick>
{
  static final LoggedIntrCelebsClickJson INSTANCE = new LoggedIntrCelebsClickJson();

  private LoggedIntrCelebsClickJson()
  {
    super(LoggedIntrCelebsClick.class, new Object[] { "categoryId" });
  }

  public static LoggedIntrCelebsClickJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedIntrCelebsClickJson
 * JD-Core Version:    0.6.2
 */