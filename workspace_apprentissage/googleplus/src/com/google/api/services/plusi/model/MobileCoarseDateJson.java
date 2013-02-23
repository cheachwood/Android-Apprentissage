package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileCoarseDateJson extends EsJson<MobileCoarseDate>
{
  static final MobileCoarseDateJson INSTANCE = new MobileCoarseDateJson();

  private MobileCoarseDateJson()
  {
    super(MobileCoarseDate.class, new Object[] { "day", "month", "year" });
  }

  public static MobileCoarseDateJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileCoarseDateJson
 * JD-Core Version:    0.6.2
 */