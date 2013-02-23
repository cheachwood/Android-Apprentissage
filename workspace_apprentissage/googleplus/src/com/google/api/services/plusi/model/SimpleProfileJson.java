package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SimpleProfileJson extends EsJson<SimpleProfile>
{
  static final SimpleProfileJson INSTANCE = new SimpleProfileJson();

  private SimpleProfileJson()
  {
    super(SimpleProfile.class, new Object[] { CommonConfigJson.class, "config", CommonContentJson.class, "content", "displayName", "obfuscatedGaiaId", PageJson.class, "page", "profileType", SharingRosterDataJson.class, "rosterData", UserJson.class, "user" });
  }

  public static SimpleProfileJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SimpleProfileJson
 * JD-Core Version:    0.6.2
 */