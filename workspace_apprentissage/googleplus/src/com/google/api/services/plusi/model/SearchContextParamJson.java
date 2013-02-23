package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SearchContextParamJson extends EsJson<SearchContextParam>
{
  static final SearchContextParamJson INSTANCE = new SearchContextParamJson();

  private SearchContextParamJson()
  {
    super(SearchContextParam.class, new Object[] { "name", "value" });
  }

  public static SearchContextParamJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SearchContextParamJson
 * JD-Core Version:    0.6.2
 */