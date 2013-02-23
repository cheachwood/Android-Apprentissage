package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientEmbedOptionsJson extends EsJson<ClientEmbedOptions>
{
  static final ClientEmbedOptionsJson INSTANCE = new ClientEmbedOptionsJson();

  private ClientEmbedOptionsJson()
  {
    super(ClientEmbedOptions.class, new Object[] { "includeType" });
  }

  public static ClientEmbedOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientEmbedOptionsJson
 * JD-Core Version:    0.6.2
 */