package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ThemeJson extends EsJson<Theme>
{
  static final ThemeJson INSTANCE = new ThemeJson();

  private ThemeJson()
  {
    super(Theme.class, new Object[] { EventCategoryJson.class, "category", ThemeImageJson.class, "image", ThemeRestrictionsJson.class, "restrictions", "themeId" });
  }

  public static ThemeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ThemeJson
 * JD-Core Version:    0.6.2
 */