package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedRibbonClickJson extends EsJson<ClientLoggedRibbonClick>
{
  static final ClientLoggedRibbonClickJson INSTANCE = new ClientLoggedRibbonClickJson();

  private ClientLoggedRibbonClickJson()
  {
    super(ClientLoggedRibbonClick.class, new Object[] { "clickArea", "componentId" });
  }

  public static ClientLoggedRibbonClickJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedRibbonClickJson
 * JD-Core Version:    0.6.2
 */