package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataSuggestedCelebrityCategoryJson extends EsJson<DataSuggestedCelebrityCategory>
{
  static final DataSuggestedCelebrityCategoryJson INSTANCE = new DataSuggestedCelebrityCategoryJson();

  private DataSuggestedCelebrityCategoryJson()
  {
    super(DataSuggestedCelebrityCategory.class, new Object[] { "category", "categoryName", DataSuggestedPersonJson.class, "celebrity", "totalCelebrityCount" });
  }

  public static DataSuggestedCelebrityCategoryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSuggestedCelebrityCategoryJson
 * JD-Core Version:    0.6.2
 */