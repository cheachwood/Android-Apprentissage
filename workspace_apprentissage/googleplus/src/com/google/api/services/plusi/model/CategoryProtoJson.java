package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CategoryProtoJson extends EsJson<CategoryProto>
{
  static final CategoryProtoJson INSTANCE = new CategoryProtoJson();

  private CategoryProtoJson()
  {
    super(CategoryProto.class, new Object[] { "categoryLabel", GVerticalProtoJson.class, "gvertical", "showClosedZippyEllipsis", CategoryProtoItemJson.class, "zippyClosedItem", CategoryProtoItemJson.class, "zippyOpenedItem" });
  }

  public static CategoryProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CategoryProtoJson
 * JD-Core Version:    0.6.2
 */