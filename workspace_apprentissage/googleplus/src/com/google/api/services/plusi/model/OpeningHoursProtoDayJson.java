package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OpeningHoursProtoDayJson extends EsJson<OpeningHoursProtoDay>
{
  static final OpeningHoursProtoDayJson INSTANCE = new OpeningHoursProtoDayJson();

  private OpeningHoursProtoDayJson()
  {
    super(OpeningHoursProtoDay.class, new Object[] { "currentDay", "dayName", OpeningHoursProtoDayIntervalJson.class, "interval" });
  }

  public static OpeningHoursProtoDayJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OpeningHoursProtoDayJson
 * JD-Core Version:    0.6.2
 */