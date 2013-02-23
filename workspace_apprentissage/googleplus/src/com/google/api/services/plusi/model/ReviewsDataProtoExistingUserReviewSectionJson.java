package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReviewsDataProtoExistingUserReviewSectionJson extends EsJson<ReviewsDataProtoExistingUserReviewSection>
{
  static final ReviewsDataProtoExistingUserReviewSectionJson INSTANCE = new ReviewsDataProtoExistingUserReviewSectionJson();

  private ReviewsDataProtoExistingUserReviewSectionJson()
  {
    super(ReviewsDataProtoExistingUserReviewSection.class, new Object[] { "body", "date", "starRating", "title" });
  }

  public static ReviewsDataProtoExistingUserReviewSectionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReviewsDataProtoExistingUserReviewSectionJson
 * JD-Core Version:    0.6.2
 */