package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ListingLoyaltyInfoJson extends EsJson<ListingLoyaltyInfo>
{
  static final ListingLoyaltyInfoJson INSTANCE = new ListingLoyaltyInfoJson();

  private ListingLoyaltyInfoJson()
  {
    super(ListingLoyaltyInfo.class, new Object[] { "loyaltyChainId", LoyaltyTierInfoJson.class, "loyaltyTier" });
  }

  public static ListingLoyaltyInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ListingLoyaltyInfoJson
 * JD-Core Version:    0.6.2
 */