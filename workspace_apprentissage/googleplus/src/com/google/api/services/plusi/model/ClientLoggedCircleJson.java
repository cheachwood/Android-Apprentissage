package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedCircleJson extends EsJson<ClientLoggedCircle>
{
  static final ClientLoggedCircleJson INSTANCE = new ClientLoggedCircleJson();

  private ClientLoggedCircleJson()
  {
    super(ClientLoggedCircle.class, new Object[] { "circleId", "circleSize", "circleType", "type" });
  }

  public static ClientLoggedCircleJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedCircleJson
 * JD-Core Version:    0.6.2
 */