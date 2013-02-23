package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareMemberJson extends EsJson<SquareMember>
{
  static final SquareMemberJson INSTANCE = new SquareMemberJson();

  private SquareMemberJson()
  {
    super(SquareMember.class, new Object[] { "displayName", "isPlusPage", "membershipStatus", "obfuscatedGaiaId", "photoUrl", DataCircleMemberPropertiesJson.class, "plusPageMemberProperties", "tagLine" });
  }

  public static SquareMemberJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareMemberJson
 * JD-Core Version:    0.6.2
 */