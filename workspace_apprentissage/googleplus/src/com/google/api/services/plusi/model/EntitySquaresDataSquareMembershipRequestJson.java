package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySquaresDataSquareMembershipRequestJson extends EsJson<EntitySquaresDataSquareMembershipRequest>
{
  static final EntitySquaresDataSquareMembershipRequestJson INSTANCE = new EntitySquaresDataSquareMembershipRequestJson();

  private EntitySquaresDataSquareMembershipRequestJson()
  {
    super(EntitySquaresDataSquareMembershipRequest.class, new Object[] { "requesterOid", EntitySquaresDataSquareJson.class, "square" });
  }

  public static EntitySquaresDataSquareMembershipRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresDataSquareMembershipRequestJson
 * JD-Core Version:    0.6.2
 */