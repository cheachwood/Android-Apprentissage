package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedShareboxInfoJson extends EsJson<LoggedShareboxInfo>
{
  static final LoggedShareboxInfoJson INSTANCE = new LoggedShareboxInfoJson();

  private LoggedShareboxInfoJson()
  {
    super(LoggedShareboxInfo.class, new Object[] { "alsoSendEmail", "autocheckedAlsoSendEmail", "emailRecipients", "postSize", "postingMode", "shareTargetType", "shareType", "sharedToEmptyCircles" });
  }

  public static LoggedShareboxInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedShareboxInfoJson
 * JD-Core Version:    0.6.2
 */