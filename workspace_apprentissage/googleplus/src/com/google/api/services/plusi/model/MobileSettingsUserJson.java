package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileSettingsUserJson extends EsJson<MobileSettingsUser>
{
  static final MobileSettingsUserJson INSTANCE = new MobileSettingsUserJson();

  private MobileSettingsUserJson()
  {
    super(MobileSettingsUser.class, new Object[] { MobileSettingsUserInfoJson.class, "info", "isChild", "isPlusPage", MobileSettingsUserInfoJson.class, "plusPageInfo" });
  }

  public static MobileSettingsUserJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileSettingsUserJson
 * JD-Core Version:    0.6.2
 */