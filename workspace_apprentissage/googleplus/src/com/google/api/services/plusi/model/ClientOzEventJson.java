package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientOzEventJson extends EsJson<ClientOzEvent>
{
  static final ClientOzEventJson INSTANCE = new ClientOzEventJson();

  private ClientOzEventJson()
  {
    super(ClientOzEvent.class, arrayOfObject);
  }

  public static ClientOzEventJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientOzEventJson
 * JD-Core Version:    0.6.2
 */