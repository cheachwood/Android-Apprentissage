package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SearchContextJson extends EsJson<SearchContext>
{
  static final SearchContextJson INSTANCE = new SearchContextJson();

  private SearchContextJson()
  {
    super(SearchContext.class, new Object[] { ClientOverridesJson.class, "clientOverrides", LocationDataJson.class, "location", SearchContextParamJson.class, "param", ChipDataJson.class, "whatChip", "whatQuery", ChipDataJson.class, "whereChip", "whereQuery" });
  }

  public static SearchContextJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SearchContextJson
 * JD-Core Version:    0.6.2
 */