package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareResultsJson extends EsJson<SquareResults>
{
  static final SquareResultsJson INSTANCE = new SquareResultsJson();

  private SquareResultsJson()
  {
    super(SquareResults.class, new Object[] { SquareResultJson.class, "result" });
  }

  public static SquareResultsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareResultsJson
 * JD-Core Version:    0.6.2
 */