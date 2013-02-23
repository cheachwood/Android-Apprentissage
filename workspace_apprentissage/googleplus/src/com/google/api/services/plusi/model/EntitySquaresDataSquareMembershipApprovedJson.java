package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySquaresDataSquareMembershipApprovedJson extends EsJson<EntitySquaresDataSquareMembershipApproved>
{
  static final EntitySquaresDataSquareMembershipApprovedJson INSTANCE = new EntitySquaresDataSquareMembershipApprovedJson();

  private EntitySquaresDataSquareMembershipApprovedJson()
  {
    super(EntitySquaresDataSquareMembershipApproved.class, new Object[] { EntitySquaresDataSquareJson.class, "square" });
  }

  public static EntitySquaresDataSquareMembershipApprovedJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresDataSquareMembershipApprovedJson
 * JD-Core Version:    0.6.2
 */