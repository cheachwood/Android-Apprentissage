package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedSuggestionSummaryInfoJson extends EsJson<ClientLoggedSuggestionSummaryInfo>
{
  static final ClientLoggedSuggestionSummaryInfoJson INSTANCE = new ClientLoggedSuggestionSummaryInfoJson();

  private ClientLoggedSuggestionSummaryInfoJson()
  {
    super(ClientLoggedSuggestionSummaryInfo.class, new Object[] { "numberOfSuggestionsLoaded", "portraitVersion", "scoringExperiments" });
  }

  public static ClientLoggedSuggestionSummaryInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedSuggestionSummaryInfoJson
 * JD-Core Version:    0.6.2
 */