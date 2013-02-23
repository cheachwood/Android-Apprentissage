package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedRhsComponentJson extends EsJson<ClientLoggedRhsComponent>
{
  static final ClientLoggedRhsComponentJson INSTANCE = new ClientLoggedRhsComponentJson();

  private ClientLoggedRhsComponentJson()
  {
    super(ClientLoggedRhsComponent.class, new Object[] { "barType", ClientLoggedRhsComponentTypeJson.class, "componentType", "index", ClientLoggedRhsComponentItemJson.class, "item", ClientLoggedRhsComponentTypeJson.class, "neighborInfo", ClientLoggedSuggestionSummaryInfoJson.class, "suggestionSummaryInfo" });
  }

  public static ClientLoggedRhsComponentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedRhsComponentJson
 * JD-Core Version:    0.6.2
 */