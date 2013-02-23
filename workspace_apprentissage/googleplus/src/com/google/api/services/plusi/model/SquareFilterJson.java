package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareFilterJson extends EsJson<SquareFilter>
{
  static final SquareFilterJson INSTANCE = new SquareFilterJson();

  private SquareFilterJson()
  {
    super(SquareFilter.class, new Object[] { "scope", SquaresParamsJson.class, "squaresParams" });
  }

  public static SquareFilterJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareFilterJson
 * JD-Core Version:    0.6.2
 */