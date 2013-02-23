package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class GoogleReviewProto extends GenericJson
{
  public List<String> aspectDisliked;
  public List<String> aspectLiked;
  public AuthorProto author;
  public String businessCategory;
  public String businessCategoryId;
  public String businessTitle;
  public String fingerprint;
  public PlacePageLink flagInappropriate;
  public PlacePageLink flagOwnerResponseInappropriate;
  public String fullText;
  public String htmlFullText;
  public Boolean isBestEver;
  public Boolean isOwner;
  public String languageCode;
  public List<MediaProto> media;
  public GoogleReviewProtoMetaAnnotationKeys metaAnnotationKeys;
  public Integer numStarsE3;
  public OwnerResponseProto ownerResponse;
  public GoogleReviewProtoMetaAnnotationKeys ownerResponseKeys;
  public String permalinkUrl;
  public List<MediaProtoThumbnail> photo;
  public PriceProto price;
  public PriceLevelsProto priceLevel;
  public String publishDate;
  public String snippet;
  public String status;
  public TimeProto time;
  public String title;
  public ZagatAspectRatingsProto zagatAspectRatings;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GoogleReviewProto
 * JD-Core Version:    0.6.2
 */