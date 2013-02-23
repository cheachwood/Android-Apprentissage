package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutStartContextLatencyMarksJson extends EsJson<HangoutStartContextLatencyMarks>
{
  static final HangoutStartContextLatencyMarksJson INSTANCE = new HangoutStartContextLatencyMarksJson();

  private HangoutStartContextLatencyMarksJson()
  {
    super(HangoutStartContextLatencyMarks.class, arrayOfObject);
  }

  public static HangoutStartContextLatencyMarksJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutStartContextLatencyMarksJson
 * JD-Core Version:    0.6.2
 */