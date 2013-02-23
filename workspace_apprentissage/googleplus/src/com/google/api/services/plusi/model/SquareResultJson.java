package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareResultJson extends EsJson<SquareResult>
{
  static final SquareResultJson INSTANCE = new SquareResultJson();

  private SquareResultJson()
  {
    super(SquareResult.class, arrayOfObject);
  }

  public static SquareResultJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareResultJson
 * JD-Core Version:    0.6.2
 */