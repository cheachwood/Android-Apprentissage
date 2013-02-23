package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PopularUpdatesJson extends EsJson<PopularUpdates>
{
  static final PopularUpdatesJson INSTANCE = new PopularUpdatesJson();

  private PopularUpdatesJson()
  {
    super(PopularUpdates.class, new Object[] { "isExpanded", "position", UpdateJson.class, "update" });
  }

  public static PopularUpdatesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PopularUpdatesJson
 * JD-Core Version:    0.6.2
 */