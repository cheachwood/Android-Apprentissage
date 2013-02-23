package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedRhsComponentTypeJson extends EsJson<LoggedRhsComponentType>
{
  static final LoggedRhsComponentTypeJson INSTANCE = new LoggedRhsComponentTypeJson();

  private LoggedRhsComponentTypeJson()
  {
    super(LoggedRhsComponentType.class, new Object[] { "category", "currentlyVisible", "id" });
  }

  public static LoggedRhsComponentTypeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedRhsComponentTypeJson
 * JD-Core Version:    0.6.2
 */