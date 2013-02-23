package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedBillboardImpressionJson extends EsJson<LoggedBillboardImpression>
{
  static final LoggedBillboardImpressionJson INSTANCE = new LoggedBillboardImpressionJson();

  private LoggedBillboardImpressionJson()
  {
    super(LoggedBillboardImpression.class, new Object[] { "billboardId" });
  }

  public static LoggedBillboardImpressionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedBillboardImpressionJson
 * JD-Core Version:    0.6.2
 */