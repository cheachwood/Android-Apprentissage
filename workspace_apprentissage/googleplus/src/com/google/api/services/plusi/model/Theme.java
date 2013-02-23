package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class Theme extends GenericJson
{
  public List<EventCategory> category;
  public List<ThemeImage> image;
  public ThemeRestrictions restrictions;
  public Integer themeId;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Theme
 * JD-Core Version:    0.6.2
 */