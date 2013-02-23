package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocalEntityInfoJson extends EsJson<LocalEntityInfo>
{
  static final LocalEntityInfoJson INSTANCE = new LocalEntityInfoJson();

  private LocalEntityInfoJson()
  {
    super(LocalEntityInfo.class, new Object[] { BusinessHoursFieldJson.class, "businessHours", LocalCategoriesJson.class, "categories", "cid", LocalFieldsWithDiffJson.class, "localFieldsWithDiff", SearchContextJson.class, "localSearchContext", FrontendPaperProtoJson.class, "paper", PriceRangeFieldJson.class, "priceRange", "type", "verificationStatus" });
  }

  public static LocalEntityInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocalEntityInfoJson
 * JD-Core Version:    0.6.2
 */