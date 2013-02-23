package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedSquareJson extends EsJson<ClientLoggedSquare>
{
  static final ClientLoggedSquareJson INSTANCE = new ClientLoggedSquareJson();

  private ClientLoggedSquareJson()
  {
    super(ClientLoggedSquare.class, new Object[] { "obfuscatedGaiaId" });
  }

  public static ClientLoggedSquareJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedSquareJson
 * JD-Core Version:    0.6.2
 */