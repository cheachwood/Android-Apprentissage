package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GoogleReviewProtoJson extends EsJson<GoogleReviewProto>
{
  static final GoogleReviewProtoJson INSTANCE = new GoogleReviewProtoJson();

  private GoogleReviewProtoJson()
  {
    super(GoogleReviewProto.class, new Object[] { "aspectDisliked", "aspectLiked", AuthorProtoJson.class, "author", "businessCategory", "businessCategoryId", "businessTitle", "fingerprint", PlacePageLinkJson.class, "flagInappropriate", PlacePageLinkJson.class, "flagOwnerResponseInappropriate", "fullText", "htmlFullText", "isBestEver", "isOwner", "languageCode", MediaProtoJson.class, "media", GoogleReviewProtoMetaAnnotationKeysJson.class, "metaAnnotationKeys", "numStarsE3", OwnerResponseProtoJson.class, "ownerResponse", GoogleReviewProtoMetaAnnotationKeysJson.class, "ownerResponseKeys", "permalinkUrl", MediaProtoThumbnailJson.class, "photo", PriceProtoJson.class, "price", PriceLevelsProtoJson.class, "priceLevel", "publishDate", "snippet", "status", TimeProtoJson.class, "time", "title", ZagatAspectRatingsProtoJson.class, "zagatAspectRatings" });
  }

  public static GoogleReviewProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GoogleReviewProtoJson
 * JD-Core Version:    0.6.2
 */