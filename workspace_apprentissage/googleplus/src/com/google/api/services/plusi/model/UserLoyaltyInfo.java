package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class UserLoyaltyInfo extends GenericJson
{
  public Integer currentTier;
  public Long currentTierTimestampMs;
  public Integer downgradeTier;
  public Long downgradeTierTimestampMs;
  public Integer upgradeTier;
  public Long upgradeTierTimestampMs;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UserLoyaltyInfo
 * JD-Core Version:    0.6.2
 */