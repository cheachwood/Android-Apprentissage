package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySquaresDataSquareInviteJson extends EsJson<EntitySquaresDataSquareInvite>
{
  static final EntitySquaresDataSquareInviteJson INSTANCE = new EntitySquaresDataSquareInviteJson();

  private EntitySquaresDataSquareInviteJson()
  {
    super(EntitySquaresDataSquareInvite.class, new Object[] { "invitationMessage", "inviterOid", EntitySquaresDataSquareJson.class, "square" });
  }

  public static EntitySquaresDataSquareInviteJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresDataSquareInviteJson
 * JD-Core Version:    0.6.2
 */