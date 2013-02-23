package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedRibbonOrderJson extends EsJson<ClientLoggedRibbonOrder>
{
  static final ClientLoggedRibbonOrderJson INSTANCE = new ClientLoggedRibbonOrderJson();

  private ClientLoggedRibbonOrderJson()
  {
    super(ClientLoggedRibbonOrder.class, new Object[] { "componentId", "droppedArea", ClientLoggedRibbonOrderOrderJson.class, "newOrder", ClientLoggedRibbonOrderOrderJson.class, "prevOrder" });
  }

  public static ClientLoggedRibbonOrderJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedRibbonOrderJson
 * JD-Core Version:    0.6.2
 */