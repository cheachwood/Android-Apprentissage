package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RatingJson extends EsJson<Rating>
{
  static final RatingJson INSTANCE = new RatingJson();

  private RatingJson()
  {
    super(Rating.class, new Object[] { "attributeId", "bestRating", RatingClientDisplayDataJson.class, "clientDisplayData", "name", "ratingValue", "url", "worstRating" });
  }

  public static RatingJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RatingJson
 * JD-Core Version:    0.6.2
 */