package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedFrameJson extends EsJson<LoggedFrame>
{
  static final LoggedFrameJson INSTANCE = new LoggedFrameJson();

  private LoggedFrameJson()
  {
    super(LoggedFrame.class, arrayOfObject);
  }

  public static LoggedFrameJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedFrameJson
 * JD-Core Version:    0.6.2
 */