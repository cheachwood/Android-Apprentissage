package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CoarseDateJson extends EsJson<CoarseDate>
{
  static final CoarseDateJson INSTANCE = new CoarseDateJson();

  private CoarseDateJson()
  {
    super(CoarseDate.class, new Object[] { "day", "deprecatedDisplayDate", "month", "year" });
  }

  public static CoarseDateJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CoarseDateJson
 * JD-Core Version:    0.6.2
 */