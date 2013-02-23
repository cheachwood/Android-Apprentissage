package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedBillboardPromoActionJson extends EsJson<ClientLoggedBillboardPromoAction>
{
  static final ClientLoggedBillboardPromoActionJson INSTANCE = new ClientLoggedBillboardPromoActionJson();

  private ClientLoggedBillboardPromoActionJson()
  {
    super(ClientLoggedBillboardPromoAction.class, new Object[] { "actionArea", "promoId" });
  }

  public static ClientLoggedBillboardPromoActionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedBillboardPromoActionJson
 * JD-Core Version:    0.6.2
 */