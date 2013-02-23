package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedHangoutJson extends EsJson<ClientLoggedHangout>
{
  static final ClientLoggedHangoutJson INSTANCE = new ClientLoggedHangoutJson();

  private ClientLoggedHangoutJson()
  {
    super(ClientLoggedHangout.class, new Object[] { "roomId" });
  }

  public static ClientLoggedHangoutJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedHangoutJson
 * JD-Core Version:    0.6.2
 */