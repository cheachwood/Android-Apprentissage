package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareTargetIdJson extends EsJson<SquareTargetId>
{
  static final SquareTargetIdJson INSTANCE = new SquareTargetIdJson();

  private SquareTargetIdJson()
  {
    super(SquareTargetId.class, new Object[] { "obfuscatedSquareId" });
  }

  public static SquareTargetIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareTargetIdJson
 * JD-Core Version:    0.6.2
 */