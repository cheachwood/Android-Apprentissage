package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OpeningHoursProtoDayIntervalJson extends EsJson<OpeningHoursProtoDayInterval>
{
  static final OpeningHoursProtoDayIntervalJson INSTANCE = new OpeningHoursProtoDayIntervalJson();

  private OpeningHoursProtoDayIntervalJson()
  {
    super(OpeningHoursProtoDayInterval.class, new Object[] { "value" });
  }

  public static OpeningHoursProtoDayIntervalJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OpeningHoursProtoDayIntervalJson
 * JD-Core Version:    0.6.2
 */