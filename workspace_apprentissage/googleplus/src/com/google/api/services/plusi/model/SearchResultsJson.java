package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SearchResultsJson extends EsJson<SearchResults>
{
  static final SearchResultsJson INSTANCE = new SearchResultsJson();

  private SearchResultsJson()
  {
    super(SearchResults.class, new Object[] { ActivityResultsJson.class, "activityResults", "debugInfoHtml", "decorationMode", PeopleResultsJson.class, "peopleResults", SpellingSuggestionsJson.class, "spellingSuggestions", SquareResultsJson.class, "squareResults", "status" });
  }

  public static SearchResultsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SearchResultsJson
 * JD-Core Version:    0.6.2
 */