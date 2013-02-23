package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class OffersProto extends GenericJson
{
  public Integer desktopRedeemable;
  public ListingLoyaltyInfo listingLoyaltyInfo;
  public Integer mobileRedeemable;
  public PlacePageLink moreLink;
  public NavbarProto navbar;
  public List<PlacePageOffer> offer;
  public ResultsRangeProto resultsRange;
  public StoryTitle title;
  public UserLoyaltyInfo userLoyaltyInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OffersProto
 * JD-Core Version:    0.6.2
 */