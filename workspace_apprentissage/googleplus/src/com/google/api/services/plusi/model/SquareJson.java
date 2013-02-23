package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareJson extends EsJson<Square>
{
  static final SquareJson INSTANCE = new SquareJson();

  private SquareJson()
  {
    super(Square.class, new Object[] { "disableSubscription", "joinability", "obfuscatedGaiaId", SquareProfileJson.class, "profile", SquareVisibilityJson.class, "visibility" });
  }

  public static SquareJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareJson
 * JD-Core Version:    0.6.2
 */