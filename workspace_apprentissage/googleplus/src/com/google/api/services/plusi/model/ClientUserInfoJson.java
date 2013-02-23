package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientUserInfoJson extends EsJson<ClientUserInfo>
{
  static final ClientUserInfoJson INSTANCE = new ClientUserInfoJson();

  private ClientUserInfoJson()
  {
    super(ClientUserInfo.class, new Object[] { "entityTypeId", "obfuscatedGaiaId", "plusPageType", "userContext" });
  }

  public static ClientUserInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientUserInfoJson
 * JD-Core Version:    0.6.2
 */