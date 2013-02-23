package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GoogleReviewsProtoJson extends EsJson<GoogleReviewsProto>
{
  static final GoogleReviewsProtoJson INSTANCE = new GoogleReviewsProtoJson();

  private GoogleReviewsProtoJson()
  {
    super(GoogleReviewsProto.class, new Object[] { PlacePageLinkJson.class, "moreReviewsLink", NavbarProtoJson.class, "navbar", ResultsRangeProtoJson.class, "resultsRange", GoogleReviewProtoJson.class, "review", ReviewsDataProtoJson.class, "reviewData", AggregatedReviewsProtoJson.class, "stats", StoryTitleJson.class, "title" });
  }

  public static GoogleReviewsProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GoogleReviewsProtoJson
 * JD-Core Version:    0.6.2
 */