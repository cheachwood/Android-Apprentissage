package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class WallJson extends EsJson<Wall>
{
  static final WallJson INSTANCE = new WallJson();

  private WallJson()
  {
    super(Wall.class, new Object[] { BooleanFieldJson.class, "enableRead", BooleanFieldJson.class, "enableWrite" });
  }

  public static WallJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.WallJson
 * JD-Core Version:    0.6.2
 */