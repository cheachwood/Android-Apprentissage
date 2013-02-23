package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareStreamJson extends EsJson<SquareStream>
{
  static final SquareStreamJson INSTANCE = new SquareStreamJson();

  private SquareStreamJson()
  {
    super(SquareStream.class, new Object[] { "description", "id", "name", SquareStreamStreamStatsJson.class, "stats" });
  }

  public static SquareStreamJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareStreamJson
 * JD-Core Version:    0.6.2
 */