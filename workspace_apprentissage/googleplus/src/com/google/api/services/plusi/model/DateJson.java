package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DateJson extends EsJson<Date>
{
  static final DateJson INSTANCE = new DateJson();

  private DateJson()
  {
    super(Date.class, new Object[] { "date", "iso8601" });
  }

  public static DateJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DateJson
 * JD-Core Version:    0.6.2
 */