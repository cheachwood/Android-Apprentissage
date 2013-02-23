package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AbuseSignalsJson extends EsJson<AbuseSignals>
{
  static final AbuseSignalsJson INSTANCE = new AbuseSignalsJson();

  private AbuseSignalsJson()
  {
    super(AbuseSignals.class, new Object[] { "headerOrder", "userAgent", "userIp" });
  }

  public static AbuseSignalsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AbuseSignalsJson
 * JD-Core Version:    0.6.2
 */