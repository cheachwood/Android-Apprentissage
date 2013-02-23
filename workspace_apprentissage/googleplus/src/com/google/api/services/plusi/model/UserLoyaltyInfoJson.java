package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UserLoyaltyInfoJson extends EsJson<UserLoyaltyInfo>
{
  static final UserLoyaltyInfoJson INSTANCE = new UserLoyaltyInfoJson();

  private UserLoyaltyInfoJson()
  {
    super(UserLoyaltyInfo.class, arrayOfObject);
  }

  public static UserLoyaltyInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UserLoyaltyInfoJson
 * JD-Core Version:    0.6.2
 */