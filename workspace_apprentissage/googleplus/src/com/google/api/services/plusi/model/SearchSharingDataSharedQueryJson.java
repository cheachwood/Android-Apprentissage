package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SearchSharingDataSharedQueryJson extends EsJson<SearchSharingDataSharedQuery>
{
  static final SearchSharingDataSharedQueryJson INSTANCE = new SearchSharingDataSharedQueryJson();

  private SearchSharingDataSharedQueryJson()
  {
    super(SearchSharingDataSharedQuery.class, new Object[] { "queryText" });
  }

  public static SearchSharingDataSharedQueryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SearchSharingDataSharedQueryJson
 * JD-Core Version:    0.6.2
 */