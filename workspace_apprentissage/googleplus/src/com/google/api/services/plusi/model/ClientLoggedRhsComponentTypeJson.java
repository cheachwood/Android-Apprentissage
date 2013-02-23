package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedRhsComponentTypeJson extends EsJson<ClientLoggedRhsComponentType>
{
  static final ClientLoggedRhsComponentTypeJson INSTANCE = new ClientLoggedRhsComponentTypeJson();

  private ClientLoggedRhsComponentTypeJson()
  {
    super(ClientLoggedRhsComponentType.class, new Object[] { "category", "currentlyVisible", "id" });
  }

  public static ClientLoggedRhsComponentTypeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedRhsComponentTypeJson
 * JD-Core Version:    0.6.2
 */