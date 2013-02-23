package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CircleFilterJson extends EsJson<CircleFilter>
{
  static final CircleFilterJson INSTANCE = new CircleFilterJson();

  private CircleFilterJson()
  {
    super(CircleFilter.class, new Object[] { DataCircleIdsJson.class, "circleIds", "scope" });
  }

  public static CircleFilterJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CircleFilterJson
 * JD-Core Version:    0.6.2
 */