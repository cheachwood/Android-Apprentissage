package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FeaturedActivityProtoJson extends EsJson<FeaturedActivityProto>
{
  static final FeaturedActivityProtoJson INSTANCE = new FeaturedActivityProtoJson();

  private FeaturedActivityProtoJson()
  {
    super(FeaturedActivityProto.class, new Object[] { PlaceActivityStreamEntryProtoJson.class, "activity", AuthorProtoJson.class, "checkedInUser", GoogleReviewProtoJson.class, "reviewTemplate", AuthorProtoJson.class, "reviewedUser", "totalCheckins", "totalMedia", "totalReviews", AuthorProtoJson.class, "wishlistedUser" });
  }

  public static FeaturedActivityProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FeaturedActivityProtoJson
 * JD-Core Version:    0.6.2
 */