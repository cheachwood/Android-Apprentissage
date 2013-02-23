package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedCircleMemberJson extends EsJson<ClientLoggedCircleMember>
{
  static final ClientLoggedCircleMemberJson INSTANCE = new ClientLoggedCircleMemberJson();

  private ClientLoggedCircleMemberJson()
  {
    super(ClientLoggedCircleMember.class, new Object[] { "contactInAnyCircle", "emailAddress", "focusContactId", "obfuscatedGaiaId", "plusPageType" });
  }

  public static ClientLoggedCircleMemberJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedCircleMemberJson
 * JD-Core Version:    0.6.2
 */