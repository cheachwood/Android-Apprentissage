package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareIdJson extends EsJson<SquareId>
{
  static final SquareIdJson INSTANCE = new SquareIdJson();

  private SquareIdJson()
  {
    super(SquareId.class, new Object[] { "obfuscatedGaiaId" });
  }

  public static SquareIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareIdJson
 * JD-Core Version:    0.6.2
 */