package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedCircleJson extends EsJson<LoggedCircle>
{
  static final LoggedCircleJson INSTANCE = new LoggedCircleJson();

  private LoggedCircleJson()
  {
    super(LoggedCircle.class, arrayOfObject);
  }

  public static LoggedCircleJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedCircleJson
 * JD-Core Version:    0.6.2
 */