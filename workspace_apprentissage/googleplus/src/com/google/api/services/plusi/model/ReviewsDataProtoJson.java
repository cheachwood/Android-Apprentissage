package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReviewsDataProtoJson extends EsJson<ReviewsDataProto>
{
  static final ReviewsDataProtoJson INSTANCE = new ReviewsDataProtoJson();

  private ReviewsDataProtoJson()
  {
    super(ReviewsDataProto.class, new Object[] { "address", "authorNickname", "businessName", "cid", "country", ReviewsDataProtoExistingUserReviewSectionJson.class, "existingUserReview", "gaiaNicknamePresent", "latitudeE6", "longitudeE6", "mapsAuthToken", "phone1", "phone2", "reviewCount", "url" });
  }

  public static ReviewsDataProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReviewsDataProtoJson
 * JD-Core Version:    0.6.2
 */