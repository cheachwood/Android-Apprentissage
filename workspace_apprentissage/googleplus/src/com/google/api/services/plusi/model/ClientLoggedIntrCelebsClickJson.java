package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedIntrCelebsClickJson extends EsJson<ClientLoggedIntrCelebsClick>
{
  static final ClientLoggedIntrCelebsClickJson INSTANCE = new ClientLoggedIntrCelebsClickJson();

  private ClientLoggedIntrCelebsClickJson()
  {
    super(ClientLoggedIntrCelebsClick.class, new Object[] { "categoryId" });
  }

  public static ClientLoggedIntrCelebsClickJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedIntrCelebsClickJson
 * JD-Core Version:    0.6.2
 */