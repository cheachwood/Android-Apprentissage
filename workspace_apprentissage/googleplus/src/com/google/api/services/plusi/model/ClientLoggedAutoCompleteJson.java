package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedAutoCompleteJson extends EsJson<ClientLoggedAutoComplete>
{
  static final ClientLoggedAutoCompleteJson INSTANCE = new ClientLoggedAutoCompleteJson();

  private ClientLoggedAutoCompleteJson()
  {
    super(ClientLoggedAutoComplete.class, new Object[] { "acceptedQuery", "obfuscatedGaiaId", "personalizationType", "query", "sourceNamespace", "suggestedText", "type" });
  }

  public static ClientLoggedAutoCompleteJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedAutoCompleteJson
 * JD-Core Version:    0.6.2
 */