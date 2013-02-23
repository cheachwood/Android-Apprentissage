package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedCircleMemberJson extends EsJson<LoggedCircleMember>
{
  static final LoggedCircleMemberJson INSTANCE = new LoggedCircleMemberJson();

  private LoggedCircleMemberJson()
  {
    super(LoggedCircleMember.class, arrayOfObject);
  }

  public static LoggedCircleMemberJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedCircleMemberJson
 * JD-Core Version:    0.6.2
 */