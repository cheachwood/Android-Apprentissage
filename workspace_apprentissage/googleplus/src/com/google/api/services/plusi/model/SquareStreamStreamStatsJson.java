package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareStreamStreamStatsJson extends EsJson<SquareStreamStreamStats>
{
  static final SquareStreamStreamStatsJson INSTANCE = new SquareStreamStreamStatsJson();

  private SquareStreamStreamStatsJson()
  {
    super(SquareStreamStreamStats.class, new Object[] { "totalPostCount", "unreadPostCount" });
  }

  public static SquareStreamStreamStatsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareStreamStreamStatsJson
 * JD-Core Version:    0.6.2
 */