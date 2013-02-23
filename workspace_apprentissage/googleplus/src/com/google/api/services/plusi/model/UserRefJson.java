package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UserRefJson extends EsJson<UserRef>
{
  static final UserRefJson INSTANCE = new UserRefJson();

  private UserRefJson()
  {
    super(UserRef.class, new Object[] { "email", "name", "userId" });
  }

  public static UserRefJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UserRefJson
 * JD-Core Version:    0.6.2
 */