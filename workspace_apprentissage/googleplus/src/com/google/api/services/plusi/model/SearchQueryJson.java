package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SearchQueryJson extends EsJson<SearchQuery>
{
  static final SearchQueryJson INSTANCE = new SearchQueryJson();

  private SearchQueryJson()
  {
    super(SearchQuery.class, new Object[] { CircleFilterJson.class, "circleFilter", "filter", LocationFilterJson.class, "locationFilter", "queryText", "sort", SquareFilterJson.class, "squareFilter" });
  }

  public static SearchQueryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SearchQueryJson
 * JD-Core Version:    0.6.2
 */