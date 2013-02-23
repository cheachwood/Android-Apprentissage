package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ThemeSpecificationJson extends EsJson<ThemeSpecification>
{
  static final ThemeSpecificationJson INSTANCE = new ThemeSpecificationJson();

  private ThemeSpecificationJson()
  {
    super(ThemeSpecification.class, new Object[] { "imageUrl", "themeId" });
  }

  public static ThemeSpecificationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ThemeSpecificationJson
 * JD-Core Version:    0.6.2
 */