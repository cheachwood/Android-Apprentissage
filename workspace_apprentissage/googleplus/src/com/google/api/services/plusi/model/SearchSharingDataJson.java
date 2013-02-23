package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SearchSharingDataJson extends EsJson<SearchSharingData>
{
  static final SearchSharingDataJson INSTANCE = new SearchSharingDataJson();

  private SearchSharingDataJson()
  {
    super(SearchSharingData.class, new Object[] { SearchQueryJson.class, "query", "shareType", SearchSharingDataSharedQueryJson.class, "sharedQuery" });
  }

  public static SearchSharingDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SearchSharingDataJson
 * JD-Core Version:    0.6.2
 */