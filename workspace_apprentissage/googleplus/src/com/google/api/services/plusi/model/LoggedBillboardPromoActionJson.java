package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedBillboardPromoActionJson extends EsJson<LoggedBillboardPromoAction>
{
  static final LoggedBillboardPromoActionJson INSTANCE = new LoggedBillboardPromoActionJson();

  private LoggedBillboardPromoActionJson()
  {
    super(LoggedBillboardPromoAction.class, new Object[] { "actionArea", "promoId" });
  }

  public static LoggedBillboardPromoActionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedBillboardPromoActionJson
 * JD-Core Version:    0.6.2
 */