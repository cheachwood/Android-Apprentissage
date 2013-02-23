package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FindMorePeopleResponseJson extends EsJson<FindMorePeopleResponse>
{
  static final FindMorePeopleResponseJson INSTANCE = new FindMorePeopleResponseJson();

  private FindMorePeopleResponseJson()
  {
    super(FindMorePeopleResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "experimentNames", "experimentThreshold", DataImportedPersonJson.class, "imported", "portraitVersion", DataSuggestedPersonJson.class, "promotedSuggestion", "queryId", DataSuggestedPersonJson.class, "suggestion" });
  }

  public static FindMorePeopleResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FindMorePeopleResponseJson
 * JD-Core Version:    0.6.2
 */