package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocalUserActivityJson extends EsJson<LocalUserActivity>
{
  static final LocalUserActivityJson INSTANCE = new LocalUserActivityJson();

  private LocalUserActivityJson()
  {
    super(LocalUserActivity.class, new Object[] { "numReviews" });
  }

  public static LocalUserActivityJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocalUserActivityJson
 * JD-Core Version:    0.6.2
 */