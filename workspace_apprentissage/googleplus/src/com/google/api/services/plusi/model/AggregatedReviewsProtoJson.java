package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AggregatedReviewsProtoJson extends EsJson<AggregatedReviewsProto>
{
  static final AggregatedReviewsProtoJson INSTANCE = new AggregatedReviewsProtoJson();

  private AggregatedReviewsProtoJson()
  {
    super(AggregatedReviewsProto.class, new Object[] { "numBestEver", "numRatingStarsE3", "numRatings", "numReviews" });
  }

  public static AggregatedReviewsProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AggregatedReviewsProtoJson
 * JD-Core Version:    0.6.2
 */