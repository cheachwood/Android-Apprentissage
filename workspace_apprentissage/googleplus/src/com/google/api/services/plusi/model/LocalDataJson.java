package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocalDataJson extends EsJson<LocalData>
{
  static final LocalDataJson INSTANCE = new LocalDataJson();

  private LocalDataJson()
  {
    super(LocalData.class, new Object[] { "cid", "localReviewId" });
  }

  public static LocalDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocalDataJson
 * JD-Core Version:    0.6.2
 */