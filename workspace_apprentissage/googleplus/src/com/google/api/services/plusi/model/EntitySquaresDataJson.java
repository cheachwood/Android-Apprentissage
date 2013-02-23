package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySquaresDataJson extends EsJson<EntitySquaresData>
{
  static final EntitySquaresDataJson INSTANCE = new EntitySquaresDataJson();

  private EntitySquaresDataJson()
  {
    super(EntitySquaresData.class, new Object[] { EntitySquaresDataSquareInviteJson.class, "invite", EntitySquaresDataSquareMembershipApprovedJson.class, "membershipApproved", EntitySquaresDataSquareMembershipRequestJson.class, "membershipRequest", EntitySquaresDataNewModeratorJson.class, "newModerator", EntitySquaresDataRenderSquaresDataJson.class, "renderSquaresData", EntitySquaresDataSquareNameChangeJson.class, "squareNameChange", EntitySquaresDataSquareSubscriptionJson.class, "subscription" });
  }

  public static EntitySquaresDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresDataJson
 * JD-Core Version:    0.6.2
 */