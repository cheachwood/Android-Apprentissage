package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedSquareJson extends EsJson<LoggedSquare>
{
  static final LoggedSquareJson INSTANCE = new LoggedSquareJson();

  private LoggedSquareJson()
  {
    super(LoggedSquare.class, arrayOfObject);
  }

  public static LoggedSquareJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedSquareJson
 * JD-Core Version:    0.6.2
 */