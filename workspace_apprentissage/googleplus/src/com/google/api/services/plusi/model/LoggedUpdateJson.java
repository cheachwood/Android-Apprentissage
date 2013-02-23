package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedUpdateJson extends EsJson<LoggedUpdate>
{
  static final LoggedUpdateJson INSTANCE = new LoggedUpdateJson();

  private LoggedUpdateJson()
  {
    super(LoggedUpdate.class, new Object[] { "activityId" });
  }

  public static LoggedUpdateJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedUpdateJson
 * JD-Core Version:    0.6.2
 */