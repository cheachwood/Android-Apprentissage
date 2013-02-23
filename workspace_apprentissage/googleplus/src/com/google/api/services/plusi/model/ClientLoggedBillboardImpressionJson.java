package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedBillboardImpressionJson extends EsJson<ClientLoggedBillboardImpression>
{
  static final ClientLoggedBillboardImpressionJson INSTANCE = new ClientLoggedBillboardImpressionJson();

  private ClientLoggedBillboardImpressionJson()
  {
    super(ClientLoggedBillboardImpression.class, new Object[] { "billboardId" });
  }

  public static ClientLoggedBillboardImpressionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedBillboardImpressionJson
 * JD-Core Version:    0.6.2
 */