package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusOneSummaryJson extends EsJson<PlusOneSummary>
{
  static final PlusOneSummaryJson INSTANCE = new PlusOneSummaryJson();

  private PlusOneSummaryJson()
  {
    super(PlusOneSummary.class, new Object[] { "id" });
  }

  public static PlusOneSummaryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusOneSummaryJson
 * JD-Core Version:    0.6.2
 */