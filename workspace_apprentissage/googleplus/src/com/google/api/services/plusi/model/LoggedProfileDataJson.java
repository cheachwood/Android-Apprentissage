package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedProfileDataJson extends EsJson<LoggedProfileData>
{
  static final LoggedProfileDataJson INSTANCE = new LoggedProfileDataJson();

  private LoggedProfileDataJson()
  {
    super(LoggedProfileData.class, new Object[] { "addressCount", "emailCount", "hasName", "hasPhoto", "jobTitleCount", "phoneCount" });
  }

  public static LoggedProfileDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedProfileDataJson
 * JD-Core Version:    0.6.2
 */