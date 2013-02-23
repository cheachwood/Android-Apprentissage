package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlacePageOfferJson extends EsJson<PlacePageOffer>
{
  static final PlacePageOfferJson INSTANCE = new PlacePageOfferJson();

  private PlacePageOfferJson()
  {
    super(PlacePageOffer.class, new Object[] { "details", "detailsSnippet", DateJson.class, "endDate", DateJson.class, "expiryDate", "fullTitle", "id", "imageUrl", "isRelevant", PlacePageLinkJson.class, "link", "loyaltyTier", "offerSummary", "redemptionAvailability", "requiredClientCapabilities", "source", DateJson.class, "startDate", "type" });
  }

  public static PlacePageOfferJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlacePageOfferJson
 * JD-Core Version:    0.6.2
 */