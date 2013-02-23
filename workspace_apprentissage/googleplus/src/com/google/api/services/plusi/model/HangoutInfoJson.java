package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutInfoJson extends EsJson<HangoutInfo>
{
  static final HangoutInfoJson INSTANCE = new HangoutInfoJson();

  private HangoutInfoJson()
  {
    super(HangoutInfo.class, new Object[] { "url" });
  }

  public static HangoutInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutInfoJson
 * JD-Core Version:    0.6.2
 */