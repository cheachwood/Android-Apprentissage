package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ThemeRestrictionsJson extends EsJson<ThemeRestrictions>
{
  static final ThemeRestrictionsJson INSTANCE = new ThemeRestrictionsJson();

  private ThemeRestrictionsJson()
  {
    super(ThemeRestrictions.class, new Object[] { "excludedLocales", "includedLocales" });
  }

  public static ThemeRestrictionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ThemeRestrictionsJson
 * JD-Core Version:    0.6.2
 */