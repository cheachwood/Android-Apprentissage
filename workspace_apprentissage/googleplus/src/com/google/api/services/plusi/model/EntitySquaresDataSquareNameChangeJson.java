package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySquaresDataSquareNameChangeJson extends EsJson<EntitySquaresDataSquareNameChange>
{
  static final EntitySquaresDataSquareNameChangeJson INSTANCE = new EntitySquaresDataSquareNameChangeJson();

  private EntitySquaresDataSquareNameChangeJson()
  {
    super(EntitySquaresDataSquareNameChange.class, new Object[] { "moderatorOid", "newSquareName", "oldSquareName", "squareOid" });
  }

  public static EntitySquaresDataSquareNameChangeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySquaresDataSquareNameChangeJson
 * JD-Core Version:    0.6.2
 */