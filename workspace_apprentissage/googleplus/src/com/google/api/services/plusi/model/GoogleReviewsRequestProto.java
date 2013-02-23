package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class GoogleReviewsRequestProto extends GenericJson
{
  public CommonReviewOptionsProto commonOptions;
  public Double qualityScoreThreshold;
  public Boolean requestOwnerResponses;
  public Boolean requestZagatReviews;
  public String sortBy;
  public Boolean suppressRatingOnlyReviews;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GoogleReviewsRequestProto
 * JD-Core Version:    0.6.2
 */