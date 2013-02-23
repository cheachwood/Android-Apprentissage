package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileSettingsUserInfoJson extends EsJson<MobileSettingsUserInfo>
{
  static final MobileSettingsUserInfoJson INSTANCE = new MobileSettingsUserInfoJson();

  private MobileSettingsUserInfoJson()
  {
    super(MobileSettingsUserInfo.class, new Object[] { "displayName", "emailAddress", "obfuscatedGaiaId", "photoIsSilhouette", "photoUrl" });
  }

  public static MobileSettingsUserInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileSettingsUserInfoJson
 * JD-Core Version:    0.6.2
 */