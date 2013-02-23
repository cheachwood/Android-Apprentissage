package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoyaltyTierInfoJson extends EsJson<LoyaltyTierInfo>
{
  static final LoyaltyTierInfoJson INSTANCE = new LoyaltyTierInfoJson();

  private LoyaltyTierInfoJson()
  {
    super(LoyaltyTierInfo.class, new Object[] { "loyaltyTier", "loyaltyTierImageUrl", "loyaltyTierName" });
  }

  public static LoyaltyTierInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoyaltyTierInfoJson
 * JD-Core Version:    0.6.2
 */