package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlaceReviewJson extends EsJson<PlaceReview>
{
  static final PlaceReviewJson INSTANCE = new PlaceReviewJson();

  private PlaceReviewJson()
  {
    super(PlaceReview.class, new Object[] { EmbedsPersonJson.class, "author", "dateModified", "dateModifiedMilliseconds", "description", EmbedClientItemJson.class, "itemReviewed", PlaceReviewMetadataJson.class, "meta", "name", "price", "reviewBody", RatingJson.class, "reviewRating", "url" });
  }

  public static PlaceReviewJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlaceReviewJson
 * JD-Core Version:    0.6.2
 */