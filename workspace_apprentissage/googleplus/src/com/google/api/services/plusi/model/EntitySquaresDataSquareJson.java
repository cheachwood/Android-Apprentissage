package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySquaresDataSquareJson extends EsJson<EntitySquaresDataSquare>
{
  static final EntitySquaresDataSquareJson INSTANCE = new EntitySquaresDataSquareJson();

  private EntitySquaresDataSquareJson()
  {
    super(EntitySquaresDataSquare.class, new Object[] { "name", "oid" });
  }

  public static EntitySquaresDataSquareJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresDataSquareJson
 * JD-Core Version:    0.6.2
 */