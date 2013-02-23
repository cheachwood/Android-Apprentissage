package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UserInfoJson extends EsJson<UserInfo>
{
  static final UserInfoJson INSTANCE = new UserInfoJson();

  private UserInfoJson()
  {
    super(UserInfo.class, arrayOfObject);
  }

  public static UserInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UserInfoJson
 * JD-Core Version:    0.6.2
 */