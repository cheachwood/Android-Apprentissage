package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ProductReview extends GenericJson
{
  public List<EmbedsPerson> author;
  public String authorReviewPageUrl;
  public Integer bestRatingAsInt;
  public String description;
  public String displayUrl;
  public String imageUrl;
  public String name;
  public String productName;
  public String productPrice;
  public Integer ratingValueAsInt;
  public List<Rating> reviewRating;
  public Boolean reviewTruncated;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProductReview
 * JD-Core Version:    0.6.2
 */