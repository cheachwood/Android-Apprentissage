package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class BirthdayDataJson extends EsJson<BirthdayData>
{
  static final BirthdayDataJson INSTANCE = new BirthdayDataJson();

  private BirthdayDataJson()
  {
    super(BirthdayData.class, new Object[] { CommonPersonJson.class, "person", "year" });
  }

  public static BirthdayDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.BirthdayDataJson
 * JD-Core Version:    0.6.2
 */