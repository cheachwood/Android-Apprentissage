package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedRibbonOrderOrderJson extends EsJson<ClientLoggedRibbonOrderOrder>
{
  static final ClientLoggedRibbonOrderOrderJson INSTANCE = new ClientLoggedRibbonOrderOrderJson();

  private ClientLoggedRibbonOrderOrderJson()
  {
    super(ClientLoggedRibbonOrderOrder.class, new Object[] { "item", "morePosition" });
  }

  public static ClientLoggedRibbonOrderOrderJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedRibbonOrderOrderJson
 * JD-Core Version:    0.6.2
 */