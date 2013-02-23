package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReviewJson extends EsJson<Review>
{
  static final ReviewJson INSTANCE = new ReviewJson();

  private ReviewJson()
  {
    super(Review.class, new Object[] { EmbedClientItemJson.class, "about", "description", "imageUrl", "name", RatingJson.class, "reviewRating", "text", "url" });
  }

  public static ReviewJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReviewJson
 * JD-Core Version:    0.6.2
 */