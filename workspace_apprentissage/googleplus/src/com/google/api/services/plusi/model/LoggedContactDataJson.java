package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedContactDataJson extends EsJson<LoggedContactData>
{
  static final LoggedContactDataJson INSTANCE = new LoggedContactDataJson();

  private LoggedContactDataJson()
  {
    super(LoggedContactData.class, new Object[] { "addressCount", "emailCount", "hasName", "hasPhoto", "jobTitleCount", "phoneCount" });
  }

  public static LoggedContactDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedContactDataJson
 * JD-Core Version:    0.6.2
 */