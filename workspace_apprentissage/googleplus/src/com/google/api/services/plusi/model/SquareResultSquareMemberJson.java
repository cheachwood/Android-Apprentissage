package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareResultSquareMemberJson extends EsJson<SquareResultSquareMember>
{
  static final SquareResultSquareMemberJson INSTANCE = new SquareResultSquareMemberJson();

  private SquareResultSquareMemberJson()
  {
    super(SquareResultSquareMember.class, new Object[] { "name", "obfuscatedGaiaId", "photoUrl" });
  }

  public static SquareResultSquareMemberJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareResultSquareMemberJson
 * JD-Core Version:    0.6.2
 */