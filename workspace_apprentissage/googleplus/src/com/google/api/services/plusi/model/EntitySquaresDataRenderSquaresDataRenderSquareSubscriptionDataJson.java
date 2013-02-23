package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySquaresDataRenderSquaresDataRenderSquareSubscriptionDataJson extends EsJson<EntitySquaresDataRenderSquaresDataRenderSquareSubscriptionData>
{
  static final EntitySquaresDataRenderSquaresDataRenderSquareSubscriptionDataJson INSTANCE = new EntitySquaresDataRenderSquaresDataRenderSquareSubscriptionDataJson();

  private EntitySquaresDataRenderSquaresDataRenderSquareSubscriptionDataJson()
  {
    super(EntitySquaresDataRenderSquaresDataRenderSquareSubscriptionData.class, new Object[] { "numUnread", "uniqueAuthorOid" });
  }

  public static EntitySquaresDataRenderSquaresDataRenderSquareSubscriptionDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresDataRenderSquaresDataRenderSquareSubscriptionDataJson
 * JD-Core Version:    0.6.2
 */