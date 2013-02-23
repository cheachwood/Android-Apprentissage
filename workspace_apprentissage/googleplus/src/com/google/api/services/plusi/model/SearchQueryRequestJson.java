package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SearchQueryRequestJson extends EsJson<SearchQueryRequest>
{
  static final SearchQueryRequestJson INSTANCE = new SearchQueryRequestJson();

  private SearchQueryRequestJson()
  {
    super(SearchQueryRequest.class, new Object[] { ActivityRequestDataJson.class, "activityRequestData", ApiaryFieldsJson.class, "commonFields", "contentFormat", ClientEmbedOptionsJson.class, "embedOptions", "enableTracing", PeopleRequestDataJson.class, "peopleRequestData", SearchQueryJson.class, "searchQuery" });
  }

  public static SearchQueryRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SearchQueryRequestJson
 * JD-Core Version:    0.6.2
 */