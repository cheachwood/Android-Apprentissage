package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProductReviewJson extends EsJson<ProductReview>
{
  static final ProductReviewJson INSTANCE = new ProductReviewJson();

  private ProductReviewJson()
  {
    super(ProductReview.class, new Object[] { EmbedsPersonJson.class, "author", "authorReviewPageUrl", "bestRatingAsInt", "description", "displayUrl", "imageUrl", "name", "productName", "productPrice", "ratingValueAsInt", RatingJson.class, "reviewRating", "reviewTruncated", "url" });
  }

  public static ProductReviewJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProductReviewJson
 * JD-Core Version:    0.6.2
 */