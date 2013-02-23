package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FrontendPaperProtoJson extends EsJson<FrontendPaperProto>
{
  static final FrontendPaperProtoJson INSTANCE = new FrontendPaperProtoJson();

  private FrontendPaperProtoJson()
  {
    super(FrontendPaperProto.class, new Object[] { PlacePageAddressProtoJson.class, "address", ListingAttributionProtoJson.class, "attribution", AuthorityPageProtoJson.class, "authorityPage", CategoryProtoJson.class, "category", FeaturedActivityProtoJson.class, "circleActivity", CityblockProtoJson.class, "cityblock", CoverPhotoProtoJson.class, "coverPhoto", CrossStreetsProtoJson.class, "crossStreets", FeaturedActivityProtoJson.class, "deprecatedFeaturedActivity", DetailsProtoJson.class, "details", EditStatusProtoJson.class, "editStatus", "ei", FollowStatusProtoJson.class, "followStatus", GooglePlusSignupPromoJson.class, "googlePlusSignupPromo", GoogleReviewsProtoJson.class, "googleReviews", HierarchiesChildrenProtoJson.class, "hierarchiesChildren", FrontendPaperProtoJson.class, "hierarchiesParent", HotelPriceProtoJson.class, "hotelPrice", AttributeProtoJson.class, "hotelRatingStars", JustificationsJson.class, "justifications", KnownForTermsProtoJson.class, "knownForTerms", LifecycleStoryProtoJson.class, "lifecycle", LiveMapsProtoJson.class, "livemaps", AttributesProtoJson.class, "menuUrls", NearbyTransitProtoJson.class, "nearbyTransit", OffersProtoJson.class, "offers", OpeningHoursProtoJson.class, "openingHours", OwnerUpdatesProtoJson.class, "ownerUpdates", DescriptionJson.class, "ownersDescription", PhoneProtoJson.class, "phone", PhotoVideoProtoJson.class, "photoVideo", PlaceInfoJson.class, "placeInfo", AttributeProtoJson.class, "price", AttributeProtoJson.class, "priceContinuous", ProviderBlocksProtoJson.class, "providerBlocks", FeaturedActivityProtoJson.class, "queriedActivity", FrontendPaperProtoJson.class, "relatedPlaces", AttributesProtoJson.class, "reservationUrls", ReviewsHeadlineProtoJson.class, "reviewsHeadline", DescriptionJson.class, "searchSnippet", EditLinksProtoJson.class, "sesameEditLinks", StarredItemProtoJson.class, "starredItem", StaticMapProtoJson.class, "staticMap", StructuredAddressProtoJson.class, "structuredAddress", TitleProtoJson.class, "title", FeaturedActivityProtoJson.class, "userActivity", "ved", ZagatAspectRatingsProtoJson.class, "zagatAspectRatings", DescriptionJson.class, "zagatEditorialReview" });
  }

  public static FrontendPaperProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FrontendPaperProtoJson
 * JD-Core Version:    0.6.2
 */