package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedSuggestionInfoJson extends EsJson<ClientLoggedSuggestionInfo>
{
  static final ClientLoggedSuggestionInfoJson INSTANCE = new ClientLoggedSuggestionInfoJson();

  private ClientLoggedSuggestionInfoJson()
  {
    super(ClientLoggedSuggestionInfo.class, arrayOfObject);
  }

  public static ClientLoggedSuggestionInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedSuggestionInfoJson
 * JD-Core Version:    0.6.2
 */