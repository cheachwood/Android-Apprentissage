package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientViewerInfoJson extends EsJson<ClientViewerInfo>
{
  static final ClientViewerInfoJson INSTANCE = new ClientViewerInfoJson();

  private ClientViewerInfoJson()
  {
    super(ClientViewerInfo.class, new Object[] { "plusPageType" });
  }

  public static ClientViewerInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientViewerInfoJson
 * JD-Core Version:    0.6.2
 */