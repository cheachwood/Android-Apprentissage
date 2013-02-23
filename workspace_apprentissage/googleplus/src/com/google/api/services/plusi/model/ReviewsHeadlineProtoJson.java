package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReviewsHeadlineProtoJson extends EsJson<ReviewsHeadlineProto>
{
  static final ReviewsHeadlineProtoJson INSTANCE = new ReviewsHeadlineProtoJson();

  private ReviewsHeadlineProtoJson()
  {
    super(ReviewsHeadlineProto.class, new Object[] { AggregatedReviewsProtoJson.class, "aggregatedReviews", PlacePageLinkJson.class, "moreLink", "userRatingE3" });
  }

  public static ReviewsHeadlineProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReviewsHeadlineProtoJson
 * JD-Core Version:    0.6.2
 */