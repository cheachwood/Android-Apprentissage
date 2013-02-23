package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySquaresDataRenderSquaresDataJson extends EsJson<EntitySquaresDataRenderSquaresData>
{
  static final EntitySquaresDataRenderSquaresDataJson INSTANCE = new EntitySquaresDataRenderSquaresDataJson();

  private EntitySquaresDataRenderSquaresDataJson()
  {
    super(EntitySquaresDataRenderSquaresData.class, new Object[] { EntitySquaresDataRenderSquaresDataRenderSquareSubscriptionDataJson.class, "renderSubscriptionData" });
  }

  public static EntitySquaresDataRenderSquaresDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresDataRenderSquaresDataJson
 * JD-Core Version:    0.6.2
 */