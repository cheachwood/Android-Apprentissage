package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SearchQueryResponseJson extends EsJson<SearchQueryResponse>
{
  static final SearchQueryResponseJson INSTANCE = new SearchQueryResponseJson();

  private SearchQueryResponseJson()
  {
    super(SearchQueryResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", SearchResultsJson.class, "results" });
  }

  public static SearchQueryResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SearchQueryResponseJson
 * JD-Core Version:    0.6.2
 */