package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AggregateRatingJson extends EsJson<AggregateRating>
{
  static final AggregateRatingJson INSTANCE = new AggregateRatingJson();

  private AggregateRatingJson()
  {
    super(AggregateRating.class, new Object[] { "name", "ratingValue", "url" });
  }

  public static AggregateRatingJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AggregateRatingJson
 * JD-Core Version:    0.6.2
 */