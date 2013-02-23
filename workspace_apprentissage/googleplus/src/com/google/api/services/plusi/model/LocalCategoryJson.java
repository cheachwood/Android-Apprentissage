package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocalCategoryJson extends EsJson<LocalCategory>
{
  static final LocalCategoryJson INSTANCE = new LocalCategoryJson();

  private LocalCategoryJson()
  {
    super(LocalCategory.class, new Object[] { "id", "name" });
  }

  public static LocalCategoryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocalCategoryJson
 * JD-Core Version:    0.6.2
 */