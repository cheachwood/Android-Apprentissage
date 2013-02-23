package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquaresParamsJson extends EsJson<SquaresParams>
{
  static final SquaresParamsJson INSTANCE = new SquaresParamsJson();

  private SquaresParamsJson()
  {
    super(SquaresParams.class, new Object[] { "squareId", "streamId" });
  }

  public static SquaresParamsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquaresParamsJson
 * JD-Core Version:    0.6.2
 */