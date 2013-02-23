package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedProfileJson extends EsJson<LoggedProfile>
{
  static final LoggedProfileJson INSTANCE = new LoggedProfileJson();

  private LoggedProfileJson()
  {
    super(LoggedProfile.class, new Object[] { LoggedContactDataJson.class, "contactData", "numContacts", LoggedProfileDataJson.class, "profileData" });
  }

  public static LoggedProfileJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedProfileJson
 * JD-Core Version:    0.6.2
 */