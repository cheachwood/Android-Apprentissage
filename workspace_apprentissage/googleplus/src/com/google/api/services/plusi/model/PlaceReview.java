package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PlaceReview extends GenericJson
{
  public List<EmbedsPerson> author;
  public String dateModified;
  public String dateModifiedMilliseconds;
  public String description;
  public EmbedClientItem itemReviewed;
  public PlaceReviewMetadata meta;
  public String name;
  public String price;
  public String reviewBody;
  public List<Rating> reviewRating;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlaceReview
 * JD-Core Version:    0.6.2
 */