package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ThemeImageJson extends EsJson<ThemeImage>
{
  static final ThemeImageJson INSTANCE = new ThemeImageJson();

  private ThemeImageJson()
  {
    super(ThemeImage.class, new Object[] { "aspectRatio", "format", "url" });
  }

  public static ThemeImageJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ThemeImageJson
 * JD-Core Version:    0.6.2
 */