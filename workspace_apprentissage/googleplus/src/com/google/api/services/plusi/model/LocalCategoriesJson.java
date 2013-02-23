package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocalCategoriesJson extends EsJson<LocalCategories>
{
  static final LocalCategoriesJson INSTANCE = new LocalCategoriesJson();

  private LocalCategoriesJson()
  {
    super(LocalCategories.class, new Object[] { LocalCategoryJson.class, "category", MetadataJson.class, "metadata" });
  }

  public static LocalCategoriesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocalCategoriesJson
 * JD-Core Version:    0.6.2
 */