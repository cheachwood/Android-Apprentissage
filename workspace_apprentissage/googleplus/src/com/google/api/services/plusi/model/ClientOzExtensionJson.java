package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientOzExtensionJson extends EsJson<ClientOzExtension>
{
  static final ClientOzExtensionJson INSTANCE = new ClientOzExtensionJson();

  private ClientOzExtensionJson()
  {
    super(ClientOzExtension.class, arrayOfObject);
  }

  public static ClientOzExtensionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientOzExtensionJson
 * JD-Core Version:    0.6.2
 */