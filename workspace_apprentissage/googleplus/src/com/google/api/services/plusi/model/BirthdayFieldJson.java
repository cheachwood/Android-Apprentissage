package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class BirthdayFieldJson extends EsJson<BirthdayField>
{
  static final BirthdayFieldJson INSTANCE = new BirthdayFieldJson();

  private BirthdayFieldJson()
  {
    super(BirthdayField.class, new Object[] { "day", MetadataJson.class, "metadata", "month", "showYear", "value", "year" });
  }

  public static BirthdayFieldJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.BirthdayFieldJson
 * JD-Core Version:    0.6.2
 */