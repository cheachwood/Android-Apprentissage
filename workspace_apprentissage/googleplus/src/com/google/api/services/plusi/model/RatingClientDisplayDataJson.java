package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RatingClientDisplayDataJson extends EsJson<RatingClientDisplayData>
{
  static final RatingClientDisplayDataJson INSTANCE = new RatingClientDisplayDataJson();

  private RatingClientDisplayDataJson()
  {
    super(RatingClientDisplayData.class, new Object[] { "renderedRatingText" });
  }

  public static RatingClientDisplayDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RatingClientDisplayDataJson
 * JD-Core Version:    0.6.2
 */