package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FavaDiagnosticsRequestStatJson extends EsJson<FavaDiagnosticsRequestStat>
{
  static final FavaDiagnosticsRequestStatJson INSTANCE = new FavaDiagnosticsRequestStatJson();

  private FavaDiagnosticsRequestStatJson()
  {
    super(FavaDiagnosticsRequestStat.class, new Object[] { "failedAttemptsTime", "networkTime", "numFailedAttempts", "requestPath", "serverTime", "totalContributingTime" });
  }

  public static FavaDiagnosticsRequestStatJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FavaDiagnosticsRequestStatJson
 * JD-Core Version:    0.6.2
 */