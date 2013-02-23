package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class FeaturedActivityProto extends GenericJson
{
  public List<PlaceActivityStreamEntryProto> activity;
  public List<AuthorProto> checkedInUser;
  public List<GoogleReviewProto> reviewTemplate;
  public List<AuthorProto> reviewedUser;
  public Integer totalCheckins;
  public Integer totalMedia;
  public Integer totalReviews;
  public List<AuthorProto> wishlistedUser;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FeaturedActivityProto
 * JD-Core Version:    0.6.2
 */