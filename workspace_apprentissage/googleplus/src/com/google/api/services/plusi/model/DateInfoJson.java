package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DateInfoJson extends EsJson<DateInfo>
{
  static final DateInfoJson INSTANCE = new DateInfoJson();

  private DateInfoJson()
  {
    super(DateInfo.class, new Object[] { "current", CoarseDateJson.class, "end", CoarseDateJson.class, "start" });
  }

  public static DateInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DateInfoJson
 * JD-Core Version:    0.6.2
 */