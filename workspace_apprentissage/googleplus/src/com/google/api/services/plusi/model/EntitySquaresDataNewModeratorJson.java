package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySquaresDataNewModeratorJson extends EsJson<EntitySquaresDataNewModerator>
{
  static final EntitySquaresDataNewModeratorJson INSTANCE = new EntitySquaresDataNewModeratorJson();

  private EntitySquaresDataNewModeratorJson()
  {
    super(EntitySquaresDataNewModerator.class, new Object[] { "newModeratorOid", "promoterOid", "squareOid", "toOwner" });
  }

  public static EntitySquaresDataNewModeratorJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresDataNewModeratorJson
 * JD-Core Version:    0.6.2
 */