package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CategoryProtoItemJson extends EsJson<CategoryProtoItem>
{
  static final CategoryProtoItemJson INSTANCE = new CategoryProtoItemJson();

  private CategoryProtoItemJson()
  {
    super(CategoryProtoItem.class, new Object[] { "id", "name" });
  }

  public static CategoryProtoItemJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CategoryProtoItemJson
 * JD-Core Version:    0.6.2
 */