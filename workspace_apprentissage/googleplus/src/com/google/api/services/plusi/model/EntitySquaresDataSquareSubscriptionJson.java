package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySquaresDataSquareSubscriptionJson extends EsJson<EntitySquaresDataSquareSubscription>
{
  static final EntitySquaresDataSquareSubscriptionJson INSTANCE = new EntitySquaresDataSquareSubscriptionJson();

  private EntitySquaresDataSquareSubscriptionJson()
  {
    super(EntitySquaresDataSquareSubscription.class, new Object[] { "activityId", "authorOid", "isRead", EntitySquaresDataSquareJson.class, "square" });
  }

  public static EntitySquaresDataSquareSubscriptionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresDataSquareSubscriptionJson
 * JD-Core Version:    0.6.2
 */