package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OffersProtoJson extends EsJson<OffersProto>
{
  static final OffersProtoJson INSTANCE = new OffersProtoJson();

  private OffersProtoJson()
  {
    super(OffersProto.class, new Object[] { "desktopRedeemable", ListingLoyaltyInfoJson.class, "listingLoyaltyInfo", "mobileRedeemable", PlacePageLinkJson.class, "moreLink", NavbarProtoJson.class, "navbar", PlacePageOfferJson.class, "offer", ResultsRangeProtoJson.class, "resultsRange", StoryTitleJson.class, "title", UserLoyaltyInfoJson.class, "userLoyaltyInfo" });
  }

  public static OffersProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OffersProtoJson
 * JD-Core Version:    0.6.2
 */