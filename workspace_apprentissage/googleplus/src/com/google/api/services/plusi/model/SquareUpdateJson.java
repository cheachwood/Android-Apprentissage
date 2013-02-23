package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareUpdateJson extends EsJson<SquareUpdate>
{
  static final SquareUpdateJson INSTANCE = new SquareUpdateJson();

  private SquareUpdateJson()
  {
    super(SquareUpdate.class, new Object[] { "obfuscatedSquareId", "squareName", "squareStreamId", "squareStreamName" });
  }

  public static SquareUpdateJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareUpdateJson
 * JD-Core Version:    0.6.2
 */