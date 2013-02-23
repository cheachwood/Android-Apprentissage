package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GoogleReviewsRequestProtoJson extends EsJson<GoogleReviewsRequestProto>
{
  static final GoogleReviewsRequestProtoJson INSTANCE = new GoogleReviewsRequestProtoJson();

  private GoogleReviewsRequestProtoJson()
  {
    super(GoogleReviewsRequestProto.class, new Object[] { CommonReviewOptionsProtoJson.class, "commonOptions", "qualityScoreThreshold", "requestOwnerResponses", "requestZagatReviews", "sortBy", "suppressRatingOnlyReviews" });
  }

  public static GoogleReviewsRequestProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GoogleReviewsRequestProtoJson
 * JD-Core Version:    0.6.2
 */