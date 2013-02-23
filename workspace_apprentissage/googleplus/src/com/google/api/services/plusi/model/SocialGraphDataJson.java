package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SocialGraphDataJson extends EsJson<SocialGraphData>
{
  static final SocialGraphDataJson INSTANCE = new SocialGraphDataJson();

  private SocialGraphDataJson()
  {
    super(SocialGraphData.class, new Object[] { "blocked", DataCircleDataJson.class, "circleData", DataCirclePersonJson.class, "circlePerson", "muted" });
  }

  public static SocialGraphDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SocialGraphDataJson
 * JD-Core Version:    0.6.2
 */